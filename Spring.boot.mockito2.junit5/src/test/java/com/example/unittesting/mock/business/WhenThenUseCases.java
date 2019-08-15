package com.example.unittesting.mock.business;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.AbstractList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WhenThenUseCases {

	@Test
	public void configure_simple_return_behavior() {
		MyList listMock = Mockito.mock(MyList.class);
		when(listMock.add(anyString())).thenReturn(false);
		boolean added = listMock.add("randomtext");
		assertThat(added, is(false));
	}
	
	
	@Test
	public void configure_return_behavior_alternative_way() {
		MyList listMock = Mockito.mock(MyList.class);
		doReturn(false).when(listMock).add(anyString());
		boolean added = listMock.add("randomtext");
		assertThat(added, is(false));
	}

	
	//configure mock to throw an exception on a method call
	@Test
	public void givenMethodIsConfiguredToThrowException_whenCallingMethod_thenExceptionIsThrown() {
		MyList listMock = Mockito.mock(MyList.class);
		when(listMock.add(anyString())).thenThrow(IllegalStateException.class);
	}
	
	
	@Test	
	public void configure_method_throw_exception() {
		MyList listMock = Mockito.mock(MyList.class);
		doThrow(NullPointerException.class).when(listMock).clear();
		assertThrows(NullPointerException.class,  
			() -> {   // With functional programming (Lambda) you can send a method as an argument
				listMock.clear();
			});
	}
	
	
	@Test
	public void configure_behavior_multiple_calls() {
		MyList listMock = Mockito.mock(MyList.class);
		when(listMock.add(anyString()))
		.thenReturn(false)
		.thenThrow(IllegalStateException.class);
	
		listMock.add("randomtext");
		assertThrows(IllegalStateException.class,  
			() -> {   // With functional programming (Lambda) you can send a method as an argument
				listMock.add("randomtext"); // will throw the exception
			});
	}
	
	
	@Test
	public void configure_behavior_of_spy() {
		MyList instance = new MyList();
		MyList spy = Mockito.spy(instance);
		doThrow(NullPointerException.class).when(spy).size();
	
		assertThrows(NullPointerException.class,  
			() -> {   // With functional programming (Lambda) you can send a method as an argument
				spy.size(); // will throw the exception
			});
	}
	
	
	@Test
	public void configure_call_real_underlying_method_on_mock() {
		MyList listMock = Mockito.mock(MyList.class);
		when(listMock.size()).thenCallRealMethod();
		assertThat(listMock.size(), equalTo(1)); // Method size() in class MyList returns 1
	}
}
	
	
class MyList extends AbstractList<String> {
	@Override
	public String get(final int index) {
		return null;
	}

	@Override
	public int size() {
		return 1;
	}
}
	
