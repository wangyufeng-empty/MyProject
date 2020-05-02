/**
 * 创建一个用户信息收集表，用户无时无刻收集用户的操作记录，用于智能推荐
 * 对应数据库表：CollectInfo_forIR
 */
package beans;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beans.user_info;
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
	
	/*返回信原始推荐信息表所有字符串*/
	public String GetIR_Original_String() throws ClassNotFoundException, SQLException{
		String IR_Original_String = "";
		user_info user = new user_info();
		List OneUserAllCollection = this.GetOneUserAllCollection_ById();  //获取一个用户的所有map
		//初始化空串
		String favorite_record = "",search_record = "",purchase_record = "",viewDetails_record="",user_hobby="";
		user.setUsername(this.user_id);
		user_hobby = user.getUserinfo().get("user_hobby").toString();
		for(Object OneUserCollection : OneUserAllCollection){
			HashMap OneUser_Collection = (HashMap)OneUserCollection; //强制类型转化为map
			favorite_record = (String) OneUser_Collection.get("favorite_record");
			search_record = (String) OneUser_Collection.get("search_record");
			purchase_record = (String) OneUser_Collection.get("purchase_record");
			viewDetails_record = (String) OneUser_Collection.get("viewDetails_record");
			IR_Original_String = IR_Original_String + favorite_record + search_record + purchase_record + viewDetails_record;
		}
		IR_Original_String+=user_hobby;  //最后加上用户喜好
		return IR_Original_String;
	}
	
	/*更新智能推荐表------------------------------------------------
	    需要添加的内容有 商品名、发布者、分类
	 * 需要设计只保存最近6次的记录
	 * 需要提前set：记录、ID
	 */
	/*关键！！！更新智能推荐信息收集表*/
	//model：收藏、搜索、购买、查看详情;
	//model_record:更新的字符串
	public void Update_IR_Table(String model_record,String model){   
		
		int result = 0;
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeStamp = (new Date().getTime());	 //时间戳	
		String model_record_time = dateFormat.format(timeStamp+6000); //时间戳转化为标准时间格式,这里+6000防止第一次记录会被覆盖
		
		//先看看表格有没有初始化	
		List OneUserAllCollection = this.GetOneUserAllCollection_ById();
		if(OneUserAllCollection.isEmpty()){ //初始化表格	 注意arraylist的判空方式为 isEmpty					
			for(int i=1; i<=6; i++,timeStamp+=1000){   //初始化这些数据的时候每一项加1秒	
				String nowtime = dateFormat.format(timeStamp);
				this.setCircle_id(i);  //设置循环ID 1-6
				this.setFavorite_record_time(nowtime);
				this.setPurchase_record_time(nowtime);
				this.setSearch_record_time(nowtime);
				this.setView_details_record_time(nowtime);
				result = this.AddOneInfo_Initialization();								
			}
			if(result==1){System.out.println(this.user_id + ": CollectInfo_forIR 初始化成功！");}
		}
		System.out.println("model :"+model);//    -------------------------------
		switch(model){
			case "favorite_record":  //收藏记录
			{this.setFavorite_record(model_record); //model_record
			this.setFavorite_record_time(model_record_time);
			result = this.UpdateOneInfo_ByRecordTime(model);  //关键，根据时间顺序更新一条用户操作记录
			if(result==1){System.out.println(this.user_id + ":" +model +"收集成功");}break;}
			case "search_record":  //搜索记录
			{this.setSearch_record(model_record); //model_record
			this.setSearch_record_time(model_record_time);
			result = this.UpdateOneInfo_ByRecordTime(model);   //关键，根据时间顺序更新一条用户操作记录
			if(result==1){System.out.println(this.user_id + ":" +model +"收集成功");}break;}
			case "purchase_record":  //购买记录
			{this.setPurchase_record(model_record); //model_record
			this.setPurchase_record_time(model_record_time);;
			result = this.UpdateOneInfo_ByRecordTime(model);   //关键，根据时间顺序更新一条用户操作记录
			if(result==1){System.out.println(this.user_id + ":" +model +"收集成功");}break;}
			case "viewDetails_record":  //查看详情记录
			{this.setView_details_record(model_record); //model_record
			this.setView_details_record_time(model_record_time);
			result = this.UpdateOneInfo_ByRecordTime(model);   //关键，根据时间顺序更新一条用户操作记录
			if(result==1){System.out.println(this.user_id + ":" +model +"收集成功");}break;}
		}
		
		
	}
	
}
