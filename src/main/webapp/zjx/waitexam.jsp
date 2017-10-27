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
        <li role="presentation"><a href="${pageContext.request.contextPath}/zjx/myexam.jsp">我的考试</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/llj/personinfo.jsp">个人信息</a></li>
        <li role="presentation" class="active"><a href="#" style="margin:0px 200px 0px 0px">等待考试</a></li>
        <li role="presentation"><h4 id="currenttime" style="margin-right: 50px; color: red"></h4></li>
       
    </ul>
</div>
<div id="body">
    <div class="container" style="margin-top: 50px;height: 100%;border: 1px solid" >
    	<div class="row">
	        <div class="col-md-8">
	            <h2>考试须知</h2>
	            <h4>考试时间：<div id="testtime">${requestScope.examtime }</div></h4>
	            <h4>1、考生须诚信考试，遵守考场规则和考试纪律</h4>
	            <h4>2、需在安静环境下进行考试，考生禁止遮挡摄像头</h4>
	            <h4>3、请勿乱动，以免摄像头识别不出</h4>
	            <h4>4、禁止与他人进行讨论</h4>
	            <h4>5、禁止嘲讽在屏幕另一端的监考员</h4>
	            <h4>6、禁止调戏在屏幕另一端的hr姐姐</h4>
	            <h4>7、禁止网上搜索答案并复制</h4>
	            <h4>8、禁止传播试题</h4>
	            <h4>9、禁止秀恩爱</h4>
	            <h4>10、禁止一切有关金钱交易的舞弊行为</h4>
	            <button id='btn' class='btn btn-default'>进入考试</button>
	        </div>
	       	<div class="col-md-4" style="margin-top: 20px ; border: 1px solid">
	       		<iframe src="${pageContext.request.contextPath }/ems/photograph.jsp" style="width: 100%;height:260px; "></iframe>
	       	</div>
    	</div>

    </div>
</div>
</body>
<script>
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
    	$("#btn").hide();
        showTime();
        function addZero(i){
            if(i<10){
                i = "0" + i;
            }return i;
        }
//        function checkStartOrEnd(){
//            showTime();
//
//            setTimeout(checkStartOrEnd,1000);
//        }
        function showTime() {
            var starttime = document.getElementById("testtime").innerHTML.split(" - ")[0];
            var endtime = document.getElementById("testtime").innerHTML.split(" - ")[1];
            var nowtime = new Date();
            var starttimez= new Date(starttime);
            var endtimez = new Date(endtime);
            var lefttime1 = parseInt((starttimez.getTime() - nowtime.getTime()) / 1000);
            var lefttime2 = parseInt((endtimez.getTime() - nowtime.getTime()) / 1000);
            var d = parseInt(lefttime1 / (24 * 60 * 60));
            var h = parseInt(lefttime1 / (60 * 60) % 24);
            var m = parseInt(lefttime1 / 60 % 60);
            var s = parseInt(lefttime1 % 60);
            d = addZero(d);
            h = addZero(h);
            m = addZero(m);
            s = addZero(s);
            document.getElementById("currenttime").innerHTML = "考试开始倒计时    " + d + ":" + h + ":" + m + ":" + s;
            if(m == 05 && s == 00 && h == 00 && d == 00){
                alert("还剩5分钟开始！")
            }
            if(lefttime1 <= 0 && lefttime2 >= 0){
                document.getElementById("currenttime").innerHTML = "正在考试中";
                $("#btn").show();
            }else if(lefttime2 <= 0){
            	document.getElementById("currenttime").innerHTML = "考试已结束";
            	$("#btn").hide();
            }
            setTimeout(showTime,1000);
        }
        
        $("#btn").click(function(){
        	$(location).attr('href', 'examming.jsp');
        });
    }
    
    $("#btn").click(function(){
    	$(location).attr('href', 'examming.jsp');
    });
</script>
</html>