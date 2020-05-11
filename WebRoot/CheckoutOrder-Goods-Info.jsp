<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面完成点击核算后跳转的用户信息确认 -->


<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<!-- 主体部分 -->
<div class="hidden_div" id="coverDiv">
</div>
<div>
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>
<%
double total_price = Double.parseDouble(session.getAttribute("total_price").toString());  //获得总价  
Map userInfo = (HashMap)session.getAttribute("userInfo");
ArrayList cartsInfo = (ArrayList)session.getAttribute("cartsInfo"); //获取到购物车的信息
String consignee = (String)session.getAttribute("consignee");//取出收货人
String userTel = (String)userInfo.get("user_tel");//取出电话号码
String userAddress = (String)userInfo.get("user_address");//取出地址
%>
<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>确认订单</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="Goods-Cart-Demo.jsp">购物车</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>确认订单</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    
    
    <!-- Start Checkout Content -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="row">
            <!-- Start Checkout Review -->
            <div class="col-lg-9">
                <div class="checkout-steps"><a class="active" href="checkout-review.html">2. 确认商品</a>
                	<a><span class="angle"></span></a>
                    <a><span class="angle"></span></a>                   
                    <a class="completed" href="CheckoutOrder-Goods-Info.jsp"><span class="step-indicator icon-circle-check"></span><span class="angle"></span>1. 收货信息</a>
                </div>
                <h4>检查你的最终订单</h4>
                <hr class="padding-bottom-1x">
                <div class="table-responsive shopping-cart">
                
					<!-- 表格 -->
                    <table class="table">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th class="text-center">小计</th>
                            <th class="text-center">编辑</th>
                        </tr>
                        </thead>
<% 
            for(Object cartInfo : cartsInfo)   /////////////2019-7-10
			{   				
				Map cart_Info = (HashMap)cartInfo;
				String goods_name = (String)cart_Info.get("goods_name");//从MAP中，获取货物的名字
				int selectedQuantity = Integer.parseInt(cart_Info.get("selectedQuantity").toString());//从MAP中，获取货物的已选数量
				double subtotal = Double.parseDouble(cart_Info.get("subtotal").toString());//从MAP中，获取货物的小计		
				String goods_publisher = (String)cart_Info.get("goods_publisher");//从MAP中，获取货物的发布者
				String goods_category = (String)cart_Info.get("goods_category");//从MAP中，获取货物的分类	     
%>         
                        <tbody>
                        <tr>
                            <td>
                                <div class="product-item">
                                    <a class="product-thumb" href="#"><img src="assets/images/shop/cart/01.jpg" alt="Product"></a>
                                    <div class="product-info">
                                        <h4 class="product-title">
                                            <a href="#"><%=goods_name %><small>x <%=selectedQuantity%></small></a>
                                        </h4>
                                        <span><em>发布者：</em><%=goods_publisher%></span><span><em>分类：</em> <%=goods_category%></span>
                                    </div>
                                </div>
                            </td>
                            <td class="text-center text-lg text-medium"><%=subtotal %>元</td>
                            <td class="text-center"><a class="btn btn-outline-primary btn-sm" href="Goods-Cart-Demo.jsp">编辑</a></td>
                        </tr>
                        </tbody>
       <%} %>
                    </table>
                </div>
                
                <div class="shopping-cart-footer">
                    <div class="column"></div>
                    <div class="column text-lg">总计: <span class="text-medium"><%=total_price %>元</span></div>
                </div>
                <div class="row padding-top-1x mt-3">
                    <div class="col-sm-6">
                        <h5>送货至：</h5>
                        <ul class="list-unstyled">
                            <li><span class="text-muted">客户:</span><%=consignee %></li>
                            <li><span class="text-muted">地址:</span><%=userAddress %></li>
                            <li><span class="text-muted">联系电话:</span><%=userTel %></li>
                        </ul>
                    </div>
                </div>
                <div class="checkout-footer margin-top-1x">
                    <div class="column hidden-xs-down"><a class="btn btn-outline-secondary" href="CheckoutOrder-User-Info.jsp"><i class="icon-arrow-left"></i>&nbsp;返回</a></div>
                    <div class="column"><a class="btn btn-primary" href="usuallyController?url=<%="提交订单" %>">提交订单</a></div>
                </div>
            </div>
            <!-- End Checkout Review -->
            <!-- Start Sidebar -->
            <div class="col-lg-3 order-sum">
                <aside class="sidebar">
                    <div class="hidden-lg-up"></div>
                    <!-- Start Order Summary Widget -->
                    <section class="widget widget-order-summary">
                         <h3 class="widget-title">订单总览</h3>
                        <table class="table">
                            <tr>
                                <td>购物车总价:</td>
                                <td class="text-medium"><%=total_price %>元</td>
                            </tr>
                            <tr>
                                <td>折扣:</td>
                                <td class="text-medium">0.0%</td>
                            </tr>
                         
                            <tr>
                                <td></td>
                                <td class="text-lg text-medium"><%=total_price %>元</td>
                            </tr>
                        </table>
                    </section>
                    <!-- End Order Summary Widget -->
                    
                    <!-- Start Promo Banner -->
                    <section class="promo-box hidden-md-down site-promo"><span class="overlay-dark site-promo-span"></span>
                        <div class="promo-box-content text-center padding-top-2x padding-bottom-2x">
                            <h4 class="text-light text-thin text-shadow">最新到货</h4>
                            <h3 class="text-bold text-light text-shadow">2080年哆啦A梦手机</h3><a class="btn btn-outline-white btn-sm" href="#">想想就好</a>
                        </div>
                    </section>
                    <!-- End Promo Banner -->
                    
                </aside>
            </div>
        </div>
    </div>
    <!-- End Product Content -->
</div>

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>