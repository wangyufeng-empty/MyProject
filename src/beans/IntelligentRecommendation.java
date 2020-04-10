/**
 *    为了更新智能推荐表设计的类
 */
package beans;

import java.sql.SQLException;

/**
 * @author 王宇峰
 *
 */
public class IntelligentRecommendation {
	private String user_id;
	private String generate_keywords; //数组转字符串
	private String recommend_goods_id; //推荐的商品ID
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getGenerate_keywords() {
		return generate_keywords;
	}
	public void setGenerate_keywords(String generate_keywords) {
		this.generate_keywords = generate_keywords;
	}
	public String getRecommend_goods_id() {
		return recommend_goods_id;
	}
	public void setRecommend_goods_id(String recommend_goods_id) {
		this.recommend_goods_id = recommend_goods_id;
	}
	
	public int AddOneInfo() throws ClassNotFoundException, SQLException{   //生成一条智能推荐信息
		int result = 0;
		String sql = "insert into intelligent_recommendation values(null,?,null,null)";  //user_id 、user_hobby
		String[] params = {user_id};
		result = db.update(sql, params);
		db.close();
		return result;
	}
	
	
		

}

