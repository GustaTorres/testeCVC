package br.com.cvc.backendfindhotels.exception;

public class InfrastructureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4297085924211711618L;

	public InfrastructureException(final String message) {
		super(message);
	}

	public InfrastructureException(final Throwable throwable) {
		super(throwable);
	}

	public InfrastructureException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
