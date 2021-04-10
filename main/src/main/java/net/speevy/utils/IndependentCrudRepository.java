package net.speevy.utils;

import java.util.Optional;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * This interface is based in SpringData CrudRepository, but aims to be implementation and
 * Framework independent. It also has been simplified
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
	T save(T entity);

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
	default void delete(T entity) {
		deleteById(extractId(entity));
	}

	ID extractId(T entity);
	
	default Optional<T> update (T entity) {
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
