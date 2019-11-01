package br.com.cvc.backendfindhotels.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2038488238729399524L;

	private BigDecimal pricePerDayAdult;
	private BigDecimal pricePerDayChild;

	public BigDecimal getPricePerDayAdult() {
		return pricePerDayAdult;
	}

	public void setPricePerDayAdult(final BigDecimal pricePerDayAdult) {
		this.pricePerDayAdult = pricePerDayAdult;
	}

	public BigDecimal getPricePerDayChild() {
		return pricePerDayChild;
	}

	public void setPricePerDayChild(final BigDecimal pricePerDayChild) {
		this.pricePerDayChild = pricePerDayChild;
	}
}
