package assignment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;

import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class OrderComputeTotalDiscountUnitTest {
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
	
	public OrderComputeTotalDiscountUnitTest() {
		dataListsMock = mock(DataLists.class);
		when(dataListsMock.GetItemList()).thenReturn(itemFile.toArray(new Item[0]));
		when(dataListsMock.GetMemberList()).thenReturn(memberFile.toArray(new Member[0]));
		itemList = dataListsMock.GetItemList();
		memberList = dataListsMock.GetMemberList();
	}
	
	/*
	 * Rule #7 , #9, #11, #13
	 */
	//Approach: Equivalence Partitioning
	//Test Case #18, #25, #28, #31
		@Test
		@Parameters (method = "getComputeTotalDiscountValid")
		public void testComputeTotalDiscountValid(Guest guest, Item[] items, int[] quantity, double expResult) {
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
			
			double actualResult = order.ComputeTotalDiscount();
			verify(dataListsMock, times(1)).GetItemList();
			verify(dataListsMock, times(1)).GetMemberList();
			assertEquals(expResult, actualResult, 0.0);
		}
		
		private Object[] getComputeTotalDiscountValid() {
			return new Object[] {
					//Test Case #18 Rule #7
					//Member Price
					//Expected Result: Total Discount = 0
					new Object[] {
							memberList[0],
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							0},
					//Test Case #25 Rule #9
					//Non-member Price
					//Expected Result: Total Discount = 0
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)),
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							0},
					//Test Case #28 Rule #11
					//Member price + 5% discount on member price for promotional items
					//Expected Result: Total Discount = 8.70
					new Object[] {
							memberList[0],
							//1 Burnt Cheesecake, 1 Absolute Durian, 1 Pineapple tart, 3 Macaron
							new Item[] {itemList[2], itemList[10], itemList[18], itemList[16]},
							new int[] {1, 1, 1, 3},
							8.70},
					//Test Case #31 Rule #13
					//Non-member price + 5% discount on non-member price for promotional items
					//Expected Result: Total Discount = 9.10
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)), 
							//1 Burnt Cheesecake, 1 Absolute Durian, 1 Pineapple tart, 3 Macaron
							new Item[] {itemList[2], itemList[10], itemList[18], itemList[16]},
							new int[] {1, 1, 1, 3},
							9.10},
					
					
			};
		}
		
		/*
		 * Rule #14
		 */
		
		//Test Case #19, #20
		@Test (expected = IllegalArgumentException.class)
		@Parameters (method = "getComputeTotalDiscountInvalid")
		public void testComputeTotalDiscountInvalid(Guest guest) {
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
			
			verify(dataListsMock, times(1)).GetItemList();
			verify(dataListsMock, times(1)).GetMemberList();
			order.ComputeTotalDiscount();
		}
		
		private Object[] getComputeTotalDiscountInvalid() {
			return new Object[] {
					//Test Case #35
					new Object[] {memberList[0]},
					//Test Case #36
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20))}
			};
		}
}
