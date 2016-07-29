package com.kh.vo;

import java.util.Date;

/**
 * Ա����ٷ�װ��
 * @author Administrator
 *
 */
public class EmpExptData {
	//����
	private String code;
	//����
	private Date date;
	//����
	private String type;
	//�Ƿ�ȫ��
	private boolean allDay;
	//��ʼʱ��
	private String start;
	//����ʱ��
	private String end;
	
	//�����Ƿ����
	public boolean isAmLeave(){
		if(null!=start && start.compareTo("12:00:00")<=0){
			return true;
		}
		return false;
	}
	//�����Ƿ����
	public boolean isPmLeave(){
		if(null!=end && end.compareTo("12:00:00")>0){
			return true;
		}
		return false;
	}
	//�Ƿ�ȫ�����
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
