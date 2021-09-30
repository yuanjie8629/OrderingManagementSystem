package assignment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import junitparams.Parameters;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class OrderComputeTotalPriceIntegrationTest {
	Order order;
	static DataLists dataLists;
	static Item[] itemList;
	static Member[] memberList;
	
	static {
		try {
			dataLists = new DataLists();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		itemList = dataLists.GetItemList();
		memberList = dataLists.GetMemberList();
	}
	
	/*
	 * Rule #6, #7, #8, #9, #11, #13
	 */
	//Approach: Equivalence Partitioning
	//Test Case #15, #19, #22, #26, #29
		@Test
		@Parameters (method = "getComputeTotalPrice")
		public void testComputeTotalPrice(Guest guest, Item[] items, int[] quantity, double expResult) {
			order = new Order(1);
			if (guest instanceof Member) {
				Member member = (Member) guest;
				order.GetMemberDetails(member.getUsername(), member.getPassword(), dataLists);
			}
			else {
				order.GetGuestDetails(guest.getName(), guest.getAddress());
			}
			
			try {
				order.ComputeDeliveryCharge(dataLists.GetDeliveryList());
			} catch (DeliveryNotAvailableException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			
			for (int i = 0; i < items.length; i++) {
				order.AddItem(items[i], quantity[i]);
			}
			
			double actualResult = order.ComputeTotalPrice();
			assertEquals(expResult, actualResult, 0.0);
		}
		
		private Object[] getComputeTotalPrice() {
			return new Object[] {
					//Test Case #15 Rule #6
					//Member Price + Additional Charge of RM3
					//Expected Result: Total Price = 25.10
					new Object[] {
							memberList[0], 
							//2 Apple Pie
							new Item[] {itemList[13]}, 
							new int[] {2},
							25.10},
					//Test Case #19 Rule #7
					//Member Price
					//Expected Result: Total Price = 189.50
					new Object[] {
							memberList[0],
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							189.50},
					//Test Case #22 Rule #8
					//Non-member price + Additional Charge of RM3
					//Expected Result: Total Price = 26.80
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)), 
							//2 Apple Pie
							new Item[] {itemList[13]}, 
							new int[] {2},
							26.80},
					//Test Case #26 Rule #9
					//Non-member price 
					//Expected Result: Total Price = 198.00
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)), 
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							198.00},
					//Test Case #29 Rule #11
					//Member price + 5% discount on member price for promotional items
					//Expected Result: Total Price = 333.80
					new Object[] {
							memberList[0],
							//1 Burnt Cheesecake, 1 Absolute Durian, 1 Pineapple tart, 3 Macaron
							new Item[] {itemList[2], itemList[10], itemList[18], itemList[16]},
							new int[] {1, 1, 1, 3},
							333.80},
					//Test Case #32 Rule #13
					//Non-member price + 5% discount on non-member price for promotional items
					//Expected Result: Total Price = 348.40
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)), 
							//1 Burnt Cheesecake, 1 Absolute Durian, 1 Pineapple tart, 3 Macaron
							new Item[] {itemList[2], itemList[10], itemList[18], itemList[16]},
							new int[] {1, 1, 1, 3},
							348.40},
			};
		}
}
