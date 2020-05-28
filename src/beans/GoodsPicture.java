/**
 *   此类主要和数据库表： goods_picture 相对应，属性和方法均为操作数据库记录
 */
package beans;

import java.sql.SQLException;
import java.util.*;

/**
 * @author 王宇峰
 *
 */
public class GoodsPicture {
	private String goods_id;  //商品ID
	private String product_image; // 商品图片
	private Integer homePage_show = 0;//主页显示（1 是，0 否）
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象

	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getProduct_image() {
		return product_image;
	}

	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}

	public Integer getHomePage_show() {
		return homePage_show;
	}

	public void setHomePage_show(Integer homePage_show) {
		this.homePage_show = homePage_show;
	}

	//通过商品id返回1~n 条图片信息（返回一个商品的n张图片）
	public List<String> getMultipleGoodsPictures() throws ClassNotFoundException, SQLException{
		List<String> goods_pictures = new ArrayList<>();
		String sql = "select * from goods_picture where goods_id=?";
		String[] params = {goods_id};
		
		/*DBUtil db = new DBUtil();*/
		/*db.getConnection();*/   //所有的方法都要先与数据库建立连接
		
		goods_pictures = db.getList(sql, params);
		db.close();
		return goods_pictures;
	}
	
	/*通过ID返回第一张图片，用于商品显示*/
	public String getFirstGoodsPictures_ById(String goods_id) 
			throws ClassNotFoundException, SQLException{
		GoodsPicture GoodsPicture = new GoodsPicture();
		GoodsPicture.setGoods_id(goods_id);
		//得到一件商品的所有图片
		List goods_pictures = GoodsPicture.getMultipleGoodsPictures();
		Map goods_picture = new HashMap<>();
		String goods_image = null;
		//取这件商品的第一张图片,注意这里有肯能为空
		
		if(goods_pictures.size()>0){
			goods_picture = (Map) goods_pictures.get(0);
			goods_image = goods_picture.get("product_image").toString();
			System.out.println(goods_id+": "+goods_image);
		}
		else{
			System.out.println(goods_id+": 没有图片，应用默认图片");
		}
		return goods_image;
	}
	
	//添加一条图片信息，存储一张图片,返回0或者1（配合循环上传多张图片）
	public int addOneGoodsPicture() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "insert into goods_picture values(null,?,?,?)";
		Object[] params = {goods_id,product_image,homePage_show};
		
		/*DBUtil db = new DBUtil();*/
		/*db.getConnection();*/
		
		result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   复合更新
		db.close();
		return result;
	}
	
	/*获取所有homePage_show=1的list*/
	public List getRCFromGoodsImg() throws ClassNotFoundException, SQLException{
		List AllRotationChart = null;
		String sql = "select * from goods_picture where homePage_show=1";
		AllRotationChart = db.getList(sql, null);
		return AllRotationChart;
	}
	
	/*获取所有轮播图*/
	public List getAllRotationChart() throws ClassNotFoundException, SQLException{
		/*回调LIST对象*/
		List returnList = new ArrayList<>();
		
		/*创建对象*/
		//GoodsPicture AllRotationChartList = new GoodsPicture();
		Goods GoodsInfo = new Goods();
		/*接收所有轮播图,homePage_show=1的list*/			
		List RCFromGoodsImg = this.getRCFromGoodsImg();		
		System.out.println("轮播图RCFromGoodsImg:"+RCFromGoodsImg.toString());  ////////
		/*需要的三个属性*/
		String product_image = ""; //图片*
		String goods_id="";  //借助id查找名字和价格
		String goods_name="";//名字 *
		double goods_price = 0.0;//价格 *
		/**********开始循环取三个属性************/
		for(int i=0;i<RCFromGoodsImg.size();i++){
			/*用每个json对象暂存新的关系*/
			Map oneMap = new HashMap<>();;
			/*取每一条记录分别处理*/
			Map RotationChart = (Map)RCFromGoodsImg.get(i);
			product_image = RotationChart.get("product_image").toString(); // 1 取出的是商品图片路径 
			goods_id = RotationChart.get("goods_id").toString(); 
			
			/*取另外两个属性*/
			GoodsInfo.setGoodsId(goods_id);
			Map goods_info = GoodsInfo.getGoodsInfo();
			goods_name = goods_info.get("goods_name").toString(); // 2
			goods_price = Double.parseDouble(goods_info.get("goods_price").toString()); // 3
			/*把三个属性组成一个object放在JSONArray中*/
			oneMap.put("goods_id", goods_id);
			oneMap.put("product_image", product_image);
			oneMap.put("goods_name", goods_name);
			oneMap.put("goods_price", goods_price);
			/*将map数据加入到LIST中*/
			returnList.add(oneMap);
		}// end for
		
		/*返回LIST*/
		return returnList;
	}
}
