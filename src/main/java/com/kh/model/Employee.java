package com.kh.model;

import java.util.Date;

/**
 * 员工信息
 * @author Administrator
 *
 */
public class Employee {
	//工号
	private String code;
	//姓名
	private String name;
	//入职日期
	private Date onjob;
	//离职日期
	private Date leavejob;
	
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
	public Date getOnjob() {
		return onjob;
	}
	public void setOnjob(Date onjob) {
		this.onjob = onjob;
	}
	public Date getLeavejob() {
		return leavejob;
	}
	public void setLeavejob(Date leavejob) {
		this.leavejob = leavejob;
	}
}
