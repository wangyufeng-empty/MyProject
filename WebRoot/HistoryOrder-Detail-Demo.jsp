<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>

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
<%
ArrayList HistoryOrder_infos = (ArrayList)session.getAttribute("HistoryOrder_infos");
String order_id = (String)session.getAttribute("historyOrder_id");
%>
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
            <div class="p-4 text-center text-white text-lg bg-dark rounded-top"><span class="text-uppercase">订单号 - </span><span class="text-medium"><%=order_id %></span></div>
            <div class="d-flex flex-wrap flex-sm-nowrap justify-content-between py-3 px-2 bg-secondary">
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>邮寄方式:</span>燕大快递</div>
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>状态:</span> 出货检查</div>
                <div class="w-100 text-center py-1 px-2"><span class='text-medium'>预计到达:</span> 2020年6月7日</div>
            </div>
            <div class="card-body">
                <div class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="pe-7s-cart"></i></div>
                        </div>
                        <h4 class="step-title">确认订单</h4>
                    </div>
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="pe-7s-config"></i></div>
                        </div>
                        <h4 class="step-title">提交订单</h4>
                    </div>
                    <div class="step completed">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="pe-7s-medal"></i></div>
                        </div>
                        <h4 class="step-title">出货检查</h4>
                    </div>
                    <div class="step">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="pe-7s-car"></i></div>
                        </div>
                        <h4 class="step-title">发货</h4>
                    </div>
                    <div class="step">
                        <div class="step-icon-wrap">
                            <div class="step-icon"><i class="pe-7s-home"></i></div>
                        </div>
                        <h4 class="step-title">收货</h4>
                    </div>
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
                    <th>名称</th>          
                    <th class="text-center">小计</th>
                    <th class="text-center">
                        <a class="btn btn-sm btn-outline-danger" onclick="deleteOrder('<%=order_id %>')">删除订单</a>
                    </th>
                </tr>
                </thead>
<% 
        for(Object HistoryOrder_info:HistoryOrder_infos)
        {
        	Map historyOrder_info = (HashMap)HistoryOrder_info;
        	String goods_name = (String)historyOrder_info.get("goods_name");
        	int selectedQuantity = Integer.parseInt(historyOrder_info.get("selectedQuantity").toString());
       		String goods_id = (String)historyOrder_info.get("goods_id");
       		double subtotal = Double.parseDouble(historyOrder_info.get("subtotal").toString());
       		Goods goods = new Goods();
       		goods.setGoodsId(goods_id);
       		Map goodsInfo = goods.getGoodsInfo();//返回一条货物信息
       		String goods_publisher = (String)goodsInfo.get("goods_publisher");
       		String goods_category = (String)goodsInfo.get("goods_category");
       		String goods_describe = (String)goodsInfo.get("goods_describe");
%>
             <tbody>
                <tr>
					<!--  第1列 -->
                    <td>
                        <div class="product-item">
                            <a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>">
                                <img src="assets/images/shop/cart/02.jpg" alt="Product">
                            </a>
                            <div class="product-info">
                                <h4 class="product-title">
                                     <a href="#"><%=goods_name %><small>x <%=selectedQuantity%></small></a>
                                </h4>
                                <span><em>发布者：</em><%=goods_publisher%></span><span><em>分类：</em> <%=goods_category%></span>
                                <br>
                                <p><%=goods_describe %></p>
                            </div>
                        </div>
                    </td>
                    
                   
                    <!--  第2列 -->
                    <td class="text-center text-lg text-medium"><%=subtotal%></td>
                </tr>
                </tbody>
              
    <% } %>
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
                    <div class="column text-right hidden-xs-down"><a class="btn btn-outline-secondary btn-sm" href="#">下一页&nbsp;<i class="icon-arrow-right"></i></a></div>
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