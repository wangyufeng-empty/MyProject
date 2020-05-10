<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx" >
<body style="height: 100%">
<script src="js/SecondHandPages_JS/indexJs.js"></script>
<script src="js/jquery.js" type="text/javascript"></script>
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%List IR_Goods_Infos = (List)session.getAttribute("IR_Goods_Infos"); %>
<div class="offcanvas-wrapper">
    <!-- 开始图片轮播 -->
    <div class="hero-slider home-1-hero">
        <div class="owl-carousel large-controls dots-inside" data-owl-carousel='{"nav": true, "dots": true, "loop": true, "autoplay": true, "autoplayTimeou": 7000}'>
        
            <!-- 开始第一张轮播 #1 -->
            <div class="item">
                <div class="container padding-top-3x">
                    <div class="row justify-content-center align-items-center">
                        <div class="col-lg-5 col-md-6 padding-bottom-2x text-md-left text-center hidden-md-down">
                        
                            <div class="from-bottom">
                                <img class="d-inline-block w-150 mb-4" src="assets/images/hero-slider/logo02.png" alt="Puma">
                                <div class="h2 text-body text-normal mb-2 pt-1">JavaWeb实战开发教程</div>
                                <div class="h2 text-body text-normal mb-4 pb-1">最低只要<span class="text-bold">59元</span></div>
                            </div>
                            
                            <a class="btn btn-primary scale-up delay-1" href="#">立即购买</a>
                        </div>
                        <div class="col-md-6 padding-bottom-2x mb-3">
                            <img class="d-block mx-auto" src="assets/images/hero-slider/02.jpg" alt="Puma Backpack">
                        </div>
                    </div>
                </div>
            </div>
            <!-- 结束第一张轮播 #1 -->
            
            
            <!-- Start Slide #2 -->
            <div class="item">
                <div class="container padding-top-3x">
                    <div class="row justify-content-center align-items-center">
                        <div class="col-lg-5 col-md-6 padding-bottom-2x text-md-left text-center hidden-md-down">
                            <div class="from-bottom">
                                <img class="d-inline-block w-200 mb-4" src="assets/images/hero-slider/logo01.png" alt="Converse">
                                <div class="h2 text-body text-normal mb-2 pt-1">Python大数据人工智能开发教程</div>
                                <div class="h2 text-body text-normal mb-4 pb-1">最低只要 <span class="text-bold">88元</span></div>
                            </div>
                            <a class="btn btn-primary scale-up delay-1" href="#">立即购买</a>
                        </div>
                        <div class="col-md-6 padding-bottom-2x mb-3">
                            <img class="d-block mx-auto" src="assets/images/hero-slider/01.jpg" alt="Chuck Taylor All Star II">
                        </div>
                    </div>
                </div>
            </div>
            <!-- End Slide #2 -->
            
            
            <!-- Start Slide #3 -->
            <div class="item">
                <div class="container padding-top-3x">
                    <div class="row justify-content-center align-items-center">
                        <div class="col-lg-5 col-md-6 padding-bottom-2x text-md-left text-center hidden-md-down">
                            <div class="from-bottom">
                                <img class="d-inline-block mb-4 home-1-hero-item" src="assets/images/hero-slider/logo03.png" alt="Motorola">
                                <div class="h2 text-body text-normal mb-2 pt-1">LG 超大计算机显示屏</div>
                                <div class="h2 text-body text-normal mb-4 pb-1">最低只要 <span class="text-bold">2288元</span></div>
                            </div>
                            <a class="btn btn-primary scale-up delay-1" href="#">立即购买</a>
                        </div>
                        <div class="col-md-6 padding-bottom-2x mb-3">
                            <img class="d-block mx-auto" src="assets/images/hero-slider/03.png" alt="Moto 360">
                        </div>
                    </div>
                </div>
            </div>
            <!-- End Slide #3 -->
            
           </div> 
        </div>
    </div>
    <!-- End Main Slider -->
    
    
    <!-- 开始首页推荐商品 -->
    <section class="container padding-top-3x">
        <h3 class="text-center mb-30">最新发布</h3>
        <div class="row">
        
        
            <div class="col-md-4 col-sm-6 home-cat">
                <div class="card">
                    <a class="card-img-tiles" href="#">
                        <div class="inner">
                            <div class="main-img">
                                <img src="assets/images/shop/categories/01.jpg" alt="Category">
                            </div>
                            <div class="thumblist">
                                <img src="assets/images/shop/categories/02.jpg" alt="Category">
                                <img src="assets/images/shop/categories/03.jpg" alt="Category">
                            </div>
                        </div>
                    </a>
                    <div class="card-body text-center">
                        <h4 class="card-title">智能手机</h4>
                        <p class="text-muted">最低2299元</p>
                        <a class="btn btn-outline-primary btn-sm" href="#">查看详情</a>
                    </div>
                </div>
            </div>
            
            
            <div class="col-md-4 col-sm-6 home-cat">
                <div class="card">
                    <a class="card-img-tiles" href="#">
                        <div class="inner">
                            <div class="main-img">
                                <img src="assets/images/shop/categories/04.jpg" alt="Category">
                            </div>
                            <div class="thumblist">
                                <img src="assets/images/shop/categories/05.jpg" alt="Category">
                                <img src="assets/images/shop/categories/06.jpg" alt="Category">
                            </div>
                        </div>
                    </a>
                    <div class="card-body text-center">
                        <h4 class="card-title">笔记本电脑</h4>
                        <p class="text-muted">最低4599元</p>
                        <a class="btn btn-outline-primary btn-sm" href="#">查看详情</a>
                    </div>
                </div>
            </div>
            
            
            <div class="col-md-4 col-sm-6 horizontal-center home-cat">
                <div class="card">
                    <a class="card-img-tiles" href="#">
                        <div class="inner">
                            <div class="main-img">
                                <img src="assets/images/shop/categories/07.jpg" alt="Category">
                            </div>
                            <div class="thumblist">
                                <img src="assets/images/shop/categories/08.jpg" alt="Category">
                                <img src="assets/images/shop/categories/09.jpg" alt="Category">
                            </div>
                        </div>
                    </a>
                    <div class="card-body text-center">
                        <h4 class="card-title">扩展显示屏</h4>
                        <p class="text-muted">最低3000元</p>
                        <a class="btn btn-outline-primary btn-sm" href="#">查看详情</a>
                    </div>
                </div>
            </div>
            
            
        </div>
    </section>
    <!-- End Top Categories -->
    
    
    <!-- 开始推荐特征商品    商品可以进行拖动！！！！！！！-->
    <section class="container padding-top-3x padding-bottom-3x">
        <h3 class="text-center mb-30">为您推荐</h3>
        <div class="owl-carousel"
             data-owl-carousel='{"nav": false, "dots": false, "margin": 30, "responsive": {"0":{"items":1},"576":{"items":2},"768":{"items":3},"991":{"items":4},"1200":{"items":4}} }'>
             
             
            <c:forEach items="${IR_Goods_Infos}" var="IR_Goods_Info">	
                	
				 <!-- Start Product  -->
            	<div class="grid-item">
                	<div class="product-card">
                    	<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">
                        	<img src="assets/images/shop/categories/04.jpg" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${IR_Goods_Info.goods_id}">
                    	</a>
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
    
<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
   
</body>
</html>
