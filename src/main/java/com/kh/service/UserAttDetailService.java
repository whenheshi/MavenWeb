package com.kh.service;

import java.util.Date;
import java.util.List;

import com.kh.model.UserAttDetail;
import com.kh.vo.UserAttDetailQuery;

public interface UserAttDetailService {
	/**
	 * ���뿼����ϸ����
	 * @param detail
	 * @return
	 */
	public int insertDetail(UserAttDetail detail);
	
	public List<UserAttDetail> findUserAttDetail(UserAttDetailQuery query);
	
	public Integer findUserAttDetailCount(UserAttDetailQuery query);

	public Date findLastUpdateDate();
}
