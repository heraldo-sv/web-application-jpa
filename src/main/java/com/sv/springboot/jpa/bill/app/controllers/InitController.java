package com.sv.springboot.jpa.bill.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitController {

	@GetMapping("/")
	public String init(Model model) {
		model.addAttribute("titulo","BillingsApp");
		model.addAttribute("paginaActual","init");
		return "init";
	}
}
