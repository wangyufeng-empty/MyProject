<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<div class="hidden_div" id="coverDiv">
</div>
<div >
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>
<script src="js/SecondHandPages_JS/indexJs.js"></script>
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
	                         <button class="btn btn-outline-secondary btn-sm " data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="<%=goods_name %>" data-toast-message="已选中!" title="加入收藏" id="<%=goods_id%>" onClick="collectGoods(this)" >
                                	<i class="icon-heart" id="icon_heart"></i>
                            </button>
                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="<%=goods_name %>" data-toast-message="已选中!" id="<%=goods_id%>" onclick="addToCart(this)">
                            			<i class="icon-bag"></i>加入购物车
                            </button>
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