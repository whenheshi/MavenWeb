package com.kh.service;

import java.util.List;

import com.kh.model.Dinner;
import com.kh.vo.DinnerQuery;

public interface DinnerService {
	/**
	 * ��ѯ�����Ϣ
	 * @param query
	 * @return
	 */
	public List<Dinner> findDinnerDetail(DinnerQuery query) throws Exception;
}
