<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/themes/gray/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/themes/icon.css" rel="stylesheet">
<!-- 先引入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.2.min.js"></script>
<!-- 引入easyui -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui-lang-zh_CN.js"></script>

<title>webcam</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.2.min.js"></script>
</head>
<script type="text/javascript">
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
    /*
    var stream = null;
    //这段代 主要是获取摄像头的视频流并显示在Video 签中
    window.addEventListener("DOMContentLoaded", function () {
        var video = document.getElementById("video");
        var videoObj = { "video": true };
        var errBack = function (error){
                console.log("Video capture error: " + error.message, error.code);
            };
        //  支持浏览器  谷歌,火狐,360,欧朋
        //navigator.getUserMedia这个写法在Opera中好像是navigator.getUserMedianow
        if (navigator.getUserMedia) {
            navigator.getUserMedia(videoObj, function (stream) {
                video.src = stream;
                video.play();
            }, errBack);
        } else if (navigator.webkitGetUserMedia) {
            navigator.webkitGetUserMedia(videoObj, function (stream) {
                video.src = window.URL.createObjectURL(stream);
                video.play();
            }, errBack);
        } else if (navigator.mozGetUserMedia){
            navigator.mozGetUserMedia(videoObj, function (stream) {
                     video.src = window.URL.createObjectURL(stream);
                    video.play();
            }, errBack);
        }
    }, false);
    */
    
//这个是拍照按钮的事件，
//document.getElementById("snap").addEventListener("click",function(){
	//CatchCode();
//});
    //定时器
    var interval = setInterval(CatchCode, "5000");
    //这个是 刷新上 图像的
    function CatchCode() {
   	 console.log("kaka");
   	 
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
        var xhr = new XMLHttpRequest();
        xhr.open("post", "${pageContext.request.contextPath}/webcam.do", true);
        //告诉服务器是以个Ajax 请求
        xhr.setRequestHeader("X-Requested-Width", "XMLHttpRequest");
        var fd = new FormData();
        fd.append("doc",base64Data);
        xhr.send(fd);

        //alert(base64Data);
        //在前端截取22位之后的字符串作为图像数据
        //开始异步上
    }
    $("#pp").textbox({
    	height : 5
    });
   </script>
<body style="padding: 0;margin-left: 0;margin-top: 0;">
	<video id="video" width="100%" height="100%" autoplay></video>      
	 <canvas style="border:1px solid red;display: none;" id="canvas"></canvas>  
</body>
</html>