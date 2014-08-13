package com.perfect.dao;

import java.util.Collection;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public interface StructureDataImportDAO<T> {

    public void save(T t);

    public void saveList(Collection<T> collection);


}
