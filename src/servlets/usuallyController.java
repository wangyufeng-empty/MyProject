package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Random;
import java.util.Scanner;

public class usuallyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public usuallyController() {
        super(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
		
		
		//过滤
		String login_state = (String)session.getAttribute("login_state");
		String userName_check = (String)session.getAttribute("userName");
		 if(login_state == null || !login_state.equals("true") || userName_check == null)
	 	{
	 		response.sendRedirect("login.jsp");
	 	} 
		 
		String url = request.getParameter("url");  //获取请求的路径，配合hidden或者链接 ?name=value 使用
		
		
		/**********************************1-请求为侧拉菜单“分类浏览”的计算机书籍类*************************/
		
		if(url.equals("CategorySearch"))   //1-请求为侧拉菜单“分类浏览”
		{
			
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			String category = request.getParameter("category");
			
			Goods goods = new Goods();
			goods.setGoodsCategory(category);
			ArrayList searchCategoryResult = null;
			try 
			{			
				searchCategoryResult = (ArrayList) goods.getGoodsInfoByCategory();  //查询结果list
				session.setAttribute("sort", category);//发送给jsp的url以显示不同的内容，这里按照类别发送
				session.setAttribute("searchCategoryResult", searchCategoryResult);//把结果集发送给显示货物的界面
				returnJson.put("goUrl", "searchCategory.jsp");
				out.print(returnJson.toString());
				//response.sendRedirect("searchCategory.jsp");
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		/*************************2-请求的地方是“退出登录”功能*******************************/
		else if(url.equals("loginout"))  //2-请求的地方是“退出登录”功能
		{
			session.setAttribute("login_state", "false");
			session.removeAttribute("userName"); 
			
			response.sendRedirect("login.jsp"); 
		}
		
		/*********************3-请求的地方是“关键字搜索”功能**********************************/
		else if(url.equals("site-search"))   //3-请求的地方是“关键字搜索”功能
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			Goods goods = new Goods();
			String search_key = request.getParameter("search_key");
			System.out.println("search_key:"+search_key);//////////////
			goods.setGoodsName(search_key); //把搜索关键字放入类的goodsname属性中，参见getGoodsInfoByKey()方法内容
			try
			{
				ArrayList SearchResultGoodsInfo = (ArrayList) goods.getGoodsInfoByKey();    //根据关键字搜索返回的结果
				if(SearchResultGoodsInfo.size()==0)  //本次关键字搜索结果为空
				{
					returnJson.put("returnMessage","本次搜索结果为空哦！");
					out.print(returnJson.toString());
				}
				else  //关键字搜索成功
					{
						/*更新智能推荐表------------------------------------------------*/
						String search_record = "" + search_key;   
						String userId = (String)session.getAttribute("userId");  //得到用户ID
						CollectInfoFor_IR CollectInfo = new CollectInfoFor_IR();  //实例化对象
						CollectInfo.setUser_id(userId); //初始化ID
						CollectInfo.Update_IR_Table(search_record, "search_record");  //高级复杂函数			
						/*结束更新智能推荐表----------------------------------------------------------*/
						try {
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
								IR.setUser_id(userId);
								List IR_Goods_Infos = IR.Generate_IR_GoodsId(keyArrays);  //传入关键词数组
								session.setAttribute("IR_Goods_Infos", IR_Goods_Infos);  //最终传入会话，在页面可以获取推荐的商品的所有信息
								
								} catch (Throwable e) {						
									e.printStackTrace();
							}						
							/*-----------------------------------------------------*/
						
					//request.setAttribute("url", "searchResultSuccess");//发送给jsp的url以显示不同的内容
					session.setAttribute("SearchResultGoodsInfo", SearchResultGoodsInfo);//把结果集发送给显示货物的界面
					returnJson.put("goUrl", "site-searchResult.jsp");
					out.print(returnJson.toString());
					
				}
			}
			catch (ClassNotFoundException | SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		/*******************************4-获取来自“显示基本信息”功能的请求申请*************************/
		else if(url.equals("userInfoDemo")) //4-获取来自“用户基本信息”功能的请求申请
		{
			String user_id = request.getParameter("userId");//获取用户ID
			user_info user = new user_info(); //新建一个用户对象
			user.setUsername(user_id);
			try 
			{
				Map userInfo = user.getUserinfo();
				request.setAttribute("userInfo", userInfo);//把结果集发送给显示货物的界面
				request.getRequestDispatcher("userInfoDemo.jsp").forward(request, response);//请求转发
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}		
		}
		
		
		/*******************************5-获取来自“修改/完善个人信息”功能的请求申请*************************/
		else if(url.equals("updateProfile"))  //5-获取来自“修改/完善个人信息”功能的请求申请
		{
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			String userId = (String)session.getAttribute("userId");  //得到用户ID
			String userTel = request.getParameter("userTel");
			String userEmail = request.getParameter("userEmail");
			String userAddress = request.getParameter("userAddress");
			String userGrade = request.getParameter("userGrade");
			String selfIntroduce = request.getParameter("selfIntroduce");
			String selfBlessing = request.getParameter("selfBlessing");
			user_info user = new user_info();
			user.setUsername(userId);
			user.setUser_tel(userTel);
			user.setUser_email(userEmail);
			user.setUser_address(userAddress);
			user.setUserGrade(userGrade);
			user.setSelf_introduce(selfIntroduce);
			user.setSelf_blessing(selfBlessing);
			try 
			{
				int result = user.updateUser();
				if(result == 1)  //更新、完善个人信息成功
				{
					returnMessage = "修改成功了哦！";
					System.out.println(userId+": 修改个人信息成功");
					returnJson.put("returnMessage", returnMessage);
					returnJson.put("status", "success");
					out.print(returnJson.toString());
				}
				else
				{
					returnMessage ="修改失败，发生未知错误！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());
				}
					
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		
		/**********************6-获取来自“头部-查看所有商品信息”功能的请求申请***********************/
		else if(url.equals("全部商品"))  //6-获取来自“头部-查看所有商品信息”功能的请求申请
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			Goods goods = new Goods();  //新建一个货物对象
			ArrayList goodsInfo = null;
			try 
			{
				goodsInfo = (ArrayList) goods.getAllGoodsInfo(); //查询结果list
				session.setAttribute("goodsInfo", goodsInfo);//把结果集发送给显示货物的界面
				returnJson.put("goUrl", "All-Goods-Demo.jsp");
				out.print(returnJson.toString());
				
				
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}  
		}
		
		
		/*****************************  7-获取来自“加入购物车”功能的请求申请 （和加入收藏类似，就是界面不同）*****************/
		/**********在这里，每个“加入购物车”的按钮都额外传递了一个返回路径，以便servlet返回时，返回刚才的操作界面*************/
		/********************************加入购物车时判断库存是否充足,并且判断已选数量是否大于等于库存***********************/
		/*************注意：：：：：；链接传参只能在第一个变量上写问号？  后面不能带问号  只能写&   *************************/
		//7-获取来自“加入购物车”功能的请求申请
		else if(url.equals("加入购物车"))  
		{ 	
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			
			//String backUrl = request.getParameter("backUrl");//获取请求的具体路径
			String goods_id = request.getParameter("goods_id");//从各个界面获取想要加入购物车的   货物ID
			String user_id = (String)session.getAttribute("userId");  //获取一下     用户ID号
			
			/********先从货物信息中取出相应的信息，然后到购物车********/			
			Goods goods = new Goods();
			GoodsCart cart = new GoodsCart();
			
			
			try 
			{
				/************************准备取出购物车已选数量****************/
				cart.setUser_id(user_id);
				cart.setGoods_id(goods_id);			
				int selectQuantity = cart.getselectedQuantity();  //购物车的已选数量
				/************************************************************/
				goods.setGoodsId(goods_id);
				Map goodsInfo = goods.getGoodsInfo();//获取到一条货物信息
				String goods_name = (String) goodsInfo.get("goods_name");  //获取货物名字
				String goods_publisher = (String) goodsInfo.get("goods_publisher");  //获取货物名字
				String goods_category = (String) goodsInfo.get("goods_category");  //获取货物名字
				Double goods_price = Double.parseDouble(goodsInfo.get("goods_price").toString()); //单价
				
				int goods_stock =  Integer.parseInt(goodsInfo.get("goods_stock").toString());   //取出库存，
				/********先从货物信息中取出相应的信息，然后到购物车 end********/
				if(goods_stock > 0 && selectQuantity < goods_stock)  //库存大于0,并且已选数量小于（不能等于，否则在下面加一以后超过库存）库存才能加入购物车
				{
					
					cart.setGoods_id(goods_id);
					cart.setUser_id(user_id);
					if(cart.isGoodsSelected())  //如果已经选了这件商品，则进去，已选数量加一即可
					{
						double subtotal = cart.getSubtotalById(); //小计   这个不能放在if之外，如果现在这件商品不存在，则取出空值！
						int selectedQuantity = cart.getselectedQuantity();//获取已选数量
						selectedQuantity++;
						if(selectedQuantity > 5)//选购数量一次性不能大于5件
						{
							returnMessage = "加入失败！"+goods_name+"  购物车里已有5件了哦！";
							returnJson.put("returnMessage", returnMessage);
							out.print(returnJson.toString());
						}
						else  //选购数量不足五件
						{
							cart.setSelectedQuantity(selectedQuantity);  
							int result_SelectedQuantity = cart.updateSelectedQuantity();//更新了选货数量
							//subtotal = selectedQuantity * goods_price;  //把商品的小计更新一下
							//cart.setSubtotal(subtotal);//设置回属性
							int result_Subtotal = cart.AutoUpdateSubtotal();//执行更新
						
							if(result_SelectedQuantity == 1 && result_Subtotal==1) //加入成功，已有记录的基础
							{
								System.out.println(user_id+":正在加入购物车"+goods_name);
								returnMessage = goods_name+":成功加入购物车了哦！";
								returnJson.put("returnMessage", returnMessage);
								returnJson.put("status", "success");
								out.print(returnJson.toString());
								
							}
							else
							{
								returnMessage = goods_name+":添加失败，发生未知错误！";
								returnJson.put("returnMessage", returnMessage);
								out.print(returnJson.toString());															
							}
						}
					}				
					else   //购物车还没有这条记录 , 则添加全新的
					{
						double subtotal = 0;
						int selectedQuantity = 1;
						cart.setSelectedQuantity(selectedQuantity);
						subtotal = selectedQuantity * goods_price;  //把商品的小计更新一下
						cart.setSubtotal(subtotal);//设置回属性
						
						cart.setGoods_name(goods_name);
						cart.setGoods_publisher(goods_publisher);
						cart.setGoods_category(goods_category);
						cart.setGoods_price(goods_price);
						
						int result = cart.addCartInfo();    //技术亮点，可以同时更新不同数据类型的一整条记录
						if(result == 1) //加入成功，已有记录的基础
						{
							System.out.println(user_id+":正在加入购物车"+goods_name);
							returnMessage = goods_name+":成功加入购物车了哦！";
							returnJson.put("returnMessage", returnMessage);
							returnJson.put("status", "success");
							out.print(returnJson.toString());							
						}
						else
						{
							returnMessage = goods_name+":添加失败，发生未知错误！";
							returnJson.put("returnMessage", returnMessage);
							out.print(returnJson.toString());						
						}
					}
				}
				else  //库存为0，或者购物车已选数量达到库存上限，无法加入购物车
				{
					returnMessage = goods_name+":已选数量达到库存上限！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());	
				}
			}
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}  
		}
		
		
		/**********  8-获取来自“加入收藏”功能的请求申请 *************/    
		//2019-7-10   已经写好类和界面，直接加入就行
		else if(url.equals("加入收藏"))  
		{
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			try 
			{
				/**************创建对象*******************/
				Goods goods = new Goods();
				WishList_Info wishList = new WishList_Info();
				/*******************************************/
				
				/**************获取参数*******************/
				//String backUrl = request.getParameter("backUrl");//获取请求的具体路径
				String goods_id = request.getParameter("goods_id");  //获取到货物ID   1
				String userId = (String)session.getAttribute("userId");  //2
				System.out.println(userId+":正在收藏 "+goods_id);
				/******************************************/
				
				/*************取商品属性****************/
				goods.setGoodsId(goods_id);
				Map goodsInfo = (HashMap)goods.getGoodsInfo();
				String goods_name = (String)goodsInfo.get("goods_name"); //3
				String goods_category = (String)goodsInfo.get("goods_category");  //4
				int goods_stock = Integer.parseInt((String)goodsInfo.get("goods_stock")); // 5
				double goods_price = Double.parseDouble((String)goodsInfo.get("goods_price"));  //6
				String goods_pubilsher = (String)goodsInfo.get("goods_publisher"); //7
				/**************************************/
				
				/*************设置收藏属性************/
				wishList.setUser_id(userId);
				wishList.setGoods_id(goods_id);
				wishList.setGoods_name(goods_name);
				wishList.setGoods_category(goods_category);
				wishList.setGoods_stock(goods_stock);
				wishList.setGoods_price(goods_price);
				/*************************************/
				
				if(wishList.isGoodsWished()==false)   //如果没有收藏
				{
					/*************执行更新**************/
					int result = wishList.addWishListInfo();
					/**********************************/
					if(result == 1) //成功加入收藏
					{
						/*开始更新智能推荐表----------------------------------*/
						String favorite_record = "" + goods_name + ";" + goods_pubilsher + ";" + goods_category;   
						CollectInfoFor_IR CollectInfo = new CollectInfoFor_IR();  //实例化对象
						CollectInfo.setUser_id(userId); //初始化ID
						CollectInfo.Update_IR_Table(favorite_record, "favorite_record");
						/*结束更新智能推荐表----------------------------------*/
						try {
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
						IR.setUser_id(userId);
						List IR_Goods_Infos = IR.Generate_IR_GoodsId(keyArrays);  //传入关键词数组
						session.setAttribute("IR_Goods_Infos", IR_Goods_Infos);  //最终传入会话，在页面可以获取推荐的商品的所有信息
						
						} catch (Throwable e) {						
							e.printStackTrace();
						}						
						/*-----------------------------------------------------*/
						
						List WiListInfos = wishList.getAllWishListInfo();
						session.setAttribute("WiListInfos", WiListInfos);//加入全局会话
						
						returnMessage = goods_name+" ：成功加入收藏！";
						returnJson.put("returnMessage", returnMessage);
						returnJson.put("status", "success");
						out.print(returnJson.toString());	
										
					}
					else
					{
						returnMessage = goods_name+" ：添加失败，发生未知错误！";
						returnJson.put("returnMessage", returnMessage);
						out.print(returnJson.toString());	
									
					}
				}
				else
				{
					returnMessage =  goods_name+" ：已在您的收藏！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());
				}
			}
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}  
			
		}
		
		/**********  9-获取来自“我的收藏”功能的请求申请 （和加入购物车类似，就是界面不同）*********************/     //2019-7-10   已经写好类和界面，直接加入就行
		else if(url.equals("我的收藏"))  // 9-获取来自“我的收藏”功能的请求申请 （和加入购物车类似，就是界面不同）
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			try 
			{
				/**************创建对象*******************/		
				WishList_Info wishList = new WishList_Info();
				/*******************************************/
				
				/**************获取参数*******************/
				String userId = (String)session.getAttribute("userId");  
				/******************************************/
				wishList.setUser_id(userId);
				List WiListInfos = wishList.getAllWishListInfo();	
				session.setAttribute("WiListInfos", WiListInfos);//加入全局会话
				returnJson.put("goUrl", "Wish-List-Demo.jsp");
				out.print(returnJson.toString());
				
			}
			catch (ClassNotFoundException | SQLException e)
			{
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
				
		}
		
		
		/***************************  10-获取来自“查看购物车”功能的请求申请 **********************************/
		else if(url.equals("查看购物车"))  // 10-获取来自“查看购物车”功能的请求申请 
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			GoodsCart cart = new GoodsCart();  //新建一个货物对象
			String user_id = (String)session.getAttribute("userId");
			cart.setUser_id(user_id);
			ArrayList cartsInfo = null;
			try 
			{
				cartsInfo = (ArrayList) cart.getAllCartInfo(); //查询结果list
				session.setAttribute("cartsInfo", cartsInfo);//把结果集发送给显示货物的界面
				returnJson.put("goUrl", "Goods-Cart-Demo.jsp");
				out.print(returnJson.toString());
				
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}  
		}
		
		/***************************  11-获取来自“清空购物车”功能的请求申请 **********************************/
		else if(url.equals("清空购物车"))  // 11-获取来自“清空购物车”功能的请求申请 
		{
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			String user_id = (String)session.getAttribute("userId");  
			ArrayList cartsInfo = null;
			try 
			{
				GoodsCart goodsCart = new GoodsCart();
				goodsCart.setUser_id(user_id);//设置ID，准备清空购物车
				goodsCart.deleteAllGoodsCartInfo(); //执行清空购物车       这里要更新一下
				cartsInfo = (ArrayList) goodsCart.getAllCartInfo(); //查询结果list
				session.setAttribute("cartsInfo", cartsInfo);//把结果集发送给显示购物车的界面，重新更新cartInfo,不然返回购物车还是显示原来的
				
				returnMessage = "购物车已清空！";
				returnJson.put("returnMessage", returnMessage);
				returnJson.put("status", "success");
				out.print(returnJson.toString());
			} 
			catch (ClassNotFoundException | SQLException e) 
			{				
				e.printStackTrace();
			}
		}
		
		/***************************  12-获取来自购物车界面的“修改数量”功能的请求申请 **********************************/
		/*******************修改数量需要额外判断库存是否充足***************/
		else if(url.equals("修改数量"))  //12-获取来自购物车界面的“修改数量”功能的请求申请
		{
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			String goods_id = request.getParameter("goods_id");
			int selectedQuantity = Integer.parseInt(request.getParameter("selectedQuantity"));  //获取用户想要修改的数量
			String user_id = (String)session.getAttribute("userId");
			/******************定义对象**********************/
			GoodsCart goodsCart = new GoodsCart();   //在这里定义购物车类对象了
			Goods goods = new Goods();
			/***********************************************/
			try 
			{
				/***************判断库存操作******************/
				goods.setGoodsId(goods_id);//准备取出库存
				Map goodsInfo = goods.getGoodsInfo();
				int goods_stock =  Integer.parseInt(goodsInfo.get("goods_stock").toString());   //取出库存，
				/***************判断库存操作******************/
				goodsCart.setUser_id(user_id);//设置用户ID
				goodsCart.setSelectedQuantity(selectedQuantity);//设置新的选货数量
				goodsCart.setGoods_id(goods_id);;//设置货物ID			
			
				Map goodsCartOne = goodsCart.getOneGoodsCartInfo();  //先获得购物车中某个用户的的某条货物信息
				String goods_name = (String) goodsCartOne.get("goods_name");
				if(selectedQuantity <= goods_stock)   //修改的数量小于等于库存，允许修改
				{
					int result = goodsCart.updateSelectedQuantity();   //这里更新选货数量
					
					int result_Subtotal = goodsCart.AutoUpdateSubtotal();//执行更新小计
					ArrayList cartsInfo = (ArrayList) goodsCart.getAllCartInfo(); //查询结果list
					session.setAttribute("cartsInfo", cartsInfo);//把结果集发送给显示购物车的界面，重新更新cartInfo,不然返回购物车还是显示原来的
					if(result == 1 && result_Subtotal==1)
					{
						returnMessage =goods_name+ "的数量修改成功啦！";
						returnJson.put("returnMessage", returnMessage);
						returnJson.put("status", "success");					
						out.print(returnJson.toString());
					}
					else
					{
						returnMessage ="修改失败，发生未知错误！";
						returnJson.put("returnMessage", returnMessage);						
						out.print(returnJson.toString());					
					}
				}
				else//所选数量大于库存
				{
					returnMessage =goods_name+"   库存不足！";
					returnJson.put("returnMessage", returnMessage);						
					out.print(returnJson.toString());			
				}
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		
		/***************************  13-获取来自购物车界面的“移除此项”功能的请求申请 **********************************/
		else if(url.equals("移除此项"))  //13-获取来自购物车界面的“移除此项”功能的请求申请
		{
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			//首先获取货物ID
			String goods_id = request.getParameter("goods_id");
			//获取会话的用户ID
			String user_id = (String)session.getAttribute("userId");
			//创建购物车对象，对购物车进行操作
			GoodsCart goodsCart = new GoodsCart();               
			goodsCart.setUser_id(user_id);//设置用户ID
			goodsCart.setGoods_id(goods_id);;//设置货物ID			
			ArrayList cartsInfo = null;
			try 
			{	//开始执行删除操作
				String goods_name = goodsCart.getGoodsNameById();  //从javabean中获取货物名
				int result = goodsCart.deleteOneGoodsCartInfo();
				cartsInfo = (ArrayList) goodsCart.getAllCartInfo(); //查询结果list
				session.setAttribute("cartsInfo", cartsInfo);//把结果集发送给显示购物车的界面，重新更新cartInfo,不然返回购物车还是显示原来的
				if(result == 1)
				{
					returnMessage = goods_name+ "：成功从您的购物车移除！";
					returnJson.put("returnMessage", returnMessage);
					returnJson.put("status", "success");
					out.print(returnJson.toString());					
				}
				else
				{
					returnMessage ="操作失败！";
					returnJson.put("returnMessage", returnMessage);
					returnJson.put("status", "success");
					out.print(returnJson.toString());		
				}
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		/***************************  14-获取来自的“核算”功能的请求申请 **********************************/
		else if(url.equals("核算"))  //14-获取来自购物车界面的“核算”功能的请求申请
		{
			String user_id = (String)session.getAttribute("userId");
			double total_price = Double.parseDouble(session.getAttribute("total_price").toString());
			if(total_price > 0)  //订单总价大于0才能核算
			{
				//创建一个用户对象，取出里面的所有信息
				user_info user = new user_info();
				user.setUsername(user_id);
				try {
				Map userInfo = user.getUserinfo();
				session.setAttribute("userInfo_order", userInfo);
				session.setAttribute("total_price", total_price);
				request.getRequestDispatcher("CheckoutOrder-User-Info.jsp").forward(request, response);//请求转发用户信息		
				} 
				catch (ClassNotFoundException | SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				String message ="你还没选购哦！";
				session.setAttribute("message", message);
				response.sendRedirect("Goods-Cart-Demo.jsp");
			}
		}
		
		
		/***************************  15-获取确认订单信息中的  “保存”  功能的请求申请 **********************************/
		else if(url.equals("updateOrderInfo"))  //15-获取确认订单信息中的“保存”功能的请求申请
		{
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			
			/*********获取参数*********/
			String userId = (String)session.getAttribute("userId");
			String userTel = request.getParameter("userTel");
			String userEmail = request.getParameter("userEmail");
			String userAddress = request.getParameter("userAddress");
			String consignee = request.getParameter("consignee");  //收货人    没有保存就没有收货人.....所以在“下一步里面更新进去默认的用户名”
			session.setAttribute("consignee", consignee);//收货人先放到会话中
			double total_price = Double.parseDouble(session.getAttribute("total_price").toString()); //订单总价
			/*********获取参数*********/
			
			user_info user = new user_info();   //把这些信息更新到用户信息中
			user.setUsername(userId);
			user.setUser_tel(userTel);
			user.setUser_email(userEmail);
			user.setUser_address(userAddress);
			try {		
			int result = user.updateUserFromOrder();  //更新这些部分用户信息
			if(result == 1 )
			{
				Map userInfo = user.getUserinfo();
				session.setAttribute("userInfo_order", userInfo);//更新session里的userinfo
				
				returnMessage ="保存成功！";
				System.out.println(userId+": 修改收货信息");
				returnJson.put("returnMessage", returnMessage);
				returnJson.put("status", "success");					
				out.print(returnJson.toString());			
			}
			else
			{
				returnMessage ="保存失败，未知错误！";		
				returnJson.put("returnMessage", returnMessage);						
				out.print(returnJson.toString());
			}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		//2019-7-9    待完成-----完成订单后，把购物车的内容清空
		

		/***************************  16-获取确认订单信息中的“下一步”功能的请求申请 **********************************/
		else if(url.equals("订单下一步"))  //16-获取确认订单信息中的“下一步”功能的请求申请 
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			try 
			{
				GoodsCart cart = new GoodsCart();  //新建一个货物对象
				user_info user = new user_info();
				String user_id = (String)session.getAttribute("userId");
				cart.setUser_id(user_id);
				user.setUsername(user_id);
				
				Map userInfo = (HashMap)user.getUserinfo();
				String consignee = (String)session.getAttribute("consignee"); //判断用户是否保存了信息为收货人
				if(consignee==null)  //用户默认自己的姓名就是收货人
				{
					String user_name = (String)userInfo.get("user_name");
					session.setAttribute("consignee", user_name);//收货人默认为自己
				}
				ArrayList cartsInfo = (ArrayList) cart.getAllCartInfo(); //查询结果list
				session.setAttribute("cartsInfo", cartsInfo);//把结果集发送给显示货物的界面
				session.setAttribute("userInfo", userInfo);//把结果集发送给显示货物的界面
				returnJson.put("goUrl", "CheckoutOrder-Goods-Info.jsp");
				out.print(returnJson.toString());
				//response.sendRedirect("CheckoutOrder-Goods-Info.jsp");
				} 
				catch (ClassNotFoundException | SQLException e) 
				{
					e.printStackTrace();
				}  
		}
		
		/***************************  17-获取确认订单信息中的“提交订单”功能的请求申请 *****************************************/
		/*******************************需要额外把库存减去已选数量，更新回数据库*******************************/
		else if(url.equals("提交订单"))  //17-获取确认订单信息中的“提交订单”功能的请求申请
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			//这个功能目前需要修改一下订单状态，把信息加入历史订单数据库，然后清除购物车
			try {
			String userId = (String)session.getAttribute("userId");
			/**********开始更新订单数据库    创建类***********/
			user_info user = new user_info(); //创建一个用户类
			userOrder order = new userOrder();//创建一个订单类
			SelledOrderInfo selledOrder = new SelledOrderInfo();  //新建一个“我卖出的订单”类
			Goods goods = new Goods();  //创建一个货物类，用于更新库存
			GoodsCart goodsCart = new GoodsCart();   //这里新建了一个购物车的类     
			historyOrder_info historyOrder = new historyOrder_info();  //创建历史订单类
			/*********开始计算订单生成时间****************/
			String order_id = userId + order.creatNowTimeNumber();  //订单的类方法，数字时间
			session.setAttribute("order_id", order_id);  //在这里把order_id放到会话
			String order_time = order.creatNowTimeFormart();   //汉字时间
			/************结束计算时间***************/
			
			/************查询这笔订单购物车里的所有ID，连起来保存************/
			goodsCart.setUser_id(userId);//设置用户ID
			String include_goodsId = goodsCart.ConnectAllGoosdID();		
			/************查询这笔订单购物车里的所有ID，连起来保存************/
			
			/***************创建新订单*************/
			String consignee = (String)session.getAttribute("consignee");
			//从用户信息数据库中获取这个用户的信息
			user.setUsername(userId);
			Map userInfo = user.getUserinfo(); //获取到这条用户信息
			String user_name = (String)userInfo.get("user_name");
			String userTel = (String)userInfo.get("user_tel"); //购买者电话
			String userAddress = (String)userInfo.get("user_address");  //购买者地址
			double total_price = Double.parseDouble(session.getAttribute("total_price").toString()); //订单总价
			order.setUser_id(userId);
			order.setOrder_id(order_id);  //学号加时间日期
			order.setOrder_time(order_time);  
			order.setConsignee(consignee);
			order.setTel_num(userTel);
			order.setAddress(userAddress);
			order.setSum_account(total_price);
			order.setOrder_state("待收货");
			order.setInclude_goodsId(include_goodsId);
			int result_addOrder = order.addOrderInfo();
			/***************创建新订单*************/
			
			/**********更新历史查询订单数据库*******/
			//首先取出购物车的相关信息
			goodsCart.setUser_id(userId);//设置用户ID
			List goodsCartInfo = goodsCart.getAllCartInfo();  //找出购物车的所有信息
			String goods_id = "";
			String goods_name = "";
			int selectedQuantity = 0;
			double subtotal = 0.0;
			String goods_publisher = "";
			String purchase_record = "",goods_category = "";
			String publisher_id = "";
			String buyer_id="";
			int addSelledOrderResult = 0;
			for(Object goodsCart_Info:goodsCartInfo)   //一件货物就创建一个记录，记录着订单号的对应关系
			{
				Map goodscart = (HashMap)goodsCart_Info;
				goods_id = (String) goodscart.get("goods_id"); //1
				goods_name = (String) goodscart.get("goods_name");  //2
				selectedQuantity = Integer.parseInt(goodscart.get("selectedQuantity").toString());  //3
				subtotal = Double.parseDouble(goodscart.get("subtotal").toString());  //4
				goods_publisher = (String)goodscart.get("goods_publisher");  //5 (智能表需要的信息)
				goods_category = (String)goodscart.get("goods_category");//6(智能表需要的信息)
				historyOrder.setOrder_id(order_id);  //7   这个在上面生成了
				historyOrder.setGoods_id(goods_id);
				historyOrder.setGoods_name(goods_name);
				historyOrder.setSelectedQuantity(selectedQuantity);
				historyOrder.setSubtotal(subtotal);
				int result_addHistoryOrder = historyOrder.addOneOrderInfo();
				if(result_addHistoryOrder==1)
				{purchase_record = purchase_record + ";" + goods_name + ";" + goods_publisher + ";" + goods_category;}
				else
					return;
				/*****>>>>>  更新  “卖出的订单”  信息>>>>>>>*****/
				//首先找出这件商品的发布者id
				goods.setGoodsId(goods_id);  //设置商品id
				Map goodsInfo = goods.getGoodsInfo();  //找到这件商品的所有信息
				publisher_id = goodsInfo.get("publisher_id").toString();
				//buyer_id
				buyer_id = userId;
				//设置类属性
				selledOrder.setUser_id(publisher_id);//商品发布者的id，他的东西被买走
				selledOrder.setBuyer_id(buyer_id);
				selledOrder.setGoods_id(goods_id);
				selledOrder.setGoods_name(goods_name);
				selledOrder.setSelectedQuantity(selectedQuantity);
				selledOrder.setSubtotal(subtotal);
				selledOrder.setBuyer_tel(userTel);
				addSelledOrderResult = selledOrder.addOneSelledOrderInfo();
				/**<<<<<<<<<<<<“卖出的订单”<<<<<<<<<<<<**/
				
				/***********依次将商品库存-已选数量>>>>*****/				
				int goods_stock = Integer.parseInt((String) goodsInfo.get("goods_stock"));
				int New_stock = goods_stock - selectedQuantity;  //执行相减
				goods.setGoodsStock(New_stock);
				goods.updateGoodsStock();  //执行更新库存
				/***********依次将商品库存-已选数量<<<<<<*****/			
			}
			/**********更新历史查询订单数据库、卖出的商品数据库《《《《*******/
			if(addSelledOrderResult==1){System.out.println(userId+":添加“我卖出的”信息");}
			/**********删除这个用户的购物车的所有信息*******/
			 int result_delete = goodsCart.deleteAllGoodsCartInfo();  //删除此用户的所有购物车信息
			/**********删除这个用户的购物车的所有信息*******/
			
			
			if(result_addOrder == 1&& result_delete!=0)
			{
				/*更新智能推荐表----------------------------------------------------------*/	
				CollectInfoFor_IR CollectInfo = new CollectInfoFor_IR();  //实例化对象
				CollectInfo.setUser_id(userId); //初始化ID
				CollectInfo.Update_IR_Table(purchase_record, "purchase_record");  //高级复杂函数			
				/*结束更新智能推荐表----------------------------------------------------------*/
				try {
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
						IR.setUser_id(userId);
						List IR_Goods_Infos = IR.Generate_IR_GoodsId(keyArrays);  //传入关键词数组
						session.setAttribute("IR_Goods_Infos", IR_Goods_Infos);  //最终传入会话，在页面可以获取推荐的商品的所有信息
						
						} catch (Throwable e) {						
							e.printStackTrace();
					}						
					/*-----------------------------------------------------*/
				
				String message ="订单提交成功啦！";
				session.setAttribute("successMessage", message);
				
				String urlString = "http://localhost:8080/Alipay_Test/alipay.trade.page.pay.jsp?WIDout_trade_no="+
						order_id+"&WIDtotal_amount="+total_price+"&WIDsubject="+user_name;
				returnJson.put("goUrl", urlString);
				out.print(returnJson.toString());
				//response.sendRedirect(urlString);
			}
			else
			{
				String message ="订单提交发生未知错误！";
				session.setAttribute("message", message);
				response.sendRedirect("CheckoutOrder-Goods-Info.jsp");
			}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		/***************************  18-获取主页面中的“我的订单”功能的请求申请 *****************************************/
		else if(url.equals("我的订单"))  //18-获取主页面中的“我的订单”功能的请求申请 
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			try
			{
				/**********创建对象***********/
				user_info user = new user_info(); //创建一个用户类
				userOrder order = new userOrder();//创建一个订单类
				GoodsCart goodsCart = new GoodsCart();   //这里新建了一个购物车的类     
				/*************************/
				
				String userId = (String)session.getAttribute("userId");
				order.setUser_id(userId);	
				user.setUsername(userId);
				Map userInfo = user.getUserinfo();
				List HistoryOrderInfo = order.getAllOrderInfo();
				session.setAttribute("HistoryOrderInfo", HistoryOrderInfo);//设置到会话里   全部订单信息
				session.setAttribute("userInfo", userInfo);//设置到会话里 ，一条用户信息
				returnJson.put("goUrl", "History-Order-Demo.jsp");				
				out.print(returnJson.toString());	
				
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		
		/***************************  19-获取订单页面中的“订单详情”功能的请求申请 *****************************************/
		else if(url.equals("订单详情"))  //19-获取订单页面中的“订单详情”功能的请求申请 
		{
			try 
			{
				historyOrder_info historyOrder = new historyOrder_info();  //创建历史订单类
				String order_id = request.getParameter("order_id");
//				session.setAttribute("historyOrder_id", order_id);
				historyOrder.setOrder_id(order_id);				
				List HistoryOrder_infos = historyOrder.getOneOrderId_Info();
				userOrder userOrder = new userOrder();
				userOrder.setOrder_id(order_id);
				userOrder.setUser_id(session.getAttribute("userId").toString());
				String order_state = userOrder.getOneOrderInfo().get("order_state").toString();
				session.setAttribute("thisOrder_state", order_state);
				System.out.println("thisOrder_state:"+session.getAttribute("thisOrder_state"));////
				session.setAttribute("HistoryOrder_infos", HistoryOrder_infos);
				response.sendRedirect("HistoryOrder-Detail-Demo.jsp");
			} 
			catch (ClassNotFoundException | SQLException e) 
			{				
				e.printStackTrace();
			}
		}
		
	
		/***************************  20-获取订单详情页面中的“删除”功能的请求申请 *****************************************/
		else if(url.equals("删除订单"))  //20-获取订单详情页面中的“删除”功能的请求申请
		{
			try 
			{
				/**********创建对象************/
				historyOrder_info historyOrder = new historyOrder_info();  //创建历史订单类
				userOrder order = new userOrder();//创建一个订单类
				/**********创建对象************/
				String userId = (String)session.getAttribute("userId");
				String order_id = request.getParameter("order_id");
				System.out.println(userId+":正在删除订单："+order_id);
				/*******************************/
				historyOrder.setOrder_id(order_id);
				order.setOrder_id(order_id);	
				order.setUser_id(userId);
				int result_order = order.deleteOneOrderInfo();
				//int result_historyOrder = historyOrder.deleteOneOrderAllInfo();  //同时删除historyorder_info表里的信息
				List HistoryOrderInfo = order.getAllOrderInfo();
				session.setAttribute("HistoryOrderInfo", HistoryOrderInfo); //更新会话的订单信息
				
				if(result_order==1 )   
				{				
					String message ="订单删除成功啦！";
					session.setAttribute("successMessage", message);
					response.sendRedirect("History-Order-Demo.jsp");
				}
				else
				{
					String message ="订单删除失败，发生未知错误！";
					session.setAttribute("message", message);
					response.sendRedirect("History-Order-Demo.jsp");
				}
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/***************************  21-获取页面中的“清空收藏”功能的请求申请 *****************************************/
		else if(url.equals("清空收藏"))  //21-获取页面中的“清空收藏”功能的请求申请
		{
			try 
			{
				/*回调信息*/
				String returnMessage = "";
				/*回调json对象*/
				JSONObject returnJson = new JSONObject();
				
				WishList_Info WishList = new WishList_Info();
				String userId = (String)session.getAttribute("userId");
				WishList.setUser_id(userId);
				int result = WishList.deleteAllWishListInfo();
				List WiListInfos = WishList.getAllWishListInfo();	
				session.setAttribute("WiListInfos", WiListInfos);//修改全局会话
				if(result != 0)
				{
					returnMessage = "我的收藏已清空！";
					System.out.println(userId+": 清空所有收藏");
					returnJson.put("returnMessage", returnMessage);
					returnJson.put("status", "success");
					out.print(returnJson.toString());	
					
				}
				else
				{
					returnMessage = "你还没有收藏商品哦！";				
					returnJson.put("returnMessage", returnMessage);				
					out.print(returnJson.toString());	
				}
			}
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		/***************************  22-获取页面中的“移除一个收藏”功能的请求申请 *****************************************/
		else if(url.equals("移除一个收藏"))  //22-获取页面中的“移除一个收藏”功能的请求申请
		{
			try 
			{
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			
			WishList_Info WishList = new WishList_Info();
			String userId = (String)session.getAttribute("userId");
			String goods_id = request.getParameter("goods_id");
			String goods_name = request.getParameter("goods_name");
			WishList.setGoods_id(goods_id);
			WishList.setUser_id(userId);
			int result = WishList.deleteOneWishListInfo();
			List WiListInfos = WishList.getAllWishListInfo();	
			session.setAttribute("WiListInfos", WiListInfos);//修改全局会话
			if(result==1)
			{
				returnMessage = goods_name+" :消失啦！";
				System.out.println(userId+": 移除收藏："+goods_name);
				returnJson.put("returnMessage", returnMessage);
				returnJson.put("status", "success");
				out.print(returnJson.toString());			
			}
			else
			{
				returnMessage =goods_name+": 移除失败，未知错误！";	
				returnJson.put("returnMessage", returnMessage);	
				out.print(returnJson.toString());	
				
			}
			}
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
			
		}
		
		
		/***************************  23-获取页面中的“发布商品”功能的请求申请 *****************************************/
		else if(url.equals("发布商品"))  //23-获取页面中的“发布商品”功能的请求申请
		{
		try 
		{
			/************创建对象************/
			Goods goods = new Goods();
			userOrder time = new userOrder();  //纯粹用于计算时间
			/*******************************/
			/************获取参数************/
			String userId = (String)session.getAttribute("userId");
			String goods_publisher = (String)session.getAttribute("userName");
			String goods_name = request.getParameter("goods_name");
			double goods_price = Double.parseDouble(request.getParameter("goods_price"));
			int goods_stock = Integer.parseInt(request.getParameter("goods_stock"));
			String goods_category = request.getParameter("goods_category");
			String goods_describe = request.getParameter("goods_describe");
			String goods_issuDate = time.creatNowTimeFormart();  //获取发布时间
			/*******************************/	
			/***********计算goods_id，使用时间戳吧************/
			Random random = new Random();
			int ends = random.nextInt(999);//生成三位数随机数小标
			
			Date date = new Date();
			String goods_id = ""+date.getTime()+"_"+String.format("%03d",ends);			
			/***********************************/
			/**********更新货物数据库，新增一条信息********/
			goods.setGoodsId(goods_id);
			goods.setGoodsName(goods_name);
			goods.setPublisher_id(userId);
			goods.setGoodsPublisher(goods_publisher);
			goods.setGoodsIssuDate(goods_issuDate);
			goods.setGoodsCategory(goods_category);
			goods.setGoodsDescribe(goods_describe);
			goods.setGoodsPrice(goods_price);
			goods.setGoodsStock(goods_stock);
			int result = goods.addOneGoodsInfo();
			if(result == 1)
			{
				/*处理上传图片的功能*/
				String goodsPics = request.getParameter("goodsPictures") == null ? "" : request.getParameter("goodsPictures").toString().trim();
				if(!goodsPics.equals("")){
					String [] goodPicArr =  goodsPics.split(",");
					for (String pic : goodPicArr) {
						GoodsPicture goodsPicture = new GoodsPicture();
						goodsPicture.setGoods_id(goods.getGoodsId());
						goodsPicture.setProduct_image(pic);
						goodsPicture.addOneGoodsPicture();
					}
				}
				String message ="您的二手商品信息发布成功，请留意商城！";
				session.setAttribute("successMessage", message);
				response.sendRedirect("Publish-Goods.jsp");
			}
			else
			{
				String message ="二手信息发布失败，未知错误！";
				session.setAttribute("message", message);
				response.sendRedirect("Publish-Goods.jsp");
			}
			
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		
		}
		
		
								//注意运用关联查询
		
		/***************************  24-获取页面中的“我的发布”功能的请求申请 *****************************************/
		else if(url.equals("我的发布"))  //24-获取页面中的“我的发布”功能的请求申请
		{
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
		try 
		{
			String publisher_id = (String)session.getAttribute("userId");  //获取此用户的姓名作为发布者
			/************创建对象************/
			Goods goods = new Goods();
			/*******************************/
			goods.setPublisher_id(publisher_id);
			List myPublish_infos = goods.getGoodsInfoByPublisherId();			
			session.setAttribute("myPublish_infos", myPublish_infos);
			returnJson.put("goUrl", "My-Publish_Infos-Demo.jsp");
			out.print(returnJson.toString());
		
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			
				e.printStackTrace();
		}
		}
		
		
		
		
		/***************************  25-获取页面中的“下架商品”功能的请求申请 *****************************************/
		else if(url.equals("下架商品"))  //25-获取页面中的“下架商品”功能的请求申请
		{
			try 
			{
				//分页，sql语句    select * from limit 2,3;   从第二条开始，往后数三条记录   
				//   动态sql语句，使用  if set  chose
				/*回调信息*/
				String returnMessage = "";
				/*回调json对象*/
				JSONObject returnJson = new JSONObject();
				String goods_id = request.getParameter("goods_id");
				String publisher_id = (String)session.getAttribute("userId");  //获取此用户的姓名作为发布者
				/**********/
				Goods goods = new Goods();
				goods.setGoodsId(goods_id);
				goods.setPublisher_id(publisher_id);
				String goods_name = goods.getGoodsNikename();  //得到此件货物名
				int result_del = goods.deleteGoodsOne();   //执行数据库删除
				
				if(result_del == 1)
				{			
					List myPublish_infos = goods.getGoodsInfoByPublisherId();			
					session.setAttribute("myPublish_infos", myPublish_infos);   //更新会话
					returnMessage =goods_name+"已下架！";
					returnJson.put("returnMessage", returnMessage);
					returnJson.put("status", "success");
					out.print(returnJson.toString());
					
				}
				else
				{
					returnMessage =goods_name+"下架失败，未知错误！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());
				}
				
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				
					e.printStackTrace();
			}
		}

		/***************************  26-查看商品详情 *****************************************/
		else if(url.equals("商品详情"))  //26-获取页面中的“查看商品详情”功能的请求申请
		{
		try {
				String goods_id = request.getParameter("goods_id");// 货物ID
				Goods goods = new Goods();				
				goods.setGoodsId(goods_id);				
				Map goodsInfo = goods.getGoodsInfo();//获取到一条货物信息
				
				/*取出商品图片*/
				GoodsPicture goods_pictures = new GoodsPicture();  //实例化商品图片
				goods_pictures.setGoods_id(goods_id);
				List<String> goodsPictures_list = goods_pictures.getMultipleGoodsPictures(); //取出这件商品的所有图片了
				session.setAttribute("goodsPictures_list", goodsPictures_list);//把结果集发送给显示货物的界面
				/*结束取出商品图片*/
			
				/*更新智能推荐表----------------------------------------------------------*/
				  
				String goods_name = (String)goodsInfo.get("goods_name");
				String goods_publisher = (String)goodsInfo.get("goods_publisher");
				String goods_category = (String)goodsInfo.get("goods_category");
				String userId = (String)session.getAttribute("userId"); //用户id 
				String viewDetails_record = ""+goods_name+";"+goods_publisher+";"+goods_category;
				CollectInfoFor_IR CollectInfo = new CollectInfoFor_IR();  //实例化对象
				CollectInfo.setUser_id(userId); //初始化ID
				CollectInfo.Update_IR_Table(viewDetails_record, "viewDetails_record");  //高级复杂函数，更新信息收集表			
				/*结束更新智能推荐表----------------------------------------------------------*/
				try {
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
						IR.setUser_id(userId);
						List IR_Goods_Infos = IR.Generate_IR_GoodsId(keyArrays);  //传入关键词数组
						session.setAttribute("IR_Goods_Infos", IR_Goods_Infos);  //最终传入会话，在页面可以获取推荐的商品的所有信息
						
						} catch (Throwable e) {						
							e.printStackTrace();
					}						
					/*-----------------------------------------------------*/
				
				session.setAttribute("goodsInfo", goodsInfo);//把结果集发送给显示货物的界面
				response.sendRedirect("OneGoods-Detail-Demo.jsp"); 				
			} 
		catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		}
		
		/***************************  27-查看用户信息，用于即时通讯*****************************************/
		else if(url.equals("userInfo"))  //查询用户信息
		{
			String jsonObj = "";
			Map<String,Object> resultMap = new HashMap<String,Object>();
			try {
				response.setContentType("text/json;charset=utf-8");
				out = response.getWriter();
				String publisher_id = request.getParameter("publisher_id").toString();   //直接获得发布者的ID
				Map<String,Object> dataMap = new HashMap<String,Object>();  //创建数据的json格式
				String username = "";//用户名
				
				user_info userInfo = new user_info();  //创建用户对象，获取用户ID
				userInfo.setUsername(publisher_id);  //直接利用user_id查找用户信息
				Map userMap = userInfo.getUserinfo();
				username = userMap.get("user_name").toString();  //从找到的这条 用户信息中获取了 user_name
				dataMap.put("name",username);//昵称
				dataMap.put("id",publisher_id);//ID
				
				
				dataMap.put("type","friend");
				//头像
				dataMap.put("avatar","http://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg");
				resultMap.put("data",dataMap);
				resultMap.put("status", "success");
				jsonObj = JSONObject.fromObject(resultMap).toString();
				out.print(jsonObj);
			}catch (Exception e) {
				resultMap.put("status", "failed");
				jsonObj = JSONObject.fromObject(resultMap).toString();
				out.print(jsonObj);
			}finally {
				if (out != null) {
					out.flush();
					out.close();
				}
			}
		}
		
		/***************************  28-获得轮播图和最新发布的3个商品 *****************************************/
		else if("getAllRotationChart".equals(url)){
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			System.out.println("获取轮播图");
			GoodsPicture goods_picture = new GoodsPicture();
			Goods Goods = new Goods();
			/*获取轮播图*/
			try {
			/*封装了获取轮播图的方法getAllRotationChart*/
			List RotationChartList = goods_picture.getAllRotationChart();
			/*获取最新发布的三件商品*/
			List LastestGoodsList = Goods.getLastestGoods();
			session.setAttribute("RotationChartList", RotationChartList);
			session.setAttribute("LastestGoodsList", LastestGoodsList);
			returnJson.put("goUrl", "index.jsp");
			out.print(returnJson.toString());
			
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
			
		/***************************  29-请求通知公告 *****************************************/
		else if("通知公告".equals(url)){
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			AnnouncementInfo announcement = new AnnouncementInfo();
			 
			try {
			List announcementList = announcement.getAllAnnouncementInfo();			
			session.setAttribute("announcementList", announcementList);
			returnJson.put("goUrl", "Announcement-Info-Demo.jsp");
			out.print(returnJson.toString());
			
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/***************************  30-获取“我卖出的”功能的请求申请 ***************************/
		else if(url.equals("我卖出的"))  
		{
			try
			{
				/**********创建对象***********/
				user_info user = new user_info(); //创建一个用户类
				SelledOrderInfo SelledOrder = new SelledOrderInfo();  //我卖出的类
				/*************************/
				
				String userId = (String)session.getAttribute("userId");
				SelledOrder.setUser_id(userId);	
				user.setUsername(userId);
				Map userInfo = user.getUserinfo();
				List SelledOrderList = SelledOrder.getSelledOrderInfo_ById();
				session.setAttribute("SelledOrderList", SelledOrderList);//设置到会话里   全部订单信息
				session.setAttribute("userInfo", userInfo);//设置到会话里 ，一条用户信息
				response.sendRedirect("MySelled-Order-Demo.jsp");
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		/***************************  31-在发布界面修改库存 ***************************/
		else if("updateStock".equals(url)){
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			
			String goods_id = request.getParameter("goods_id").toString(); //获取商品ID
			int goods_stock =Integer.parseInt(request.getParameter("goods_stock").toString());
			Goods Goods = new Goods();
			Goods.setGoodsId(goods_id);
			Goods.setGoodsStock(goods_stock);			
			try {
				if(Goods.updateGoodsStock()==1){   //成功修改一条库存，需要更新会话信息，否则库存显示不会改变
					String publisher_id = (String)session.getAttribute("userId");
					Goods.setPublisher_id(publisher_id);
					List myPublish_infos = Goods.getGoodsInfoByPublisherId();			
					session.setAttribute("myPublish_infos", myPublish_infos);
					
					returnMessage ="库存修改成功！";
					returnJson.put("returnMessage", returnMessage);
					returnJson.put("status", "success");
					out.print(returnJson.toString());			
				}
				else{
					returnMessage ="库存修改失败，未知错误！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/***************************  32-订单确认收货 ***************************/
		else if("确认收货".equals(url)){
			/*回调信息*/
			String returnMessage = "";
			/*回调json对象*/
			JSONObject returnJson = new JSONObject();
			String user_id = (String)session.getAttribute("userId");
			String order_id = request.getParameter("order_id").toString();
			userOrder userOrder = new userOrder();
			userOrder.setUser_id(user_id);
			userOrder.setOrder_id(order_id);
			userOrder.setOrder_state("已完成");
			
			try {
				if(userOrder.updateOrderState()==1){
					//更新会话的订单状态信息
					String order_state = userOrder.getOneOrderInfo().get("order_state").toString();
					session.setAttribute("thisOrder_state", order_state);
					
					returnMessage = "订单收货成功，此订单已完成！";
					returnJson.put("returnMessage", returnMessage);
					returnJson.put("status", "success");
					out.print(returnJson.toString());
				}
				else{
					returnMessage ="确认收货失败，未知错误！";
					returnJson.put("returnMessage", returnMessage);
					out.print(returnJson.toString());
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
	} //doPost end
}// class end


