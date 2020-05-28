package beans;
import java.sql.SQLException;
import beans.Goods;
import java.util.*;

public class historyOrder_info {
	//以下属性和数据库属性顺序一致
	private String order_id;//user_id
	private String goods_id = null;//goods_id
	private String goods_name = null;	//goods_name
	private int selectedQuantity = 0; //selectedQuantity  已选数量
	private double subtotal = 0;  //这件商品的小计价格
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public int getSelectedQuantity() {
		return selectedQuantity;
	}
	public void setSelectedQuantity(int selectedQuantity) {
		this.selectedQuantity = selectedQuantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public DBUtil getDb() {
		return db;
	}
	public void setDb(DBUtil db) {
		this.db = db;
	}
	
	//通过订单ID返回n条货物的具体信息，返回订单ID、商品名、数量、商品ID、小计、发布者、分类、描述
	public List getOneOrderId_Info() throws ClassNotFoundException, SQLException{
		List orderInfo = null;
		String sql = "select * from historyOrder_info where order_id=?";
		String[] params = {order_id};
		orderInfo = db.getList(sql, params);
		
		/*>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
		//循环取出每一件
		List goodsinfoList = new ArrayList<>();
		GoodsPicture GoodsPicture = new GoodsPicture();
		Goods Goods = new Goods();  //取商品的一些信息
		
		String product_image = null;
		String goods_id = null;
		for(Object goods_info : orderInfo){
			Map<String, String> goodsInfo = (HashMap)goods_info;
			goods_id = goodsInfo.get("goods_id").toString();		
			//取出商品图片
			product_image = GoodsPicture.getFirstGoodsPictures_ById(goods_id);
			goodsInfo.put("product_image", product_image);
			/*获取发布者、分类、描述*/
			Goods.setGoodsId(goods_id);
			goodsInfo.put("goods_publisher", Goods.getGoodsInfo().get("goods_publisher").toString());
			goodsInfo.put("goods_category", Goods.getGoodsInfo().get("goods_category").toString());
			String goods_describe = Goods.getGoodsInfo().get("goods_describe").toString();
			if(goods_describe.length()>40){//字符串过长，用省略号替换
				goods_describe = goods_describe.substring(0, 45)+"......";
			}
			goodsInfo.put("goods_describe", goods_describe);
			
			goodsinfoList.add(goodsInfo);
		}
		/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
		
		
		db.close();
		return goodsinfoList;
	}
	
	
	//添加一条订单查询详细信息
	public int addOneOrderInfo() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "insert into historyOrder_info values(null,?,?,?,?,?)";
		Object[] params = {order_id,goods_id,goods_name,selectedQuantity,subtotal};
		
		/*DBUtil db = new DBUtil();*/
		/*db.getConnection();*/
		
		result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   复合更新
		db.close();
		return result;
	}
	
	
	//删除同一个订单ID的所有信息         返回受影响的数量
	public int deleteOneOrderAllInfo() throws ClassNotFoundException, SQLException
	{
		
		String sql = "delete from historyorder_info where order_id=?";
		String[] params = {order_id};
		
		/*DBUtil db = new DBUtil();*/
		/*db.getConnection();*/
		
		int result = db.update(sql, params);
		db.close();
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
