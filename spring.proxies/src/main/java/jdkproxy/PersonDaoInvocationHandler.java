package jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import dao.PersonDao;

public class PersonDaoInvocationHandler implements InvocationHandler {

	private PersonDao target;
	
	public PersonDaoInvocationHandler(PersonDao target) {
		this.target = target;
	}

	// Mechanism to avoid duplicate common code in each method
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("Before " + method.getName());
		Object result = method.invoke(target, args);
		System.out.println("After " + method.getName());
		return result;
	}

}
