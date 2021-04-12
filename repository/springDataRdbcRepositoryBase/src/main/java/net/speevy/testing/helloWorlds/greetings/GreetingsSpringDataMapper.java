package net.speevy.testing.helloWorlds.greetings;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.StreamSupport;

import org.mapstruct.*;

public interface GreetingsSpringDataMapper<T> {
	T toRepo(Greetings vo);
	
	@InheritInverseConfiguration
	Greetings toEntity(T repo);

	default List<Greetings> toEntityList (Iterable<T> repos) {
		return StreamSupport.stream(repos.spliterator(), false)
				.map(this::toEntity)
				.collect(toList());
	}
}
