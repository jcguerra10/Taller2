package com.taller2.demo.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
			Model model, @RequestParam(value = "action", required = true) String action, String startdatelb,String enddatelb) {

		String ret = "redirect:/historiccosts/";

		if (!action.equals("Cancel")) {
			if (!bindingResult.hasErrors()) {
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

}
