/**
 *    为了更新智能推荐表设计的类
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 王宇峰
 *
 */
public class IntelligentRecommendation {
	private String user_id;
	private int circle_id;
	private String generate_keywords; //数组转字符串
	private String recommend_goods_id; //推荐的商品ID
	public  DBUtil db = DBUtil.getDBUtil();//定义一个数据库对象
	
	public int getCircle_id() {
		return circle_id;
	}
	public void setCircle_id(int circle_id) {
		this.circle_id = circle_id;
	}
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
		String sql = "insert into intelligent_recommendation values(null,?,?,?,?)";  //user_id 、generate_keywords,recommend_goods_id
		Object[] params = {user_id,circle_id,generate_keywords,recommend_goods_id};
		result = db.updateComplex(sql, params);
		db.close();
		return result;
	}
	
	/*更新一个用户的所有推荐的goods_id*/
	public int Update_IR_RIdAndKey(){
		int result = 0;
		String sql = "update intelligent_recommendation set recommend_goods_id=?,generate_keywords=? where user_id=? and circle_id=?";
		Object[] params = {recommend_goods_id,generate_keywords,user_id,circle_id};
		result = db.updateComplex(sql, params);
		db.close();
		return result;
	}
	
	public List GetAll_IR_ById() throws ClassNotFoundException, SQLException{
		List IR = null;
		String sql = "select * from intelligent_recommendation where user_id=?";
		String[] params = {user_id};
		IR = db.getList(sql, params);
		db.close();
		return IR;
	}
	
	//获取推荐商品的ID并入库，返回推荐商品的所有信息
	//keyArrays:关键词数组
	//需要提前set user_id、setGenerate_keywords、setRecommend_goods_id\circle_id
	public List Generate_IR_GoodsId(String[] KeyArrays) throws ClassNotFoundException, SQLException{
		String[] keyArrays = new String[6];  //保证关键词数组有6个空间，防止报错
		if(KeyArrays.length<6){
			for(int i=0;i<KeyArrays.length;i++){
				keyArrays[i] = KeyArrays[i];
			}
			for(int i=KeyArrays.length;i<6;i++){
				keyArrays[i]="";
			}
		}
		else{
			keyArrays = KeyArrays;
		}
		
		int result = 0;
		List IR_GoodsIdS = null;  //创建一个动态数组保存ID
		List AllIR = null;   //创建一个动态数组保存推荐表的所有信息
		String REGEXP_string = "";
		/*拼接字符串*/
		
		for(int i=0;i<6;i++){    //这里写死，防止用户刚创建时，关键字不足6个
			if(keyArrays[i].equals("")||keyArrays[i]==null)
				keyArrays[i] = "NULL";  //防止sql错误		
			REGEXP_string = REGEXP_string+ keyArrays[i];						
			if(i!=5)
				REGEXP_string = REGEXP_string+"|";
		}
		
		
		System.out.println("REGEXP_string  :"+ REGEXP_string);
		this.setGenerate_keywords(Arrays.toString(keyArrays));  //首先设置属性，生成的关键字 2
		String sql = "select *, "+
				"("+
						"(IF (CONCAT_WS(\" \",goods_name,goods_publisher,goods_category,goods_describe) LIKE ?,1,0)) " +
						"+(IF (CONCAT_WS(\" \",goods_name,goods_publisher,goods_category,goods_describe) LIKE ?,1,0)) "+
						"+(IF (CONCAT_WS(\" \",goods_name,goods_publisher,goods_category,goods_describe) LIKE ?,1,0)) "+
						"+(IF (CONCAT_WS(\" \",goods_name,goods_publisher,goods_category,goods_describe) LIKE ?,1,0)) "+
						"+(IF (CONCAT_WS(\" \",goods_name,goods_publisher,goods_category,goods_describe) LIKE ?,1,0)) "+
						"+(IF (CONCAT_WS(\" \",goods_name,goods_publisher,goods_category,goods_describe) LIKE ?,1,0)) "+
				") AS keyweight "+
				"from goods_info where "+
				"CONCAT_WS(\" \",goods_name,goods_publisher,goods_category,goods_describe) "+
				"REGEXP"+ "\""+REGEXP_string+"\" "+
				"ORDER BY keyweight DESC "+
				"LIMIT 10";
		
		String[] params = {"%"+keyArrays[0]+"%","%"+keyArrays[1]+"%","%"+keyArrays[2]+"%","%"+keyArrays[3]+"%","%"+keyArrays[4]+"%","%"+keyArrays[5]+"%"};
		System.out.println("params  :"+ Arrays.toString(params));
		System.out.println("sql  :"+ sql);
		
		IR_GoodsIdS = db.getList(sql, params);  //获取到了推荐商品的LIST  <=10条
		AllIR = this.GetAll_IR_ById();  //获取本表的一个用户的所有信息
		
		if(AllIR.size()==0||AllIR==null){  //推荐表为空,则推荐表使用插入方法，初始化插入10条信息
			if(IR_GoodsIdS.size()<10){  //推荐数量不足10条，只可能是初始的时候,两段拼凑
				for(int i=0;i<IR_GoodsIdS.size();i++){
					Map IR_GoodsId = (Map) IR_GoodsIdS.get(i);  //把LIST的对象一个个取出来转化为MAP				
					this.setCircle_id(i+1);
					this.setRecommend_goods_id((String)IR_GoodsId.get("goods_id"));  //3 
					result = this.AddOneInfo();
					if(result==1){System.out.println("获取到的推荐goods_id为：" + this.getRecommend_goods_id());}	
				}
				for(int j=IR_GoodsIdS.size();j<10;j++){
					this.setCircle_id(j+1);
					this.setRecommend_goods_id("NULL");  //3 
					result = this.AddOneInfo();
					if(result==1){System.out.println("MAP不足，自动添加ID为：" + this.getRecommend_goods_id());}	
				}		
			}
			else
			{   //推荐的数量大于等于10条
				for(int i=0;i<10;i++){
					Map IR_GoodsId = (Map) IR_GoodsIdS.get(i);  //这时候get(i)可以取到0-9，不会报错
					this.setCircle_id(i+1);
					this.setRecommend_goods_id((String)IR_GoodsId.get("goods_id"));  //3 
					result = this.AddOneInfo();
					if(result==1){System.out.println("获取到的推荐goods_id为：" + this.getRecommend_goods_id());}	
				}
			}
		}
			
		if(result==1){System.out.println(this.user_id + ": IntelligentRecommendation 初始化成功！");}		
		
		//推荐表已经有信息。采用更新的方法
		else{
			for(int i=0;i<AllIR.size();i++){  
				Map IR_GoodsId = (Map) IR_GoodsIdS.get(i);  //把LIST的对象一个个取出来转化为MAP
				if(!IR_GoodsId.isEmpty()){ //map不为空
					this.setRecommend_goods_id((String)IR_GoodsId.get("goods_id"));  //3 
					this.setCircle_id(i+1);
					result = this.Update_IR_RIdAndKey();
					if(result==1){System.out.println("更新了推荐表中的goods_id为：" + this.getRecommend_goods_id());}					
				}
			}
		}
		
		db.close();
		return IR_GoodsIdS;
	}
	
		

}

