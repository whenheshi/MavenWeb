package com.kh.vo;

import java.util.Date;
import java.util.List;

import com.kh.util.DateUtils;

/**
 * 员工查询封装类
 * @author Administrator
 *
 */
public class EmployeeQuery extends PageQuery{
	//姓名
	private String name;
	//工号
	private String code;
	private String dept;
	private String branch;
	private Date onjob;
	private Date leavejob;
	
	private List<String> empCodes;
	
	private List<String> branches;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public String getOnjobStr() {
		if(null==getOnjob())
			return "";
		return DateUtils.formatDate(getOnjob(), "yyyy-MM-dd");
	}
	public String getLeavejobStr() {
		if(null==getLeavejob())
			return "";
		return DateUtils.formatDate(getLeavejob(), "yyyy-MM-dd");
	}
	public List<String> getEmpCodes() {
		return empCodes;
	}
	public void setEmpCodes(List<String> empCodes) {
		this.empCodes = empCodes;
	}
	public List<String> getBranches() {
		return branches;
	}
	public void setBranches(List<String> branches) {
		this.branches = branches;
	}
}
