/**
 * 
 */
package faceLogin;
import java.io.IOException;
/*
*@author 王宇峰
*@time 2020年5月30日
*@filename faceSearch.java
*package faceLogin;
*public class faceSearch{ }
*/
/**
 * @author 王宇峰
 *
 */
import java.util.*;
import com.baidu.ai.aip.auth.AuthService;


import utils.*;
import net.sf.json.JSONArray;

/**
* 人脸搜索
*/
public class faceSearch {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
     * @throws Exception 
    */
	
	/*函数待传入参数：
	 * @param image:前端解码好的BASE64图片编码
	 * @param user_id : 用户学号
	 * */
    public static String search(String image,String user_id) 
    throws Exception {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        
        /*读取图片并将之转化为BASE64格式的字符串*/
//        byte[] bytes = FileUtil.readFileByBytes("G:\\Workspaces\\人脸识别比对的图片\\刘德华1.jfif");//【本地图片路径1】
//        String image = Base64Util.encode(bytes);
        
        
        
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image); //图片base64数据
            map.put("liveness_control", "NORMAL");  //活体检测控制一般的
            map.put("group_id_list", "userLogin");  //指定用户组group 人脸库总已经存在的用户组
            map.put("image_type", "BASE64");     //图片类型，这里转化过的base64
            map.put("quality_control", "NORMAL");   //图片质量控制
            map.put("user_id", user_id);

            String param = GsonUtils.toJson(map);
            AuthService auth = new AuthService();
            String accessToken = auth.getAuth();
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
	 * @param string
	 * @return
	 */
	private static JSONArray fromObject(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws Exception {
//        String score =  faceSearch.search();
//        if(Double.parseDouble(score)<80) {
//        	System.out.println("分数score为: "+score+"，登录失败！");
//        }
//        else {
//        	System.out.println("分数score为: "+score+"，登录成功！");
//		}
        
    }
    
}
