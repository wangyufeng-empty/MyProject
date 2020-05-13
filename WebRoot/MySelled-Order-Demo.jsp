<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面完成点击核算后跳转的用户信息确认 -->
<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>

<!-- 主体部分 -->
<div class="hidden_div" id="coverDiv">
</div>
<div>
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>
<%
ArrayList SelledOrderList = (ArrayList)session.getAttribute("SelledOrderList"); //全部订单信息
Map userInfo = (HashMap)session.getAttribute("userInfo"); //一条用户信息

String user_name = (String)userInfo.get("user_name");
String register_time = (String)userInfo.get("register_time");//注册时间
%>
<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>我的订单</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="index-1.html">主页</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>我的订单</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    <!-- Start My Orders -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="row">
            <div class="col-lg-4">
                <aside class="user-info-wrapper">
                    <div class="user-cover account-details">
                        <div class="info-label" data-toggle="tooltip" title="你是一个小可爱，这是你的订单信息哦"><i class="icon-medal"></i>小可爱</div>
                    </div>
                    <div class="user-info">
                        <div class="user-avatar"><a class="edit-avatar" href="#"></a><img src="assets/images/account/user-ava.jpg" alt="User"></div>
                        <div class="user-data">
                            <h4><%=user_name %></h4><span>注册时间： <%=register_time %></span>
                        </div>
                    </div>
                </aside>
                <nav class="list-group">
                    <a class="list-group-item with-badge" href="account-update.jsp"><i class="icon-head"></i>我的基本信息</a>
                    <a class="list-group-item with-badge" href="usuallyController?url=<%="我的订单"%>"><i class="icon-bag"></i>我买到的</a>
                    <a class="list-group-item active" href="usuallyController?url=<%="我卖出的"%>"><i class="icon-heart"></i>我卖出的</a>
                  
                </nav>
            </div>
            
            <div class="col-lg-8">
                <div class="padding-top-2x mt-2 hidden-lg-up"></div>
                <div class="table-responsive">
                    <table class="table table-hover margin-bottom-none">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>买家学号</th>
                            <th>买家联系方式</th>
                            <th>商品名</th>
                            <th>数量</th>
                            <th>总计</th>
                        </tr>
                        </thead>
<%				int i=0;
				for(Object SelledOrder : SelledOrderList)
				{	i++;
					Map SelledOrderMap = (HashMap)SelledOrder;
					String buyer_id = (String)SelledOrderMap.get("buyer_id");
					String buyer_tel = (String)SelledOrderMap.get("buyer_tel");
					String goods_name = (String)SelledOrderMap.get("goods_name");
					int selectedQuantity = Integer.parseInt(SelledOrderMap.get("selectedQuantity").toString());
					double subtotal = Double.parseDouble(SelledOrderMap.get("subtotal").toString());
%>
                        <tbody>
                        <tr>
							<!--设计点击订单号可以查看具体的订单信息 -->
                            <td>#<%=i %></td>
                            <td><%=buyer_id %></td>
                            <td><span class="text-success"><%=buyer_tel %></span></td>
                            <td><%=goods_name %></td>
                            <td><%=selectedQuantity %></td>
                            <td><%=subtotal %></td>
                        </tr>                     
                        </tbody>
<%		
				} 		
%>  
                    </table>
                </div>
                <hr>
                <div class="text-right"><a class="btn btn-link-primary margin-bottom-none" href="#"><i class="icon-download"></i>&nbsp;下载订单</a></div>
            </div>
        </div>
    </div>
    <!-- End My Orders -->
</div>


<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>