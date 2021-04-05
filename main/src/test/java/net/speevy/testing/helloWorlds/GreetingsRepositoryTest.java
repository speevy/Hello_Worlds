package net.speevy.testing.helloWorlds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import java.util.*;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
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
		
		List<GreetingsEntity> result = repository.findAll();
		
		assertTrue(result.stream().anyMatch(g -> "Hello World!".equals(g.getMessage())));
		assertTrue(result.stream().anyMatch(g -> "Hi World!".equals(g.getMessage())));
 	}

	private void insertGreetings() {
		GreetingsEntity greetings1 = new GreetingsEntity(null, "Hello World!");
		GreetingsEntity greetings2 = new GreetingsEntity(null, "Hi World!");
		
		GreetingsEntity saved1 = repository.save(greetings1);
		GreetingsEntity saved2 = repository.save(greetings2);
		
		assertThat(greetings1.getId()).isNotNull();
		assertThat(greetings2.getId()).isNotNull();
		assertThat(saved1.getId()).isNotNull();
		assertThat(saved2.getId()).isNotNull();

	}

	@Test
	void testUpdateExisting() {
		testUpdateExisting(repository::update);
 	}

	@Test
	void testSaveExisting() {
		testUpdateExisting(repository::save);
 	}
	
	private void testUpdateExisting(Consumer<GreetingsEntity> updater) {
		insertGreetings();

		List<GreetingsEntity> allGreetings = repository.findAll();

		assertFalse(allGreetings.isEmpty());
		
		GreetingsEntity greetings1 = allGreetings.get(0);
		greetings1.setMessage("Hola món!");
		updater.accept(greetings1);
		
		List<GreetingsEntity> result = repository.findAll();
		
		assertTrue(result.stream().anyMatch(g -> "Hola món!".equals(g.getMessage())));
		assertTrue(result.stream().anyMatch(g -> "Hi World!".equals(g.getMessage())));
		assertFalse(result.stream().anyMatch(g -> "Hello World!".equals(g.getMessage())));
 	}

	@Test
	void testUpdateNotExisting() {
		insertGreetings();

		List<GreetingsEntity> allGreetings = repository.findAll();

		assertFalse(allGreetings.isEmpty());
		
		Long maxId = allGreetings.stream().map(GreetingsEntity::getId).reduce(0L, Math::max);

		Optional<GreetingsEntity> result = repository.update(new GreetingsEntity(maxId + 1L, "Test"));
		
		assertTrue(result.isEmpty());
 	}

	
	@Test
	void testUpdateNullId() {
		assertThrows(IllegalArgumentException.class, () -> {
			repository.update(new GreetingsEntity(null, "Test")); 
		});
 	}

	
	@Test
	void testFindById() {
		GreetingsEntity greetings1 = new GreetingsEntity(null, "Hello World!");
		GreetingsEntity greetings2 = new GreetingsEntity(null, "Hi World!");
		
		repository.save(greetings1);
		repository.save(greetings2);
		
		
		Optional<GreetingsEntity> result1 = repository.findById(greetings1.getId());
		Optional<GreetingsEntity> result2 = repository.findById(greetings2.getId());
		Optional<GreetingsEntity> result3 = repository.findById(Math.max(greetings1.getId(), greetings2.getId()) + 1L);
		
		assertTrue (result1.isPresent());
		assertTrue (result2.isPresent());
		assertTrue (result3.isEmpty());
		
		assertEquals (greetings1, result1.get());
		assertEquals (greetings2, result2.get());
 	}

	@Test
	void testFindByQuery() {
		GreetingsEntity greetings1 = new GreetingsEntity(null, "Hello World!");
		GreetingsEntity greetings2 = new GreetingsEntity(null, "Hi World!");
		
		repository.save(greetings1);
		repository.save(greetings2);
		
		List<GreetingsEntity> results = repository.findByIdLessThan(greetings2.getId());
		
		assertEquals(1, results.size());
		assertEquals (greetings1, results.get(0));
 	}
	
}
