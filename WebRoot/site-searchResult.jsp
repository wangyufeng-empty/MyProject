<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="js/SecondHandPages_JS/indexJs.js"></script>

 <section class="container padding-top-3x padding-bottom-3x">
        <h3 class="text-center mb-30">搜索结果</h3>
        <div class="owl-carousel"
             data-owl-carousel='{"nav": false, "dots": false, "margin": 30, "responsive": {"0":{"items":1},"576":{"items":2},"768":{"items":3},"991":{"items":4},"1200":{"items":4}} }'>
			<c:forEach items="${sessionScope.SearchResultGoodsInfo}" var="GoodsInfo" varStatus="status">
			
			<!-- Start Product  -->
	            <div class="grid-item">
	                <div class="product-card">
	                    <c:if test="${not empty GoodsInfo.product_image}"> 
   							<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">
                   				<img src="${GoodsInfo.product_image}" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">
               				</a>
						</c:if>
					
              			<c:if test="${empty GoodsInfo.product_image}">  
   							<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">
                   				<img src="assets/images/nopic.jpg" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">
               				</a>
						</c:if> 
	                    <h3 class="product-title"><a href="usuallyController?url=<%="商品详情"%>&goods_id=${GoodsInfo.goods_id}">${GoodsInfo.goods_name}</a></h3>
	                    <h4 class="product-price">${GoodsInfo.goods_price}元</h4>
	                     <h6 class="product-price">库存：${GoodsInfo.goods_stock}件</h6>
	                    <div class="product-buttons">
	                        <div class="product-buttons">
		                        <button class="btn btn-outline-secondary btn-sm " data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${GoodsInfo.goods_name}" data-toast-message="已选中!" title="加入收藏" id="${GoodsInfo.goods_id}" onClick="collectGoods(this)" >
	                                <i class="icon-heart" id="icon_heart"></i>
	                            </button>
	                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${GoodsInfo.goods_name}" data-toast-message="已选中!" id="${GoodsInfo.goods_id}" onclick="addToCart(this)">
	                            	<i class="icon-bag"></i>加入购物车
	                            </button>
                            </div>
	                    </div>
	                </div>
	            </div>
	            <!-- End Product -->
	            
			</c:forEach>       
	            
   
        </div>
    </section>
    <!-- 结束商品 -->
    
      													<!-- 推荐相关产品        -->                                                      
      <!--    Start Related Products -->
        <h3 class="text-center padding-top-3x mb-30">猜您喜欢</h3>
        <div class="owl-carousel" data-owl-carousel='{ "nav": false, "dots": false, "margin": 30, "responsive": {"0":{"items":1},"576":{"items":2},"768":{"items":3},"991":{"items":4},";1200":{"items":4}} }'>
        
            <c:forEach items="${sessionScope.IR_Goods_Infos}" var="IR_Goods_Info">	
            	<!--  Start Product  -->
	            <div class="grid-item">
	                <div class="product-card">
	                    <div class="rating-stars">
	                        <i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i>
	                    </div>
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
	                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="加入收藏" id="${IR_Goods_Info.goods_id}" onClick="collectGoods(this)">
                                	<i class="icon-heart"></i>
                            	</button>
                            	<button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${IR_Goods_Info.goods_name}" data-toast-message="成功加入购物车!" id="${IR_Goods_Info.goods_id}" onclick="addToCart(this)">
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

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>