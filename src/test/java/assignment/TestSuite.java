package assignment;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(value = Suite.class) 
@SuiteClasses(value = {
		UnitTestSuite.class,
		IntegrationTestSuite.class
})

public class TestSuite {
	
}
