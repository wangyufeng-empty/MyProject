/**
 *    这个类主要是用于需求分析里面的“实名认证”功能，即从教务系统中取出在校大学生，然后进行验证输入的学号、姓名是否正确
 *    对应数据库：real_name_verification
 *    在这里不需要修改和存储，只需要取出数据
 */
package beans;
import java.util.*;
import java.sql.SQLException;

/**
 * @author 王宇峰
 *
 */
public class RealNameVerification {
	private String student_id;
	private String student_name;
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	
	public Map getRealName_Student() throws ClassNotFoundException, SQLException{
		Map student_info = null;
		String sql = "select * from real_name_verification where student_id=?";
		String[] params = {student_id};
		student_info = db.getMap(sql, params);
		db.close();
		return student_info;
	}
}
