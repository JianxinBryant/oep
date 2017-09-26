<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<title>在线考试平台(OEP)-登录页面</title>
<script type="text/javascript">
	$(function() {
		
		
	});
	
</script>
</head>


<body class="bg-primary">
	<div class="container">
		<div class="row">
		<div style="margin-top: 150px; margin-left: 35%"><h1>在&nbsp;线&nbsp;考&nbsp;试&nbsp;平&nbsp;台</h1></div>
		<div style="margin-top: 50px; margin-left: 42%"><h2>请&nbsp;登&nbsp;录</h2></div>
			<div class="col-md-3"></div>
			<div class="col-md-6" style="margin-top: 50px">
				<!-- 登陆 -->
				<div >
					<div class="form-group">
						<label >用户名</label> <input
							type="text" class="form-control"  id="tname"
							placeholder="请输入用户名" value="${cookie.tname.value}">
					</div>
					<div class="form-group">
						<label >密码</label> <input
							type="password" class="form-control"  id="tpsw"
							placeholder="请输入用户密码" value="${cookie.tpsw.value}">
					</div>
					<div class="checkbox">
						<label> <input type="checkbox" id="rme">记住我
						</label>
					</div>
					<button  class="btn btn-danger" id="login"  style="margin-left: 25%">登&nbsp;录</button>
					<a href="Register.jsp"><button  class="btn btn-success" id="register" style="margin-left: 100px">注&nbsp;册</button></a>
				</div>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
</body>
</html>