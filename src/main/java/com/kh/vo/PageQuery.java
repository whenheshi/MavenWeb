package com.kh.vo;

/**
 * ��ҳ��
 * @author Administrator
 *
 */
public class PageQuery{
	//ÿҳ��С
	private Integer pageSize = 20;
	//��ǰҳ
	private Integer currentPage = 1;
	//�ܼ�¼��
	private Integer totalCount = 0;
	
	public Integer getStart() {
		return (currentPage-1)*pageSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageCount() {
		Integer temp = totalCount%pageSize;
		if(temp.compareTo(0)>0){
			return (Integer)(totalCount/pageSize)+1;
		}
		return (Integer)(totalCount/pageSize);
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
