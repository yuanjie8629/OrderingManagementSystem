package assignment;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.mockito.Mockito.*;

import java.io.*;
import java.util.*;

@RunWith(JUnitParamsRunner.class)
public class OrderPaymentIntegrationTest {
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
	 * Rule #15, #16
	 */
	
	//Test Case #37, #38
	@Test
	@Parameters (method = "getPaymentStatus")
	public void testPaymentStatus(boolean paymentStatus, String expResult) {
		order = new Order(1);
		order.GetMemberDetails("john", "john", dataLists);
		
		try {
			order.ComputeDeliveryCharge(dataLists.GetDeliveryList());
		} catch (DeliveryNotAvailableException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		Item[] itemList = dataLists.GetItemList();
		order.AddItem(itemList[0], 3);
		order.AddItem(itemList[14], 5);
		order.AddItem(itemList[19], 1);
		
		Payment paymentMock = mock(Payment.class);
		order.setPayment(paymentMock);
		when(paymentMock.Pay()).thenReturn(paymentStatus);
		order.MakePayment();
		dataLists.AddOrder(order);
		dataLists.AddPayment(paymentMock);
		
		verify(paymentMock, times(1)).Pay();
		assertEquals(expResult, order.getStatus());
	}
	
	private Object[] getPaymentStatus() {
		return new Object[] {
				new Object[] {true, "Paid & Ready for Delivery"},
				new Object[] {false, "Pending for Payment"}
		};
	}
	
}
