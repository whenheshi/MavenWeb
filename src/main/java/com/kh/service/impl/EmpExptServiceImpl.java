package com.kh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dao.EmpExptDao;
import com.kh.model.EmpExpt;
import com.kh.service.EmpExptService;
import com.kh.vo.EmpExptQuery;

@Service
public class EmpExptServiceImpl implements EmpExptService {
	@Autowired
	private EmpExptDao empExptDao;

	@Override
	public List<EmpExpt> findEmpExptDetail(EmpExptQuery query) {
		if(query.getPageSize()<1000){
			//分页查询时查询总记录数
			query.setTotalCount(empExptDao.findEmpExptCount(query));
		}
		return empExptDao.findEmpExpt(query);
	}

	@Override
	public int insertExptInfo(EmpExpt info) {
		String exist = empExptDao.checkEmpExptExist(info);
		if(null!=exist && "1".equals(exist)){
			return 0;
		}
		return empExptDao.insertEmpExptInfo(info);
	}

}
