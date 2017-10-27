<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 先引入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-1.9.1.js"></script>
<!-- 引入bootstrap.css -->
<link type="text/css" href="${pageContext.request.contextPath}/statics/js/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
	#head{
		width: 100%;
        height: 40px;
        background-color: #9d9d9d;
	}
</style>
</head>
<body>
<div id="head" >
    <ul class="nav nav-pills">
        <li role="presentation"><img src="imag/IMG_0978.JPG" class="img-responsive" style="width: 65px;height: 40px;margin: 0px 200px 0px 50px"></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/zjx/usermain.jsp">首页</a></li>
        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/zjx/myexam.jsp">我的考试</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/llj/personinfo.jsp" style="margin:0px 400px 0px 0px">个人信息</a></li>
        <li role="presentation"><h4>名字</h4></li>
    </ul>
</div>
<div id="body">
    <div class="container" style="margin-top: 50px;height: 100%;border: 1px solid" >
        <h2>我的考试</h2>
        <ul id="ul">
            
        </ul>
    </div>
</div>
</body>
<script type="text/javascript">
window.onload = function(){
	$.ajax({
		url:'queryUserExamAction',
		dataType:'json',
		success:function(data){
			$.each(data,function(i,v){
				console.log(v.e_id);
				
				$("#ul").append("<li id="+v.e_id+"><span>"+v.e_name+"</span>	考试时间：<span id='testtime'>"+v.examtime+"</span>	<span>总分"+v.e_total+"</span>	");
				var starttime = new Date(v.examtime.split(" - ")[0]);
				console.log(starttime);
				var endtime = new Date(v.examtime.split(" - ")[1]);
				console.log(endtime);
				var nowtime = new Date();
				if(parseInt((starttime.getTime() - nowtime.getTime()) / 1000) > 0){
					$("#ul").append("<a class='a' href='joinWaitExamAction?e_id="+v.e_id+"'>进入考试预览</a></li>");
				}else if(parseInt((starttime.getTime() - nowtime.getTime()) / 1000) < 0 && parseInt((endtime.getTime() - nowtime.getTime()) / 1000) > 0){
					if(v.score != 0){
						$("#ul").append("<h4>已完成考试</h4></li>");
					}else
						$("#ul").append("<a class='a' href='joinWaitExamAction?e_id="+v.e_id+"'>正在考试</a></li>");
				}else{
					if(v.score == 0){
						/* $("#ul").append("<a class='a' href='joinWaitExamAction?e_id="+v.e_id+"'>缺考</a></li>"); */
						$("#ul").append("<h4>缺考</h4></li>");
					}else{
						$("#ul").append("<h4>已完成考试</h4></li>");
					}
				}
			})
		}
	})
	
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