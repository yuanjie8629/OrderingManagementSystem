package assignment;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class) 
@SuiteClasses(value = {
		OrderAddItemIntegrationTest.class,
		OrderComputeAdditionalChargeIntegrationTest.class,
		OrderComputeSubtotalPriceIntegrationTest.class,
		OrderComputeTotalDiscountIntegrationTest.class,
		OrderComputeTotalPriceIntegrationTest.class,
		OrderDeliveryChargeIntegrationTest.class,
		OrderPaymentIntegrationTest.class
})

public class IntegrationTestSuite {

}
