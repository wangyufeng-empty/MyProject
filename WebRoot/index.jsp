<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx" >
<body style="height: 100%">

<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/SecondHandPages_JS/indexJs.js"></script>
<script src="js/jquery.js" type="text/javascript"></script>

<div class="offcanvas-wrapper">
    <!-- 开始图片轮播 -->
<div id="rotationChart"> 
  	
  	<div class='hero-slider home-1-hero'>
    <div class='owl-carousel large-controls dots-inside' data-owl-carousel='{"nav": true, "dots": true, "loop": true, "autoplay": true, "autoplayTimeou": 7000}'>
    
	   	 <c:forEach items="${sessionScope.RotationChartList}" var="RotationChart" varStatus="status">	
			   	<!--开始一张图片 -->
		        <div class='item'>
		            <div class='container padding-top-3x'>
		                <div class='row justify-content-center align-items-center'>
		                    <div class='col-lg-5 col-md-6 padding-bottom-2x text-md-left text-center hidden-md-down'>
		                        <div class='from-bottom'>
		                            <img class='d-inline-block w-150 mb-4' src='assets/images/hero-slider/logo02.png' alt='Puma'>
		                            <div class='h2 text-body text-normal mb-2 pt-1'>${RotationChart.goods_name }</div>
		                            <div class='h2 text-body text-normal mb-4 pb-1'>最低只要
		                                <span class='text-bold'>${RotationChart.goods_price }元</span></div>
		                        </div>
		                        <a class="btn btn-primary scale-up delay-1" href="usuallyController?url=商品详情&goods_id=${RotationChart.goods_id }">查看详情</a></div>
		                    <div class='col-md-6 padding-bottom-2x mb-3'>
		                        <img class='d-block mx-auto' src="${RotationChart.product_image }" alt='Puma Backpack' style="width: 500px;height: 400px;"></div>
		                </div>
		            </div>
		        </div>
		        <!--结束一张图片 -->
	   	 </c:forEach>
		
    </div>
</div>
  	
  	
</div>
    <!-- End Main Slider -->
    
    
    <!-- 开始首页推荐商品 -->
    <section class="container padding-top-3x">
        <h3 class="text-center mb-30">最新发布</h3>
        <div class="row">
        
         <c:forEach items="${sessionScope.LastestGoodsList}" var="LastestGoods" varStatus="status">	
         
         	<div class="col-md-4 col-sm-6 home-cat">
                <div class="card">
                    <a class="card-img-tiles" href="#">
                        <div class="inner">
                        	<c:if test="${not empty LastestGoods.product_image}"> 
	        					<div class="main-img">
                                	<img src="${LastestGoods.product_image}" alt="Category">
                            	</div>
    						</c:if>
    					
                     		<c:if test="${empty LastestGoods.product_image}">  
	        					<div class="main-img">
                                	<img src="assets/images/nopic.jpg" alt="Category">
                            	</div>
    						</c:if> 
                            
                            
                            <div class="thumblist">
                             	<img src="assets/images/cat.jfif" alt="Category">
<!--                                 <img src="assets/images/shop/categories/03.jpg" alt="Category"> -->
                            </div>
                        </div>
                    </a>
                    <div class="card-body text-center">
                        <h4 class="card-title">${LastestGoods.goods_name}</h4>
                        <p class="text-muted">最低${LastestGoods.goods_price}元</p>
                        <a class="btn btn-outline-primary btn-sm" href="usuallyController?url=<%="商品详情"%>&goods_id=${LastestGoods.goods_id}">查看详情</a>
                    </div>
                </div>
            </div>
         
         </c:forEach>
            
            
        </div>
    </section>
    <!-- End Top Categories -->
    
    
    <!-- 开始推荐特征商品    商品可以进行拖动！！！！！！！-->
    <section class="container padding-top-3x padding-bottom-3x">
        <h3 class="text-center mb-30">为您推荐</h3>
        <div class="owl-carousel"
             data-owl-carousel='{"nav": false, "dots": false, "margin": 30, "responsive": {"0":{"items":1},"576":{"items":2},"768":{"items":3},"991":{"items":4},"1200":{"items":4}} }'>
             
             
            <c:forEach items="${sessionScope.IR_Goods_Infos}" var="IR_Goods_Info">	
                	
				 <!-- Start Product  -->
            	<div class="grid-item">
                	<div class="product-card">
                	
                		<c:if test="${not empty IR_Goods_Info.product_image}"> 
        					<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">
                        		<img src="${IR_Goods_Info.product_image}" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">
                    		</a>
    					</c:if>
    					
                     	<c:if test="${empty IR_Goods_Info.product_image}">  
        					<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">
                        		<img src="assets/images/nopic.jpg" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">
                    		</a>
    					</c:if> 
                    	
                   		<h3 class="product-title"><a href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">${IR_Goods_Info.goods_name}</a></h3>
                    	<h4 class="product-price">${IR_Goods_Info.goods_price} 元</h4>
                    	<div class="product-buttons">
                        	<div class="product-buttons">
                            	<button class="btn btn-outline-secondary btn-sm" title="加入收藏" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${IR_Goods_Info.goods_name}" data-toast-message="已选中!" id="${IR_Goods_Info.goods_id}" onClick="collectGoods(this)" >
                                	<i class="icon-heart" id="icon_heart"></i>
                            	</button>
                            	<button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${IR_Goods_Info.goods_name}" data-toast-message="已选中!" id="${IR_Goods_Info.goods_id}" onclick="addToCart(this)">
                            			<i class="icon-bag"></i>加入购物车
                            	</button>
                        	</div>
                    	</div>
                	</div>
            	</div>
            	<!-- End Product #1 -->
					
			</c:forEach>
             
        </div>
    </section>
    <!-- 结束推荐商品 -->
   
</div> 
<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
   
</body>
</html>
