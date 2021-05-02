package net.speevy.testing.helloWorlds.config;

import org.springframework.context.annotation.*;

import net.speevy.testing.helloWorlds.greetings.*;

@Configuration
public class ServiceLayerConfig {

	private final GreetingsRepository greetingsRepository;

	public ServiceLayerConfig(GreetingsRepositorySpringDataJpa springDataRepository, GreetingsSpringDataMapperJpa mapper) {
		super();
		this.greetingsRepository = new GreetingsRepositoryJpa(springDataRepository, mapper);
	}

	@Bean
	GreetingsService greetingsService() {
		return new GreetingsServiceImpl(greetingsRepository);
	}
}
