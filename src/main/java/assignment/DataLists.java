package assignment;
import java.io.FileNotFoundException;
import java.util.*;

class DataLists {
	private List<Item> itemList;
	private List<Member> memberList;
	private List<DeliveryRate> deliveryList;
	private List<Order> orderList;
	private List<Payment> paymentList;
	private static FileUtilise file;
	
	public DataLists() throws FileNotFoundException {
		file = new FileUtilise();
		itemList = new ArrayList<>(file.ReadItemFile());
		deliveryList = new ArrayList<>(file.ReadDeliveryFile());
		memberList = new ArrayList<>(file.ReadMemberFile());
		orderList = new ArrayList<>();
		paymentList = new ArrayList<>();
	}
	
	//Item List
	public void AddItem(Item item) {
		itemList.add(item);
	}
	
	public Item[] GetItemList() {
		return itemList.toArray(new Item[0]);
	}
	
	public int GetItemListCount() {
		return itemList.size();
	}
	
	
	//Member List
	public void AddMember(Member member) {
		memberList.add(member);
	}
	
	public Member FindMember(String username, String password) {
		for (Member m : memberList) {
			if (username.equals(m.getUsername()) && password.equals(m.getPassword())) {
				return m;
			}
		}
		return null;
	}
	
	
	public boolean CheckUsername(String username) {
		for (Member m: memberList) {
			if (username.equalsIgnoreCase(m.getUsername()))
				return true;
		}
		return false;
	}
	
	public Member[] GetMemberList() {
		return memberList.toArray(new Member[0]);
	}
	
	//Delivery Rates List
	public void AddDelivery(DeliveryRate delivery) {
		deliveryList.add(delivery);
	}
	
	public DeliveryRate[] GetDeliveryList() {
		return deliveryList.toArray(new DeliveryRate[0]);
	}
	
	public int GetDeliveryListCount() {
		return deliveryList.size();
	}
	
	public String[] GetDeliveryDistrict() {
		HashSet<String> district = new HashSet<>();
		for (DeliveryRate d: deliveryList) {
			district.add(d.getDistrict());
		}
		return  district.toArray(new String[0]);
	}
	
	public String[] GetAreaFromDistrict(String selectedDistrict) {
		ArrayList<String> area = new ArrayList<>();
		for(DeliveryRate d: deliveryList) {
			if (selectedDistrict.equals(d.getDistrict())) {
				area.add(d.getArea());
			}
		}
		return area.toArray(new String[0]);
	}
	
	//Order List
	public void AddOrder(Order order) {
		orderList.add(order);
	}
	
	public Order TrackOrder(int orderId) {
		Order selectedOrder = null;
		for (Order order: orderList) {
			if (orderId == order.getId()) 
				selectedOrder = new Order(order);
		}
		if (selectedOrder == null)
			throw new NullPointerException("\nInvalid Order ID.\nPlease enter '0' to back or enter valid Order ID.\n\n");
		return selectedOrder;
	}
	
	//Payment List
	public void AddPayment(Payment payment) {
		paymentList.add(payment);
	}
	
	
}
