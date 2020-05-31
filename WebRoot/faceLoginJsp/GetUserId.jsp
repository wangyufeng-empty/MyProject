<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<%@ page errorPage="../Error_page_deal.jsp" %>
<% request.setCharacterEncoding("utf-8"); 
%>


<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="../css/font-awesome.min.css" type="text/javascript" rel="stylesheet">
<link href="../css/bootsnav.css" type="text/css" rel="stylesheet">
<link href="../css/normalize.css" type="text/css" rel="stylesheet">
<link href="../css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../assets/css/loadGif.css"/>
<script src="../js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="../js/bootstrap.min.js" type="text/javascript"></script>
<script src="../js/bootsnav.js" type="text/javascript"></script>
<script src="http://cdn.bootcss.com/jquery/1.12.3/jquery.min.js"></script>
<script src="../layer/layer/layer.js"></script>

<script src="../js/faceLogin/face_login.js"></script>

<!-- <script src="js/jquery.js" type="text/javascript"></script> -->
<!--[if IE]><script src="js/html5.js"></script><![endif]-->
<title>面部识别登录</title>
</head>

<body class="logobg_style">
<input type="text" hidden="hidden" id="reqUser_Id" value="<%=session.getAttribute("autoFullId") %>"/>
  	<div id="large-header" class="large-header login-page">
  		<canvas id="demo-canvas" width="1590" height="711"></canvas>
  		<div class="login-form" style="top: 45%;left: 50%;">
  		
  			<div class="login-content">
  			<br>
  				<h2 class="title_name">人脸识别</h2>
  				<h3 class="title_name">请输入已注册学号</h3>
  				<br><br>
  				<form action="#" name="faceLoginForm" id="faceLoginForm" class="login_padding" method="post" onsubmit="return false">
				<input type="hidden" name="method" value=<%= "GetUserId"%> />
				<div class="form-group clearfix">
					<div class="input-group">
						<div class="input-group-addon">
							<i class="icon_user"></i>
						</div>
						<input type="text" class="form-control" name="user_id" id="user_id" placeholder="请输入已注册学号" autocomplete="off"  oninput="value=value.replace(/[^\d]/g , '')">					
						</div>
						<span id="userId_tip"></span>
				</div>
				
                  <div class="tishi"></div>
				<div class="form-group">
				<input type="submit" value="下一步" class="btn btn-danger btn-block btn-login" onclick="faceLoginNext()"/>
						
				</div>
				<div class=" textright"><a href="../login.jsp" class="forget">返回</a></div>
				<!-- Implemented in v1.1.4 -->				
				<div class="form-group">			
				</div>
			</form>
  			</div> 			
  		</div>
  	</div>
  	
<script src="../js/TweenLite/TweenLite.min.js"></script>
<script src="../js/TweenLite/EasePack.min.js"></script>
<script src="../js/TweenLite/rAF.js"></script>
<script src="../js/TweenLite/demo-1.js"></script>
</body>
</html>
