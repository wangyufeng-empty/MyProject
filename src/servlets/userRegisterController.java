package servlets;
//用来实现用户注册的控制器
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;  //用来创建会话

import beans.user_info;
import beans.CollectInfoFor_IR;
import beans.IntelligentRecommendation;

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
		 /*新建一个用户对象*/
		 user_info user = new user_info();
		
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String registerTime = dateFormat.format(new Date());
		/*****************结束计算时间*****************************/
		
		user.setRegister_time(registerTime);//设置注册时间
		user.setNickname(username);
		user.setUsername(userId);
		user.setPassword(password);
		user.setUserSex(userSex);
		user.setUserHobby(userHobby);
		user.setUserGrade(userGrade);
		user.setAccount_state(1);
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
					if(user.isRealName()){ //实名认证成功
						result = user.addUserRegister();  //执行类的方法
						if(result == 1) //用户注册成功，把用户爱好更新到智能推荐表的user_hobby中
						{
							System.out.println(userId+"实名认证成功，用户已创建");
							
							/*初始化CollectInfo_forIR表格，为用户开辟一个信息收集表*/
							CollectInfoFor_IR collectInfo_table = new CollectInfoFor_IR();
							collectInfo_table.setUser_id(userId);  //ID
							long timeStamp; //时间戳							 
							timeStamp = (new Date().getTime());
							
							for(int i=1; i<=6; i++,timeStamp+=1000){   //初始化这些数据的时候每一项加1秒
								String nowtime = dateFormat.format(timeStamp);
								collectInfo_table.setCircle_id(i);  //设置循环ID 1-6
								collectInfo_table.setFavorite_record_time(nowtime);
								collectInfo_table.setPurchase_record_time(nowtime);
								collectInfo_table.setSearch_record_time(nowtime);
								collectInfo_table.setView_details_record_time(nowtime);
								result = collectInfo_table.AddOneInfo_Initialization();
							}
							if(result==1){System.out.println(userId+": CollectInfo_forIR初始化成功！");}
							/*结束初始化*/
							
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
					else{  //实名认证失败
						session.setAttribute("message", "实名认证失败！请咨询管理员或检查您的学号和姓名是否对应");    //返回注册界面，这个图标不一样
						response.sendRedirect("register.jsp");		
					}
					
						
				}
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}

}
