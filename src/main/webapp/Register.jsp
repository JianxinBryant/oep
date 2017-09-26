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
<title>在线考试平台(OEP)-注册页面</title>
</head>
<body class="bg-primary">
	<div class="container">
		<div class="row">
			<div style="margin-top: 50px; margin-left: 42%">
				<h1>欢&nbsp;迎&nbsp;注&nbsp;册</h1>
			</div>
			<div class="col-md-3"></div>
			<div class="col-md-6" style="margin-top: 50px">

					<form action="register" method="post" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-0 control-label">用户名:</label>
							<input
							type="text"  id="userName"
							placeholder="请输入用户名"  class="form-control">
						</div>
						<div  class="form-group">
							<label class="col-sm-0 control-label">密码:</label>
							<input
							type="text"  id="password"
							placeholder="请输入密码"  class="form-control">
						</div>
						<div  class="form-group">
							<label class="col-sm-0 control-label">确认密码:</label>
							<input
							type="text"  id="conPassword"
							placeholder="请确认密码"  class="form-control">
						</div>
						<div  class="form-group">
							<label class="col-sm-0 control-label">E-mail:</label>
							<input
							type="text"  id="e-mail"
							placeholder="请输入e-mail"  class="form-control">
						</div>
						<div  class="form-group">
							<label class="col-sm-0 control-label">手机号：</label>
							<input
							type="text"  id="telephone"
							placeholder="请输入手机号"  class="form-control">
						</div>
						<br/>
						<div align="center">
							<input type="submit" value="提交注册" class="btn btn-success" name="register"></div>
					</form>
					<br/>
					<div align="center">
					已有账号？<a href="Login.jsp" style="color: #cc99cc">点击直接登录</a></div>
			</div>
		</div>
	</div>
</body>
</html>