package assignment;
import java.util.*;

class Order {
	private int id;
	public int getId() {
		return id;
	}
	
	private Guest guest;
	public Guest getGuest() {
		return guest;
	}
	
	private double deliveryCharge;
	public double getDeliveryCharge() {
		return deliveryCharge;
	}
	
	private String status;
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	private Payment payment;
	public Payment getPayment() {
		return new Payment(payment);
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
	private Map<Item, Integer> orderItemList;
	
	public Order() {
		orderItemList = new HashMap<>();
	}
	
	public Order(Payment payment) {
		this.payment = payment;
	}
	
	public Order(Order order) {
			this.id = order.getId();
			this.guest = order.getGuest();
			this.deliveryCharge = order.getDeliveryCharge();
			this.orderItemList = new HashMap<Item, Integer>(order.GetOrderItemList());
			this.payment = order.getPayment();
			this.status = order.getStatus();
	}
	

	public Order(int id){
		this.id = id;
		orderItemList = new HashMap<>();
	}
	
	public void ComputeDeliveryCharge(DeliveryRate[] deliveryList) throws DeliveryNotAvailableException {
		boolean validArea = false;
		for (int i = 0; i < deliveryList.length; i++) {
			if (deliveryList[i].getArea().equals(guest.getAddress().getArea())) {
				this.deliveryCharge = deliveryList[i].getCharges();
				validArea = true;
			}
		}
		
		if (!validArea) {
			throw new DeliveryNotAvailableException();
		}
	}
	
	public void GetMemberDetails(String username, String password, DataLists dataLists) {
		this.guest = dataLists.FindMember(username, password);
		
		if (this.guest == null)
			throw new NullPointerException("\nInvalid username or password.");
	}
	
	public void GetGuestDetails(String name, Address address) {
		this.guest = new Guest(name,address);
	}

	public void AddItem(Item item, int quantity) {
		if (quantity < 1) {
			throw new IllegalArgumentException("\nInvalid Input! Quantity cannot be less than 1.");
		}
		else if (item == null)
			throw new IllegalArgumentException("\nError! Item selected is selected.");
		
		if (orderItemList.containsKey(item)) {
			int oldQuantity = orderItemList.get(item);
			orderItemList.replace(item, oldQuantity + quantity);
		}
		else
			orderItemList.put(item, quantity);
	}
	
	public void RemoveItem(Item item) {
		if (item == null)
			throw new IllegalArgumentException("\nThere is no item selected.");
		orderItemList.remove(item);
	}
	
	public Map<Item, Integer> GetOrderItemList(){
		return new HashMap<Item, Integer>(orderItemList);
	}
	
	public double ComputeSubtotalPrice() {
		//Calculate the total price of order with only the original member/non member price
		if (orderItemList.size() <= 0)
			throw new IllegalArgumentException("\nThere is no item in this order.");
		
		Iterator<Map.Entry<Item, Integer>> iterator = orderItemList.entrySet().iterator();
		double subTotalPrice = 0;
		while(iterator.hasNext()) {
			Map.Entry<Item, Integer> orderItem = iterator.next();
			Item item = orderItem.getKey();
			if (guest instanceof Member) {
				subTotalPrice += item.getMemberPrice() * orderItem.getValue();
			}
			else {
				subTotalPrice += item.getNonMemberPrice() * orderItem.getValue();
			}
		}
		return subTotalPrice;
	}
	
	public double ComputeTotalDiscount() {
		if (orderItemList.size() <= 0)
			throw new IllegalArgumentException("\nThere is no item in this order.");

		Iterator<Map.Entry<Item, Integer>> iterator = orderItemList.entrySet().iterator();
		double totalDiscount = 0;
		while(iterator.hasNext()) {
			Map.Entry<Item, Integer> orderItem = iterator.next();
			Item item = orderItem.getKey();
			if (item.getPromotional()) {
				if (guest instanceof Member) {
					totalDiscount += item.getMemberPrice() * 0.05 * orderItem.getValue();
				}
				else {
					totalDiscount += item.getNonMemberPrice() * 0.05 * orderItem.getValue();
				}
			}
		}
		return totalDiscount;
	}
	
	public double ComputeAdditionalCharge() {
		if (ComputeSubtotalPrice() - ComputeTotalDiscount() < 25)
			return 3;
		else 
			return 0;
	}
	
	public double ComputeTotalPrice() {
		return ComputeSubtotalPrice() + ComputeAdditionalCharge() + deliveryCharge - ComputeTotalDiscount();
	}
	
	public void MakePayment() {
		if (payment.Pay())
			this.status = "Paid & Ready for Delivery";
		else
			this.status = "Pending for Payment";
	}
}
