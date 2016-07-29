package com.kh.dao;

import java.util.List;

import com.kh.model.Employee;
import com.kh.vo.EmployeeQuery;

public interface EmployeeDao {
	
	public List<EmployeeQuery> findEmployee(EmployeeQuery query);
	
	public Integer findEmployeeCount(EmployeeQuery query);
	
	public String checkEmployeeExist(EmployeeQuery query);
	
	public String checkEmpDeptExist(EmployeeQuery query);
	
	public int insertEmployeeInfo(EmployeeQuery query);
	
	public int insertEmployeeDept(EmployeeQuery query);
	
	public void updateEmployeeInfo(EmployeeQuery query);
	
	public void updateEmployeeDept(EmployeeQuery query);
	
	public List<String> findOrgList();

	public List<String> findBranchByDept(String org);
	
	public List<Employee> findEmployeesByDeptAndBranch(EmployeeQuery query);
}
