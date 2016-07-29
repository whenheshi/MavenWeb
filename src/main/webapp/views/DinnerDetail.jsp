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
<title>晚餐刷卡明细查询</title>
</head>
<body>
	<div class="title">晚餐刷卡明细查询</div>
	<div style="width:100%">
		<form action="ExportDinnerDetail.do" target="hiddenIframe" method="post" accept-charset="utf-8" onsubmit="document.charset='utf-8';">
		<table id="conditionTable" url="findDinnerPage.do">
			<tr>
				<td class="lableTd">姓名:</td>
				<td><input type="text" name="name" value="${name}"></td>
				<td class="lableTd">工号:</td>
				<td><input type="text" name="code" value="${code}"></td>
				<td class="lableTd">部门:</td>
				<td><input type="text" name=dept value="${dept}"></td>
				<td class="lableTd">用餐日期:</td>
				<td><input type="text" id="sdate" name="sdate" onfocus="WdatePicker({vel:'sdate',readOnly:true,firstDayOfWeek:1})" class="Wdate"/></td>
				<td class="lableTd">至</td>
				<td><input type="text" id="edate" name="edate" onfocus="WdatePicker({vel:'edate',readOnly:true,firstDayOfWeek:1})" class="Wdate"/></td>
				<td>
					<button type="button" onclick="goPage(1)" style="width:4em;">查询</button>
					<button type="submit" style="width:4em;">导     出</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div style="width:100%">
		<table id="resultTable" class="table">
			<thead>
				<tr>
					<th class="dateStr">用餐日期</th>
					<th class="name">姓名</th>
					<th class="code">工号</th>
					<th class="dept">部门</th>
					<th class="timeStr">用餐时间</th>
					<th class="checkoutStr">下班刷卡时间</th>
					<th class="flag">是否加班用餐</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div style="width:100%;height:30px;">
		<div style="height:100%;width:50%;float:right;text-align:right;" id="pageInfo">
		</div>
	</div>
	<iframe name="hiddenIframe" style="height: 0;width: 0;display: none;"></iframe>
	
	<div class="title">技术支持:管理工程部</div>
	
<script type="text/javascript">
$(function(){
	
});
</script>	
</body>
</html>