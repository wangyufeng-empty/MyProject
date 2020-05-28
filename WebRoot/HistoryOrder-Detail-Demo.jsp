<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script language="javascript">
function deleteOrder(order_id){
	/*为了防止在js参数传递中被自动转化为科学计数法的数字，应该在
	   html页面内给参数加上引号*/
	console.log("deleteOrder:"+order_id);
	layer.confirm('你确定要删除这个订单吗？',
	function(){
	window.location.href = "usuallyController?url=<%="删除订单" %>&order_id="+order_id;
})
}
</script>
<!-- 主体部分 -->
<div class="hidden_div" id="coverDiv">
</div>
<div >
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>

<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>订单详情</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="Goods-Cart-Demo.jsp">我的订单</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>订单详情</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    <!-- Start Order Trucking -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="card mb-3">
            <div class="p-4 text-center text-white text-lg bg-dark rounded-top"><span class="text-uppercase">订单号 - </span>
            <span class="text-medium" id="orderId">${sessionScope.HistoryOrder_infos[0].order_id }</span></div>
            <div class="d-flex flex-wrap flex-sm-nowrap justify-content-between py-3 px-2 bg-secondary">
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>邮寄方式:</span>燕大快递</div>
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>状态:</span> 出货检查</div>
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>预计到达:</span> 2020年6月13日</div>
            </div>
            <div class="card-body">
                <div class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">确认订单</h4>
                    </div>
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">提交订单</h4>
                    </div>
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">出货检查</h4>
                    </div>
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-location"></i></div>
                        </div>
                        <h4 class="step-title">发货</h4>
                    </div>
                    
                    <c:if test="${sessionScope.thisOrder_state eq '待收货'}">  
        				<div class="step">
                        	<div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-right"></i></div>
                        </div>
                        <h4 class="step-title">收货</h4>
                    	</div>
    				</c:if> 
    				
    				<c:if test="${sessionScope.thisOrder_state eq '已完成'}">  
        				<div class="step completed">
                        	<div class="step-icon-wrap">
                            <div class="step-icon"><i class="layui-icon layui-icon-location"></i></div>
                        </div>
                        <h4 class="step-title">收货</h4>
                    	</div>
    				</c:if> 
                    
                    
                </div>
            </div>
          <!-- Start Checkout Review -->
            <div class="col-lg-12">
                
                <h3>此订单包含的商品信息如下：</h3>
                <hr class="padding-bottom-1x">
                
                <div class="table-responsive shopping-cart">
            <table class="table">
                <thead>
                <tr>
                    <th>商品详情</th>          
                    <th class="text-center">小计</th>
                    <th class="text-center">
                       
                    </th>
                </tr>
                </thead>

			<c:forEach items="${sessionScope.HistoryOrder_infos}" var="HistoryOrder" varStatus="status">	
			
				 <tbody>
                	<tr>
						<!--  第1列 -->
	                    <td>
	                        <div class="product-item">
	                            <c:if test="${not empty HistoryOrder.product_image}"> 
        							<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${HistoryOrder.goods_id}">
                        				<img src="${HistoryOrder.product_image}" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${HistoryOrder.goods_id}">
                    				</a>
    							</c:if>
    					
                     			<c:if test="${empty HistoryOrder.product_image}">  
        							<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=${HistoryOrder.goods_id}">
                        				<img src="assets/images/nopic.jpg" alt="Product" href="usuallyController?url=<%="商品详情"%>&goods_id=${HistoryOrder.goods_id}">
                    				</a>
    							</c:if> 
	                            <div class="product-info">
	                                <h4 class="product-title">
	                                     <a href="#">${HistoryOrder.goods_name}<small>x ${HistoryOrder.selectedQuantity}</small></a>
	                                </h4>
	                                <span><em>发布者：</em>${HistoryOrder.goods_publisher}</span> <span><em>分类：</em> ${HistoryOrder.goods_category}</span>
	                                <br>
	                                <p>${HistoryOrder.goods_describe}</p>
	                            </div>
	                        </div>
	                    </td>                    
	                    <!--  第2列 -->
	                    <td class="text-center text-lg text-medium">￥${HistoryOrder.subtotal}</td>
                	</tr>
                </tbody>
                
			</c:forEach>
            
     </table>
     </div>
     
                <!-- 开始分页 -->
                <nav class="pagination">
                    <div class="column">
                        <ul class="pages">
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li>...</li>
                            <li><a href="#">10</a></li>
                            <li><a href="#">20</a></li>
                            <li><a href="#">30</a></li>
                        </ul>
                    </div>
                    
                    <div class="column text-right hidden-xs-down"><a class="btn btn-danger" style="color: white;" onclick="deleteOrder('${sessionScope.HistoryOrder_infos[0].order_id }')">删除订单</a></div>
                    <c:if test="${sessionScope.thisOrder_state eq '待收货'}">  
        				 <div class="column text-right hidden-xs-down"><input type="button" class="btn btn-success" value="确认收货" onclick="ConfirmReceipt('${sessionScope.HistoryOrder_infos[0].order_id }')"/></div>
    				</c:if> 
                   
                   
                </nav>
                <!-- 结束分页 -->
            </div>
            <!-- End Search Content -->
            
            
          
        </div>
    </div>
    <!-- End Order Trucking -->
</div>

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>