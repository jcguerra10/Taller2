package com.taller2.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taller2.demo.model.prod.Product;
import com.taller2.demo.model.prod.Productcosthistory;
import com.taller2.demo.model.prod.ProductcosthistoryPK;
import com.taller2.demo.model.prod.UserApp;
import com.taller2.demo.repositories.ProductRepository;
import com.taller2.demo.repositories.ProductcosthistoryRepository;
import com.taller2.demo.services.ProductServiceImp;
import com.taller2.demo.services.ProductcosthistoryServiceImp;

@Controller
public class HistoricCostController {

	@Autowired
	private ProductcosthistoryRepository productcosthistoryRepository;
	@Autowired
	private ProductcosthistoryServiceImp productcosthistoryServiceImp;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductServiceImp productServiceImp;

	@GetMapping("/historiccosts/")
	public String products(Model model) {
		model.addAttribute("historiccosts", productcosthistoryRepository.findAll());
		return "/historicCosts/index";
	}

	@GetMapping("/historiccosts/add/")
	public String productAddScreen(Model model) {
		model.addAttribute(new Productcosthistory());
		model.addAttribute("products", productRepository.findAll());
		return "/historiccosts/add";
	}

	@PostMapping("/historiccosts/add/")
	public String productAdd(@Valid @ModelAttribute Productcosthistory productcosthistory, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action, String startdatelb,
			String enddatelb) {

		String ret = "redirect:/historiccosts/";

		if (!action.equals("Cancel")) {
			if (!bindingResult.hasErrors() && startdatelb.length() != 0 && enddatelb.length() != 0) {
				
				Timestamp startDate = Convert(startdatelb);
				Timestamp endDate = Convert(enddatelb);

				productcosthistory.setEnddate(endDate);
				ProductcosthistoryPK pk = new ProductcosthistoryPK();
				pk.setProductid(productcosthistory.getProduct().getProductid());
				pk.setStartdate(startDate);
				productcosthistory.setId(pk);
				productcosthistoryServiceImp.saveProductcosthistory(productcosthistory);
			} else {
				ret = "/historiccosts/add";
				model.addAttribute(new Productcosthistory());
				model.addAttribute("products", productRepository.findAll());
			}
		}
		return ret;
	}

	private Timestamp Convert(String date) {
		String res = "";
		String[] splt = null;
		
		if (!date.isEmpty() || !date.isBlank()) {
			splt = date.split("T");

			res += splt[0];

			res += " " + splt[1] + ":00";
		}
		return Timestamp.valueOf(res);
	}

	// ----------------- EDIT -----------------

	@GetMapping("/historiccosts/edit/{id}")
	public String editHistoriccostScreen(@PathVariable("id") String id, Model model) {
		String[] spl = id.split("_");

		ProductcosthistoryPK pk = new ProductcosthistoryPK();

		pk.setProductid(Integer.parseInt(spl[0]));
		pk.setStartdate(Timestamp.valueOf(spl[1]));

		Optional<Productcosthistory> find = productcosthistoryRepository.findById(pk);
		if (find.isEmpty())
			throw new IllegalArgumentException("Invalid Id:" + id);
		model.addAttribute("pk", pk);
		model.addAttribute("productcosthistory", find.get());
		model.addAttribute("products", productRepository.findAll());
		return "/historiccosts/edit";
	}

	@PostMapping("/historiccosts/edit/{id}")
	public String editHistoriccosts(Model model, @PathVariable("id") String id,
			@RequestParam(value = "action", required = true) String action, String enddatelb, String standardcost) {

		Productcosthistory productcosthistoric = new Productcosthistory();
		
		String[] spl = id.split("_");
		ProductcosthistoryPK pk = new ProductcosthistoryPK();

		System.out.println(spl[0]);

		pk.setProductid(Integer.parseInt(spl[0]));
		pk.setStartdate(Timestamp.valueOf(spl[1]));

		productcosthistoric.setId(pk);
		

		String dir = "redirect:/historiccosts/";

		if (!action.equals("Cancel")) {
			Timestamp endDate = Convert(enddatelb);

			Product p = productRepository.findById(Integer.parseInt(spl[0])).get();
			productcosthistoric.setEnddate(endDate);
			productcosthistoric.setStandardcost(BigDecimal.valueOf(Double.parseDouble(standardcost)));
			productcosthistoric.setProduct(p);

			productcosthistoryServiceImp.editProductcosthistory(productcosthistoric, pk);
			model.addAttribute("historiccosts", productRepository.findAll());
		}
		return dir;
	}

}
