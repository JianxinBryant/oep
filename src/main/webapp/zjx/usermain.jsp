<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<!-- 先引入jquery -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/jquery-1.9.1.js"></script>
<!-- 引入bootstrap.css -->
<link type="text/css"
	href="${pageContext.request.contextPath}/statics/js/bootstrap.min.css"
	rel="stylesheet">
<!-- 引入bootstrap.js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/statics/js/bootstrap.min.js"></script>
<style type="text/css">
#head {
	width: 100%;
	height: 40px;
	background-color: #9d9d9d;
}

#img {
	width: 100%;
	height: 200px;
}
</style>
</head>
<body>
	<div id="head">
		<ul class="nav nav-pills">
			<li role="presentation"><img src="imag/IMG_0978.JPG"
				class="img-responsive"
				style="width: 65px; height: 40px; margin: 0px 200px 0px 50px"></li>
			<li role="presentation" class="active"><a href="#">首页</a></li>
			<li role="presentation"><a href="#">我的考试</a></li>
			<li role="presentation"><a href="#"
				style="margin: 0px 400px 0px 0px">个人信息</a></li>
			<li role="presentation"><a href="#">登录</a></li>
			<li role="presentation"><a href="#">注册</a></li>
		</ul>
	</div>
	<div id="img">
		<div id="myCarousel" class="carousel slide" style="height: 200px">
			<!-- 轮播（Carousel）指标 -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
			<!-- 轮播（Carousel）项目 -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="imag/IMG_0978.JPG"
						style="width: 400px; height: 200px; margin: auto"
						alt="First slide">
				</div>
				<div class="item">
					<img src="imag/timg.jpg"
						style="width: 400px; height: 200px; margin: auto"
						alt="Second slide">
				</div>
				<div class="item">
					<img src="imag/IMG_0978.JPG"
						style="width: 400px; height: 200px; margin: auto"
						alt="Third slide">
				</div>
			</div>
			<!-- 轮播（Carousel）导航 -->
			<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;
			</a> <a class="carousel-control right" href="#myCarousel"
				data-slide="next">&rsaquo; </a>
		</div>
	</div>

	<div id="body" class="container" style="border: 1px solid">
		<div>
			<h2 style="text-align: center">考试专区</h2>
		</div>
		<div class="row">
			<div id="a">
				<!-- <div class="col-md-3"
				style="margin: 5px 0px 5px 0px; height: 100%; border: 1px solid">
				<div style="width: 100%; height: 100px; margin: 20px"></div>
			</div> -->
			</div>
		</div>
		<div id="b"></div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">考试报名</h4>
				</div>
				<div class="modal-body" id="major"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="cancel">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="confirm">确认</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
<script src="${pageContext.request.contextPath}/statics/js/allexam.js"></script>
</html>