package com.kh.vo;


/**
 * 员工ID封装类
 * @author Administrator
 *
 */
public class EmpCodeQuery extends PageQuery{
	//姓名
	private String name;
	//工号
	private String code;
	//钉钉ID
	private String id;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
