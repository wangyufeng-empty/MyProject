<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<!-- 此页面用来显示所有商品信息 -->

<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<!--  主体部分  -->
<script language="javascript">
function check()
{
	if(publishGoods.goods_stock.value > 20)
	{
		layer.msg("发布数量请少于20件！");
		publishGoods.goods_stock.focus();
  		return false;
	}
	var myeprice=publishGoods.goods_price.value;  //
	var myReg=/\d+(\.\d+)?/;  
	if(!myReg.test(myeprice)){
  	layer.msg("请输入正确的价格");
  	publishGoods.goods_price.focus();
  	return false;
  	}
}

</script>


<!--  开始进行发布商品 -->

<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>发布你的二手信息</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="index-1.html">主页</a></li>
                    <li class="separator">&nbsp;</li>          
                    <li>发布商品</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    
    
    <!-- Start Product Content -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="row">
        <!--                                              在这里上传多张图片，并预览                                      -->
           <!-- Start Product Gallery -->
            <div class="col-md-6">
                <div class="product-gallery"><span class="product-badge text-danger">20% Off</span>
                    <div class="gallery-wrapper">
                        <div class="gallery-item active"><a href="assets/images/shop/single/01.jpg" data-hash="one" data-size="1000x667"></a></div>
                        <div class="gallery-item"><a href="assets/images/shop/single/02.jpg" data-hash="two" data-size="1000x667"></a></div>
                        <div class="gallery-item"><a href="assets/images/shop/single/03.jpg" data-hash="three" data-size="1000x667"></a></div>
                        <div class="gallery-item"><a href="assets/images/shop/single/04.jpg" data-hash="four" data-size="1000x667"></a></div>
                        <div class="gallery-item"><a href="assets/images/shop/single/05.jpg" data-hash="five" data-size="1000x667"></a></div>
                    </div>
                    <div class="product-carousel owl-carousel">
                        <div data-hash="one"><img src="assets/images/shop/single/01.jpg" alt="Product"></div>
                        <div data-hash="two"><img src="assets/images/shop/single/02.jpg" alt="Product"></div>
                        <div data-hash="three"><img src="assets/images/shop/single/03.jpg" alt="Product"></div>
                        <div data-hash="four"><img src="assets/images/shop/single/04.jpg" alt="Product"></div>
                        <div data-hash="five"><img src="assets/images/shop/single/05.jpg" alt="Product"></div>
                    </div>
                    <ul class="product-thumbnails">
                        <li class="active"><a href="#one"><img src="assets/images/shop/single/th01.jpg" alt="Product"></a></li>
                        <li><a href="#two"><img src="assets/images/shop/single/th02.jpg" alt="Product"></a></li>
                        <li><a href="#three"><img src="assets/images/shop/single/th03.jpg" alt="Product"></a></li>
                        <li><a href="#four"><img src="assets/images/shop/single/th04.jpg" alt="Product"></a></li>
                        <li><a href="#five"><img src="assets/images/shop/single/th05.jpg" alt="Product"></a></li>
                    </ul>
                </div>
            </div>
            <!-- End Product Gallery -->
            <!--                                                   结束上传多张图片                                            -->
            
            <!-- Start Product Info -->
            <div class="col-md-6 single-shop">
                <div class="hidden-md-up"></div>
                
               
                <!--开始填写表单，待执行servlet验证       -->
                <form class="row" name="publishGoods" action="usuallyController" method="post" onsubmit="return check()">
                	<input type="hidden" name="url" value="发布商品">
                	
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">商品名</label>
                            <input class="form-control" type="text" name="goods_name" required>   
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">价格</label>
                            <input class="form-control" type="text" name="goods_price" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-ln">发布的数量</label>
                            <input class="form-control" type="text" name="goods_stock" required oninput="value=value.replace(/[^\d]/g , '')">
                        </div>
                    </div>
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="quantity">请选择分类</label>
                            <select class="form-control" id="quantity" name="goods_category">
                                <option value="计算机书籍">计算机书籍</option>
                                <option value="耳机">耳机</option>
                                <option value="电脑">电脑</option>
                                <option value="相机">相机</option>
                                <option value="单片机">单片机</option>
                                <option value="开发软件/工具">开发软件/工具</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <label for="account-phone">添加商品简介</label>
                            <textarea class="form-control" name="goods_describe" required></textarea> 
                        </div>
                    </div>
                   
                    
                    <div class="col-6">
                        <hr class="mt-2 mb-3">
                        <div class="d-flex flex-wrap justify-content-between align-items-center">                        
                            <button class="btn btn-primary " type="submit">发布</button>
                        </div>
                    </div>
                </form>
            </div>
           
            <!-- End Product Info -->
        </div>
        
       
    </div>
    <!-- End Product Content -->
</div>

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>



