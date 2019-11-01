package br.com.cvc.backendfindhotels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackendFindHotelsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(BackendFindHotelsApplication.class, args);
	}

}
