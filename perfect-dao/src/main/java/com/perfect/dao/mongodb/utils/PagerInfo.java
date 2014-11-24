package com.perfect.dao.mongodb.utils;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class PagerInfo extends AbstractPager implements Serializable{
public PagerInfo() {
}
public PagerInfo(int pageNo, int pageSize, int totalCount) {
	super(pageNo, pageSize, totalCount);
}
public PagerInfo(int pageNo, int pageSize, int totalCount,List<?> list) {
	super(pageNo, pageSize, totalCount);
	this.list=list;
}
public int getFirstStation(){
	return  pageNo * pageSize;
}
public List<?> getList(){
	return list;
}
@SuppressWarnings("unused")
private List<?> list;
@SuppressWarnings("unchecked")
public void setList(List list) {
	this.list = list;
}
}
