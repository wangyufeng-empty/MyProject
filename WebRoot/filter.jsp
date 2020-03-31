 <%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8" %>  
 <%@ page errorPage="Error_page_deal.jsp" %>  <!-- 加上了错误处理信息，跳转到页面 Error_page_deal.jsp-->
  <% request.setCharacterEncoding("utf-8"); %>  
 <% 
 String login_state = (String)session.getAttribute("login_state");
 String userName_check = (String)session.getAttribute("userName");
 if(login_state == null || !login_state.equals("true") || userName_check == null)
 	{
    		//response.setHeader("refresh","0;url=Load.jsp" );   
 		response.sendRedirect("login.jsp");//这是正确地完整路径，已验证！
 	} 
 %> 