package servlets;

//实现用户登录验证信息的controller   它是servlet
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;  //用来创建会话

import beans.user_info;
import net.sf.json.JSONObject;
import beans.Administrators;
import beans.AnsjSplitAndWordCount;
import beans.IntelligentRecommendation;
import beans.CollectInfoFor_IR;

//@WebServlet("/userLoginController")
public class userLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public userLoginController() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);  //统一跳转到doPost()处理
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
		
		user_info user = new user_info();//新建一个用户对象
		Administrators admin = new Administrators();//新建一个管理员对象，用于判断登录的用户类型
		String returnMessage = "";  //待返回的ajax信息
		JSONObject returnJson = new JSONObject();  //待返回json
		
		String username = request.getParameter("username");//获取账号
		String password = request.getParameter("password");//获取密码
		
		user.setUsername(username); //给用户对象设置属性值，方便等等进行对比  见user_info.java文件
		user.setPassword(password);  
		admin.setAdminId(username);//给管理员用户设置账号密码属性值
		admin.setAdminPsw(password);
		
		try 
		{
			/************************** 这个用户是普通用户*********************/
			if(username.length()==12)  
			{
				if(user.checkName())  //判断用户名是否存在的方法,如果用户存在
				{
					String user_password = user.getUserPassword();//从数据库获取标准密码
					
					if(user_password.equals(password))  //用户密码正确
					{
						if(user.accountState()==1){  //并且账号状态为1，正常
							
							try {
								CollectInfoFor_IR CollectInfo = new CollectInfoFor_IR();  //实例化对象
								CollectInfo.setUser_id(username); //初始化ID
								/*获取原始推荐文本*/
								String IR_Original_String = CollectInfo.GetIR_Original_String();
								//System.out.println("IR_Original_String 初始初始文本为：" + IR_Original_String);
								/*获取词频最高的前6个关键词*/
								AnsjSplitAndWordCount WordCount = new AnsjSplitAndWordCount();					
								String[] keyArrays = new String[6];
								keyArrays = WordCount.Ansj_SplitAndWordCount(IR_Original_String);
								System.out.println("IR_Original_String处理后的IR_KeyWord为：" + Arrays.toString(keyArrays));
								/*获取推荐商品的ID并入库,返回推荐商品的所有信息*/
								IntelligentRecommendation IR = new IntelligentRecommendation();//实例化对象
								IR.setUser_id(username);
								List IR_Goods_Infos = IR.Generate_IR_GoodsId(keyArrays);  //传入关键词数组
								session.setAttribute("IR_Goods_Infos", IR_Goods_Infos);  //最终传入会话，在页面可以获取推荐的商品的所有信息
								
								} catch (Throwable e) {						
									e.printStackTrace();
								}						
							
								String userNickname = user.getUserNikename();//从数据库获取用户名		
								session.setAttribute("userName", userNickname);     //用户名
						        session.setAttribute("userPassword",password);    //用session对象容器装入你的账号和密码  
						        session.setAttribute("userId", username);     //用户ID
						        session.setAttribute("login_state","true"); //设置登录状态为  true 
						        //response.sendRedirect("index.jsp");//在前台含有ajax的时候会被拦截，无法跳转
						        String goUrl = "index.jsp";
								returnJson.put("goUrl", goUrl);
								out.print(returnJson.toString());
							
						}//end accountState=1
						else //用户账号被封禁，无法登录
						{
							returnMessage = "您的账户已被封禁，请联系管理员！";
							returnJson.put("returnMessage", returnMessage);
							out.print(returnJson.toString());
						}
									      
					}
					else  //否则，密码错误
					{
						returnMessage = "密码错误！";
						returnJson.put("returnMessage", returnMessage);
						out.print(returnJson.toString());
						
					}
				}
				else //用户名不存在，提示注册
				{
					returnMessage = "账号不存在，请先注册！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());
					
				}
			} //username==12
			
			/************************** 这个用户是管理员*********************/
			/***************管理员是开发者分配的账号，无法自己找回密码或者注册**************/
			
			if(username.length()==6)  //用户为管理员
			{
				if(admin.checkName())  //账号存在
				{
					String admin_password = admin.getAdminPassword();  //获取标准密码
					if(admin_password.equals(password))  //密码正确
					{
						String adminname = admin.getAdminname();
						session.setAttribute("adminName", adminname);     
				        session.setAttribute("adminPassword",password);    //用session对象容器装入你的账号和密码  
				        session.setAttribute("adminId", username);     
				        session.setAttribute("login_state","true"); //设置登录状态为  true 
				        //response.sendRedirect("Manager-Index.jsp");
				        //response.sendRedirect("http://wyfbuy.free.idcfengye.com/SecondHandShopping_BSM/html/index.html");
				        String goUrl = "http://localhost:8080/SecondHandShopping_BSM/html/index.html";
						returnJson.put("goUrl", goUrl);
						out.print(returnJson.toString());
					}
					else  //否则，密码错误
					{
						returnMessage = "密码错误！";
						returnJson.put("returnMessage", returnMessage);
						out.print(returnJson.toString());	
					}
				}
				else //管理员不存在
				{
					returnMessage = "管理员账号不存在，请联系开发者：王宇峰！";
					returnJson.put("returnJson", returnMessage);
					out.print(returnMessage.toString());	
				}
			}
		}
		catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}


