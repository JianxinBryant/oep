<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/statics/themes/default/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/statics/themes/icon.css" rel="stylesheet">
<!-- 先引入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.9.1.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.easyui.min.js"></script>
<title>题库管理</title>
</head>
<script type="text/javascript">
	$(function() {
		//表格
		$('#tb').datagrid({
			url : '${pageContext.request.contextPath}/tiku/showitem',
			title : "题库管理" ,
			queryParams : {
				key : $("#searchBox").val()
			},
			pageNumber : 1,
			pageSize : 30,
			pageList : [  30 , 50 , 60  ],
			columns : [[
				{field : 'ck' , checkbox : true},
				{field : 'q_id', title : '试题id', width : 100, align : 'center',hidden:true}, 
				{field : 'q_content',title : '试题内容',width : 100,align : 'center'}, 
				{field : 'q_answer',title : '试题答案',width : 100,align : 'center'}, 
				{field : 'o_content',title : '选择题详细答案',width : 100,align : 'center'}, 
			]],
			ctrlSelect:true,
            border: true,  
			pagination : true,
			rownumbers : true,
			fitColumns : true,
	    	toolbar: '#searchtool',
	    	footer:'#ft'
	    });
		//搜索输入框
		$("#searchBox").textbox();
		//搜索按钮
		$("#searchBtn").linkbutton({
			url : '${pageContext.request.contextPath}/tiku/searchitem',
			iconCls : "icon-search",
			onClick : function(){
				//重载表格
				$('#tb').datagrid("load",{
					key : $("#searchBox").val()
				});
			}
		});
		//新增
		$("#addBtn").linkbutton({
			iconCls : "icon-add",
			plain : "true",
			text : "添加",
			onClick : function(){
				//增加页面
				location.href = "${pageContext.request.contextPath}/zy/additem.jsp";
			}
		});
		//编辑///////////////////////////////////////////////////////////////////////
		$("#editBtn").linkbutton({
			iconCls : "icon-edit",
			plain : "true",
			text : "编辑",
			onClick : function(){
				location.href = "${pageContext.request.contextPath}/zy/update.jsp";
			}
		});
		//*****************************************************************************
		//删除
		$("#delBtn").linkbutton({
			iconCls : "icon-remove",
			plain : "true",
			text : "删除",
			onClick : function(){
				var delrows = $('#tb').datagrid('getSelections');
				var q_id = [];
				//获取所有选中的item的id
				for(var i = 0;i < delrows.length;i++){
					q_id[i] = delrows[i].q_id;
				}
				//异步请求删除选中的item
				$.ajax({
					//删除页面
					url:'${pageContext.request.contextPath}/tiku/deleteitem',
					type:'post',
					data:'q_id='+q_id,
					dataType : "json",
					success : function(data){
						$('#tb').datagrid("reload");
						//if(data.result == true){
						//删除成功并刷新数据
						//	alert(data.msg);
						//	$('#tb').datagrid("reload");
						//}else{
						//	alert(data.msg);
						//}
					}
				});
			}
		});
	});
</script>
<body>

    <table id="tb" ></table>
    <div align="center">
    <a href="${pageContext.request.contextPath}/llj/mainadmin.jsp"><button>返回</button></a>
	<a href="${pageContext.request.contextPath}/hwx/Login.jsp"><button>退出</button></a>
    </div>
    <div id="searchtool" style="height: 30px;">
    	<div style="float: right;padding:2px 5px;">
    	<input id='searchBox'/>
        <a id="searchBtn" href="#" >Search</a>
    	</div>
    </div>	
    <div id="ft" style="padding:2px 5px;">
        <a id="addBtn"></a>
        <!--  <a id="editBtn"></a>-->
        <a id="delBtn"></a>
        <p style="float: right;font-size: 5px;margin-top: 5px;margin-bottom: 0px;"></p>
    </div>
</body>
</html>