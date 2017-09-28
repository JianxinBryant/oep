<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/bootstrap/css/bootstrap.min.css">
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
	<div class="container-fluid">
	<div class="col-md-8 col-md-offset-2">
	<div id="examBaseInfo" style="text-align: center;">
		<div>
		<h2>${examInfo.exam.e_name }</h2>
		</div>
		<div style="margin-top: 30px;">
		<span>
			<label>考试时间：</label>${examInfo.exam.e_starttime } - ${examInfo.exam.e_endtime}     
			<label>考试总分：</label>${examInfo.exam.e_total }
		</span>
		</div>
	</div>
	<div style="margin-top: 50px;">
		<c:forEach var="question" items="${examInfo.questions }" varStatus="i">
		<div style="margin-top: 10px;">
			<div><h4>${i.index + 1 }.${question.question.q_content }</h4></div>
				<h5>
				<c:forEach var="option" items="${question.options }">
					<c:if test="${fn:length(question.question.q_answer) > 1 }">
						<input type="checkbox" value="${option.abcd }"/>${option.o_content }
						<br>
					</c:if>
					<c:if test="${fn:length(question.question.q_answer)  == 1 }">
						<input type="radio" value="${option.abcd }"/>${option.o_content }
						<br>
					</c:if>
				</c:forEach>
				</h5>
		</div>
		</c:forEach>
	</div>
	</div>
	<div>
	</div>
	</div>
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