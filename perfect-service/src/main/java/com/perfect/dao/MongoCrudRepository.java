package com.perfect.dao;

import com.perfect.mongodb.utils.Pager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-4.
 */
@NoRepositoryBean
public interface MongoCrudRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    public final String START = "start";
    public final String PAGESIZE = "pageSize";


    /**
     * 按条件查询
     * <br>------------------------------<br>
     *
     * @param params
     * @param skip
     * @param limit
     * @return
     */
    List<T> find(Map<String, Object> params, int skip, int limit);

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
    void insertAll(List<T> entities);

    /**
     * 更新
     * <br>------------------------------<br>
     *
     * @param t
     */
    void update(T t);
//
//    /**
//     * 批量更新
//     * <br>------------------------------<br>
//     *
//     * @param entities
//     */
//    void update(List<T> entities);

//    /**
//     * 根据id删除
//     * <br>------------------------------<br>
//     *
//     * @param id
//     */
//    void deleteById(ID id);
//

    /**
     * 批量删除
     * <br>------------------------------<br>
     *
     * @param ids
     */
    void deleteByIds(List<ID> ids);

    /**
     * @param start    开始页数
     * @param pageSize 每页数量
     * @param q        查询参数
     * @return
     */
    Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy);


}
