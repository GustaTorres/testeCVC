package br.com.cvc.backendfindhotels.exception;

import java.time.Instant;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cvc.backendfindhotels.exception.model.CustomErrorResponse;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler({ InfrastructureException.class })
	@ResponseBody
	public ResponseEntity<CustomErrorResponse> infrastructureException(final Exception ex) {
		final CustomErrorResponse customErrorResponse = buildError(ex, HttpStatus.SERVICE_UNAVAILABLE);
		return new ResponseEntity<>(customErrorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler({ TimeoutException.class })
	@ResponseBody
	public ResponseEntity<CustomErrorResponse> timeOutException(final Exception ex) {
		final CustomErrorResponse customErrorResponse = buildError(ex, HttpStatus.GATEWAY_TIMEOUT);
		return new ResponseEntity<>(customErrorResponse, HttpStatus.GATEWAY_TIMEOUT);
	}

	@ExceptionHandler({ UnexpectedException.class })
	@ResponseBody
	public ResponseEntity<CustomErrorResponse> genericException(final Exception ex) {
		final CustomErrorResponse customErrorResponse = buildError(ex, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
	}

	private CustomErrorResponse buildError(final Exception ex, final HttpStatus httpStatus) {
		final CustomErrorResponse customErrorResponse = new CustomErrorResponse();
		customErrorResponse.setTimestamp(Instant.now());
		customErrorResponse.setMessage(ex.getMessage());
		customErrorResponse.setThrowable(ex);
		customErrorResponse.setHttpStatus(httpStatus);
		return customErrorResponse;
	}

}
