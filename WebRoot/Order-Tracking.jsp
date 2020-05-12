<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面完成点击核算后跳转的用户信息确认 -->
<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<!-- 主体部分 -->
<%String order_id = (String)session.getAttribute("order_id"); %>
<div class="hidden_div" id="coverDiv">
</div>
<div>
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>
<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>跟踪订单</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="Goods-Cart-Demo.jsp">购物车</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>跟踪订单</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    <!-- Start Order Trucking -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="card mb-3">
            <div class="p-4 text-center text-white text-lg bg-dark rounded-top"><span class="text-uppercase">订单号 - </span><span class="text-medium"><%=order_id %></span></div>
            <div class="d-flex flex-wrap flex-sm-nowrap justify-content-between py-3 px-2 bg-secondary">
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>邮寄方式:</span>燕大快递</div>
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>状态:</span> 出货检查</div>
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>预计到达:</span> 2020年6月7日</div>
            </div>
            <div class="card-body">
                <div class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">确认订单</h4>
                    </div>
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">提交订单</h4>
                    </div>
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-location"></i></div>
                        </div>
                        <h4 class="step-title">出货检查</h4>
                    </div>
                    <div class="step">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">发货</h4>
                    </div>
                    <div class="step">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">收货</h4>
                    </div>
                </div>
            </div>
            <div class="padding-top-1x padding-bottom-1x">
                    <a class="btn btn-outline-secondary" href="index.jsp">返回商城</a>           
            </div>
        </div>
    </div>
    <!-- End Order Trucking -->
</div>



<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>