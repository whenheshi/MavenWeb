package com.kh.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kh.model.Employee;
import com.kh.util.DateUtils;

/**
 * ��ҵ���������ݷ�װ
 * @author Administrator
 *
 */
public class OrgAttModel {
	//��Ӫ��������
	private String name;
	//����Ա���б�
	private List<Employee> users = new ArrayList<Employee>();
	//����ϸ����
	private List<AttendanceData> data = new ArrayList<AttendanceData>();
	//�쳣ǩ���˴���
	private List<String> specials = new ArrayList<String>();
	//�������б�
	private List<String> workDays = new ArrayList<String>();
	
	
	/**
	 * ��ȡ��Ӫ����ʵ�ʴ�����
	 * @return
	 */
	public int getActualCheckCount(){
		int count = 0;
		for(AttendanceData temp : data){
			count+=temp.getCheckCount();
		}
		return count;
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public int getUserCount(){
		return this.getUsers().size();
	}
	
	/**
	 * ��ȡӦǩ������
	 * @return
	 */
	public int getShouldCheckCount(){
		//�쳣������
		int expDays = null!=getSpecials()?getSpecials().size():0;
		//Ӧ������
		int workDayCount = null!=getWorkDays()?getWorkDays().size():0;
		//Ա����ְ����ְȥ������
		Date begin = DateUtils.parseDate(getWorkDays().get(0), "yyyy-MM-dd");
		Date end = DateUtils.parseDate(getWorkDays().get(getWorkDays().size()-1), "yyyy-MM-dd");
		int expEmployee = 0;
		
		for(Employee employee:users){
			Date onjob = employee.getOnjob();
			Date leavejob = employee.getLeavejob();
			//��ְ�պ���ְ���ڶ�Ϊ��
			if(null==onjob && null==leavejob){
				continue;
			}
			//��ְ�պ���ְ�ղ���ͳ�Ʒ�Χ��
			if((null!=onjob && onjob.before(begin))&&(null!=leavejob && leavejob.after(end))){
				continue;
			}
			
			for(String dateStr:workDays){
				Date now = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
				if(null!=onjob && onjob.after(now)){
					expEmployee++;
				}else if(null!=leavejob && leavejob.before(now)){
					expEmployee++;
				}
			}
			
		}
		
		//�������
		int leave = 0;
		for(AttendanceData temp : data){
			if(null!=temp.getExpData() && temp.getExpData().isAllDay()){
				leave++;
			}
		}
		
		return (workDayCount*getUserCount()*2 - expDays*2 - expEmployee*2 -leave*2);
	}
	
	/**
	 * ��ȡǩ����
	 * @return
	 */
	public String getChectRate(){
		//ʵ��ǩ������
		BigDecimal actualCount = new BigDecimal(getActualCheckCount());
		
		//Ӧ�򿨴��� =������*��������*2 - �쳣������*2
		BigDecimal maxCount = new BigDecimal(getShouldCheckCount());
		if(maxCount.compareTo(new BigDecimal(0))==0){
			return "0";
		}
		
		//ǩ����=(ʵ�ʴ򿨴���/Ӧ�򿨴���)*100%
		BigDecimal rate = actualCount.multiply(new BigDecimal(100)).divide(maxCount, 2, BigDecimal.ROUND_HALF_UP);
		
		return rate.toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AttendanceData> getData() {
		return data;
	}
	public void setData(List<AttendanceData> data) {
		this.data = data;
	}

	public List<String> getSpecials() {
		return specials;
	}

	public void setSpecials(List<String> specials) {
		this.specials = specials;
	}

	public List<Employee> getUsers() {
		return users;
	}

	public void setUsers(List<Employee> users) {
		this.users = users;
	}

	public List<String> getWorkDays() {
		return workDays;
	}

	public void setWorkDays(List<String> workDays) {
		this.workDays = workDays;
	}
}
