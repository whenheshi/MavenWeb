package com.kh.service.impl;

import org.springframework.stereotype.Service;

import com.kh.service.AttdenceCalculatorService;

@Service
public class AttdenceCalculatorServiceImpl implements AttdenceCalculatorService {

	@Override
	public String getNewName(String userName) {
		return "hello spring!"+userName;
	}

}
