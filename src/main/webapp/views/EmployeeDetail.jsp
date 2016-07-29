<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/page.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.custom.79639.js"></script>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/base.css" rel="stylesheet" type="text/css"/>
<title>员工信息</title>
</head>
<body>
	<div class="title">员工信息
		<div style="width:200px;right:10px;top:10px;position: absolute;">
			<section class="main">
				<div class="wrapper-demo">
					<div id="dd" class="wrapper-dropdown-5" tabindex="1">导航
						<ul class="dropdown">
							<li><a href="findEmployeeDetail.do"><i class="icon-user"></i>员工信息</a></li>
							<li><a href="findUserAttDetail.do"><i class="icon-time"></i>签到信息</a></li>
							<li><a href="findEmpCode.do"><i class="icon-lock"></i>钉钉工号</a></li>
							<li><a href="findEmpExpt.do"><i class="icon-glass"></i>请假信息</a></li>
							<li><a href="directToDataDetail.do" target="签到统计"><i class="icon-search"></i>签到统计</a></li>
						</ul>
					</div>
				</div>
			</section>
		</div>
	</div>
	<div style="width:100%">
		<table id="conditionTable" url="findEmployeeDetailPage.do">
			<tr>
				<td class="lableTd">姓名:</td>
				<td><input type="text" name="name" value="${name}"></td>
				<td class="lableTd">事业部:</td>
				<td><input type="text" name="dept" value="${dept}"></td>
				<td class="lableTd">办事处:</td>
				<td><input type="text" name="branch" value="${branch}">
					<input type="text" name="onjob" value="1999-01-01" style="display: none;">
					<input type="text" name="leavejob" value="2099-01-01" style="display: none;">
					<input type="text" name="code" value="" style="display: none;"></td>
				
				<td><button type="button" onclick="goPage(1)" style="width:4em;">查询</button></td>
			</tr>
		</table>
	</div>
	<div style="width:100%">
		<table id="resultTable" class="table">
			<thead>
				<tr>
					<th class="dept">事业部</th>
					<th class="branch">办事处</th>
					<th class="name">姓名</th>
					<th class="code">工号</th>
					<th class="onjobStr">入职日期</th>
					<th class="leavejobStr">离职时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div style="width:100%;height:30px;">
		<div style="height:100%;width:45%;float:left;text-align:left;" id="operation">
			<input id="fileUpload" type="file" size="20" name="fileUpload">
			<button id="buttonUpload" onclick="fileUpload()" style="width:4em;">上传</button>
		</div>
		<div style="height:100%;width:50%;float:right;text-align:right;" id="pageInfo">
		</div>
	</div>
	
<script type="text/javascript">
$(function(){
	$("#resultTable tbody tr").live("dblclick",function(){
		console.dir(this);
	});
	var dd = new DropDown($("#dd"));
});

function fileUpload(){
	$.ajaxFileUpload({
	    url: 'employeeUpload.do',
	    secureuri: false,
	    data: {},
	    fileElementId: 'fileUpload',
	    dataType: 'text',
	    success: function (data) {
	        if("success"==data){
	        	alert("上传成功");
	        	goPage(1);
	        }else{
	        	alert("上传失败:"+data);
	        }
	    },
	    error: function (data) {
	        alert("上传失败");
	    }
	});
}
</script>	
</body>
</html>