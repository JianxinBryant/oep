<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	考试已结束！
	<a href="${pageContext.request.contextPath}/zjx/endExamAction">返回首页</a>
</body>
<script type="text/javascript">
window.onload = function(){
	var timer = setInterval(function(){
		$.ajax({
			url:'checkUserAction',
			success:function(data){
				console.log(data);
				if(data=='1'){
					$("#body").append("<div><h4>你的账户在其他地点登录，请注意密码是否泄漏！</h4><a href='${pageContext.request.contextPath}/hwx/Login.jsp'>确定</a></div>");
					clearInterval(timer);
				}
			}
		});
	},4000);
}
</script>
</html>