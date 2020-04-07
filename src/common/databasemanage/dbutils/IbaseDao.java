package common.databasemanage.dbutils;

import java.util.List;
import java.util.Map;

public interface IbaseDao {
	/**
	 * 根据传入sql查询对应Map的List集合
	 * @param sql
	 */
	List<Map<String, Object>> findMapListBySql(String sql) throws Exception;
	/**
	 * 根据传入sql和参数数组查询对应Map的List集合，防止sql注入。
	 * @param sql params
	 */
	List<Map<String, Object>> findMapListBySql(String sql, Object[] params) throws Exception;

	/**
	 * 根据传入sql和参数数组查询对应的Map集合，防止sql注入。
	 * @param sql params
	 */
	Map<String, Object> findMapBySql(String sql, Object[] params) throws Exception;
	/**
	 * 根据传入sql查询对应的Map集合
	 * @param sql
	 */
	Map<String, Object> findMapBySql(String sql) throws Exception;

	/**
	 * 根据传入sql查询对应的Object对象
	 * 单行单列,一般用于聚集查询，例如select count(*) from tab_student。
	 * @param sql params
	 * @return Object
	 */
	Object findObjectBySql(String sql) throws Exception;
	/**
	 * 根据传入sql和参数数组查询对应的Object对象，防止sql注入。
	 * 单行单列,一般用于聚集查询，例如select count(*) from tab_student。
	 * @param sql params
	 * @return Object
	 */
	Object findObjectBySql(String sql, Object[] params) throws Exception;

	/**
	 * 添加一条记录
	 * @param sql
	 */
	void addOrUpdateBySql(String sql) throws Exception;
	/**
	 * 添加一条记录,防止sql注入
	 * @param sql params
	 */
	void addOrUpdateBySql(String sql, Object[] params) throws Exception;

	/**
	 * 删除记录
	 * @param sql
	 */
	void deleteBySql(String sql) throws Exception;
	/**
	 * 删除记录,防止sql注入
	 * @param sql params
	 */
	void deleteBySql(String sql, Object[] params) throws Exception;
}

