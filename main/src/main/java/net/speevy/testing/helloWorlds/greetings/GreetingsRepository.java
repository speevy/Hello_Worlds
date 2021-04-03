package net.speevy.testing.helloWorlds.greetings;

import net.speevy.utils.IndependentCrudRepository;

public interface GreetingsRepository extends IndependentCrudRepository<Greetings, Long>, BaseGreetingsRepository {
	

}
