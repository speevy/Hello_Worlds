package net.speevy.testing.helloWorlds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import java.util.*;
import java.util.function.Consumer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.ComponentScan.Filter;

import net.speevy.testing.helloWorlds.greetings.*;

@DataJdbcTest(includeFilters = {@Filter(type = ASSIGNABLE_TYPE, classes = {
		GreetingsRepository.class, 
		GreetingsSpringDataMapper.class
	})})
public class GreetingsRepositoryTest {

	@Autowired GreetingsRepository repository;
	
	@Test
	void testFindAll() {
		insertGreetings();
		
		List<Greetings> result = repository.findAll();
		
		assertTrue(result.stream().anyMatch(g -> "Hello World!".equals(g.getMessage())));
		assertTrue(result.stream().anyMatch(g -> "Hi World!".equals(g.getMessage())));
 	}

	private List<Greetings> insertGreetings() {
		Greetings greetings1 = new Greetings(null, "Hello World!");
		Greetings greetings2 = new Greetings(null, "Hi World!");
		
		Greetings saved1 = repository.save(greetings1);
		Greetings saved2 = repository.save(greetings2);
		
		assertThat(greetings1.getId()).isNotNull();
		assertThat(greetings2.getId()).isNotNull();
		assertThat(saved1.getId()).isNotNull();
		assertThat(saved2.getId()).isNotNull();

		return List.of(greetings1, greetings2);
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
		List<Greetings> inserted = insertGreetings();

		inserted.forEach(greetings -> {
			Optional<Greetings> result = repository.findById(greetings.getId());

			assertTrue (result.isPresent());
			assertEquals (greetings, result.get());
		});
		
		long max = inserted.stream().map(Greetings::getId).reduce(Long.MIN_VALUE, Math::max);
		
		assertTrue (repository.findById(max + 1).isEmpty());
 	}

	@Test
	void testFindByQuery() {
		Greetings greetings1 = new Greetings(null, "Hello World!");
		Greetings greetings2 = new Greetings(null, "Hi World!");
		
		repository.save(greetings1);
		repository.save(greetings2);
		
		List<Greetings> results = repository.findByIdLessThan(greetings2.getId());
		
		assertEquals(1, results.size());
		assertEquals (greetings1, results.get(0));
 	}

	Random rand = new Random();

	@RepeatedTest(10)
	void testDelete() {
		testDelete(repository::delete);
 	}

	@RepeatedTest(10)
	void testDeleteById() {
		testDelete(greetings -> repository.deleteById(greetings.getId()));
 	}

	private void testDelete(Consumer<Greetings> deleteAction) {
		List<Greetings> inserted = insertGreetings();
		
		Greetings toBeDeleted = inserted.get(rand.nextInt(inserted.size()));

		deleteAction.accept(toBeDeleted);
		
		inserted.forEach(greetings -> {
			Optional<Greetings> result = repository.findById(greetings.getId());

			if (toBeDeleted.equals(greetings)) {
				assertTrue (result.isEmpty());
			} else {
				assertTrue (result.isPresent());
				assertEquals (greetings, result.get());
			}
		});
 	}

}
