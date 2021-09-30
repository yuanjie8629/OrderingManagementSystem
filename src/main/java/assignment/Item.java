package assignment;
class Item {
	private int id;
	public int getId() {
		return id;
	}
	
	private String name;
	public String getName(){
		return name;
	}
	
	private String type;
	public String getType() {
		return type;
	}
	
	private double memberPrice;
	public double getMemberPrice() {
		return memberPrice;
	}
	
	private double nonMemberPrice;
	public double getNonMemberPrice() {
		return nonMemberPrice;
	}
	
	private boolean promotional;
	public boolean getPromotional() {
		return promotional;
	}
	
	public Item(int id, String name, String type, double memberPrice, double nonMemberPrice, boolean promotional) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.memberPrice = memberPrice;
		this.nonMemberPrice = nonMemberPrice;
		this.promotional = promotional;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			Item compareItem = (Item) obj;
			if (id == compareItem.getId() && name.equals(compareItem.getName()) 
					&& type.equals(compareItem.getType()) && memberPrice == compareItem.getMemberPrice()
					&& nonMemberPrice == compareItem.getNonMemberPrice() 
					&& promotional == compareItem.getPromotional()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
    public int hashCode() {
        return this.id;
    }
}

