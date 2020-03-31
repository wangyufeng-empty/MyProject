package beans;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishList_Info {
	//以下属性和数据库属性顺序一致
	private String user_id;//user_id
	private String goods_id = null;//goods_id
	private String goods_name = null;	//goods_name	
	private String goods_category = null;//goods_category  种类
	int goods_stock = 0;
	double goods_price = 0;
	private DBUtil db;//定义一个数据库对象
	
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public void setGoods_category(String goods_category) {
		this.goods_category = goods_category;
	}
	public void setGoods_stock(int goods_stock) {
		this.goods_stock = goods_stock;
	}
	public void setDb(DBUtil db) {
		this.db = db;
	}
	
	//查询所有收藏信息              返回list对象  到时候要用map 一个个循环取出来   p154
	public List getAllWishListInfo() throws ClassNotFoundException, SQLException
	{
		List WishListInfo = null;
		String sql = "select * from user_favorites where user_id=?";
		String[] params = {user_id};
		DBUtil db = new DBUtil();
		db.getConnection();
		
		WishListInfo = db.getList(sql, params);
		db.close();
		return WishListInfo;
	}
	
	//通过货物编号和用户ID返回一条购物车信息
	public Map getOnesWishListInfo() throws ClassNotFoundException, SQLException{
		Map WishListInfo = null;
		String sql = "select * from user_favorites where user_id=? and goods_id=?";
		String[] params = {user_id,goods_id};
		
		DBUtil db = new DBUtil();
		db.getConnection();   //所有的方法都要先与数据库建立连接
		
		WishListInfo = db.getMap(sql, params);
		db.close();
		return WishListInfo;
	}
	
	//删除一条购收藏信息    返回受影响的数量
	public int deleteOneWishListInfo() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "delete from user_favorites where user_id=? and goods_id=?";
		String[] params = {user_id,goods_id};
		
		DBUtil db = new DBUtil();
		db.getConnection();
		
		result = db.update(sql, params);
		db.close();
		return result;
	}
	
	//删除所有收藏货物信息    返回受影响的数量
	public int deleteAllWishListInfo() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "delete from user_favorites where user_id=?";
		String[] params = {user_id};
		
		DBUtil db = new DBUtil();
		db.getConnection();
		
		result = db.update(sql, params);
		db.close();
		return result;
	}
	
	
	//添加一条收藏信息
	public int addWishListInfo() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "insert into user_favorites values(?,?,?,?,?,?)";
		Object[] params = {user_id,goods_id,goods_name,goods_category,goods_stock,goods_price};
		
		DBUtil db = new DBUtil();
		db.getConnection();
		
		result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   复合更新
		db.close();
		return result;
	}
	
	//检查是否已经收藏了这件商品，如果选了则返回true，否则返回false            !!!!!!!!!!重要!!!!!!!!!
	public boolean isGoodsWished() throws ClassNotFoundException, SQLException
	{
		boolean state = false;
		HashMap WishInfo = (HashMap)this.getOnesWishListInfo();
		if(WishInfo!=null) //如果此条信息不为空，即存在
		{
			state = true;   //用户已经选了这件商品
		}
		
		return state;
	}
			
	
	
	
	
	
	
}
