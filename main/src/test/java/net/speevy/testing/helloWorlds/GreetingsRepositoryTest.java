package net.speevy.testing.helloWorlds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import net.speevy.testing.helloWorlds.greetings.*;

@DataJdbcTest
public class GreetingsRepositoryTest {

	@Autowired GreetingsRepository repository;
	
	@Test
	void testGreetingsRepository() {
		Greetings greetings1 = new Greetings(null, "Hello World!");
		Greetings greetings2 = new Greetings(null, "Hi World!");
		
		repository.save(greetings1);
		repository.save(greetings2);
		
		assertThat(greetings1.getId()).isNotNull();
		assertThat(greetings2.getId()).isNotNull();
		
		greetings1.setMessage("Hola món!");
		
		repository.save(greetings1);
		
		List<Greetings> result = repository.findAll();
		
		assertTrue(result.stream().anyMatch(g -> "Hola món!".equals(g.getMessage())));
		assertTrue(result.stream().anyMatch(g -> "Hi World!".equals(g.getMessage())));
		assertFalse(result.stream().anyMatch(g -> "Hello World!".equals(g.getMessage())));
 	}
}
