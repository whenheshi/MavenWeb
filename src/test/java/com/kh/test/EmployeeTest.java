package com.kh.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kh.service.EmployeeService;
import com.kh.util.DateUtils;
import com.kh.vo.EmployeeQuery;

public class EmployeeTest {
	private EmployeeService employeeService;
	@Before
	public void before(){                                        
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext-core.xml"
				,"classpath:applicationContext-mybatis.xml"});
		employeeService = (EmployeeService) context.getBean("employeeServiceImpl");
	}
	
	@Test
	public void addEmployee(){
		EmployeeQuery query = new EmployeeQuery();
		query.setName("何时");
		query.setCode("KH06972aaa");
		query.setOnjob(DateUtils.parseDate("20160509"));
		query.setDept("一级机构");
		query.setBranch("");
		int count = employeeService.addEmployeeInfo(query);
		System.out.println(count);
	}
	
	@Test
	public void modifyEmployee(){
		EmployeeQuery query = new EmployeeQuery();
		query.setName("test");
		query.setCode("KH06972aaa");
		query.setOnjob(DateUtils.parseDate("20130101"));
		query.setLeavejob(DateUtils.parseDate("20160505"));
		query.setDept("科华恒盛");
		query.setBranch("");
		employeeService.updateEmployeeInfo(query);
	}
}
