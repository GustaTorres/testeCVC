package br.com.cvc.backendfindhotels.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoomDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3868778135057863515L;

	private Long roomID;
	private HotelDto hotel;
	private String categoryName;
	private BigDecimal totalPrice;
	private PriceDetailDto priceDetail;

	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(final Long roomID) {
		this.roomID = roomID;
	}

	public HotelDto getHotel() {
		return hotel;
	}

	public void setHotel(final HotelDto hotel) {
		this.hotel = hotel;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(final BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public PriceDetailDto getPriceDetail() {
		return priceDetail;
	}

	public void setPriceDetail(final PriceDetailDto priceDetail) {
		this.priceDetail = priceDetail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
		result = prime * result + ((roomID == null) ? 0 : roomID.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RoomDto other = (RoomDto) obj;
		if (hotel == null) {
			if (other.hotel != null)
				return false;
		} else if (!hotel.equals(other.hotel))
			return false;
		if (roomID == null) {
			if (other.roomID != null)
				return false;
		} else if (!roomID.equals(other.roomID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoomDto [roomID=" + roomID + ", hotel=" + hotel + ", categoryName=" + categoryName + ", totalPrice="
				+ totalPrice + ", priceDetail=" + priceDetail + "]";
	}
}
