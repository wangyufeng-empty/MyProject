<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<%@ page errorPage="Error_page_deal.jsp" %>
<% request.setCharacterEncoding("UTF-8"); %>
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
<link rel="stylesheet" type="text/css" href="assets/css/loadGif.css"/>
<script src="js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script src="js/bootsnav.js" type="text/javascript"></script>
<script src="http://cdn.bootcss.com/jquery/1.12.3/jquery.min.js"></script>
<script src="layer/layer/layer.js"></script>
<link rel="stylesheet" href="layui/css/layui.css">
<script src="layui/layui.all.js"></script>
<script src="js/SecondHandPages_JS/registerJs.js"></script>
<script src="js/jquery.js" type="text/javascript"></script>
<!--[if IE]><script src="js/html5.js"></script><![endif]-->
<title>注册校园二手交易网账号</title>
</head>
<body class="logobg_style" >

  	<div id="large-header" class="large-header">
  		<canvas id="demo-canvas" width="100%" height="100%"></canvas>  
  		<div class="login-form"> 
  		
<!--   			<div class="login-content">  -->
   				<center><h2 class="title_name">账户注册</h2></center><br> 
  				
				  <form class="layui-form" name="form_register" id="form_register" action="userRegisterController" method="post">
				  
				  <div class="layui-form-item">
				    <label class="layui-form-label">姓名</label>
				    <div class="layui-input-block">
				      <input type="text" name="userName" id="user_name" required  lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
				    </div>
				  </div>
				  
				  <div class="layui-form-item">
				    <label class="layui-form-label">学号</label>
				    <div class="layui-input-block">
				      <input type="text" name="userId" id="user_id" required  lay-verify="required" placeholder="请输入学号" autocomplete="off" class="layui-input" oninput="value=value.replace(/[^\d]/g , '')">
				      <span id="userId_tip"></span>
				    </div>
				  </div>
				  
				  <div class="layui-form-item">
				    <label class="layui-form-label" >密码</label>
				    <div class="layui-input-block">
				      <input type="password" name="password" id="user_psw" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
				    	<span id="password_tip"></span>
				    </div>
				    
				  </div>
				  
				   <div class="layui-form-item">
				    <label class="layui-form-label">确认密码</label>
				    <div class="layui-input-block">
				      <input type="password" name="password_confirm" id="password_confirm" required lay-verify="required" placeholder="请确认密码" autocomplete="off" class="layui-input">
				    <span id="password_confirm_tip"></span>
				    </div>
				  </div>
				  
				  
				  <div class="layui-form-item">
				    <label class="layui-form-label">性别</label>
				    <div class="layui-input-block">
				      <input type="radio" name="userSex" id="user_sex" value="男" title="男">
				      <input type="radio" name="userSex" id="user_sex" value="女" title="女" checked>
				    </div>
				  </div>
				  
				   <div class="layui-form-item">
				    <label class="layui-form-label">爱好</label>
				    <div class="layui-input-block">
				      <input type="checkbox" name="hobby" id="user_hobby" value="人工智能" title="人工智能">
				      <input type="checkbox" name="hobby" id="user_hobby" value="大数据" title="大数据">
				      <input type="checkbox" name="hobby" id="user_hobby" value="数据库" title="数据库">
				      <input type="checkbox" name="hobby" id="user_hobby" value="边缘计算" title="边缘计算">
				      <input type="checkbox" name="hobby" id="user_hobby" value="硬件" title="硬件">
				    </div>
				  </div>
				  
				  <div class="layui-form-item">
				    <label class="layui-form-label">年级</label>
				    <div class="layui-input-block">
				      <select name="userGrade" lay-verify="required" id="user_grade">
				        <option value=""></option>
				        <option value="大一">大一</option>
				        <option value="大二">大二</option>
				        <option value="大三">大三</option>
				        <option value="大四">大四</option>
				      </select>
				    </div>
				  </div>
				  
				  <div class="layui-form-item">
				    <label class="layui-form-label">密保问题</label>
				    <div class="layui-input-block">
				      <select name="userQuestion" lay-verify="required">
				       
				        <option value="userQuestion_motherName">你母亲的姓名是什么？</option>
				        <option value="userQuestion_firstLove">你的初恋是谁？</option>
				      </select>
				      
				      <input type="text" name="userAnswer" required lay-verify="required" placeholder="请输入答案" autocomplete="off" class="layui-input">
				   
				    </div>
				  </div>

				  <div class="layui-form-item">
				    <div class="layui-input-block">
				      <input type="submit" class="layui-btn" value="立即注册" id="register"/>
				      <input type="reset" class="layui-btn layui-btn-primary" id="resetReg" value="重置"/>
				      <input type="button" onclick="location.href='login.jsp'" class="layui-btn layui-btn-primary" value="返回"/>
				    </div>
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
