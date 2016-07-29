var pageInfo = {
	pageSize:20,
	currentPage:1,
	totalCount:0,
	pageCount:0
};

$(function(){
	goPage(1);
});

function addPage(add){
	goPage(pageInfo.currentPage+add);
}

function goPage(page){
	pageInfo.currentPage = page;
	initCondition();
	$.ajax({
		url:$("#conditionTable").attr("url"),
		type: "post",
		data: pageInfo,
		dataType: "json",
		success:function(data){
			pageInfo = data.page;
			initResultContent(data.datas);
			initPage();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			
		}
	});
}

function initPage(){
	var html = '<span>共<b>'+pageInfo.totalCount+'</b>条记录,每页<b>'+pageInfo.pageSize+'</b>条,当前第<b>'+pageInfo.currentPage+'</b>页,共<b>'+pageInfo.pageCount+'</b>页</span>'
		+'<button type="button" onclick="goPage(1)" style="width:60px"'+(pageInfo.currentPage==1?'disabled':'')+'>首页</button>'
		+'<button type="button" onclick="addPage(-1)" style="width:80px"'+(pageInfo.currentPage==1?'disabled':'')+'>上一页</button>'
		+'<button type="button" onclick="addPage(1)" style="width:80px"'+(pageInfo.currentPage==pageInfo.pageCount?'disabled':'')+'>下一页</button>'
		+'<button type="button" onclick="goPage(pageInfo.pageCount)" style="width:60px"'+(pageInfo.currentPage==pageInfo.pageCount?'disabled':'')+'>尾页</button>';
	$("#pageInfo").html(html);
}

function initCondition(){
	var conds = $("#conditionTable").find("input");
	conds.each(function(){
		if(null!=this.value){
			pageInfo[this.name]=this.value;
		}
	});
}


function initResultContent(datas){
	var headers = $("#resultTable thead th");
	var rowHtml = [];
	datas.forEach(function(data){
		rowHtml.push("<tr");
		if(data.className && ""!=data.className){
			rowHtml.push(" class='"+data.className+"'>");
		}
		rowHtml.push(">");
		headers.each(function(){
			rowHtml.push("<td class='"+this.className+"'>");
			rowHtml.push(data[this.className]);
			rowHtml.push("</td>");
		});
		rowHtml.push("</tr>");
	});
	$("#resultTable tbody").html(rowHtml.join(""));
}

function DropDown(el) {
	this.dd = el;
	this.initEvents();
}

DropDown.prototype = {
	initEvents : function() {
		var obj = this;

		obj.dd.live('click', function(event){
			$(this).toggleClass('active');
			event.stopPropagation();
		});	
	}
}

if (!Array.prototype.forEach) {

	Array.prototype.forEach = function forEach(callback, thisArg) {

		var T, k;

		if (this == null) {
			throw new TypeError("this is null or not defined");
		}
		var O = Object(this);
		var len = O.length >>> 0;
		if (typeof callback !== "function") {
			throw new TypeError(callback + " is not a function");
		}
		if (arguments.length > 1) {
			T = thisArg;
		}
		k = 0;

		while (k < len) {

			var kValue;
			if (k in O) {

				kValue = O[k];
				callback.call(T, kValue, k, O);
			}
			k++;
		}
	};
}