package br.com.cvc.backendfindhotels.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.cvc.backendfindhotels.client.HotelBrokerClient;
import br.com.cvc.backendfindhotels.client.model.HotelClientDto;
import br.com.cvc.backendfindhotels.exception.InfrastructureException;
import feign.hystrix.FallbackFactory;

@Component
public class HotelBrokerClientFallback implements FallbackFactory<HotelBrokerClient> {

	@Override
	public HotelBrokerClient create(final Throwable cause) {

		return new HotelBrokerClient() {

			@Override
			public List<HotelClientDto> getHotelsByCityCode(final Long cityCode) {
				final String error = String.format("unable to list hotels by city code: %s", cityCode);
				throw new InfrastructureException(error, cause);
			}

			@Override
			public List<HotelClientDto> getHotelById(final Long hotelId) {
				final String error = String.format("unable to get hotel by id: %s", hotelId);
				throw new InfrastructureException(error, cause);
			}
		};
	}

}
