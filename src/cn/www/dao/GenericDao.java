package cn.www.dao;

import java.io.Serializable;
import java.util.List;

import cn.www.utils.query.Page;
import cn.www.utils.query.QueryOrder;
import cn.www.utils.query.QueryParam;

/**
 * 范型DAO接口
 */
public interface GenericDao<T, PK extends Serializable> {
	/**
	 * 持久化新创建的瞬时(transient)对象
	 * 
	 * @param transientObject
	 * @return PK 主键
	 */
	PK save(T transientObject);

	/**
	 * 更新已修改的脱管(detached)对象
	 * 
	 * @param detachedObject
	 */
	void update(T detachedObject);

	/**
	 * 删除持久化(persistent)对象
	 * 
	 * @param persistentObject
	 */
	void delete(T persistentObject);

	/**
	 * 删除主键id所对应的持久化(persistent)对象
	 * 
	 * @param id
	 */
	void deleteByPK(PK id);

	/**
	 * 获得主键id所对应的持久化(persistent)对象
	 * 
	 * @param id
	 * @return
	 */
	T findByPK(PK id);

	/**
	 * 获取所有对象
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 根据给定的实例查找对象
	 * 
	 * @param exampleInstance
	 * @return
	 */
	List<T> findAfterExample(T exampleInstance);

	/**
	 * 根据传入的类和id查找对象
	 * 
	 * @param <E>
	 * @param _clz
	 * @param id
	 * @return
	 */
	<E> E findByPK(Class<E> _clz, PK id);

	/**
	 * 根据传入的类和id删除对象
	 * 
	 * @param <E>
	 * @param _clz
	 * @param id
	 */
	<E> void deleteByPK(Class<E> _clz, PK id);

	/**
	 * 根据定制的组合条件查询
	 * 
	 * @param <E>
	 * @param
	 * @param _params
	 *            查询参数,如没有传空list
	 * @param _orders
	 *            查询排序,如没有传空list
	 * @return
	 */
	<E> List<E> findByCustomized(Class<E> _clz, List<QueryParam> _params, List<QueryOrder> _orders);

	/**
	 * 根据SQL条件查询
	 */
	<E> List<E> findByCustomizedSQL(Class<E> _clz, String sqlparams);

	/**
	 * 根据定制的组合条件查询分页
	 * 
	 * @param <E>
	 * @param _pageNum
	 *            页码
	 * @param
	 * @param _params
	 *            查询参数,如没有传空list
	 * @param _orders
	 *            查询排序,如没有传空list
	 * @return
	 */
	<E> Page<E> findPageByCustomized(int _pageNum, int _pageSize, Class<E> _clz, List<QueryParam> _params,
			List<QueryOrder> _orders);

}
