package com.taller2.demo.controller;

import com.taller2.demo.model.prod.Productsubcategory;
import com.taller2.demo.repositories.ProductcategoryRepository;
import com.taller2.demo.repositories.ProductsubcategoryRepository;
import com.taller2.demo.services.ProductcategoryServiceImp;
import com.taller2.demo.services.ProductsubcategoryImp;
import com.taller2.demo.services.interfaces.ProductsubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @Autowired
    private ProductcategoryServiceImp productcategoryService;

    @Autowired
    private ProductcategoryRepository productcategoryRepository;

    @Autowired
    private ProductsubcategoryImp productsubcategoryService;

    @Autowired
    private ProductsubcategoryRepository productsubcategoryRepository;

    @GetMapping("/products/categories/")
    private String showCategories(Model model) {
        model.addAttribute("categories", productcategoryRepository.findAll());
        model.addAttribute("subcategories", productsubcategoryRepository.findAll());
        return "/categories/index";
    }

}
