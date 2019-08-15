package com.example.unittesting.mock.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.unittesting.mock.data.SomeDataService;

//@RunWith(MockitoJUnitRunner.class) - Junit4 way
@ExtendWith(MockitoExtension.class) // Junit5 way
public class SomeBusinessMockTest {

	@InjectMocks // Achieves steps 1 & 3 below - creates class objects and inject mocked dependencies
	SomeBusinessImpl business;

	@Mock // Achieves step 2 below - creates mocks
	SomeDataService dataServiceMock;
	
	// Without using @Mock and @InjectMocks annotations we need to do below steps --
	// 1) Create new instance of SomeBusinessImpl and assign it to a member variable
		// SomeBusinessImpl business = new SomeBusinessImpl(); 
	// 2) Create mock of SomeDataService using mock() function
		// SomeDataService dataServiceMock = mock(SomeDataService.class);
	// 3) Inject SomeDataService mock into SomeBusinessImpl using a setter method
		// business.setSomeDataService(dataServiceMock);

	@Test
	public void calculateSumUsingDataService_basic() {
		// when-thenReturn construct is used for stubbing of a mock object
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 1, 2, 3 }); // Alternative to SomeDataServiceStub from SomeDataServiceStub class
		assertEquals(6, business.calculateSumUsingDataService());
	}

	@Test
	public void calculateSumUsingDataService_empty() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] {}); // Alternative to SomeDataServiceEmptyStub from SomeDataServiceStub class
		assertEquals(0, business.calculateSumUsingDataService());
	}

	@Test
	public void calculateSumUsingDataService_oneValue() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 5 }); // Alternative to SomeDataServiceOneElementStub from SomeDataServiceStub class
		assertEquals(5, business.calculateSumUsingDataService());
	}
}
