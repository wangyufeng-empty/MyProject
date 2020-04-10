/**
 * 创建一个用户信息收集表，用户无时无刻收集用户的操作记录，用于智能推荐
 * 对应数据库表：CollectInfo_forIR
 */
package beans;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 王宇峰
 *
 */
public class CollectInfoFor_IR {
	private String user_id;
	private int circle_id;
	private String favorite_record;
	private String favorite_record_time;  //设置成静态，使之能一直增加并且在不同对象之间获取到
	private String search_record;
	private String search_record_time;
	private String purchase_record;
	private String purchase_record_time;
	private String viewDetails_record;
	private String viewDetails_record_time;
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象;
	
	/*一堆属性的set方法*/
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setCircle_id(int circle_id) {
		this.circle_id = circle_id;
	}

	public void setFavorite_record(String favorite_record) {
		this.favorite_record = favorite_record;
	}

	public void setFavorite_record_time(String favorite_record_time) {
		this.favorite_record_time = favorite_record_time;
	}

	public void setSearch_record(String search_record) {
		this.search_record = search_record;
	}

	public void setSearch_record_time(String search_record_time) {
		this.search_record_time = search_record_time;
	}

	public void setPurchase_record(String purchase_record) {
		this.purchase_record = purchase_record;
	}

	public void setPurchase_record_time(String purchase_record_time) {
		this.purchase_record_time = purchase_record_time;
	}

	public void setView_details_record(String view_details_record) {
		this.viewDetails_record = view_details_record;
	}

	public void setView_details_record_time(String view_details_record_time) {
		this.viewDetails_record_time = view_details_record_time;
	}

	

	/*用于注册完成时给用户信息收集表初始化6条固定的信息*/
	public int AddOneInfo_Initialization(){
		int result=0;
		String sql = "insert into CollectInfo_forIR values(null,?,?,null,?,null,?,null,?,null,?)";  
		Object[] params = {user_id,circle_id,favorite_record_time,search_record_time,purchase_record_time,viewDetails_record_time};
		result = db.updateComplex(sql, params);
		db.close();
		return result;
	}
	
	/*根据record_time和user_id更新一条记录,term指示更新的项*/
	/*需要提前set：记录、记录时间、ID*/
	public int UpdateOneInfo_ByRecordTime(String term){
		int result = 0;
		String sql = "";
		if(term.equals("favorite_record")){  //指示更新收藏记录
			sql = "UPDATE CollectInfo_forIR AS CIIR SET favorite_record = ?,favorite_record_time=? WHERE user_id = ? "+
					"ORDER BY CIIR.favorite_record_time ASC LIMIT 1;";
			Object[] params = {favorite_record,favorite_record_time,user_id};
			result = db.updateComplex(sql, params);
		}
		else if(term.equals("search_record")){  //指示更新搜索记录
			sql = "UPDATE CollectInfo_forIR AS CIIR SET search_record = ?,search_record_time=? WHERE user_id = ? "+
					"ORDER BY CIIR.search_record_time ASC LIMIT 1;";
			Object[] params = {search_record,search_record_time,user_id};
			result = db.updateComplex(sql, params);
		}
		else if(term.equals("purchase_record")){  //指示更新购买记录
			sql = "UPDATE CollectInfo_forIR AS CIIR SET purchase_record = ?,purchase_record_time=? WHERE user_id = ? "+
					"ORDER BY CIIR.purchase_record_time ASC LIMIT 1;";
			Object[] params = {purchase_record,purchase_record_time,user_id};
			result = db.updateComplex(sql, params);
		}
		else if(term.equals("viewDetails_record")){  //指示更新购买记录
			sql = "UPDATE CollectInfo_forIR AS CIIR SET viewDetails_record = ?,viewDetails_record_time=? WHERE user_id = ? "+
					"ORDER BY CIIR.viewDetails_record_time ASC LIMIT 1;";
			Object[] params = {viewDetails_record,viewDetails_record_time,user_id};
			result = db.updateComplex(sql, params);
		}
		
		db.close();
		return result;
	}
	
	/*通过ID返回一个用户的所有收集信息*/
	public List GetOneUserAllCollection_ById(){
		
		List OneUserAllCollection = null;
		String sql = "select * from CollectInfo_forIR where user_id=?";	
		String[] params = {user_id};
		try {
			OneUserAllCollection = db.getList(sql, params);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		return OneUserAllCollection;
	}
	
	
	
	
}
