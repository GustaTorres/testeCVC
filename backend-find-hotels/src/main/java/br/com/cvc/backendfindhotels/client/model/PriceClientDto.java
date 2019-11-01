package br.com.cvc.backendfindhotels.client.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceClientDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3941729912881726531L;

	private BigDecimal adult;
	private BigDecimal child;

	public BigDecimal getAdult() {
		return adult;
	}

	public void setAdult(final BigDecimal adult) {
		this.adult = adult;
	}

	public BigDecimal getChild() {
		return child;
	}

	public void setChild(final BigDecimal child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "PriceClientDto [adult=" + adult + ", child=" + child + "]";
	}
}
