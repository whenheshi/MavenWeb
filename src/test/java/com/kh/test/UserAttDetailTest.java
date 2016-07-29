package com.kh.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kh.model.UserAttDetail;
import com.kh.service.UserAttDetailService;
import com.kh.util.DateUtils;

public class UserAttDetailTest {
	private UserAttDetailService userAttDetailService;
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext-core.xml"
				,"classpath:applicationContext-mybatis.xml"});
		userAttDetailService = (UserAttDetailService) context.getBean("userAttDetailServiceImpl");
	}
	
	@Test
	public void addUserAttDetail(){
		UserAttDetail detail = new UserAttDetail();
		detail.setName("姚飞平");
		detail.setDept("营销中心-华南运营中心-广东科华");
		detail.setJob("总经理");
		detail.setCode("01050933625232");
		detail.setDate(DateUtils.parseDate("20160401"));
		detail.setTime(DateUtils.parseDate("20160401"));
		detail.setAddr("桂城街道中海·万锦豪园");
		detail.setDetail("广东省佛山市南海区桂城街道中海·万锦豪园");
		detail.setMac("928ed9844c12adaa27fbd83da721ee");
		int count = userAttDetailService.insertDetail(detail);
		System.out.println(count);
	}
}
