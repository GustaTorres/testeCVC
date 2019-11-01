package br.com.cvc.backendfindhotels.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/calculator-travel")
public class CalculatorController {

	@GetMapping
	public String calcularTravel() {
		return "OK";
	}

}
