package com.kh.vo;

import java.util.Date;
import java.util.List;

/**
 * Ա����ٷ�װ��
 * @author Administrator
 *
 */
public class EmpExptQuery extends PageQuery{
	//����
	private String name;
	//����
	private String code;
	//����
	private String dept;
	//��ʼ����
	private Date sdate;
	//��������
	private Date edate;
	//Ա���б�
	private List<String> employees;
	
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
	public List<String> getEmployees() {
		return employees;
	}
	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}
}
