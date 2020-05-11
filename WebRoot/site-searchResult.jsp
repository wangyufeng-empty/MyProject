<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="js/SecondHandPages_JS/indexJs.js"></script>
<%List IR_Goods_Infos = (List)session.getAttribute("IR_Goods_Infos"); %>
<%
ArrayList goodsInfo = (ArrayList)session.getAttribute("goodsInfo"); 
%>
<div class="hidden_div" id="coverDiv">
</div>
<div >
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>
 <section class="container padding-top-3x padding-bottom-3x">
        <h3 class="text-center mb-30">搜索结果</h3>
        <div class="owl-carousel"
             data-owl-carousel='{"nav": false, "dots": false, "margin": 30, "responsive": {"0":{"items":1},"576":{"items":2},"768":{"items":3},"991":{"items":4},"1200":{"items":4}} }'>
<%			for(Object goods_info : goodsInfo)
			{
				Map goodInfo = (HashMap)goods_info;
				String goods_name = (String)goodInfo.get("goods_name");//从MAP中，获取货物的名字
				Double goods_price = Double.parseDouble(goodInfo.get("goods_price").toString());//从MAP中，获取货物的价格
				int  goods_stock = Integer.parseInt((String)goodInfo.get("goods_stock"));
				String goods_id = (String)goodInfo.get("goods_id");//从MAP中，获取货物的ID
%>            
	            <!-- Start Product  -->
	            <div class="grid-item">
	                <div class="product-card">
	                    <a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>">
	                        <img src="assets/images/shop/products/01.jpg" alt="Product">
	                    </a>
	                    <h3 class="product-title"><a href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>"><%=goods_name %></a></h3>
	                    <h4 class="product-price"><%= goods_price %>元</h4>
	                     <h6 class="product-price">库存：<%=goods_stock %>件</h6>
	                    <div class="product-buttons">
	                        <div class="product-buttons">
		                        <button class="btn btn-outline-secondary btn-sm " data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="<%=goods_name %>" data-toast-message="已选中!" title="加入收藏" id="<%=goods_id%>" onClick="collectGoods(this)" >
	                                <i class="icon-heart" id="icon_heart"></i>
	                            </button>
	                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="<%=goods_name %>" data-toast-message="已选中!" id="<%=goods_id%>" onclick="addToCart(this)">
	                            	<i class="icon-bag"></i>加入购物车
	                            </button>
                            </div>
	                    </div>
	                </div>
	            </div>
	            <!-- End Product -->
<%
			} 
%>  
            
        </div>
    </section>
    <!-- 结束商品 -->
    
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
	                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="加入收藏" onclick="location.href='usuallyController?url=<%="加入收藏"%>&goods_id=${IR_Goods_Info.goods_id}&backUrl=<%="site-searchResult.jsp"%>' ">
                                	<i class="icon-heart"></i>
                            	</button>
                            	<button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="${IR_Goods_Info.goods_name}" data-toast-message="成功加入购物车!" onclick="location.href='usuallyController?url=<%="加入购物车"%>&goods_id=${IR_Goods_Info.goods_id}&backUrl=<%="site-searchResult.jsp"%>' ">
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