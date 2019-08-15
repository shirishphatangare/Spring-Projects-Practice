package com.example.unittesting.mock.business;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

//@RunWith(MockitoJUnitRunner.class) - Junit4 way
@ExtendWith(MockitoExtension.class) // Junit5 way -  Added to use @Mock and @Spy annotations
public class ListMockTest {

	List<String> mock = mock(List.class);

	@Test
	public void size_basic() {
		when(mock.size()).thenReturn(5);
		assertEquals(5, mock.size());
	}

	@Test
	public void returnDifferentValues() { // Multiple return values
		when(mock.size()).thenReturn(5).thenReturn(10);
		assertEquals(5, mock.size()); // First invocation returns 5, hence assertion succeeds
		assertEquals(10, mock.size()); // Second invocation returns 10, hence assertion succeeds
	}

	@Test
	@Disabled
	public void returnWithParameters() { // Specific Argument Matchers
		when(mock.get(0)).thenReturn("in28Minutes"); // Index specific stubbing of mock object
		assertEquals("in28Minutes", mock.get(0));
		assertEquals("in28Minutes", mock.get(1)); // get method is not stubbed for index 1, hence default value(null) is returned and this assertion fails!
		assertEquals(null, mock.get(1)); // This assertion succeeds!
	}

	@Test
	public void returnWithGenericParameters() { // Generic Argument Matchers
		when(mock.get(anyInt())).thenReturn("in28Minutes"); // Overloaded versions of anyInt() method exists for different data types

		assertEquals("in28Minutes", mock.get(0));
		assertEquals("in28Minutes", mock.get(1)); 
	}

	// Stubbing/mocking is done in mock object to return values
	// However, returning values may not always be required. In some cases we may need to verify that mocked object has called some method with some argument(s) some number of times. In such cases verification is used.
	
	@Test
	public void verificationBasics() {
		// SUT - System Under Test - Mock object may call some method inside system under test and we need to verify that
		// Assume that below code is executed in SUT
		String value1 = mock.get(0); 
		String value2 = mock.get(1);

		// verify cases
		verify(mock).get(0); // Verify that get(0) method is called on a mocked object
		verify(mock, times(2)).get(anyInt()); // Verify that get() method is called on a mocked object 2 times with any integer parameter
		verify(mock, atLeast(1)).get(anyInt());
		verify(mock, atLeastOnce()).get(anyInt());
		verify(mock, atMost(2)).get(anyInt());
		verify(mock, never()).get(2);
	}
	

	@Test
	public void argumentCapturing() { // How to capture an argument passed to a method call on a mocked object
		//SUT
		mock.add("SomeString");
		
		//Verification and argument capturing
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(mock).add(captor.capture());
		
		assertEquals("SomeString", captor.getValue()); // asserting if a captured argument is same as expected
	}
	
	
	@Test
	public void multipleArgumentCapturing() {
		//SUT
		mock.add("SomeString1");
		mock.add("SomeString2");
		
		//Verification
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		
		verify(mock, times(2)).add(captor.capture());
		
		List<String> allValues = captor.getAllValues();
		
		assertEquals("SomeString1", allValues.get(0));
		assertEquals("SomeString2", allValues.get(1));
	}
	
	
	// Multiple argument capturing using annotations
	@Captor
	ArgumentCaptor<String> captor;
	
	@Test
	public void multipleArgumentCapturingUsingAnnotation() {
		//SUT
		mock.add("SomeString1");
		mock.add("SomeString2");
		
		//Verification
		//ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		
		verify(mock, times(2)).add(captor.capture());
		
		List<String> allValues = captor.getAllValues();
		
		assertEquals("SomeString1", allValues.get(0));
		assertEquals("SomeString2", allValues.get(1));
	}
	
	@Mock
	ArrayList arrayListMock; 

	@Test
	public void mocking() { // mocking does not retain behavior(code) of original class
		//ArrayList arrayListMock = mock(ArrayList.class); // Alternative to @Mock annotation
		System.out.println(arrayListMock.get(0));//null
		System.out.println(arrayListMock.size());//0
		arrayListMock.add("Test");
		arrayListMock.add("Test2");
		System.out.println(arrayListMock.size());//0
		when(arrayListMock.size()).thenReturn(5); // Default values are returned till stubbing is done
		System.out.println(arrayListMock.size());//5
	}

	@Spy
	ArrayList arrayListSpy;
	
	@Test
	public void spying() { // spying by default retains behavior(code) of original class. In short, it uses real class
		//ArrayList arrayListSpy = spy(ArrayList.class); // use spy instead of mock - Alternative to @Spy annotation
		arrayListSpy.add("Test0");
		System.out.println(arrayListSpy.get(0));//Test0
		System.out.println(arrayListSpy.size());//1
		arrayListSpy.add("Test");
		arrayListSpy.add("Test2");
		System.out.println(arrayListSpy.size());//3
		
		when(arrayListSpy.size()).thenReturn(5); // stubbing is applied only to specific methods. Other methods retain original behavior
		System.out.println(arrayListSpy.size());//5
		
		arrayListSpy.add("Test4"); // This addition in ArrayList do not affect size output because size method is stubbed and hence lost original behavior
		System.out.println(arrayListSpy.size());//5
		
		verify(arrayListSpy).add("Test4"); // Verification fro spying works in same way as with mocking
	}

	
}
