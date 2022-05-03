package com.taller2.demo.controller;

import java.sql.Timestamp;

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

import com.taller2.demo.model.prod.ProductcosthistoryPK;
import com.taller2.demo.model.prod.Productinventory;
import com.taller2.demo.model.prod.ProductinventoryPK;
import com.taller2.demo.model.prod.UserApp;
import com.taller2.demo.repositories.LocationRepository;
import com.taller2.demo.repositories.ProductInventoryRepository;
import com.taller2.demo.repositories.ProductRepository;
import com.taller2.demo.services.LocationServiceImp;
import com.taller2.demo.services.ProductInventoryServiceImp;
import com.taller2.demo.services.ProductServiceImp;

@Controller
public class InventoryController {

	@Autowired
	private ProductInventoryRepository productInventoryRepository;
	@Autowired
	private ProductInventoryServiceImp productInventoryServiceImp;

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private LocationServiceImp locationServiceImp;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductServiceImp productServiceImp;

	@GetMapping("/inventoryproduct/")
	public String products(Model model) {
		model.addAttribute("productsinventory", productInventoryRepository.findAll());
		
		
		
		productInventoryRepository.findAll().forEach(pi ->
		
		System.out.println("---" +pi.getLocation() == null)
				);
		return "/inventoryproduct/index";
	}

	@GetMapping("/inventoryproduct/add/")
	public String addInventoryScreen(Model model) {
		model.addAttribute("productinventory", new Productinventory());
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("locations", locationRepository.findAll());
		return "/inventoryproduct/add";
	}

	@PostMapping("inventoryproduct/add/")
	public String addInventory(@Valid @ModelAttribute Productinventory productInventory, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action, Integer quantity) {
		String ret = "redirect:/inventoryproduct/";
		
		if (!action.equals("Cancel")) {
			if (!bindingResult.hasErrors()) {
				System.out.println(productInventory.getProduct().getName());
				productInventoryServiceImp.saveProductInventory(productInventory);
			} else {
				ret = "/inventoryproduct/add";				
			}
		}
		return ret;
	}

	@GetMapping("/inventoryproduct/edit/{id}")
	public String editInventoryScreen(@Valid @ModelAttribute Productinventory productInventory,
			BindingResult bindingresult, @PathVariable("id") Integer id, Model model) {

		Productinventory pInventory = productInventoryRepository.findById(id).get();

		model.addAttribute("inventoryproduct", pInventory);
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("locations", locationRepository.findAll());
		return "/inventoryproduct/edit";
	}

	@PostMapping("/inventoryproduct/edit/{id}")
	public String editInventory(@Valid @ModelAttribute Productinventory productInventory, BindingResult bindingResult,
			Model model, @PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action) {
		String dir = "redirect:/inventoryproduct/";

		if (!action.equals("Cancel")) {
			if (!bindingResult.hasErrors()) {
				productInventoryServiceImp.editProductInventory(productInventory, id);
			} else {
				dir = "/inventoryproduct/add";
			}
		}
		return dir;
	}

}
