package com.example.powermock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class) // Use the PowerMock JUnit runner
@PrepareForTest(Data.class) // specify the classes to prepare for testing using PowerMock (Here Data.class)
public class PowerMockitoMockingFinalMethodTest {
	
	@Test
	public void test_final_method_class() {

		//mock the final class.
		final  Data mock = PowerMockito.mock(Data.class);
		
		// Stub the behaviors 
		when(mock.reverse("CAT")).thenReturn("TAC");
		
		assertEquals("TAC", mock.reverse("CAT"));
		
	}
	
	
	
}
