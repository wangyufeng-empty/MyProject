<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,beans.*" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); response.setContentType("text/html;charset=utf-8"); response.setCharacterEncoding("utf-8");%>
<%@ include file="filter.jsp" %> 
<html lang="zxx" >
<body style="height: 100%">
<!--固定页头部分 -->
<%@ include file="header.jsp" %>
<script src="js/SecondHandPages_JS/myPublishDemoJs.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 主体部分 -->
<div class="hidden_div" id="coverDiv">
</div>
<div>
		<img id="loadgif" style="position:fixed; overflow: auto; z-index:9999;left:43%;top:45%;width: 200px;height: 200px;display: none" alt="加载中..." src="../assets/images/timg_loading.gif">
</div>

<div class="offcanvas-wrapper">
    <!-- Start Page Title -->
    <div class="page-title">
        <div class="container">
            <div class="column">
                <h1>通知公告</h1>
            </div>
            <div class="column">
                <ul class="breadcrumbs">
                    <li><a href="index.jsp">主页</a>
                    </li>
                    <li class="separator">&nbsp;</li>
                    <li>通知公告</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Page Title -->
    <!-- Start Content -->
    <div class="col-lg-8 col-md-8 offset-lg-2 offset-md-2 padding-bottom-3x">
       
        
        <h6 class="text-muted text-normal text-uppercase padding-top-2x mt-2">历史公告</h6>
        <hr class="margin-bottom-1x">
        <div class="accordion" id="accordion2" role="tablist">
        
        <c:forEach items="${sessionScope.announcementList}" var="announcement" varStatus="status">	
            <div class="card">
                <div class="card-header" role="tab">
                    <h6><a  href="#collapse${status.count }" data-toggle="collapse" data-parent="#accordion2"><i class="layui-icon">&#xe645;</i> 
                    	重要通知 #${status.count }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	发布者：燕大管理员${announcement.publisher_id }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	发布时间：${announcement.announcement_date }
                    </a></h6>
                </div>
                <div class="collapse" id="collapse${status.count }" role="tabpanel">
                    <div class="card-body">&nbsp;&nbsp;&nbsp;&nbsp;${announcement.announcement_content }</div>
                </div>
            </div>
        </c:forEach>
        
            
        </div>
    </div>
    <!-- End Content -->
</div>

<!--固定页脚部分 -->
<%@ include file="footer.jsp"%>
</body>
</html>