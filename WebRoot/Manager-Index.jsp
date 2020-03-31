<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,java.sql.*,beans.*" pageEncoding="utf-8" %>
<%@ page errorPage="Error_page_deal.jsp" %>  <!-- 加上了错误处理信息，跳转到页面 Error_page_deal.jsp-->
<!DOCTYPE html>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>王宇峰的校园二手网</title>
  <link rel="stylesheet" href="layui/css/layui.css">
  <link rel="stylesheet" href="css/Load.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">校园二手网</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="main_index.jsp" target="content">主页</a></li>
      <li class="layui-nav-item"><a href="carts_display.jsp" target="content">数据统计</a></li>
      <li class="layui-nav-item"><a href="" target="content">报表生成</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          <%String user_name=(String)session.getAttribute("adminName"); %>
        	 管理员：<%=user_name %>
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="login.jsp" >退了</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="layui-nav-item" >商品管理</a>
          <dl class="layui-nav-child">
            <dd><a href="main_index.jsp" target="content">添加</a></dd>
            <dd><a href="javascript:;">删除</a></dd>   
            <dd><a href="javascript:;">修改</a></dd>
            <dd><a href="">质检</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">用户信息管理</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;">添加</a></dd>
            <dd><a href="javascript:;">删除</a></dd>
            <dd><a href="">修改</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item"><a href="">订单管理</a></li>
        <li class="layui-nav-item"><a href="">评论管理</a></li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div id = "contentDiv"><iframe name = "content" id = "contentIframe"></iframe></div>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    © 网页设计版权所有 王宇峰
  </div>
</div>
<script src="../layui/layui.js"></script>
<script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
  
});
</script>
</body>
</html>