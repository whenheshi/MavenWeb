package com.kh.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kh.model.Dinner;
import com.kh.service.DinnerService;
import com.kh.vo.DinnerQuery;

@Service
public class DinnerServiceImpl implements DinnerService {
	@Value("#{configProperties['jdbc_attdance_url']}")
	private String attdance_url;
	@Value("#{configProperties['jdbc_attdance_username']}")
	private String attdance_username;
	@Value("#{configProperties['jdbc_attdance_password']}")
	private String attdance_password;
	@Value("#{configProperties['jdbc_attdance_driverClassName']}")
	private String attdance_classname;

	@Override
	public List<Dinner> findDinnerDetail(DinnerQuery query) throws Exception{
		List<Dinner> result = new ArrayList<Dinner>();
		//��ѯ���ˢ������
		String dinnerSql = "select name,code,date,time from  table where 1=1 order by aa limit "+query.getStart()+","+query.getPageSize();
		List<Map<String,Object>> list = queryDatas(attdance_classname, attdance_url, attdance_username, attdance_password, dinnerSql);
		List<String> codes = new ArrayList<String>();
		for(Map<String,Object> map:list){
			String code = String.valueOf(map.get("code"));
			if(!codes.contains(code)){
				codes.add(code);
			}
		}
		//��ѯ�Է���Ա�Ŀ�������
		
		return result;
	}
	
	/**
	 * jdbc ��ѯ����
	 * @param className
	 * @param url
	 * @param name
	 * @param pwd
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryDatas(String className, String url, String name,
			String pwd, String sql) throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			List<Map<String,Object>> list = new ArrayList();
			// ����MySql��������
			Class.forName(className);
			con = DriverManager.getConnection(url, name, pwd);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData md;
			Map<String,Object> map;
			while (rs.next()) {
				map = new HashMap<String,Object>();
				md = rs.getMetaData();
				for(int i=1;i<=md.getColumnCount();i++){
					String colunmName = md.getColumnName(i);
					map.put(colunmName, rs.getObject(i));
				}
				list.add(map);
			}
			return list;
		} catch (ClassNotFoundException e) {
			throw new Exception("�Ҳ������������� ����������ʧ�ܣ�");
		} catch (SQLException se) {
			throw new Exception("���ݿ�����ʧ�ܣ�");
		} catch (Exception ep){
			throw ep;
		} finally {
			if (rs != null) { // �رռ�¼��
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) { // �ر�����
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) { // �ر����Ӷ���
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		DinnerServiceImpl test = new DinnerServiceImpl();
		String pwd = "kehua";
		String name = "root";
		String sql = "select a.code,a.name,a.dept,a.sdate,a.stime,a.createdate from employee_expt a  limit 0,20";
		String url = "jdbc:mysql://localhost:3306/attendance?useUnicode=true&amp;characterEncoding=utf-8";
		String className = "com.mysql.jdbc.Driver";
		List<Map<String,Object>> list = test.queryDatas(className, url, name, pwd, sql);
		for(Map<String,Object> map:list){
			System.out.println();
			for(String key:map.keySet()){
				System.out.print(key+":"+String.valueOf(map.get(key))+"|");
			}
		}
	}

}
