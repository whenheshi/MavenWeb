package com.kh.vo;

import java.util.Date;

/**
 * 员工请假封装类
 * @author Administrator
 *
 */
public class EmpExptData {
	//工号
	private String code;
	//日期
	private Date date;
	//类型
	private String type;
	//是否全天
	private boolean allDay;
	//开始时间
	private String start;
	//结束时间
	private String end;
	
	//上午是否请假
	public boolean isAmLeave(){
		if(null!=start && start.compareTo("12:00:00")<=0){
			return true;
		}
		return false;
	}
	//下午是否请假
	public boolean isPmLeave(){
		if(null!=end && end.compareTo("12:00:00")>0){
			return true;
		}
		return false;
	}
	//是否全天请假
	public boolean isAllDay() {
		return allDay || (isAmLeave()&&isPmLeave());
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
