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
<script>
		var host = "172.18.23.73"; //172.18.23.73
		
		function handleOnMessage(){
			console.log("收到消息");
		}
		
		
		//打开webSocket连接
		var webSocket = new WebSocket("ws://"+host+":8080/oep/DanMuServer");
		
		//-----------websocket事件注册--------------
		//websocket连接事件
		webSocket.onopen = function(evt) {
			console.log("websocket已连接");
		};
		
		//websocket关闭事件
		webSocket.onclose = function(evt) {
			webSocket.close();
		};
		
		//websocket消息响应事件
		webSocket.onmessage = function(msg){
			create(msg.data);
		}
		
		
		//-------------end-----------------------
		var timer = null;
		var current = [];//存储当前输入框的内容  
		var newarr = [];//存储每个弹幕距左边框的距离  
		var num = new Array();//数组，用来存储划分每个块的序号  
	
		$(function(){

			clearInterval(timer);//清除定时器  
		
			timer = setInterval(move, 20);//开启定时器  
			
			
			for (var i = 0; i < ($("#barrage").height() / 20 - 1); i++) {
				num.splice(i, 0, i);//将整个显示框划分成多个块，并对每个块进行标号  
			}
		})

		

		
		function sendMsg(){
			webSocket.send($("#danmu").val());
		}

		function create(w) {//创建一个弹幕  
			console.log("danmu:   "+$("#danmu").val());
			var node = document.createElement("div");//创建一个div元素，用来存储弹幕的信息  
			node.innerHTML = w;
			var t = random(0, num.length - 1);
			node.style.top = num[t] * 20 + "px";//从划分的块中随机选中一块。  
			Delete(num[t]);//删除已被选中的块   
			node.style.left = "950px";
			node.style.color = "#" + randomColor();//随机颜色  
			node.style.fontSize = random(20, 40) + "px";
			$("#barrage").append(node);//插入子节点
		}

		function move() {
			var arr = $("#barrage").children("div");//获取所有的弹幕  
			for (var i = 0; i < arr.length; i++) {

				//将每个弹幕距左边边框的距离分别存储在newarr数组中  
				newarr.push(arr[i].offsetLeft);
				arr[i].style.left = newarr[i] + "px";//更新距离  
				newarr[i] = newarr[i] - 2;//每次减少2px  

				if (newarr[i] < 0) {
					//$("barrage").removeChild(arr[i]);
					arr[i].remove();
					newarr.splice(i, 1);//在newarr中删除这个div  
				}
			}
		}

		function Delete(m) {//从预选块中删除已被选择的块  
			for (var i = 0; i < num.length; i++) {
				if (num[i] == m) {
					//console.log(m)  
					num.splice(i, 1);
				}
			}
		}

		function randomColor() {//随机颜色  
			var color = Math.ceil(Math.random() * 16777215).toString(16);

			while (color.length < 6) {
				color = "0" + color;
			}
			return color;
		}

		function random(m, n) {//随机在m、n之间的整数  
			return Math.round(Math.random() * (n - m)) + m;
		}
	</script>
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
<script>
    window.onload = function(){
    	
//        $("#iframe").width(100%);
		$.ajax({
			url:'queryQuestionAction',
			data:{
				'page' : 1,
			},
			dataType:'json',
			success : function(data){
				var exam_question = data.exam_question;
				var question = data.question;
				var options = data.options;
				var questionnum = data.questionnum;
				var page = data.page;
				var currentanswer = data.answerofuser.answer;
				console.log(currentanswer);
				$("#q_id").attr("value" , question.q_id);//加入隐藏域
				$("#t_id").attr("value" , question.t_id);//加入隐藏域
				$("#page").attr("value" , page);//加入隐藏域
				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
				for(var i=0; i < questionnum; i++){
					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
				}
				$("#question").append("<h3>"+page+"</h3><h3>"+question.q_content+"</h3>");
				if(question.t_id==1){
					
					$.each(options, function(i, v){
						$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
					})
					radioischecked(currentanswer);
					
				}else if(question.t_id==2){
					$.each(options, function(i, v){
						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
					})
					checkboxischecked(currentanswer);
				}else if(question.t_id==3){
					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
					textareaisempty(currentanswer);
				}
				
				console.log("val: "+$("#q_id").attr("value"));
				$.ajax({
					url:'queryEtimeAction',
					success: function(data){
						$("#testtime").html(data)
					}
				})
			}
		})
		
        $("#iframe").height(screen.availHeight-235);
        showTime();


        function addZero(i){
            if(i<10){
                i = "0" + i;
            }
            return i;
        }

        function showTime() {
            var testtime = document.getElementById("testtime").innerHTML.split(" - ")[1];
            var nowtime = new Date();
            var endtime = new Date(testtime);
            var lefttime = parseInt((endtime.getTime() - nowtime.getTime()) / 1000);
            var d = parseInt(lefttime / (24 * 60 * 60));
            var h = parseInt(lefttime / (60 * 60) % 24);
            var m = parseInt(lefttime / 60 % 60);
            var s = parseInt(lefttime % 60);
            d = addZero(d);
            h = addZero(h);
            m = addZero(m);
            s = addZero(s);
            document.getElementById("currenttime").innerHTML = "距离考试结束：   " + d + ":" + h + ":" + m + ":" + s;
            if(m == 05 && d==00 && h==00 && s==00){
                alert("考试时间还剩5分钟！")
            }
            if(lefttime <= 0){
                var userid = 1;
                document.getElementById("currenttime").innerHTML = "考试已结束";
                $.ajax({
                	url:'insertAnswerActionF?t_id='+$("#t_id").attr("value")+'&&q_id='+$("#q_id").attr("value"),
                	type:'POST',
                	data:$('#formz').serialize(),
                	async:false,
                	success:function(data){
                		location.href="endexam.jsp";
                	}
                })
            }
            setTimeout(showTime,1000);
        }
        
        //点击题号按钮
        $("#questionnum").on(
        		'click',
        		'.but',
        		function(){
        			$("#nextpage").attr("value" , $(this).html());//加入隐藏域
        			console.log("nextpage: "+ $("#nextpage").attr("value"));
        			if($("#t_id").attr("value") == 1 || $("#t_id").attr("value") == 2){
        	    		
        	    		if($("#t_id").attr("value")==1 && $('input:radio[name="option"]:checked').val() == null){
        	        		alert('什么都没选中1');
        	        		$.ajax({
        	        			url:'deleteAnswerAction',
        	    				data:{
        	    					'q_id' : $("#q_id").attr("value")
        	    				},
        	    				success : function(data){
        	    					$.ajax({
                	        			url:'queryQuestionAction',
                	        			data:{
                	        				'page' : $("#nextpage").attr("value"),
                	        			}, 
                	        			dataType:'json',
                	        			success : function(data){
                	        				console.log("到底执行没有？");
                	        				$("#questionnum").empty();
                	        				$("#question").empty();
                	        				var exam_question = data.exam_question;
                	        				var question = data.question;
                	        				var options = data.options;
                	        				var questionnum = data.questionnum;
                	        				var page = data.page;
                	        				var currentanswer = data.answerofuser.answer;
                	        				$("#q_id").attr("value" , question.q_id);//加入隐藏域
                	        				$("#t_id").attr("value" , question.t_id);//加入隐藏域
                	        				$("#page").attr("value" , page);//加入隐藏域
                	        				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
                	        				for(var i=0; i < questionnum; i++){
                	        					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
                	        				}
                	        				console.log("page= "+ page);
                	        				$("#question").append("<h3>"+page+"</h3><h3>"+question.q_content+"</h3>");
                	        				if(question.t_id==1){
                	        					$.each(options, function(i, v){
                	            					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                	            				})
                	            				radioischecked(currentanswer);
                	        				}else if(question.t_id==2){
                	        					$.each(options, function(i, v){
                	        						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                	        					})
                	        					checkboxischecked(currentanswer);
                	        				}else if(question.t_id==3){
                	        					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
                	        					textareaisempty(currentanswer);
                	        				}
                	        				
                	        				/* $("#q_id").attr("value" , question.q_id); */
                	        			}	
                	        		})
        	    				}
        	        		})
        	    				
        	        		
        	    		}else if($("#t_id").attr("value")==2 && $('input:checkbox[name="option"]:checked').val() == null){
        	    			alert('什么都没选中');
        	    			$.ajax({
        	    				url:'deleteAnswerAction',
        	    				data:{
        	    					'q_id' : $("#q_id").attr("value")
        	    				},
        	    				success : function(data){
        	    					$.ajax({
                	        			url:'queryQuestionAction',
                	        			data:{
                	        				'page' : $("#nextpage").attr("value"),
                	        			}, 
                	        			dataType:'json',
                	        			success : function(data){
                	        				$("#questionnum").empty();
                	        				$("#question").empty();
                	        				var exam_question = data.exam_question;
                	        				var question = data.question;
                	        				var options = data.options;
                	        				var questionnum = data.questionnum;
                	        				var page = data.page;
                	        				var currentanswer = data.answerofuser.answer;
                	        				$("#q_id").attr("value" , question.q_id);//加入隐藏域
                	        				$("#t_id").attr("value" , question.t_id);//加入隐藏域
                	        				$("#page").attr("value" , page);//加入隐藏域
                	        				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
                	        				for(var i=0; i < questionnum; i++){
                	        					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
                	        				}
                	        				$("#question").append("<h3>"+page+"</h3><h3>"+question.q_content+"</h3>");
                	        				if(question.t_id==1){
                	        					$.each(options, function(i, v){
                	            					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                	            				})
                	            				radioischecked(currentanswer);
                	        				}else if(question.t_id==2){
                	        					$.each(options, function(i, v){
                	        						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                	        					})
                	        					checkboxischecked(currentanswer);
                	        				}else if(question.t_id==3){
                	        					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
                	        					textareaisempty(currentanswer);
                	        				}
                	        				
                	        			}	
                	        		})
        	    				}
        	    			})
        	    					
        	    		}
        	    		else{
        	    			$.ajax({
        	        		url:'insertAnswerAction?q_id='+$("#q_id").attr("value"),
        	        		type:'POST',
        	        		data:$('#formz').serialize(),
        	        		async:false,
        	        		success:function(data){
        	        			console.log("zzzz "+ $(this).html());
        	        			$.ajax({
        	                		/* url:'queryQuestionAction?'+'page='+$(this).html() */
        	                		url:'queryQuestionAction',
        	                		data:{
        	            				'page' : $("#nextpage").attr("value"),
        	            			}, 
        	            			dataType:'json',
        	            			success : function(data){
        	            				$("#questionnum").empty();
        	            				$("#question").empty();
        	            				var exam_question = data.exam_question;
        	            				var question = data.question;
        	            				var options = data.options;
        	            				var questionnum = data.questionnum;
        	            				var page = data.page;
        	            				var currentanswer = data.answerofuser.answer;
        	            				$("#q_id").attr("value" , question.q_id);//加入隐藏域
        	            				$("#t_id").attr("value" , question.t_id);//加入隐藏域
        	            				$("#page").attr("value" , page);//加入隐藏域
        	            				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
        	            				for(var i=0; i < questionnum; i++){
        	            					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
        	            				}
        	            				$("#question").append("<h3>"+page+"</h3><h3>"+question.q_content+"</h3>");
        	            				if(question.t_id==1){
        	            					$.each(options, function(i, v){
        	                					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
        	                				})
        	                				radioischecked(currentanswer);
        	            				}else if(question.t_id==2){
        	            					$.each(options, function(i, v){
        	            						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
        	            					})
        	            					checkboxischecked(currentanswer);
        	            				}else if(question.t_id==3){
        	            					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
        	            					textareaisempty(currentanswer);
        	            				}
        	            				
        	            				/* $("#q_id").attr("value" , question.q_id); */
        	            			}
        	                	})
        	        		}
        	        	})}
        	    	}else if($("#t_id").attr("value") == 3){
        	    		console.log("进入简答题逻辑");
        	    		$.ajax({
        	    			url:'insertAnswerAction1?q_id='+$("#q_id").attr("value"),
        	        		type:'POST',
        	        		data:$('#formz').serialize(),
        	        		async:false,
        	        		success:function(){
        	        			$.ajax({
                            		/* url:'queryQuestionAction?'+'page='+$(this).html() */
                            		url:'queryQuestionAction',
                            		data:{
                        				'page' : $("#nextpage").attr("value"),
                        			},
                        			dataType:'json',
                        			success : function(data){
                        				$("#questionnum").empty();
                        				$("#question").empty();
                        				var exam_question = data.exam_question;
                        				var question = data.question;
                        				var options = data.options;
                        				var questionnum = data.questionnum;
                        				var page = data.page;
                        				var currentanswer = data.answerofuser.answer;
                        				$("#q_id").attr("value" , question.q_id);//加入隐藏域
                        				$("#t_id").attr("value" , question.t_id);//加入隐藏域
                        				$("#page").attr("value" , page);//加入隐藏域
                        				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
                        				for(var i=0; i < questionnum; i++){
                        					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
                        				}
                        				$("#question").append("<h3>"+page+"</h3><h3>"+question.q_content+"</h3>");
                        				if(question.t_id==1){
                        					$.each(options, function(i, v){
                            					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                            				})
                            				radioischecked(currentanswer);
                        				}else if(question.t_id==2){
                        					$.each(options, function(i, v){
                        						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                        					})
                        					checkboxischecked(currentanswer);
                        				}else if(question.t_id==3){
                        					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
                        					textareaisempty(currentanswer);
                        				}
                        				
                        				/* $("#q_id").attr("value" , question.q_id); */
                        				
                        			}
                            	})
        	        		}
        	    		})
        	    		
        	    	}
            	})
            	
    $("#btn1").click(function(){
    	var truthBeTold = window.confirm("确认要交卷了吗"); 
    	if (truthBeTold) { 
			console.log("他交卷了");
			document.getElementById("currenttime").innerHTML = "考试已结束";
            $.ajax({
            	url:'insertAnswerActionF?t_id='+$("#t_id").attr("value")+'&&q_id='+$("#q_id").attr("value"),
            	type:'POST',
            	data:$('#formz').serialize(),
            	async:false,
            	success:function(data){
            		location.href="endexam.jsp";
            	}
            })
		}else{
			console.log("他还没交卷");
			$.ajax({
				url:'insertExamActionF?t_id='+$("#t_id").attr("value")+'&&q_id='+$("#q_id").attr("value"),
				type:'POST',
            	data:$('#formz').serialize(),
            	async:false,
            	success:function(){
            		$.ajax({
            			url:'queryQuestionAction',
            			data:{
            				'page' : parseInt($("#page").attr("value"))
            			}, 
            			dataType:'json',
            			success : function(data){
            				$("#questionnum").empty();
            				$("#question").empty();
            				var exam_question = data.exam_question;
            				var question = data.question;
            				var options = data.options;
            				var questionnum = data.questionnum;
            				var page = data.page;
            				var currentanswer = data.answerofuser.answer;
            				$("#q_id").attr("value" , question.q_id);//加入隐藏域
            				$("#t_id").attr("value" , question.t_id);//加入隐藏域
            				$("#page").attr("value" , page);//加入隐藏域
            				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
            				for(var i=0; i < questionnum; i++){
            					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
            				}
            				$("#question").append("<h3>"+question.q_content+"</h3>");
            				if(question.t_id==1){
            					$.each(options, function(i, v){
                					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                				})
                				radioischecked(currentanswer);
            				}else if(question.t_id==2){
            					$.each(options, function(i, v){
            						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
            					})
            					checkboxischecked(currentanswer);
            				}else if(question.t_id==3){
            					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
            					textareaisempty(currentanswer);
            				}
            				
            				/* $("#q_id").attr("value" , question.q_id); */
            			}	
            		})
            	}
			})
		}
    })
   	
    
    //点击选项(单选与多选)
    $("#question").on(
    	'click',
    	'.option',
    	function(){
    		if($("#t_id").attr("value")==1){
    			var answer;
    			console.log("11111 "+$("#t_id").attr("value"));
    			answer = $(this).val();
    			console.log(answer);
    		}else{
    			console.log("这是处理多选题");
    		}
    		$("#answer").attr("value" , answer);
    		console.log("zzz " + $("#answer").attr("value"));
    	}
    )
    
    $("#question").on(
    	'click',
    	'.options',
    	function(){
    		if($("#t_id").attr("value")==2){
    			
    		}
    	}
    )
    
    //点击提交按钮
    $("#btn").click(function(){
    	console.log($("#page").attr("value")+"~"+$("#questionnumz").attr("value"));
    	if(parseInt($("#page").attr("value"))+parseInt(1) > $("#questionnumz").attr("value")){
    		var truthBeTold = window.confirm("确认要交卷了吗"); 
    		if (truthBeTold) { 
    			console.log("他交卷了");
    			document.getElementById("currenttime").innerHTML = "考试已结束";
                $.ajax({
                	url:'insertAnswerActionF?t_id='+$("#t_id").attr("value")+'&&q_id='+$("#q_id").attr("value"),
                	type:'POST',
                	data:$('#formz').serialize(),
                	async:false,
                	success:function(data){
                		location.href="endexam.jsp";
                	}
                })
    		}else{
    			console.log("他还没交卷");
    			$.ajax({
    				url:'insertAnswerActionF?t_id='+$("#t_id").attr("value")+'&&q_id='+$("#q_id").attr("value"),
    				type:'POST',
                	data:$('#formz').serialize(),
                	async:false,
                	success:function(){
                		console.log("进来没有到底");
                		$.ajax({
                			url:'queryQuestionAction',
                			data:{
                				'page' : parseInt($("#page").attr("value"))
                			}, 
                			dataType:'json',
                			success : function(data){
                				$("#questionnum").empty();
                				$("#question").empty();
                				var exam_question = data.exam_question;
                				var question = data.question;
                				var options = data.options;
                				var questionnum = data.questionnum;
                				var page = data.page;
                				var currentanswer = data.answerofuser.answer;
                				$("#q_id").attr("value" , question.q_id);//加入隐藏域
                				$("#t_id").attr("value" , question.t_id);//加入隐藏域
                				$("#page").attr("value" , page);//加入隐藏域
                				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
                				for(var i=0; i < questionnum; i++){
                					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
                				}
                				$("#question").append("<h3>"+question.q_content+"</h3>");
                				if(question.t_id==1){
                					$.each(options, function(i, v){
                    					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                    				})
                    				radioischecked(currentanswer);
                				}else if(question.t_id==2){
                					$.each(options, function(i, v){
                						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                					})
                					checkboxischecked(currentanswer);
                				}else if(question.t_id==3){
                					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
                					textareaisempty(currentanswer);
                				}
                				
                				/* $("#q_id").attr("value" , question.q_id); */
                			}	
                		})
                	}
    			})
    		}
    	}else{
    		if($("#t_id").attr("value") == 1 || $("#t_id").attr("value") == 2){
        		console.log("日你妈");
            	if($("#t_id").attr("value")==1 && $('input:radio[name="option"]:checked').val() == null){
            		alert('什么都没选中');
            		$.ajax({
            			url:'deleteAnswerAction',
            			data:{
            				'q_id' : $("#q_id").attr("value")
            			},
            			dataType:'json',
            			success:function(){
            				$.ajax({
                    			url:'queryQuestionAction',
                    			data:{
                    				'page' : parseInt($("#page").attr("value"))+parseInt(1)
                    			}, 
                    			dataType:'json',
                    			success : function(data){
                    				$("#questionnum").empty();
                    				$("#question").empty();
                    				var exam_question = data.exam_question;
                    				var question = data.question;
                    				var options = data.options;
                    				var questionnum = data.questionnum;
                    				var page = data.page;
                    				var currentanswer = data.answerofuser.answer;
                    				$("#q_id").attr("value" , question.q_id);//加入隐藏域
                    				$("#t_id").attr("value" , question.t_id);//加入隐藏域
                    				$("#page").attr("value" , page);//加入隐藏域
                    				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
                    				for(var i=0; i < questionnum; i++){
                    					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
                    				}
                    				$("#question").append("<h3>"+question.q_content+"</h3>");
                    				if(question.t_id==1){
                    					$.each(options, function(i, v){
                        					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                        				})
                        				radioischecked(currentanswer);
                    				}else if(question.t_id==2){
                    					$.each(options, function(i, v){
                    						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                    					})
                    					checkboxischecked(currentanswer);
                    				}else if(question.t_id==3){
                    					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
                    					textareaisempty(currentanswer);
                    				}
                    				
                    				/* $("#q_id").attr("value" , question.q_id); */
                    			}	
                    		})
            			}
            		})
            		
            		}else if($("#t_id").attr("value")==2 && $('input:checkbox[name="option"]:checked').val() == null){
            			alert('什么都没选中');
    	    			$.ajax({
    	    				url:'deleteAnswerAction',
    	    				data:{
    	    					'q_id' : $("#q_id").attr("value")
    	    				},
    	    				dataType:'json',
    	    				success : function(data){
    	    					$.ajax({
            	        			url:'queryQuestionAction',
            	        			data:{
            	        				'page' : parseInt($("#page").attr("value"))+parseInt(1)
            	        			}, 
            	        			dataType:'json',
            	        			success : function(data){
            	        				$("#questionnum").empty();
            	        				$("#question").empty();
            	        				var exam_question = data.exam_question;
            	        				var question = data.question;
            	        				var options = data.options;
            	        				var questionnum = data.questionnum;
            	        				var page = data.page;
            	        				var currentanswer = data.answerofuser.answer;
            	        				$("#q_id").attr("value" , question.q_id);//加入隐藏域
            	        				$("#t_id").attr("value" , question.t_id);//加入隐藏域
            	        				$("#page").attr("value" , page);//加入隐藏域
            	        				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
            	        				for(var i=0; i < questionnum; i++){
            	        					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
            	        				}
            	        				$("#question").append("<h3>"+question.q_content+"</h3>");
            	        				if(question.t_id==1){
            	        					$.each(options, function(i, v){
            	            					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
            	            				})
            	            				radioischecked(currentanswer);
            	        				}else if(question.t_id==2){
            	        					$.each(options, function(i, v){
            	        						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
            	        					})
            	        					checkboxischecked(currentanswer);
            	        				}else if(question.t_id==3){
            	        					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
            	        					textareaisempty(currentanswer);
            	        				}
            	        				
            	        				/* $("#q_id").attr("value" , question.q_id); */
            	        			}	
            	        		})
    	    				}
    	    			})
            		}
            		else{
            			$.ajax({
            			url:'insertAnswerAction?q_id='+$("#q_id").attr("value"),
            			type:'POST',
            			data:$('#formz').serialize(),
            			async:false,
            			success:function(data){
            				$.ajax({
                    			/* url:'queryQuestionAction?'+'page='+$(this).html() */
                    			url:'queryQuestionAction',
                    			data:{
                					'page' : parseInt($("#page").attr("value"))+parseInt(1)
                				}, 
                				dataType:'json',
                				success : function(data){
                					$("#questionnum").empty();
                					$("#question").empty();
                					var exam_question = data.exam_question;
                					var question = data.question;
                					var options = data.options;
                					var questionnum = data.questionnum;
                					var page = data.page;
                					var currentanswer = data.answerofuser.answer;
                					$("#q_id").attr("value" , question.q_id);//加入隐藏域
                					$("#t_id").attr("value" , question.t_id);//加入隐藏域
                					$("#page").attr("value" , page);//加入隐藏域
                					$("#questionnumz").attr("value" , questionnum);//加入隐藏域
                					for(var i=0; i < questionnum; i++){
                						$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
                					}
                					$("#question").append("<h3>"+question.q_content+"</h3>");
                					if(question.t_id==1){
                						$.each(options, function(i, v){
                    					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                    					})
                    					radioischecked(currentanswer);
                					}else if(question.t_id==2){
                						$.each(options, function(i, v){
                							$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                						})
                						checkboxischecked(currentanswer);
                					}else if(question.t_id==3){
                						$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
                						textareaisempty(currentanswer);
                					}
                				
                					/* $("#q_id").attr("value" , question.q_id); */
                				
                				}
                    		
                    		})
            			}
            	})}
        	}else{//如果不是选择题
        		$.ajax({
            		url:'insertAnswerAction1?q_id='+$("#q_id").attr("value"),
            		type:'POST',
            		data:$('#formz').serialize(),
            		async:false,
            		success:function(data){
            			$.ajax({
                    		/* url:'queryQuestionAction?'+'page='+$(this).html() */
                    		url:'queryQuestionAction',
                    		data:{
                				'page' : parseInt($("#page").attr("value"))+parseInt(1)
                			}, 
                			dataType:'json',
                			success : function(data){
                				$("#questionnum").empty();
                				$("#question").empty();
                				var exam_question = data.exam_question;
                				var question = data.question;
                				var options = data.options;
                				var questionnum = data.questionnum;
                				var page = data.page;
                				var currentanswer = data.answerofuser.answer;
                				$("#q_id").attr("value" , question.q_id);//加入隐藏域
                				$("#t_id").attr("value" , question.t_id);//加入隐藏域
                				$("#page").attr("value" , page);//加入隐藏域
                				$("#questionnumz").attr("value" , questionnum);//加入隐藏域
                				for(var i=0; i < questionnum; i++){
                					$("#questionnum").append("<button class='btn btn-default but'>" + (i+1) + "</button>");
                				}
                				$("#question").append("<h3>"+question.q_content+"</h3>");
                				if(question.t_id==1){
                					$.each(options, function(i, v){
                    					$("#question").append("<input type='radio' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                    				})
                    				radioischecked(currentanswer);
                				}else if(question.t_id==2){
                					$.each(options, function(i, v){
                						$("#question").append("<input type='checkbox' id='"+ v.abcd + "' class='option' name='option' value="+v.abcd+"><span>"+v.abcd+". "+v.o_content+"<span><br>");
                					})
                					checkboxischecked(currentanswer);
                				}else if(question.t_id==3){
                					$("#question").append("<textarea id='textareaz' name='textareaz' class='form-control' rows='3'></textarea> ");
                					textareaisempty(currentanswer);
                				}
                				
                				/* $("#q_id").attr("value" , question.q_id); */
                				
                			}
                    		
                    	})
            		}
            	})
        	}
    	}
    		
    })
    
    var radioischecked = function(answer){
        console.log("进入此方法1!");
        var answers = answer.split('');
        var options = $('input:radio[name="option"]');
        for(var i=0;i<answers.length;i++){
        	for(var j=0;j<options.length;j++){
        		if(answers[i]==options.eq(j).attr("value")){
        			options.eq(j).attr("checked",true);
        		}
        	}
        }
        
    }
    
    var checkboxischecked = function(answer){
    	console.log("进入此方法2!");
    	var answers = answer.split('');
        var options = $('input:checkbox[name="option"]');
        for(var i=0;i<answers.length;i++){
        	for(var j=0;j<options.length;j++){
        		if(answers[i]==options.eq(j).attr("value")){
        			options.eq(j).attr("checked",true);
        		}
        	}
        }
    }
    
    var textareaisempty = function(answer){
    	$("#textareaz").html(answer);
    }
    
 }
    
        		
</script>
</html>