<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/statics/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/jquery-1.9.1.js"></script>
<title>在线考试平台(OEP)-管理员登录页面</title>
<script type="text/javascript">
	$(document).ready(function() {
		<!--关闭红字警告-->
		$("#U_Name").focusin(function() {
			$("#uname-nullerror").css("display", "none");
			$("#uname-existerror").css("display", "none");
		});
		<!--判断用户名是否为空和是否存在-->
		$("#U_Name").focusout(function() {

			$.ajax({
				url : "${pageContext.request.contextPath}/hwx/checkmname",
				type : "post",
				data : {
					mname : $("#uname").val(),
				},
				dataType : "json",
				success : function(data) {
					if (data == 21) {
						$("#uname-nullerror").css("display", "block");
					}
					if (data == 1) {
						$("#uname-existerror").css("display", "block");
					}
				},

			});
		});

		<!--登录按钮触发-->
		$("#Loginbtn").click(function() {
			$.ajax({
				url : "${pageContext.request.contextPath}/hwx/managerlogin",
				type : "post",
				data : {
					mname : $("#uname").val(),
					mpassword : $("#upsw").val(),
					idtfcode : $("#idtfcode").val(),
				},
				dataType : "json",
				success : function(data) {
					if (data == 314) {
						alert("登录成功！")
						location.href = "/oep/llj/mainadmin.jsp"
					}else if(data == 365){
						alert("登录失败！\n错误代码:365\n可能的原因是用户名和密码不匹配")
					}else if (data == 391) {
						alert("登录失败！\n错误代码:391\n可能的原因是:验证码为空")
					}else if (data == 392) {
						alert("登录失败！\n错误代码:392\n可能的原因是:验证码不正确")
					}

				},
			})
		});

		
		$("#codeimg").click(function() {
			document.getElementById('codeimg').src = "${pageContext.request.contextPath}/hwx/crtcode?"+Math.random();
		});
	});
</script>
</head>


<body style="background-image: url('img/managerlogin.jpg');">
<div style="background-color: black;margin: 0;min-height: 985px;width: 100%;position: absolute;opacity:0.3;z-index: 1"></div>
	<div class="container"  style="position: absolute;z-index: 2;margin-left: 20%">
		<div class="row">
			<div style="margin-left:90%"><a href="Login.jsp" style="color:white">普通用户登录点我</a></div>
			<div style="margin-top: 150px; margin-left: 37%">
				<h1 style="color: white;">在&nbsp;线&nbsp;考&nbsp;试&nbsp;平&nbsp;台</h1>
			</div>
			
			<div class="col-md-3"></div>
			<div class="col-md-6" style="margin-top: 50px">
				<!-- 登陆 -->
				
				<div
					style="background-color: white; height: 400px; width: 550px; margin-left: -30px; border-radius: 10px; opacity: 0.8">
					<div
						style="width: 450px; height: 250px; position: absolute; left: 110px; margin-top: 60px;">
					<div class="form-group" id="U_Name">
						<label>用户名</label> <input style="max-width: 350px" type="text" class="form-control"
							id="uname" placeholder="请输入用户名" value="${cookie.tname.value}">
						<div id="uname-nullerror" style="color: red; display: none" class="alert alert-danger">管理员名不能为空！</div>
						<div id="uname-existerror" style="color: red; display: none" class="alert alert-danger">管理员不存在！</div>
					</div>
					<div class="form-group">
						<label>密码</label> <input style="max-width: 350px" type="password" class="form-control"
							id="upsw" placeholder="请输入用户密码" value="${cookie.tpsw.value}">
						<div id="psw-nullerror" style="color: red; display: none" class="alert alert-danger">密码不能为空！</div>
					</div>
					
					<div class="form-group" id="Idtfcode">
						<label></label> <input style="max-width: 350px" type="text" 
							id="idtfcode" placeholder="请输入图片中的数字"/>
						<img id="codeimg" src="${pageContext.request.contextPath}/hwx/crtcode" style="width: 80px;height: 30px"/>
						
					</div>
					<div class="checkbox">
						<label> <input type="checkbox" id="rme">记住我
						</label>
					</div>
					<button class="btn btn-danger" id="Loginbtn"
						style="margin-left: 25%">登&nbsp;录</button>
					</div>
				</div>
			</div>
			<div class="col-md-3"></div>

		</div>
	</div>
</body>
</html>