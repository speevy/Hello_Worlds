package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;

public interface GreetingsRepositorySpringDataJdbc extends GreetingsRepositorySpringData {

	@Override
	@Query("select * from greetings where id < :maxId")
	List<GreetingsDB> findByIdLessThan(Long maxId);
}
