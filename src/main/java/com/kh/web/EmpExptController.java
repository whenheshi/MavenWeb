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

import com.kh.model.EmpExpt;
import com.kh.service.EmpExptService;
import com.kh.util.DateUtils;
import com.kh.vo.EmpExptQuery;

/**
 * Ա�������Ϣ������
 * @author Administrator
 *
 */
@Controller
public class EmpExptController extends BaseControllor{
	@Autowired
    private EmpExptService empExptService;
	
	/**
	 * ��ת��Ա�������ϸҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findEmpExpt")  
    public String findUserAttDetail(Model model){
        return "ExceptDetail";
    }
	
	/**
	 * ��ѯ�û������ϸ
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping(value="findEmpExptPage")
	@ResponseBody
	public Map<String,Object> findEmployeeDetailPage(Model model,EmpExptQuery query){
		Map<String,Object> map = new HashMap<String,Object>();
		query.setEmployees(null);
		List<EmpExpt> list = empExptService.findEmpExptDetail(query);
		map.put("datas", list);
		map.put("page", query);
		return map;
	}
	

	/**
	 * �ϴ�Ա�������Ϣ
	 * @param file
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="exptUpload")
	@ResponseBody
	public String exptUpload(@RequestParam(value="fileUpload",required=true)MultipartFile file) throws Exception{
		Date start = new Date();
		int count = 0;
		InputStream fi = file.getInputStream();
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		//У���ϴ��ļ���ʽ
		row = sheet.getRow(0);
		String[] heads = {"��Ա����","��Ա���","����","������","��ʼ����","��ʼʱ��","��������","����ʱ��","���ʱ��","�������","��˱��","��ע","������","��������"};
		for(int i=0;i<heads.length;i++){
			cell = row.getCell(i);
			String temp = getCellValue(cell);
			if(!temp.equals(heads[i])){
				return "�ļ���ʽ����!";
			}
		}
		int rowCount = sheet.getPhysicalNumberOfRows();
		String name,code,dept,type,sdate,stime,edate,etime,hours,days,marks,operator,createdate;
		EmpExpt info;
		for(int i=1;i<rowCount;i++){
			row = sheet.getRow(i);
			//����
			cell = row.getCell(0);
			name =  getCellValue(cell);
			//���
			cell = row.getCell(1);
			code =  getCellValue(cell);
			//����
			cell = row.getCell(2);
			dept =  getCellValue(cell);
			//�������
			cell = row.getCell(3);
			type =  getCellValue(cell);
			//��ʼ����
			cell = row.getCell(4);
			sdate =  getCellValue(cell);
			//��ʼʱ��
			cell = row.getCell(5);
			stime =  getCellValue(cell);
			//��������
			cell = row.getCell(6);
			edate =  getCellValue(cell);
			//����ʱ��
			cell = row.getCell(7);
			etime =  getCellValue(cell);
			//���ʱ��
			cell = row.getCell(8);
			hours =  getCellValue(cell);
			//�������
			cell = row.getCell(9);
			days =  getCellValue(cell);
			//��ע
			cell = row.getCell(11);
			marks =  getCellValue(cell);
			//������
			cell = row.getCell(12);
			operator =  getCellValue(cell);
			//����ʱ��
			cell = row.getCell(13);
			createdate =  getCellValue(cell);
			System.out.println(name+","+code+","+dept+","+type+","+sdate+","+stime+","+edate+","+etime+","+hours+","+days+","+marks+","+operator+","+createdate);
			
			info = new EmpExpt();
			info.setCode(code);
			info.setName(name);
			info.setDept(dept);
			info.setType(type);
			info.setSdate(DateUtils.parseDate(sdate, "yyyy-MM-dd"));
			info.setStime(DateUtils.parseDate(stime, "HH:mm:ss"));
			info.setEdate(DateUtils.parseDate(edate, "yyyy-MM-dd"));
			info.setEtime(DateUtils.parseDate(etime, "HH:mm:ss"));
			info.setHours(Double.valueOf(hours));
			info.setDays(Double.valueOf(days));
			info.setMarks(marks);
			info.setOperator(operator);
			info.setCreatedate(DateUtils.parseDate(createdate, "yyyy-MM-dd"));
			
			count += empExptService.insertExptInfo(info);
		}
		Date end = new Date();
		System.out.println("======================================================");
		System.out.println("�ϴ�"+count+"����¼,��ʱ"+(end.getTime()-start.getTime())/1000+"��");
		System.out.println("======================================================");
		return "success";
	}

}
