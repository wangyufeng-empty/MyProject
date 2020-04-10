package servlets;

import beans.DBUtil;
import beans.FriendMessage;
import net.sf.json.JSONObject;
import service.IFriendMessageService;
import service.impl.FriendMessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package: servlets
 * @Date: 2020-4-5
 * @Description: 离线消息、历史消息
 */
public class MessageController extends HttpServlet {
    private IFriendMessageService friendMessageService = FriendMessageServiceImpl.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getParameter("url") == null ? "" : request.getParameter("url").toString();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/json;charset=utf-8");
            out = response.getWriter();
            //获取离线消息-----------------------
            if(url.equals("OfflineMsg")){
                String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                    String testSql = "select fm.*,ui.user_name from friend_message fm LEFT JOIN user_info ui ON fm.from_user_id = ui.user_id WHERE to_user_id = ? and is_read = ?";
                    Object [] params = {userId,1};
                    List<FriendMessage> friendMessageList = friendMessageService.findBeanListBySql(testSql,params);

                    List<JSONObject> retlist=new ArrayList<JSONObject>();
                    //DBUtil db = new DBUtil();
                    DBUtil db = DBUtil.getDBUtil();
                    conn = db.getConnection();
                    String sql = "select fm.*,ui.user_name from friend_message fm LEFT JOIN user_info ui ON fm.from_user_id = ui.user_id WHERE to_user_id = ? and is_read = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, userId);
                    pstmt.setInt(2, 0);
                    rs = pstmt.executeQuery();
                    while(rs.next()){
                        String send_time = rs.getString("send_time");
                        String content = rs.getString("content");
                        String from_user_id = rs.getString("from_user_id");
                        String user_name = rs.getString("user_name");

                        ///封装好友消息
                        JSONObject toMessage=new JSONObject();
                        toMessage.put("avatar", "https://q.qlogo.cn/qqapp/101235792/B704597964F9BD0DB648292D1B09F7E8/100");//头像
                        toMessage.put("type","friend");
                        toMessage.put("timestamp",format.parse(send_time).getTime());
                        toMessage.put("time",send_time);
                        toMessage.put("content",content);
                        toMessage.put("mine",false);
                        toMessage.put("username",user_name == null ? "" :user_name);
                        toMessage.put("id", from_user_id);
                        toMessage.put("fromid", from_user_id);
                        retlist.add(toMessage);
                    }
                    //将状态更新成已读
                    String updateSql = "update friend_message set is_read = ? where to_user_id = ?";
                    pstmt = conn.prepareStatement(updateSql);
                    pstmt.setInt(1, 1);
                    pstmt.setString(2, userId);
                    pstmt.executeUpdate();
                    resultMap.put("data",retlist);
                    resultMap.put("status", "success");
                    resultMap.put("msg", "成功");
                    out.print(JSONObject.fromObject(resultMap).toString());
                }catch (Exception e){
                    e.printStackTrace();
                    resultMap.put("msg",e.getMessage());
                    resultMap.put("status", "failed");
                    out.print(JSONObject.fromObject(resultMap).toString());
                }finally {
                    try{
                        if(rs != null)
                            rs.close();
                            rs = null;
                        if(pstmt != null)
                            pstmt.close();
                            pstmt = null;
                        if(conn != null)
                            conn.close();
                            conn = null;
                    }catch(SQLException s){
                        s.printStackTrace();
                    }
                }
            }
            
           //历史聊天记录总数  -----------------------------------------
            if(url.equals("getMsgTotal")){
                String mineId = request.getParameter("mineId") == null ? "" : request.getParameter("mineId").toString();
                String friendId = request.getParameter("friendId") == null ? "" : request.getParameter("friendId").toString();
                String type = request.getParameter("type") == null ? "" : request.getParameter("type").toString();

                if(type.trim().equals("friend")){
                    Connection conn = null;
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    try {
                        List<JSONObject> retlist=new ArrayList<JSONObject>();
                        //DBUtil db = new DBUtil();
                        DBUtil db = DBUtil.getDBUtil();
                        conn = db.getConnection();
                        String sql = "select count(id) total from friend_message where (from_user_id = ? and to_user_id = ?) or (from_user_id = ? and to_user_id = ?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, friendId);
                        pstmt.setString(2, mineId);
                        pstmt.setString(3, mineId);
                        pstmt.setString(4, friendId);
                        rs = pstmt.executeQuery();
                        if(rs.first()) {
                            resultMap.put("total",rs.getInt("total"));
                        }else{
                            resultMap.put("total",0);
                        }
                        resultMap.put("status", "success");
                        resultMap.put("msg", "成功");
                        out.print(JSONObject.fromObject(resultMap).toString());
                    }catch (Exception e){
                        e.printStackTrace();
                        resultMap.put("msg","获取聊天记录失败");
                        resultMap.put("status", "failed");
                        out.print(JSONObject.fromObject(resultMap).toString());
                    }finally {
                        try{
                            if(rs != null)
                                rs.close();
                            rs = null;
                            if(pstmt != null)
                                pstmt.close();
                            pstmt = null;
                            if(conn != null)
                                conn.close();
                            conn = null;
                        }catch(SQLException s){
                            s.printStackTrace();
                        }
                    }
                }else{
                    resultMap.put("msg","参数解析失败");
                    resultMap.put("status", "failed");
                    out.print(JSONObject.fromObject(resultMap).toString());
                }
            }
          //历史聊天记录-----------------------------------------
            if(url.equals("msgHis")){
                String mineId = request.getParameter("mineId") == null ? "" : request.getParameter("mineId").toString();
                String friendId = request.getParameter("friendId") == null ? "" : request.getParameter("friendId").toString();
                String type = request.getParameter("type") == null ? "" : request.getParameter("type").toString();

                int cur = request.getParameter("cur") == null ? 1 : Integer.parseInt(request.getParameter("cur").toString());
                int pageSize = request.getParameter("pageSize") == null ? 1 : Integer.parseInt(request.getParameter("pageSize").toString());
                int stratRow = (cur -1)*pageSize;
                if(type.trim().equals("friend")){
                    Connection conn = null;
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    try {
                        List<JSONObject> retlist=new ArrayList<JSONObject>();
                       /* DBUtil db = new DBUtil();*/
                        DBUtil db = DBUtil.getDBUtil();
                        conn = db.getConnection();
                        String sql = "SELECT fm.from_user_id AS id,( SELECT user_name FROM user_info WHERE user_id = fm.from_user_id ) AS username,fm.content,fm.send_time FROM friend_message fm " +
                                "WHERE( fm.from_user_id = ? AND fm.to_user_id = ? ) OR ( fm.from_user_id = ? AND fm.to_user_id = ? ) ORDER BY fm.send_time DESC LIMIT ?,?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, friendId);
                        pstmt.setString(2, mineId);
                        pstmt.setString(3, mineId);
                        pstmt.setString(4, friendId);
                        pstmt.setInt(5, stratRow);
                        pstmt.setInt(6, pageSize);
                        rs = pstmt.executeQuery();
                        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                        while(rs.next()){
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("id",rs.getString("id"));
                            map.put("username",rs.getString("username"));
                            map.put("avatar", "https://q.qlogo.cn/qqapp/101235792/B704597964F9BD0DB648292D1B09F7E8/100");//头像
                            map.put("content",rs.getString("content"));
                            map.put("timestamp",format.parse(rs.getString("send_time")).getTime());
                            resultList.add(map);
                        }
                        //给结果集排序
                        Collections.sort(resultList,new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                                long timestamp1= Long.parseLong(m1.get("timestamp").toString());
                                long timestamp2= Long.parseLong(m2.get("timestamp").toString());
                                if(timestamp1<timestamp2){
                                    return -1;
                                }
                                return 1;
                            }
                        });
                        resultMap.put("data",resultList);
                        resultMap.put("status", "success");
                        resultMap.put("msg", "成功");
                        out.print(JSONObject.fromObject(resultMap).toString());
                    }catch (Exception e){
                        e.printStackTrace();
                        resultMap.put("msg","获取聊天记录失败");
                        resultMap.put("status", "failed");
                        out.print(JSONObject.fromObject(resultMap).toString());
                    }finally {
                        try{
                            if(rs != null)
                                rs.close();
                            rs = null;
                            if(pstmt != null)
                                pstmt.close();
                            pstmt = null;
                            if(conn != null)
                                conn.close();
                            conn = null;
                        }catch(SQLException s){
                            s.printStackTrace();
                        }
                    }
                }else{
                    resultMap.put("msg","参数解析失败");
                    resultMap.put("status", "failed");
                    out.print(JSONObject.fromObject(resultMap).toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
