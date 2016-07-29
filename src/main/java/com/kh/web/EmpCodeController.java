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

import com.kh.model.EmpCode;
import com.kh.service.EmpCodeService;
import com.kh.vo.EmpCodeQuery;

/**
 * Ա�������Ϣ������
 * @author Administrator
 *
 */
@Controller
public class EmpCodeController extends BaseControllor{
	@Autowired
    private EmpCodeService empCodeService;
	
	/**
	 * ��ת��Ա�������ϸҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findEmpCode")  
    public String findUserAttDetail(Model model){
        return "EmpCode";
    }
	
	/**
	 * ��ѯ�û������ϸ
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping(value="findEmpCodePage")
	@ResponseBody
	public Map<String,Object> findEmployeeDetailPage(Model model,EmpCodeQuery query){
		Map<String,Object> map = new HashMap<String,Object>();
		List<EmpCode> list = empCodeService.findEmpCodeDetail(query);
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
	@RequestMapping(value="codeUpload")
	@ResponseBody
	public String codeUpload(@RequestParam(value="fileUpload",required=true)MultipartFile file) throws Exception{
		Date start = new Date();
		int count = 0;
		InputStream fi = file.getInputStream();
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		//У���ϴ��ļ���ʽ
		row = sheet.getRow(0);
		String[] heads = {"Ա��UserID","����","����"};
		for(int i=0;i<heads.length;i++){
			cell = row.getCell(i);
			String temp = getCellValue(cell);
			if(!temp.equals(heads[i])){
				return "�ļ���ʽ����!";
			}
		}
		int rowCount = sheet.getPhysicalNumberOfRows();
		String name,code,id;
		EmpCode info;
		for(int i=1;i<rowCount;i++){
			row = sheet.getRow(i);
			//����
			cell = row.getCell(1);
			name =  getCellValue(cell);
			//����
			cell = row.getCell(2);
			code =  getCellValue(cell);
			//id
			cell = row.getCell(0);
			id =  getCellValue(cell);
			
			if(null==code||"".equals(code.trim())){
				continue;
			}
			info = new EmpCode();
			info.setCode(code.trim().toUpperCase());
			info.setName(name);
			info.setId(id.trim());
			
			count += empCodeService.insertCodeInfo(info);
		}
		Date end = new Date();
		System.out.println("======================================================");
		System.out.println("�ϴ�"+count+"����¼,��ʱ"+(end.getTime()-start.getTime())/1000+"��");
		System.out.println("======================================================");
		return "success";
	}

}
