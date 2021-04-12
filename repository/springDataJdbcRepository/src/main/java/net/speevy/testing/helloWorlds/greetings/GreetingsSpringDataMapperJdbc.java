package net.speevy.testing.helloWorlds.greetings;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GreetingsSpringDataMapperJdbc extends GreetingsSpringDataMapper<GreetingsJdbc> {

}
