package br.com.cvc.backendfindhotels.service;

import java.math.BigInteger;
import java.time.Instant;

import br.com.cvc.backendfindhotels.model.CalculateTravelDto;

public interface CalculatorService {

	CalculateTravelDto calculateTravel(Long cityCode, Instant checkIn, Instant checkout, BigInteger amoutAdult,
			BigInteger amoutChildren);

}
