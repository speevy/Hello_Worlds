package net.speevy.testing.helloWorlds.greetings;

import org.mapstruct.*;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GreetingsMapper {
	@Mapping(source = "message", target = "hello")
	GreetingsDTO toDto(Greetings vo);
	
	@Mapping(target = "id", ignore = true)
	@InheritInverseConfiguration
	Greetings toVo(GreetingsDTO dto);
}
