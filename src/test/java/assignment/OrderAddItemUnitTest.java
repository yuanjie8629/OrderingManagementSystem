package assignment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import junitparams.JUnitParamsRunner;

import org.junit.runner.RunWith;
import junitparams.Parameters;

import static org.mockito.Mockito.*;


@RunWith(JUnitParamsRunner.class)
public class OrderAddItemUnitTest {
	static ArrayList<Item> itemFile = new ArrayList<>();
	Order order;
	DataLists dataListsMock;
	
	static {
		itemFile = ReadTestFile.ReadItemFile();
	}
	
	public OrderAddItemUnitTest() {
		order = new Order();
		dataListsMock = mock(DataLists.class);
	}
	
	/*
	 * Rule #4
	 */
	
	//Test Case #9
	@Test
	public void testAddItemsValid() {
		Map<Item, Integer> expResult = new HashMap<>() {{
			//Add the 1st item in text file which is equal to itemList.get(0) 
			put(new Item(1, "Butter Cake", "Cake", 46.00, 46.80, false), 3);
			//Add the 15th item in text file which is equal to itemList.get(14) 
			put(new Item(15, "Cinnamon Roll (2 pieces)", "Pastry", 8.20, 8.70, false), 5);
			//Add the last item in text file which is equal to itemList.get(19) 
			put(new Item(20, "Spring roll (1 container)", "Pastry", 30.00, 32.00, true), 1);
		}};
		when(dataListsMock.GetItemList()).thenReturn(itemFile.toArray(new Item[0]));
		Item[] itemList = dataListsMock.GetItemList();
		order.AddItem(itemList[0], 3);
		order.AddItem(itemList[14], 5);
		order.AddItem(itemList[19], 1);
		
		Map<Item, Integer> actualResult = order.GetOrderItemList();
		verify(dataListsMock, times(1)).GetItemList();
		assertTrue(order.GetOrderItemList().size() == 3);
		assertEquals(expResult, actualResult);

	}
		
	/*
	 * Rule #5
	 */
	
	//Approach: Boundary Value Analysis on quantity
	//Test Case #10, #11
	@Test (expected = IllegalArgumentException.class)
	@Parameters(method = "getAddItemsInvalid")
	public void testAddItemsInvalid(Item item, int quantity) {
		order.AddItem(item, quantity);
	}
	
	private Object[] getAddItemsInvalid() {
		return new Object[] {
				//Test Case #10
				//quantity invalid low = 0
				new Object[] {itemFile.get(0), 0},
				//Test Case #11
				new Object[] {null, 5},
				//Test Case #12
				new Object[] {null, 0}
		};
	}

}
