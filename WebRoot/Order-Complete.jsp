<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面完成点击核算后跳转的用户信息确认 -->
<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%List IR_Goods_Infos = (List)session.getAttribute("IR_Goods_Infos"); %>
<!-- 主体部分 -->
<%String order_id = (String)session.getAttribute("order_id"); %>
<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>完成订单</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="Goods-Cart-Demo.jsp">购物车</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>完成订单</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    <!-- Start Checkout Content -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="card text-center">
            <div class="card-body padding-top-2x">
                <h3 class="card-title">感谢您这次的购物!</h3>
                 <p class="card-text">您本次购物的订单号为： <span class="text-medium"><%=order_id %></span></p><br>
                <p class="card-text">燕山大学二手商品交易网系统由王宇峰全程独立设计，测试。历时将近180天全程打造，目前已经是第6个版本。</p><br>
                <p class="card-text">本系统功能完善，系统稳定，可以实现市面上交易网的大部分核心功能，欢迎使用和体验。</p><br>
                <p class="card-text">Write/Design by: 王宇峰</p>
                <div class="padding-top-1x padding-bottom-1x">
                    <a class="btn btn-outline-secondary" href="index.jsp">返回商城</a>
                    <a class="btn btn-outline-primary" href="Order-Tracking.jsp"><i class="icon-location"></i>&nbsp;跟踪订单</a>
                </div>
            </div>
        </div>
    </div>
    <!-- End Product Content -->
    
    	<!-- 推荐相关产品        -->                                                      
      <!--    Start Related Products -->
        <h3 class="text-center padding-top-3x mb-30">猜您喜欢</h3>
        <div class="owl-carousel" data-owl-carousel='{ "nav": false, "dots": false, "margin": 30, "responsive": {"0":{"items":1},"576":{"items":2},"768":{"items":3},"991":{"items":4},";1200":{"items":4}} }'>
        
            <c:forEach items="${IR_Goods_Infos}" var="IR_Goods_Info">	
            	<!--  Start Product  -->
	            <div class="grid-item">
	                <div class="product-card">
	                    <div class="rating-stars">
	                        <i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i>
	                    </div>
	                    <a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">
	                        <img src="assets/images/shop/products/05.jpg" alt="Product">
	                    </a>
	                    <h3 class="product-title"><a href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">${IR_Goods_Info.goods_name}</a></h3>
	                    <h4 class="product-price">${IR_Goods_Info.goods_price} 元</h4>
	                    <div class="product-buttons">
	                        <div class="product-buttons">
	                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="加入收藏" onclick="location.href='usuallyController?url=<%="加入收藏"%>&goods_id=${IR_Goods_Info.goods_id}&backUrl=<%="Order-Complete.jsp"%>' ">
                                	<i class="icon-heart"></i>
                            	</button>
                            	<button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${IR_Goods_Info.goods_name}" data-toast-message="成功加入购物车!" onclick="location.href='usuallyController?url=<%="加入购物车"%>&goods_id=${IR_Goods_Info.goods_id}&backUrl=<%="Order-Complete.jsp"%>' ">
                            			<i class="icon-bag"></i>加入购物车</button>
	                        </div>
	                    </div>
	                </div>
	            </div>
          		<!--   End Product #2 -->
            
            </c:forEach>
            
         
        </div>
      <!--  End Related Products   -->      
       <!-- 结束智能推荐  -->
</div>


<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>