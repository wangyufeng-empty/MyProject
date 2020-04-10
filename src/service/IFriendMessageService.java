package service;

import beans.FriendMessage;

import java.util.List;

/**
 * @Package: service
 * @Author: sxf
 * @Date: 2020-4-7
 * @Description:
 */
public interface IFriendMessageService {
    List<FriendMessage> findBeanListBySql(String sql, Object[] params) throws Exception;
}
