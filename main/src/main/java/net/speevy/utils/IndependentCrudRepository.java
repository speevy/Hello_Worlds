package net.speevy.utils;

import java.util.Optional;

import net.speevy.testing.helloWorlds.greetings.Greetings;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * This interface is based in SpringData CrudRepository, but aims to be implementation and
 * Framework independent.
 *
 * @param <T> The Entity type
 * @param <ID> The EntityId type
 */
public interface IndependentCrudRepository<T, ID> {
	
	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 *
	 * @param entity must not be {@literal null}.
	 * @return the saved entity; will never be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
	 * @see CrudRepository.save(S entity);
	 */
	<S extends T> S save(S entity);

	/**
	 * Saves all given entities.
	 *
	 * @param entities must not be {@literal null} nor must it contain {@literal null}.
	 * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
	 *         as the {@literal Iterable} passed as an argument.
	 * @throws IllegalArgumentException in case the given {@link Iterable entities} or one of its entities is
	 *           {@literal null}.
	 * @see CrudRepository.saveAll(Iterable<S> entities);
	 */
	<S extends T> Iterable<S> saveAll(Iterable<S> entities);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 * @see CrudRepository.findById(ID id)
	 */
	Optional<T> findById(ID id);

	/**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id must not be {@literal null}.
	 * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 * @see CrudRepository.existsById(ID id)
	 */
	boolean existsById(ID id);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 * @see CrudRepository.findAll()
	 */
	Iterable<T> findAll();

	/**
	 * Returns all instances of the type {@code T} with the given IDs.
	 * <p>
	 * If some or all ids are not found, no entities are returned for these IDs.
	 * <p>
	 * Note that the order of elements in the result is not guaranteed.
	 *
	 * @param ids must not be {@literal null} nor contain any {@literal null} values.
	 * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
	 *         {@literal ids}.
	 * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
	 * @see CrudRepository.findAllById(Iterable<ID> ids)
	 */
	Iterable<T> findAllById(Iterable<ID> ids);

	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities.
	 * @see CrudRepository.count()
	 */
	long count();

	/**
	 * Deletes the entity with the given id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
	 * @see CrudRepository.deleteById(ID id)
	 */
	void deleteById(ID id);

	/**
	 * Deletes a given entity.
	 *
	 * @param entity must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 * @see CrudRepository.delete(T entity)
	 */
	void delete(T entity);

	/**
	 * Deletes the given entities.
	 *
	 * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
	 * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
	 * @see CrudRepository.deleteAll(Iterable<? extends T> entities)
	 */
	void deleteAll(Iterable<? extends T> entities);

	/**
	 * Deletes all entities managed by the repository.
	 * @see CrudRepository.deleteAll()
	 */
	void deleteAll();

	ID extractId(T entity);
	
	default <S extends T> Optional<S> update (S entity) {
		assert(entity != null);
		final ID id = extractId(entity);
		if (id == null) {
			throw new IllegalArgumentException("Cannot update entity with null Id");
		}
		
		if (!existsById(id)) {
			return Optional.empty();
		}
		
		return Optional.of(save(entity));
	}


}
