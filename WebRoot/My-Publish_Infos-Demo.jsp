<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<html lang="zxx">
<body>
<!--固定页头部分 -->
<%@ include file="header.jsp" %>

<script language="javascript">
function tips(goods_id){
	layer.confirm('你确定要下架这件商品吗？',
	function(){
	window.location.href = "usuallyController?url=<%="下架商品" %>&goods_id="+goods_id;
})
}
</script>

<!-- 主体部分 -->
<%
ArrayList myPublish_infos = (ArrayList)session.getAttribute("myPublish_infos");
%>

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
                    <th class="text-center">数量</th>
                    
                </tr>
                </thead>
<%
			for(Object myPublish_info : myPublish_infos) 
			{
				Map myPublish = (HashMap)myPublish_info;
				String goods_id = (String)myPublish.get("goods_id");  //货物ID
				String goods_name = (String)myPublish.get("goods_name"); //货物名
				String goods_category = (String)myPublish.get("goods_category"); //
				int goods_stock = Integer.parseInt((String)myPublish.get("goods_stock"));  //发布的剩余数量
				double goods_price = Double.parseDouble((String)myPublish.get("goods_price"));  //价格
			
%>
 			<tbody>
                <tr>
					<!--  第1列 -->
                    <td>
                        <div class="product-item">
                            <a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>">
                                <img src="assets/images/shop/cart/01.jpg" alt="Product">
                            </a>
                            <div class="product-info">
                                <h4 class="product-title">
                                     <a href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>"><%=goods_name %></a>
                                </h4>
                                <span><em>价格：</em><%=goods_price%></span><span><em>分类：</em> <%=goods_category%></span>
                               
                            </div>
                        </div>
                    </td>
                    
                   
                    <!--  第2列 -->
                    <td class="text-center text-lg text-medium"><%=goods_stock%></td>
                    <!--  第3列 -->
                    <td class="text-center">
                        <a class="btn btn-sm btn-outline-danger" onClick="tips(<%=goods_id %>)">下架商品</a>
                    </td>
                </tr>
                </tbody>
              
    		<% } %>
     </table>
     </div>
     </div>
     </div>
     



<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>