package br.com.cvc.backendfindhotels.controller;

import java.math.BigInteger;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.backendfindhotels.model.CalculateTravelDto;
import br.com.cvc.backendfindhotels.service.CalculatorService;

@RestController
@RequestMapping("api/v1/calculator-travel")
public class CalculatorController {

	@Autowired
	private CalculatorService calculatorService;

	@GetMapping
	public ResponseEntity<CalculateTravelDto> calcularTravel(@RequestParam("cityCode") final Long cityCode,
			@RequestParam("checkIn") final Instant checkIn, @RequestParam("checkOut") final Instant checkout,
			@RequestParam("amoutAdults") final BigInteger amoutAdult,
			@RequestParam("amoutChildren") final BigInteger amoutChildren) {

		final CalculateTravelDto calculateTravelDto = calculatorService.calculateTravel(cityCode, checkIn, checkout,
				amoutAdult, amoutChildren);

		return ResponseEntity.ok(calculateTravelDto);
	}

}
