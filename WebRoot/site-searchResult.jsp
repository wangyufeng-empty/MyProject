<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx">
<body>
<!--固定页头部分 -->
<%@ include file="header.jsp" %>

<%
ArrayList goodsInfo = (ArrayList)session.getAttribute("goodsInfo"); 
%>
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
	                    <a class="product-thumb" href="#">
	                        <img src="assets/images/shop/products/01.jpg" alt="Product">
	                    </a>
	                    <h3 class="product-title"><a href="#"><%=goods_name %></a></h3>
	                    <h4 class="product-price"><%= goods_price %>元</h4>
	                     <h6 class="product-price">库存：<%=goods_stock %>件</h6>
	                    <div class="product-buttons">
	                        <div class="product-buttons">
	                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="加入收藏" onclick="location.href='usuallyController?url=<%="加入收藏"%>&goods_id=<%=goods_id%>&backUrl=<%="site-searchResult.jsp"%>' ">
	                            <i class="icon-heart"></i>
	                            </button>
	                            <button class="btn btn-outline-primary btn-sm"  onclick="location.href='usuallyController?url=<%="加入购物车"%>&goods_id=<%=goods_id%>&backUrl=<%="site-searchResult.jsp"%>' ">加入购物车</button>
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

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>