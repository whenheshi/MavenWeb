package com.kh.vo;

import java.util.List;

/**
 * 签到信息查询封装类
 * @author Administrator
 *
 */
public class UserAttDetailQuery extends PageQuery{
	//姓名
	private String name;
	//部门
	private String dept;
	//日期开始
	private String dateBegin;
	//日期结束
	private String dateEnd;
	//员工列表
	private List<String> employees;
	
	public List<String> getEmployees() {
		return employees;
	}
	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}
	public String getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	
}
