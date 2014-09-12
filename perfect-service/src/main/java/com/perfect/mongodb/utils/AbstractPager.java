package com.perfect.mongodb.utils;



public class AbstractPager{
	private static final long serialVersionUID = 1L;
	public static final int defCount=20;

    private int nextPage;

    private int prePage;

    private int totalPage;

	public AbstractPager() {
	}
	public AbstractPager(int pageNo,int pageSize,int totalCount){
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
        this.nextPage = getNextPage();
        this.prePage = getPrePage();
        this.totalPage = getTotalPage();
	}
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}
	public int getTotalCount() {
		return totalCount;
	}
	public int getTotalPage() {
		int totalPage=totalCount/pageSize;
		if (totalPage==0||totalCount%pageSize!=0) {
			totalPage++;
		}
		return totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public boolean isFirstPage() {
		return pageNo<=1;
	}
	public boolean isLastPage() {
		return pageNo>=getTotalPage();
	}
	public int getNextPage() {
		if (isLastPage()) {
			return getTotalPage();
		}else{
			return getPageNo()+1;
		}
	}
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		}
		else{
			return pageNo-1;
		}
	}

    protected int totalCount = 0;
	protected int pageSize = 20;
	protected int pageNo = 1;
	
	
	public void setTotalCount(int totalCount){
		if (totalCount<0) {
			this.totalCount=0;
		}else{
			this.totalCount=totalCount;
		}
	}
	public void setPageSize(int pageSize){
		if (pageSize<1) {
			this.pageSize=defCount;
		}else{
			this.pageSize=pageSize;
		}
	}
	public void setPageNo(int pageNo){
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}
}
