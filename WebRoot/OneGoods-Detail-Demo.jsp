<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zxx">
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>

<%
Map OneGoodsInfo = (Map)session.getAttribute("goodsInfo"); //得到服务器端session的请求货物信息
List<String> goodsPictures_list = (List<String>)session.getAttribute("goodsPictures_list");
String[] sequence={"one","two","three","four","five","six","seven","eight","nine","ten","eleven"};
request.setAttribute("sequence",sequence);  
/*接下来分别获取需要的属性*/
String goods_id = (String)OneGoodsInfo.get("goods_id");//商品id
String goods_name = (String)OneGoodsInfo.get("goods_name");//商品名字
Double goods_price = Double.parseDouble(OneGoodsInfo.get("goods_price").toString());//价格
String goods_describe = (String)OneGoodsInfo.get("goods_describe"); //商品描述
String goods_issuDate = (String)OneGoodsInfo.get("goods_issuDate");  //发布时间
String goods_publisher = (String)OneGoodsInfo.get("goods_publisher");  //发布者
String goods_category = (String)OneGoodsInfo.get("goods_category");  //发布分类
int goods_stock = Integer.parseInt((String)OneGoodsInfo.get("goods_stock"));  //库存
%>

<div>
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>商品详情</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="index.jsp">主页</a></li>
                    <li class="separator">&nbsp;</li>
                    <li><a href="OneGoods-Detail-Demo.jsp">商品详情</a></li>
                    <li class="separator">&nbsp;</li>
                    <li>商品详情</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    <!-- Start Product Content -->
    <div class="container padding-top-1x padding-bottom-3x">
        <div class="row">
            <!-- Start Product Gallery -->
            <div class="col-md-6">
                <div class="product-gallery"><span class="product-badge text-danger">左右滑动查看图片</span>
                
                    <div class="product-carousel owl-carousel">                
                       <c:forEach items="${goodsPictures_list}" var="goodsPicture">	               					
							<div data-hash="${goodsPicture.product_image}"><img src="${goodsPicture.product_image}" alt="Product"></div>					
					   </c:forEach>
                    </div>
                    
                    <ul class="product-thumbnails">
                    	<c:forEach items="${goodsPictures_list}" var="goodsPicture">	
                			
							<li><a href="#${goodsPicture.product_image}"><img src="${goodsPicture.product_image}" alt="Product"></a></li>
					
					    </c:forEach>
					    
<!--                         <li class="active"><a href="#one"><img src="assets/images/shop/single/th01.jpg" alt="Product"></a></li> -->
<!--                         <li><a href="#two"><img src="assets/images/shop/single/th02.jpg" alt="Product"></a></li> -->
<!--                         <li><a href="#three"><img src="assets/images/shop/single/th03.jpg" alt="Product"></a></li> -->
<!--                         <li><a href="#four"><img src="assets/images/shop/single/th04.jpg" alt="Product"></a></li> -->
<!--                         <li><a href="#five"><img src="assets/images/shop/single/th05.jpg" alt="Product"></a></li> -->
                    </ul>
                </div>
            </div>
            <!-- End Product Gallery -->
            
            
            <!-- Start Product Info 展示产品信息-->
            <div class="col-md-6 single-shop">
                <div class="hidden-md-up"></div>
                <div class="rating-stars">
                    <i class="icon-star filled"></i>
                    <i class="icon-star filled"></i>
                    <i class="icon-star filled"></i>
                    <i class="icon-star filled"></i>
                    <i class="icon-star filled"></i>
                </div>
                <span class="text-muted align-middle">&nbsp;&nbsp;5 | 2条评论</span>
                <h2 class="padding-top-1x text-normal with-side"><%=goods_name %></h2>
                <span class="h2 d-block with-side">¥&nbsp;<%=goods_price %></span>
                <p><%=goods_describe %></p>
               
                <div class="row margin-top-1x">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label for="size">发布时间</label>
                            <select class="form-control" id="goods_issuDate">
                                <option><%=goods_issuDate %></option>                             
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-5">
                        <div class="form-group">
                            <label for="color">发布者</label>
                            <select class="form-control" id="goods_publisher">
                                <option><%=goods_publisher%></option>                                
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <label for="quantity">库存：</label>                       
                            <select class="form-control" id="goods_stock">
                                <option><%=goods_stock %>件</option>                                
                            </select>
                        </div>
                    </div>
                </div>
                <div class="pt-1 mb-2"><span class="text-medium">商品编码:</span> <%=goods_id %></div>
                <div class="padding-bottom-1x mb-2">
                    <span class="text-medium">商品类别:&nbsp;</span>
                    <a class="navi-link" href="#"><%=goods_category %></a>
                </div>
            </div>
            <div class="col-md-12">
                <hr class="mt-30 mb-30">
                <div class="d-flex flex-wrap justify-content-between mb-30">
                    <div class="entry-share">
                        <span class="text-muted">分享链接:</span>
                        <div class="share-links">
                            <a class="social-button shape-circle sb-facebook" href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="Facebook">
                                <i class="socicon-facebook"></i>
                            </a>
                            <a class="social-button shape-circle sb-twitter" href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="Twitter">
                                <i class="socicon-twitter"></i>
                            </a>
                            <a class="social-button shape-circle sb-instagram" href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="Instagram">
                                <i class="socicon-instagram"></i>
                            </a>
                            <a class="social-button shape-circle sb-google-plus" href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="Google +">
                                <i class="socicon-googleplus"></i>
                            </a>
                        </div>
                    </div>
                    <div class="sp-buttons mt-2 mb-2">
                        <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="加入收藏" data-original-title="加入收藏" onclick="location.href='usuallyController?url=<%="加入收藏"%>&goods_id=<%=goods_id%>&backUrl=<%="OneGoods-Detail-Demo.jsp"%>' ">
                            <i class="icon-heart"></i>
                        </button>
                        <button class="btn btn-primary" data-toast="" data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="<%=goods_name %>" data-toast-message="成功加入购物车!" onclick="location.href='usuallyController?url=<%="加入购物车"%>&goods_id=<%=goods_id%>&backUrl=<%="OneGoods-Detail-Demo.jsp"%>' "><i class="icon-bag"></i> 加入购物车</button>
                        <button type="button" publisher="<%=goods_publisher %>" class="btn btn-primary contactPublisher" >
                            <i class="layui-icon">&#xe626;</i> 联系卖家
                        </button>
                    </div>
                </div>
            </div>
            <!-- End Product Info -->
        </div>
        
        <!-- Start Product Tabs -->
        <div class="col-md-12">
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item"><a class="nav-link active" href="#description" data-toggle="tab" role="tab">详细描述</a></li>
                <li class="nav-item"><a class="nav-link" href="#reviews" data-toggle="tab" role="tab">评论</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade show active" id="description" role="tabpanel">
                    <p><%=goods_describe %></p>
                    
                </div>
                <div class="tab-pane fade" id="reviews" role="tabpanel">
                    <!-- Start Review #1 -->
                    <div class="comment">
                        <div class="comment-author-ava"><img src="assets/images/reviews/01.jpg" alt="Review Author"></div>
                        <div class="comment-body">
                            <div class="comment-header d-flex flex-wrap justify-content-between">
                                <h4 class="comment-title">使用感想</h4>
                                <div class="mb-2">
                                    <div class="rating-stars"><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i></div>
                                </div>
                            </div>
                            <p class="comment-text">评论：这个卖家很爽快，竟然还是我们专业的同学，感谢平台！</p>
                            <div class="comment-footer"><span class="comment-meta">匿名用户</span></div>
                        </div>
                    </div>
                    <!-- End Review #1 -->
                    <!-- Start Review #2 -->
                    <div class="comment">
                        <div class="comment-author-ava"><img src="assets/images/reviews/02.jpg" alt="Review Author"></div>
                        <div class="comment-body">
                            <div class="comment-header d-flex flex-wrap justify-content-between">
                                <h4 class="comment-title">使用感想</h4>
                                <div class="mb-2">
                                    <div class="rating-stars"><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i></div>
                                </div>
                            </div>
                            <p class="comment-text">这个校园二手平台是我使用过最方便的平台，非常简约小巧，适合在学校里面使用！</p>
                            <div class="comment-footer"><span class="comment-meta">匿名用户</span></div>
                        </div>
                    </div>
                    <!-- End Review #2 结束第二条评论 -->
                    
                </div>
            </div>
        </div>
        
        
        
        <!-- End Product Tabs -->
        <!-- 推荐相关产品                                                               以下是注释、、、、、、、
         Start Related Products 
        <h3 class="text-center padding-top-3x mb-30">Releted Products</h3>
        <div class="owl-carousel" data-owl-carousel='{ "nav": false, "dots": false, "margin": 30, "responsive": {"0":{"items":1},"576":{"items":2},"768":{"items":3},"991":{"items":4},";1200":{"items":4}} }'>
            Start Product #1 
            <div class="grid-item">
                <div class="product-card">
                    <a class="product-thumb" href="shop-single-3.html">
                        <img src="assets/images/shop/products/01.jpg" alt="Product">
                    </a>
                    <h3 class="product-title"><a href="shop-single-3.html">iPhone X</a></h3>
                    <h4 class="product-price">$749.99</h4>
                    <div class="product-buttons">
                        <div class="product-buttons">
                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="Whishlist">
                                <i class="icon-heart"></i>
                            </button>
                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="Product" data-toast-message="successfuly added to cart!">Add to Cart</button>
                        </div>
                    </div>
                </div>
            </div>
             End Product #1 
           Start Product #2 
            <div class="grid-item">
                <div class="product-card">
                    <div class="rating-stars">
                        <i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i>
                    </div>
                    <a class="product-thumb" href="shop-single-2.html">
                        <img src="assets/images/shop/products/05.jpg" alt="Product">
                    </a>
                    <h3 class="product-title"><a href="shop-single-2.html">Panasonic TX-32</a></h3>
                    <h4 class="product-price">$949.50</h4>
                    <div class="product-buttons">
                        <div class="product-buttons">
                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="Whishlist">
                                <i class="icon-heart"></i>
                            </button>
                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="Product" data-toast-message="successfuly added to cart!">Add to Cart</button>
                        </div>
                    </div>
                </div>
            </div>
            End Product #2 
           Start Product #3 
            <div class="grid-item">
                <div class="product-card">
                    <a class="product-thumb" href="shop-single-3.html">
                        <img src="assets/images/shop/products/09.jpg" alt="Product">
                    </a>
                    <h3 class="product-title"><a href="shop-single-3.html">Sony HDR-AS50R</a></h3>
                    <h4 class="product-price">$700.00</h4>
                    <div class="product-buttons">
                        <div class="product-buttons">
                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="Whishlist">
                                <i class="icon-heart"></i>
                            </button>
                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="Product" data-toast-message="successfuly added to cart!">Add to Cart</button>
                        </div>
                    </div>
                </div>
            </div>
            End Product #3 
             Start Product #4 
            <div class="grid-item">
                <div class="product-card">
                    <div class="rating-stars">
                        <i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i><i class="icon-star filled"></i>
                    </div>
                    <a class="product-thumb" href="shop-single-2.html">
                        <img src="assets/images/shop/products/13.jpg" alt="Product">
                    </a>
                    <h3 class="product-title"><a href="shop-single-2.html">HP LaserJet Pro 200</a></h3>
                    <h4 class="product-price">$249.50</h4>
                    <div class="product-buttons">
                        <div class="product-buttons">
                            <button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="Whishlist">
                                <i class="icon-heart"></i>
                            </button>
                            <button class="btn btn-outline-primary btn-sm" data-toast data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="Product" data-toast-message="successfuly added to cart!">Add to Cart</button>
                        </div>
                    </div>
                </div>
            </div>
            End Product #4                 
        </div>
        End Related Products         
        -->
      
      
      
    </div>
    <!-- End Product Content -->
  </div>
<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>