package com.taller2.demo.controller;

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
import com.taller2.demo.model.prod.Productcategory;
import com.taller2.demo.model.prod.Productsubcategory;
import com.taller2.demo.model.prod.UserApp;
import com.taller2.demo.repositories.ProductRepository;
import com.taller2.demo.repositories.ProductcategoryRepository;
import com.taller2.demo.repositories.ProductsubcategoryRepository;
import com.taller2.demo.services.ProductServiceImp;
import com.taller2.demo.services.ProductcategoryServiceImp;
import com.taller2.demo.services.ProductsubcategoryImp;

@Controller
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductServiceImp productServiceImp;

	@Autowired
	ProductcategoryRepository productcategoryRepository;
	@Autowired
	ProductcategoryServiceImp productcategoryServiceImp;

	@Autowired
	ProductsubcategoryRepository productsubcategoryRepository;
	@Autowired
	ProductsubcategoryImp productsubcategoryImp;

	@GetMapping("/products/")
	public String showProducts(Model model, UserApp ua) {
		model.addAttribute("products", productRepository.findAll());

		return "/products/index";
	}

	@GetMapping("/products/add/")
	public String productAddScreen(Model model) {
		model.addAttribute(new Product());
		model.addAttribute("subcategories", productsubcategoryRepository.findAll());
		return "/products/add";
	}

	@PostMapping("/products/add/")
	public String productAdd(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action, String startdate, String enddate) {

		String ret = "redirect:/products/";

		if (!action.equals("Cancel")) {
			if (!bindingResult.hasErrors()) {

				productServiceImp.saveProduct(product);

			} else {
				ret = "/products/add";
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

	// ----------------- CATEGORIES -----------------

	@GetMapping("/products/createcategories/")
	public String createCategoriesScreen(Model model) {

		return "/products/createcategories";
	}

	@PostMapping("/products/createcategories/")
	public String createCategories(Model model, String category, String subcategory) {

		Productcategory pc = new Productcategory();
		pc.setName(category);

		Productsubcategory psc = new Productsubcategory();
		psc.setName(subcategory);

		psc.setProductcategory(pc);

		productcategoryServiceImp.saveProductcategory(pc);
		productsubcategoryImp.saveProductsubcategory(psc);

		return "redirect:/products/add/";
	}

	// ----------------- EDIT -----------------

	@GetMapping("/products/edit/{id}")
	public String editProductScreen(@PathVariable("id") Integer id, Model model) {
		Optional<Product> find = productRepository.findById(id);
		if (find.isEmpty())
			throw new IllegalArgumentException("Invalid Id:" + id);
		model.addAttribute("product", find.get());
		return "/products/edit";
	}

	@PostMapping("/products/edit/{id}")
	public String editProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model,
			@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, String startdate, String enddate) {
		
		String dir = "redirect:/products/";

		if (!action.equals("Cancel")) {
			if (!bindingResult.hasErrors()) {

				productServiceImp.editProduct(product, id);
				model.addAttribute("products", productRepository.findAll());
			} else {
				model.addAttribute("product", product);
				dir = "products/edit";
			}
		}
		return dir;
	}

}
