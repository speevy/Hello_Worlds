package net.speevy.testing.helloWorlds.greetings;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.StreamSupport;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GreetingsSpringDataMapper {
	GreetingsDB toRepo(Greetings vo);
	
	@InheritInverseConfiguration
	Greetings toEntity(GreetingsDB repo);

	default List<Greetings> toEntityList (Iterable<GreetingsDB> repos) {
		return StreamSupport.stream(repos.spliterator(), false)
				.map(this::toEntity)
				.collect(toList());
	}
}
