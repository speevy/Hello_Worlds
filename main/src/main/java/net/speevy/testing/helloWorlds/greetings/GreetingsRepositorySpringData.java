package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.*;

@NoRepositoryBean
public interface GreetingsRepositorySpringData extends CrudRepository<Greetings, Long> {
	List<Greetings> findAll();
	
	@Query("select * from greetings where id < :maxId")
	List<Greetings> findByIdLessThan(Long maxId);
}
