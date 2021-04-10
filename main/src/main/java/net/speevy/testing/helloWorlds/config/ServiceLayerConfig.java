package net.speevy.testing.helloWorlds.config;

import org.springframework.context.annotation.*;

import net.speevy.testing.helloWorlds.greetings.*;

@Configuration
public class ServiceLayerConfig {

	private final GreetingsRepository greetingsRepository;

	public ServiceLayerConfig(GreetingsRepositorySpringData springDataRepository, GreetingsSpringDataMapper mapper) {
		super();
		this.greetingsRepository = new GreetingsRepositoryImpl(springDataRepository, mapper);
	}

	@Bean
	GreetingsService greetingsService() {
		return new GreetingsServiceImpl(greetingsRepository);
	}
}
