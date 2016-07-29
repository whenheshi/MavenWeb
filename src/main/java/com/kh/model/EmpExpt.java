package com.kh.model;

import java.util.Date;

import com.kh.util.DateUtils;

/**
 * 请假信息
 * @author Administrator
 *
 */
public class EmpExpt {
	//员工姓名
	private String name;
	//员工姓名
	private String code;
	//部门
	private String dept;
	//请假类型
	private String type;
	//开始日期
	private Date sdate;
	//开始时间
	private Date stime;
	//结束日期
	private Date edate;
	//结束时间
	private Date etime;
	//小时数
	private Double hours;
	//天数
	private Double days;
	//备注
	private String marks;
	//操作人
	private String operator;
	//操作日期
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
