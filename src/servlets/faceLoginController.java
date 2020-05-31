/**
 * 
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.*;

import net.sf.json.JSONObject;
import faceLogin.*;
import net.sf.json.JSONArray;
/*
*@author 王宇峰
*@time 2020年5月30日
*@filename faceLoginController.java
*package servlets;
*public class faceLoginController{ }
*/
/**
 * @author 王宇峰
 *
 */
public class faceLoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
		 * Constructor of the object.
		 */
	public faceLoginController() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
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
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		String method = request.getParameter("method");
		/*回调json对象和信息*/
		JSONObject returnJsonObject = new JSONObject();
		String resultMessage = "";
		int resultState = 0;  //返回状态 成功：1    失败：0
		//人脸匹配请求
		if("faceSearch".equals(method)) {  
			
			String imageBASE64 = request.getParameter("imageBASE64");
			String user_id = request.getParameter("user_id");
		try {
				/*调用API，返回json字符串*/
				String result = faceSearch.search(imageBASE64, user_id);
				
				JSONObject resultJson=JSONObject.fromObject(result);//把百度返回的result字符串转化为json对象
				
				/*获取返回代码*/
				String error_code = resultJson.getString("error_code");
				
				/*获取返回错误信息*/
				String error_msg = resultJson.getString("error_msg");
				
				/*验证成功*/
				if(error_code.equals("0")) {
					/*解析json的分数数据*/	            
		            String result_in = resultJson.getString("result").toString();  //获取到result里面的数据
		            JSONObject result_list = JSONObject.fromObject(result_in);  //转为json对象
		            JSONArray user_list = result_list.getJSONArray("user_list");//  用JSONArray对象获取user_list数组数据
		            JSONObject user_1 = (JSONObject) user_list.get(0);  //获取到第一个人的json对象
		            
		            String score = user_1.getString("score");
		            System.out.println("score: "+score);//////////
		            /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
		            
		            /*执行登录成功的程序*/
		            if(Double.parseDouble(score)>=80) {
		            	
		            	/*准备进入主页*/
		            	user_info user = new user_info();//新建一个用户对象
		            	user.setUsername(user_id);		            
		            	if(user.accountState()==1){  //并且账号状态为1，正常
							/*移除登录错误次数*/
							session.removeAttribute("ERRORPWDCOUNT");
							session.removeAttribute("reqUser_Id");
			            	session.removeAttribute("autoFullId");
			            	
							CollectInfoFor_IR CollectInfo = new CollectInfoFor_IR();  //实例化对象
							CollectInfo.setUser_id(user_id); //初始化ID
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
							IR.setUser_id(user_id);
							List IR_Goods_Infos = IR.Generate_IR_GoodsId(keyArrays);  //传入关键词数组
							session.setAttribute("IR_Goods_Infos", IR_Goods_Infos);  //最终传入会话，在页面可以获取推荐的商品的所有信息
							
							String userNickname = user.getUserNikename();//从数据库获取用户名		
							session.setAttribute("userName", userNickname);     //用户名
					       
					        session.setAttribute("userId", user_id);     //用户ID
					        session.setAttribute("login_state","true"); //设置登录状态为  true
					        
					        /*更新轮播图的session*/
					        GoodsPicture goods_picture = new GoodsPicture();
					        List RotationChartList = goods_picture.getAllRotationChart();
							session.setAttribute("RotationChartList", RotationChartList);
					        /*更新最新发布的商品*/
							Goods Goods = new Goods();
							List LastestGoodsList = Goods.getLastestGoods();
							session.setAttribute("LastestGoodsList", LastestGoodsList);
							
			            	resultMessage = "登录成功！";
			            	resultState = 1; //成功
					       
						}//end accountState=1
						else //用户账号被封禁，无法登录
						{
							resultMessage = "您的账户已被封禁，请联系管理员！";
							resultState = 0;
							returnJsonObject.put("resultMessage", resultMessage);
							
						}
		            	
		            	
		            }
		            else if(Double.parseDouble(score)<80){
		            	resultMessage = "错误：不是本人登录！";
		            	resultState = 0;
					}
		           
				}
				
				/*检测失败*/
				else if(error_code.equals("223120")) {
					resultMessage = "活体检测失败，请真人检测或者保持光源充足";
					resultState = 0;
				}
				else if(error_code.equals("222207")) {
					resultMessage = "您还未注册过面部信息！";
	            	resultState = 0;
				}
				else if(error_code.equals("223114")){
					resultMessage = "面部信息模糊，保持光源充足，并重新验证";
	            	resultState = 0;
				}
				else {
		            	resultMessage = "error: "+error_msg;
		            	resultState = 0;
					}
				
				returnJsonObject.put("resultMessage", resultMessage);
				returnJsonObject.put("resultState", resultState);
				returnJsonObject.put("error_code", error_code);
				out.print(returnJsonObject.toString());
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("faceAdd".equals(method)) {  
			
			/*获取必要参数*/
			String imageBASE64 = request.getParameter("imageBASE64");
			String user_id = request.getParameter("user_id");
			String user_name = request.getParameter("user_name");
			
			/*调用API，返回json字符串*/
			String result = faceAdd.add(imageBASE64, user_id, user_name);
			
			//把百度返回的result字符串转化为json对象
			JSONObject resultJson=JSONObject.fromObject(result);
			
			/*获取返回代码*/
			String error_code = resultJson.getString("error_code");
			
			/*获取返回错误信息*/
			String error_msg = resultJson.getString("error_msg");
			
			System.out.println(result);
	        JSONObject resultJsonObject=JSONObject.fromObject(result);
	        
	        
	        
	        if(error_code.equals("0")) {
	        	/*获取面部信息唯一标识码*/
	        	String face_token = JSONObject.fromObject(resultJsonObject.getString("result")).getString("face_token");
	        	resultMessage = "面部信息注册成功！";
	        	resultState = 1;
	        }
	        else if(error_code.equals("223120")){
	        	resultMessage = "活体检测失败，请真人验证或保证拍摄质量！";
	        	resultState = 0;
			}
	        else {
	        	resultMessage = "error: "+error_msg;
	        	resultState = 0;
			}
	        
	        /*返回信息*/
	        returnJsonObject.put("resultMessage", resultMessage);
			returnJsonObject.put("resultState", resultState);
			returnJsonObject.put("error_code", error_code);
			out.print(returnJsonObject.toString());
		}
		
		/*获得请求服务的学号*/
		else if("GetUserId".equals(method)) {
			
			String user_id = request.getParameter("user_id");//获取学号
			user_info user = new user_info();//新建一个用户对象
			user.setUsername(user_id);
			try {
				if(user.checkName()) { //用户存在
					String goUrl = "faceLogin.jsp";
					session.setAttribute("reqUser_Id", user_id);  //待请求服务的user_id
					session.setAttribute("autoFullId", user_id);  //自动填充的ID
					returnJsonObject.put("goUrl", goUrl);
				}
				else //用户名不存在，提示注册
				{
					resultMessage ="账号不存在，请先注册！";
					returnJsonObject.put("resultMessage", resultMessage);
					
				}
				out.print(returnJsonObject.toString());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		
		
		out.flush();
		out.close();
	}
	
	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	public String faceAdd() {
		String face_token = "";
		return face_token;
	}
	

}
