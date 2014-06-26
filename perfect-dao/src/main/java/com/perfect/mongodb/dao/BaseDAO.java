package com.perfect.mongodb.dao;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public interface BaseDAO<T> {

    /**
     * 新增
     * <br>------------------------------<br>
     *
     * @param t
     */
    void insert(T t);

    /**
     * 新增
     * <br>------------------------------<br>
     *
     * @param list
     */
    void insertAll(List<T> list);

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

    /**
     * 修改
     * <br>------------------------------<br>
     *
     * @param t
     */
    void updateById(T t);

    /**
     * 更新多条
     * <br>------------------------------<br>
     *
     * @param s source
     * @param d target
     */
    void update(T s, T d);

    /**
     * 根据主键查询
     * <br>------------------------------<br>
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * 查询全部
     * <br>------------------------------<br>
     *
     * @return
     */
    List<T> findAll();

    /**
     * 按条件查询
     * <br>------------------------------<br>
     *
     * @param t
     * @param skip
     * @param limit
     * @return
     */
    List<T> find(T t, int skip, int limit);

    /**
     * 根据条件查询出来后 在去修改
     * <br>------------------------------<br>
     *
     * @param q 查询条件
     * @param u 修改的值对象
     * @return
     */
    T findAndModify(T q, T u);

    /**
     * 查询出来后 删除
     * <br>------------------------------<br>
     *
     * @param t
     * @return
     */
    T findAndRemove(T t);

    /**
     * count
     * <br>------------------------------<br>
     *
     * @return
     */
    long count();
}
