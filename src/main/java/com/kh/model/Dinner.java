package com.kh.model;

import java.util.Date;

import com.kh.util.DateUtils;


/**
 * ���ˢ����Ϣ
 * @author Administrator
 *
 */
public class Dinner {
	//����
	private String code;
	//����
	private String name;
	//����
	private String dept;
	//�ò�����
	private Date date;
	//�ò�ʱ��
	private Date time;
	//�°�ʱ��
	private Date checkout;
	//�Ƿ�Ϲ�
	private String flag;
	
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
	public Date getCheckout() {
		return checkout;
	}
	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}
	public String getFlag() {
		Date dinnerTime = DateUtils.parseDate(getDateStr()+" "+(DateUtils.isSummer(getDate())?"20:00":"19:30"), "yyyy-MM-dd HH:mm");
		if(null!=getCheckout() && !dinnerTime.after(getCheckout())){
			return "��";
		}
		return "��";
	}
	public String getClassName() {
		return getFlag()=="��"?"":"error";
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDateStr() {
		if(null==getDate())
			return "";
		return DateUtils.formatDate(getDate(), "yyyy-MM-dd");
	}
	public String getTimeStr() {
		if(null==getTime())
			return "";
		return DateUtils.formatDate(getTime(), "HH:mm");
	}
	public String getCheckoutStr() {
		if(null==getCheckout())
			return "";
		return DateUtils.formatDate(getCheckout(), "HH:mm");
	}
}
