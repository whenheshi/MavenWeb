package com.kh.vo;

import java.util.Date;


/**
 * ��Ͳ�ѯ��װ��
 * @author Administrator
 *
 */
public class DinnerQuery extends PageQuery{
	//����
	private String code;
	//����
	private String name;
	//����
	private String dept;
	//��ʼ����
	private Date sdate;
	//��������
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
