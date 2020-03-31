<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<%
String user_name = (String)session.getAttribute("userName"); 
String user_id = (String)session.getAttribute("userId");
user_info user = new user_info();
user.setUsername(user_id);
Map userinfo = user.getUserinfo();
String register_time = (String)userinfo.get("register_time");//注册时间
String userGrade = (String)userinfo.get("user_grade");
String userTel = (String)userinfo.get("user_tel");
String userEmail = (String)userinfo.get("user_email");
String userAddress = (String)userinfo.get("user_address");
String selfIntroduce = (String)userinfo.get("self_introduce");
String selfBlessing = (String)userinfo.get("self_blessing");
%>

<html lang="zxx">
<body>
<!--固定页头部分 -->
<%@ include file="header.jsp" %>

<script language="javascript">
//验证表单
function check()
{
	if (updateProfile.userTel.value.length != 11) 
	{		 //验证电话号码格式
		layer.msg('电话号码格式错误');
		updateProfile.userTel.focus();
		return false;
	}
	
	//var myforms=document.forms;
　　var myemail=updateProfile.userEmail.value;  //邮箱格式的正则表达式
　　var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;  
	if(myReg.test(myemail)){  //验证邮箱格式
	　　　　return true;
	}else{
	　　　　layer.msg('请输入正确的邮箱地址');
	　　　　return false;
	}
}
</script>

<!-- 主体部分 -->
<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>基本信息</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="index-1.html">主页</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>我的基本信息</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    
    <!-- Start My Profile -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="row">
            <div class="col-lg-4">
                <aside class="user-info-wrapper">
                    <div class="user-cover account-details">
                        <div class="info-label" data-toggle="tooltip" title="你是一个小可爱，这是你的基本信息哦"><i class="icon-medal"></i>小可爱</div>
                    </div>
                    <div class="user-info">
                        <div class="user-avatar"><a class="edit-avatar" href="#"></a><img src="assets/images/account/user-ava.jpg" alt="User"></div>
                        <div class="user-data">
                            <h4><%=user_name %></h4><span>注册时间： <%=register_time %></span>
                        </div>
                    </div>
                </aside>
                <nav class="list-group">
                    <a class="list-group-item active" href="account-update.jsp"><i class="icon-head"></i>我的基本信息</a>
                    <a class="list-group-item with-badge" href="usuallyController?url=<%="我的订单"%>"><i class="icon-bag"></i>我的订单</a>
                    <a class="list-group-item with-badge" href="usuallyController?url=<%="我的收藏"%>"><i class="icon-heart"></i>我的收藏</a>
                </nav>
            </div>
            <div class="col-lg-8">
                <div class="padding-top-2x mt-2 hidden-lg-up"></div>
                
                
                
			<!--开始填写表单，修改/完善个人基本信息           ，待执行servlet验证       -->
                <form class="row" name="updateProfile" action="usuallyController" method="post" onsubmit="return check()">
                	<input type="hidden" name="url" value="updateProfile">
                	<input type="hidden" name="userId" value="<%=user_id %>">
                	<div class="col-md-6">
                        <div class="form-group">
                            <label for="account-email">学号</label>
                            <input class="form-control" type="text" name="userId" value="<%=user_id %>" disabled>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">姓名</label>
                            <input class="form-control" type="text" name="userName" value="<%=user_name %>" disabled>
                        </div>
                    </div>
                    
					<!--  从这里开始需要更新 -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">电话号码</label>
                            <input class="form-control" type="text" name="userTel" value="<%=userTel %>" required oninput="value=value.replace(/[^\d]/g , '')">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-ln">邮箱</label>
                            <input class="form-control" type="email" name="userEmail" value="<%=userEmail %>" required>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">收货地址</label>
                            <input class="form-control" type="text" name="userAddress" value="<%=userAddress %>" required>
                        </div>
                    </div>
                    
                     <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">年级</label>                 	  
                            <input class="form-control" type="text" name="userGrade" value="<%=userGrade %>" required>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">个人简介</label>
                            <textarea class="form-control" name="selfIntroduce" value="<%=selfIntroduce %>" required></textarea> 
                        </div>
                    </div>
                     <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">对自己的寄语</label>
                            <textarea class="form-control" name="selfBlessing" value="<%=selfBlessing %>" required></textarea> 
                        </div>
                    </div>
                    
                    <div class="col-12">
                        <hr class="mt-2 mb-3">
                        <div class="d-flex flex-wrap justify-content-between align-items-center">
                            
                            <button class="btn btn-primary margin-right-none" type="submit">保存</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- End My Profile -->
</div>


<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>