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
 * 员工请假信息控制器
 * @author Administrator
 *
 */
@Controller
public class EmpExptController extends BaseControllor{
	@Autowired
    private EmpExptService empExptService;
	
	/**
	 * 跳转至员工信请假细页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findEmpExpt")  
    public String findUserAttDetail(Model model){
        return "ExceptDetail";
    }
	
	/**
	 * 查询用户请假明细
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
	 * 上传员工请假信息
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
		//校验上传文件格式
		row = sheet.getRow(0);
		String[] heads = {"人员姓名","人员编号","部门","请假类别","开始日期","开始时间","结束日期","结束时间","请假时长","请假天数","审核标记","备注","操作者","操作日期"};
		for(int i=0;i<heads.length;i++){
			cell = row.getCell(i);
			String temp = getCellValue(cell);
			if(!temp.equals(heads[i])){
				return "文件格式错误!";
			}
		}
		int rowCount = sheet.getPhysicalNumberOfRows();
		String name,code,dept,type,sdate,stime,edate,etime,hours,days,marks,operator,createdate;
		EmpExpt info;
		for(int i=1;i<rowCount;i++){
			row = sheet.getRow(i);
			//姓名
			cell = row.getCell(0);
			name =  getCellValue(cell);
			//编号
			cell = row.getCell(1);
			code =  getCellValue(cell);
			//部门
			cell = row.getCell(2);
			dept =  getCellValue(cell);
			//请假类型
			cell = row.getCell(3);
			type =  getCellValue(cell);
			//开始日期
			cell = row.getCell(4);
			sdate =  getCellValue(cell);
			//开始时间
			cell = row.getCell(5);
			stime =  getCellValue(cell);
			//结束日期
			cell = row.getCell(6);
			edate =  getCellValue(cell);
			//结束时间
			cell = row.getCell(7);
			etime =  getCellValue(cell);
			//请假时长
			cell = row.getCell(8);
			hours =  getCellValue(cell);
			//请假天数
			cell = row.getCell(9);
			days =  getCellValue(cell);
			//备注
			cell = row.getCell(11);
			marks =  getCellValue(cell);
			//操作者
			cell = row.getCell(12);
			operator =  getCellValue(cell);
			//操作时间
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
		System.out.println("上传"+count+"条记录,耗时"+(end.getTime()-start.getTime())/1000+"秒");
		System.out.println("======================================================");
		return "success";
	}

}
