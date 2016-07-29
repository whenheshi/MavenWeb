package com.kh.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dao.UserAttDetailDao;
import com.kh.model.UserAttDetail;
import com.kh.service.UserAttDetailService;
import com.kh.vo.UserAttDetailQuery;

@Service
public class UserAttDetailServiceImpl implements UserAttDetailService {
	@Autowired
	private UserAttDetailDao userAttDetailDao;

	@Override
	public int insertDetail(UserAttDetail detail) {
		String exist = userAttDetailDao.checkIsExist(detail);
		if(exist!=null && "1".equals(exist)){
			return 0;
		}
		return userAttDetailDao.insertUserAttDetail(detail);
	}
	
	@Override
	public List<UserAttDetail> findUserAttDetail(UserAttDetailQuery query){
		return userAttDetailDao.findUserAttDetail(query);
	}

	@Override
	public Integer findUserAttDetailCount(UserAttDetailQuery query) {
		return userAttDetailDao.findUserAttCount(query);
	}

	@Override
	public Date findLastUpdateDate() {
		return userAttDetailDao.findLastUpdateDate();
	}

}
