package com.testing.string;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


@DisplayName("Test cases for String Class") // @DisplayName can be used at class level
// @TestInstance(TestInstance.Lifecycle.PER_CLASS) - Changes behavior to create one instance per class
class StringTest  {

	private String str;

	@BeforeAll // Executed once before all the test cases - like initialize DB connection
	static void beforeAll() {
		System.out.println("Initialize connection to database");
	}

	@AfterAll // Executed once after all the test cases - like close DB connection
	static void afterAll() { 
		System.out.println("Close connection to database");
	}

	@BeforeEach // Executed before every test - Setting up data in every test
	void beforeEach(TestInfo testInfo, TestReporter testReporter) {
		//System.out.println("Initialize Test Data for  " + testInfo.getDisplayName());
		testReporter.publishEntry("DisplayName: ","Initialize Test Data for:  " + testInfo.getDisplayName());
		testReporter.publishEntry("TagName: ","Tags for this test case:  " + testInfo.getTags());
	}

	@AfterEach // Executed after every test - Data clean up after every test
	void afterEach(TestInfo info) {
		System.out.println("Clean up Test Data for  " + info.getDisplayName());
	}
	
	
	@Test // In order for the tests to be recognized as such, we’ll add the @Test annotation
		  // A test must not be private, nor may it return a value - otherwise it’ll just be ignored. It can be public or protected 
	protected void whateveryouwant1() { // Method name can be whatever you want
		fail("Not yet implemented"); // Test method under construction so it should always fail. This is necessary because empty test case would succeed
	}
	
	@Test
	void whateveryouwanr2() { 
		// Absence of failure is success in any test case - Even this empty test case would succeed
		// Whenever a test case is written below steps are typically followed and last step of assertions is very important
		// 1) Write test code
		// 2) Invoke method square(4) => CUT
		// 3) Checks in place - 16 => Assertions
	}
	
	// Do multiple test asserts with AssertAll - Success if all grouped assertions succeed or else failure
	@Test 
	void testMultiply() {
		assertAll(
				() -> assertEquals(0, Math.multiplyExact(1, 0)),
				() -> assertEquals(1, Math.multiplyExact(1, 1)),
				() -> assertEquals(6, Math.multiplyExact(2, 3))
				);
	}

	@Test
	@Disabled // @Ignored in Junit4
	void length_basic() {
		int actualLength = "ABCD".length(); // Make sure you cover all possible inputs for a test case including corner cases like empty string "".length() or even null input 
		int expectedLength = 4;
		boolean testvar = false;
		//assumeTrue(testvar); // Validates the given assumption that testvar is true - Here assumption fails and TestAbortedException is thrown since testvar is false
		assumeFalse(testvar);  // Here assumption fails succeeds 
		// Assert length == 4
		assertEquals(expectedLength, actualLength); // Note - expected is a first parameter and then actual
		// Overloaded assertEquals() methods are available for other data types 
	}

	@Test 
	protected void length_greater_than_zero() { // To execute ONLY 1 test select method name --> Right click --> Run As --> Junit Test, otherwise all test cases in a class will be executed 
		assertTrue("ABCD".length() > 0);
		assertTrue("ABC".length() > 0);
		assertTrue("A".length() > 0);
		assertTrue("DEF".length() > 0); // assert that condition returns true
	}
	
	// In above test 'length_greater_than_zero' there are different input parameters for which same test needs to be executed
	// @ParameterizedTest will be better approach for such scenarios
	@ParameterizedTest
	@ValueSource(strings = { "ABCD","", "ABC", "A", "DEF" })
	void length_greater_than_zero_using_parameterized_test(String str) {
		assertTrue(str.length() > 0);
	}

	@ParameterizedTest(name = "{0} toUpperCase is {1}") // Giving name to a Parameterized Test by using argument index
	@CsvSource(value = { "abcd, ABCD", "abc, ABC", "'',''", "abcdefg, ABCDEFG" })
	void uppercase(String word, String capitalizedWord) {
		assertEquals(capitalizedWord, word.toUpperCase());
	}

	@ParameterizedTest(name = "{0} length is {1}")
	@CsvSource(value = { "abcd, 4", "abc, 3", "'',0", "abcdefg, 7" })
	void length(String word, int expectedLength) {
		assertEquals(expectedLength, word.length());
	}

	@Test
	@DisplayName("When length is null, throw an exception") // Provides better readability when test results are observed
	void length_exception() { // This test fails if NullPointerException is NOT thrown
		String str = null;
		// Asserting exceptions with Junit
		assertThrows(NullPointerException.class,  
				() -> {   // With functional programming (Lambda) you can send a method as an argument
					str.length();
				});
	}

	@Test
	@Disabled // Disable this unit test - This test will not be executed - @Disabled can also be used at class level
	void performanceTest() { // Unit Testing For Performance
		// Another example of functional programming
		assertTimeout(Duration.ofSeconds(5), () -> { // 'assertTimeout' may be required in scenarios where a method is expected to be executed within specific time limit.
													 // and test should fail if it takes more time than expected (here 5 secs)
 			for (int i = 0; i <= 1000000; i++) {
				int j = i;
				System.out.println(j);
			}
		});
	}

	@Test
	@Tag("smoke") // @Tag annotation can be used to selectively include/exclude tests with specific tags - it can be for a specific profile or anything else
	protected void toUpperCase_basic() { // Unlike previous Junit versions - Test methods need not be public 
		String str = "abcd";
		String result = str.toUpperCase();
		assertNotNull(result); //assert that result is not null
		// assertNull(result); //assert that result is null
		assertEquals("ABCD", str.toUpperCase()); // Right click on result variable --> Refactor --> inline - to improve code readability and get rid of redundant variables
	}

	//@Test - Test should not be annotated with multiple competing annotations such as @Test, @RepeatedTest, @ParameterizedTest, @TestFactory, etc.
	@RepeatedTest(5) // There might be scenarios like generating random numbers in a method or a method using async code like Threads or ExecutorService where we need to repeat same test multiple times
	void contains_basic() {
		// Both assertions below are doing the same thing
		assertEquals(false, "abcdefgh".contains("ijk"));
		assertFalse("abcdefgh".contains("ijk"),"Main String input should contain sub String"); // Last message parameter adds more information in failure trace giving reason why test failed
	}
	
	
	@RepeatedTest(5) // RepetitionInfo can be used to execute repetitions conditionally
	void contains_basic_info(RepetitionInfo info) {
		if(info.getCurrentRepetition() == 1) {
			assertEquals(false, "abcdefgh".contains("ijk"));
		}
		if(info.getCurrentRepetition() == 3) {
			assertFalse("abcdefgh".contains("ijk"),"Main String input should contain sub String"); // Last message parameter adds more information in failure trace giving reason why test failed
		}
	}


	@Test
	void split_basic() {
		String str = "abc def ghi";
		String actualResult[] = str.split(" ");
		String[] expectedResult = new String[] { "abc", "def", "ghi" };
		assertArrayEquals(expectedResult, actualResult); // assert array equality - Verify each item in the arrays are equal in the right position
		// assertIterableEquals(expected, actual); Verify each item in the iterable are equal in the corresponding positions
	}
	
	@Nested // Grouping similar tests with @Nested annotation - Here all tests for an empty string
	@DisplayName("For an empty String")
	class EmptyStringTests {
		
		@BeforeEach
		void setToEmpty() {
			str = "";
		}
		
		@Test
		@DisplayName("length should be zero")
		void lengthIsZero() {
			assertEquals(0,str.length());
		}
		
		@Test
		@DisplayName("upper case is empty")
		void uppercaseIsEmpty() {
			assertEquals("",str.toUpperCase());
		}

	}
	
	// @BeforeAll, @AfterAll, @BeforeEach, @AfterEach of parent class are also available for Nested class tests
	@Nested // Grouping similar tests with @Nested annotation - Here all tests for an add operation
	class AddTest {
		@Test
		void testAddingTwoPositives() {
			assertEquals(2, Math.addExact(1, 1), 
					"Add method should return the sum of two numbers");
		}
		
		@Test
		void testAddingTwoNegatives() {
			assertEquals(-2, Math.addExact(-1, -1), 
					"Add method should return the sum of two numbers");
		}
		
		@Test
		void testAddingAPositiveAndANegative() {
			assertEquals(0, Math.addExact(-1, 1), 
					"Add method should return the sum of two numbers");
		}
	}
	
}
