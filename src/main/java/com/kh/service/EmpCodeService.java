package com.kh.service;

import java.util.List;

import com.kh.model.EmpCode;
import com.kh.vo.EmpCodeQuery;

public interface EmpCodeService {
	/**
	 * 查询员工ID信息
	 * @param query
	 * @return
	 */
	public List<EmpCode> findEmpCodeDetail(EmpCodeQuery query);
	
	public int insertCodeInfo(EmpCode info);
}
