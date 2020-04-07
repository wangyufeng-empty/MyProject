package common.databasemanage.dbutils;

import java.util.List;

public interface IbaseDaoWithBean<T> extends  IbaseDao {
	/**
	 * 根据传入sql查询对应javaBean的List集合
	 * @param sql
	 */
	List<T> findBeanListBySql(String sql) throws Exception;
	/**
	 * 根据传入sql和参数数组查询对应javaBean的List集合，防止sql注入。
	 * @param sql params
	 */
	List<T> findBeanListBySql(String sql, Object[] params) throws Exception;
}

