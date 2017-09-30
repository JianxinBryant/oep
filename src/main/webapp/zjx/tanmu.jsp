<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="Generator" content="EditPlus®">
<meta name="Author" content="natural_live">
<meta name="Keywords" content="barrage">
<meta name="Description" content="">
<title>弹幕</title>
<style>
* {
	margin: 0;
	padding: 0;
}

#barrage {
	margin: auto;
	margin-top: 50px;
	position: relative;
	width: 1000px;
	height: 600px;
	background: #fff;
	border: 2px solid #ffcc00;
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
<script type="text/javascript">
	var host = "localhost"; //172.18.23.77

	function handleOnMessage(){
		console.log("收到消息");
	}


	//打开webSocket连接
	var webSocket = new WebSocket("ws://"+host+":8080/sub_note/DanMuServer");

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
</script>

</head>
<body>

	<div id="barrage"></div>
	<div id="btn">
		<input type="text" id="text"></input> <input type="button" id="submit"
			value="发送"></input>
	</div>
	<script>
		var timer = null;
		var current = [];//存储当前输入框的内容  
		var newarr = [];//存储每个弹幕距左边框的距离  
		var num = new Array();//数组，用来存储划分每个块的序号  

		for (var i = 0; i < $("barrage").offsetHeight / 20 - 1; i++) {
			num.splice(i, 0, i);//将整个显示框划分成多个块，并对每个块进行标号  
			 
		}

		window.onload = function() {//加载页面发生的事件  

			clearInterval(timer);//清除定时器  
		
			timer = setInterval(move, 20);//开启定时器  
		}

		function create(w) {//创建一个弹幕  
			var node = document.createElement("div");//创建一个div元素，用来存储弹幕的信息  
			node.innerHTML = w;
			var t = random(0, num.length - 1);
			node.style.top = num[t] * 20 + "px";//从划分的块中随机选中一块。  
			Delete(num[t]);//删除已被选中的块   
			node.style.left = "950px";
			node.style.color = "#" + randomColor();//随机颜色  
			node.style.fontSize = random(20, 40) + "px";
			$("barrage").appendChild(node);//插入子节点  
		}

		function move() {
			var arr = $("barrage").getElementsByTagName("div");//获取所有的弹幕  
			for (var i = 0; i < arr.length; i++) {

				//将每个弹幕距左边边框的距离分别存储在newarr数组中  
				newarr.push(arr[i].offsetLeft);
				arr[i].style.left = newarr[i] + "px";//更新距离  
				newarr[i] = newarr[i] - 2;//每次减少2px  

				if (newarr[i] < 0) {
					$("barrage").removeChild(arr[i]);
					newarr.splice(i, 1);//在newarr中删除这个div  
				}
			}
		}

		$("submit").onclick = function() {//输入款发送弹幕  
			//create($("text").value);
			current[current.length] = $("text").value;
			webSocket.send($("text").value);
			$("text").value = "";
			
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
</body>
</html>
