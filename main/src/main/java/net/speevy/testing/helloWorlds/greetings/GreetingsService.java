package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

public interface GreetingsService {

	Optional<Greetings> save(Greetings greetings);

	Collection<Greetings> findAll();

	Optional<Greetings> getGreeting(Long id);

}
