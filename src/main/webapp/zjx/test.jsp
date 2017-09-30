<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
    window.onload = function (){
        try{
            //动态创建一个canvas元 ，并获取他2Dcontext。如果出现异常则表示不支持
            document.createElement("canvas").getContext("2d");
            document.getElementById("support").innerHTML = "浏览器支持HTML5 CANVAS";
        }catch(e){
            document.getElementById("support").innerHTML = "浏览器不支持HTML5 CANVAS";
        }
    };
    var localMediaStream = null;
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
</script>
<body>
<div id="support"></div>
<div id="contentHolder">
    <video id="video" width="160" height="120" autoplay></video>
    <button id="snap" onclick="CatchCode()"> 拍照</button>
    <canvas style="border:1px solid red" id="canvas" hidden></canvas>
</div>
</body>
</html>