package net.speevy.testing.helloWorlds.greetings;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.StreamSupport;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GreetingsSpringDataMapper {
	Greetings toRepo(GreetingsEntity vo);
	
	@InheritInverseConfiguration
	GreetingsEntity toEntity(Greetings repo);

	default List<GreetingsEntity> toEntityList (Iterable<Greetings> repos) {
		return StreamSupport.stream(repos.spliterator(), false)
				.map(this::toEntity)
				.collect(toList());
	}
}
