/**
 * 
 */
package faceLogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Base64Util;
import utils.FileUtil;
import utils.GsonUtils;
import utils.HttpUtil;
import com.baidu.ai.aip.auth.*;
/**
 * @author 王宇峰
 *
 */
public class faceMatch {
	
	    public static String match() {
	        // 请求url
	        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
	        try {
	        	/*读取图片并将之转化为BASE64格式的字符串*/
	            byte[] bytes1 = FileUtil.readFileByBytes("G:\\Workspaces\\人脸识别比对的图片\\范冰冰.jpg");//【本地图片路径1】
	            byte[] bytes2 = FileUtil.readFileByBytes("G:\\Workspaces\\人脸识别比对的图片\\范冰冰1.jpg");//【本地图片路径2】
	            /*转化为BASE64格式*/
	            String image1 = Base64Util.encode(bytes1);
	            String image2 = Base64Util.encode(bytes2);

	            List<Map<String, Object>> images = new ArrayList<>();

	            Map<String, Object> map1 = new HashMap<>();
	            map1.put("image", image1);
	            map1.put("image_type", "BASE64");
	            map1.put("face_type", "LIVE");
	            map1.put("quality_control", "NORMAL");
	            map1.put("liveness_control", "NORMAL");

	            Map<String, Object> map2 = new HashMap<>();
	            map2.put("image", image2);
	            map2.put("image_type", "BASE64");
	            map2.put("face_type", "LIVE");
	            map2.put("quality_control", "NORMAL");
	            map2.put("liveness_control", "NORMAL");

	            images.add(map1);
	            images.add(map2);

	            String param = GsonUtils.toJson(images);
	            AuthService auth = new AuthService();
	            String accessToken = auth.getAuth();
	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	            
	            /*@param: param:json数组*/
	            String result = HttpUtil.post(url, accessToken, "application/json", param);
	            System.out.println(result.toString());
	            String score=result.split(",")[5].split(":")[2];
	            return score;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    public static void main(String[] args) {
	        String score = faceMatch.match();
	        
	        if(Double.parseDouble(score.trim())>70) {
	        	System.out.println("分数："+score+",可能是同一个人！");
	        }
	        else {
	        	System.out.println("分数："+score+",可能不是同一个人！");
			}
	        
	    }
}
