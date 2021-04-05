package net.speevy.testing.helloWorlds.greetings;

import org.mapstruct.*;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GreetingsMapper {
	@Mapping(source = "message", target = "hello")
	GreetingsDTO toDto(GreetingsEntity vo);
	
	@Mapping(target = "id", ignore = true)
	@InheritInverseConfiguration
	GreetingsEntity toVo(GreetingsDTO dto);

	@Mapping(source = "dto.hello", target = "message")
	@Mapping(source = "id", target = "id")
	GreetingsEntity toVo(Long id, GreetingsDTO dto);
}
