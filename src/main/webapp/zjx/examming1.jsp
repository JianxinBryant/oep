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
#body{

}
* {
margin: 0;
padding: 0;

}

#barrage {
	margin: auto;
	margin-top: 50px;
	position: absolute;
	border: 2px solid #ffcc00;
	left: 200px;
	top: 14px;
	width: 1000px;
	height: 700px;
	filter: alpha(Opacity = 60);
	-moz-opacity: 0.6;
	opacity: 0.6;
	
}

#barrage div {
	width: 20%;
	height: 20px;
	line-height: 20px;
	position: absolute;
}

#btn {
	margin: auto;
	margin-top: 30px;
	height: 50px;
	width: 300px;
}

#text {
	font-size: 20px;
	height: 30px;
	border-radius: 4px;
	border: 1px solid #c8cccf;
	color: #6a6f77;
}

#submit {
	padding: 7px;
	font-size: 14px;
	height: 30px;
	border-radius: 4px;
	border: 1px solid #c8cccf;
}
</style>
</head>
<body>
<div id="head" style="z-index: 10;">
    <ul class="nav nav-pills">
        <li role="presentation"><img src="imag/IMG_0978.JPG" class="img-responsive" style="width: 65px;height: 40px;margin: 0px 200px 0px 50px"></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/zjx/usermain.jsp">首页</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/zjx/myexam.jsp">我的考试</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/llj/personinfo.jsp">个人信息</a></li>
        <li role="presentation" class="active"><a href="#" style="margin:0px 200px 0px 0px">正在考试</a></li>
        <li role="presentation"><h4 id="currenttime" style="margin-right: 50px; color: red"></h4></li>
        
    </ul>
</div>
<div id="body" style="z-index: 10;">
    <div class="container" style="margin-top: 50px;height: 100%;border: 1px solid" >
            <div id="barrage"></div>
        <div>
            <div id="testtime" hidden="true"></div>
        </div>
        <!-- <div id="mainpage"></div> -->
        <!-- <iframe id="iframe" width="100%" src="http://localhost:63342/oep/waitexam.html">
        	
        </iframe> -->
        <div>
            <div id="col9" class="col-md-9" style="border: 1px solid">
            	<div id="questionnum"></div>
                <form id="formz">
                	<div id="question" style="height: 548px; margin: 20px 20px; border: 1px solid"></div>
                </form>
                <div id="q_id" hidden></div>
                <div id="qq_id" hidden></div>
                <div id="pq_id" hidden></div>
                <div id="t_id" hidden></div>
                <div id="answer" hidden></div>
                <div id="page" hidden></div>
                <div id="nextpage" hidden></div>
                <div id="questionnumz" hidden></div>
                <button id="btn">提交</button>
                <button id="btn1">交卷</button>
            </div>
            <div id="col3" class="col-md-3" style="border: 1px solid">
            	<div style="margin-top: 20px ;height: 200px; border: 1px solid">
            		<iframe src="${pageContext.request.contextPath }/ems/sxt.jsp" style="width: 100%;height:250px; "></iframe>
            	</div>
                <div id="showbullet" style="margin-top: 20px ;height: 280px; border: 1px solid">1111111</div>
                <div id="writebullet" style="margin-top: 20px ;height: 100px; border: 1px solid">
	                <div>
						<input type=text id="danmu">
						<input type="button" onclick="sendMsg()" value="发送"/>
					</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>