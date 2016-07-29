package com.kh.model;

import java.util.Date;

import com.kh.util.DateUtils;

public class UserAttDetail {
	//姓名
	private String name;
	//部门
	private String dept;
	//职位
	private String job;
	//工号
	private String code;
	//日期
	private Date date;
	//时间
	private Date time;
	//地址
	private String addr;
	//详细地址
	private String detail;
	//备注
	private String memo;
	//手机设备号
	private String mac;
	
	private String dateStr;
	
	private String timeStr;
	
	private Date onjob;
	
	private Date leavejob;
	
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDateStr() {
		if(null==getDate())
			return "";
		return DateUtils.formatDate(getDate(), "yyyy-MM-dd");
	}
	public String getTimeStr() {
		if(null==getTime())
			return "";
		return DateUtils.formatDate(getTime(), "HH:mm:ss");
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
