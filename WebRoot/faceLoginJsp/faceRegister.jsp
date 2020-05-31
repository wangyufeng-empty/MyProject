<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>校园二手交易网刷脸登录</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/faceLogin/style.css" />

<script type="text/javascript" src="<%=basePath%>/js/faceLogin/jquery-1.4.4.min.js"></script>
<style>
body {
	height: 100%;
	background: #213838;
	overflow: hidden;
}

canvas {
	z-index: -1;
	position: absolute;
}
</style>
<script src="<%=basePath%>/js/faceLogin/jquery.js"></script>
<script src="<%=basePath%>/js/faceLogin/verificationNumbers.js"></script>
<script src="<%=basePath%>/js/faceLogin/Particleground.js"></script>
<script>
$(document).ready(function() {
	
  //粒子背景特效
  $('body').particleground({
    dotColor: '#5cbdaa',
    lineColor: '#5cbdaa'
  });
  
  var reqId = $("#reqUser_Id_lg_reg").val();
  if(reqId=="null"){
  
	layer.confirm("错误，危险操作 !", 
	{
		btn:['明白了'],
		title:'警告',
		icon: 2, 
		cancel:function(index, layero){
               window.location = "login.jsp";
         },
         btn1: function(index){
			 	window.location = "login.jsp";
			 	layer.close(index);
		 },
	});
  }
  
  
});
</script>
<script src="<%=basePath%>/layer/layer/layer.js"></script>

<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

body {
	height: 100vh;
	background-position: center;
	overflow: hidden;
}

h1 {
	color: #fff;
	text-align: center;
	font-weight: 100;
	margin-top: 40px;
}

#media {
	width: 280px;
	height: 250px;
	margin: 10px auto 0;
	border-radius: 30px;
	overflow: hidden;
	opacity: 0.7px;
}

#video {
	
}

#canvas {
	display: none;
}

#btn {
	width: 160px;
	height: 50px;
	background: #03a9f4;
	margin: 70px auto 0;
	text-align: center;
	line-height: 50px;
	color: #fff;
	border-radius: 40px;
}
</style>
</head>

<body>
	<input type="text" hidden="hidden" id="reqUser_Id_lg_reg" value="<%=session.getAttribute("reqUser_Id") %>"/>
	<form action="#" method="post" onsubmit="return false">
		<dl class="admin_login">
			
			<dt>
				<strong>校园二手交易网</strong><em>面部信息注册</em> <strong>请把你的脸部靠近放摄像头面前</strong>
			</dt>
			
			<div id="media">
				<video id="video" width="300" height="300" autoplay></video>
				<canvas id="canvas" width="300" height="300" hidden="hidden"></canvas>
			</div>
			
			<dd>
				<input type="button" onclick="face_Register()" value="立即注册面部信息"
					class="submit_btn" />
				<input type="button" onclick="javascript:window.location='login.jsp'" value="返回"
				class="submit_btn" />
			</dd>

		</dl>
		</form>
		
		<script type="text/javascript">
  		//var 是定义变量
  		//let声明局部变量，只在let命令所在的代码块内有效
//   		var video = document.getElementById("video"); //获取video标签
//   		var canvas = document.getElementById('canvas');
// 		var context = canvas.getContext('2d');
		
// 		//导航 获取用户媒体对象
// 		navigator.mediaDevices.getUserMedia({
//     		video: true
//   		}).then(function (mediaStream) {
//     		video.srcObject = mediaStream;
//    		 	video.onloadedmetadata = function () {
//       		/* myVideo.controls = "controls"; 不显示控件*/
//       			video.play();
//     		}
//   		});

		var canvas = document.getElementById("canvas"),
            context = canvas.getContext("2d"),
            video = document.getElementById("video"),
            videoObj = { "video": true },
            errBack = function(error) {
                console.log("Video capture error: ", error.code);
            };
            
        // Put video listeners into place
        if(navigator.getUserMedia) { // Standard
            navigator.getUserMedia(videoObj, function(stream) {
                video.srcObject = stream;               
                video.play();
            }, errBack);
        } else if(navigator.webkitGetUserMedia) { // WebKit-prefixed
            navigator.webkitGetUserMedia(videoObj, function(stream){
                video.srcObject = window.webkitURL.createObjectURL(stream);
                video.play();
            }, errBack);
        } else if(navigator.mozGetUserMedia) { // WebKit-prefixed
            navigator.mozGetUserMedia(videoObj, function(stream){
                video.srcObject = window.URL.createObjectURL(stream);
                video.play();
            }, errBack);
        }
  		
  		function getBase64() {
			context.drawImage(video,0,0,300,300);
			var imgSrc = canvas.toDataURL("image/png").split("base64,")[1];
			//console.log(imgSrc);
			return imgSrc;

		};
  	
		function face_Register(){
			
			//把流媒体数据画到convas画布上去
			var reqUser_Id = $("#reqUser_Id_lg_reg").val();
			
			var imgSrc = getBase64();
			//var user_id = $("#user_id").val();
			var data = {
					"method":"faceAdd",
	                "imageBASE64":imgSrc,	                
 	                "user_id":reqUser_Id,
 	                "user_name":"user_test",
	            };
			$.ajax({
	            type: "POST",
	            url:'faceLoginController',
	            dataType:'json', 
	            cache: false,
	            data:data,
	            beforeSend: function (){
	             //ajax刷新前加载load动画
	
				 index = layer.load(5, {time: 30*1000,shade: [0.1,'#fff']});
         		},
	            success:function(data){
	              	var resultJsonObject= eval(data);
	              	 //alert(resultJsonObject.score);
	                var resultState = resultJsonObject.resultState;
	                var resultMessage = resultJsonObject.resultMessage;
	                var error_code = resultJsonObject.error_code;
	                
	                console.log("resultState :"+resultState);
	                console.log("resultMessage :"+resultMessage);
                    if(resultState=="1"){
                    	layer.confirm(resultMessage+"是否进行面部识别登录？", function() {
                    		 window.location="faceLoginJsp/faceLogin.jsp";                  	
                    	});
                    	
                    }
                   else{                    
                      layer.alert(resultMessage);
                    }
	            },
	             error:function(){ 
					 layer.alert("请求失败！");
				 },
	            complete:function () {			 
	             	//完成以后隐藏load动画
				 	 layer.close(index);
         		}
        	})

		}
		
		
		</script>
	
</body>
</html>
