package net.speevy.testing.helloWorlds.greetings;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/test")
public class GreetingsRestController {

	private final GreetingsService service;
	private final GreetingsMapper mapper;
	
	@GetMapping()
	@ResponseBody
	public Collection<GreetingsDTO> helloWorld() {
		log.debug(()-> "Into HelloWorld");
		return service.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	@ResponseBody
	public String helloWorld(@PathVariable Long id) {
		return service.getGreeting(id)
				.map(mapper::toDto)
				.map(GreetingsDTO::getHello)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
	}

}
