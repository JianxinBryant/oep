<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div>
        <div id="col9" class="col-md-9" style="border: 1px solid">
            <div id="question" style="height: 548px; margin: 20px 20px; border: 1px solid">
            	
            </div>
        </div>
        <div id="col3" class="col-md-3" style="border: 1px solid">
            <div id="showbullet" style="margin-top: 20px ;height: 430px; border: 1px solid"></div>
            <div id="writebullet" style="margin-top: 20px ;height: 100px; border: 1px solid"></div>
        </div>
    </div>
</body>
<script>
    $(function(){
        $("#col9").height(screen.availHeight-240);
        $("#col3").height(screen.availHeight-240);
//        $("#showbullect").width($("#col3").width-20);
//        $("#showbullect").height(200);
//        $("#showbullect").css("margin","0px 10px");
//        $("#showbullect").css("border","1px solid");
    })
</script>
</html>