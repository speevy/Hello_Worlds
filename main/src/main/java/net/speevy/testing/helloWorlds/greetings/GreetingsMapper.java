package net.speevy.testing.helloWorlds.greetings;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GreetingsMapper {
	GreetingsDTO toDto(Greetings vo);
	
	@InheritInverseConfiguration
	Greetings toVo(GreetingsDTO dto);
}
