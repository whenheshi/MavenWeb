package com.kh.vo;

import java.util.List;

/**
 * ǩ����Ϣ��ѯ��װ��
 * @author Administrator
 *
 */
public class UserAttDetailQuery extends PageQuery{
	//����
	private String name;
	//����
	private String dept;
	//���ڿ�ʼ
	private String dateBegin;
	//���ڽ���
	private String dateEnd;
	//Ա���б�
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
