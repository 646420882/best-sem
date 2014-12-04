package com.perfect.dao.base;


import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dto.BaseDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 14/11/25.
 */
public interface HeyCrudRepository<T extends BaseDTO, ID extends Serializable> extends MongoEntityConstants {

    public <E> Class<E> getEntityClass();


    public Class<T> getDTOClass();
    /**
     * Interface for generic CRUD operations on a repository for a specific type.
     *
     * @author Oliver Gierke
     * @author Eberhard Wolff
     */

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param dto
     * @return the saved entity
     */
    T save(T dto);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is (@literal null}.
     */
    Iterable<T> save(Iterable<T> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findOne(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    boolean exists(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    Iterable<T> findAll(Iterable<ID> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    boolean delete(ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is (@literal null}.
     */
    boolean delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is (@literal null}.
     */
    int delete(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    boolean deleteAll();


    public int deleteByIds(List<ID> ids);


    public List<T> find(Map<String, Object> params, int skip, int limit);

    public List<T> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc);

}