<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面用来显示所有商品信息 -->


<html lang="zxx" >

<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<script src="js/SecondHandPages_JS/indexJs.js"></script>
<!-- 主体部分 -->

<div class="hidden_div" id="coverDiv">
</div>
<div>
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>
<!-- Start Hero Products -->
    <section class="bg-secondary padding-top-3x padding-bottom-3x">
        <div class="container">
            <h3 class="text-center mb-30">燕大·交易网·全部商品</h3>
            <ul class="nav nav-pills justify-content-center">
                <li class="nav-item"><a class="nav-link active" href="#" data-filter="*">全部</a></li>
            </ul>
            <div class="isotope-grid filter-grid cols-4 mt-30">
                <div class="gutter-sizer"></div>
                <div class="grid-sizer"></div>
                
<%			ArrayList goodsInfo = (ArrayList)session.getAttribute("goodsInfo"); //查询结果
			for(Object goods_info : goodsInfo)
			{
				Map goodInfo = (HashMap)goods_info;
				String goods_name = (String)goodInfo.get("goods_name");//从MAP中，获取货物的名字
				Double goods_price = Double.parseDouble(goodInfo.get("goods_price").toString());//从MAP中，获取货物的价格
				int  goods_stock = Integer.parseInt((String)goodInfo.get("goods_stock"));
				String goods_id = (String)goodInfo.get("goods_id");//从MAP中，获取货物的ID
%>        
                <!-- Start Product -->
                <div class="grid-item classic">
                    <div class="product-card">
                        <a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>">
                            <img src="assets/images/shop/products/w12.jpg" alt="Product">
                        </a>
                        <h3 class="product-title"><a href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>"><%=goods_name %></a></h3>
                        <h4 class="product-price"><%=goods_price %>元</h4>
                        <h6 class="product-price">库存：<%=goods_stock %>件</h6>
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
                <!-- End Product -->
<%
			} 
%>     
            </div>
        </div>
    </section>
    <!-- End Hero Products -->

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>
