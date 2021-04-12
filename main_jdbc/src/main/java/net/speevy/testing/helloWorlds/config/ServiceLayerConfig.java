package net.speevy.testing.helloWorlds.config;

import org.springframework.context.annotation.*;

import net.speevy.testing.helloWorlds.greetings.*;

@Configuration
public class ServiceLayerConfig {

	private final GreetingsRepository greetingsRepository;

	public ServiceLayerConfig(GreetingsRepositorySpringDataJdbc springDataRepository, GreetingsSpringDataMapperJdbc mapper) {
		super();
		this.greetingsRepository = new GreetingsRepositoryJdbc(springDataRepository, mapper);
	}

	@Bean
	GreetingsService greetingsService() {
		return new GreetingsServiceImpl(greetingsRepository);
	}
}
