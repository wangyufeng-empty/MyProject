package service.impl;

import beans.FriendMessage;
import dao.IFriendMessageDao;
import dao.impl.FriendMessageDaoImpl;
import service.IFriendMessageService;

import java.util.List;

/**
 * @Package: service.impl
 * @Author: sxf
 * @Date: 2020-4-7
 * @Description:
 */
public class FriendMessageServiceImpl implements IFriendMessageService {
    private IFriendMessageDao friendMessageDao = FriendMessageDaoImpl.getInstance();

    private volatile static FriendMessageServiceImpl instance = null;
    // 私有化构造方法
    private FriendMessageServiceImpl() {

    }
    public static FriendMessageServiceImpl getInstance() {
        if (instance == null) {
            synchronized (FriendMessageServiceImpl.class) {
                if (instance == null) {
                    instance = new FriendMessageServiceImpl();
                }
            }
        }
        return instance;
    }

    public List<FriendMessage> findBeanListBySql(String sql, Object[] params) throws Exception {
        return friendMessageDao.findBeanListBySql(sql,params);
    }

}
