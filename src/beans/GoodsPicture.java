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

	//通过商品id返回1~n 条图片信息（返回一个商品的n张图片）
	public List<String> getMultipleGoodsPictures() throws ClassNotFoundException, SQLException{
		List<String> goods_pictures = null;
		String sql = "select * from goods_picture where goods_id=?";
		String[] params = {goods_id};
		
		/*DBUtil db = new DBUtil();*/
		/*db.getConnection();*/   //所有的方法都要先与数据库建立连接
		
		goods_pictures = db.getList(sql, params);
		db.close();
		return goods_pictures;
	}
	
	//添加一条图片信息，存储一张图片,返回0或者1（配合循环上传多张图片）
	public int addOneGoodsPicture() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "insert into goods_picture values(null,?,?)";
		Object[] params = {goods_id,product_image};
		
		/*DBUtil db = new DBUtil();*/
		/*db.getConnection();*/
		
		result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   复合更新
		db.close();
		return result;
	}
	
	
}
