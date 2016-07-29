package com.kh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dao.EmpCodeDao;
import com.kh.model.EmpCode;
import com.kh.service.EmpCodeService;
import com.kh.vo.EmpCodeQuery;

@Service
public class EmpCodeServiceImpl implements EmpCodeService {
	@Autowired
	private EmpCodeDao empCodeDao;

	@Override
	public List<EmpCode> findEmpCodeDetail(EmpCodeQuery query) {
		if(query.getPageSize()<1000){
			query.setTotalCount(empCodeDao.findEmpCodeCount(query));
		}
		return empCodeDao.findEmpCode(query);
	}

	@Override
	public int insertCodeInfo(EmpCode info) {
		String exist = empCodeDao.checkEmpCodeExist(info);
		if(null!=exist && "1".equals(exist)){
			return 0;
		}
		return empCodeDao.insertEmpCode(info);
	}

}
