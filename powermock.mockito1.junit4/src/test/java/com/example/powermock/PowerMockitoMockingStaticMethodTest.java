package com.example.powermock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UtilityClass.class /*The class with static method to be mocked*/})
public class PowerMockitoMockingStaticMethodTest {

	@Mock
	Dependency dependencyMock;	// Actual mock

	@InjectMocks
	SystemUnderTest systemUnderTest; // Wrapper for a mock

	@Test
	public void powerMockito_MockingAStaticMethodCall() {

		when(dependencyMock.retrieveAllStats()).thenReturn(
				Arrays.asList(1, 2, 3));

		// Enable static mocking for all methods of a UtilityClass
		PowerMockito.mockStatic(UtilityClass.class);

		when(UtilityClass.staticMethod(anyLong())).thenReturn(150);

		assertEquals(150, systemUnderTest.methodCallingAStaticMethod());

		//To verify a specific method call
		//First : Call PowerMockito.verifyStatic() 
		//Second : Call the method to be verified
		
		// Verifies certain behavior happened once 
        // Alias to verifyStatic(times(1)) 
		PowerMockito.verifyStatic();
		UtilityClass.staticMethod(1 + 2 + 3);

		// verify exact number of calls
		//PowerMockito.verifyStatic(Mockito.times(2));

	}
}