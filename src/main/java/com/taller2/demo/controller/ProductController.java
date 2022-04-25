package com.taller2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.taller2.demo.model.prod.UserApp;

@Controller
public class ProductController {
	@GetMapping("/products/")
	public String products(UserApp ua) {
		
		return "/products/index";
	}
}
