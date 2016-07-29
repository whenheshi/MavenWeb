package com.kh.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kh.model.Employee;
import com.kh.util.DateUtils;

/**
 * 事业部考勤数据封装
 * @author Administrator
 *
 */
public class OrgAttModel {
	//运营中心名称
	private String name;
	//中心员工列表
	private List<Employee> users = new ArrayList<Employee>();
	//打卡明细数据
	private List<AttendanceData> data = new ArrayList<AttendanceData>();
	//异常签到人次数
	private List<String> specials = new ArrayList<String>();
	//工作日列表
	private List<String> workDays = new ArrayList<String>();
	
	
	/**
	 * 获取运营中心实际打卡数量
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
	 * 获取人数
	 * @return
	 */
	public int getUserCount(){
		return this.getUsers().size();
	}
	
	/**
	 * 获取应签到次数
	 * @return
	 */
	public int getShouldCheckCount(){
		//异常打卡数据
		int expDays = null!=getSpecials()?getSpecials().size():0;
		//应打卡天数
		int workDayCount = null!=getWorkDays()?getWorkDays().size():0;
		//员工离职、入职去除天数
		Date begin = DateUtils.parseDate(getWorkDays().get(0), "yyyy-MM-dd");
		Date end = DateUtils.parseDate(getWorkDays().get(getWorkDays().size()-1), "yyyy-MM-dd");
		int expEmployee = 0;
		
		for(Employee employee:users){
			Date onjob = employee.getOnjob();
			Date leavejob = employee.getLeavejob();
			//入职日和离职日期都为空
			if(null==onjob && null==leavejob){
				continue;
			}
			//入职日和离职日不在统计范围内
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
		
		//请假天数
		int leave = 0;
		for(AttendanceData temp : data){
			if(null!=temp.getExpData() && temp.getExpData().isAllDay()){
				leave++;
			}
		}
		
		return (workDayCount*getUserCount()*2 - expDays*2 - expEmployee*2 -leave*2);
	}
	
	/**
	 * 获取签到率
	 * @return
	 */
	public String getChectRate(){
		//实际签到次数
		BigDecimal actualCount = new BigDecimal(getActualCheckCount());
		
		//应打卡次数 =打卡天数*部门人数*2 - 异常打卡数据*2
		BigDecimal maxCount = new BigDecimal(getShouldCheckCount());
		if(maxCount.compareTo(new BigDecimal(0))==0){
			return "0";
		}
		
		//签到率=(实际打卡次数/应打卡次数)*100%
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
