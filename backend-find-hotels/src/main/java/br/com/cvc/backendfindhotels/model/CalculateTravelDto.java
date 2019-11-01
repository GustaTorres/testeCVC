package br.com.cvc.backendfindhotels.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalculateTravelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -427481459248575585L;
	/**
	 * 
	 */

	private Long id;
	private String cityName;
	private List<RoomDto> rooms = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public List<RoomDto> getRooms() {
		return rooms;
	}

	public void setRooms(final List<RoomDto> rooms) {
		this.rooms = rooms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final CalculateTravelDto other = (CalculateTravelDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CalculateTravelDto [id=" + id + ", cityName=" + cityName + ", rooms=" + rooms + "]";
	}
}
