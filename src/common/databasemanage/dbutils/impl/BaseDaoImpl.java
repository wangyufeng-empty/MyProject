package common.databasemanage.dbutils.impl;

import cn.sxf.jdbc.TxQueryRunner;
import common.databasemanage.dbutils.IbaseDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;
import java.util.Map;

public class BaseDaoImpl implements IbaseDao {
	//自己对dbutils的封装
	public QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 添加一条记录
	 */
	public void addOrUpdateBySql(String sql) throws Exception {
		qr.update(sql);
	}
	/**
	 * 删除记录
	 */
	public void deleteBySql(String sql) throws Exception {
		qr.update(sql);
	}
	/**
	 * 添加一条记录,防止sql注入
	 * @param sql params
	 */
	public void addOrUpdateBySql(String sql, Object[] params) throws Exception {
		qr.update(sql, params);
	}
	
	/**
	 * 删除记录,防止sql注入
	 * @param sql params
	 */
	public void deleteBySql(String sql, Object[] params) throws Exception {
		qr.update(sql, params);
	}
	/**
	 * 根据传入sql查询对应Map的List集合
	 * @param sql
	 */
	public List<Map<String,Object>> findMapListBySql(String sql) throws Exception {
		List<Map<String, Object>> list = qr.query(sql,new MapListHandler());
		return list;
	}
	/**
	 * 根据传入sql和参数数组查询对应Map的List集合，防止sql注入。
	 * @param sql params
	 */
	public List<Map<String,Object>> findMapListBySql(String sql, Object[] params)
			throws Exception {
		List<Map<String, Object>> list = qr.query(sql,new MapListHandler(),params);
		return list;
	}
	/**
	 * 根据传入sql和参数数组查询对应的Map集合，防止sql注入。
	 * @param sql params
	 */
	public Map<String, Object> findMapBySql(String sql, Object[] params)
			throws Exception {
		Map<String, Object> map = qr.query(sql, new MapHandler(),params);
		return map;
	}
	/**
	 * 根据传入sql查询对应的Map集合
	 * @param sql
	 */
	public Map<String, Object> findMapBySql(String sql) throws Exception {
		Map<String, Object> map = qr.query(sql, new MapHandler());
		return map;
	}
	/**
	 * 根据传入sql查询对应的Object对象
	 * 单行单列,一般用于聚集查询，例如select count(*) from tab_student。
	 * @param sql params
	 * @return Object
	 */
	public Object findObjectBySql(String sql) throws Exception {
		/*
		 * 对聚合函数的查询结果，有的驱动返回的是Long，有的返回的是BigInteger，
		 * 所以我们可以把它转换成Number，Number是Long和BigInteger的父类！
		 * 然后我们再调用Number的intValue()或longValue()方法就OK了。
		 */
		Object o = qr.query(sql, new ScalarHandler());
		return o;
	}
	/**
	 * 根据传入sql和参数数组查询对应的Object对象，防止sql注入。
	 * 单行单列,一般用于聚集查询，例如select count(*) from tab_student。
	 * @param sql params
	 * @return Object
	 */
	public Object findObjectBySql(String sql, Object[] params) throws Exception {
		/*
		 * 对聚合函数的查询结果，有的驱动返回的是Long，有的返回的是BigInteger，
		 * 所以我们可以把它转换成Number，Number是Long和BigInteger的父类！
		 * 然后我们再调用Number的intValue()或longValue()方法就OK了。
		 */
		Object o = qr.query(sql, new ScalarHandler(),params);
		return o;
	}

}
