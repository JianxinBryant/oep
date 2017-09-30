<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
<!-- 先引入jquery -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/jquery-1.9.1.js"></script>
<!-- 引入chart.js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/Chart.js"></script>
<!-- 引入bootstrap -->
<link type="text/css"
	href="${pageContext.request.contextPath}/statics/js/bootstrap.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/jquery.table2excel.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/jquery.jqprint.js"></script>
<style type="text/css">
#head {
	width: 100%;
	height: 40px;
	background-color: #F8F8F8;
}
</style>
</head>
<body>
	<div id="head">
		<ul class="nav nav-pills">
			<li role="presentation"><img src="imag/IMG_0978.JPG"
				class="img-responsive"
				style="width: 65px; height: 40px; margin: 0px 200px 0px 50px"></li>
			<li role="presentation"><a href="${pageContext.request.contextPath}/zjx/usermain.jsp">首页</a></li>
        	<li role="presentation"><a href="${pageContext.request.contextPath}/zjx/myexam.jsp">我的考试</a></li>
        	<li role="presentation" class="active"><a href="${pageContext.request.contextPath}/llj/personinfo.jsp">个人信息</a></li>
			<li role="presentation"><h3 id="h3name"></h3></li>
		</ul>
	</div>
	<div class="container">
		<div class="page-header">
			<h2>用户个人信息</h2>
		</div>
		<div class="row">
			<div class="col-md-3" id="uname">用户名：</div>
			<div class="col-md-3" id="uemail">邮箱：</div>
			<div class="col-md-3" id="utelephone">手机：</div>
			<div class="col-md-3" id="uregistertime">注册时间：</div>
		</div>
		<br>
		<div class="row">
			<button id="query" class="btn btn-default" type="button">查询个人成绩</button>
		</div>
		<div class="row">
			<div class="col-md-5">
				<table id="a" class="table table-striped">

				</table>
				<div id="b"></div>
				<div id="mo">
					<button class="btn btn-default" data-toggle="modal"
						data-target="#myModal" id="show">导出成绩到excel或打印成绩</button>
				</div>
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-5" id="chdiv1"></div>
		</div>

		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">

						<h4 class="modal-title" id="myModalLabel">是否导出为excel</h4>
					</div>
					<div class="modal-body">
						<table id="c" border="1">

						</table>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal" id="cancel">取消</button>
							<button type="button" class="btn btn-primary"
								data-dismiss="modal" id="btn">导出为excel</button>
							<button type="button" class="btn btn-primary" id="pri">打印</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script
	src="${pageContext.request.contextPath}/statics/js/personinfo.js"></script>
</html>