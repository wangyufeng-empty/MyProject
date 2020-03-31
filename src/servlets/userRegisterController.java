package servlets;
//用来实现用户注册的控制器
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;  //用来创建会话

import beans.DBUtil;
import beans.user_info;

//@WebServlet("/userRegisterController")
public class userRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public userRegisterController() {
        super();    
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out = response.getWriter();
		 HttpSession session = request.getSession();
		 response.setContentType("text/html;charset=utf-8");
		 response.setCharacterEncoding("UTF-8"); 
		 request.setCharacterEncoding("UTF-8"); 
		 user_info user = new user_info();//新建一个用户对象
		
		int result = 0;
		String userId = request.getParameter("userId");//获取学号
		String username = request.getParameter("userName");//获取姓名
		String password = request.getParameter("password");//获取密码
		String userSex = request.getParameter("userSex");//获取性别
		String userGrade = request.getParameter("userGrade");//获取年级
		String[] user_Hobby = request.getParameterValues("hobby");//获取爱好
		String userHobby = "";
		for(String ch:user_Hobby) //把复选框内容转化成一串字符串
		{
			userHobby = userHobby + ch + ";";
		}
		String userQuestion = request.getParameter("userQuestion");//获取用户选择的密保问题
		String userAnswer = request.getParameter("userAnswer");//获取密保问题的答案
		
		/*******************开始计算注册时间***********************/
		Calendar calendar = new GregorianCalendar();
		String year = ""+calendar.get(Calendar.YEAR); //int转化成string：与String相加，例如 int i = 10; String  s = ""+i;
		int Month = calendar.get(Calendar.MONTH)+1;
		String month = ""+Month;
		String day = ""+calendar.get(Calendar.DAY_OF_MONTH);
//		String hour = ""+calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
//		String min = ""+calendar.get(Calendar.MINUTE);          //获取当前分钟
//		String sec = ""+calendar.get(Calendar.SECOND);          //获取当前秒
		if(month.length()==1)//时间补零操作
			month = "0"+month;	
		if(day.length()==1)
			day = "0"+day;
		String registerTime = year + month + day;
		/*****************结束计算时间*****************************/
		
		user.setRegister_time(registerTime);//设置注册时间
		user.setNickname(username);
		user.setUsername(userId);
		user.setPassword(password);
		user.setUserSex(userSex);
		user.setUserHobby(userHobby);
		user.setUserGrade(userGrade);
		if(userQuestion.equals("userQuestion_motherName"))  //是否问题为motherName，如果是->
		{
			user.setUserQuestion_motherName(userAnswer);  //把这个问题的答案赋值给用户属性
			user.setUserQuestion_firstLove("0");
		}
		else
		{
			user.setUserQuestion_motherName("0");
			user.setUserQuestion_firstLove(userAnswer);  //把这个问题的答案赋值给用户属性
		}
		
		try {
				if(user.checkName())  //存在
				{
					String message = "该用户已被注册！";
					session.setAttribute("message", message);
					response.sendRedirect("register.jsp");
					//response.sendRedirect("userExist.jsp");
				}
				else
				{
					result = user.addUserRegister();  //执行类的方法
					if(result == 1) //用户注册成功
					{
						session.setAttribute("successMessage", "注册成功");    //返回登录界面，这个图标不一样
						response.sendRedirect("login.jsp");					
						//response.sendRedirect("registerSuccess.jsp");
					}
					else //注册失败
					{
						session.setAttribute("message", "注册失败");    //返回登录界面，这个图标不一样
						response.sendRedirect("login.jsp");		
					}
						
				}
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}

}