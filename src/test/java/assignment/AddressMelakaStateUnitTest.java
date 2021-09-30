package assignment;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AddressMelakaStateUnitTest {
	Address address;
	
	/*
	 * Rule #1
	 */
	
	//Approach: Equivalence Partitioning
	//Test Case #1, #2, #3, #4
	@Test (expected = IllegalArgumentException.class)
	@Parameters(method = "getAddressStateInvalidIllegalArgExp")
	public void testAddressStateInvalidIllegalArgExp(String state) throws IllegalArgumentException, DeliveryNotAvailableException {
			address = new Address(state);
	}
	
	private Object[] getAddressStateInvalidIllegalArgExp() {
		return new Object[] {
				//Test Case #1
				new Object[] {" "},
				//Test Case #2
				new Object[] {null},
				//Test Case #3
				new Object[] {"Melaka@"},
				//Test Case #4
				new Object[] {"Melaka2"}
		};
	}
	
	/*
	 * Rule #1
	 */

	//Test Case #5
	@Test (expected = DeliveryNotAvailableException.class)
	public void testDeliveryNotAvailExp() throws IllegalArgumentException, DeliveryNotAvailableException {
		String state = "Johor";
		address = new Address(state);
	}
	
	/*
	 * Rule #1
	 */

	//Test Case #6
	@Test
	public void testAddressStateValid() {
		String state = "Melaka";
		String expResult = "Melaka";
		try {
			address = new Address(state);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (DeliveryNotAvailableException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		String actualResult = address.getState();
		assertEquals(expResult, actualResult);
	}
}
