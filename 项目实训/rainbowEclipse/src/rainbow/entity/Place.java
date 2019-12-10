package rainbow.entity;

public class Place {
	private int placeId;
	private String name;
	private String enName;
	private String img;
	private String country;
	private String province;
	private String city;
	private String description;
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Place [placeId=" + placeId + ", name=" + name + ", enName=" + enName + ", img=" + img + ", country="
				+ country + ", province=" + province + ", city=" + city + ", description=" + description + "]";
	}
	public Place(int placeId, String name, String enName, String img, String country, String province, String city,
			String description) {
		this.placeId = placeId;
		this.name = name;
		this.enName = enName;
		this.img = img;
		this.country = country;
		this.province = province;
		this.city = city;
		this.description = description;
	}
	
	

}
