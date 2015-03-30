package com.xbd.erp.base.pojo.sys;

public class Page {
	
	private int pageSize ;//每页多少记录数
	private long pageNo ;//当前页
	private long start;//当前页起始记录数
	private long end ;//当前页最后记录数
	private Object data;//页面数据			
	private long startPage;//
	private long totalCount;//(totalCount + pageSize - 1)/pageSize
	private long totalPageCount;//共多少页
	
	public Page(long totalCount,long pageNo, int pageSize, Object data) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.data = data;
		this.pageNo=pageNo;
		if (totalCount % pageSize == 0){
			this.totalPageCount= totalCount / pageSize;
		}else{
			this.totalPageCount= totalCount / pageSize + 1;
		}
		this.setStart((pageNo-1)*pageSize+1);
		long end = totalCount - (pageNo-1)*pageSize < pageSize ? totalCount : pageNo*pageSize ;
		this.setEnd(end);
	}
	
	public long getTotalPageCount() {
		return this.totalPageCount;
	}
	
	public void setTotalPageCount(long totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getStart() {
		return start ;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getStartPage() {
		return startPage;
	}

	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
}
