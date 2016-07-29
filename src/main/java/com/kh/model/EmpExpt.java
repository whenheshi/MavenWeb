package com.kh.model;

import java.util.Date;

import com.kh.util.DateUtils;

/**
 * �����Ϣ
 * @author Administrator
 *
 */
public class EmpExpt {
	//Ա������
	private String name;
	//Ա������
	private String code;
	//����
	private String dept;
	//�������
	private String type;
	//��ʼ����
	private Date sdate;
	//��ʼʱ��
	private Date stime;
	//��������
	private Date edate;
	//����ʱ��
	private Date etime;
	//Сʱ��
	private Double hours;
	//����
	private Double days;
	//��ע
	private String marks;
	//������
	private String operator;
	//��������
	private Date createdate;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public Double getHours() {
		return hours;
	}
	public void setHours(Double hours) {
		this.hours = hours;
	}
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	public String getSdateStr(){
		if(null==getSdate())
			return "";
		return DateUtils.formatDate(getSdate(), "yyyy-MM-dd");
	}
	
	public String getStimeStr(){
		if(null==getStime())
			return "";
		return DateUtils.formatDate(getSdate(), "yyyy-MM-dd") +" "+ DateUtils.formatDate(getStime(), "HH:mm:ss");
	}
	
	public String getEdateStr(){
		if(null==getEdate())
			return "";
		return DateUtils.formatDate(getEdate(), "yyyy-MM-dd");
	}
	
	public String getEtimeStr(){
		if(null==getEtime())
			return "";
		return DateUtils.formatDate(getEdate(), "yyyy-MM-dd") +" "+ DateUtils.formatDate(getEtime(), "HH:mm:ss");
	}
	
	public String getCreatedateStr(){
		if(null==getCreatedate())
			return "";
		return DateUtils.formatDate(getCreatedate(), "yyyy-MM-dd");
	}
	
}
