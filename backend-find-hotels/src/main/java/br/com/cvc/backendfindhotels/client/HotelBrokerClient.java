package br.com.cvc.backendfindhotels.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.cvc.backendfindhotels.client.model.HotelClientDto;

@FeignClient(url = "https://cvcbackendhotel.herokuapp.com/hotels/", name = "hotelBroker")
public interface HotelBrokerClient {

	@GetMapping("avail/{cityCode}")
	public List<HotelClientDto> getHotelsByCityCode(@PathVariable("cityCode") Long cityCode);

	@GetMapping("{idHotel}")
	public List<HotelClientDto> getHotelById(@PathVariable("idHotel") Long idHotel);

}
