package manualproxy;

import dao.PersonDaoImpl;
import model.Person;

public class ManualRunner {

	public static void main(String[] args) {
		PersonDaoProxy proxy = new PersonDaoProxy (new PersonDaoImpl());
		
		Person person = proxy.findById(5);
		proxy.save(person);

	}

}
