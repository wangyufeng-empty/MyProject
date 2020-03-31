<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面用来显示所有商品信息 -->

<html lang="zxx">
<body>
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%
ArrayList WiListInfos = (ArrayList)session.getAttribute("WiListInfos");
user_info user = new user_info();
String userName = (String)session.getAttribute("userName");
%>
<!-- 主体部分 -->
<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>我的收藏</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="index.jsp">主页</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>我的收藏</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    
    <!-- Start My Wishlist -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="row">
            <div class="col-lg-4">
                <aside class="user-info-wrapper">
                    <div class="user-cover account-details">
                        <div class="info-label" data-toggle="tooltip" title="你是一个小可爱，这是你的收藏信息哦"><i class="icon-medal"></i>小可爱</div>
                    </div>
                    <div class="user-info">
                        <div class="user-avatar"><a class="edit-avatar" href="#"></a><img src="assets/images/account/user-ava.jpg" alt="User"></div>
                        <div class="user-data">
                            <h4><%=userName %></h4>
                        </div>
                    </div>
                </aside>
                <nav class="list-group">
                     <a class="list-group-item with-badge" href="account-update.jsp"><i class="icon-head"></i>我的基本信息</a>
                    <a class="list-group-item with-badge" href="usuallyController?url=<%="我的订单"%>"><i class="icon-bag"></i>我的订单</a>
                    <a class="list-group-item active" href="usuallyController?url=<%="我的收藏"%>"><i class="icon-heart"></i>我的收藏</a>
                </nav>
            </div>
            <div class="col-lg-8">
                <div class="padding-top-2x mt-2 hidden-lg-up"></div>
                
                <!-- Wishlist Table-->
                <div class="table-responsive wishlist-table margin-bottom-none">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th class="text-center"><a class="btn btn-sm btn-outline-danger" href="usuallyController?url=<%="清空收藏"%>">清空收藏</a></th>
                        </tr>
                        </thead>    
<%	
				for(Object WiListInfo : WiListInfos)
				{
					Map wiListInfo = (HashMap)WiListInfo;
					String goods_name = (String)wiListInfo.get("goods_name");
					String goods_id = (String)wiListInfo.get("goods_id");
					String goods_category = (String)wiListInfo.get("goods_category");
					int  goods_stock = Integer.parseInt((String)wiListInfo.get("goods_stock"));
					double goods_price = Double.parseDouble((String)wiListInfo.get("goods_price"));
%>                    
                        <tbody>                      
                        <tr>
                            <td>
                                <div class="product-item"><a class="product-thumb" href="#"><img src="assets/images/shop/cart/03.jpg" alt="Product"></a>
                                    <div class="product-info">
                                        <h4 class="product-title"><a href="#"><%=goods_name%></a></h4>
                                        <span><em>分类：</em> <%=goods_category%></span>
                                        <div class="text-lg text-medium text-muted"><%=goods_price%>元</div>
                                        <div>库存:
                                            <div class="d-inline text-success"><%=goods_stock%>件</div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td class="text-center"><a class="remove-from-cart" href="usuallyController?url=<%="移除一个收藏"%>&goods_id=<%=goods_id %>&goods_name=<%=goods_name %>" data-toggle="tooltip" title="移除"><i class="icon-cross"></i></a></td>
                        </tr>
              <%} %>   
                        
                        </tbody>
                    </table>
                </div>
                <hr class="mb-4">
                <div class="custom-control custom-checkbox">
                    <input class="custom-control-input" type="checkbox" id="inform_me" checked>
                    <label class="custom-control-label" for="inform_me">商品降价时提醒我</label>
                </div>
            </div>
        </div>
    </div>
    <!-- End My Wishlist -->
</div>


<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>
