package net.speevy.testing.helloWorlds.greetings;

public class GreetingsRepositoryJdbc extends GreetingsRepositoryImpl<GreetingsJdbc> {

	public GreetingsRepositoryJdbc(GreetingsRepositorySpringDataJdbc repo,
			GreetingsSpringDataMapperJdbc mapper) {
		super(repo, mapper);
	}

	@Override
	protected Long getId(GreetingsJdbc dbObj) {
		return dbObj.getId();
	}

}
