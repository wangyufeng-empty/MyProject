/**
 *    此类主要实现聊天记录的保存，和数据库的表：friend_message  相对应，属性和方法均为操作数据库记录
 */
package beans;
import java.sql.*;
import java.util.*;
/**
 * @author 王宇峰
 *
 */
public class FriendMessage {
	private String from_user_id;
	private String to_user_id;
	private String content;
	private String send_time;
	private int is_read;
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
	public String getFrom_user_id() {
		return from_user_id;
	}
	public void setFrom_user_id(String from_user_id) {
		this.from_user_id = from_user_id;
	}
	public String getTo_user_id() {
		return to_user_id;
	}
	public void setTo_user_id(String to_user_id) {
		this.to_user_id = to_user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public int getIs_read() {
		return is_read;
	}
	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}
	
	//根据to_user_id和from_user_id返回n条聊天记录(一次返回10条消息)，结果用map进行循环
	public List getMultipleMessage() throws ClassNotFoundException, SQLException{
		List friend_message = null;
		String sql = "select * from friend_message where from_user_id=? and to_user_id=? limit 0,10";
		String[] params = {from_user_id,to_user_id};
		
//		DBUtil db = new DBUtil();  //创建数据库对象
//		db.getConnection();   //所有的方法都要先与数据库建立连接
		
		friend_message = db.getList(sql, params);  //getList方法
		db.close();
		return friend_message;
	}
	
	
	//添加一条聊天记录，返回0或者1（配合循环上传多条聊天记录）
	public int addOneGoodsPicture() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "insert into friend_message values(null,?,?,?,?,?)";
		Object[] params = {from_user_id,to_user_id,content,send_time,is_read};		
//		DBUtil db = new DBUtil();//
//		db.getConnection();	
		result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新   复合更新
		db.close();
		return result;
	}

}
