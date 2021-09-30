package assignment;
import java.util.*;
import java.io.*;

public class OrderingSystemUI {
	private static Scanner input;
	private static DataLists dataLists;
	private static Order order;
	private static int orderIdCount = 1;
	private static int paymentIdCount = 1;
	
	public void start() {
		try {
			dataLists = new DataLists();
			HardCodeData();
		} catch (FileNotFoundException e) {
			System.out.println("File Error: Some File(s) does not exists.");
			System.exit(0);
		} catch (DeliveryNotAvailableException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		boolean continueFlag = false;
		do {
			input = new Scanner(System.in);
			continueFlag = LoginPage();
			if (continueFlag) {
				try {
					continueFlag = OrderingPage();
				} catch (DeliveryNotAvailableException e) {
					System.out.println(e.getMessage());
				}
			}
		}while(!continueFlag);
		
	}

	public boolean LoginPage() {
		
		System.out.println("-----------------------------------");
		System.out.println("Online Ordering and Delivery System");
		System.out.println("-----------------------------------");
		System.out.println("1. Continue as Guest");
		System.out.println("2. Log in as Member");
		System.out.println("3. Register as Member");
		System.out.println("4. Track order status");
		System.out.println("5. Exit");
		System.out.println("-----------------------------------");
		
		boolean optionRepeat = true;
		do {
			try {
				System.out.print("Option: ");
				int option;
				option = Integer.parseInt(input.nextLine());
				optionRepeat = false;
				switch (option) {
					case 1: 
						return GuestPage();
					case 2: 
						return MemberPage();
					case 3:
						return RegisterPage();
					case 4:
						TrackingPage();
						return false;
					case 5:
						System.out.println("\n\nExited Program.");
						System.exit(0);
					default:
						System.out.println("Invalid Input! Value should be ranged from '1' to '4' only.\n");
						optionRepeat = true;
						break;
				}
			}
			catch (NumberFormatException e){
				System.out.println("Invalid Input! Value should contain only number.\n");
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}while(optionRepeat);
		return true;
		
	}
	
	public boolean GuestPage() {
		String name = null, state = null, district = null, area = null, street = null;
		int postalCode = 0, unitNum = 0;
		boolean repeatFlag = true;
		System.out.println("\n\n-----------------------------------");
		System.out.println("Continue As Guest");
		System.out.println("-----------------------------------");
		
		do {
			try {
				System.out.println("Name: ");
				name = input.nextLine();
				if (name.trim().isEmpty() || name == null)
					throw new IllegalArgumentException("Please enter your name.\n");
				else if (!name.matches("^[a-zA-Z\\s]*$"))
					throw new IllegalArgumentException("Invalid Input! Name should contain only alphabets.\n");
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e){
				System.out.println("Exception found: " + e.getMessage());
			}
		}while (repeatFlag);
		
		
		System.out.println("\nAddress");
		System.out.println("-------------------");
		Address address = null;
		repeatFlag = true;
		do {
			try {
				System.out.print("State: ");
				state = input.nextLine();
				if (state.equals("0"))
					return false;
				address = new Address(state);
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (DeliveryNotAvailableException e) {
				System.out.println(e.getMessage());
				System.out.println("Please enter '0' to back or enter 'Melaka' to continue for ordering within Melaka.\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(repeatFlag);
		
		repeatFlag = true;
		System.out.println("\nDistrict");
		System.out.println("--------------");
		String[] districtList = dataLists.GetDeliveryDistrict();
		for (int i = 0; i < districtList.length; i++) {
			System.out.println(i+1 + ". " + districtList[i]);
		}
		System.out.println("--------------");
		do {
			try {
				System.out.print("Option: ");
				int option = Integer.parseInt(input.nextLine());
				if (option <= 0 || option > districtList.length) {
					System.out.println("\nInvalid Input! Option should be ranged from '1' to '" 
							+ districtList.length + "' only.\n");
				}
				else {
					for (int i = 0; i< districtList.length; i++) {
						if (option == i + 1) {
							address.setDistrict(districtList[i]);
						}
					}
					repeatFlag = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid Input! Option should contain only number.\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(repeatFlag);
		
		repeatFlag = true;
		System.out.println("\nArea");
		System.out.println("--------------");
		String[] areaList = dataLists.GetAreaFromDistrict(address.getDistrict());
		for (int i = 0; i < areaList.length; i++) {
			System.out.println(i+1 + ". " + areaList[i]);
		}
		System.out.println("--------------");
		do {
			try {
				System.out.print("Option: ");
				int option = Integer.parseInt(input.nextLine());
				if (option <= 0 || option > areaList.length) {
					System.out.println("\nInvalid Input! Option should be ranged from '1' to '" 
							+ areaList.length + "' only.\n");
				}
				else {
					for (int i = 0; i< areaList.length; i++) {
						if (option == i + 1) {
							address.setArea(areaList[i]);
						}
					}
					repeatFlag = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid Input! Option should contain only number.\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(repeatFlag);
		
		repeatFlag = true;
		do {
			try {
				System.out.print("Postal Code: ");
				address.setPostalCode(Integer.parseInt(input.nextLine()));
				repeatFlag = false;
			} catch (NumberFormatException e){
				System.out.println("Invalid Input! Postal Code should contain only number.\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while (repeatFlag);
		
		repeatFlag = true;
		do {
			try {
				System.out.print("Street: ");
				address.setStreet(input.nextLine());
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while (repeatFlag);
		
		repeatFlag = true;
		do {
			try {
				System.out.print("Unit Number: ");
				address.setUnitNum(Integer.parseInt(input.nextLine()));
				repeatFlag = false;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input! Unit Number should contain only digits.\n");
			}
		}while (repeatFlag);
		order = new Order(orderIdCount++);
		order.GetGuestDetails(name, address);
		return true;
	}
	
	
	public boolean MemberPage() {
		String username = null, password = null;
		boolean repeatFlag = true, invalidFlag = true;
		System.out.println("\n\n-----------------------------------");
		System.out.println("Login as Member");
		System.out.println("-----------------------------------");
		
		do {
			do {
				try {
					System.out.print("Username: ");
					username = input.nextLine();
					if (username.trim().isEmpty() || username == null)
						throw new IllegalArgumentException("Please enter username.\n");
					else if (username.equals("0"))
						return false;
					repeatFlag = false;
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while (repeatFlag);
			
			repeatFlag = true;
			
			System.out.print("Password: ");
			do {
				try {
					password = input.nextLine();
					if (password.trim().isEmpty() || password == null)
						throw new IllegalArgumentException("Please enter password.\n");
					else if (password.equals("0"))
						return false;
					repeatFlag = false;
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while (repeatFlag);
			
			try{
				order = new Order(orderIdCount++);
				order.GetMemberDetails(username, password, dataLists);
				invalidFlag = false;
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
				System.out.println("Press '0' to back or login with correct username and password.\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while (invalidFlag);
		return true;
	}
	
	public boolean RegisterPage() {
		String username = null, password = null, name = null, state = null, 
				district = null, area = null, street = null, phoneNum = null;
		int postalCode = 0, unitNum = 0;
		boolean repeatFlag = true;
		System.out.println("\n\n-----------------------------------");
		System.out.println("Register as Member");
		System.out.println("-----------------------------------");
		do {
			try {
				System.out.println("Username: ");
				username = input.nextLine();
				if (username.trim().isEmpty() || username == null)
					throw new IllegalArgumentException("Please enter username.\n");
				if (dataLists.CheckUsername(username))
					throw new IllegalArgumentException("Username has been taken, try another one.\n");
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e){
				System.out.println("Exception found: " + e.getMessage());
			}
		}while (repeatFlag);
		
		repeatFlag = true;
		
		do {
			try {
				System.out.println("Password: ");
				password = input.nextLine();
				if (password.trim().isEmpty() || password == null)
					throw new IllegalArgumentException("Please enter password.\n");
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e){
				System.out.println("Exception found: " + e.getMessage());
			}
		}while (repeatFlag);
		
		repeatFlag = true;
		
		do {
			try {
				System.out.println("Name: ");
				name = input.nextLine();
				if (name.trim().isEmpty() || name == null)
					throw new IllegalArgumentException("Please enter your name.\n");
				else if (!name.matches("^[a-zA-Z\\s]*$"))
					throw new IllegalArgumentException("Invalid Input! Name should contain only alphabets.\n");
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e){
				System.out.println("Exception found: " + e.getMessage());
			}
		}while (repeatFlag);
		
		repeatFlag = true;
		
		do {
			try {
				System.out.println("Phone Number: ");
				phoneNum = input.nextLine();
				if (phoneNum.length() < 10 || phoneNum.length() > 11)
					throw new IllegalArgumentException("Phone Number should have 10 - 11 digits only.\n");
				repeatFlag = false;
				
			} catch (NumberFormatException e) {
				System.out.print("Phone Number should have 10 - 11 digits only.\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e){
				System.out.println("Exception found: " + e.getMessage());
			}
		}while (repeatFlag);
		
		
		System.out.println("\nAddress");
		System.out.println("-------------------");
		Address address = null;
		repeatFlag = true;
		do {
			try {
				System.out.print("State: ");
				state = input.nextLine();
				if (state.equals("0"))
					return false;
				address = new Address(state);
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (DeliveryNotAvailableException e) {
				System.out.println(e.getMessage());
				System.out.println("Please enter '0' to back or enter 'Melaka' to continue for ordering within Melaka.\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(repeatFlag);
		
		repeatFlag = true;
		System.out.println("\nDistrict");
		System.out.println("--------------");
		String[] districtList = dataLists.GetDeliveryDistrict();
		for (int i = 0; i < districtList.length; i++) {
			System.out.println(i+1 + ". " + districtList[i]);
		}
		System.out.println("--------------");
		do {
			try {
				System.out.print("Option: ");
				int option = Integer.parseInt(input.nextLine());
				if (option <= 0 || option > districtList.length) {
					System.out.println("\nInvalid Input! Option should be ranged from '1' to '" 
							+ districtList.length + "' only.\n");
				}
				else {
					for (int i = 0; i< districtList.length; i++) {
						if (option == i + 1) {
							address.setDistrict(districtList[i]);
						}
					}
					repeatFlag = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid Input! Option should contain only number.\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(repeatFlag);
		
		repeatFlag = true;
		System.out.println("\nArea");
		System.out.println("--------------");
		String[] areaList = dataLists.GetAreaFromDistrict(address.getDistrict());
		for (int i = 0; i < areaList.length; i++) {
			System.out.println(i+1 + ". " + areaList[i]);
		}
		System.out.println("--------------");
		do {
			try {
				System.out.print("Option: ");
				int option = Integer.parseInt(input.nextLine());
				if (option <= 0 || option > areaList.length) {
					System.out.println("\nInvalid Input! Option should be ranged from '1' to '" 
							+ areaList.length + "' only.\n");
				}
				else {
					for (int i = 0; i< areaList.length; i++) {
						if (option == i + 1) {
							address.setArea(areaList[i]);
						}
					}
					repeatFlag = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid Input! Option should contain only number.\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while(repeatFlag);
		
		repeatFlag = true;
		do {
			try {
				System.out.print("Postal Code: ");
				address.setPostalCode(Integer.parseInt(input.nextLine()));
				repeatFlag = false;
			} catch (NumberFormatException e){
				System.out.println("Invalid Input! Postal Code should contain only number.\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while (repeatFlag);
		
		repeatFlag = true;
		do {
			try {
				System.out.print("Street: ");
				address.setStreet(input.nextLine());
				repeatFlag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while (repeatFlag);
		
		repeatFlag = true;
		do {
			try {
				System.out.print("Unit Number: ");
				address.setUnitNum(Integer.parseInt(input.nextLine()));
				repeatFlag = false;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input! Unit Number should contain only digits.\n");
			}
		}while (repeatFlag);
		Member member = new Member(username, password, name, address, phoneNum);
		dataLists.AddMember(member);
		order = new Order(orderIdCount++);
		order.GetMemberDetails(username, password, dataLists);
		return true;
	}
	
	public void TrackingPage() {
		System.out.println("\n\n-----------------------------------");
		System.out.println("Track order");
		System.out.println("-----------------------------------");
		Order order = null;
		boolean optionFlag = true;
		do {
			System.out.println("Order ID: ");
			try {
				int id = Integer.parseInt(input.nextLine());
				if (id == 0) {
					return;
				}
				order= dataLists.TrackOrder(id);
				optionFlag = false;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input! Order ID should contain only digits.\n");
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}while (optionFlag);
		System.out.println("\n\n-----------------------------------");
		System.out.println("Order ID: " + order.getId());
		System.out.println("Name: " + order.getGuest().getName());
		System.out.println("Address: " + order.getGuest().getAddress().getUnitNum() + ", " 
				+ order.getGuest().getAddress().getStreet() + ", " 
				+ order.getGuest().getAddress().getArea() + ", " 
				+ order.getGuest().getAddress().getPostalCode() + " " 
				+ order.getGuest().getAddress().getDistrict() + ", " 
				+ order.getGuest().getAddress().getState());
		if (order.getGuest() instanceof Member) {
			Member member = (Member) order.getGuest();
			System.out.println("Phone Number: " + member.getPhoneNum());
			System.out.println("Type: Member");
		}
		else
			System.out.println("Type: Guest");
		System.out.println("Order status: " + order.getStatus());
		System.out.printf("\n%-5s %-35s %-8s %-12s %-15s %s\n", "No.", "Items", "Type", "Quantity", "Price/Unit"
				, "Promotional Item");
		Map<Item, Integer> itemList = new HashMap<Item, Integer>(order.GetOrderItemList());
		Iterator<Map.Entry<Item, Integer>> iterator = itemList.entrySet().iterator();
		int counter = 1;
		while(iterator.hasNext()) {
			Map.Entry<Item, Integer> orderItem = iterator.next();
			System.out.printf( "%-5s %-35s %-11s %-12s", counter++, orderItem.getKey().getName(),
					orderItem.getKey().getType(), orderItem.getValue());
			if (order.getGuest() instanceof Member)
				System.out.printf("%-20.2f", orderItem.getKey().getMemberPrice());
			else
				System.out.printf("%-20.2f",orderItem.getKey().getNonMemberPrice());
			if (orderItem.getKey().getPromotional() == true)
				System.out.println("Yes");
			else
				System.out.println("No");
		}
		System.out.printf("\nSubtotal Price: %.2f\n", order.ComputeSubtotalPrice());
		System.out.printf("Delivery Charges: %.2f\n", order.getDeliveryCharge());
		System.out.printf("Additional Charges: %.2f\n", order.ComputeAdditionalCharge());
		System.out.printf("Total Discount: -%.2f\n", order.ComputeTotalDiscount());
		System.out.printf("Total Price: %.2f\n", order.ComputeTotalPrice());
		System.out.println("-----------------------------------\n\n");
		input.nextLine();
	}
	
	public boolean OrderingPage() throws DeliveryNotAvailableException {
		boolean repeat = true;
		order.ComputeDeliveryCharge(dataLists.GetDeliveryList());
		do {
			System.out.println("\n\n\n-----------------------------------");
			System.out.println("Online Ordering and Delivery System");
			System.out.println("-----------------------------------");
			System.out.println("Name: " + order.getGuest().getName());
			System.out.println("Address: " + order.getGuest().getAddress().getUnitNum() + ", " + order.getGuest().getAddress().getStreet()
					+ ", " + order.getGuest().getAddress().getArea() + ", " + order.getGuest().getAddress().getPostalCode() + " " 
					+ order.getGuest().getAddress().getDistrict() + ", " + order.getGuest().getAddress().getState());
			System.out.println("-----------------------------------");
			System.out.println("1. Add new item");
			System.out.println("2. Remove an item");
			System.out.println("3. Checkout");
			System.out.println("4. Cancel Order");
			System.out.println("-----------------------------------");
			boolean optionRepeat = true;
			do {
				try {
					System.out.print("Option: ");
					int option;
					option = Integer.parseInt(input.nextLine());
					optionRepeat = false;
					switch (option) {
						case 1: 
							AddItemPage();
							break;
						case 2: 
							RemoveItemPage();
							break;
						case 3:
							repeat = CheckOutPage();
							break;
						case 4:
							repeat = false;
							break;
						default:
							optionRepeat = true;
							System.out.println("Invalid Input! Value should be ranged from '1' to '4' only.\n");
							break;
					}
				}
				catch (NumberFormatException e){
					System.out.println("Invalid Input! Value should contain only number.\n");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					input.nextLine();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}while(optionRepeat);
		}while(repeat);
		return false;
	}
	
	public void AddItemPage() {
		System.out.println("\n\n-----------------------------------");
		System.out.println("Add Item");
		System.out.println("-----------------------------------");
		Item[] itemList = dataLists.GetItemList();
		System.out.printf("\n%-5s %-35s %-10s %-16s %-20s %s\n", "No.", "Items", "Type", "Member Price", 
				"Non-member Price", "Promotional Item");
		for (int i = 0; i < itemList.length; i++) {
			System.out.printf("\n%-5s %-35s %-14s %-15.2f %-25.2f", i+1, itemList[i].getName(),
					itemList[i].getType(), itemList[i].getMemberPrice(), itemList[i].getNonMemberPrice());
			if (itemList[i].getPromotional() == true)
				System.out.printf("%s", "Yes");
			else
				System.out.printf("%s", "No");
		}
		System.out.println("\n-----------------------------------");
		boolean repeatFlag = true;
		do {
			boolean optionFlag = true;
			int itemIndex = -1;
			do {
				try {
					System.out.println("Press '0' to back");
					System.out.print("Item No.: ");
					itemIndex = Integer.parseInt(input.nextLine());
					if (itemIndex == 0 )
						return;
					if (itemIndex < 0 || itemIndex > itemList.length) {
						throw new IllegalArgumentException("\nInvalid Input! Value should be ranged from '1' to '" 
								+ itemList.length + "' only.\n");
					} 
					optionFlag = false;
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input! Value should contain only number.\n");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while (optionFlag);
			
			optionFlag = true;
			
			do {
				try {
					System.out.print("Quantity: ");
					int quantity = Integer.parseInt(input.nextLine());
					order.AddItem(itemList[itemIndex - 1], quantity);
					optionFlag = false;
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input! Value should contain only number.\n");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while (optionFlag);
	
			optionFlag = true;
			
			do {
				try {
					System.out.println("\nDo you want to add another item? ('1'-'Yes' \\ '0'-'No')");
					System.out.print("Option: ");
					int option = Integer.parseInt(input.nextLine());
					if (option < 0 || option > 1) {
						throw new IllegalArgumentException("Invalid Input! Please enter '0' or '1' only.");
					}
					optionFlag = false;
					if (option == 0) 
						repeatFlag = false;
						
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input! Value should contain only number.\n");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while (optionFlag);
		}while (repeatFlag);
		
	}
	
	public void RemoveItemPage() {
		boolean repeatFlag = true;
		do {
			Map<Item, Integer> itemList = new HashMap<Item, Integer>(order.GetOrderItemList());
			if (itemList.size() > 0) {
				System.out.println("\n\n-----------------------------------");
				System.out.println("Remove Item");
				System.out.println("-----------------------------------");
				System.out.printf("\n%-5s %-35s %-8s %s\n", "No.", "Items", "Type", "Quantity");
				Item [] item = new Item[itemList.size()];
				Iterator<Map.Entry<Item, Integer>> iterator = itemList.entrySet().iterator();
				int counter = 1;
				while(iterator.hasNext()) {
					Map.Entry<Item, Integer> orderItem = iterator.next();
					item[counter-1] = orderItem.getKey();
					System.out.printf( "\n%-5s %-35s %-11s %-12s", counter++, orderItem.getKey().getName(),
							orderItem.getKey().getType(), orderItem.getValue());
				}
				System.out.println("\n-----------------------------------");
				boolean optionFlag = true;
				int itemIndex = -1;
				do {
					try {
						System.out.println("Press '0' to back");
						System.out.print("Item No.: ");
						itemIndex = Integer.parseInt(input.nextLine());
						if (itemIndex == 0) 
							return;
						if (itemIndex < 0 || itemIndex > itemList.size()) {
							throw new IllegalArgumentException("\nInvalid Input! Value should be ranged from '1' to '" 
									+ itemList.size() + "' only.\n");
						}
						order.RemoveItem(item[itemIndex - 1]);
						optionFlag = false;
					} catch (NumberFormatException e) {
						System.out.println("Invalid Input! Value should contain only number.\n");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}while (optionFlag);
				
				optionFlag = true;
				
				do {
					try {
						System.out.println("\nDo you want to remove another item? ('1'-'Yes' \\ '0'-'No')");
						System.out.print("Option: ");
						int option = Integer.parseInt(input.nextLine());
						optionFlag = false;
						if (option != 0 && option != 1) {
							throw new IllegalArgumentException("Invalid Input! Please enter '0' or '1' only.");
						}
						if (option == 0) 
							repeatFlag = false;
							
					} catch (NumberFormatException e) {
						System.out.println("Invalid Input! Value should contain only number.\n");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}while (optionFlag);
			}
			else {
				System.out.println("\nThere is no item in this order.");
				input.nextLine();
				repeatFlag = false;
			}
		}while (repeatFlag);
		
	}
	
	public boolean CheckOutPage() throws IllegalArgumentException{
		System.out.println("\n\n-----------------------------------");
		System.out.println("CheckOut");
		System.out.println("-----------------------------------");
		double subTotalPrice = order.ComputeSubtotalPrice();
		double totalDiscount = order.ComputeTotalDiscount();
		double addCharge = order.ComputeAdditionalCharge();
		double deliveryCharge = order.getDeliveryCharge();
		double totalPrice = order.ComputeTotalPrice();
		
		System.out.println("Order ID: " + order.getId());
		System.out.println("Name: " + order.getGuest().getName());
		System.out.println("Address: " + order.getGuest().getAddress().getUnitNum() + ", " 
				+ order.getGuest().getAddress().getStreet() + ", " 
				+ order.getGuest().getAddress().getArea() + ", " 
				+ order.getGuest().getAddress().getPostalCode() + " " 
				+ order.getGuest().getAddress().getDistrict() + ", " 
				+ order.getGuest().getAddress().getState());
		System.out.print("Type: ");
		if (order.getGuest() instanceof Member) 
			System.out.println("Member");
		else
			System.out.println("Guest");
		System.out.printf("\n%-5s %-35s %-8s %-12s %-15s %s\n", "No.", "Items", "Type", "Quantity", "Price/Unit"
				, "Promotional Item");
		Map<Item, Integer> itemList = new HashMap<Item, Integer>(order.GetOrderItemList());
		Iterator<Map.Entry<Item, Integer>> iterator = itemList.entrySet().iterator();
		int counter = 1;
		while(iterator.hasNext()) {
			Map.Entry<Item, Integer> orderItem = iterator.next();
			System.out.printf( "%-5s %-35s %-11s %-12s", counter++, orderItem.getKey().getName(),
					orderItem.getKey().getType(), orderItem.getValue());
			if (order.getGuest() instanceof Member)
				System.out.printf("%-20.2f", orderItem.getKey().getMemberPrice());
			else
				System.out.printf("%-20.2f",orderItem.getKey().getNonMemberPrice());
			if (orderItem.getKey().getPromotional() == true)
				System.out.println("Yes");
			else
				System.out.println("No");
			}
		System.out.println("\n-----------------------------------");
		System.out.printf("\nSubtotal Price: %.2f\n", subTotalPrice);
		System.out.printf("Delivery Charges: %.2f\n", deliveryCharge);
		System.out.printf("Additional Charges: %.2f\n", addCharge);
		System.out.printf("Total Discount: -%.2f\n", totalDiscount);
		System.out.printf("Total Price: %.2f\n", totalPrice);
		System.out.println("-----------------------------------\n\n");
		System.out.println("Confirm Checkout? ('1'-'Yes' // '0'-'No')");
		boolean optionFlag = true;
		do {
			try {
				int option = Integer.parseInt(input.nextLine());
				if (option != 0 && option != 1) {
					throw new IllegalArgumentException("Invalid Input! Please enter '0' or '1' only.");
				}
				if (option == 0) 
					return true;
				optionFlag = false;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input! Value should contain only number.\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
			}
		} while(optionFlag);
		Payment payment = new Payment(paymentIdCount++, null, order.getId(), order.ComputeTotalPrice());
		order.setPayment(payment);
		order.MakePayment();
		dataLists.AddOrder(order);
		dataLists.AddPayment(payment);
		System.out.println("\n\nOrder has been checked out.");
		System.out.println("Payment gateway is unavailable at the moment.");
		System.out.println("Your order will be pending for payment.");
		System.out.println("\nOrder ID: " + order.getId());
		input.nextLine();
		return false;
	}
	
	public void HardCodeData() throws DeliveryNotAvailableException{
		Order order = new Order(orderIdCount++);
		order.GetMemberDetails("john", "john", dataLists);
		Item[] itemList = dataLists.GetItemList();
		order.AddItem(itemList[3], 2);
		order.AddItem(itemList[5], 1);
		order.AddItem(itemList[7], 2);
		order.AddItem(itemList[1], 4);
		Payment payment = new Payment(paymentIdCount++, "Credit Card", order.getId(), order.ComputeTotalPrice());
		order.setPayment(payment);
		order.MakePayment();
		dataLists.AddOrder(order);
		dataLists.AddPayment(payment);
		
		Address address = new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20);
		order = new Order(orderIdCount++);
		order.GetGuestDetails("Lean Wei Liang", address);
		order.ComputeDeliveryCharge(dataLists.GetDeliveryList());
		order.AddItem(itemList[0], 1);
		order.AddItem(itemList[2], 1);
		order.AddItem(itemList[15], 1);
		order.AddItem(itemList[19], 2);
		payment = new Payment(paymentIdCount++, "Online Banking", order.getId(), order.ComputeTotalPrice());
		order.setPayment(payment);
		order.MakePayment();
		dataLists.AddOrder(order);
		dataLists.AddPayment(payment);
	}
}
