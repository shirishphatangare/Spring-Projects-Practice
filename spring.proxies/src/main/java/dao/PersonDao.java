package dao;

import model.Person;

public interface PersonDao {

	Person findById(int id);
	void save(Person person);
	
}
