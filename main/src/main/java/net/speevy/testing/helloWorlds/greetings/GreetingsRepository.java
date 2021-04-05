package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import net.speevy.utils.IndependentCrudRepository;

public interface GreetingsRepository extends IndependentCrudRepository<GreetingsEntity, Long> {
	
	List<GreetingsEntity> findAll();

	List<GreetingsEntity> findByIdLessThan(Long maxId);


}
