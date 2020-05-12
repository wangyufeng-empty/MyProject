package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.user_info;
import net.sf.json.JSONObject;



//@WebServlet("/retrivePasswordController")
/*****此页面创造性地接收两个页面传来的请求，并进行控制，实现两个jsp页面通过servlet的控制完成交互*****/
public class retrivePasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public retrivePasswordController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*****设置请求时的编码格式*****/
		request.setCharacterEncoding("utf-8");

		/*****设置响应时的编码格式*****/
		response.setCharacterEncoding("utf-8");
		
		/*****设置浏览器显示时的显示格式*****/
		response.setContentType("text/html;charset=utf-8");
		
		 HttpSession session = request.getSession();
		 PrintWriter out = response.getWriter();
		 String url = request.getParameter("url");	 
		 user_info user = new user_info();//新建一个用户对象
		 String userId = request.getParameter("userId");//获取学号
		 request.setAttribute("userId", userId);
		 user.setUsername(userId);
		 
		 if(url.equals("Retrieve_password.jsp"))//获取来自Retrieve_password.jsp页面的找回密码请求的服务
		 {
			 /*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			 try 
			 {
				if(user.checkName())
				 {
					String question = user.getUserQuestion();//调用方法获取用户的密保问题				
					if(question.equals("userQuestion_motherName"))//问题是第一个
					{								
						returnJson.put("question", "你母亲的姓名是什么？");
					}
					else if(question.equals("userQuestion_firstLove"))//问题是第二个
					{				
						returnJson.put("question", "你的初恋是谁？");
					}
					String goUrl = "RetrievePassword_CheckQuestion.jsp";
					returnJson.put("goUrl", goUrl);
					returnJson.put("userId", userId);
					out.print(returnJson.toString());
				 }
				else //用户名不存在，提示注册
				{
					returnMessage ="账号不存在，请先注册！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());
				}
				
			 } 
			 catch (ClassNotFoundException | SQLException e) 
			 {	
				e.printStackTrace();
			 }
		 }
		 
		 /*****！！！这是两个请求的控制器，注意对象的属性值是否变化！！！****/
		 else if(url.equals("RetrievePassword_CheckQuestion.jsp"))  //填完问题和新密码的界面发过来的请求      
		 {
			 /*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			 try 
			 {
				 String userAnswer = request.getParameter("userAnswer");//获得用户输入的答案
				 String password = request.getParameter("password");//获得用户输入的新密码
				 user.setPassword(password);//给用户属性密码赋值
				 String stdAnswer = user.getUserAnswer();
				 if(userAnswer.equals(stdAnswer))   //如果答案匹配成功
				 {
					 int result = user.updatePassword();
					 if(result == 1)				 
					 {
						 returnMessage ="密码重置成功,快去登录吧！";
						 returnJson.put("returnMessage", returnMessage);
						 out.print(returnJson.toString());
					 }
					 else
					 {
						 returnMessage ="密码重置失败，发生未知错误！";
						 returnJson.put("returnMessage", returnMessage);
						 out.print(returnJson.toString());
					 }
				 }
				 else
				 {
					 returnMessage ="问题回答错误！";
					 returnJson.put("returnMessage", returnMessage);
					 out.print(returnJson.toString());
				 }
			 }
			 catch (ClassNotFoundException | SQLException e) 
			 {	
				e.printStackTrace();
			 }
			
		 }
	}

}
