<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 先引入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.7.2.js"></script>
<!-- 引入easyui -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/easyui-lang-zh_CN.js"></script>

<title>webcam</title>
</head>
<script type="text/javascript">
$(function(){
    var localMediaStream = null
    window.onload=function(){
   		navigator.getUserMedia || (navigator.getUserMedia = navigator.mozGetUserMedia || navigator.webkitGetUserMedia || navigator.msGetUserMedia);
   	    if (!navigator.getUserMedia) {
   	    	return;
   	    }
   		var video = document.getElementById('video');
   		var localMediaStream = null;
   		startWebcam(false);
   		function startWebcam(e) {
   			var constraints = {video: true, audio: false};
   			function successCallback(localMediaStream) {
   			    video.src = window.URL.createObjectURL(localMediaStream);
   			    video.play();
   			    localMediaStream = localMediaStream;
   			    video.onloadedmetadata = function(e) {
   			        console.log("id: " + localMediaStream.id);
   			        console.log("AudioTracks" , localMediaStream.getAudioTracks());
   			        console.log("VideoTracks" , localMediaStream.getVideoTracks());
   			    };
   			}
   			function errorCallback(error){
   			  console.log(error);
   			}
   			navigator.getUserMedia(constraints, successCallback, errorCallback);
   		}
   	}
	//这个是拍照按钮的事件，
	document.getElementById("snap").addEventListener("click",function(){
		CatchCode();
	});
    //定时器
    //var interval = setInterval(CatchCode, "10000");
    //这个是 刷新上 图像的
    function CatchCode() {
        //实际运用可不写，测试代 ， 为单击拍照按钮就获取了当前图像，有其他用途
        var canvans = document.getElementById("canvas");
        var video = document.getElementById("video");
        var context = canvas.getContext("2d");
        
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        context.drawImage(video,0,0);
        
        //获取浏览器页面的画布对象
        //以下开始编 数据
        var imgData = canvans.toDataURL("image/jpg");
        //将图像转换为base64数据
        var base64Data = imgData.split(",")[1];
        var json ;
        var xhr = new XMLHttpRequest();
     
        var fd = new FormData();
        fd.append("doc",base64Data);
		$.ajax({
		    url: '${pageContext.request.contextPath}/photoGraph.do',
		    type: 'post',
		    beforeSend : function(XHR){
		    	XHR.setRequestHeader("X-Requested-Width", "XMLHttpRequest");
		    },
		    contentType: false, // 很重要
		    traditional: true,
		    processData:false,
		    data: fd, 
		    success: function(data) {
		    	console.log(data);
		    	$("#canvas").css("display","block");
		    	//console.log($("#img").attr("src"));
		    }
		});
    }
});
</script>
<body style="padding: 0;margin-left: 0;margin-top: 0;">
	<video id="video" width="99%" height="90%" autoplay></video>      
	<canvas id="canvas" style="width:250px; border:1px solid red;display: none;" id="canvas"></canvas>  
	<button id="snap">拍照</button>
</body>
</html>