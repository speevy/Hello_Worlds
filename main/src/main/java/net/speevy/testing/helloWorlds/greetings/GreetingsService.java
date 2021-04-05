package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

public interface GreetingsService {

	GreetingsEntity save(GreetingsEntity greetings);

	Collection<GreetingsEntity> findAll();

	Optional<GreetingsEntity> getGreeting(Long id);

	Optional<GreetingsEntity> update(GreetingsEntity greetings);

}
