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
 * 员工信息控制器
 * @author Administrator
 *
 */
@Controller
public class EmployeeInfoController extends BaseControllor{
	@Autowired
    private EmployeeService employeeService;
	
	/**
	 * 跳转至员工信息明细页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findEmployeeDetail")  
    public String findUserAttDetail(Model model){
        return "EmployeeDetail";
    }
	
	/**
	 * 查询用户打卡明细
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
	 * 上传员工明细信息
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
		//校验上传文件格式
		row = sheet.getRow(0);
		String[] heads = {"事业部","办事处/运营中心","岗位名称","姓名","user_id","工号","入职日期","离职日期"};
		for(int i=0;i<heads.length;i++){
			cell = row.getCell(i);
			String temp = getCellValue(cell);
			if(!temp.equals(heads[i])){
				return "文件格式错误!";
			}
		}
		int rowCount = sheet.getPhysicalNumberOfRows();
		String dept,branch,name,code,onjob,leavejob;
		for(int i=1;i<rowCount;i++){
			row = sheet.getRow(i);
			//事业部
			cell = row.getCell(0);
			dept =  getCellValue(cell);
			if(".".equals(dept)){
				dept = "";
			}
			//办事处
			cell = row.getCell(1);
			branch =  getCellValue(cell);
			//姓名
			cell = row.getCell(3);
			name =  getCellValue(cell);
			//ID 工号
			cell = row.getCell(5);
			code =  getCellValue(cell);
			//入职日期
			cell = row.getCell(6);
			onjob =  getCellValue(cell);
			//离职日期
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
		System.out.println("上传"+count+"条记录,耗时"+(end.getTime()-start.getTime())/1000+"秒");
		System.out.println("======================================================");
		return "success";
	}
}
