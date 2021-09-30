package assignment;
class Address {	
	private String state;
	public String getState(){
		return state;
	}
	
	private String district;
	public String getDistrict(){
		return district;
	}
	//No validation because user selects the district from the district list
	public void setDistrict(String district) {
		this.district = district;
	}
	
	private String area;
	public String getArea(){
		return area;
	}
	//No validation because user selects the area from the area list
	public void setArea(String area) {
		this.area = area;
	}

	
	private int postalCode;
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) throws IllegalArgumentException{
		if (postalCode >= 100000 || postalCode <10000) 
			throw new IllegalArgumentException("Invalid Input! Postal Code should contain 5 digits.");
		else 
			this.postalCode = postalCode;
	}
	
	private String street;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) throws IllegalArgumentException {
		if (street == null || street.trim().isEmpty())
			throw new IllegalArgumentException("\nPlease enter address's street.\n");
		else if (!street.matches("^[a-zA-Z\\s]*(.[0-9])?$"))
			throw new IllegalArgumentException("Invalid Input! Address's street should contain only alphabets or number at the end for street number.\n");
		this.street = street;
	}
	
	private int unitNum;
	public int getUnitNum() {
		return unitNum;
	}
	
	public void setUnitNum(int unitNum) {
		this.unitNum = unitNum;
	}

	public Address(String state) throws IllegalArgumentException, DeliveryNotAvailableException {
		if (state == null || state.trim().isEmpty())
			throw new IllegalArgumentException("\nPlease enter address's state.\n");
		else if (!state.matches("^[a-zA-Z\\s]*$"))
			throw new IllegalArgumentException("Invalid Input! Address's state should contain only alphabets.\n");
		else if (!state.equalsIgnoreCase("Melaka"))
			throw new DeliveryNotAvailableException();
		else 
			this.state = state;
	}
	
	/*For input from file
	 Assumption: All addresses stored inside the file are correct and within Melaka, no validation needed.*/
	public Address(String state, String district, String area, int postalCode, String street, int unitNum){
		this.state = state;
		this.district = district;
		this.area = area;
		this.postalCode = postalCode;
		this.street = street;
		this.unitNum = unitNum;
	}
}