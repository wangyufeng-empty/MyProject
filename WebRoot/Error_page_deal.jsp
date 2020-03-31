<%@page import="java.io.PrintWriter"%>
<%@ page contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<%@ page isErrorPage="true"%>
<html>	
	<head><title>error_infomation</title></head>
	<body>
		<b>错误描述：</b><br><%= exception.getMessage() %><br/>
		<%= exception.toString() %><p/>
		<b>详细错误信息：</b><p/>
		<pre><% exception.printStackTrace(new java.io.PrintWriter(out)); %>
		</pre>
	</body>
</html>