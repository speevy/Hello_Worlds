package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GreetingsServiceImpl implements GreetingsService {
	private final GreetingsRepository repository;

	@Override
	public Optional<GreetingsEntity> getGreeting(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Collection<GreetingsEntity> findAll() {
		return repository.findAll();
	}

	@Override
	public GreetingsEntity save(GreetingsEntity greetings) {
		return repository.save(greetings);
	}

	@Override
	public Optional<GreetingsEntity> update(GreetingsEntity greetings) {
		return repository.update(greetings);
	}

}
