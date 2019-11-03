package br.com.cvc.backendfindhotels.exception.model;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.http.HttpStatus;

public class CustomErrorResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7948364865068285466L;

	private Instant timestamp;
	private String message;
	private HttpStatus httpStatus;
	private Throwable throwable;

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(final HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(final Throwable throwable) {
		this.throwable = throwable;
	}
}
