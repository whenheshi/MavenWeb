package com.kh.service;

import java.util.List;

import com.kh.model.EmpExpt;
import com.kh.vo.EmpExptQuery;

public interface EmpExptService {
	/**
	 * ��ѯԱ�������Ϣ
	 * @param query
	 * @return
	 */
	public List<EmpExpt> findEmpExptDetail(EmpExptQuery query);
	
	public int insertExptInfo(EmpExpt info);
}
