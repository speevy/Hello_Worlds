package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GreetingsRepositoryImpl implements GreetingsRepository {
	
	private final GreetingsRepositorySpringData repo;
	private final GreetingsSpringDataMapper mapper;

	@Override
	public Long extractId(Greetings entity) {
		return entity.getId();
	}

	@Override
	public Greetings save(Greetings entity) {
		assert(entity != null);
		final GreetingsDB repoEntity = mapper.toRepo(entity);
		final GreetingsDB saved = repo.save(repoEntity);

		if (entity.getId() == null) {
			entity.setId(saved.getId());
		}
		
		return mapper.toEntity(saved);
	}

	@Override
	public Optional<Greetings> findById(Long id) {
		return repo.findById(id).map(mapper::toEntity);
	}

	@Override
	public boolean existsById(Long id) {
		return repo.existsById(id);
	}

	@Override
	public long count() {
		return repo.count();
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Greetings> findAll() {
		return mapper.toEntityList(repo.findAll());
	}

	@Override
	public List<Greetings> findByIdLessThan(Long maxId) {
		return mapper.toEntityList(repo.findByIdLessThan(maxId));
	}
	
}
