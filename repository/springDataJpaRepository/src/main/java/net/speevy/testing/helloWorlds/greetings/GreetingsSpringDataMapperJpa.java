package net.speevy.testing.helloWorlds.greetings;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GreetingsSpringDataMapperJpa extends GreetingsSpringDataMapper<GreetingsJpa> {

}
