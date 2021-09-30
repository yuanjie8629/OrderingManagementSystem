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
public class OrderPaymentUnitTest {
	Order order;

	/*
	 * Rule #15, #16
	 */
	
	//Test Case #37, #38
	@Test
	@Parameters (method = "getPaymentStatus")
	public void testPaymentStatus(boolean paymentStatus, String expResult) {
		Payment paymentMock = mock(Payment.class);
		order = new Order(paymentMock);
		when(paymentMock.Pay()).thenReturn(paymentStatus);
		order.MakePayment();
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
