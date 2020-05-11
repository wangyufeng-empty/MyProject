/**
 * 对应数据库 announcement_info
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author 王宇峰
 */
public class AnnouncementInfo {

	/**
	 * @param args
	 */
	private String announcement_id;
	private String announcement_content;
	private String announcement_date;
	private String publisher_id;
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象;
	public String getAnnouncement_id() {
		return announcement_id;
	}
	public void setAnnouncement_id(String announcement_id) {
		this.announcement_id = announcement_id;
	}
	public String getAnnouncement_content() {
		return announcement_content;
	}
	public void setAnnouncement_content(String announcement_content) {
		this.announcement_content = announcement_content;
	}
	public String getAnnouncement_date() {
		return announcement_date;
	}
	public void setAnnouncement_date(String announcement_date) {
		this.announcement_date = announcement_date;
	}
	public String getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}
	
	//查询所有公告信息
	public List getAllAnnouncementInfo() throws ClassNotFoundException, SQLException
	{
		List announcement = new ArrayList<>();
		String sql = "SELECT * FROM announcement_info ORDER BY announcement_date DESC;";		
		announcement = db.getList(sql, null);
		db.close();
		return announcement;
	}
	
	
	
	
	
	
	

}
