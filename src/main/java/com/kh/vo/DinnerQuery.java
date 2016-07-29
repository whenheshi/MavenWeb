package com.kh.vo;

import java.util.Date;


/**
 * 晚餐查询封装类
 * @author Administrator
 *
 */
public class DinnerQuery extends PageQuery{
	//工号
	private String code;
	//姓名
	private String name;
	//部门
	private String dept;
	//开始日期
	private Date sdate;
	//结束日期
	private Date edate;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
}
