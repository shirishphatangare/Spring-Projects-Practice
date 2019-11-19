package manualproxy;

import dao.PersonDao;
import model.Person;

public class PersonDaoProxy implements PersonDao{
	
	private PersonDao persondao;
	
	public PersonDaoProxy(PersonDao persondao) {
		this.persondao = persondao;
	}
	
	// Notice similar logging code in each of the method below. Need some mechanism to avoid duplicate code in each method.
	// JDK proxy or cglib provide this mechanism.
	@Override
	public Person findById(int id) {
		System.out.println("Manual Proxy Operation before findById..");
		Person person = persondao.findById(id);
		System.out.println("Manual Proxy Operation after findById..");
		return person;
	}

	@Override
	public void save(Person person) {
		System.out.println("Manual Proxy Operation before save..");
		persondao.save(person);
		System.out.println("Manual Proxy Operation after save..");
	}

}
