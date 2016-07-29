package com.kh.web;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.service.EmployeeService;
import com.kh.util.DateUtils;
import com.kh.vo.EmployeeQuery;

/**
 * Ա����Ϣ������
 * @author Administrator
 *
 */
@Controller
public class EmployeeInfoController extends BaseControllor{
	@Autowired
    private EmployeeService employeeService;
	
	/**
	 * ��ת��Ա����Ϣ��ϸҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findEmployeeDetail")  
    public String findUserAttDetail(Model model){
        return "EmployeeDetail";
    }
	
	/**
	 * ��ѯ�û�����ϸ
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping(value="findEmployeeDetailPage")
	@ResponseBody
	public Map<String,Object> findEmployeeDetailPage(Model model,EmployeeQuery query){
		Map<String,Object> map = new HashMap<String,Object>();
		query.setEmpCodes(null);
		query.setBranches(null);
		List<EmployeeQuery> list = employeeService.findUserAttDetail(query);
		map.put("datas", list);
		map.put("page", query);
		return map;
	}
	

	/**
	 * �ϴ�Ա����ϸ��Ϣ
	 * @param file
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="employeeUpload")
	@ResponseBody
	public String employeeUpload(@RequestParam(value="fileUpload",required=true)MultipartFile file) throws Exception{
		Date start = new Date();
		int count = 0;
		InputStream fi = file.getInputStream();
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		//У���ϴ��ļ���ʽ
		row = sheet.getRow(0);
		String[] heads = {"��ҵ��","���´�/��Ӫ����","��λ����","����","user_id","����","��ְ����","��ְ����"};
		for(int i=0;i<heads.length;i++){
			cell = row.getCell(i);
			String temp = getCellValue(cell);
			if(!temp.equals(heads[i])){
				return "�ļ���ʽ����!";
			}
		}
		int rowCount = sheet.getPhysicalNumberOfRows();
		String dept,branch,name,code,onjob,leavejob;
		for(int i=1;i<rowCount;i++){
			row = sheet.getRow(i);
			//��ҵ��
			cell = row.getCell(0);
			dept =  getCellValue(cell);
			if(".".equals(dept)){
				dept = "";
			}
			//���´�
			cell = row.getCell(1);
			branch =  getCellValue(cell);
			//����
			cell = row.getCell(3);
			name =  getCellValue(cell);
			//ID ����
			cell = row.getCell(5);
			code =  getCellValue(cell);
			//��ְ����
			cell = row.getCell(6);
			onjob =  getCellValue(cell);
			//��ְ����
			cell = row.getCell(7);
			leavejob =  getCellValue(cell);
			
			EmployeeQuery query = new EmployeeQuery();
			query.setName(name);
			query.setCode(code.trim().toUpperCase());
			query.setDept(dept);
			query.setBranch(branch);
			if(null!=onjob && !"".equals(onjob)) 
				query.setOnjob(DateUtils.parseDate(onjob, "yyyy-MM-dd"));
			if(null!=leavejob && !"".equals(leavejob))
				query.setLeavejob(DateUtils.parseDate(leavejob, "yyyy-MM-dd"));
			count += employeeService.addEmployeeInfo(query);
		}
		Date end = new Date();
		System.out.println("======================================================");
		System.out.println("�ϴ�"+count+"����¼,��ʱ"+(end.getTime()-start.getTime())/1000+"��");
		System.out.println("======================================================");
		return "success";
	}
}
