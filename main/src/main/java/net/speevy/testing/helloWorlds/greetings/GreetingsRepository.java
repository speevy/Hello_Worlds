package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import net.speevy.utils.IndependentCrudRepository;

public interface GreetingsRepository extends IndependentCrudRepository<Greetings, Long> {
	
	List<Greetings> findAll();

	List<Greetings> findByIdLessThan(Long maxId);


}
