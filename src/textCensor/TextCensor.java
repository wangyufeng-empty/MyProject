
package textCensor;

/**
 * @author 王宇峰
 *
 */
import com.baidu.aip.contentcensor.AipContentCensor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class TextCensor {
//	/*函数待传入参数：
//	 * @param text:前端传入的待检测字符串
//	 * */
	//设置APPID/AK/SK  静态类属性
    public static final String APP_ID = "20207331";
    public static final String API_KEY = "og8mzjel72zrReojxOioIRYw";
    public static final String SECRET_KEY = "YEk9BE9uFyvTuQX4KtnrsOZ6QpmziGT1";
    
    public static JSONObject censor(String text) {

	    /*创建文本审核对象*/
	    AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
	    
	    /*调用SDK接口*/
	    org.json.JSONObject response = client.textCensorUserDefined(text);
	    
	    String result = response.toString();
	    
	    // 调用接口
		String resultMessage = ""; //待返回的信息
		String resultState = "";  //待返回结果  通过：1   拦截：0
		JSONObject return_JsonObject = new JSONObject();
	    System.out.println("result:  "+result);
	    /*将json字符串转化为json对象*/
	    JSONObject resultObject = JSONObject.fromObject(result);
	    /*1.提取conclusionType进行判断*/
	    String conclusionType = resultObject.getString("conclusionType");
	    /*1.1如果判断疑似或者不合规*/
	    if (conclusionType.equals("2")||conclusionType.equals("3")) {
	    	resultState = "0";
	    	resultMessage += "文本存在以下违规项：==><br>";
			/*1.1.1解析data数据*/
	    	String data = resultObject.getString("data");
	    	JSONArray dataArray = JSONArray.fromObject(data);
	    	/*遍历data的json数组*/
	    	if(dataArray.size()>0) {
	    		for (int i = 0; i < dataArray.size(); i++) {
					JSONObject dataObject = dataArray.getJSONObject(i);//获取到第i个json对象
					/*分别解析每个data里面的json对象*/
					String type = dataObject.getString("type");
					/*当type=11时subType取值含义：0:百度官方默认违禁词库*/
					if (type.equals("11")) {
						resultMessage =  resultMessage+"使用违禁词";
					}
					/*当type=12时subType取值含义：0:低质灌水、1:暴恐违禁、2:文本色情、3:政治敏感、4:恶意推广、5:低俗辱骂*/
					else if(type.equals("12")) {
						/*msg	String : 不合规项描述信息*/
						String msg = dataObject.getString("msg");
						resultMessage = resultMessage + msg + "";
					}
					/*hits	Array	:	命中关键词信息*/					
					/*解析命中的关键词信息*/
					String hits = dataObject.getString("hits");
					JSONArray hitsArray = JSONArray.fromObject(hits);
					JSONObject hitsObject = hitsArray.getJSONObject(0); //取到hits-list里面的第一个json对象
					/*继续解析hitsObject中的words数组*/
					String words = hitsObject.getString("words");
					JSONArray wordsArray = JSONArray.fromObject(words);
	//				System.out.println("words : "+words);/*->>>>>>>>>>>>>>>>>>*/
					if(wordsArray.size()>=3) {
						resultMessage =  resultMessage+"，例如："+wordsArray.getString(0)+"；"
													+wordsArray.getString(1)+"；"+wordsArray.getString(2)+"......<br>";
					}
					else if(wordsArray.size()>0&&wordsArray.size()<3){
						resultMessage =  resultMessage+"，例如：";
						for (int j = 0; j < wordsArray.size(); j++) {
							resultMessage += wordsArray.getString(j)+"；";
						}
						resultMessage =  resultMessage+"<br>";
					}
					else {
						resultMessage =  resultMessage+"<br>";
					}
				}// end dataArray
	    	} //end data.size()>0
	    	else {
	    		resultMessage =  resultMessage+"言论涉嫌违禁！";
			}
		} //end 违禁
	    /*否则为合格文本*/
	    else 
	    {
	    	resultMessage += "文本言论合规，给予通过！";
	    	resultState = "1";
		}
	    return_JsonObject.put("resultMessage", resultMessage);
	    return_JsonObject.put("resultState", resultState);
	    
	    System.out.println("return_JsonObject:  "+return_JsonObject.toString());
	   
	    return return_JsonObject;
    }
    public static void main(String[] args) {
//    	JSONObject resultObject = new JSONObject();
//    	String textString ="你是什么东西，你不是东西";
//    	resultObject = TextCensor.censor(textString);
//    	String resultState = resultObject.getString("resultState");
//    	String resultMessage = resultObject.getString("resultMessage");
//    	if(resultState.equals("0")) {
//    		resultMessage = "信息已被拦截！"+resultMessage;
//    	}
//    	System.out.println("你的语句为："+textString);
//    	System.out.println(resultMessage);
    }
}
