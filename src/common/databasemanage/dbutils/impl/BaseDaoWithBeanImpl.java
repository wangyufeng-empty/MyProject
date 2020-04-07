package common.databasemanage.dbutils.impl;

import cn.sxf.jdbc.TxQueryRunner;
import common.databasemanage.dbutils.IbaseDaoWithBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDaoWithBeanImpl<T> extends BaseDaoImpl implements IbaseDaoWithBean<T> {
	//自己对dbutils的封装
	public QueryRunner qr = new TxQueryRunner();

	public List<T> findBeanListBySql(String sql) throws Exception {
		//泛型转换
		//ParameterizedType表示参数类型，即T。而参数可以有多个，我们取第一个也就是T
		ParameterizedType parameterizedType =
				(ParameterizedType) this.getClass().getGenericSuperclass();//这里打印parameterizedType得到的是BaseAction<ElecTest>
		//得到泛型T的真实类型的Class对象，用于通过反射创建实例
		Class entityClass = (Class) parameterizedType.getActualTypeArguments()[0];////获取真实的类型参数们！就是<>之内的类型
		List<T> List = qr.query(sql, new BeanListHandler<T>(entityClass));
		return List;
	}

	public List<T> findBeanListBySql(String sql, Object[] params)
			throws Exception {
		//泛型转换
		//ParameterizedType表示参数类型，即T。而参数可以有多个，我们取第一个也就是T
		ParameterizedType parameterizedType =
				(ParameterizedType) this.getClass().getGenericSuperclass();//这里打印parameterizedType得到的是BaseAction<ElecTest>
		//得到泛型T的真实类型的Class对象，用于通过反射创建实例
		Class entityClass = (Class) parameterizedType.getActualTypeArguments()[0];////获取真实的类型参数们！就是<>之内的类型
		List<T> List = qr.query(sql, new BeanListHandler<T>(entityClass),params);
		return List;
	}
}
