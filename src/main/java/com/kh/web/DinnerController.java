package com.kh.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.model.Dinner;
import com.kh.service.DinnerService;
import com.kh.service.EmpCodeService;
import com.kh.util.DateUtils;
import com.kh.vo.DinnerQuery;

/**
 * 晚餐统计控制器
 * @author Administrator
 *
 */
@Controller
public class DinnerController extends BaseControllor{
	@Autowired
    private DinnerService dinnerService;
	
	/**
	 * 跳转至员工信请假细页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findDinnerDetail")  
    public String findDinnerDetail(Model model){
        return "DinnerDetail";
    }
	
	/**
	 * 查询晚餐明细
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping(value="findDinnerPage")
	@ResponseBody
	public Map<String,Object> findDinnerPage(Model model,DinnerQuery query){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Dinner> list = findDinnerList(query);
		//dinnerService.findDinnerDetail(query);
		map.put("datas", list);
		map.put("page", query);
		return map;
	}
	
	/**
	 * 查询刷卡数据
	 * @param query
	 * @return
	 */
	private List<Dinner> findDinnerList(DinnerQuery query) {
		List<Dinner> list = generateTestDatas(query);
		return list;
	}
	
	/**
	 * 生成测试数据
	 * @param query
	 * @return
	 */
	private List<Dinner> generateTestDatas(DinnerQuery query) {
		List<Dinner> list = new ArrayList<Dinner>();
		Dinner dinner;
		for(int i=0;i<20;i++){
			Date date = DateUtils.addDay(DateUtils.parseDate("20160101", "yyyyMMdd"),  (int)(Math.random()*364));
			Date time = DateUtils.parseDate("18:00", "HH:mm");
			Date checkout = DateUtils.parseDate(DateUtils.formatDate(date, "yyyy-MM-dd")+" 19:00", "yyyy-MM-dd HH:mm");
			dinner = new Dinner();
			dinner.setName("测试用户"+i);
			dinner.setCode("KH0000"+i);
			dinner.setDept("测试部门");
			dinner.setDate(date);
			dinner.setTime(DateUtils.addMinutes(time, (int)(Math.random()*30)));
			dinner.setCheckout(DateUtils.addMinutes(checkout, (int)(Math.random()*120)));
			list.add(dinner);
		}
		query.setTotalCount(40);
		return list;
	}

	/**
	 * 导出Excel
	 * @param query
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="ExportDinnerDetail",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
	public void ExportDinnerDetail(DinnerQuery query,HttpServletResponse response) throws IOException {
		response.reset();
		OutputStream os = response.getOutputStream();
		response.setContentType("application/x-msdownload");
		String fileName="DinnerDetails"+DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
		
		List<Dinner> list = findDinnerList(query);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("晚餐明细");
		HSSFRow row = sheet.createRow(0);
		int indexA = 1;
		HSSFCell cell =null;
		cell = row.createCell(0);
		cell.setCellValue("用餐日期");
		cell = row.createCell(1);
		cell.setCellValue("姓名");
		cell = row.createCell(2);
		cell.setCellValue("工号");
		cell = row.createCell(3);
		cell.setCellValue("部门");
		cell = row.createCell(4);
		cell.setCellValue("用餐时间");
		cell = row.createCell(5);
		cell.setCellValue("下班刷卡时间");
		cell = row.createCell(6);
		cell.setCellValue("是否加班用餐");
		for(Dinner dinner:list){
			row = sheet.createRow(indexA++);
			cell = row.createCell(0);
			cell.setCellValue(dinner.getDateStr());
			cell = row.createCell(1);
			cell.setCellValue(dinner.getName());
			cell = row.createCell(2);
			cell.setCellValue(dinner.getCode());
			cell = row.createCell(3);
			cell.setCellValue(dinner.getDept());
			cell = row.createCell(4);
			cell.setCellValue(dinner.getTimeStr());
			cell = row.createCell(5);
			cell.setCellValue(dinner.getCheckoutStr());
			cell = row.createCell(6);
			cell.setCellValue(dinner.getFlag());
		}
		
		wb.write(os);
	}
	
}
