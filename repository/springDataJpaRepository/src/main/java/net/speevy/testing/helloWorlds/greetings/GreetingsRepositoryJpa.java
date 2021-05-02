package net.speevy.testing.helloWorlds.greetings;

public class GreetingsRepositoryJpa extends GreetingsRepositoryImpl<GreetingsJpa> {

	public GreetingsRepositoryJpa(GreetingsRepositorySpringDataJpa repo,
			GreetingsSpringDataMapperJpa mapper) {
		super(repo, mapper);
	}

	@Override
	protected Long getId(GreetingsJpa dbObj) {
		return dbObj.getId();
	}

}
