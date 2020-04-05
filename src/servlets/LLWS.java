package servlets;

import beans.FriendMessage;
import net.sf.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/LLWS/{userId}")
public class LLWS {


    //ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
    public static ConcurrentHashMap<String,Session> mapUS = new ConcurrentHashMap<String,Session>();
	public static ConcurrentHashMap<Session,String> mapSU = new ConcurrentHashMap<Session,String>();

    //连接建立成功调用的方法
	@OnOpen
	public void onOpen(Session session,@PathParam("userId") String userId) {
		String jsonString="{'content':'online','id':"+userId+",'type':'onlineStatus'}";
		session.setMaxIdleTimeout(1800000);
		for (String value : mapSU.values()) {
			try {
				mapUS.get(value).getBasicRemote().sendText(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mapUS.put(userId+"",session);
		mapSU.put(session,userId+"");
		//更新redis中的用户在线状态
		System.out.println("用户"+userId+"进入llws,当前在线人数为" + mapUS.size() );
	}
  
    //连接关闭调用的方法 
    @OnClose  
    public void onClose(Session session) { 
    	String userId=mapSU.get(session);
    	if(userId!=null&&userId!=""){
        	mapUS.remove(userId);
        	mapSU.remove(session);
			System.out.println("用户"+userId+"退出llws,当前在线人数为" + mapUS.size());
    	}
    }  
  
    // 收到客户端消息后调用的方法 
    @OnMessage  
    public void onMessage(String message, Session session) {
    	   JSONObject jsonObject=JSONObject.fromObject(message);
           String type = jsonObject.getJSONObject("to").getString("type");
           if(type.equals("onlineStatus")){
				for(Session s:session.getOpenSessions()){		//循环发给所有在线的人
				   JSONObject toMessage=new JSONObject();
	               toMessage.put("id", jsonObject.getJSONObject("mine").getString("id"));  
	               toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));
	               toMessage.put("type",type);
					for (String value : mapSU.values()) {
						try {
							mapUS.get(value).getBasicRemote().sendText(toMessage.toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
           }else{
               String toId=jsonObject.getJSONObject("to").getString("id");
               SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
               Date date = new Date();
               String time=df.format(date);
               jsonObject.put("time", time); 
               JSONObject toMessage=new JSONObject();
               toMessage.put("avatar", jsonObject.getJSONObject("mine").getString("avatar"));  
               toMessage.put("type",type);      
               toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));   
               toMessage.put("timestamp",date.getTime()); 
               toMessage.put("time",time); 
               toMessage.put("mine",false);
               toMessage.put("username",jsonObject.getJSONObject("mine").getString("username"));   
    	   	    if(type.equals("friend")||type.equals("fankui")){
    	   	    	   toMessage.put("id", jsonObject.getJSONObject("mine").getString("id"));
    	   	    }else{
    	   	    	   toMessage.put("id", jsonObject.getJSONObject("to").getString("id"));
    	   	    }        
               switch (type) {
    		   		case "friend":           							 //单聊
						FriendMessage friendMessage = new FriendMessage();
    			    	  if(mapUS.containsKey(toId+"")){               //如果在线，及时推送
							  session = mapUS.get(toId+"");
							  synchronized(session) {
							  	try{
									session.getBasicRemote().sendText(toMessage.toString());//发送消息给对方
								}catch (Exception e){
									e.printStackTrace();
								}

							  }
							  friendMessage.setIs_read(1);
							  System.out.println("单聊-来自客户端的消息:" + toMessage.toString());
    			    	  }else{                                        //如果不在线 就记录到数据库，下次对方上线时推送给对方。
							  friendMessage.setIs_read(0);
							  System.out.println("单聊-对方不在线，消息已存入数据库:" + toMessage.toString());
    			    	  }
						friendMessage.setFrom_user_id(jsonObject.getJSONObject("mine").getString("id"));
						friendMessage.setTo_user_id(jsonObject.getJSONObject("to").getString("id"));
						friendMessage.setContent(toMessage.getString("content"));
						friendMessage.setSend_time(time);
						try{
							friendMessage.addOneGoodsPicture();
						}catch (Exception e){
							e.printStackTrace();
						}
    		   			break;
    		   		default:
    		   			break;
       		 }       
           }

    }  
  
    /** 
     * 发生错误时调用 
     * @param session 
     * @param error 
     */  
    @OnError  
    public void onError(Session session, Throwable error) {
    	String userId=mapSU.get(session);
    	if(userId!=null&&userId!=""){
        	mapUS.remove(userId);
        	mapSU.remove(session);
			System.out.println("用户"+userId+"退出llws！当前在线人数为" + mapUS.size());
    	}
		System.out.println("llws发生错误!");
        error.printStackTrace();
    }  
    
    /** 
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。 
     */  
    public void sendMessage(Session session, String message) {
           session.getAsyncRemote().sendText(message);  
    }  
	
}
