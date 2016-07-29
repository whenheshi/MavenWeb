package com.kh.dao;

import java.util.List;

import com.kh.model.EmpCode;
import com.kh.vo.EmpCodeQuery;

public interface EmpCodeDao {
	
	public List<EmpCode> findEmpCode(EmpCodeQuery query);
	
	public Integer findEmpCodeCount(EmpCodeQuery query);
	
	public int insertEmpCode(EmpCode info);
	
	public String checkEmpCodeExist(EmpCode info);
	
}
