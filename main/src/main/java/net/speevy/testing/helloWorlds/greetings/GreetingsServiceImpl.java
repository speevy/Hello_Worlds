package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GreetingsServiceImpl implements GreetingsService {
	private final GreetingsRepository repository;

	@Override
	public Optional<Greetings> getGreeting(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Collection<Greetings> findAll() {
		return repository.findAll();
	}

	@Override
	public Greetings save(Greetings greetings) {
		return repository.save(greetings);
	}

	@Override
	public Optional<Greetings> update(Greetings greetings) {
		return repository.update(greetings);
	}

	@Override
	public boolean delete(long id) {
		if (!repository.existsById(id)) {
			return false;
		}
		
		repository.deleteById(id);
		return true;
	}

}
