package com.kh.model;

import java.util.Date;

/**
 * Ա����Ϣ
 * @author Administrator
 *
 */
public class Employee {
	//����
	private String code;
	//����
	private String name;
	//��ְ����
	private Date onjob;
	//��ְ����
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
