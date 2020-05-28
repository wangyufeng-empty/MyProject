<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="js/SecondHandPages_JS/myPublishDemoJs.js"></script>

<!-- 主体部分 -->

<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>我的发布</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="Goods-Cart-Demo.jsp">主页</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>我的发布</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    
    <!-- Start Checkout Review -->
            <div class="col-lg-12">
                
                <h3>你的发布信息如下：</h3>
                <hr class="padding-bottom-1x">
                <div class="table-responsive shopping-cart">
                
            <table class="table">
                <thead>
                <tr>
                    <th>名称</th>          
                    <th class="text-center">库存</th>
                    <th class="text-center">修改</th>
                    <th class="text-center">删除</th>
                </tr>
                </thead>
                
		<c:forEach items="${sessionScope.myPublish_infos}" var="myPublish" varStatus="status">
			<tbody>
                <tr>
					<!--  第1列 -->
                    <td>
                        <div class="product-item">
                            	<c:if test="${not empty myPublish.product_image}"> 
        							<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${myPublish.goods_id}">
                        				<img src="${myPublish.product_image}" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${myPublish.goods_id}">
                    				</a>
    							</c:if>
    					
                     			<c:if test="${empty myPublish.product_image}">  
        							<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${myPublish.goods_id}">
                        				<img src="assets/images/nopic.jpg" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${myPublish.goods_id}">
                    				</a>
    							</c:if> 
                            <div class="product-info">
                                <h4 class="product-title">
                                     <a href="usuallyController?url=<%="商品详情"%>&goods_id=${myPublish.goods_id}">${myPublish.goods_name}</a>
                                </h4>
                                <span><em>价格：</em>${myPublish.goods_price}</span><span><em>分类：</em>${myPublish.goods_category}</span>
                               
                            </div>
                        </div>
                    </td>
                    
                   
                    <!--  第2列 -->
                    <td class="text-center text-lg text-medium">${myPublish.goods_stock}</td>
                    <!--  第3列 -->
                    <td class="text-center">
                        <a class="btn btn-sm btn-outline-danger" id="${myPublish.goods_id}" onClick="updateStock('${myPublish.goods_id}')">修改库存</a>
                    </td>
                     <!--  第4列 -->
                    <td class="text-center">
                        <a class="btn btn-sm btn-outline-danger" id="${myPublish.goods_id}" onClick="deleteGoods('${myPublish.goods_id}')">下架商品</a>
                    </td>
                </tr>
            </tbody>
		</c:forEach>
 		    
     </table>
     </div>
     </div>
     </div>
     



<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>