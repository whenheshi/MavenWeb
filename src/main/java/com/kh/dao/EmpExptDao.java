package com.kh.dao;

import java.util.List;

import com.kh.model.EmpExpt;
import com.kh.vo.EmpExptQuery;

public interface EmpExptDao {
	
	public List<EmpExpt> findEmpExpt(EmpExptQuery query);
	
	public Integer findEmpExptCount(EmpExptQuery query);
	
	public int insertEmpExptInfo(EmpExpt info);
	
	public String checkEmpExptExist(EmpExpt info);
	
}
