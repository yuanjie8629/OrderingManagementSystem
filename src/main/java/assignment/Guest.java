package assignment;
class Guest{
	private String name;
	public String getName() {
		return name;
	}
	
	private Address address;
	public Address getAddress() {
		return address;
	}
	
	public Guest(String name, Address address) {
		this.name = name;
		this.address = address;
	}
}
