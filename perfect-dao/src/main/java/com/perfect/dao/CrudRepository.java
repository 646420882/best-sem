package com.perfect.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by baizz on 2014-7-4.
 */
@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * <br>------------------------------<br>
     *
     * @param id
     * @return
     */
    T findOne(ID id);

    /**
     * 查询全部
     * <br>------------------------------<br>
     *
     * @return
     */
    Collection<T> findAll();

    /**
     * 按条件查询
     * <br>------------------------------<br>
     *
     * @param t
     * @param skip
     * @param limit
     * @return
     */
    Collection<T> find(T t, int skip, int limit);

    /**
     * 新增
     * <br>------------------------------<br>
     *
     * @param t
     */
    void insert(T t);

    /**
     * 批量新增
     * <br>------------------------------<br>
     *
     * @param entities
     */
    void insertAll(Collection<? extends T> entities);

    /**
     * 更新
     * <br>------------------------------<br>
     *
     * @param t
     */
    void update(T t);

    /**
     * 批量更新
     * <br>------------------------------<br>
     *
     * @param entities
     */
    void update(Collection<T> entities);

    /**
     * 按条件批量更新
     * <br>------------------------------<br>
     *
     * @param key
     * @param value1
     * @param value2
     */
    void updateMulti(String key, Object value1, Object value2);

    /**
     * 根据id删除
     * <br>------------------------------<br>
     *
     * @param id
     */
    void deleteById(ID id);

    /**
     * 批量删除
     * <br>------------------------------<br>
     *
     * @param ids
     */
    void deleteByIds(Collection<ID> ids);

    /**
     * 按条件删除
     * <br>------------------------------<br>
     *
     * @param t
     */
    void delete(T t);

    /**
     * 删除全部
     * <br>------------------------------<br>
     */
    void deleteAll();

}
