package com.kh.dao;

import java.util.Date;
import java.util.List;

import com.kh.model.UserAttDetail;
import com.kh.vo.UserAttDetailQuery;

public interface UserAttDetailDao {
	
	public int insertUserAttDetail(UserAttDetail detail);
	
	public String checkIsExist(UserAttDetail detail);
	
	public List<UserAttDetail> findUserAttDetail(UserAttDetailQuery query);

	public Integer findUserAttCount(UserAttDetailQuery query);

	public Date findLastUpdateDate();
	
}
