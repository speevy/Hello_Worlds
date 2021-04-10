package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.*;

@NoRepositoryBean
public interface GreetingsRepositorySpringData extends CrudRepository<GreetingsDB, Long> {
	List<GreetingsDB> findAll();
	
	@Query("select * from greetings where id < :maxId")
	List<GreetingsDB> findByIdLessThan(Long maxId);
}
