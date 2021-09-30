package assignment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class OrderComputeSubtotalPriceUnitTest {
	static ArrayList<Item> itemFile = new ArrayList<>();
	static ArrayList<Member> memberFile = new ArrayList<>();
	Order order;
	DataLists dataListsMock;
	static Item[] itemList;
	static Member[] memberList;
	
	static {
		itemFile = ReadTestFile.ReadItemFile();
		memberFile = ReadTestFile.ReadMemberFile();
	}
	
	public OrderComputeSubtotalPriceUnitTest() {
		dataListsMock = mock(DataLists.class);
		when(dataListsMock.GetItemList()).thenReturn(itemFile.toArray(new Item[0]));
		when(dataListsMock.GetMemberList()).thenReturn(memberFile.toArray(new Member[0]));
		itemList = dataListsMock.GetItemList();
		memberList = dataListsMock.GetMemberList();
	}
	
	/*
	 * Rule #6, #7, #8, #9, #11, #13
	 */
	
	//Approach: Equivalence Partitioning
	//Test Case #13, #16, #20, #23, #25, #27
		@Test
		@Parameters (method = "getComputeSubtotalPriceValid")
		public void testComputeSubtotalPriceValid(Guest guest, Item[] items, int[] quantity, double expResult) {			
				order = new Order(1);
				if (guest instanceof Member) {
					Member member = (Member) guest;
					when(dataListsMock.FindMember(anyString(), anyString())).thenReturn(member);
					order.GetMemberDetails(member.getUsername(), member.getPassword(), dataListsMock);
					verify(dataListsMock, times(1)).FindMember(anyString(), anyString());
				}
				else {
					order.GetGuestDetails(guest.getName(), guest.getAddress());
				}
			
			for (int i = 0; i < items.length; i++) {
				order.AddItem(items[i], quantity[i]);
			}
			
			double actualResult = order.ComputeSubtotalPrice();
			verify(dataListsMock, times(1)).GetItemList();
			verify(dataListsMock, times(1)).GetMemberList();		
			assertEquals(expResult, actualResult, 0.0);
		}
		
		private Object[] getComputeSubtotalPriceValid() {
			return new Object[] {
					//Test Case #13 Rule #6
					//Member Price + Additional Charge of RM3
					//Expected Result: Subtotal Price = 18.60
					new Object[] {
							memberList[0], 
							//2 Apple Pie
							new Item[] {itemList[13]}, 
							new int[] {2},
							18.60},
					//Test Case #16 Rule #7
					//Member Price
					//Expected Result: Subtotal Price = 186.00
					new Object[] {
							memberList[0],
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							186.00},
					//Test Case #20 Rule #8
					//Non-member price + Additional Charge of RM3
					//Expected Result: Subtotal Price = 19.80
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)), 
							//2 Apple Pie
							new Item[] {itemList[13]}, 
							new int[] {2},
							19.80},
					//Test Case #23 Rule #9
					//Non-member price 
					//Expected Result: Subtotal Price = 194.00
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)), 
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							194.00},
					//Test Case #27 Rule #11
					//Member price + 5% discount on member price for promotional items
					//Expected Result: Subtotal Price = 339.00
					new Object[] {
							memberList[0],
							//1 Burnt Cheesecake, 1 Absolute Durian, 1 Pineapple tart, 3 Macaron
							new Item[] {itemList[2], itemList[10], itemList[18], itemList[16]},
							new int[] {1, 1, 1, 3},
							339.00},
					//Test Case #26 Rule #13
					//Non-member price + 5% discount on non-member price for promotional items
					//Expected Result: Subtotal Price = 353.50
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)), 
							//1 Burnt Cheesecake, 1 Absolute Durian, 1 Pineapple tart, 3 Macaron
							new Item[] {itemList[2], itemList[10], itemList[18], itemList[16]},
							new int[] {1, 1, 1, 3},
							353.50},
			};
		}

		/*
		 * Rule 14
		 */
		//Approach: Equivalence Partitioning (Guest, Member)
		//Test Case #33, #34
		@Test (expected = IllegalArgumentException.class)
		@Parameters (method = "getComputeSubtotalPriceInvalid")
		public void testComputeSubtotalPriceInvalid(Guest guest) {
			order = new Order(1);
			if (guest instanceof Member) {
				Member member = (Member) guest;
				when(dataListsMock.FindMember(anyString(), anyString())).thenReturn(member);
				order.GetMemberDetails(member.getUsername(), member.getPassword(), dataListsMock);
				verify(dataListsMock, times(1)).FindMember(anyString(), anyString());
			}
			else {
				order.GetGuestDetails(guest.getName(), guest.getAddress());
			}
			
				
			order.ComputeSubtotalPrice();
		}
		
		private Object[] getComputeSubtotalPriceInvalid() {
			return new Object[] {
					//Test Case #33
					//Member
					new Object[] {memberList[0]},
					//Test Case #34
					//Guest
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20))}
			};
		}
}
