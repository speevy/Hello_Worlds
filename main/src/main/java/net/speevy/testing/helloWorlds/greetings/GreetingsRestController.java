package net.speevy.testing.helloWorlds.greetings;

import static org.springframework.http.HttpStatus.*;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
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

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody GreetingsDTO dto) {
		
		Greetings toSave = mapper.toVo(id, dto);
		
		if (service.save(toSave).isPresent()) {
			return new ResponseEntity<Void>(OK);
		} else {
			return new ResponseEntity<Void>(NOT_FOUND);
		}
	}
}
