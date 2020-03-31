<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面完成点击核算后跳转的用户信息确认 -->


<html lang="zxx">
<body>
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<%
double total_price = Double.parseDouble(session.getAttribute("total_price").toString());  //获得总价    
Map userInfo = (Map)session.getAttribute("userInfo_order");//获取到用户的基本信息
String consignee = (String)session.getAttribute("consignee");//取出收货人
if(consignee==null)//第一次进入界面，默认收货人是用户自己
{consignee = (String)userInfo.get("user_name");session.setAttribute("consignee", consignee);}
String userTel = (String)userInfo.get("user_tel");//取出电话号码
String userEmail = (String)userInfo.get("user_email");//取出邮箱
String userAddress = (String)userInfo.get("user_address");//取出地址
%>
<!-- 主体部分 -->
<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>检查订单</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="Goods-Cart-Demo.jsp">购物车</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>检查订单</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    
    <!-- 开始确认的主体部分 -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="row">
        
            <!-- Start Checkout Address -->
            <div class="col-lg-9">
                <div class="checkout-steps">
                    <a href="#">2. 确认订单</a>
                    <a><span class="angle"></span></a>
                    <a><span class="angle"></span></a>
                    <a class="active" href="#"><span class="angle"></span>1. 收货信息</a>
                </div>
                <h4>确认/完善收货信息</h4>
                
                <hr class="padding-bottom-1x">
                
           
                <!--开始填写表单，修改/完善个人基本信息，收货信息           ，待执行servlet验证       -->
                <div align="center">
                <form class="col" name="updateProfile" action="usuallyController" method="post" onsubmit="return check()">
                	<input type="hidden" name="url" value="updateOrderInfo">
                	
                	
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">收货人</label>
                            <input class="form-control" type="text" name="consignee" value="<%=consignee %>" required>
                        </div>
                    </div>
                    
					<!--  从这里开始需要更新 -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">电话号码</label>
                            <input class="form-control" type="text" name="userTel" value="<%=userTel %>" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-ln">邮箱</label>
                            <input class="form-control" type="email" name="userEmail" value="<%=userEmail %>" required>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-phone">收货地址</label>
                            <input class="form-control" type="text" name="userAddress" value="<%=userAddress %>" required>
                        </div>
                    </div>
                 
                    <div class="col-12">
                        <hr class="mt-2 mb-3">
                        <div class="d-flex flex-wrap justify-content-between align-items-center">
                            
                           <button class="btn btn-primary margin-right-none" type="submit">保存信息/生成订单</button>
                        </div>
                    </div>
                </form>
                </div>
                
                <br>
                <h4>更多设置</h4>
                <hr class="padding-bottom-1x">
                <div class="form-group">
                    <div class="custom-control custom-checkbox">
                        <input class="custom-control-input" type="checkbox" id="same_address" checked>
                        <label class="custom-control-label" for="same_address">设为常用地址</label>
                    </div>    
                </div>
         
                <div class="checkout-footer">
                    <div class="column"><a class="btn btn-outline-secondary" href="Goods-Cart-Demo.jsp"><i class="icon-arrow-left"></i><span class="hidden-xs-down">&nbsp;返回购物车</span></a></div>
                    <div class="column"><a class="btn btn-primary" href="usuallyController?url=<%="订单下一步" %>"><span class="hidden-xs-down">下一步&nbsp;</span><i class="icon-arrow-right"></i></a></div>
                </div>
            </div>
            <!-- End Checkout Address -->
            
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