package com.kh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dao.EmployeeDao;
import com.kh.model.Employee;
import com.kh.service.EmployeeService;
import com.kh.vo.EmployeeQuery;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public List<EmployeeQuery> findUserAttDetail(EmployeeQuery query) {
		if(query.getPageSize()<1000){
			query.setTotalCount(employeeDao.findEmployeeCount(query));
		}
		return employeeDao.findEmployee(query);
	}

	@Override
	public int addEmployeeInfo(EmployeeQuery query) {
		int count = 0;
		//插入员工明细
		String existEmployee = employeeDao.checkEmployeeExist(query);
		if(existEmployee==null || !"1".equals(existEmployee)){
			count += employeeDao.insertEmployeeInfo(query);
		}else if("1".equals(existEmployee)){
			updateEmployeeInfo(query);
		}
		String existDept = employeeDao.checkEmpDeptExist(query);
		if(existDept==null || !"1".equals(existDept)){
			count += employeeDao.insertEmployeeDept(query);
		}
		return count;
	}

	@Override
	public void updateEmployeeInfo(EmployeeQuery query) {
		employeeDao.updateEmployeeInfo(query);
		EmployeeQuery deptQuery = new EmployeeQuery();
		deptQuery.setCode(query.getCode());
		List<EmployeeQuery> depts  = employeeDao.findEmployee(deptQuery);
		if(null==depts || depts.size()==0){
			employeeDao.insertEmployeeDept(query);
		}else if(depts.size()==1){
			employeeDao.updateEmployeeDept(query);
		}
	}

	@Override
	public List<String> findOrgList() {
		return employeeDao.findOrgList();
	}

	@Override
	public List<String> findBranchByDept(String org) {
		return employeeDao.findBranchByDept(org);
	}

	@Override
	public List<Employee> findEmployeeByOrgAndBranch(String org, String branch) {
		EmployeeQuery query = new EmployeeQuery();
		if(null!=org && !"".equals(org)){
			query.setDept(org);
		}
		if(null!=branch && !"".equals(branch) && branch.indexOf(",")<0){
			query.setBranch(branch);
			query.setBranches(null);
		}else if(null!=branch && !"".equals(branch) && branch.indexOf(",")>-1){
			List<String> branches = new ArrayList<String>();
			String[] arr = branch.split(",");
			for(String temp:arr){
				if(null!=temp && !"".equals(temp)){
					branches.add(temp);
				}
			}
			query.setBranches(branches);
		}
		return employeeDao.findEmployeesByDeptAndBranch(query);
	}

	@Override
	public List<Employee> findEmployeeByOrgAndDateRange(String org,
			String branch, Date begin, Date end) {
		EmployeeQuery query = new EmployeeQuery();
		if(null!=org && !"".equals(org)){
			query.setDept(org);
		}
		if(null!=branch && !"".equals(branch) &&branch.indexOf(",")<0){
			query.setBranch(branch);
			query.setBranches(null);
		}else if(null!=branch && !"".equals(branch) && branch.indexOf(",")>-1){
			List<String> branches = new ArrayList<String>();
			String[] arr = branch.split(",");
			for(String temp:arr){
				if(null!=temp && !"".equals(temp)){
					branches.add(temp);
				}
			}
			query.setBranches(branches);
		}
		List<Employee> tempList = employeeDao.findEmployeesByDeptAndBranch(query);
		List<Employee> result = new ArrayList<Employee>();
		for(Employee emp : tempList){
			//入职日期在结束日期之后，或者离职日期在开始日期之前
			if((null != emp.getOnjob()&&emp.getOnjob().after(end))
					||(null!=emp.getLeavejob()&&emp.getLeavejob().before(begin))){
				//do nothing
			}else{
				result.add(emp);
			}
		}
		return result;
	}

}
