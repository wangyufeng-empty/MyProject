package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.user_info;

public class JsVerify extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public JsVerify() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*****设置请求时的编码格式*****/
		request.setCharacterEncoding("utf-8");

		/*****设置响应时的编码格式*****/
		response.setCharacterEncoding("utf-8");
		
		/*****设置浏览器显示时的显示格式*****/
		response.setContentType("text/html;charset=utf-8");	
		 
		/*这里完成service服务层,根据url不同，调用不同函数完成相应的逻辑*/
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		String method = request.getParameter("method");  //获取请求的路径，配合hidden或者链接 ?name=value 使用
		System.out.println("Request Js method: "+method);
		try {
			
		if(method.equals("ajax")){
			
			
		}
		else if("checkUserId".equals(method)){
			checkUserId(request, response);
		}
		
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
	} //doPost end

	private void checkUserId(HttpServletRequest request, HttpServletResponse response) throws 
	IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String user_id = request.getParameter("user_id");
		System.out.println("user_id:"+user_id);////////////
		user_info user_info = new user_info();
		user_info.setUsername(user_id);
		System.out.println("checkName:"+user_info.checkName());///////////
		if(user_info.checkName()){  //存在
			response.getWriter().print(1); 
		}
		else{
			response.getWriter().print(0); 
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//class end 

