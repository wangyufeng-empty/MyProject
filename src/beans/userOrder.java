package beans;
import java.sql.SQLException;
import java.util.*;

public class userOrder {                   /*****************************创建一个用户订单类************************************/
	//以下属性和数据库属性顺序一致
		private String user_id;//user_id
		private String order_id = null;//order_id
		private String order_time = null;	//order_time
		private String consignee = null;    //consignee  收货人
		private String tel_num = null;//tel_num
		private String address = null;//address
		private double sum_account = 0; //sum_account   订单总价
		private String order_state = null;   //order_state  订单状态
		private String include_goodsId = null;  //这笔订单包含的货物有哪些
		private int is_user_show = 1;
		public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
		
		
		
		public String getInclude_goodsId() {
			return include_goodsId;
		}

		public void setInclude_goodsId(String include_goodsId) {
			this.include_goodsId = include_goodsId;
		}

		public String getOrder_state() {
			return order_state;
		}

		public void setOrder_state(String order_state) {
			this.order_state = order_state;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getOrder_id() {
			return order_id;
		}

		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}

		public String getOrder_time() {
			return order_time;
		}

		public void setOrder_time(String order_time) {
			this.order_time = order_time;
		}

		public String getConsignee() {
			return consignee;
		}

		public void setConsignee(String consignee) {
			this.consignee = consignee;
		}

		public String getTel_num() {
			return tel_num;
		}

		public void setTel_num(String tel_num) {
			this.tel_num = tel_num;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public double getSum_account() {
			return sum_account;
		}

		public void setSum_account(double sum_account) {
			this.sum_account = sum_account;
		}

		
			public int getIs_user_show() {
			return is_user_show;
		}

		public void setIs_user_show(int is_user_show) {
			this.is_user_show = is_user_show;
		}

			/************************以后还要对查询更加多样化，比如实现通过时间范围查询相应的订单信息********************/
			//查询所有订单信息              在这里只展示is_user_show为1的订单，
		    //订单信息不能被用户单向删除，管理员还能继续查看)
			public List getAllOrderInfo() throws ClassNotFoundException, SQLException
			{
				List Orders = null;
				String sql = "select * from order_info where user_id=? and is_user_show=1";
				String[] params = {user_id};
				
				Orders = db.getList(sql, params);
				db.close();
				return Orders;
			}
			
		//通过账号和订单号返回一条订单信息，用户查询订单(
		public Map getOneOrderInfo() throws ClassNotFoundException, SQLException{
			Map orderInfo = null;
			String sql = "select * from order_info where user_id=? and order_id=?";//数据库里面的user_id就是userName
			String[] params = {user_id,order_id};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/   //所有的方法都要先与数据库建立连接
			
			orderInfo = db.getMap(sql, params);
			db.close();
			return orderInfo;
		}
		
		//添加一条订单信息   /***************************在这里不包括订单状态，订单状态单独修改********************/
		public int addOrderInfo() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "insert into order_info values(?,?,?,?,?,?,?,?,?,1)";
			Object[] params = {user_id,order_id,order_time,consignee,tel_num,address,sum_account,order_state,include_goodsId};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			result = db.updateComplex(sql, params);//调用数据库操作方法，执行更新
			db.close();  //我每次都有手动关闭连接啊，怎么会连接过多呢
			return result;
		}
		
		//修改一条订单信息  只能修改收货人、电话号码、地址           返回受影响的结果数量
		public int updateOneOrderInfo() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update order_info set consignee=?,tel_num=?,address=? where user_id=? and order_id=?"; 
			String[] params = {consignee,tel_num,address,user_id,order_id};
			
			///*DBUtil db = new DBUtil();*/
			DBUtil db = DBUtil.getDBUtil();		
			///*db.getConnection();*/				
			result = db.update(sql, params);
			db.close();
			return result;
		}
		
		//修改一条订单的状态，状态有：待收货、已完成
		public int updateOrderState() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update order_info set order_state=? where user_id=? and order_id=?"; 
			String[] params = {order_state,user_id,order_id};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
				
			result = db.update(sql, params);
			db.close();
			return result;
		}
		
		//删除一条订单信息(只要把is_user_show置为0即可)         返回受影响的数量
		public int deleteOneOrderInfo() throws ClassNotFoundException, SQLException
		{
			int result = 0;
			String sql = "update order_info set is_user_show=0  where user_id=? and order_id=?";
			String[] params = {user_id,order_id};
			
			/*DBUtil db = new DBUtil();*/
			/*db.getConnection();*/
			
			result = db.update(sql, params);
			db.close();
			return result;
		}
		
		//计算时间(汉字标准格式)，用于订单时间等等
		public String creatNowTimeFormart()
		{
			/*******************开始计算订单生成时间***********************/
			Calendar calendar = new GregorianCalendar();
			String year = ""+calendar.get(Calendar.YEAR); //int转化成string：与String相加，例如 int i = 10; String  s = ""+i;
			int Month = calendar.get(Calendar.MONTH)+1;
			String month = ""+Month;
			String day = ""+calendar.get(Calendar.DAY_OF_MONTH);
			String hour = ""+calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
			String min = ""+calendar.get(Calendar.MINUTE);          //获取当前分钟
			String sec = ""+calendar.get(Calendar.SECOND);          //获取当前秒
			if(month.length()==1)//时间补零操作
				month = "0"+month;	
			if(day.length()==1)
				day = "0"+day;
			if(hour.length()==1)
				hour = "0"+hour;
			if(min.length()==1)
				min = "0"+min;
			if(sec.length()==1)
				sec = "0"+sec;
			String Now_time = year+"年"+month+"月"+day+"日-"+hour+":"+min+":"+sec;
			/*****************结束计算时间*****************************/
			return Now_time;
		}
		
		
		//计算时间(数字标准格式)，用于订单时间等等
		public String creatNowTimeNumber()
		{
			/*******************开始计算订单生成时间***********************/
			Calendar calendar = new GregorianCalendar();
			String year = ""+calendar.get(Calendar.YEAR); //int转化成string：与String相加，例如 int i = 10; String  s = ""+i;
			int Month = calendar.get(Calendar.MONTH)+1;
			String month = ""+Month;
			String day = ""+calendar.get(Calendar.DAY_OF_MONTH);
			String hour = ""+calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
			String min = ""+calendar.get(Calendar.MINUTE);          //获取当前分钟
			String sec = ""+calendar.get(Calendar.SECOND);          //获取当前秒
			if(month.length()==1)//时间补零操作
				month = "0"+month;	
			if(day.length()==1)
				day = "0"+day;
			if(hour.length()==1)
				hour = "0"+hour;
			if(min.length()==1)
				min = "0"+min;
			if(sec.length()==1)
				sec = "0"+sec;
			String Now_time = year + month + day + hour + min + sec;
			/*****************结束计算时间*****************************/
			return Now_time;
		}

		
}



