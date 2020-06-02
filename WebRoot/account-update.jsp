<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %>


<html lang="zxx">
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<input type="hidden" id="user_grade" value="${userMap['user_grade'] }"/>
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
                    <li><a href="index.jsp">主页</a></li>
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
                            <h4>${userMap['user_name'] }</h4><span>注册时间：${userMap.register_time }</span>
                        </div>
                    </div>
                </aside>
                <nav class="list-group">
                    <a class="list-group-item active" href="account-update.jsp"><i class="icon-head"></i>我的基本信息</a>
                    <a class="list-group-item with-badge" href="#" onclick="NavigationJump('我的订单')"><i class="icon-bag"></i>我的订单</a>
                    <a class="list-group-item with-badge" href="#" onclick="NavigationJump('我的收藏')"><i class="icon-heart"></i>我的收藏</a>
                </nav>
            </div>
            <div class="col-lg-8">
                <div class="padding-top-2x mt-2 hidden-lg-up"></div>
                
                
                
			<!--开始填写表单，修改/完善个人基本信息           ，待执行servlet验证       -->
                <form class="row" name="updateProfile" id="updateProfile" action="##" method="post" onsubmit="return false">
                	<input type="hidden" name="url" value="updateProfile">
                	<input type="hidden" name="userId" value="${userMap['user_id'] }">
                	<div class="col-md-6">
                        <div class="form-group">
                            <label for="account-email">学号</label>
                            <input class="form-control" type="text" name="userId" value="${userMap['user_id'] }" disabled>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">姓名</label>
                            <input class="form-control" type="text" name="userName" value="${userMap['user_name'] }" disabled>
                        </div>
                    </div>
                    
					<!--  从这里开始需要更新 -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">电话号码</label>
                            <input class="form-control" type="text" name="userTel" id="userTel" value="${userMap['user_tel'] }" required oninput="value=value.replace(/[^\d]/g , '')">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-ln">邮箱</label>
                            <input class="form-control" type="email" name="userEmail" id="userEmail" value="${userMap['user_email'] }" required>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">收货地址</label>
                            <input class="form-control" type="text" name="userAddress" id="userAddress" value="${userMap['user_address'] }" required>
                        </div>
                    </div>
                    
                     <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">年级</label>                 	   
							<select class="form-control" id="userGrade" name="userGrade">
                            	<option value="">请选择</option>
                                <option value="大一" id="大一">大一</option>
                                <option value="大二" id="大二">大二</option>
                                <option value="大三" id="大三">大三</option>
                                <option value="大四" id="大四">大四</option>                            
                            </select>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">个人简介</label>
                            <textarea class="form-control" name="selfIntroduce" required>${userMap['self_introduce'] }</textarea> 
                        </div>
                    </div>
                     <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">对自己的寄语</label>
                            <textarea class="form-control" name="selfBlessing" required>${userMap['self_blessing'] }</textarea> 
                        </div>
                    </div>
                    
                    <div class="col-12">
                        <hr class="mt-6 mb-6">
                        <div class="d-flex flex-wrap justify-content-between align-items-center">
                            
                            <input type="button" class="btn btn-primary margin-right-none" id="saveUserInfo" value="保存" onclick="SaveUserInfo()"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- End My Profile -->
</div>

<script src="js/SecondHandPages_JS/accountUpdateJs.js"></script> 
<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>