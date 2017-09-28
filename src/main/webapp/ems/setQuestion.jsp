<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/statics/js/jquery-easyui-1.5.3/themes/gray/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/statics/js/jquery-easyui-1.5.3/themes/icon.css" rel="stylesheet">
<!-- 颜色 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/statics/js/jquery-easyui-1.5.3/themes/color.css">
<!-- 先引入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.9.1.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<!-- 引入datagrid分类js -->
<script type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-groupview.js"></script>
<title>设置考题信息</title>
</head>
<script type="text/javascript">
	$(function(){
		//已选的题型及其分数、题数
		var typeInfo = [];
		//所有题型信息
		var allType = [];
		$(".easyui-layout").layout({fit:true});
		//获取考试基本信息
		$.ajax({
			url : "${pageContext.request.contextPath}/ems/getExamBaseInfo",
			type : "post",
			success : function(json){
				var data = eval("("+json+")");
				var infoSpan = $("span[name='examInfo']");
				infoSpan.eq(0).html(data.examName);
				infoSpan.eq(1).html(data.examTime);
				infoSpan.eq(2).html(data.examTotal);
				infoSpan.eq(3).html(data.examState);
			}
		});
		//设置考题的table
		$("#setQuestionType").datagrid({
		    url:'${pageContext.request.contextPath}/ems/getQuestionType',
		    width : 305,
		    columns:[[    
		        {field:'ck',checkbox:true,align:'center'},    
		        {field:'typeId',title:'题型id',width:50,align:'center',hidden:true},    
		        {field:'typeName',title:'题型',width:100,align:'center'},    
		        {field:'questionNumber',title:'题数',width:50,align:'center',editor:{
	                type:'numberspinner',
	                options:{
	                	min : 0,
	                	max : 100,
	                	editable: true
	                }  	
		        }},    
		        {field:'questionScore',title:'每题分数',width:75,align:'center',editor:{
	                type:'numberspinner',
	                options:{
	                	min : 0,
	                	max : 100,
	                	editable: true
	                }
		        }},    
		        {field:'questionTotal',title:'总分数',width:50,align:'center'}    
		    ]],
		    border : true,
		    selectOnCheck : false,
		    checkOnSelect : false,
		    //编辑单元格
		    onClickCell : function(index, field, value){
				if (endEditing()){
					var tb = $('#setQuestionType');
					tb.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
					var data = tb.datagrid("getData");
					if(previousIndex != undefined){
						var total = data.rows[previousIndex].questionNumber * data.rows[previousIndex].questionScore;
						tb.datagrid("updateRow",{
							index : previousIndex,
							row : {
								questionTotal : total,
							}
						});
					}
					//typeInfo更新
					updateTypeInfo()
					//更新题的分值
					var selectRows = $("#selectedQuestions").datagrid("getRows");
					for(var i = 0;i < selectRows.length;i++){
						for(var j = 0;j<typeInfo.length;j++){
							if(selectRows[i].typeId == typeInfo[j].typeId && 
									selectRows[i].score != typeInfo[j].score ){
								var state = "";
								if(selectRows[i].state == "uncommitted")
									state = "unconmitted";
								else 
									state = "update";
								$("#selectedQuestions").datagrid("updateRow",{
									index : i,
									row : {
										state : state,
										score : typeInfo[j].score,
									}
								});
							}
						}
					}
					setColor();
					editIndex = index;
				}
		    },
		    onLoadSuccess : function(data){
		    	for(var i = 0;i < data.rows.length;i++){
		    		if(data.rows[i].questionNumber != 0){
		    			$(this).datagrid("checkRow",i);
		    		}
		    	}
		    	updateTypeInfo();
				$("#selectType").combobox({  
					data : data.rows
				});
    			var selectedTotal = 0;
    			for(var i = 0;i < typeInfo.length;i++){
    				selectedTotal = selectedTotal+Number(typeInfo[i].total);
    			}
    			$("#selectedTotal").html(selectedTotal);
		    },
		    onUncheck : function(index, row){//取消设置某些考题
		    	$.messager.confirm('警告',
		    			'<h4>此操作会删除该考试全部 '+row.typeName+' !</h4><br/><p align="center">您确认要执行此吗？</p>',
		    		function(r){ 
		    	    if (r){
		    	    	//删除该类试题
	                    var rows = $("#selectedQuestions").datagrid("getRows");
	                    var ids = [];
	                    var j = 0;
	                    for(var i = 0;i < rows.length;i++){
	                    	if(rows[i].typeId == row.typeId)
	                    		ids[j++] = rows[i].id;
	                    }
	                    if(ids.length == 0) return;
	                	removeQuestion(ids);
	                	updateTypeInfo();
	                }else{
		    	    	$("#setQuestionType").datagrid('checkRow',index);
		    	    }
		    	});  
		    },
		    onUncheckAll : function(rows){//取消设置全部考题
		    	$.messager.confirm('警告',
		    			'<h4>此操作会删除该考试全部试题!</h4><br/><p align="center">您确认要执行此吗？</p>',
		    		function(r){ 
		    	    if (r){
		    	    	//删除该类试题
	                    var qrows = $("#selectedQuestions").datagrid("getRows");
	                    var ids = [];
	                    for(var i = 0;i < qrows.length;i++){
	                    	ids[i] = qrows[i].id;
	                    }
	                    if(ids.length == 0) return;
	                	removeQuestion(ids);	
	                	updateTypeInfo();
		    	    }else{
		    	    	for(var i = 0;i < rows.length;i++)
		    	    		$("#setQuestionType").datagrid('checkRow',i);
		    	    }
		    	});  
		    },
		});
		function updateTypeInfo(){
			var typeRows = $("#setQuestionType").datagrid("getChecked");
			var types = [];
			for(var i = 0;i < typeRows.length;i++){
				types[i] = {
					typeId : typeRows[i].typeId,
					typeName : typeRows[i].typeName,
					number : typeRows[i].questionNumber,
					score : typeRows[i].questionScore,
					total : typeRows[i].questionTotal
				};
			}
			typeInfo = types;
		}
		//editCell方法
		$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
				});
			}
		});
		//是否结束编辑
		var editIndex = undefined;
		var previousIndex = undefined;
		function endEditing(){
			var tb = $('#setQuestionType');
			if (editIndex == undefined){return true}
			if (tb.datagrid('validateRow', editIndex)){
				tb.datagrid('endEdit', editIndex);
				//记录上一个编辑的索引
				previousIndex = editIndex;
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		};
		//已选择的选择题
		$("#selectedQuestions").datagrid({
			title : "已选题库",
			url : '${pageContext.request.contextPath}/ems/getQuestionOfExam',
			height : 300,
		    columns:[[    
				{field:'ck',checkbox:true,align:'center'},    
				{field:'id',title:'编号',width:25,align:'center'},    
				{field:'typeId',title:'题型编号',width:50,align:'center',hidden:true},
				{field:'typeName',title:'题型',width:50,align:'center'},
				{field:'content',title:'内容',width:200,align:'center'},    
				{field:'answer',title:'答案',width:50,align:'center'},
				{field:'score',title:'分数',width:50,align:'center',hidden:true},
				{field:'state',title:'提交',width:50,align:'center',hidden:true},
			]],
            collapsible:true,
            rownumbers:true,
            fitColumns:true,
            view : groupview,
            groupField:'typeName',
            groupFormatter:function(value,rows){
                return value + ' - 分值:' +rows[0].score +'  - 总数:'+ rows.length;
            },
			toolbar : [{
            	text:'保存',
                iconCls:'icon-save',
                //新增试题
                handler:function(){
                	var rows = $("#selectedQuestions").datagrid("getRows");
                	var insertQuestion = [];
                	var updateQuestion = [];
                	var j = 0, k = 0;
                	for(var i = 0;i < rows.length;i++){
                		//新增的试题
                		if(rows[i].state == 'uncommitted'){
                			insertQuestion[j++] = {
                				qid : rows[i].id,
                				score : getScoreOfType(rows[i].typeId)
                			};
                		}
                		//更新的试题
                		if(rows[i].state == 'update'){
                			updateQuestion[k++] = {
                				qid : rows[i].id,
                				score : getScoreOfType(rows[i].typeId)
                			};
                		}
                	}
                	if(insertQuestion.length+updateQuestion.length == 0){
						return;                		
                	}
                	//json 转换为 String
                	var insertQuestionJson = JSON.stringify(insertQuestion);
                	var updateQuestionJson = JSON.stringify(updateQuestion);
                	//提交未添加的试题
                	$.ajax({
                		url : "${pageContext.request.contextPath}/ems/setQuestion",
                		type : "post",
                		data : {
                			insertQuestion : insertQuestionJson,
                			updateQuestion : updateQuestionJson
                		},
                		dataType : "json",
                		success : function(data){
                       		var msg;
                    		if(data.result == true){
                    			msg = '设置试题成功';
                    			$("#selectedQuestions").datagrid("reload");
                    			$("#setQuestionType").datagrid("reload");
                    		}else{
                    			msg = '设置试题失败';
                    		}
                            $.messager.show({
                                title:'Message',
                                msg:msg,
                                showType:'fade',
                                style:{
                                    right:'',
                                    top:document.body.scrollTop+document.documentElement.scrollTop,
                                    bottom:''
                                }
                            });
                		}
                	});
                }},"-",{
				text:'删除',
                iconCls:'icon-remove',
                handler:function(){	
                    var rows = $("#selectedQuestions").datagrid("getSelections");
                    var ids = [];
                    for(var i = 0;i < rows.length;i++){
                    	ids[i] = rows[i].id;
                    }
                    if(ids.length == 0){
                    	return;
                    }
                	removeQuestion(ids);
                }
			}]
		});
		function removeQuestion(ids){
            $.ajax({
            	//remove试题
            	url : '${pageContext.request.contextPath}/ems/removeQuestionOfExam',
            	type : 'post',
            	data : {
            		ids : ids
            	},
            	dataType : 'json',
            	success : function(data){
            		var msg; 
            		if(data.result){
            			msg = '删除成功';
            			$("#selectedQuestions").datagrid("reload");
            		}else{
            			msg = '删除失败';
            		}
                    $.messager.show({
                        title:'Message',
                        msg:msg,
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
		//选择题查询面板
		$("#questionBank").datagrid({
			title : '题库',
			url : "${pageContext.request.contextPath}/ems/getQuestion",
			height : 308,
			queryParams : {
				questionTypeId : "",
				key :  $("#searchBox").val()
			},
			pageNumber : 1,
			pageSize : 10,
			pagination : true,
			pageList : [10,20,30,40,50],
			rownumbers : true,
			collapsible:true,
			fitColumns : true,
		    columns:[[    
				{field:'ck',checkbox:true,align:'center'},    
				{field:'id',title:'编号',width:25,align:'center'},
				{field:'typeId',title:'题型编号',width:20,align:'center',hidden:true},
				{field:'typeName',title:'题型',width:50,align:'center'},
				{field:'content',title:'内容',width:200,align:'center'},    
				{field:'answer',title:'答案',width:50,align:'center'},    
			]],
	    	toolbar : '#searchtool',
		});
		$("#questionBank").datagrid("getPager").pagination({
            buttons : [{
            	text : '添加',
                iconCls : 'icon-add',
                handler : function(){
                	var tb2 = $("#questionBank");
                	var rows = tb2.datagrid("getSelections");//获取所有选择的行
                	var tb1 = $("#selectedQuestions");
                	var selectedQuestion = tb1.datagrid("getData").rows;//获取已选全部题
                	var addNumber = 0;
                	for(var i = 0;i < rows.length;i++){
                		var mark1 = true;
                		var mark2 = false;
                		//若已选,则不添加
                		for(var j = 0;j < selectedQuestion.length;j++){
	                		if(selectedQuestion[j].id == rows[i].id){
    	            			mark1 = false;
        	        		}
                		}
                		updateTypeInfo();
                		//若不包含该类题型则不添加
                		for(var j = 0;j < typeInfo.length;j++){
                			if(rows[i].typeId == typeInfo[j].typeId){
                				mark2 = true;
                			}
                		}
                		if(mark1 && mark2){//添加行
	                		tb1.datagrid('appendRow',{
    	            			id : rows[i].id,
    	            			typeId : rows[i].typeId,
    	            			typeName : rows[i].typeName,
        	        			content : rows[i].content,
            	    			answer : rows[i].answer,
            	    			score : getScoreOfType(rows[i].typeId),
            	    			state : "uncommitted"
                			});
                			addNumber++;
                		}
                	}
                	setColor();
                	tb2.datagrid("uncheckAll");//清除勾选
                }
            }]
        });
		//类型选择框
		$("#selectType").combobox({  
			//url : "${pageContext.request.contextPath}/ems/getQuestionType",
			width : 100 ,
			valueField : 'typeId',    
		    textField : 'typeName',
		    editable : false,
		    panelHeight : 120,
		    value : '请选择题型',
		    data : typeInfo
		});
		//搜索输入框
		$("#searchBox").textbox({
			width : 200
		});
		//搜索按钮
		$("#searchBtn").linkbutton({
			iconCls : "icon-search",
			onClick : function(){
				var questionTypeId = $("#selectType").combobox("getValue");
				if(questionTypeId == "请选择题型")
					return;
				//重载表格
				$('#questionBank').datagrid("load",{
					questionTypeId : questionTypeId,
					key : $("#searchBox").val()
				});
			}
		
		});
		$("#issuebtn").linkbutton({
			text : "发        布",
			height : 60,
			width : 150,
			onClick : function(){
				$("#previewPanel").window("open","true");
				$("#previewPanel iframe").attr("src","${pageContext.request.contextPath}/ems/preview.jsp");
			}
		});
		$("#issuebtn span:last").css("font-size","20px");
		$("#previewPanel").window({
			//href : "${pageContext.request.contextPath}/ems/preview.jsp",
			width : 1200,
			height : 600,
			title : "预览",
			collapsible : false,
			maximizable : false,
			minimizable : false,
			closed : true,
			draggable : false,
			resizable : false,
			modal:true
		});
			
		/*
			获取某题型的分数
			@typeId 题型id
			@return 这种题型的分值
		*/
		function getScoreOfType(typeId){
			var score = -1;
			for(var i = 0;i < typeInfo.length;i++){
				if(typeInfo[i].typeId == typeId)
					score = typeInfo[i].score;
			}
			return score;
		}
		var btable = $(".datagrid-btable");
		//给未保存的记录作色
		function setColor(){
			var btable = $(".datagrid-btable");
			for(var j = 0;j < btable.size();j++){
	        	var trs = btable.eq(j).children().first().children();
	        	for(var i = 0;i < trs.size();i++){
	        		var stateCell = trs.eq(i).children().last().children().first();
	        		var state = stateCell.html();
	        		if(state == "uncommitted" || state == "update"){
	        			trs.eq(i).css("background","gray");
	        		}
	        	}
			}
		}
	});
</script>
<body>
	<div id="previewPanel"><iframe src="" style="width: 100%"></iframe></div>
	<div class="easyui-layout" style="width:100%;height:700px;">
        <div data-options="region:'north'" title="考试信息" style="height:110px">
        	<h2 style="float: right;">
        		<label>考试名:</label><span name='examInfo'></span>
        		<label>考试时间:</label><span name='examInfo'></span>
	        	<br/>
        		<label>考试总分:</label><span name='examInfo'></span>
        		<label>考试状态:</label><span name='examInfo'></span>
        	</h2>
        </div>
        <div  data-options="region:'east',split:false" title="选择考题类型" style="width: 307px;">
        	<!-- 设置考题 -->
			<table id="setQuestionType" >
			</table>
			<br/>
			<div style="text-align: center">
			<h2 >已选题总分:</h2>
			<h2 id="selectedTotal" ></h2>
			<a id="issuebtn" class="easyui-linkbutton c8" ></a>
			</div>
        </div>
        <div data-options="region:'center'" style="width: 100%;">
       		<!-- 单选题设置面板 -->
			<div id="singleChoicePanel" title="单选题">
				<!-- 已选面板 -->
				<div>
					<table id="selectedQuestions" style="width: 100%">
					</table>
				</div>
				<!-- 题库面板 -->
				<div>
					<table id="questionBank" style="width: 100%">
					</table>
					<!-- 搜索框 -->
			   	 	<div id="searchtool" style="height: 30px;">
			    		<div style="float: right;padding:2px 5px;">
			    			<input id='selectType'/>
			    			<input id='searchBox'/>
			        		<a id="searchBtn">Search</a>
			    		</div>
			    	</div> 
				</div>
			</div>
		</div>
       </div>
</body>
</html>