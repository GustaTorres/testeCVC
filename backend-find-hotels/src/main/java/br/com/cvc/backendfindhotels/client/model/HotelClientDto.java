package br.com.cvc.backendfindhotels.client.model;

import java.io.Serializable;
import java.util.List;

public class HotelClientDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1428132985032682183L;

	private Long id;
	private String name;
	private Long cityCode;
	private String cityName;
	private List<RoomClientDto> rooms;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Long getCityCode() {
		return cityCode;
	}

	public void setCityCode(final Long cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public List<RoomClientDto> getRooms() {
		return rooms;
	}

	public void setRooms(final List<RoomClientDto> rooms) {
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
		final HotelClientDto other = (HotelClientDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HotelClientDto [id=" + id + ", name=" + name + ", cityCode=" + cityCode + ", cityName=" + cityName
				+ ", rooms=" + rooms + "]";
	}
}
