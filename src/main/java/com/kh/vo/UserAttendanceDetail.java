package com.kh.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Ա������ͳ�Ʒ�װ��
 * @author Administrator
 *
 */
public class UserAttendanceDetail {
	//��ҵ��
	private String org;
	//���´�
	private String branch;
	//����/id
	private String code;
	//����
	private String name;
	//����ϸ����
	private Map<String,AttendanceData> details = new HashMap<String,AttendanceData>();
	//ʵ�ʴ򿨴���
	public Integer attCount;
	//Ӧ�򿨴���
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
	 * ��Ӵ���ϸ
	 * @param detail
	 */
	public void addDetail(AttendanceData detail){
		details.put(detail.getDate(), detail);
	}
	
	/**
	 * ��ȡĳ��򿨴���
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
	 * ��ȡȫ���򿨴���
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
