package beans;
import java.sql.SQLException;
import java.util.*;

public class user_info 
{
	//以下属性和数据库属性顺序一致
	private String username;//user_id
	private String nickname = null;//昵称  user_name
	private String password = null;	//user_psw
	private String userSex = null;    //user_sex
	private String userGrade = null;//user_grade
	private String userHobby = null;//user_hobby
	private String user_tel = null;  //user_tel
	private String user_email = null; //user_email
	private String user_address = null; //user_email
	private String self_introduce = null; //self_introduce 自我介绍
	private String self_blessing = null; //self_blessing 自我祝福
	private String userQuestion_motherName = null; //用户问题，你妈妈的姓名
	private String userQuestion_firstLove = null;  //用户问题，你的初恋
	private String register_time = null; //register_time 注册时间
	private DBUtil db;//定义一个数据库对象
	
	public String getUserQuestion_motherName() {
		return userQuestion_motherName;
	}
	public void setUserQuestion_motherName(String userQuestion_motherName) {
		this.userQuestion_motherName = userQuestion_motherName;
	}
	public String getUserQuestion_firstLove() {
		return userQuestion_firstLove;
	}
	public void setUserQuestion_firstLove(String userQuestion_firstLove) {
		this.userQuestion_firstLove = userQuestion_firstLove;
	}
	public void UserInfo() throws ClassNotFoundException, SQLException
	{
		db = new DBUtil();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public DBUtil getDb() {
		return db;
	}
	public void setDb(DBUtil db) {
		this.db = db;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public String getUserHobby() {
		return userHobby;
	}
	public void setUserHobby(String userHobby) {
		this.userHobby = userHobby;
	}
	
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getSelf_introduce() {
		return self_introduce;
	}
	public void setSelf_introduce(String self_introduce) {
		this.self_introduce = self_introduce;
	}
	public String getSelf_blessing() {
		return self_blessing;
	}
	public void setSelf_blessing(String self_blessing) {
		this.self_blessing = self_blessing;
	}
	public String getRegister_time() {
		return register_time;
	}
	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}
	
	
	//查询所有用户信息              返回list对象  到时候要用map 一个个循环取出来   p154
	public List getAllUserInfo() throws ClassNotFoundException, SQLException
	{
		List Users = null;
		String sql = "select * from user_info";
		
		DBUtil db = new DBUtil();
		db.getConnection();
		
		Users = db.getList(sql, null);
		db.close();
		return Users;
	}
		
	//通过账号返回一条用户信息
	public Map getUserinfo() throws ClassNotFoundException, SQLException{
		Map userinfo = null;
		String sql = "select * from user_info where user_id=?";//数据库里面的user_id就是userName
		String[] params = {username};
		
		DBUtil db = new DBUtil();
		db.getConnection();   //所有的方法都要先与数据库建立连接
		
		userinfo = db.getMap(sql, params);
		db.close();
		return userinfo;
	}
	
	//返回用户选择的密保问题
	public String getUserQuestion() throws ClassNotFoundException, SQLException
	{
		String userQuestion = null;//用户选择的问题
		String Answer1 = null;//问题1
		String Answer2 = null;//问题2
		Map userinfo = this.getUserinfo();
		Answer1 = (String) userinfo.get("userQuestion_motherName"); //1
		Answer2 = (String) userinfo.get("userQuestion_firstLove");  //2
		if(Answer1.equals("0"))
		{
			userQuestion = "userQuestion_firstLove"; //如果答案1是0，那么一定是选择了问题2
		}
		else
			userQuestion = "userQuestion_motherName";//否则，选择了问题1
		
		return userQuestion;
	}
	
	//返回用户选择的问题对应的答案
	public String getUserAnswer() throws ClassNotFoundException, SQLException
	{
		String userAnswer = null;
		Map userinfo = this.getUserinfo();//先通过学号获取一下用户整条信息
		String userQuestion = this.getUserQuestion();//获取用户选择了哪个问题
		userAnswer = (String)userinfo.get(userQuestion);
		
		return userAnswer;
	}
		
	//检查用户名是否存在     返回true或者false
	public boolean checkName() throws ClassNotFoundException, SQLException{
		//System.out.println("进来checkname");////////////////////////////////////////////
		boolean exist = false;
		String sql = "select * from user_info where user_id=?";

		String[] params = {username};

		DBUtil db = new DBUtil();
		db.getConnection();
		
		Map m = db.getMap(sql, params);
		if(m != null)
		{
			exist = true;
		}
		db.close();
		return exist;
		
	}
	
	//返回用户密码，返回字符串,可以用于检查密码是否正确
	public String getUserPassword() throws ClassNotFoundException, SQLException{
		String password = null;
		HashMap userinfo = (HashMap)this.getUserinfo();
		if(userinfo!=null) //如果此条用户信息不为空，即用户存在
		{
			password = (String)userinfo.get("user_psw");
		}
		else  //用户不存在
			password = null;
		this.password = password;
		return password;
	}
	
	//返回用户姓名，返回字符串
		public String getUserNikename() throws ClassNotFoundException, SQLException{
			String Nikename = null;
			HashMap userinfo = (HashMap)this.getUserinfo();
			if(userinfo!=null) //如果此条用户信息不为空，即用户存在
			{
				Nikename = (String)userinfo.get("user_name");
			}
			else  //用户不存在
				Nikename = null;
			this.nickname = Nikename;
			return Nikename;
		}
	
	//添加用户信息   //****************注册时添加******************/
	public int addUserRegister() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "insert into user_info (user_id,user_name,user_psw,user_sex,user_grade,user_hobby,userQuestion_motherName,userQuestion_firstLove,register_time) values(?,?,?,?,?,?,?,?,?)";
		String[] params = {username,nickname,password,userSex,userGrade,userHobby,userQuestion_motherName,userQuestion_firstLove,register_time};
		
		DBUtil db = new DBUtil();
		db.getConnection();
		
		result = db.update(sql, params);//调用数据库操作方法，执行更新
		db.close();
		return result;
	}
	
	/*********************修改/完善用户信息(部分记录)  返回受影响的结果数量*********************/
	public int updateUser() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "update user_info set user_tel=?,user_email=?,user_address=?,user_grade=?,self_introduce=?,self_blessing=? where user_id=?";  //不用修改密保问题
		String[] params = {user_tel,user_email,user_address,userGrade,self_introduce,self_blessing,username};
		
		DBUtil db = new DBUtil();
		db.getConnection();
			
		result = db.update(sql, params);
		db.close();
		return result;
	}
	
	/*********************修改/完善  订单中的用户信息(3列记录)  返回受影响的结果数量*********************/
	public int updateUserFromOrder() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "update user_info set user_tel=?,user_email=?,user_address=? where user_id=?";  //不用修改密保问题
		String[] params = {user_tel,user_email,user_address,username};
		
		DBUtil db = new DBUtil();
		db.getConnection();
			
		result = db.update(sql, params);
		db.close();
		return result;
	}
	
	//单独修改用户密码，用于找回密码功能
	public int updatePassword() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "update user_info set user_psw=? where user_id=?";
		String[] params ={password,username};
		DBUtil db = new DBUtil();
		db.getConnection();
		result = db.update(sql, params);
		db.close();
		return result;
	}
	
	//删除用户信息(一整条记录)  返回受影响的数量
	public int deleteUser() throws ClassNotFoundException, SQLException
	{
		int result = 0;
		String sql = "delete from user_info where user_id=?";
		String[] params = {username};
		
		DBUtil db = new DBUtil();
		db.getConnection();
		
		result = db.update(sql, params);
		db.close();
		return result;
	}
	
}




