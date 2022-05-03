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
			Model model, @RequestParam(value = "action", required = true) String action) {

		String ret = "redirect:/historiccosts/";

		if (!action.equals("Cancel")) {
			if (!bindingResult.hasErrors()) {
				productcosthistoryServiceImp.saveProductcosthistory(productcosthistory);
			} else {
				bindingResult.getAllErrors().forEach(t ->
				System.out.println(t)
				);
				
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
	public String editHistoriccostScreen(@PathVariable("id") Integer id, Model model) {
		
		Optional<Productcosthistory> find = productcosthistoryRepository.findById(id);
		if (find.isEmpty())
			throw new IllegalArgumentException("Invalid Id:" + id);
		model.addAttribute("productcosthistory", find.get());
		model.addAttribute("products", productRepository.findAll());
		return "/historiccosts/edit";
	}

	@PostMapping("/historiccosts/edit/{id}")
	public String editHistoriccosts(@Valid @ModelAttribute Productcosthistory productcosthistory, BindingResult bindingResult,Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action, String enddatelb, String standardcost) {


		String dir = "redirect:/historiccosts/";

		if (!action.equals("Cancel")) {
			
			productcosthistoryServiceImp.editProductcosthistory(productcosthistory, id);
		}
		return dir;
	}

}
