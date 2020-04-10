package dao.impl;

import beans.FriendMessage;
import common.databasemanage.dbutils.impl.BaseDaoWithBeanImpl;
import dao.IFriendMessageDao;

/**
 * @Package: dao.impl
 * @Author: sxf
 * @Date: 2020-4-7
 * @Description:
 */
public class FriendMessageDaoImpl extends BaseDaoWithBeanImpl<FriendMessage> implements IFriendMessageDao {
    private volatile static FriendMessageDaoImpl instance = null;

    // 私有化构造方法
    private FriendMessageDaoImpl() {

    }
    public static FriendMessageDaoImpl getInstance() {
        if (instance == null) {
            synchronized (FriendMessageDaoImpl.class) {
                if (instance == null) {
                    instance = new FriendMessageDaoImpl();
                }
            }

        }
        return instance;
    }
}
