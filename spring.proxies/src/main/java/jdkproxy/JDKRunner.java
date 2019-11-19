package jdkproxy;

import java.lang.reflect.Proxy;

import dao.PersonDao;
import dao.PersonDaoImpl;
import model.Person;

public class JDKRunner {

	public static void main(String[] args) {
		
		PersonDao persondao = (PersonDao) Proxy.newProxyInstance(PersonDao.class.getClassLoader(), PersonDaoImpl.class.getInterfaces(), 
				new PersonDaoInvocationHandler(new PersonDaoImpl()));
		
		Person person = persondao.findById(5);
		persondao.save(person);
	}

}
