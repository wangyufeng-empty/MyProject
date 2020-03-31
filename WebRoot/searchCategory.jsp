<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx">
<body>
<!--固定页头部分 -->
<%@ include file="header.jsp" %>


<!-- 获取参数开始 -->
<%
String sort = (String)session.getAttribute("sort");  //得到商品种类
ArrayList goodsInfo = (ArrayList)session.getAttribute("goodsInfo"); //查询结果
%>
	<section class="container padding-top-3x">
	        <h3 class="text-center mb-30"><%= sort%></h3>
	        <div class="row">
	        
<%			for(Object goods_info : goodsInfo)
			{
				Map goodInfo = (HashMap)goods_info;
				String goods_name = (String)goodInfo.get("goods_name");//从MAP中，获取货物的名字
				Double goods_price = Double.parseDouble(goodInfo.get("goods_price").toString());//从MAP中，获取货物的价格
				int  goods_stock = Integer.parseInt((String)goodInfo.get("goods_stock"));
				String goods_id = (String)goodInfo.get("goods_id");//从MAP中，获取货物的ID
%>        
	            <div class="col-md-4 col-sm-6 home-cat">
	                <div class="card">
	                    <a class="card-img-tiles" href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>">
	                        <div class="inner">
	                            <div class="main-img">
	                                <img src="assets/images/shop/categories/01.jpg" alt="Category">
	                            </div>
	                        </div>
	                    </a>
	                    <div class="card-body text-center">
	                        <h4 class="card-title"><%=goods_name %></h4>
	                        <p class="text-muted">最低<%= goods_price%>元</p>
	                        <p class="text-muted">库存<%= goods_stock%>件</p>
	                         <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="加入收藏" onclick="location.href='usuallyController?url=<%="加入收藏"%>&goods_id=<%=goods_id%>&backUrl=<%="searchCategory.jsp"%>' ">
	                          <i class="icon-heart"></i>
	                         </button>
	                        <a class="btn btn-outline-primary btn-sm" href="usuallyController?url=<%="加入购物车"%>&goods_id=<%=goods_id%>&backUrl=<%="searchCategory.jsp"%>">加入购物车</a>
	                    </div>
	                </div>
	            </div>
<%
			} 
%>  
	 </div>
	    </section>
	    <!-- End Top Categories -->

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>