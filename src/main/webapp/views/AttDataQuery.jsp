<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.select-multiple.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.artDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.blockUI.min.js"></script>
<link href="${pageContext.request.contextPath}/css/base.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/ui-dialog.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/select-multiple.css" rel="stylesheet" type="text/css"/>
<title>考勤数据统计</title>
</head>
<body>
	<h1>考勤统计(考勤数据最后更新日期:${lastDate})</h1>
	<div style="width:100%">
		<form action="ExportDataDetail.do" target="hiddenIframe" method="post" accept-charset="utf-8" onsubmit="document.charset='utf-8';">
		<table id="conditionTable" url="findAttDataSummary.do">
			<tr>
				<td class="lableTd" width="5%" >事业部:</td>
				<td width="25%" colspan="3">
					<select id="deptSel" name="dept" value="${dept}">
						<option value=''></option>
					</select>
				</td>
				<td class="lableTd" width="5%" rowspan="3">员工:</td>
				<td width="15%" rowspan="3" style="padding:0;">
					<select multiple="multiple" id="employeeMultSel" name="employee" value="${employee}">
					</select>
				</td>
				<td width="10%" rowspan="3">
					<button type="button" onclick="queryDatas()" style="height:30px;width:80px;">查     询</button>
					<button type="submit" style="height:30px;width:80px;">导     出</button>
				</td>
			</tr>
			<tr>
				<td class="lableTd" width="10%">办事处/运营中心:</td>
				<td width="25%" colspan="3">
					<select multiple="multiple" id="branchSel" name="branch" value="${branch}" style="width:99%">
					</select>
				</td>
			</tr>
			<tr>
				<td class="lableTd" width="5%">签到日期:</td>
				<td width="5%"><input type="text" id="dateBegin" name="dateBegin" onfocus="WdatePicker({vel:'endDay',readOnly:true,firstDayOfWeek:1})" class="Wdate"/></td>
				<td class="lableTd" width="2%">至</td>
				<td width="5%"><input type="text" id="dateEnd" name="dateEnd" onfocus="WdatePicker({vel:'dateEnd',readOnly:true,firstDayOfWeek:1})" class="Wdate"/></td>
				<div id="workDays" sytle="display:none"></div>
			</tr>
		</table>
		</form>
	</div>
	<div style="width:100%">
		<div style="width:48%;float:left;">
			<table id="deptResult" class="table">
				<thead>
					<tr>
						<th>事业部</th>
						<th>人数</th>
						<th>应签到次数</th>
						<th>实际签到次数</th>
						<th>签到率(%)</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div style="width:48%;float:right;">
			<table id="branchResult" class="table">
				<thead>
					<tr>
						<th>办事处/运营中心</th>
						<th>人数</th>
						<th>应签到次数</th>
						<th>实际签到次数</th>
						<th>签到率(%)</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	<div style="width:100%">
		<table id="employeeResult" class="table">
			<thead>
				<tr>
					<th>事业部</th>
					<th>办事处/运营中心</th>
					<th>姓名</th>
					<th>签到时间</th>
					<th>应签到天数  / 次数</th>
					<th>实际签到次数</th>
					<th>签到明细</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<iframe name="hiddenIframe" style="height: 0;width: 0;display: none;"></iframe>
	<h3>技术支持:管理工程部</h3>
<script type="text/javascript">
if ( !Array.prototype.forEach ) {

  Array.prototype.forEach = function forEach( callback, thisArg ) {

    var T, k;

    if ( this == null ) {
      throw new TypeError( "this is null or not defined" );
    }
    var O = Object(this);
    var len = O.length >>> 0; 
    if ( typeof callback !== "function" ) {
      throw new TypeError( callback + " is not a function" );
    }
    if ( arguments.length > 1 ) {
      T = thisArg;
    }
    k = 0;

    while( k < len ) {

      var kValue;
      if ( k in O ) {

        kValue = O[ k ];
        callback.call( T, kValue, k, O );
      }
      k++;
    }
  };
}

$(function(){
	//加载事业部列表
	$.ajax({
		url:"getOrgConfig.do",
		type: "post",
		dataType: "json",
		success:function(data){
			var array=[];
			data.forEach(function(item){
				array.push("<option value='"+item+"'>"+item+"</option>");
			});
			$("#deptSel").html(array.join(""));
		}
	});
	
	$.ajax({
		url:"getBranchConfig.do",
		type: "post",
		data:{org:""},
		dataType: "json",
		success:function(data){
			$('#branchSel').selectMultiple('destroy');
			var array=[];
			array.push("<option value=''></option>");
			data.forEach(function(item){
				if(null!=item && ""!=item){
					array.push("<option value='"+item+"'>"+item+"</option>");
				}
			});
			$("#branchSel").html(array.join(""));
			$('#branchSel').selectMultiple({});
		}
	});
	
	$("#deptSel").change(function(){
		var org = this.value;
		$.ajax({
			url:"getBranchConfig.do",
			type: "post",
			data:{org:org},
			dataType: "json",
			success:function(data){
				$('#branchSel').selectMultiple('destroy');
				var array=[];
				array.push("<option value=''></option>");
				data.forEach(function(item){
					if(null!=item && ""!=item){
						array.push("<option value='"+item+"'>"+item+"</option>");
					}
				});
				$("#branchSel").html(array.join(""));
				$('#branchSel').selectMultiple({});
			}
		});
		changeEmployee();
	});
	
	$("#branchSel").change(function(){
		changeEmployee();
	});
	
	//员工复选
	$('#employeeMultSel').selectMultiple({});
});

function changeEmployee(){
	$('#employeeMultSel').selectMultiple('destroy');
	var org = $("#deptSel").val();
	var branchSelected = $('#branchSel').val();
	var braches = (null==branchSelected)?null:branchSelected.join(",");
	$.ajax({
		url:"getEmployeeConfig.do",
		type: "post",
		data:{org:org,branch:braches},
		dataType: "json",
		success:function(data){
			var array=[];
			data.forEach(function(item){
				array.push("<option value='"+item.code+"'>"+item.name+"</option>");
			});
			$("#employeeMultSel").html(array.join(""));
			$('#employeeMultSel').selectMultiple({});
		}
	});
}

function queryDatas(){
	var empSelected = $('#employeeMultSel').val();
	var employees = (null==empSelected)?null:empSelected.join(",");
	var branchSelected = $('#branchSel').val();
	var braches = (null==branchSelected)?null:branchSelected.join(",");
	var info = {
		org:$("#deptSel").val(),
		branch:braches,
		dateBegin:$("#dateBegin").val(),
		dateEnd:$("#dateEnd").val(),
		employees:employees
	};
	if(""==info.dateBegin||""==info.dateEnd){
		alert("请选择起始日期!");
		return;
	}
	$.blockUI({
        message: '<h2>查询中，请稍候...</h2>',
        css: { border: '1px solid #fff' }
    });
	$.ajax({
		url:"queryDataDetail.do",
		type: "post",
		data:info,
		dataType: "json",
		success:function(data){
			var checkRange = data.checkRange;
			var checkCount = data.checkCount;
			var orgHtml = [];
			if(null!=data.orgData){
				data.orgData.forEach(function(item){
					orgHtml.push('<tr>');
					orgHtml.push('<td>'+item[0]+'</td>');
					orgHtml.push('<td>'+item[1]+'</td>');
					orgHtml.push('<td>'+item[4]+'</td>');
					orgHtml.push('<td>'+item[2]+'</td>');
					orgHtml.push('<td>'+item[3]+'</td>');
					orgHtml.push('</tr>');
				});
			}
			$("#deptResult tbody").html(orgHtml.join(""));
			var branchHtml = [];
			if(null!=data.branchData){
				data.branchData.forEach(function(item){
					branchHtml.push('<tr>');
					branchHtml.push('<td>'+item[0]+'</td>');
					branchHtml.push('<td>'+item[1]+'</td>');
					branchHtml.push('<td>'+item[4]+'</td>');
					branchHtml.push('<td>'+item[2]+'</td>');
					branchHtml.push('<td>'+item[3]+'</td>');
					branchHtml.push('</tr>');
				});
			}
			$("#branchResult tbody").html(branchHtml.join(""));
			var empHtml=[];
			if(null!=data.employeeData){
				data.employeeData.forEach(function(item){
					empHtml.push('<tr id="emp_'+item.code+'"');
					if(item.attCount==0&&item.shouldCheckCount>0){
						empHtml.push(' class="error"');
					}else if(item.attCount == item.shouldCheckCount){
						empHtml.push(' class="allcheck"');
					}
					empHtml.push('>');
					empHtml.push('<td>'+item.org+'</td>');
					empHtml.push('<td>'+(item.branch==null?'':item.branch)+'</td>');
					empHtml.push('<td>'+item.name+'</td>');
					empHtml.push('<td>'+checkRange+'</td>');
					empHtml.push('<td>'+eval(item.shouldCheckCount)/2+' / '+item.shouldCheckCount+'</td>');
					empHtml.push('<td>'+item.attCount+'</td>');
					empHtml.push('<td><a onclick="viewDetail(\''+item.name+'\',this)">查  看</a></td>');
					empHtml.push('</tr>');
				})
			}
			$("#employeeResult tbody").html(empHtml.join(""));
			data.employeeData.forEach(function(item){
				$("#emp_"+item.code).data(item.details);
			});
			$("#workDays").data("workDays",data.workDays);
			$.unblockUI();
		}
	});
}

function viewDetail(name,obj){
	var workDays = $("#workDays").data("workDays");
	var datas = $(obj).parents("tr").data();
	var html = [];
	html.push("<div style='width:400px;height:400px;overflow:auto;'><table><thead><tr><th>打卡日期</th><th>打卡次数</th><th>上班时间</th><th>下班时间</th></thead><tbody>");
	
	for(var key in workDays){
		html.push("<tr>");
		html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'>"+workDays[key]+"</td>");
		if(datas[workDays[key]]){
			var checkType = datas[workDays[key]].checkType;
			var onjob = (null==datas[workDays[key]].onjob)?"":datas[workDays[key]].onjob;
			var leavejob = (null==datas[workDays[key]].leavejob)?"":datas[workDays[key]].leavejob;
			if(null==checkType||""==checkType){
				html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'>"+datas[workDays[key]].checkCount+"</td>");
				html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'>"+onjob+"</td>");
				html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'>"+leavejob+"</td>");
			}else{
				html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'>"+checkType+"</td>");
				html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'></td>");
				html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'></td>");
			}
		}else{
			html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'>0</td>");
			html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'></td>");
			html.push("<td style='border: 1px solid #fff;background: #e5f1f4;'></td>");
		}
		html.push("</tr>");
	}
	html.push("</tbody></table></div>");
	var d = dialog({
	    title: name+'打卡明细',
	    content: html.join("")
	});
	d.show();
}

</script>	
</body>
</html>