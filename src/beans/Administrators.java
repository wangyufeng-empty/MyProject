package beans;
import java.sql.SQLException;
import java.util.*;

public class Administrators {
	//以下属性和数据库属性顺序一致
		private String adminId;//admin_id
		private String adminPsw = null;	//admin_psw
		private String adminName = null;//昵称admin_name	
		private String adminSex = null;    //admin_sex
		public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象			
		public String getAdminId() {
			return adminId;
		}

		public void setAdminId(String adminId) {
			this.adminId = adminId;
		}

		public String getAdminPsw() {
			return adminPsw;
		}

		public void setAdminPsw(String adminPsw) {
			this.adminPsw = adminPsw;
		}

		public String getAdminName() {
			return adminName;
		}

		public void setAdminName(String adminName) {
			this.adminName = adminName;
		}

		public String getAdminSex() {
			return adminSex;
		}

		public void setAdminSex(String adminSex) {
			this.adminSex = adminSex;
		}

		public DBUtil getDb() {
			return db;
		}

		public void setDb(DBUtil db) {
			this.db = db;
		}

		//通过管理员账号返回一条管理员信息
		public Map getAdminInfo() throws ClassNotFoundException, SQLException{
			Map admininfo = null;
			String sql = "select * from administrator_info where admin_id=?";
			String[] params = {adminId};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/   //所有的方法都要先与数据库建立连接
			
			admininfo = db.getMap(sql, params);
			return admininfo;
		}
		
		
		//检查管理员是否存在     返回true或者false
		public boolean checkName() throws ClassNotFoundException, SQLException{
			
			boolean exist = false;
			String sql = "select * from administrator_info where admin_id=?";

			String[] params = {adminId};

			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			Map m = db.getMap(sql, params);
			if(m != null)
			{
				exist = true;
			}
			return exist;
			
		}
		
		//返回管理员密码，返回字符串,可以用于检查密码是否正确
		public String getAdminPassword() throws ClassNotFoundException, SQLException{
			String password = null;
			HashMap admininfo = (HashMap)this.getAdminInfo();
			if(admininfo!=null) //如果此条用户信息不为空，即用户存在
			{
				password = (String)admininfo.get("admin_psw");
			}
			else  //用户不存在
				password = null;
			this.adminPsw = password;
			return password;
		}
		
		//返回管理员姓名，返回字符串
			public String getAdminname() throws ClassNotFoundException, SQLException{
				String AdminName = null;
				HashMap userinfo = (HashMap)this.getAdminInfo();
				if(userinfo!=null) //如果此条用户信息不为空，即用户存在
				{
					AdminName = (String)userinfo.get("admin_name");
				}
				else  //管理员不存在
					AdminName = null;
				this.adminName = AdminName;
				return AdminName;
			}
		
		//添加管理员信息      界面无权添加，留待开发者进一步分类权限
		public int addAdmin() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "insert into administrator_info values(?,?,?,?)";
			String[] params = {adminId,adminPsw,adminName,adminSex};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			result = db.update(sql, params);//调用数据库操作方法，执行更新
			return result;
		}
		
		//修改管理员信息(一整条记录)  返回受影响的结果数量    界面无权修改，留待开发者进一步分类权限
		public int updateAdmin() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update administrator_info set admin_id=?,admin_psw=?,admin_name=?,admin_sex=? where admin_id=?";  
			String[] params = {adminId,adminPsw,adminName,adminSex,adminId};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
				
			result = db.update(sql, params);
			return result;
		}
		
		//单独修改管理员密码，用于找回密码功能        界面无权修改，留待开发者进一步分类权限
		public int updatePassword() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update administrator_info set admin_psw=? where admin_id=?";
			String[] params ={adminPsw,adminId};
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			result = db.update(sql, params);
			return result;
		}
}
