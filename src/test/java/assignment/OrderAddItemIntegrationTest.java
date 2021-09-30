package assignment;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import junitparams.JUnitParamsRunner;

import org.junit.runner.RunWith;
import junitparams.Parameters;



@RunWith(JUnitParamsRunner.class)
public class OrderAddItemIntegrationTest {
	Order order;
	static DataLists dataLists;
	
	static {
		try {
			dataLists = new DataLists();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	public OrderAddItemIntegrationTest() {
		order = new Order();
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
		Item[] itemList = dataLists.GetItemList();
		order.AddItem(itemList[0], 3);
		order.AddItem(itemList[14], 5);
		order.AddItem(itemList[19], 1);
		
		Map<Item, Integer> actualResult = order.GetOrderItemList();
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
				new Object[] {dataLists.GetItemList()[0], 0},
				//Test Case #11
				new Object[] {null, 5},
				//Test Case #12
				new Object[] {null, 0}
		};
	}

}
