/**
 * 此实体类对应数据库表：selled_order_info  为了用户能够查看到“我卖出的”信息
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王宇峰
 *
 */
public class SelledOrderInfo {
	private String user_id;
	private String buyer_id;
	private String goods_id;
	private String goods_name;
	private int selectedQuantity;
	private double subtotal;
	private String buyer_tel;
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
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
	public String getBuyer_tel() {
		return buyer_tel;
	}
	public void setBuyer_tel(String buyer_tel) {
		this.buyer_tel = buyer_tel;
	}
	
	/*增加一条卖出的订单信息*/
	public int addOneSelledOrderInfo(){
	
		int result = 0;
		String sql = "insert into selled_order_info values(null,?,?,?,?,?,?,?)";
		Object[] params = {user_id,buyer_id,goods_id,goods_name,selectedQuantity,subtotal,buyer_tel};
		
		result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   复合更新
		db.close();
		return result;
		
	}
	
	/*根据用户id返回一个“我卖出的”订单信息*/
	public List getSelledOrderInfo_ById() throws ClassNotFoundException, SQLException{
		List SelledOrderInfoList = new ArrayList<>();
		String sql = "select * from selled_order_info where user_id=?";
		String[] params = {user_id};
		SelledOrderInfoList = db.getList(sql, params);
		db.close();
		return SelledOrderInfoList;
	}
	
	
}
