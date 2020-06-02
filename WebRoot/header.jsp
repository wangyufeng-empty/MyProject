<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 此页面是所有网页的头部信息 -->
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String baseWsPath = request.getServerName()+":"+request.getServerPort()+path+"/";
    String identifier = UUID.randomUUID().toString().replace("-", "");
%>

<head>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <title>校园计算机二手网</title>
    <!-- Mobile Specific Meta Tag -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 标题旁边的图片 -->
    <link rel="icon" type="image/x-icon" href="assets/images/favicon.ico">
    <!-- Bootsrap CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/bootstrap.min.css">
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/font-awesome.min.css">
    <!-- Feather Icons CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/feather-icons.css">
    <!-- Pixeden Icons CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/pixeden.css">
    <!-- Social Icons CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/socicon.css">
    <!-- PhotoSwipe CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/photoswipe.css">
    <!-- Izitoast Notification CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/izitoast.css">
    <!-- Main Style CSS -->
    <link rel="stylesheet" media="screen" href="assets/css/style.css">
    <script src="js/jquery-1.11.0.min.js"></script>
    
    <script src="js/jquery.min.js"></script>
	<script src="js/jQuery.upload.min.js"></script>
	<script src="js/SecondHandPages_JS/headerJs.js"></script>
	
	<link rel="stylesheet" href="css/upload.css">
    <link rel="stylesheet" type="text/css" href="assets/css/loadGif.css"/>
    
	<script src="layer/layer/layer.js"></script>
	<link rel="stylesheet" href="layui/css/layui.css">
	<script src="layui/layui.js"></script>
    <style>
        .ssdtitle{font-size:18px;}
        .ssdtitle:before{
            content:'';
            background-image:url('images/msg.png');
            background-size:25px 25px;
            vertical-align:text-bottom;
            display:inline-block;
            width:25px;
            height:25px;
            border:0;
            top:0;
            left:0;
        }
    </style>
</head>
<%--/********************************技术亮点：以后所有信息可以直接显示，不用重新白屏显示，然后跳转************************************************/ --%>
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

<%
String username = (String)session.getAttribute("userName"); //从登录servlet获取用户名
String userId = (String)session.getAttribute("userId");  //从登录servlet获取用户ID号
%>
<input type="hidden" id="baseWsPath" value="<%=baseWsPath%>"/>
<input type="hidden" id="basePath" value="<%=basePath%>"/>
<input type="hidden" id="identifier" value="<%=identifier%>"/>
<!-- 开始顶部侧拉菜单 -->
<div class="offcanvas-container" id="shop-categories">
    <div class="offcanvas-header">
        <h3 class="offcanvas-title" style="color: white;">分类浏览</h3>
    </div>
    
    <nav class="offcanvas-menu">
        <ul class="menu">
        
            <li class="has-children">
                <span>
                    <a category="计算机书籍" class="CategorySearch" href="#">计算机书籍</a>                
                </span>
                <span>
                   <a category="耳机" class="CategorySearch" href="#">耳机</a>                 
                </span>
                <span>
                    <a category="电脑" class="CategorySearch" href="#">电脑</a>                 
                </span>
                <span>
                    <a category="相机" class="CategorySearch" href="#">相机</a>                 
                </span>
                <span>
                    <a category="单片机" class="CategorySearch" href="#">单片机</a>                
                </span>
                <span>
                    <a category="开发软件/工具" class="CategorySearch" href="#">开发软件/工具</a>
                </span>
            </li>
            
        </ul>
    </nav>
</div>
<!-- End Shop Category Menu -->

<!-- Start TopBar -->
<div class="topbar">
    <div class="topbar-column">
        <a class="hidden-md-down" href="#"><i class="layui-icon layui-icon-cellphone-fine"></i>&nbsp;&nbsp;17633360183</a>
        <a class="hidden-md-down" href="#"><i class="layui-icon layui-icon-login-qq"></i>&nbsp;&nbsp;905202254</a>
        <a class="hidden-md-down" href="#"><i class="layui-icon layui-icon-release"></i>&nbsp;&nbsp;905202254@qq.com</a>
        <a class="hidden-md-down" href="#"><i class="layui-icon layui-icon-username"></i>&nbsp;&nbsp;王宇峰制作</a>
    </div>
    
</div>
<!-- End TopBar -->

<!-- Start NavBar -->
<header class="navbar navbar-sticky">

    <!-- 开始搜索部分 -->
    <form class="site-search" id="site-search" method="post" onsubmit="return false">
        <input type="text" name="search_key" placeholder="输入关键字搜索商品...">
        <!-- 用于区分是哪个表单 -->
        <input type="hidden" name="url" value="site-search" />
        
        <div class="search-tools">
            <span class="clear-search">清除</span>
            <span class="close-search"><i class="icon-cross"></i></span>
        </div>
      
    </form>
    <!-- 结束搜索部分 -->
    
	<!--开始返回搜索结果 -->

                                              <!-- 开始LOGO部分 -->
    <div class="site-branding">
        <div class="inner">
            <a class="offcanvas-toggle cats-toggle" href="#shop-categories" data-toggle="offcanvas"></a>
            <a class="offcanvas-toggle menu-toggle" href="#mobile-menu" data-toggle="offcanvas"></a>
            <a class="site-logo" href="index.jsp"><img src="assets/images/logo/logo.png" alt="Inspina"></a>
        </div>
    </div>
                                               <!-- 结束LOGO部分 -->
    
    <!-- 开始顶部导航栏部分 -->
    <nav class="site-menu">
        <ul>
<!--    class="active"  用来加亮标签 -->
            <li >
                <a href="#" onclick="NavigationJump('getAllRotationChart')"><span>主页</span></a>
            </li>
            
            <li>            
                <a href="#" onclick="NavigationJump('全部商品')"><span>全部商品</span></a>
            </li>
            
            <li>
                <a href="#" onclick="NavigationJump('查看购物车')"><span>购物车</span></a>
            </li>
            
            <li>
                <a href="#" onclick="NavigationJump('我的订单')"><span>我的订单</span></a>
            </li>
            
            <li>
                <a href="Publish-Goods.jsp"><span>发布商品</span></a>
            </li>
            
             <li>
                <a href="#" onclick="NavigationJump('我的发布')"><span>我的发布</span></a>
            </li>
            
            <li>
                <a href="#" onclick="NavigationJump('我的收藏')"><span>我的收藏</span></a>
            </li>
            
            <li>
                <a href="#" onclick="NavigationJump('个人中心')"><span>个人中心</span></a>
            </li>
            <li>
                <a href="#" onclick="NavigationJump('通知公告')"><span>通知公告</span></a>
            </li>
            
        </ul>
    </nav>
    <!-- 结束顶部导航栏 -->
    
    <!-- 开始工具栏 -->
    <div class="toolbar">
        <div class="inner">
            <div class="tools">
                <div class="search"><i class="icon-search"></i></div>
                
                <!-- 开始账户 -->
                <div class="account">
                    <a href="#"></a><i class="icon-head"></i>
                    <ul class="toolbar-dropdown">
                        <li class="sub-menu-user">
                            <div class="user-ava">
                                <img src="assets/images/account/user-ava-sm.jpg" >
                            </div>
                            <div class="user-info">
                                <input type="hidden" id="username" value="<%= username%>"/>
                                <input type="hidden" id="userId" value="<%=userId%>"/>
                                <h6 class="user-name"><%= username%></h6>
                                <span class="text-xs text-muted">普通用户</span>
                            </div>
                        </li>
                        <li><a href="usuallyController?url=<%="userInfoDemo"%>&userId=<%=userId%>">基本信息</a></li>
                        <li class="sub-menu-separator"></li>
                        <li>
                        <a href="usuallyController?url=<%="loginout"%>" ><i class="layui-icon">&#xe60c;</i> 退出登录</a>                      
                        </li>
                    </ul>
                </div>
                <!-- 结束账户 -->
                
            </div>
        </div>
    </div>
    <!-- End Toolbar -->
    
</header>
<script src="layui/im.js"></script>
<script src="js/announcementWS.js"></script>

<!-- End NavBar -->
