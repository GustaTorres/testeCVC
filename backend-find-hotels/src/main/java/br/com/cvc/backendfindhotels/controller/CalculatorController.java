package br.com.cvc.backendfindhotels.controller;

import java.math.BigInteger;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/city/{cityCode}")
	public ResponseEntity<CalculateTravelDto> calcularTravelByCity(
			@PathVariable final Long cityCode,
			@RequestParam("checkIn") final Instant checkIn, 
			@RequestParam("checkOut") final Instant checkout,
			@RequestParam("amoutAdults") final BigInteger amoutAdult,
			@RequestParam("amoutChildren") final BigInteger amoutChildren) {

		final CalculateTravelDto calculateTravelDto = calculatorService.calculateTravelByCity(cityCode, checkIn,
				checkout, amoutAdult, amoutChildren);

		return ResponseEntity.ok(calculateTravelDto);
	}
	
	@GetMapping("/hotel/{hotelId}")
	public ResponseEntity<CalculateTravelDto> calcularTravelByHotel(
			@PathVariable final Long hotelId,
			@RequestParam("checkIn") final Instant checkIn, 
			@RequestParam("checkOut") final Instant checkout,
			@RequestParam("amoutAdults") final BigInteger amoutAdult,
			@RequestParam("amoutChildren") final BigInteger amoutChildren) {

		final CalculateTravelDto calculateTravelDto = calculatorService.calculateTravelByHotel(hotelId, checkIn,
				checkout, amoutAdult, amoutChildren);

		return ResponseEntity.ok(calculateTravelDto);
	}

}
