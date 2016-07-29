package com.kh.service;

import java.util.Date;
import java.util.List;

import com.kh.model.Employee;
import com.kh.vo.EmployeeQuery;

public interface EmployeeService {
	/**
	 * 查询员工信息
	 * @param query
	 * @return
	 */
	public List<EmployeeQuery> findUserAttDetail(EmployeeQuery query);
	
	public int addEmployeeInfo(EmployeeQuery query);
	
	public void updateEmployeeInfo(EmployeeQuery query);

	public List<String> findOrgList();

	public List<String> findBranchByDept(String org);

	public List<Employee> findEmployeeByOrgAndBranch(String org, String branch);

	public List<Employee> findEmployeeByOrgAndDateRange(String org,
			String branch, Date begin, Date end);
}
