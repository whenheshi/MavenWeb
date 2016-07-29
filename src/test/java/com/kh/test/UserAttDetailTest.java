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
		detail.setName("Ҧ��ƽ");
		detail.setDept("Ӫ������-������Ӫ����-�㶫�ƻ�");
		detail.setJob("�ܾ���");
		detail.setCode("01050933625232");
		detail.setDate(DateUtils.parseDate("20160401"));
		detail.setTime(DateUtils.parseDate("20160401"));
		detail.setAddr("��ǽֵ��к��������԰");
		detail.setDetail("�㶫ʡ��ɽ���Ϻ�����ǽֵ��к��������԰");
		detail.setMac("928ed9844c12adaa27fbd83da721ee");
		int count = userAttDetailService.insertDetail(detail);
		System.out.println(count);
	}
}
