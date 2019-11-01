package br.com.cvc.backendfindhotels.client.model;

import java.io.Serializable;

public class RoomClientDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5586539590262962479L;

	private Long roomID;
	private String categoryName;
	private PriceClientDto price;

	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(final Long roomID) {
		this.roomID = roomID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	public PriceClientDto getPrice() {
		return price;
	}

	public void setPrice(final PriceClientDto price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		final RoomClientDto other = (RoomClientDto) obj;
		if (roomID == null) {
			if (other.roomID != null)
				return false;
		} else if (!roomID.equals(other.roomID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoomClientDto [roomID=" + roomID + ", categoryName=" + categoryName + ", price=" + price + "]";
	}
}
