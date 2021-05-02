package net.speevy.testing.helloWorlds.greetings;

import java.util.List;

import org.springframework.data.jpa.repository.*;

public interface GreetingsRepositorySpringDataJpa extends GreetingsRepositorySpringData<GreetingsJpa>, JpaRepository<GreetingsJpa, Long> {

	@Query("from GreetingsJpa where id < :maxId")
	List<GreetingsJpa> findByIdLessThan(Long maxId);
}
