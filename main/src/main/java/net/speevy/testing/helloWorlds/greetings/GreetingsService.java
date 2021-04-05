package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

public interface GreetingsService {

	Greetings save(Greetings greetings);

	Collection<Greetings> findAll();

	Optional<Greetings> getGreeting(Long id);

	Optional<Greetings> update(Greetings greetings);

}
