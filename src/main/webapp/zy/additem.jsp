<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>题库管理页面</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
	<script>
	$(function () {
		$('#myTab li:eq(0) a').tab('show');
	});
	</script>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class = "navbar-header">
				<a class = "navbar-brand" href="#">试题添加</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul id="myTab" class="nav navbar-nav nav-tabs">
					<li><a href="#Add" data-toggle="tab">单选题</a>
					<li><a href="#Search" data-toggle="tab">大题</a>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${pageContext.request.contextPath}/zy/item.jsp">返回</a></li>
					<li><a href="${pageContext.request.contextPath}/hwx/Login.jsp">退出</a></li>
				</ul>
			</div>
		</div>
	</nav>
<div id="myTabContent" class="tab-content">
<div class="tab-pane fade in active " id="#">
 	<h2 align="center">Welcome</h2>
</div>
<div class="tab-pane fade" id="Add">
 	<!-- 增加题目  -->
 	<form method="post" action="${pageContext.request.contextPath}/tiku/additem" >
 	<div class="container-fluid" style="border-top: 54px">
		<div class="row">
			<div class="col-md-10" align="center" style="margin-top: 10%">
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-1" align="right">
			</div>
		</div>
		<h3 align="center">选择题</h3>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">问题描述</span>
				<input type="text" class="form-control" name="addquestiondescribe" id="wentimiaoshu">
				</div>
			</div>
		</div>
		<!-- 选择题  -->
		<br>
		<br>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">选项A</span>
				<input type="text" class="form-control" name="addselectA">
				</div>
			</div>
		</div>


		<br>
		<br>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">选项B</span>
				<input type="text" class="form-control" name="addselectB">
				</div>
			</div>
		</div>
		
		<br>
		<br>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">选项C</span>
				<input type="text" class="form-control" name="addselectC">
				</div>
			</div>
		</div>
		
		<br>
		<br>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">选项D</span>
				<input type="text" class="form-control" name="addselectD">
				</div>
			</div>
		</div>
		
		<br>
		<br>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">标准答案</span>
				<select
							class="form-control" onchange="selectOnchang(this)" name="addrightselect">
							<option value="a">A</option>
							<option value="b">B</option>
							<option value="c">C</option>
							<option value="d">D</option>
						</select>
				</div>
			</div>
		</div>
		
		<br>
		<br>
		<div class="row" >
			<div class="col-md-4"></div>
			<div class="col-md-4" align="center">
				<input type="submit" value="确认单选添加" name="addsubmitselect" id="danxuantianjia"/> 
			</div>
		</div>
	</div>
	<br>
	<br>
	</form>
</div>
<!-- 增加结束   -->
<div class="tab-pane fade" id="Search">
 <form method="post" action="${pageContext.request.contextPath}/tiku/additem" >
 	<div class="container-fluid" style="border-top: 54px">
		<div class="row">
			<div class="col-md-10" align="center" style="margin-top: 10%">
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-1" align="right">
			</div>
		</div>
	<h3 align="center"> 大题 </h3>
	<div class="row">
	<div class="col-md-12" align="center">
			<textarea id="daticontent" cols="100" rows="15" name="daticontent" style="margin:0 auto;">
			</textarea>
			</div>
			</div>
			<br>
			<br>
			<div class="row">
			<div class="col-md-12" align="center">
			<textarea id="datianswer" cols="100" rows="15" name="datianswer" style="margin:0 auto;">
			</textarea>
			</div>
			</div>
			<br>
			<br>
			<div class="row" >
			<div class="col-md-12" align="center">
				<input type="submit" value="确认大题添加" name="addsubmitdati" id="dati"/> 
			</div>
		</div>
		</div>
	</form>
</div>
</div>
</body>
</html>