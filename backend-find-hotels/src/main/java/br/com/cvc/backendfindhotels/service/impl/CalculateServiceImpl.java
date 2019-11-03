package br.com.cvc.backendfindhotels.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import br.com.cvc.backendfindhotels.client.HotelBrokerClient;
import br.com.cvc.backendfindhotels.client.model.HotelClientDto;
import br.com.cvc.backendfindhotels.client.model.PriceClientDto;
import br.com.cvc.backendfindhotels.client.model.RoomClientDto;
import br.com.cvc.backendfindhotels.exception.UnexpectedException;
import br.com.cvc.backendfindhotels.model.CalculateTravelDto;
import br.com.cvc.backendfindhotels.model.HotelDto;
import br.com.cvc.backendfindhotels.model.PriceDetailDto;
import br.com.cvc.backendfindhotels.model.RoomDto;
import br.com.cvc.backendfindhotels.service.CalculatorService;

@Service
public class CalculateServiceImpl implements CalculatorService {

	@Autowired
	private HotelBrokerClient hotelBrokerClient;

	@Value("${process-parallel.number-threads}")
	private Integer threads;

	@Value("${comission}")
	private BigDecimal comission;

	@Override
	public CalculateTravelDto calculateTravelByCity(final Long cityCode, final Instant checkIn, final Instant checkout,
			final BigInteger amoutAdult, final BigInteger amoutChildren) {
		final List<HotelClientDto> hotels = hotelBrokerClient.getHotelsByCityCode(cityCode);
		return calculateTravel(checkIn, checkout, hotels);

	}

	@Override
	public CalculateTravelDto calculateTravelByHotel(final Long hotelId, final Instant checkIn, final Instant checkout,
			final BigInteger amoutAdult, final BigInteger amoutChildren) {
		final List<HotelClientDto> hotels = hotelBrokerClient.getHotelById(hotelId);
		return calculateTravel(checkIn, checkout, hotels);
	}

	private CalculateTravelDto calculateTravel(final Instant checkIn, final Instant checkout,
			final List<HotelClientDto> hotelsByCityCode) {
		final CalculateTravelDto calculateTravelDto = new CalculateTravelDto();

		if (hotelsByCityCode == null || hotelsByCityCode.isEmpty()) {
			return calculateTravelDto;
		}

		final Optional<HotelClientDto> findAny = hotelsByCityCode.stream().findAny();
		findAny.ifPresent(hotelFound -> {
			calculateTravelDto.setCityName(hotelFound.getCityName());
			calculateTravelDto.setId(1L);
		});

		final List<List<HotelClientDto>> listHotelPart = partitionList(hotelsByCityCode);

		final long days = ChronoUnit.DAYS.between(checkIn, checkout);
		final List<CompletableFuture<List<RoomDto>>> completables = processCalcParallel(listHotelPart, days);

		final List<RoomDto> rooms = getProcessDone(completables);
		calculateTravelDto.getRooms().addAll(rooms);

		return calculateTravelDto;
	}

	private List<List<HotelClientDto>> partitionList(final List<HotelClientDto> hotelsByCityCode) {
		final int size = hotelsByCityCode.size();
		final int partition = new BigDecimal(size).divide(new BigDecimal(threads), RoundingMode.HALF_UP).intValue();
		return Lists.partition(hotelsByCityCode, partition);
	}

	private List<CompletableFuture<List<RoomDto>>> processCalcParallel(final List<List<HotelClientDto>> listHotelPart,
			final long days) {
		final ExecutorService executor = Executors.newFixedThreadPool(threads);

		final List<CompletableFuture<List<RoomDto>>> completables = new ArrayList<>();
		for (final List<HotelClientDto> hotels : listHotelPart) {

			final CompletableFuture<List<RoomDto>> completableFuture = new CompletableFuture<>();

			executor.submit(() -> {
				final List<RoomDto> rooms = processCalc(hotels, days);
				completableFuture.complete(rooms);
				return null;
			});

			completables.add(completableFuture);
		}
		return completables;
	}

	private List<RoomDto> getProcessDone(final List<CompletableFuture<List<RoomDto>>> completables) {
		final List<RoomDto> synchronizedList = new ArrayList<>();

		for (final CompletableFuture<List<RoomDto>> completableFuture : completables) {
			List<RoomDto> list;
			try {
				list = completableFuture.get();
				synchronizedList.addAll(list);
			} catch (InterruptedException | ExecutionException e) {
				completableFuture.completeExceptionally(e);
				Thread.currentThread().interrupt();
				throw new UnexpectedException(e);
			}
		}
		return synchronizedList;
	}

	private List<RoomDto> processCalc(final List<HotelClientDto> hotelsByCityCode, final long days) {

		final List<RoomDto> roomsDto = new ArrayList<>();

		hotelsByCityCode.forEach(hotel -> {
			final List<RoomClientDto> rooms = hotel.getRooms();

			rooms.forEach(room -> {

				final HotelDto hotelDto = new HotelDto();
				hotelDto.setId(hotel.getId());
				hotelDto.setName(hotel.getName());

				final RoomDto roomDto = new RoomDto();
				roomDto.setHotel(hotelDto);
				roomDto.setCategoryName(room.getCategoryName());
				roomDto.setRoomID(room.getRoomID());

				final PriceClientDto price = room.getPrice();

				final BigDecimal pricePerAdult = price.getAdult();
				final BigDecimal pricePerChild = price.getChild();

				final BigDecimal pricePerDayAdult = calcCommission(pricePerAdult);
				final BigDecimal pricePerDayChild = calcCommission(pricePerChild);

				final PriceDetailDto priceDetailDto = new PriceDetailDto();
				priceDetailDto.setPricePerDayAdult(pricePerDayAdult);
				priceDetailDto.setPricePerDayChild(pricePerDayChild);
				roomDto.setPriceDetail(priceDetailDto);

				final BigDecimal totalPrice = pricePerDayAdult.add(pricePerDayChild).multiply(BigDecimal.valueOf(days));
				roomDto.setTotalPrice(totalPrice);

				roomsDto.add(roomDto);
			});
		});
		return roomsDto;
	}

	private BigDecimal calcCommission(final BigDecimal price) {
		return price.divide(comission, RoundingMode.HALF_UP);
	}
}
