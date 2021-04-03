package net.speevy.testing.helloWorlds.greetings;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@Component
@RequiredArgsConstructor
public class GreetingsRepositoryImpl implements GreetingsRepository {
	
	@Delegate
	private final GreetingsRepositorySpringData repository;

	@Override
	public Long extractId(Greetings entity) {
		return entity.getId();
	}
	
}
