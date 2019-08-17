package com.in28minutes.unittesting.unittesting.data;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.unittesting.unittesting.model.Item;

@RunWith(SpringRunner.class)
@DataJpaTest // Here DataJpaTest annotation is creating Spring configuration relevant to JPA tests, reading from H2 DB (actual DB we are using) and executing below tests since we don't have data.sql in /src/test/resources
// However, if you are connecting real DB then data.sql should be moved to /src/test/resources folder and @DataJpaTest will take care not to hit 
// actual DB for tests but it will hit H2 DB and conduct test on H2 instead of actual DB.
public class ItemRepositoryTest {
	
	@Autowired
	private ItemRepository repository;
	
	@Test
	public void testFindAll() {
		List<Item> items = repository.findAll();
		assertEquals(3,items.size());
	}

	@Test
	public void testFindOne() {
		Item item = repository.findById(10001).get();
		assertEquals("Item1",item.getName());
	}

}
