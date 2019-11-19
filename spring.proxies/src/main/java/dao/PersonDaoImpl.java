package dao;

import model.Person;

public class PersonDaoImpl implements PersonDao{

	@Override
	public Person findById(int id) {
		Person p = new Person();
		System.out.println("Finding Person By Id..");
		return p;
	}

	@Override
	public void save(Person person) {
		System.out.println("Saving a Person..");
	}

}
