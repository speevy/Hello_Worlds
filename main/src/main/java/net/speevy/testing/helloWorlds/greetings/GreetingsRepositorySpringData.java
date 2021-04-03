package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface GreetingsRepositorySpringData extends CrudRepository<Greetings, Long>, BaseGreetingsRepository {
	List<Greetings> findAll();
	
	@Override
	@Query("select * from greetings where id < :maxId")
	List<Greetings> findByIdLessThan(Long maxId);
}
