<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<%@ page errorPage="Error_page_deal.jsp" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="css/font-awesome.min.css" type="text/javascript" rel="stylesheet">
<link href="css/bootsnav.css" type="text/css" rel="stylesheet">
<link href="css/normalize.css" type="text/css" rel="stylesheet">
<link href="css/css.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script src="js/bootsnav.js" type="text/javascript"></script>
<script src="http://cdn.bootcss.com/jquery/1.12.3/jquery.min.js"></script>
<script src="layer/layer/layer.js"></script>
<!-- <script src="js/jquery.js" type="text/javascript"></script> -->
<!--[if IE]><script src="js/html5.js"></script><![endif]-->
<title>登录校园二手交易网</title>
</head>
<%  
	//这部分用来接收成功提示
	String successMessage = "";
	successMessage = (String)session.getAttribute("successMessage");
	if("".equals(successMessage)  || successMessage==null)
    {}
 	else
 	{
%>
		<script language="javascript">
		layer.msg('<%=successMessage%>', {icon: 6});//这句话好坑，坑了我一晚上		    
		</script>
<% 
		session.setAttribute("successMessage", "");  //把message从新设置为空
	}
	
	//这部分用来接收一般错误提示
	String message="";
    message=(String)session.getAttribute("message");
    
    if("".equals(message)  || message==null)
    {}
 	else
 	{
%>
		<script language="javascript">
		layer.msg('<%=message%>', {icon: 5});//这句话好坑，坑了我一晚上		    
		</script>
<% 
		session.setAttribute("message", "");  //把message从新设置为空
	}
%>
<script language="javascript">

//登录操作
function check() 
{ 
	if (form_login.username.value == "") {
	
		layer.msg("请输入你的账号/学号");
		form_login.username.focus();
		return false;
		
	}
	if (form_login.username.value.length != 12 && form_login.username.value.length != 6) {
		
		layer.msg('账号格式错误');
		form_login.username.focus();
		return false;
		
	}
	if(form_login.password.value=="") {
	
		layer.msg('请输入你的密码');
		form_login.password.focus();
		return false;	
	}
}
</script>

<body class="logobg_style">
  	<div id="large-header" class="large-header login-page">
  		<canvas id="demo-canvas" width="1590" height="711"></canvas>
  		<div class="login-form" style="top: 45%;left: 50%;">
  		
  			<div class="login-content">
  				<h2 class="title_name">欢迎您登录</h2>
  				
  				<form action="userLoginController" name="form_login" class="login_padding" method="post" onSubmit="return check();">
				
				<div class="form-group clearfix">
					<div class="input-group">
						<div class="input-group-addon">
							<i class="icon_user"></i>
						</div>
						<input type="text" class="form-control" name="username" id="username" placeholder="请输入账号/学号" autocomplete="off" oninput="value=value.replace(/[^\d]/g , '')">
					</div>
				</div>
				

				<div class="form-group clearfix">
					<div class="input-group">
						<div class="input-group-addon">
							<i class="icon_password"></i>
						</div>
						<input type="password" class="form-control" name="password" id="password" placeholder="请输入密码" autocomplete="off">
					</div>
				</div>
				
				
					<div class=" textright"><a href="Retrieve_password.jsp" class="forget">忘记密码？</a></div>
                  <div class="tishi"></div>
				<div class="form-group">
				<input type="submit" value="登录" class="btn btn-danger btn-block btn-login">
						
				</div>
				<div class=" textright"><a href="register.jsp" class="forget">立即注册</a></div>
				<!-- Implemented in v1.1.4 -->				
				<div class="form-group">			
				</div>
			</form>
  			</div> 			
  		</div>
  	</div>
  	
<script src="js/TweenLite/TweenLite.min.js"></script>
<script src="js/TweenLite/EasePack.min.js"></script>
<script src="js/TweenLite/rAF.js"></script>
<script src="js/TweenLite/demo-1.js"></script>
</body>
</html>
