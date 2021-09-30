package assignment;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;

public class OrderDeliveryChargeUnitTest {
	static ArrayList<Item> itemFile = new ArrayList<>();
	static ArrayList<DeliveryRate> deliveryFile = new ArrayList<>();
	static ArrayList<Member> memberFile = new ArrayList<>();
	Order order;
	DataLists dataListsMock;
	static Member[] memberList;
	
	@BeforeClass
	public static void setup() {
		deliveryFile = ReadTestFile.ReadDeliveryRateFile();
		memberFile = ReadTestFile.ReadMemberFile();
	}
	
	@Before
	public void mocking() {
		dataListsMock = mock(DataLists.class);
		when(dataListsMock.GetDeliveryList()).thenReturn(deliveryFile.toArray(new DeliveryRate[0]));
		when(dataListsMock.GetMemberList()).thenReturn(memberFile.toArray(new Member[0]));
		memberList = dataListsMock.GetMemberList();
	}
	/*
	 * Rule #2
	 */
	
	//Test Case #7
		@Test (expected = DeliveryNotAvailableException.class)
		public void testGetDeliveryChargesInvalid() throws DeliveryNotAvailableException {
			Address address = new Address("Melaka", "Jasin", "Johor Bahru", 77000, "Jalan Rim Baru 3", 20);
			order = new Order (1);
			order.GetGuestDetails("Lean Wei Linag", address);
			order.ComputeDeliveryCharge(dataListsMock.GetDeliveryList());

		}
		
	/*
	 * Rule #3
	 */
		
	//Test Case #8
		@Test
		public void testGetDeliveryChargesValid() {
			Member member = memberList[0];
			when(dataListsMock.FindMember(anyString(), anyString())).thenReturn(member);
			order = new Order(1);
			order.GetMemberDetails(member.getUsername(), member.getPassword(), dataListsMock);
			try {
				order.ComputeDeliveryCharge(dataListsMock.GetDeliveryList());
			} catch (DeliveryNotAvailableException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			
			double expResult = 3.50;
			double actualResult = order.getDeliveryCharge();
			verify(dataListsMock, times(1)).GetDeliveryList();
			verify(dataListsMock, times(1)).GetMemberList();
			verify(dataListsMock, times(1)).FindMember(anyString(), anyString());
			assertEquals(expResult, actualResult, 0.0);
		}
}
