<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 先引入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.9.1.js"></script>
<title>预览</title>
</head>
<script type="text/javascript">
	$(function(){
		$("#issue").click(function(){
			$.ajax({
				url : "${pageContext.request.contextPath}/ems/issue",
				type : "post",
				data :{},
				dataType : "json",
				success : function(data){
					alert("发布成功");
				}
			});
		});		
	});
</script>
<body>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<div id="issue" style="z-index: 9999; position: fixed ! important;left:40%;top:80%">
		<button id="issueBtn" style="position: absolute; width: 260px;margin:auto; left: 0px; right: 0; top: 0px;">
		确认发布
		</button>
	</div>
</body>
</html>