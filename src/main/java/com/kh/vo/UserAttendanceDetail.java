package com.kh.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工考勤统计封装类
 * @author Administrator
 *
 */
public class UserAttendanceDetail {
	//事业部
	private String org;
	//办事处
	private String branch;
	//工号/id
	private String code;
	//姓名
	private String name;
	//打卡明细数据
	private Map<String,AttendanceData> details = new HashMap<String,AttendanceData>();
	//实际打卡次数
	public Integer attCount;
	//应打卡次数
	public int shouldCheckCount;
	
	public int shouldCheckDay;
	
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public Map<String, AttendanceData> getDetails() {
		return details;
	}
	public void setDetails(Map<String, AttendanceData> details) {
		this.details = details;
	}
	public int getShouldCheckCount() {
		int count = getShouldCheckDay();
		if(null!=details){
			AttendanceData temp;
			for(String key:details.keySet()){
				temp = details.get(key);
				if(null!=temp.getExpData()&&temp.getExpData().isAllDay()){
					count--;
				}
			}
		}
		return count*2;
	}
	/**
	 * 添加打卡明细
	 * @param detail
	 */
	public void addDetail(AttendanceData detail){
		details.put(detail.getDate(), detail);
	}
	
	/**
	 * 获取某天打卡次数
	 * @param date
	 * @return
	 */
	public int getAttDetail(String date){
		AttendanceData data = details.get(date);
		if(null!=data){
			return data.getCheckCount();
		}
		return 0;
	}
	
	/**
	 * 获取全部打卡次数
	 * @return
	 */
	public Integer getAttCount(){
		AttendanceData data ;
		int count = 0;
		for(String key : details.keySet()){
			data = details.get(key);
			count += data.getCheckCount();
		}
		return count;
	}
	public int getShouldCheckDay() {
		return shouldCheckDay;
	}
	public void setShouldCheckDay(int shouldCheckDay) {
		this.shouldCheckDay = shouldCheckDay;
	}
}
