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
        <li role="presentation"><a href="#">首页</a></li>
        <li role="presentation"><a href="#">我的考试</a></li>
        <li role="presentation"><a href="#">个人信息</a></li>
        <li role="presentation" class="active"><a href="#" style="margin:0px 200px 0px 0px">等待考试</a></li>
        <li role="presentation"><h4 id="currenttime" style="margin-right: 50px; color: red"></h4></li>
        <li role="presentation"><h4>名字</h4></li>
    </ul>
</div>
<div id="body">
    <div class="container" style="margin-top: 50px;height: 100%;border: 1px solid" >
        <div>
            <h2>考试须知</h2>
            <h4>考试时间：<div id="testtime">2017/09/25,20:10:00 - 2017/09/26,00:00:00</div></h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
            <h4>zzzzzzzzzzzzzzzzzzzzzzzzzzz</h4>
        </div>

    </div>
</div>
</body>
<script>
    window.onload = function(){
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
            var testtime = document.getElementById("testtime").innerHTML.split(" - ")[1];
            var nowtime = new Date();
            var endtime = new Date(testtime);
            var lefttime = parseInt((endtime.getTime() - nowtime.getTime()) / 1000);
            var d = parseInt(lefttime / (24 * 60 * 60));
            var h = parseInt(lefttime / (60 * 60) % 24);
            var m = parseInt(lefttime / 60 % 60);
            var s = parseInt(lefttime % 60);
            h = addZero(h);
            m = addZero(m);
            s = addZero(s);
            document.getElementById("currenttime").innerHTML = "考试开始倒计时    " + d + ":" + h + ":" + m + ":" + s;
            if(m == 05 && s == 00){
                alert("还剩5分钟开始！")
            }
            if(lefttime <= 0){
                var userid = 1;
                document.getElementById("currenttime").innerHTML = "考试已结束";
                $.ajax({
                    url:'ExammingAction',
                    type:'post',
                    data:{'userid':userid},
                    dataType:'json'
                });
                return;
            }
            setTimeout(showTime,1000);
        }
    }
</script>
</html>