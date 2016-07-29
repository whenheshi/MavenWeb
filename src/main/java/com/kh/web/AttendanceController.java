package com.kh.web;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.model.EmpExpt;
import com.kh.model.Employee;
import com.kh.model.UserAttDetail;
import com.kh.service.AttdenceCalculatorService;
import com.kh.service.EmpExptService;
import com.kh.service.EmployeeService;
import com.kh.service.UserAttDetailService;
import com.kh.util.DateUtils;
import com.kh.vo.AttendanceData;
import com.kh.vo.EmpExptData;
import com.kh.vo.EmpExptQuery;
import com.kh.vo.EmployeeQuery;
import com.kh.vo.OrgAttModel;
import com.kh.vo.UserAttDetailQuery;
import com.kh.vo.UserAttendanceDetail;

/**
 * 考勤统计控制器
 * @author Administrator
 *
 */
@Controller
public class AttendanceController extends BaseControllor{
	@Autowired
    private AttdenceCalculatorService attdenceCalculatorService;
	@Autowired
    private UserAttDetailService attDetailService;
	@Autowired
    private EmployeeService employeeService;
	@Autowired
    private EmpExptService empExptService;
	
	@RequestMapping(value="index")  
    public String index(Model model){
		String newName = attdenceCalculatorService.getNewName("heshi");
        model.addAttribute("str001", "heshi");
        model.addAttribute("str002", newName);
        return "index";
    }
	
	/**
	 * 跳转至用户打卡明细页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="findUserAttDetail")  
    public String findUserAttDetail(Model model){
		Date lastDate = attDetailService.findLastUpdateDate();
		model.addAttribute("lastDate", DateUtils.formatDate(lastDate, "yyyy-MM-dd"));
        return "UserAttDetail";
    }
	
	/**
	 * 查询用户打卡明细
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping(value="findUserAttDetailPage")
	@ResponseBody
	public Map<String,Object> findUserAttDetailPage(Model model,UserAttDetailQuery query){
		Map<String,Object> map = new HashMap<String,Object>();
		query.setEmployees(null);
		List<UserAttDetail> list = attDetailService.findUserAttDetail(query);
		int total = attDetailService.findUserAttDetailCount(query);
		query.setTotalCount(total);
		map.put("datas", list);
		map.put("page", query);
		return map;
	}
	
	/**
	 * 上传用户打卡明细
	 * @param file
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="userAttUpload")
	@ResponseBody
	public String userAttUpload(@RequestParam(value="fileUpload",required=true)MultipartFile file,Model model) throws Exception{
		Date start = new Date();
		int count = 0;
		InputStream fi = file.getInputStream();
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		//校验上传文件格式
		row = sheet.getRow(2);
		String[] heads = {"姓名","工号","部门","职位","日期","时间","地点","详细地址"};
		for(int i=0;i<heads.length;i++){
			cell = row.getCell(i);
			String temp = getCellValue(cell);
			if(!temp.equals(heads[i])){
				return "文件格式错误!";
			}
		}
		int rowCount = sheet.getPhysicalNumberOfRows();
		String name,code,dept,job,date,time,addr,detail,memo;
		for(int i=3;i<rowCount;i++){
			row = sheet.getRow(i);
			//姓名
			cell = row.getCell(0);
			name =  getCellValue(cell);
			//ID 工号
			cell = row.getCell(1);
			code =  getCellValue(cell);
			//部门
			cell = row.getCell(2);
			dept =  getCellValue(cell);
			//职位
			cell = row.getCell(3);
			job =  getCellValue(cell);
			//日期
			cell = row.getCell(4);
			date =  getCellValue(cell);
			//时间
			cell = row.getCell(5);
			time =  getCellValue(cell);
			//地点
			cell = row.getCell(6);
			addr =  getCellValue(cell);
			//详细地址
			cell = row.getCell(7);
			detail =  getCellValue(cell);
			//详细地址
			cell = row.getCell(8);
			memo =  getCellValue(cell);
			
			UserAttDetail uaDetail = new UserAttDetail();
			uaDetail.setName(name);
			uaDetail.setDept(dept);
			uaDetail.setJob(job);
			uaDetail.setCode(code.trim().toUpperCase());//工号转大写
			uaDetail.setDate(DateUtils.parseDate(date,"yyyy-MM-dd"));
			uaDetail.setTime(DateUtils.parseDate(date+" "+time,"yyyy-MM-dd HH:mm:ss"));
			uaDetail.setAddr(addr);
			uaDetail.setDetail(detail);
			uaDetail.setMemo(memo);
			uaDetail.setMac("");
			try{
				count += attDetailService.insertDetail(uaDetail);
			}catch (Exception e) {
				uaDetail.setMemo("");
				count += attDetailService.insertDetail(uaDetail);
			}
		}
		Date end = new Date();
		System.out.println("======================================================");
		System.out.println("上传"+count+"条记录,耗时"+(end.getTime()-start.getTime())/1000+"秒");
		System.out.println("======================================================");
		return "success";
	}
	
	/**
	 * 跳转至打卡统计界面
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="directToDataDetail")  
    public String directToDataDetail(Model model) throws Exception{
		Date lastDate = attDetailService.findLastUpdateDate();
		model.addAttribute("lastDate", DateUtils.formatDate(lastDate, "yyyy-MM-dd"));
        return "AttDataQuery";
    }
	
	/**
	 * 获取事业部列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getOrgConfig")
	@ResponseBody
	public List<String> getOrgConfig(){
		return employeeService.findOrgList();
	}
	
	/**
	 * 获取办事处列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getBranchConfig")
	@ResponseBody
	public List<String> getBranchConfig(@RequestParam(value="org",required=false)String org){
		return employeeService.findBranchByDept(org);
	}
	
	
	/**
	 * 获取员工列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getEmployeeConfig")
	@ResponseBody
	public List<Employee> getEmployeeConfig(@RequestParam(value="org",required=false)String org,@RequestParam(value="branch",required=false)String branch){
		if((null==org||"".equals(org))&&(null==branch||"".equals(branch)))
			return new ArrayList<Employee>();
		return employeeService.findEmployeeByOrgAndBranch(org,branch);
	}
	
	public List<Employee> getEmployeeConfig(String org,String branch,Date begin,Date end){
		if((null==org||"".equals(org))&&(null==branch||"".equals(branch)))
			return new ArrayList<Employee>();
		return employeeService.findEmployeeByOrgAndDateRange(org,branch,begin,end);
	}
	
	
	/**
	 * 查询打卡明细
	 * @param org
	 * @param branch
	 * @param employees
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="queryDataDetail")
	@ResponseBody
	public Map<String,Object> queryDataDetail(
			@RequestParam(value = "org", required = false) String org,
			@RequestParam(value = "branch", required = false) String branch,
			@RequestParam(value = "employees", required = false) String employees,
			@RequestParam(value = "dateBegin", required = false) String dateBegin,
			@RequestParam(value = "dateEnd", required = false) String dateEnd) throws IOException {
		Date beginDate = DateUtils.parseDate(dateBegin, "yyyy-MM-dd");
		Date endDate = DateUtils.parseDate(dateEnd, "yyyy-MM-dd");
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> workDays = readWorkDays(dateBegin,dateEnd);
		int workDayCount = workDays.size();
		//获取部门包含的员工列表
		List<String> tempUsers = filteUsers(org,branch,beginDate,endDate);
		//获取考勤明细数据
		Map<String,AttendanceData> attDataMap = getAttendDataDetail(dateBegin,dateEnd,tempUsers,workDays);
		
		//查询事业部签到率
		List<Object[]> orgDataList = new ArrayList<Object[]>();
		if("".equals(org)&&(null==branch || "".equals(branch))){
			List<String> orgList = getOrgConfig();
			for(String orgTemp:orgList){
				if(orgTemp==null||"".equals(orgTemp.trim())){
					continue;
				}
				OrgAttModel orgModel = new OrgAttModel();
				orgModel.setName(orgTemp);
				List<Employee> users = getEmployeeConfig(orgTemp,branch,beginDate,endDate);
				orgModel.setUsers(users);
				orgModel.setWorkDays(workDays);
				List<AttendanceData> data = new ArrayList<AttendanceData>();
				for(Employee userId:users){
					for(String workDay:workDays){
						String key =workDay+"|"+userId.getCode();
						if(attDataMap.containsKey(key)&&null!=attDataMap.get(key)){
							data.add(attDataMap.get(key));
						}
					}
				}
				orgModel.setData(data);
				orgDataList.add(new Object[]{orgTemp,orgModel.getUserCount(),orgModel.getActualCheckCount(),orgModel.getChectRate(),orgModel.getShouldCheckCount()});
			}
		}else if(!"".equals(org)){
			OrgAttModel orgModel = new OrgAttModel();
			orgModel.setName(org);
			List<Employee> users = getEmployeeConfig(org,branch,beginDate,endDate);
			orgModel.setUsers(users);
			orgModel.setWorkDays(workDays);
			List<AttendanceData> data = new ArrayList<AttendanceData>();
			for(Employee userId:users){
				for(String workDay:workDays){
					String key =workDay+"|"+userId.getCode();
					if(attDataMap.containsKey(key)&&null!=attDataMap.get(key)){
						data.add(attDataMap.get(key));
					}
				}
			}
			orgModel.setData(data);
			orgDataList.add(new Object[]{org,orgModel.getUserCount(),orgModel.getActualCheckCount(),orgModel.getChectRate(),orgModel.getShouldCheckCount()});
		}
		map.put("orgData", orgDataList);
		
		//查询办事处签到数据
		List<Object[]> branchDataList = new ArrayList<Object[]>();
		List<String> branchList = getBranchList(org,branch);
		for(String branchTemp:branchList){
			if(null==branchTemp || "".equals(branchTemp)){
				continue;
			}
			OrgAttModel orgModel = new OrgAttModel();
			orgModel.setName(branchTemp);
			//事业部员工工号列表
			List<Employee> users = getEmployeeConfig(org,branchTemp,beginDate,endDate);
			orgModel.setUsers(users);
			orgModel.setWorkDays(workDays);
			List<AttendanceData> data = new ArrayList<AttendanceData>();
			for(Employee userId:users){
				for(String workDay:workDays){
					String key =workDay+"|"+userId.getCode();
					if(attDataMap.containsKey(key)&&null!=attDataMap.get(key)){
						data.add(attDataMap.get(key));
					}
				}
			}
			orgModel.setData(data);
			branchDataList.add(new Object[]{branchTemp,orgModel.getUserCount(),orgModel.getActualCheckCount(),orgModel.getChectRate(),orgModel.getShouldCheckCount()});
		}
		map.put("branchData", branchDataList);
		
		//查询员工签到汇总
		List<UserAttendanceDetail> employeeDataList = new ArrayList<UserAttendanceDetail>();
		//组织或机构不为空，显示用户打卡汇总
		if(!"".equals(org)||!"".equals(branch)){
			List<EmployeeQuery> users = getUsers(org,branch,employees,beginDate,endDate);
			for(EmployeeQuery config:users){
				UserAttendanceDetail attDetail = new UserAttendanceDetail();
				attDetail.setName(config.getName());
				attDetail.setCode(config.getCode());
				attDetail.setOrg(config.getDept());
				attDetail.setBranch(config.getBranch());
				String userId = config.getCode();
				int expDays = 0;
				for(String workDay:workDays){
					Date now = DateUtils.parseDate(workDay,"yyyy-MM-dd");
					String checkType = "";
					if(null!=config.getOnjob() && config.getOnjob().after(now)){
						expDays++;
						checkType="未入职";
					}else if(null!=config.getLeavejob() && config.getLeavejob().before(now)){
						expDays++;
						checkType="已离职";
					}
					if(attDataMap.containsKey(workDay+"|"+userId)){
						attDetail.addDetail(attDataMap.get(workDay+"|"+userId));
					}else{
						AttendanceData temp = new AttendanceData();
						temp.setCheckType(checkType);
						temp.setDate(workDay);
						temp.setCode(userId);
						temp.setName(config.getName());
						attDetail.addDetail(temp);
					}
					
				}
				attDetail.setShouldCheckDay((workDayCount-expDays));
				employeeDataList.add(attDetail);
			}
		}
		map.put("employeeData", employeeDataList);
		map.put("checkRange",DateUtils.formatDate(beginDate, "yy.M.d")
				+"~"+DateUtils.formatDate(endDate, "yy.M.d"));
		map.put("checkCount", workDayCount*2);
		map.put("workDays", workDays);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ExportDataDetail",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
	public void ExportDataDetail(
			@RequestParam(value = "dept", required = false) String dept,
			@RequestParam(value = "branch", required = false) String branch,
			@RequestParam(value = "employee", required = false) String employees,
			@RequestParam(value = "dateBegin", required = false) String dateBegin,
			@RequestParam(value = "dateEnd", required = false) String dateEnd,
			HttpServletResponse response) throws IOException {
		response.reset();
		OutputStream os = response.getOutputStream();
		response.setContentType("application/x-msdownload");
		StringBuffer fileName=new StringBuffer();
		fileName.append(dateBegin.replaceAll("-", "")).append("_").append(dateEnd.replaceAll("-", ""));
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
		
		Map<String,Object> map = queryDataDetail(dept,branch,employees,dateBegin,dateEnd);
		
		List<Object[]> orgList =(List<Object[]>) map.get("orgData");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("事业部考勤汇总数据");
		HSSFRow row = sheet.createRow(0);
		int indexA = 1;
		HSSFCell cell =null;
		cell = row.createCell(0);
		cell.setCellValue("事业部");
		cell = row.createCell(1);
		cell.setCellValue("人数");
		cell = row.createCell(2);
		cell.setCellValue("应签到次数");
		cell = row.createCell(3);
		cell.setCellValue("实际签到次数");
		cell = row.createCell(4);
		cell.setCellValue("签到率");
		//统计各事业部打卡数据
		for(Object[] org:orgList){
			row = sheet.createRow(indexA++);
			cell = row.createCell(0);
			cell.setCellValue(String.valueOf(org[0]));
			cell = row.createCell(1);
			cell.setCellValue(String.valueOf(org[1]));
			cell = row.createCell(2);
			cell.setCellValue(String.valueOf(org[4]));
			cell = row.createCell(3);
			cell.setCellValue(String.valueOf(org[2]));
			cell = row.createCell(4);
			cell.setCellValue(String.valueOf(org[3])+"%");
		}
		
		//部门
		List<Object[]> branchList =(List<Object[]>) map.get("branchData");
		
		HSSFSheet sheetB = wb.createSheet("各办事处勤汇总数据");
		row = sheetB.createRow(0);
		int indexB = 1;
		cell = row.createCell(0);
		cell.setCellValue("办事处/运营中心");
		cell = row.createCell(1);
		cell.setCellValue("人数");
		cell = row.createCell(2);
		cell.setCellValue("应签到次数");
		cell = row.createCell(3);
		cell.setCellValue("实际签到次数");
		cell = row.createCell(4);
		cell.setCellValue("签到率");
		for(Object[] branchTemp:branchList){
			row = sheetB.createRow(indexB++);
			cell = row.createCell(0);
			cell.setCellValue(String.valueOf(branchTemp[0]));
			cell = row.createCell(1);
			cell.setCellValue(String.valueOf(branchTemp[1]));
			cell = row.createCell(2);
			cell.setCellValue(String.valueOf(branchTemp[4]));
			cell = row.createCell(3);
			cell.setCellValue(String.valueOf(branchTemp[2]));
			cell = row.createCell(4);
			cell.setCellValue(String.valueOf(branchTemp[3])+"%");
		}
		
		List<UserAttendanceDetail> userAttList =(List<UserAttendanceDetail>) map.get("employeeData");
		List<String> workDays =(List<String>) map.get("workDays");
		//打卡明细
		HSSFSheet sheetC = wb.createSheet("打卡明细");
		int indexRow = 0;
		row = sheetC.createRow(indexRow++);
		int colIndex = 0;
		cell = row.createCell(colIndex++);
		cell.setCellValue("事业部");
		cell = row.createCell(colIndex++);
		cell.setCellValue("办事处/运营中心");
		cell = row.createCell(colIndex++);
		cell.setCellValue("姓名");
		cell = row.createCell(colIndex++);
		cell.setCellValue("签到时间");
		cell = row.createCell(colIndex++);
		cell.setCellValue("应签次数");
		cell = row.createCell(colIndex++);
		cell.setCellValue("实际签到次数");
		for(String date:workDays){
			cell = row.createCell(colIndex++);
			cell.setCellValue(DateUtils.formatDate(DateUtils.parseDate(date,"yyyy-MM-dd"), "yy.M.d"));
		}
		String start = DateUtils.formatDate(DateUtils.parseDate(dateBegin,"yyyy-MM-dd"), "yy.M.d");
		String end = DateUtils.formatDate(DateUtils.parseDate(dateEnd,"yyyy-MM-dd"), "yy.M.d");
		AttendanceData detail;
		for(UserAttendanceDetail arr:userAttList){
			colIndex = 0;
			row = sheetC.createRow(indexRow++);
			cell = row.createCell(colIndex++);
			cell.setCellValue(arr.getOrg());
			cell = row.createCell(colIndex++);
			cell.setCellValue(arr.getBranch());
			cell = row.createCell(colIndex++);
			cell.setCellValue(arr.getName());
			cell = row.createCell(colIndex++);
			cell.setCellValue(start+"-"+end);
			cell = row.createCell(colIndex++);
			cell.setCellValue(arr.getShouldCheckCount());
			cell = row.createCell(colIndex++);
			cell.setCellValue(arr.getAttCount());
			for(String date:workDays){
				cell = row.createCell(colIndex++);
				detail = arr.getDetails().get(date);
				if(null==detail){
					cell.setCellValue("0");
				}else{
					if(null!=detail.getCheckType() && !"".equals(detail.getCheckType())){
						cell.setCellValue(detail.getCheckType());
					}else{
						cell.setCellValue(String.valueOf(detail.getCheckCount()));
					}
				}
			}
		}
		
		wb.write(os);
		//os.flush();
	}
	
	/**
	 * 根据事业部和办事处参数获取员工工号列表
	 * @param org
	 * @param branch
	 * @param begin
	 * @param end
	 * @return
	 */
	private List<String> filteUsers(String org, String branch,Date begin,Date end) {
		List<String> users = new ArrayList<String>();
		if((null==org||"".equals(org))&&(null==branch||"".equals(branch))){
			return users;
		}else{
			List<Employee> emps = getEmployeeConfig(org,branch,begin,end);
			for(Employee emp:emps){
				if(!users.contains(emp.getCode())){
					users.add(emp.getCode());
				}
			}
		}
		return users;
	}

	private List<EmployeeQuery> getUsers(String org, String branch, String employees,Date begin,Date end) {
		List<String> emps = null;
		if(null!=employees && !"".equals(employees)){
			String[] employeeArray = employees.split(",");
			if(employeeArray.length>0){
				emps = new ArrayList<String>();
				for(String emp : employeeArray){
					emps.add(emp);
				}
			}
		}
		
		EmployeeQuery query = new EmployeeQuery();
		if(null!=branch && !"".equals(branch) &&branch.indexOf(",")<0){
			query.setBranch(branch);
			query.setBranches(null);
		}else if(null!=branch && !"".equals(branch) && branch.indexOf(",")>-1){
			List<String> branches = new ArrayList<String>();
			String[] arr = branch.split(",");
			for(String temp:arr){
				if(null!=temp && !"".equals(temp)){
					branches.add(temp);
				}
			}
			query.setBranches(branches);
		}
		query.setDept(org);
		query.setEmpCodes(emps);
		query.setPageSize(99999);
		List<EmployeeQuery> list = employeeService.findUserAttDetail(query);
		
		List<EmployeeQuery> result = new ArrayList<EmployeeQuery>();
		for(EmployeeQuery emp : list){
			//入职日期在结束日期之后，或者离职日期在开始日期之前
			if((null != emp.getOnjob()&&emp.getOnjob().after(end))
					||(null!=emp.getLeavejob()&&emp.getLeavejob().before(begin))){
				//do nothing
			}else{
				result.add(emp);
			}
		}
		return result;
	}

	private List<String> getBranchList(String org, String branch) {
		if(null!=branch && !"".equals(branch)){
			List<String> branchList = new ArrayList<String>();
			if(branch.indexOf(",")<0){
				branchList.add(branch);
			}else{
				String[] arr = branch.split(",");
				for(String temp:arr){
					if(null!=temp && !"".equals(temp)){
						branchList.add(temp);
					}
				}
			}
			return branchList;
		}else{
			return getBranchConfig(org);
		}
		
	}

	/**
	 * 查询工作日
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 * @throws IOException 
	 */
	private List<String> readWorkDays(String start, String end) throws IOException {
		List<String> workDays = new ArrayList<String>();
		InputStream fi = AttendanceController.class.getResourceAsStream("WorkDayConfig.xls");
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		//最后的工作日结果列表
		int rowCount = sheet.getPhysicalNumberOfRows();
		//配置列表
		List<String> onList = new ArrayList<String>();
		List<String> offList = new ArrayList<String>();
		for(int i=1;i<rowCount;i++){
			row = sheet.getRow(i);
			//日期
			cell = row.getCell(0);
			String date =  getCellValue(cell);
			//上班\休息
			cell = row.getCell(1);
			String onOff = getCellValue(cell);
			if("休息".equals(onOff)){
				offList.add(date);
			}else{
				onList.add(date);
			}
		}
		Date startDate = DateUtils.parseDate(start,"yyyy-MM-dd");
		Date endDate = DateUtils.parseDate(end,"yyyy-MM-dd");
		while(!startDate.after(endDate)){
			String dateTemp = DateUtils.formatDate(startDate,"yyyy-MM-dd");
			String temp = DateUtils.formatDate(startDate,"yyyyMMdd");
			if(DateUtils.isWeekend(startDate)){//周末
				if(onList.contains(temp)){
					//周末但是调班
					workDays.add(dateTemp);
				}
			}else{//工作日
				if(!offList.contains(temp)){
					//节假日
					workDays.add(dateTemp);
				}
			}
			startDate = DateUtils.addDay(startDate, 1);
		}
		return workDays;
	}

	/**
	 * 获取打卡明细数据
	 * @param org
	 * @param branch
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	private Map<String,AttendanceData> getAttendDataDetail(String dateBegin,String dateEnd,List<String> tempUsers,List<String> workDays){
		Date start = DateUtils.parseDate(dateBegin, "yyyy-MM-dd");
		Date end = DateUtils.parseDate(dateEnd, "yyyy-MM-dd");
		//查询签到信息
		UserAttDetailQuery query = new UserAttDetailQuery();
		query.setCurrentPage(1);
		query.setPageSize(9999999);
		query.setDateBegin(dateBegin);
		query.setDateEnd(dateEnd);
		query.setEmployees(tempUsers);
		List<UserAttDetail> list = attDetailService.findUserAttDetail(query);
		
		//查询请假信息
		EmpExptQuery expQuery = new EmpExptQuery();
		expQuery.setEmployees(tempUsers);
		expQuery.setSdate(start);
		expQuery.setEdate(end);
		List<EmpExpt> expList = empExptService.findEmpExptDetail(expQuery);
		
		//请假信息map
		Map<String,EmpExptData> expMap = new HashMap<String,EmpExptData>();
		EmpExptData temp;
		for(EmpExpt exp : expList){
			//开始日期=结束日期
			if(exp.getSdateStr().equals(exp.getEdateStr())){
				temp = new EmpExptData();
				temp.setAllDay(true);
				temp.setCode(exp.getCode());
				temp.setType(exp.getType());
				expMap.put(exp.getSdateStr()+"|"+exp.getCode(), temp);
			}else{
				Date startDate = exp.getSdate();
				Date endDate = exp.getEdate();
				while(!startDate.after(endDate)){
					//请假时间在统计范围内
					if(!startDate.before(start)&&!startDate.after(end)){
						temp = new EmpExptData();
						temp.setCode(exp.getCode());
						temp.setType(exp.getType());
						if(startDate.compareTo(start)==0){
							//第一天
							temp.setStart(DateUtils.formatDate(exp.getStime(), "HH:mm:ss"));
							temp.setEnd("18:00:00");
						}else if(startDate.compareTo(end)==0){
							//最后一天
							temp.setStart("08:30:00");
							temp.setEnd(DateUtils.formatDate(exp.getEtime(), "HH:mm:ss"));
						}else{
							//中间天数
							temp.setAllDay(true);
						}
						expMap.put(DateUtils.formatDate(startDate,"yyyy-MM-dd")+"|"+exp.getCode(), temp);
					}
					startDate = DateUtils.addDay(startDate, 1);
				}
			}
		}
		
		//考勤信息map
		Map<String,AttendanceData> map = new HashMap<String,AttendanceData>();
		AttendanceData data;
		for(UserAttDetail attDetail:list){
			//主键为日期+工号
			String key = attDetail.getDateStr()+"|"+attDetail.getCode();
			if(map.containsKey(key)){
				data = map.get(key);
				if(null!=data.getCheckType() && !"".equals(data.getCheckType())){
					continue;
				}
			}else{
				data = new AttendanceData();
				if(expMap.containsKey(key)){
					data.setExpData(expMap.get(key));
				}
				data.setCode(attDetail.getCode());
				data.setName(attDetail.getName());
				data.setDate(attDetail.getDateStr());
			}
			//设置上班打卡时间
			if(null == data.getOnjob()||attDetail.getTimeStr().compareTo(data.getOnjob())<0){
				data.setOnjob(attDetail.getTimeStr());
			}
			//设置下班打卡时间
			if(null == data.getLeavejob()||attDetail.getTimeStr().compareTo(data.getLeavejob())>0){
				data.setLeavejob(attDetail.getTimeStr());
			}
			//入职日期在考勤日期之后
			if(null!=attDetail.getOnjob() && attDetail.getOnjob().after(attDetail.getDate())){
				data.setCheckType("未入职");
				//将开始日期直至入职日期的区间内考勤数据均定义为未入职
				Date startDate = start;
				while(startDate.before(attDetail.getOnjob())){
					data = new AttendanceData();
					data.setCode(attDetail.getCode());
					data.setName(attDetail.getName());
					data.setDate(DateUtils.formatDate(startDate,"yyyy-MM-dd"));
					data.setCheckType("未入职");
					map.put(DateUtils.formatDate(startDate,"yyyy-MM-dd")+"|"+attDetail.getCode(), data);
					startDate = DateUtils.addDay(startDate, 1);
				} 
			}else if(null!=attDetail.getLeavejob() && attDetail.getLeavejob().before(attDetail.getDate())){
				data.setCheckType("已离职");
				//将离职日期至考勤结束日期内的考勤数据均定义为已离职
				Date startDate = end;
				while(startDate.after(attDetail.getLeavejob())){
					data = new AttendanceData();
					data.setCode(attDetail.getCode());
					data.setName(attDetail.getName());
					data.setDate(DateUtils.formatDate(startDate,"yyyy-MM-dd"));
					data.setCheckType("已离职");
					map.put(DateUtils.formatDate(startDate,"yyyy-MM-dd")+"|"+attDetail.getCode(), data);
					startDate = DateUtils.addDay(startDate, -1);
				}
			}
			map.put(key, data);
		}
		//补齐没有查询签到记录的数据
		for(String day : workDays){
			for(String user : tempUsers){
				String key = day+"|"+user;
				if(!map.containsKey(key)){
					data = new AttendanceData();
					data.setCode(user);
					data.setDate(day);
					data.setExpData(expMap.get(key));
					data.setOnjob(null);
					data.setLeavejob(null);
					map.put(key, data);
				}
			}
		}
		return map;
	}
	
	
}
