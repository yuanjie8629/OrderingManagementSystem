package assignment;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(value = Suite.class) 
@SuiteClasses(value = {
		OrderDeliveryChargeUnitTest.class,
		OrderAddItemUnitTest.class,
		OrderComputeSubtotalPriceUnitTest.class,
		OrderComputeTotalDiscountUnitTest.class,
		OrderComputeAdditionalChargeUnitTest.class,
		OrderComputeTotalPriceUnitTest.class,
		OrderPaymentUnitTest.class
})

public class OrderModuleUnitTestSuite {
	
}
