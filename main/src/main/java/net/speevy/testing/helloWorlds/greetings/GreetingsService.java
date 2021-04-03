package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

public interface GreetingsService {

	void save(Greetings greetings);

	Collection<Greetings> findAll();

	Optional<Greetings> getGreeting(Long id);

}
