<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<!-- 此页面每次刷新随机显示一个背景，并且格子选项随机排布 -->

<%
Map userInfo = (Map)request.getAttribute("userInfo");//获取到用户的基本信息

String user_Id = (String)userInfo.get("user_id");//取出学号
String user_Name = (String)userInfo.get("user_name");//取出姓名
String userSex = (String)userInfo.get("user_sex");//取出性别
String userGrade = (String)userInfo.get("user_grade");//取出年级
String userHobby = (String)userInfo.get("user_hobby");//取出爱好
String userIntroduce = (String)userInfo.get("self_introduce");//取出自我介绍
String userBlessing = (String)userInfo.get("self_blessing");//取出祝福
String userTel = (String)userInfo.get("user_tel");//取出电话号码
String userEmail = (String)userInfo.get("user_email");//取出邮箱
%>
<html>
<head>
<meta charset="utf-8">
<title>我的个人信息</title>

<link href="css/main.css" rel="stylesheet" media="all" />

<script src="js/jQuery2.js" type="text/javascript"></script>
<script src="js/fun.base.js" type="text/javascript"></script>
<script src="js/julying.gridmenu.js" type="text/javascript"></script>
<script src="js/julying.planettravel.js" type="text/javascript"></script>
<script src="js/home.js" type="text/javascript"></script>

</head>
<body style="height: 100%">


<div id="julyingGridMenu" class="gridMenu">
	<h1 class="logos"><a href="index.jsp">校园二手交易网</a></h1>
	<div class="position">
        <div id="about-me" class="about-me item">
            <div class="show">
            	<div class="thumb">01</div>
                <div class="small-thumb"></div>
            	<div class="title">关于我</div>
                <div class="close"></div>
            </div>            
        </div>      
       <div class="about-me-body body">        	
            <div class="show">
            	<div class="me"></div>
                <div class="about"> 
                  <strong class="name"><%=user_Name %></strong>，学生
                  <p>我是一个<%=userGrade %>的<%=userSex %>生</p>
                  <p>爱好<%=userHobby %>。</p>
                </div><div class="clear"></div>
                
                <div class="introduction">
                    <p><%=userIntroduce %></p>
                    <p>我经常会行走各地拍摄自然风景，喜欢无人机。也会忙里偷闲，开发一些特别的、实用的、开源的程序。</p>
                    <p>我喜欢编写有挑战性的程序。如果您也热爱摄影、编程，欢迎和我一起讨论深度摄影、编程技术。 </p>
                    <p>我祝愿自己：<%= userBlessing%></p>
                </div>
         	</div><!-- .show-->
            <div class="bg" child-num="20"></div>
        </div><!-- .about-me-body-->
        

        <div id="blog" class="blog item">
            <div class="show">
            	<div class="thumb">02</div>
                <div class="small-thumb"></div>
            	<div class="title"><a href="account-update.jsp">完善/修改信息</a></div>                
                <div class="close"></div>
            </div>
        </div>
        
        
        
        <div id="contact-me" class="contact-me item">
            <div class="show">
            	<div class="thumb">03</div>
                <div class="small-thumb"></div>
            	<div class="title">名片</div>
                <div class="close"></div>
            </div>
        </div>
        <div class="contact-me-body body">
            <div class="show">
            	<div class="business-card">                	
&lt;card&gt;
<ul>
	<li>&lt;学号&gt;<strong><%=user_Id %></strong><span>&lt;/学号&gt;</span></li>
	<li>&lt;姓名&gt;<strong><%=user_Name %></strong><span>&lt;/姓名&gt;</span></li>
	<li>&lt;性别&gt;<strong><%=userSex %></strong><span>&lt;/性别&gt;</span></li>
	<li>&lt;年级&gt;<strong><%=userGrade %></strong><span>&lt;/年级&gt;</span></li>
	<li>&lt;爱好&gt;<strong><%=userHobby %></strong><span>&lt;/爱好&gt;</span></li>
	<li>&lt;tel-num&gt;	<strong name="replace" val="<%=userTel%>">loading...</strong><span>&lt;/tel-num&gt;</span></li>
	<li>&lt;email&gt;<strong name="replace" val="<%=userEmail%>">loading...</strong><span>&lt;/email&gt;</span></li>
	
</ul>  
&lt;/card&gt;   
				<div class="expand"></div>                            
                </div>
                
            
         	</div>
            <div class="bg" child-num="0"></div>
        </div>
        
        <div class="cells"><li class="cell"></li></div>
    </div>
</div><!-- #julyingGridMenu -->
  

</body>
</html>