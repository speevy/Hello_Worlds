package net.speevy.testing.helloWorlds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import net.speevy.testing.helloWorlds.greetings.*;

@DataJdbcTest
public class GreetingsRepositoryTest {

	@Autowired GreetingsRepository repository;
	
	@Test
	void testFindAll() {
		insertGreetings();
		
		List<Greetings> result = repository.findAll();
		
		assertTrue(result.stream().anyMatch(g -> "Hello World!".equals(g.getMessage())));
		assertTrue(result.stream().anyMatch(g -> "Hi World!".equals(g.getMessage())));
 	}

	private void insertGreetings() {
		Greetings greetings1 = new Greetings(null, "Hello World!");
		Greetings greetings2 = new Greetings(null, "Hi World!");
		
		repository.save(greetings1);
		repository.save(greetings2);
		
		assertThat(greetings1.getId()).isNotNull();
		assertThat(greetings2.getId()).isNotNull();
	}

	@Test
	void testUpdateExisting() {
		testUpdateExisting(repository::update);
 	}

	@Test
	void testSaveExisting() {
		testUpdateExisting(repository::save);
 	}
	
	private void testUpdateExisting(Consumer<Greetings> updater) {
		insertGreetings();

		List<Greetings> allGreetings = repository.findAll();

		assertFalse(allGreetings.isEmpty());
		
		Greetings greetings1 = allGreetings.get(0);
		greetings1.setMessage("Hola món!");
		updater.accept(greetings1);
		
		List<Greetings> result = repository.findAll();
		
		assertTrue(result.stream().anyMatch(g -> "Hola món!".equals(g.getMessage())));
		assertTrue(result.stream().anyMatch(g -> "Hi World!".equals(g.getMessage())));
		assertFalse(result.stream().anyMatch(g -> "Hello World!".equals(g.getMessage())));
 	}

	@Test
	void testUpdateNotExisting() {
		insertGreetings();

		List<Greetings> allGreetings = repository.findAll();

		assertFalse(allGreetings.isEmpty());
		
		Long maxId = allGreetings.stream().map(Greetings::getId).reduce(0L, Math::max);

		Optional<Greetings> result = repository.update(new Greetings(maxId + 1L, "Test"));
		
		assertTrue(result.isEmpty());
 	}

	
	@Test
	void testUpdateNullId() {
		assertThrows(IllegalArgumentException.class, () -> {
			repository.update(new Greetings(null, "Test")); 
		});
 	}

	
	@Test
	void testFindById() {
		Greetings greetings1 = new Greetings(null, "Hello World!");
		Greetings greetings2 = new Greetings(null, "Hi World!");
		
		repository.save(greetings1);
		repository.save(greetings2);
		
		
		Optional<Greetings> result1 = repository.findById(greetings1.getId());
		Optional<Greetings> result2 = repository.findById(greetings2.getId());
		Optional<Greetings> result3 = repository.findById(Math.max(greetings1.getId(), greetings2.getId()) + 1L);
		
		assertTrue (result1.isPresent());
		assertTrue (result2.isPresent());
		assertTrue (result3.isEmpty());
		
		assertEquals (greetings1, result1.get());
		assertEquals (greetings2, result2.get());
 	}

}
