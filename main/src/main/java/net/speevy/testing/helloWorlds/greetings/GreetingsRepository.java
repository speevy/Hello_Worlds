package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

public interface GreetingsRepository extends CrudRepository<Greetings, Long> {
	
	List<Greetings> findAll();
	
	default Optional<Greetings> update (Greetings g) {
		assert(g != null);
		final Long id = g.getId();
		if (id == null) {
			throw new IllegalArgumentException("Cannot update Greetings with null Id");
		}
		
		if (!existsById(id)) {
			return Optional.empty();
		}
		
		return Optional.of(save(g));
	}
}
