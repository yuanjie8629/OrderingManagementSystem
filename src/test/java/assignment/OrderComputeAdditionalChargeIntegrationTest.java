package assignment;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import junitparams.Parameters;
import junitparams.JUnitParamsRunner;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class OrderComputeAdditionalChargeIntegrationTest {
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
	 * Rule #6, #7, #8, #9
	 */
	//Approach: Equivalence Partitioning (Member, Guest)
	//Test Case #14, #17, #21, #24
		@Test
		@Parameters (method = "getComputeAdditionalCharge")
		public void testComputeAdditionalCharge(Guest guest, Item[] item, int[] quantity, double expResult) {
			order = new Order(1);
			if (guest instanceof Member) {
				Member member = (Member) guest;
				order.GetMemberDetails(member.getUsername(), member.getPassword(), dataLists);
			}
			else {
				order.GetGuestDetails(guest.getName(), guest.getAddress());
			}
			
			for (int i = 0; i < item.length; i++) {
				order.AddItem(item[i], quantity[i]);
			}
			
			double actualResult = order.ComputeAdditionalCharge();
			assertEquals(expResult, actualResult, 0.0);
		}

		private Object[] getComputeAdditionalCharge() {
			return new Object[] {
					//Test Case #14
					//Member Price
					//Expected Result: Additional Charge = 3.00
					new Object[] {
							//2 set apple pie
							memberList[0], new Item[] {itemList[13]}, new int[] {2}, 3.00
							},
					//Test Case #17
					//Member Price
					//Expected Result: Additional Charge = 0
					new Object[] {
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							memberList[0],
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							0},
					//Test Case #21
					//Non-member Price
					//Expected Result: Additional Charge = 3.00
					new Object[] {
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)),
							//1 set Chocolate Indulgence, 1 set Pineapple tart, 5 set Carrot Cake
							new Item[] {itemList[3], itemList[18], itemList[9]}, 
							new int[] {1, 1, 5}, 0
							},
					//Test Case #24
					//Non-member Price
					//Expected Result: Additional Charge = 0
					new Object[] {
							//1 Burnt Cheesecake, 5 Croissant, 3 Macaron
							new Guest("Lean Wei Linag", 
									new Address("Melaka", "Jasin", "Jasin", 77000, "Jalan Rim Baru 3", 20)),
							new Item[] {itemList[2], itemList[15], itemList[16]},
							new int[] {1, 5, 3},
							0},
			};
		}

}
