package br.com.cvc.backendfindhotels.exception;

public class UnexpectedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1261710746747805383L;

	public UnexpectedException(final String message) {
		super(message);
	}

	public UnexpectedException(final Throwable throwable) {
		super(throwable);
	}

	public UnexpectedException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
