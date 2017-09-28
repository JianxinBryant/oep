<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/statics/js/jquery-easyui-1.5.3/themes/bootstrap/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/statics/js/jquery-easyui-1.5.3/themes/icon.css" rel="stylesheet">
<!-- 先引入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.9.1.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<title>考试管理</title>
</head>
<script type="text/javascript">
	$(function() {
		//表格
		$('#tb').datagrid({
			url : '${pageContext.request.contextPath}/ems/search',
			title : "考试查询" ,
			queryParams : {
				key : $("#searchBox").val()
			},
			fit : true,
			pageNumber : 1,
			pageSize : 20,
			pageList : [ 1, 2 , 3, 5, 10, 15, 20 ,30 ,40 ,50 ],
			columns : [[
				{field : 'examId', title : '考试id', width : 100, align : 'center',hidden:true}, 
				{field : 'examName',title : '考试名',width : 100,align : 'center'}, 
				{field : 'examStartTime',title : '考试开始时间',width : 200,align : 'center'}, 
				{field : 'examEndTime',title : '考试结束时间',width : 200,align : 'center'}, 
				{field : 'examTotal',title : '总分',width : 100,align : 'center'}, 
				{field : 'examState',title : '状态',width : 100,align : 'center'}, 
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
				//考试名
				$("#eName").textbox("setValue","");
				//考试开始时间
				$("#eStartTime").datetimebox("setValue","");
				//考试结束时间
				$("#eEndTime").datetimebox("setValue","");
				//总分
				$("#eTotal").numberspinner("setValue","");  
				$("#panel").window({title:"新增考试"});
				$("#panel").window("open","true");
				//保存试卷信息,进入考题设置界面
				$("#saveBtn").linkbutton({
					text : "保存",
					onClick : function(){
						insertExam();						
					}
				});
				//location.href = "${pageContext.request.contextPath}/ems/examDetail.jsp";
			}
		});
		//编辑
		$("#editBtn").linkbutton({
			iconCls : "icon-edit",
			plain : "true",
			text : "编辑",
			onClick : function(){
				var editrow = $('#tb').datagrid('getSelected');
				if(editrow == null) return;
				var exam = {
					examId : editrow.examId,
					examName : editrow.examName,
					examStartTime : editrow.examStartTime,
					examEndTime : editrow.examStartTime,
					examTotal : editrow.examTotal
				};
				//考试名
				$("#eName").textbox("setValue",editrow.examName);
				//考试开始时间
				$("#eStartTime").datetimebox("setValue",editrow.examStartTime);
				//考试结束时间
				$("#eEndTime").datetimebox("setValue",editrow.examEndTime);
				//总分
				$("#eTotal").numberspinner("setValue",editrow.examTotal);  
				$("#panel").window({title:"更新考试"});
				$("#panel").window("open","true");
				aa = 0;
				//保存试卷信息,进入考题设置界面
				$("#saveBtn").linkbutton({
					text : "更新",
					onClick : function(){
						updateExam(editrow.examId);		
					}
				});
			}
		});
		//删除
		$("#delBtn").linkbutton({
			iconCls : "icon-remove",
			plain : "true",
			text : "删除",
			onClick : function(){
				var delrows = $('#tb').datagrid('getSelections');
				var examId = [];
				//获取所有选中的exam的id
				for(var i = 0;i < delrows.length;i++){
					examId[i] = delrows[i].examId;
				}
				//异步请求删除选中的exam
				$.ajax({
					url:'${pageContext.request.contextPath}/ems/delete',
					type:'post',
					data:{
						examId : examId
					},
					dataType : "json",
					success : function(data){
				    	$.messager.show({
	                        title:'Message',
	                        msg:data.msg,
	                        showType:'fade',
	                        style:{
	                            right:'',
	                            top:document.body.scrollTop+document.documentElement.scrollTop,
	                            bottom:''
	                        }
	                    });
						if(data.result == true){
							//删除成功并刷新数据
							$('#tb').datagrid("reload");
						}
					}
				});
			}
		});
		$("#panel").window({
			title : "预览",
			collapsible : false,
			maximizable : false,
			minimizable : false,
			closed : true,
			draggable : false,
			resizable : false,
			modal:true
		});
		//面板
		$("#panel").panel({
			title : "新增考试",
		});
		//表单
		$("#ff").form({
			novalidate:true
		});
		//考试名
		$("input[name='examName'").textbox({
			label : '考试名:',
			required:true
		});
		//考试开始时间
		$("input[name='startTime'").datetimebox({   
			label : "开始时间:",
			width : 245,
		});
		//考试结束时间
		$("input[name='endTime'").datetimebox({    
			label : "结束时间:",
			width : 245,
		});
		var aa = 0;
		var dateboxval = '';
		//重写日期格式 
		$.fn.datebox.defaults.formatter = function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			if(m<10&&m>0)
				m = '0'+m;
			var d = date.getDate();
			if(aa%3 == 0){
				dateboxval = y+'年'+m+'月'+d+'日';
			}
			aa++;
			return dateboxval;
		}
		//总分
		$("input[name='examTotal'").numberspinner({    
			label : "总分:",
		    min : 0,
		    max : 1000,
		    editable : true,
		    width : 245
		});  
		//保存
		function insertExam(){
			$("#ff").form("submit",{
			    url : "${pageContext.request.contextPath}/ems/insert",
			    success : function(data){
			    	var json = eval("("+data+")");
			    	$.messager.show({
                        title:'Message',
                        msg:json.msg,
                        showType:'fade',
                        style:{
                            right:'',
                            top:document.body.scrollTop+document.documentElement.scrollTop,
                            bottom:''
                        }
                    });
			    	if(json.result){
			    		$('#saveBtn').linkbutton({
			    				text : "下一步",
			    				onClick : function(){
			    					location.href = json.url;
			    				}
			    		});
			    	}
			    }   
			});
		}
		//更新
		function updateExam(examId){
			$("#ff").form({
				queryParams : {
					examId : examId
				}
			});
			$("#ff").form("submit",{
			    url : "${pageContext.request.contextPath}/ems/update",
			    success : function(data){
			    	var json = eval("("+data+")");
			    	if(json.result){
			    		$("#tb").datagrid("reload");
			    		$('#saveBtn').linkbutton({
			    			text : "设置试题",
			    			onClick : function(){
			    				location.href = json.url;
			    			}
			    		});
			    	}                            
			    	$.messager.show({
                        title:'Message',
                        msg:json.msg,
                        showType:'fade',
                        style:{
                            right:'',
                            top:document.body.scrollTop+document.documentElement.scrollTop,
                            bottom:''
                        }
                    });
			    }   
			});
		}
		$("#clearBtn").linkbutton({
			text : "重置",
			onClick : function(){
				$("#ff").form("clear");
			}
		});
	});
</script>
<body>
    <table id="tb" ></table>
    <div id="searchtool" style="height: 30px;">
    	<div style="float: right;padding:2px 5px;">
    	<input id='searchBox'/>
        <a id="searchBtn" href="#" >Search</a>
    	</div>
    </div>
    <div id="ft" style="padding:2px 5px;">
        <a id="addBtn"></a>
        <a id="editBtn"></a>
        <a id="delBtn"></a>
        <p style="float: right;font-size: 5px;margin-top: 5px;margin-bottom: 0px;">按住Ctrl可多选</p>
    </div>
    
    <div id="panel" style="width:100%;max-width:630px;padding:30px 60px;">
        <form id="ff" method="post">
            <div style="margin-bottom:20px">
                <input id="eName" name="examName" style="width:100%" >
            </div>
            <div style="margin-bottom:20px">
            <span>
            	<span style="width:30%">
                	<input id="eStartTime" name="startTime" >
            	</span>
            	<span style="width:30%;margin-left: 2px;">
	                <input id="eEndTime" name="endTime"  >
            	</span>
            </span>
            </div>
            <div style="margin-bottom:20px">
                <input id="eTotal" name="examTotal">
            </div>
        </form>
        <div style="text-align:center;padding:5px 0">
            <a id="saveBtn" style="width:80px"></a>
            <a id="clearBtn" style="width:80px"></a>
        </div>
    </div>
</body>
</html>