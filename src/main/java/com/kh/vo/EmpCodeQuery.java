package com.kh.vo;


/**
 * Ա��ID��װ��
 * @author Administrator
 *
 */
public class EmpCodeQuery extends PageQuery{
	//����
	private String name;
	//����
	private String code;
	//����ID
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
