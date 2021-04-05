package net.speevy.testing.helloWorlds.greetings;

import java.util.*;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GreetingsRepositoryImpl implements GreetingsRepository {
	
	private final GreetingsRepositorySpringData repo;
	private final GreetingsSpringDataMapper mapper;

	@Override
	public Long extractId(GreetingsEntity entity) {
		return entity.getId();
	}

	@Override
	public GreetingsEntity save(GreetingsEntity entity) {
		assert(entity != null);
		final Greetings repoEntity = mapper.toRepo(entity);
		final Greetings saved = repo.save(repoEntity);

		if (entity.getId() == null) {
			entity.setId(saved.getId());
		}
		
		return mapper.toEntity(saved);
	}

	@Override
	public Optional<GreetingsEntity> findById(Long id) {
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
	public List<GreetingsEntity> findAll() {
		return mapper.toEntityList(repo.findAll());
	}

	@Override
	public List<GreetingsEntity> findByIdLessThan(Long maxId) {
		return mapper.toEntityList(repo.findByIdLessThan(maxId));
	}
	
}
