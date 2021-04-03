package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GreetingsRepository extends CrudRepository<Greetings, Long> {
	
	List<Greetings> findAll();
}
