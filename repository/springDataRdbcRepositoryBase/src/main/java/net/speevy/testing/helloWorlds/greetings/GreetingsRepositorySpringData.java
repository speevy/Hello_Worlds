package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import org.springframework.data.repository.*;


@NoRepositoryBean
public interface GreetingsRepositorySpringData <T> extends CrudRepository<T, Long> {

	List<T> findAll();
	
	List<T> findByIdLessThan(Long maxId);
}
