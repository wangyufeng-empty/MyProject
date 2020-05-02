package beans;
import java.sql.SQLException;
import java.util.*;

public class Goods {                  //商品类
	//以下属性和数据库属性顺序一致
		private String goodsId;//
		private String goodsId_db;  //新ID
		private String goodsName = null;
		private String goodsPublisher = null;	//商品的发布者
		private String goodsIssuDate = null;    //商品的发布日期
		private String goodsCategory = null;    //商品的分类
		private String goodsDescribe = null;   //商品描述
		private double goodsPrice = 0;       //商品的价格，注意是double类型
		private int goodsStock = 0;          //商品的库存，注意是int类型
		

		public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
		
		
		public void setGoodsId_db(String goodsId_db) {
			this.goodsId_db = goodsId_db;
		}

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public String getGoodsPublisher() {
			return goodsPublisher;
		}

		public void setGoodsPublisher(String goodsPublisher) {
			this.goodsPublisher = goodsPublisher;
		}

		public String getGoodsIssuDate() {
			return goodsIssuDate;
		}

		public void setGoodsIssuDate(String goodsIssuDate) {
			this.goodsIssuDate = goodsIssuDate;
		}

		public String getGoodsCategory() {
			return goodsCategory;
		}

		public void setGoodsCategory(String goodsCategory) {
			this.goodsCategory = goodsCategory;
		}
		
		public String getGoodsDescribe() {
			return goodsDescribe;
		}

		public void setGoodsDescribe(String goodsDescribe) {
			this.goodsDescribe = goodsDescribe;
		}

		public double getGoodsPrice() {
			return goodsPrice;
		}

		public void setGoodsPrice(double goodsPrice) {
			this.goodsPrice = goodsPrice;
		}

		public int getGoodsStock() {
			return goodsStock;
		}

		public void setGoodsStock(int goodsStock) {
			this.goodsStock = goodsStock;
		}

		public DBUtil getDb() {
			return db;
		}

		public void setDb(DBUtil db) {
			this.db = db;
		}

		//通过货物编号返回一条货物信息
		public Map getGoodsInfo() throws ClassNotFoundException, SQLException{
			Map goodsinfo = null;
			String sql = "select * from goods_info where goods_id=?";//数据库里面的user_id就是userName
			String[] params = {goodsId};
			
//			DBUtil db = new DBUtil();
//			db.getConnection();   //所有的方法都要先与数据库建立连接
			
			goodsinfo = db.getMap(sql, params);
			db.close();
			return goodsinfo;
		}
		
		//通过货物发布者返回N条货物信息,用于用户查看“我的发布”
		public List getGoodsInfoByGoodsPublisher() throws ClassNotFoundException, SQLException{
			List goodsInfo = null;
			String sql = "select * from goods_info where goods_publisher=?";
			String[] params = {goodsPublisher};
//			
//			DBUtil db = new DBUtil();
//			db.getConnection();   //所有的方法都要先与数据库建立连接
			
			goodsInfo = db.getList(sql, params);
			db.close();
			return goodsInfo;
		}
		
		//通过货物类别返回N条货物信息
		public List getGoodsInfoByCategory() throws ClassNotFoundException, SQLException{
			List goodsinfo = null;
			String sql = "select * from goods_info where goods_category=?";//数据库里面的user_id就是userName
			String[] params = {goodsCategory};
			
//			DBUtil db = new DBUtil();
//			db.getConnection();   //所有的方法都要先与数据库建立连接
//			
			goodsinfo = db.getList(sql, params);
			db.close();
			return goodsinfo;
		}
		
		/****************************通过关键字搜索返回n条货物信息   !!!!!!!!!!!!!!!!!!!!!!!!!!!!亮点***************/
		public List getGoodsInfoByKey() throws ClassNotFoundException, SQLException{
			List goodsinfo = null;
			String sql = "select * from goods_info where concat(goods_name,goods_publisher,goods_category,goods_describe)  like ?";//数据库里面的user_id就是userName
			String[] params = {"%"+goodsName+"%"};   //在传递参数的时候进行模糊搜索，加%%
//			
//			DBUtil db = new DBUtil();
//			db.getConnection();   //所有的方法都要先与数据库建立连接
			
			goodsinfo = db.getList(sql, params);
			db.close();
			return goodsinfo;
		}
		
		//查询所有货物信息              返回list对象  到时候要用map 一个个循环取出来   p154
		public List getAllGoodsInfo() throws ClassNotFoundException, SQLException
		{
			List Goods = null;
			String sql = "select * from goods_info";
			
//			DBUtil db = new DBUtil();
//			db.getConnection();
//			
			Goods = db.getList(sql, null);
			db.close();
			return Goods;
		}
				
	
		//返回货物名，字符串
		public String getGoodsNikename() throws ClassNotFoundException, SQLException{
			String goodsname = null;
			HashMap goodsinfo = (HashMap)this.getGoodsInfo();
			if(goodsinfo!=null) //如果此条信息不为空，即存在
			{
				goodsname = (String)goodsinfo.get("goods_name");
			}
			else  //不存在
				goodsname = null;
			this.goodsName = goodsname;   //防止函数调用错误
			return goodsname;
		}
			
		//返回库存，返回一个数值
		public int getGoodStock() throws ClassNotFoundException, SQLException{
			int goodsstock =0;
			HashMap goodsinfo = (HashMap)this.getGoodsInfo();
			if(goodsinfo!=null) //如果此条信息不为空，即存在
			{
				goodsstock = (int)goodsinfo.get("goods_stock");
			}
			else  //不存在
				goodsstock = 0;
			this.goodsStock = goodsstock;
			return goodsstock;
		}
		
		//添加货物信息
		public int addOneGoodsInfo() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "insert into goods_info values(?,?,?,?,?,?,?,?)";
			Object[] params = {goodsId,goodsName,goodsPublisher,goodsIssuDate,goodsCategory,goodsDescribe,goodsPrice,goodsStock};
//			
//			DBUtil db = new DBUtil();
//			db.getConnection();
			
			result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   ****************这里采用的是复合型数组，参数回填更加灵活！******************
			db.close();
			return result;
		}
		
		//修改货物信息(一整条记录)  返回受影响的结果数量
		public int updateUser() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			/***************不能修改货物的ID******************/
			String sql = "update goods_info set goods_name=?,goods_publisher=?,goods_issuDate=?,goods_category=?,goods_describe=?,goods_price=?,goods_stock=? where goods_id=?"; 
			Object[] params = {goodsName,goodsPublisher,goodsIssuDate,goodsCategory,goodsDescribe,goodsPrice,goodsStock,goodsId};
			
//			DBUtil db = new DBUtil();
//			db.getConnection();
				
			result = db.updateComplex(sql, params);    // ****************这里采用的是复合型数组，参数回填更加灵活！******************/
			db.close();
			return result;
		}
		
		//单独修改库存，用于提交订单后，库存修改，或者管理员修改               2019-7-2
		public int updateGoodsStock() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update goods_info set goods_stock=? where goods_id=?";
			Object[] params ={goodsStock,goodsId};
			
//			DBUtil db = new DBUtil();
//			db.getConnection();
//			
			result = db.updateComplex(sql, params);
			db.close();
			return result;
		}
		
		//单独修改货物id   ,用于下架商品时的ID变化
		public int updateGoodsID() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update goods_info set goods_id=? where goods_id=?";
			Object[] params ={goodsId_db,goodsId};
//			
//			DBUtil db = new DBUtil();
//			db.getConnection();
			
			result = db.updateComplex(sql, params);
			db.close();
			return result;
		}
		
		//删除货物信息(一整条记录)  返回受影响的数量      (可以用户库存为0时)
		public int deleteGoodsOne() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "delete from goods_info where goods_id=?";
			String[] params = {goodsId};
			
//			DBUtil db = new DBUtil();
//			db.getConnection();
			
			result = db.update(sql, params);
			db.close();
			return result;
		}
		
		//检查库存是否大于零，是则返回true，否则返回false            !!!!!!!!!!重要!!!!!!!!!
		public boolean isStock_GT_0() throws ClassNotFoundException, SQLException
		{
			boolean state = false;
			int stock = this.getGoodStock();   //得到库存
			if(stock > 0)
			{
				state = true;
			}
			return state;
		}

}
