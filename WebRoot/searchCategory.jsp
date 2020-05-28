<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>

<script src="js/SecondHandPages_JS/indexJs.js"></script>
<!-- 获取参数开始 -->
<%
String sort = (String)session.getAttribute("sort");  //得到商品种类
%>
	<section class="container padding-top-3x">
	        <h3 class="text-center mb-30"><%= sort%></h3>
	        <div class="row">
	        
			<c:forEach items="${sessionScope.searchCategoryResult}" var="GoodsInfo" varStatus="status">
				<div class="col-md-4 col-sm-6 home-cat">
	                <div class="card">
	                    <a class="card-img-tiles" href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">
	                        <div class="inner">
	                            <div class="main-img">
	                            	<c:if test="${not empty GoodsInfo.product_image}"> 
	                                	<img src="${GoodsInfo.product_image}" alt="Category" href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">
	                                </c:if>
	                                <c:if test="${empty GoodsInfo.product_image}"> 
	                                	<img src="assets/images/nopic.jpg" alt="Category" href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">
	                                </c:if>
	                            </div>
	                        </div>
	                    </a>
	                    <div class="card-body text-center">
	                        <h4 class="card-title">${GoodsInfo.goods_name}</h4>
	                        <p class="text-muted">最低${GoodsInfo.goods_price}元</p>
	                        <p class="text-muted">库存${GoodsInfo.goods_stock}件</p>
	                         <button class="btn btn-outline-secondary btn-sm " data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${GoodsInfo.goods_name}" data-toast-message="已选中!" title="加入收藏" id="${GoodsInfo.goods_id}" onClick="collectGoods(this)" >
                                	<i class="icon-heart" id="icon_heart"></i>
                            </button>
                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${GoodsInfo.goods_name}" data-toast-message="已选中!" id="${GoodsInfo.goods_id}" onclick="addToCart(this)">
                            			<i class="icon-bag"></i>加入购物车
                            </button>
                        </div>
	                </div>
	            </div>
			</c:forEach>
	 </div>
	    </section>
	    <!-- End Top Categories -->

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>