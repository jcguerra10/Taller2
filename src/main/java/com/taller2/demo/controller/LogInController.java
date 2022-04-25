package com.taller2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.taller2.demo.model.prod.UserApp;


@Controller
public class LogInController {

	@GetMapping("/login")
	public String login(UserApp ua) {
		
		return "login";
	}
	
	/*
	@PostMapping("/login")
	public String postLogin(UserApp ua) {
		System.out.println(">>>>>>>>");
		return "redirect:index";
	}
	*/
}
