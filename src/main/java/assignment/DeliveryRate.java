package assignment;
class DeliveryRate {
	private String district;
	public String getDistrict() {
		return district;
	}
	
	private String area;
	public String getArea() {
		return area;
	}
	
	private double charges;
	public double getCharges() {
		return charges;
	}
	
	public DeliveryRate(String district, String area, double charges) {
		this.district = district;
		this.area = area;
		this.charges = charges;
	}
}
