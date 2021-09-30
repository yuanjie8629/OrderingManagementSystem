package assignment;
class Member extends Guest{
	private String username;
	public String getUsername() {
		return username;
	}
	
	private String password;
	public String getPassword() {
		return password;
	}
	
	private String phoneNum;
	public String getPhoneNum() {
		return phoneNum;
	}
	
	public Member(String username, String password, String name, Address address, String phoneNum) {
		super(name, address);
		this.username = username;
		this.password = password;
		this.phoneNum = phoneNum;
	}
}
