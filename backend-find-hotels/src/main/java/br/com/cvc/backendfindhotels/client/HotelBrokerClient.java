package br.com.cvc.backendfindhotels.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.cvc.backendfindhotels.client.fallback.HotelBrokerClientFallback;
import br.com.cvc.backendfindhotels.client.model.HotelClientDto;

@FeignClient(url = "${feign.client.hotelBroker.url}", name = "hotelBroker", fallbackFactory = HotelBrokerClientFallback.class)
public interface HotelBrokerClient {

	@GetMapping("avail/{cityCode}")
	public List<HotelClientDto> getHotelsByCityCode(@PathVariable("cityCode") Long cityCode);

	@GetMapping("{hotelId}")
	public List<HotelClientDto> getHotelById(@PathVariable("hotelId") Long hotelId);

}
