package com.kh.model;

/**
 * 员工部门信息
 * @author Administrator
 *
 */
public class EmpDept {
	//工号
	private String code;
	//事业部
	private String dept;
	//办事处
	private String branch;
	
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
}
