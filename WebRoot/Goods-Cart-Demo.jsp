<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 

<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<script src="js/SecondHandPages_JS/goodsCartJs.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
String user_id = (String)session.getAttribute("userId"); //直接获得ID
ArrayList cartsInfo = (ArrayList)session.getAttribute("cartsInfo"); 
if(cartsInfo.size() != 0)
{
%>

<!-- 主体部分 -->

<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>购物车</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="index.jsp">主页</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>购物车</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    <!-- Start Cart Content -->
  <div class="container padding-top-1x padding-bottom-3x">
  
        <!-- 开始提示窗口 -->
        <div class="alert alert-info alert-dismissible fade show text-center margin-bottom-1x"><span class="alert-close" data-dismiss="alert"></span>
            <p><i class="layui-icon layui-icon-cart"></i>&nbsp;&nbsp;欢迎来到你的购物车，在这里，你可以修改选货数量，提交订单等。一件商品一次性不能选购超过五件，注意库存是否充足！ </p>
        </div>        
        <!-- 结束提示 -->                
        <!-- 开始购物车部分 -->
        <div class="table-responsive shopping-cart">
            <table class="table">
                <thead>
                <tr>
                    <th>名称</th>
                    <th class="text-center">修改数量</th>                 
                    <th class="text-center">单价</th>
                    <th class="text-center">已选数量</th>
                    <th class="text-center">小计</th>
                    <th class="text-center">
                        <button class="btn btn-sm btn-outline-danger" id="clearGoodsCart">清空购物车</button>
                    </th>
                </tr>
                </thead>
<% 
		   double total_price = 0;  //这是所有商品的总价！！！
		   for(Object cartInfo : cartsInfo)   /////////////2019-7-8
			{   				
				Map cart_Info = (HashMap)cartInfo;
				String goods_id = (String)cart_Info.get("goods_id");//从MAP中，获取货物的ID
				String goods_name = (String)cart_Info.get("goods_name");//从MAP中，获取货物的名字
				Double goods_price = Double.parseDouble(cart_Info.get("goods_price").toString());//从MAP中，获取货物的价格
				int selectedQuantity = Integer.parseInt(cart_Info.get("selectedQuantity").toString());//从MAP中，获取货物的已选数量
				double subtotal = Double.parseDouble(cart_Info.get("subtotal").toString());//从MAP中，获取货物的小计		
				String goods_publisher = (String)cart_Info.get("goods_publisher");//从MAP中，获取货物的发布者
				String goods_category = (String)cart_Info.get("goods_category");//从MAP中，获取货物的分类	
				String product_image = (String)cart_Info.get("product_image"); //图片
				total_price+=subtotal;  //累加这个总价	
%>          

                <tbody>
                <tr>
					<!--  第1列 -->
                    <td>
                        <div class="product-item">
<%                        if(product_image!=null){                                                                                                                 %> 
                            <a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>">
                                <img src="<%=product_image %>" alt="Product">
                            </a>
<%                         }else{                                                                                                                      %>
							<a class="product-thumb" href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>">
                                <img src="assets/images/nopic.jpg" alt="Product">
                            </a>
<%                         }                                                                                                                      %>
                            <div class="product-info">
                                <h4 class="product-title"><a href="usuallyController?url=<%="商品详情"%>&goods_id=<%=goods_id%>"><%=goods_name %></a></h4>
                                <span><em>发布者：</em><%=goods_publisher%></span><span><em>分类：</em> <%=goods_category%></span>
                            </div>
                        </div>
                    </td>
                    
                    <!--  第2列 -->
                    <td class="text-center">
                        <div class="count-input">
                        
                          <!--这里实现了选择货物数量的动态传递参数，只要选择框发生改变，就调用函数改变“已选数量” -->
                            <select id="updateSelectedQuantity_option" name="updateSelectedQuantity_option" class="form-control" onChange="submitSelectedQuantity(<%=goods_id%>,this.options[this.options.selectedIndex].value)">
                            	<option value="">点我</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        
                        </div>
                    </td>
                    <!--  第3列 -->
                    <td class="text-center text-lg text-medium"><%=goods_price%></td>
                    
                    <!--  第4列 -->
                    <td class="text-center text-lg text-medium"><%=selectedQuantity%></td>
                    
                    <!--  第5列 -->
                    <td class="text-center text-lg text-medium"><%=subtotal%></td>
                    
                   <!--  第6列 -->
                    <td class="text-center">
                        <a class="remove-from-cart" id="removeItem" onclick="removeItemFromCart(<%=goods_id %>)"  title="移除此项"><i class="icon-cross"></i></a>
                    </td>
                </tr>
                </tbody>
<%
			
			} 
			session.setAttribute("total_price",total_price);  //把总价直接设置到会话，不然传递参数太麻烦。中间隔了好几个页面才用到
%>  
            </table>

        </div>
        <div class="shopping-cart-footer">
            <div class="column">
				<!--提交折扣码的表单 -->
                <form class="coupon-form" method="post" action="#">
                    <input class="form-control form-control-sm" type="text" placeholder="折扣券" required>
                    <button class="btn btn-outline-primary btn-sm" type="submit">添加折扣码</button>
                </form>  
            </div>          
            <div class="column text-lg">总价：<span class="text-medium"><%=total_price %>元</span></div>
        </div>
        
        <div class="shopping-cart-footer">
            <div class="column">
                <a class="btn btn-outline-secondary" href="index.jsp"><i class="icon-arrow-left"></i>&nbsp;继续选购</a>
            </div>
            <div class="column">
                <a class="btn btn-primary" href="Goods-Cart-Demo.jsp">刷新购物车</a><a class="btn btn-success" href="usuallyController?url=<%="核算"%>">核算</a>
            </div>
        </div>
      </div> 
        <!-- End Shopping Cart -->
  </div>
    <!-- End Cart Content -->
<% 
}  //如果cart_info不为空才能进来，不然页面跳转会发生指针悬挂

else
{
%>
		<!-- 开始提示窗口 -->
        <div class="alert alert-info alert-dismissible fade show text-center margin-bottom-1x"><span class="alert-close" data-dismiss="alert"></span>
            <p><i class="layui-icon layui-icon-cart"></i>&nbsp;&nbsp;欢迎来到你的购物车，你还没有选购哦，快去选购吧！ </p>
        </div>        
        <!-- 结束提示 -->
         <div class="column">
            <a class="btn btn-outline-secondary" href="index.jsp"><i class="icon-arrow-left"></i>&nbsp;继续选购</a>
         </div>
<% 
}
%>

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>