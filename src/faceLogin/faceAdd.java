/**
 * 
 */
package faceLogin;
/*
*@author 王宇峰
*@time 2020年5月30日
*@filename faceAdd.java
*package faceLogin;
*public class faceAdd{ }
*/
/**
 * @author 王宇峰
 *
 */
import utils.*;
import java.util.*;
import com.baidu.ai.aip.auth.AuthService;
import net.sf.json.JSONObject;
/**
* 人脸注册
*/
public class faceAdd {

	
	/*函数待传入参数：
	 * @param image:前端解码好的BASE64图片编码
	 * @param user_id : 用户学号
	 * @param user_info: 用户姓名
	 * */
    public static String add(String image,String user_id,String user_name) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
        	/*读取图片并将之转化为BASE64格式的字符串*/
//            byte[] bytes = FileUtil.readFileByBytes("G:\\Workspaces\\人脸识别比对的图片\\刘德华.jfif");//【本地图片路径1】
//            String image = Base64Util.encode(bytes);
        	
        	
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);//这里的image可以是传入face_token，人脸图片的唯一标识
            map.put("image_type", "BASE64");
            map.put("group_id", "userLogin");
            map.put("user_id", user_id);
            map.put("user_info", user_name);
            map.put("liveness_control", "HIGH");          
            map.put("quality_control", "NORMAL");
            map.put("action_type", "REPLACE");  //用户重复注册时，图片将替换

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
           
            return result;  //返回结果json字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        String face_token =  faceAdd.add("160104010002","刘德华");
//        System.out.println("face_token: "+face_token);
    }
}