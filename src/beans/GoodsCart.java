package beans;
import java.sql.SQLException;
import beans.Goods;
import java.util.*;

                                   /*******************************************创建一个购物车类**************************************/
public class GoodsCart {                          
	//以下属性和数据库属性顺序一致
		private String user_id;//user_id
		private String goods_id = null;//goods_id
		private String goods_name = null;	//goods_name
		private String goods_publisher = null;    //goods_publisher  发布者
		private String goods_category = null;//goods_category  种类
		private double goods_price = 0;//goods_price
		private int selectedQuantity = 0; //selectedQuantity  已选数量
		private double subtotal = 0;  //这件商品的小计价格
		public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
		
		
		/*************************一堆get和set方法***************************/
		
		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getGoods_id() {
			return goods_id;
		}

		public void setGoods_id(String goods_id) {
			this.goods_id = goods_id;
		}

		public String getGoods_name() {
			return goods_name;
		}

		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}

		public String getGoods_publisher() {
			return goods_publisher;
		}

		public void setGoods_publisher(String goods_publisher) {
			this.goods_publisher = goods_publisher;
		}

		public String getGoods_category() {
			return goods_category;
		}

		public void setGoods_category(String goods_category) {
			this.goods_category = goods_category;
		}

		public double getGoods_price() {
			return goods_price;
		}

		public void setGoods_price(double goods_price) {
			this.goods_price = goods_price;
		}

		public int getSelectedQuantity() {
			return selectedQuantity;
		}

		public void setSelectedQuantity(int selectedQuantity) {
			this.selectedQuantity = selectedQuantity;
		}

		public DBUtil getDb() {
			return db;
		}

		public void setDb(DBUtil db) {
			this.db = db;
		}
		
		public double getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(double subtotal) {
			this.subtotal = subtotal;
		}

		//查询所有购物车信息              返回list对象  到时候要用map 一个个循环取出来   p154
		public List getAllCartInfo() throws ClassNotFoundException, SQLException
		{
			List GoodsCart = null;
			String sql = "select * from goodsCart_info where user_id=?";
			String[] params = {user_id};
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			GoodsCart = db.getList(sql, params);
			db.close();
			return GoodsCart;
		}

		//通过货物编号和用户ID返回一条购物车信息
		public Map getOneGoodsCartInfo() throws ClassNotFoundException, SQLException{
			Map goodscartinfo = null;
			String sql = "select * from goodsCart_info where user_id=? and goods_id=?";
			String[] params = {user_id,goods_id};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/   //所有的方法都要先与数据库建立连接
			
			goodscartinfo = db.getMap(sql, params);
			db.close();
			return goodscartinfo;
		}
		
		//通过账号和货物id返回  "货物名"   返回一个字符串
		public String getGoodsNameById() throws ClassNotFoundException, SQLException{
			String goodsname =null;
			HashMap CartInfo = (HashMap)this.getOneGoodsCartInfo();
			if(CartInfo!=null) //如果此条信息不为空，即存在
			{
				goodsname = (String) CartInfo.get("goods_name");
			}
			return goodsname;
		}
		
		//通过账号和货物id返回  "已选数量"   返回一个数值	
		public int getselectedQuantity() throws ClassNotFoundException, SQLException{
			int selectedQuantity =0;
			HashMap CartInfo = (HashMap)this.getOneGoodsCartInfo();
			if(CartInfo!=null) //如果此条信息不为空，即存在
			{
				selectedQuantity = Integer.parseInt((String) CartInfo.get("selectedQuantity"));
			}
			else  //不存在
				selectedQuantity = 0;
			this.selectedQuantity = selectedQuantity;
			return selectedQuantity;
		}
		
		//通过账号和货物id返回  "小计"   返回一个double数值	
		public double getSubtotalById() throws ClassNotFoundException, SQLException{
			double Subtotal =0;
			HashMap CartInfo = (HashMap)this.getOneGoodsCartInfo();
			if(CartInfo!=null) //如果此条信息不为空，即存在
			{
				Subtotal = Double.parseDouble((String) CartInfo.get("subtotal"));
			}
			
			this.subtotal = Subtotal;
			return Subtotal;
		}
		
		//添加一条购物车信息
		public int addCartInfo() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "insert into goodsCart_info values(?,?,?,?,?,?,?,?)";
			Object[] params = {user_id,goods_id,goods_name,goods_publisher,goods_category,goods_price,selectedQuantity,subtotal};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   复合更新
			db.close();
			return result;
		}
		
		
		//单独修改 :   "已选数量"
		public int updateSelectedQuantity() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update goodsCart_info set selectedQuantity=? where user_id=? and goods_id=?";
			Object[] params ={selectedQuantity,user_id,goods_id};
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			result = db.updateComplex(sql, params);
			db.close();
			return result;
		}
		
		//单独修改 :   "小计 subtotal"
		public int updateSubtotal() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update goodsCart_info set subtotal=? where user_id=? and goods_id=?";
			Object[] params ={subtotal,user_id,goods_id};
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			result = db.updateComplex(sql, params);
			db.close();
			return result;
		}
		
		//自动更新 :   "小计 subtotal"  只需要传递 user_id=? and goods_id=?  在修改完选货数量后调用
		public int AutoUpdateSubtotal() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			Map goodsCartOne = this.getOneGoodsCartInfo();  //  user_id=? and goods_id=?
			double goodsPrice = Double.parseDouble(goodsCartOne.get("goods_price").toString());  //获取价格
			int selected_Quantity = Integer.parseInt(goodsCartOne.get("selectedQuantity").toString());  //获取已选数量
			double Subtotal = goodsPrice * selected_Quantity;
			String sql = "update goodsCart_info set subtotal=? where user_id=? and goods_id=?";
			Object[] params ={Subtotal,user_id,goods_id};
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			result = db.updateComplex(sql, params);
			db.close();
			return result;
		}
		
		//删除一条购物车货物信息    返回受影响的数量
		public int deleteOneGoodsCartInfo() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "delete from goodsCart_info where user_id=? and goods_id=?";
			String[] params = {user_id,goods_id};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			result = db.update(sql, params);
			db.close();
			return result;
		}
		
		//删除所有购物车货物信息    返回受影响的数量
		public int deleteAllGoodsCartInfo() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "delete from goodsCart_info where user_id=?";
			String[] params = {user_id};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			result = db.update(sql, params);
			db.close();
			return result;
		}
		
		//检查已选数量是否大于这件商品的库存   如果已选数量大于库存，则返回true，否则返回false            !!!!!!!!!!重要!!!!!!!!!
		public boolean isSelectedQuantity_GT_stock() throws ClassNotFoundException, SQLException
		{
			boolean state = false;
			Goods goods = new Goods();  //创建一个货物对象，查找其库存
			goods.setGoodsId(goods_id);  //把good_id传进去，找到对应的哪个货物
			int stock = goods.getGoodStock();   //得到库存
			int selectedQuantity = this.getselectedQuantity();   //得到已选数量
			if(selectedQuantity > stock)
			{
				state = true;
			}
			return state;
		}
		
		//检查是否已经选了这件商品，如果选了则返回true，否则返回false            !!!!!!!!!!重要!!!!!!!!!
		public boolean isGoodsSelected() throws ClassNotFoundException, SQLException
		{
			boolean state = false;
			HashMap CartInfo = (HashMap)this.getOneGoodsCartInfo();
			if(CartInfo!=null) //如果此条信息不为空，即存在
			{
				state = true;   //用户已经选了这件商品
			}
			
			return state;
		}
		
		//连接所有的货物id
		public String ConnectAllGoosdID() throws ClassNotFoundException, SQLException
		{
			String conn_goodsId = "";
			ArrayList GoodsCart = (ArrayList)this.getAllCartInfo();
			for(Object cartInfo:GoodsCart)
			{
				Map cart_Info = (HashMap)cartInfo;
				conn_goodsId = conn_goodsId + cart_Info.get("goods_id")+";";
			}
			return conn_goodsId;
		}
}










