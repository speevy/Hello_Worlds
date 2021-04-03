package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

public interface BaseGreetingsRepository {

	List<Greetings> findAll();

	List<Greetings> findByIdLessThan(Long maxId);

}
