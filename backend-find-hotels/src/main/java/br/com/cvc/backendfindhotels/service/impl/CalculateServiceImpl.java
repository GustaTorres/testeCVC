package br.com.cvc.backendfindhotels.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.cvc.backendfindhotels.client.HotelBrokerClient;
import br.com.cvc.backendfindhotels.client.model.HotelClientDto;
import br.com.cvc.backendfindhotels.client.model.PriceClientDto;
import br.com.cvc.backendfindhotels.client.model.RoomClientDto;
import br.com.cvc.backendfindhotels.model.CalculateTravelDto;
import br.com.cvc.backendfindhotels.model.PriceDetailDto;
import br.com.cvc.backendfindhotels.model.RoomDto;
import br.com.cvc.backendfindhotels.service.CalculatorService;

@Service
public class CalculateServiceImpl implements CalculatorService {

	@Autowired
	private HotelBrokerClient hotelBrokerClient;

	@Override
	public CalculateTravelDto calculateTravel(final Long cityCode, final Instant checkIn, final Instant checkout,
			final BigInteger amoutAdult, final BigInteger amoutChildren) {

		final List<HotelClientDto> hotelsByCityCode = hotelBrokerClient.getHotelsByCityCode(cityCode);

		final Instant init = Instant.now();

		final long days = ChronoUnit.DAYS.between(checkIn, checkout);

		final CalculateTravelDto calculateTravelDto = new CalculateTravelDto();

		final Optional<HotelClientDto> findAny = hotelsByCityCode.stream().findAny();

		findAny.ifPresent(hotelFound -> {
			calculateTravelDto.setCityName(hotelFound.getCityName());
			calculateTravelDto.setId(1L);
		});

		final List<List<HotelClientDto>> partition = Lists.partition(hotelsByCityCode, 1000);

		final List<CompletableFuture<List<RoomDto>>> completables = new ArrayList<>();

		for (final List<HotelClientDto> hotels : partition) {

			final CompletableFuture<List<RoomDto>> completableFuture = CompletableFuture.supplyAsync(() -> {
				return processCalc(hotels, days);
			});

			completables.add(completableFuture);

		}

		for (final CompletableFuture<List<RoomDto>> completableFuture : completables) {
			try {
				final List<RoomDto> list = completableFuture.get();

				calculateTravelDto.getRooms().addAll(list);

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		final Instant end = Instant.now();
		final long between = ChronoUnit.NANOS.between(init, end);

		System.out.println("tempo de resposta: " + between);

		return calculateTravelDto;
	}

	private List<RoomDto> processCalc(final List<HotelClientDto> hotelsByCityCode, final long days) {
		final List<RoomDto> roomsDto = new ArrayList<>();
		hotelsByCityCode.parallelStream().forEach(hotel -> {
			final List<RoomClientDto> rooms = hotel.getRooms();

			rooms.forEach(room -> {
				final RoomDto roomDto = new RoomDto();

				roomDto.setCategoryName(room.getCategoryName());
				roomDto.setRoomID(room.getRoomID());
				final PriceClientDto price = room.getPrice();

				final BigDecimal pricePerAdult = price.getAdult();
				final BigDecimal pricePerChild = price.getChild();

				final BigDecimal priceAdult = pricePerAdult.multiply(new BigDecimal(days));
				// .divide(new BigDecimal(0.7));

				final BigDecimal priceChild = pricePerChild.multiply(new BigDecimal(days));
				// .divide(new BigDecimal(0.7));

				final PriceDetailDto priceDetailDto = new PriceDetailDto();
				priceDetailDto.setPricePerDayAdult(room.getPrice().getAdult());
				priceDetailDto.setPricePerDayChild(room.getPrice().getChild());

				roomDto.setPriceDetailDto(priceDetailDto);

				final BigDecimal totalPrice = priceAdult.add(priceChild);
				roomDto.setTotalPrice(totalPrice);

				roomsDto.add(roomDto);
			});
		});
		return roomsDto;
	}

}
