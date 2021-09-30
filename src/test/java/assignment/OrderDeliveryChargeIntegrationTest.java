package assignment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;

public class OrderDeliveryChargeIntegrationTest {
	Order order;
	static DataLists dataLists;
	static Item[] itemList;
	static Member[] memberList;
	
	@BeforeClass
	public static void setup() {
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
	 * Rule #2
	 */
	
	//Test Case #7
		@Test (expected = DeliveryNotAvailableException.class)
		public void testGetDeliveryChargesInvalid() throws DeliveryNotAvailableException {
			Address address = new Address("Melaka", "Jasin", "Johor Bahru", 77000, "Jalan Rim Baru 3", 20);
			order = new Order(1);
			order.GetGuestDetails("Lean Wei Linag", address);
			order.ComputeDeliveryCharge(dataLists.GetDeliveryList());
		}
		
	/*
	 * Rule #3
	 */
		
	//Test Case #8
		@Test
		public void testGetDeliveryChargesValid() {
			Member member = memberList[0];
			order = new Order(1);
			order.GetMemberDetails(member.getUsername(), member.getPassword(), dataLists);
			
			try {
				order.ComputeDeliveryCharge(dataLists.GetDeliveryList());
			} catch (DeliveryNotAvailableException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			
			double expResult = 3.50;
			double actualResult = order.getDeliveryCharge();
			assertEquals(expResult, actualResult, 0.0);
		}
}
