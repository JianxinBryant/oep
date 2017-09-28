<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/bootstrap/css/bootstrap.min.css" />
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath}/statics/themes/bootstrap/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath}/statics/themes/icon.css" rel="stylesheet">
<!-- 引入easyui.js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/easyui-lang-zh_CN.js"></script>
</head>
<script type="text/javascript">
$(function(){
	
	/* 对表格数据的查询 */
	$('#usertb').datagrid({
		url:"${pageContext.request.contextPath}/selectUser",
	    title:"用户管理",
	    queryParams : {
	    	userkey : $("#userkeybox").val()
		},
		pageNumber:1,			/* 初始化页码 */
		pageSize:5,				/* 初始化页面大小 */
		pageList:[5,10,15,20,50],/* 初始化页面大小选择列表 */
	    columns:[[
	        {field:'u_id',title:'用户ID',width:100,align:'center',hidden:true},    
	        {field:'u_name',title:'用户名',width:100,align:'center'},    
	        {field:'u_telephone',title:'联系电话',width:100,align:'center'},
	        {field:'u_email',title:'邮箱',width:100,align:'center'},    
	        {field:'u_registertime',title:'注册时间',width:100,align:'center'}
	    ]],
	    fitColumns:true,		/* 自动调整行距 */
	    toolbar:"#tools",		/* 顶部工具栏 */
	    rownumbers:true , 		/* 显示行列号 */
	    ctrlSelect:true,		/* 按住Ctrl多选 */
	    pagination:true			/* 显示分页工具栏,出现page,rows */
	});  
	
	/* 根据用户关键字查询用户信息 */
	$('#searchbtn').linkbutton({    
	    iconCls: 'icon-search' ,
	    url:"${pageContext.request.contextPath}/selectUser",
	    onClick:function(){
	    	//加载根据关键字搜索后的表格
	    	$('#usertb').datagrid("reload",{ 
	    		userkey : $("#userkeybox").val()
	    	})
	    }
	});  
	
	/* 删除用户 */
	//点击删除按钮，弹出删除对话框  
	$("#remove").linkbutton({
		iconCls:"icon-remove",
		onClick:function(){
			var ids = [];
			var rows = $('#usertb').datagrid('getSelections');
			//被选中的行数>0，执行删除
			if (rows.length>0){
				$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    	    	if (r){ 
	    	    		//将被选中的行的用户id放在一个数组中
	    				for(var i=0; i<rows.length; i++){
		    				ids.push(rows[i].u_id);
		    			}
		    			$.ajax({
		    				url:"${pageContext.request.contextPath}/removeUser",
		    				type:'post',
		    				 data:{
		    					 selectUid:  ids
		    				} , 
		    				dataType:'json',
		    				success:function(data){
		    					 $.messager.alert('提示',data.msg);
		    					 $('#usertb').datagrid("reload");
		    				}
		    			})
	    	   		 }    
	    		}); 
			}else{
				 $.messager.alert('提示',"请选择需要删除的记录");
			}
	    }
	}) 
	
	
	/* 编辑用户信息 */
	//弹出编辑窗口
	$("#edit").linkbutton({
		iconCls:"icon-edit",
		onClick : function() {
			var row = $('#usertb').datagrid('getSelected');
			if (row) {
				$('#editwin').dialog('open').dialog('center');
				$('#fm').form('load', row);		
			}else{
				 $.messager.alert('提示',"请选择一条需要修改的记录");
			}
		}
	})
	//保存修改后的用户信息
		$('#fm').form({  
			url:"${pageContext.request.contextPath}/editUser",
		    onSubmit: function(){ 
		    	var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.alert('提示',"请根据要求正确填写表单");
				}
				return isValid;	// 返回false终止表单提交
		    },
		    
		    success:function(data){
		    	var info=eval("("+data+")");
		    	$.messager.alert('提示',info.msg);
				$('#editwin').dialog('close');
				$('#usertb').datagrid("reload");  
		    }    
		});  
	$("#save").linkbutton({
		onClick : function() {
			$('#fm').form({
	    		queryParams : {
					uid : $("input[name='u_id']").val(),
			    	uname : $("input[name='u_name']").val(),
			    	utelephone:$("input[name='u_telephone']").val(),
			    	uemail : $("input[name='u_email']").val(),
			    	uregistertime : $("input[name='u_registertime']").val()
				},
	    	});
			$('#fm').submit(); 
		}
	})
	
	//编辑中的取消按钮
	$("#cancel").linkbutton({
		onClick : function() {
			$('#editwin').dialog('close').dialog('center');
		}
	})
	
	/* 编辑中的各项的值的验证 */	
 	$.extend($.fn.validatebox.defaults.rules, { 
		nameLength:{  //用户名为中文、英文、数字的组合且长度为4-10 
	    	    	validator: function(value){     
	    	        	return /^[a-zA-Z0-9\u4E00-\u9FA5]{4,10}$/.test(value);     
	    	        },     
	    	        message: '用户名可由中文、英文、数字组成，输入内容长度为4-10个字符'    
	    	    },
		phoneNum: { //验证手机号   
			validator: function(value){ 
				return /^1[3-8]+\d{9}$/.test(value);
				},    
			message: '请输入正确的手机号码'   
			},
		email: {  //验证电子邮箱   
	    	validator: function(value){     
	        	return /^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/.test($.trim(value));     
	        },     
	        message: '电子邮箱格式错误'    
	    } 
	}) 
})
</script>

<body>
<div>
	<!-- 表格内容 -->
	<div>
		<table id="usertb" ></table>
	</div>	
	<div id="tools" style="padding:2px 5px;">
        <a id="edit"  class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑用户</a>
        <a id="remove"  class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除用户</a>
        <div style="float:right">
    		<input id="userkeybox" type="text"/>
    		<a id="searchbtn"  class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    	</div>
    </div>
    
    <!-- 编辑弹窗 -->
    <div id="editwin" class="easyui-dialog" style="width:400px" title="编辑用户" closed="true"
             buttons="#dlg-buttons">
        <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">用户信息</div>
            <div style="margin-bottom:10px;display:none">
                	用户id：<input name="u_id" class="easyui-textbox" required="true" label="用户id:" style="width:100%;">
            </div>
            <div style="margin-bottom:10px">
                	用户名：<input name="u_name" class="easyui-textbox" required="true" label="用户名:" validType="nameLength" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                	联系电话：<input name="u_telephone" class="easyui-textbox" required="true" label="联系电话:" validType="phoneNum" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                	邮箱：<input name="u_email" class="easyui-textbox" required="true" label="邮箱:" validType="email" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                	注册时间：<input name="u_registertime" class="easyui-datebox" required="true" label="注册时间:" style="width:100%">
            </div>
        </form>
			<div id="footers" style="padding: 2px 5px;float:right;">
				<a id="save" class="easyui-linkbutton" iconCls="icon-save" plain="true" style="width:100px" >保存</a> 
				<a id="cancel" class="easyui-linkbutton" iconCls="icon-cancel"	plain="true" style="width:100px">取消</a>
			</div>
		</div>  
	</div>

</body>
</html>