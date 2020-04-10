package beans;

import java.sql.*;
import java.util.*;
import java.util.Date;

import java.text.SimpleDateFormat;
/*
 * 数据库应用中，连接的产生和关闭是非常耗时间的，
 * 所以我们应该尽量去避免这样的操作
 * 以后可以使用数据库连接池解决
 * 
 * 现在我们使单例模式，只有一个连接
 * 
 * 单例模式第一件事让类的构造方法私有化
 */

public class DBUtil {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/SecondHandShopping_web";
	private static final String username = "root";
	private static final String password = "123456";
	private static DBUtil dbutil = null;
	private static Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DBUtil(){   //定义构造方法，初始化链接参数
		getConnection();
	}

	

    public Connection getConnection()  //在这里第一步获取连接对象
	{
		
    	try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,username,password);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
    
    public static DBUtil getDBUtil(){
    	if(dbutil == null){
    		dbutil = new DBUtil();
    	}
    	return dbutil;
    }
    

	private PreparedStatement getPreparedStatement(String sql) throws ClassNotFoundException   //在这里第二步获取语句对象
	{
		try{
			pstmt = getConnection().prepareStatement(sql);//关键语句，获取语句对象
		}
		catch(SQLException s)
		{
			s.printStackTrace();
		}
		return pstmt;
	}
	private void setParams(String sql,String[] params) throws ClassNotFoundException   //给预编译语句  设置参数 
	{
		pstmt = this.getPreparedStatement(sql);
		if(params!=null)                  //这里是一个坑，如果传进来的数组是null。不加这个判断会报错
		{
			for(int i = 0;i < params.length;i++)
			{
				try{
					pstmt.setString(i+1, params[i]);///回填参数   只能回填字符串类型的
				}
				catch(SQLException s)
				{
					s.printStackTrace();
				}
			}
		}
	}
	
	/**************************************************************************************/
	public static String getType(Object o){ //获取变量类型方法

		return o.getClass().toString(); //使用int类型的getClass()方法
	} 
	
	
	/*********************技术亮点：可以在一个sql语句中放置多种类型的变量，不一定是String类型的，相比之前的有较大的改进********************/
	
	private void setParamsComplex(String sql,Object[] params) throws ClassNotFoundException   //给预编译语句  设置参数  可以实现sql语句的复合参数！！！！难点
	{
		pstmt = this.getPreparedStatement(sql);
		for(int i = 0;i < params.length;i++)
		{
			try
			{
				String type = DBUtil.getType(params[i]);   //获取此时的对象类型
				
				if(type.equals("class java.lang.String"))
				{
					pstmt.setString(i+1, params[i].toString());///回填字符串参数
				}
				if(type.equals("class java.lang.Integer"))
				{
					pstmt.setInt(i+1, Integer.parseInt(params[i].toString()));///回填整形参数
				}
				if(type.equals("class java.lang.Double"))
				{
					pstmt.setDouble(i+1, Double.parseDouble(params[i].toString()));///回填浮点参数
				}
			}
			catch(SQLException s)
			{
				s.printStackTrace();
			}
		}
	}
	public List getList(String sql,String[] params) throws SQLException  //执行查询操作，将执行结果放在LIST中
, ClassNotFoundException
	{
		List list = new ArrayList();   //创建一个list对象用于返回值
		try{
			this.setParams(sql, params);   //调用设置参数回填
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();//获取元数据
			while(rs.next())
			{
				Map m = new HashMap();//m 可以理解为一条记录，只是数据类型不一样
				for(int i=1; i<=rsmd.getColumnCount();i++)
				{
					String colName = rsmd.getColumnName(i);//获取列名
					m.put(colName, rs.getString(colName));//把键值对加入map对象
				}
				list.add(m);//把一条记录加入list对象
			}
		}
		catch(SQLException s)
		{
			s.printStackTrace();
		}
		return list;
	}
	//执行数据库操作时，将返回的结果封装到LIST对象中   针对于查询结果只有一个的情况，如果查询结果有多个，则应该直接用getlist()
	public Map getMap(String sql,String[] params) throws ClassNotFoundException, SQLException
	{
		List list = getList(sql,params);
		if(list.isEmpty()){
			return null;
			}
		else
			//System.out.println("list.get(0):"+list.get(0));//////////////////////////////////
			return (Map)list.get(0);//返回list的第一个元素
	}
	public int update(String sql,String[] params)
	{
		int result_state = 0;
		try
		{
			setParams(sql,params);  //执行这句话的时候，已经返回了具体的pstmt语句对象，参数也回填完
			result_state = pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result_state;
	}
	
	/*******************************************技术亮点：配合之前的参数回填******************************************************/
	
	public int updateComplex(String sql,Object[] params)   //这里传进来的是对象型数组！！！！！！难点
	{
		int result_state = 0;
		try
		{
			setParamsComplex(sql,params);  //执行这句话的时候，已经返回了具体的pstmt语句对象，参数也回填完
			result_state = pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result_state;
	}
	//关闭对象
	public void close()
	{
		try
		{
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(con != null)
				con.close();
		}
		catch(SQLException s)
		{
			s.printStackTrace();
		}
	}

	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException    //用来测试
	{
		long timeStamp;
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 timeStamp = (new Date().getTime());
		 for(int i=1; i<6; i++,timeStamp+=1000){
			 System.out.println(i+": timeStamp = "+ timeStamp);
			 String nowtime = dateFormat.format(timeStamp);
			 System.out.println(i+": nowtime = "+ nowtime);
		 }
		 
	}
}


