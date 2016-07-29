package com.kh.vo;

/**
 * 每日考勤数据封装类
 * @author Administrator
 *
 */
public class AttendanceData {
	//工号
	private String code;
	//姓名
	private String name;
	//日期
	private String date;
	//签到类型
	private String checkType;
	//上班时间
	private String onjob;
	//下班时间
	private String leavejob;
	//当天请假信息
	private EmpExptData expData;
	
	//获取有效打卡次数
	public int getCheckCount(){
		//签到类型非空，则为异常签到
		if(null!=getCheckType() && !"".equals(getCheckType())){
			return 0;
		}
		int count = 0;
		if(isCome())
			count ++;
		if(isGo())
			count ++;
		return count;
	}
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	//是否上班打卡
	public boolean isCome() {
		if(null==getExpData()){
			//没有请假
			if(null!=getOnjob() && getOnjob().compareTo("12:00:00")<=0){
				return true;
			}
		}else{
			if(null == getOnjob()){
				return false;
			}else if(getExpData().isAllDay()){
				return false;
			}else{
				//半天假校验打两次卡即可
				if(getOnjob().compareTo(getLeavejob())<0){
					return true;
				}
			}
		}
		return false;
	}
	//是否下班打卡
	public boolean isGo() {
		if(null==getExpData()){
			//没有请假
			if(null!=getLeavejob() && getLeavejob().compareTo("12:00:00")>0){
				return true;
			}
		}else{
			if(null == getLeavejob()){
				return false;
			}else if(getExpData().isAllDay()){
				return false;
			}else{
				//半天假校验打两次卡即可
				if(getOnjob().compareTo(getLeavejob())<0){
					return true;
				}
			}
		}
		return false;
	}
	//返回当天考勤异常类型
	public String getCheckType() {
		if (null != checkType && !"".equals(checkType)) {
			return checkType;
		} else if (null != getExpData() && getExpData().isAllDay()) {
			return getExpData().getType();
		}
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getOnjob() {
		return onjob;
	}

	public void setOnjob(String onjob) {
		this.onjob = onjob;
	}

	public String getLeavejob() {
		return leavejob;
	}

	public void setLeavejob(String leavejob) {
		this.leavejob = leavejob;
	}

	public EmpExptData getExpData() {
		return expData;
	}

	public void setExpData(EmpExptData expData) {
		this.expData = expData;
	}
}
