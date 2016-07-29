package com.kh.model;

import java.util.Date;

import com.kh.util.DateUtils;


/**
 * 晚餐刷卡信息
 * @author Administrator
 *
 */
public class Dinner {
	//工号
	private String code;
	//姓名
	private String name;
	//部门
	private String dept;
	//用餐日期
	private Date date;
	//用餐时间
	private Date time;
	//下班时间
	private Date checkout;
	//是否合规
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
			return "是";
		}
		return "否";
	}
	public String getClassName() {
		return getFlag()=="是"?"":"error";
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
