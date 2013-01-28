package cn.www.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.www.utils.query.OP;
import cn.www.utils.query.Page;
import cn.www.utils.query.QueryOrder;
import cn.www.utils.query.QueryParam;

/**
 * 范型Dao的Hibernate实现
 */
public abstract class GenericHibernateDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements
		GenericDao<T, PK> {
	/**
	 * 持久化对象所对应的Class
	 */
	private Class<T> pojoClass = null;

	/**
	 * 默认构造函数，用于获取范型T的带有类型化信息的Class对象
	 */
	public GenericHibernateDaoImpl() {
	}

	/**
	 * 获得Dao对应的pojo类型
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getPojoClass() {
		if (this.pojoClass == null) {
			this.pojoClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
		return this.pojoClass;
	}

	/**
	 * 获得Dao对应的POJO类型名
	 * 
	 * @return
	 */
	public String getPojoClassName() {
		return getPojoClass().getName();
	}

	/**
	 * 持久化新创建的瞬时(transient)对象
	 * 
	 * @param _transientObject
	 * @return PK 主键
	 */
	@SuppressWarnings("unchecked")
	public PK save(T _transientObject) {
		return (PK) getHibernateTemplate().save(_transientObject);
	}

	/**
	 * 更新已修改的脱管(detached)对象
	 * 
	 * @param _detachedObject
	 */
	public void update(T _detachedObject) {
		getHibernateTemplate().update(_detachedObject);
	}

	/**
	 * 保存或更新对象
	 * 
	 * @param _object
	 */
	public void saveOrUpdate(T _object) {
		getHibernateTemplate().saveOrUpdate(_object);
	}

	/**
	 * 删除持久化(persistent)对象
	 * 
	 * @param _persistentObject
	 */
	public void delete(T _persistentObject) {
		getHibernateTemplate().delete(_persistentObject);
	}

	/**
	 * 删除主键id所对应的持久化(persistent)对象
	 * 
	 * @param _id
	 */
	public void deleteByPK(PK _id) {
		T instance = findByPK(_id);
		if (instance != null) {
			getHibernateTemplate().delete(instance);
		}
	}

	/**
	 * 获得主键id所对应的持久化(persistent)对象
	 * 
	 * @param _id
	 * @return
	 */
	public T findByPK(PK _id) {
		return (T) getHibernateTemplate().get(getPojoClass(), _id);
	}

	/**
	 * 根据某个具体属性进行查找
	 * 
	 * @param _propertyName
	 * @param _value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String _propertyName, Object _value) {
		String queryString = "from " + getPojoClassName() + " as obj where obj." + _propertyName + "=?";
		return (List<T>) getHibernateTemplate().find(queryString, _value);
	}

	/**
	 * 获取所有对象
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return (List<T>) getHibernateTemplate().loadAll(getPojoClass());
	}

	/**
	 * 根据给定的实例查找对象
	 * 
	 * @param _exampleInstance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAfterExample(T _exampleInstance) {
		return (List<T>) getHibernateTemplate().findByExample(_exampleInstance);
	}

	/**
	 * 根据hql查询
	 * 
	 * @param _hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHql(String _hql) {
		return (List<T>) getHibernateTemplate().find(_hql);
	}

	/**
	 * 根据hql查询
	 * 
	 * @param _hql
	 *            查询语句
	 * @param _values
	 *            可变参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHql(String _hql, Object... _values) {
		return (List<T>) getHibernateTemplate().find(_hql, _values);
	}

	/**
	 * 根据hql查询
	 * 
	 * @param _hql
	 *            查询语句
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> findIteratorByHql(String _hql) {
		return (Iterator<T>) getHibernateTemplate().iterate(_hql);
	}

	/**
	 * 根据hql查询
	 * 
	 * @param _hql
	 *            查询语句
	 * @param _values
	 *            可变参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> findIteratorByHql(String _hql, Object... _values) {
		return (Iterator<T>) getHibernateTemplate().iterate(_hql, _values);
	}

	/**
	 * 根据条件查找对象
	 * 
	 * @param _criterion
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findByCriteria(final Criterion... _criterion) {
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session _arg0) throws HibernateException, SQLException {
				Criteria crit = getSession().createCriteria(getPojoClass());
				for (Criterion c : _criterion) {
					crit.add(c);
				}
				return crit.list();
			}
		});
		return list;
	}

	/**
	 * 根据条件查找对象
	 * 
	 * @param orderBy
	 *            排序字段
	 * @param isAsc
	 *            是否顺序排序
	 * @param _criterion
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findByCriteria(final String orderBy, final boolean isAsc, final Criterion... _criterion) {
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session _session) throws HibernateException, SQLException {
				Criteria crit = getSession().createCriteria(getPojoClass());
				for (Criterion c : _criterion) {
					crit.add(c);
				}
				if (isAsc)
					crit.addOrder(Order.asc(orderBy));
				else
					crit.addOrder(Order.desc(orderBy));
				return crit.list();
			}
		});
		return list;
	}

	/**
	 * 根据条件查找对象
	 * 
	 * @param _detachedCriteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final DetachedCriteria _detachedCriteria) {
		return (List<T>) getHibernateTemplate().findByCriteria(_detachedCriteria);
	}

	/**
	 * 根据hql统计总数
	 * 
	 * @param _hql
	 *            查询语句
	 * @param _values
	 *            查询参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getCountByHql(final String _hql, Object... _values) {
		int sqlFromIndex = _hql.indexOf("from");
		int sqlOrderbyIndex = _hql.indexOf("order by");
		String sqlCount;
		// 因为此方法只取得查询的结果总数，所以将查询语句中可能存在的排序语句去掉来提高查询效率
		if (sqlOrderbyIndex > 0) {
			sqlCount = "select count(*) " + _hql.substring(sqlFromIndex, sqlOrderbyIndex);
		} else {
			sqlCount = "select count(*) " + _hql.substring(sqlFromIndex);
		}
		List countList = getHibernateTemplate().find(sqlCount, _values);
		return ((Long) countList.get(0)).intValue();
	}

	/**
	 * 根据DetachedCriteria统计总数
	 * 
	 * @param _detachedCriteria
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCountByCriteria(final DetachedCriteria _detachedCriteria) {
		Integer count = new Integer((getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Object obj = null;
				try {
					Criteria criteria = _detachedCriteria.getExecutableCriteria(session);
					obj = criteria.setProjection(Projections.rowCount()).uniqueResult();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return obj;
			}
		})).toString());
		return count;
	}

	/**
	 * 根据DetachedCriteria加载分页，指定页大小和起始位置
	 * 
	 * @param _detachedCriteria
	 * @param _pageSize
	 * @param _startIndex
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findPageByCriteria(final DetachedCriteria _detachedCriteria, final int _pageSize, final int _startIndex) {
		List list = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = _detachedCriteria.getExecutableCriteria(session);
				List items = criteria.setFirstResult(_startIndex).setMaxResults(_pageSize).list();
				return items;
			}
		});
		return list;
	}

	/**
	 * 根据hql加载分页，指定页大小和起始位置
	 * 
	 * @param _hql
	 * @param _pageSize
	 * @param _startIndex
	 * @param _values
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findPageByHql(final String _hql, final int _pageSize, final int _startIndex, final Object... _values) {
		List list = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(_hql);
				for (int i = 0; i < _values.length; i++) {
					query.setParameter(i, _values[i]);
				}
				query.setFirstResult(_startIndex).setMaxResults(_pageSize);
				return query.list();
			}
		});
		return list;
	}

	/**
	 * 根据定制的组合条件查询
	 * 
	 * @param <E>
	 * @param _clz
	 *            待查询的对象的类
	 * @param _params
	 *            查询参数,如没有传空list
	 * @param _orders
	 *            查询排序,如没有传空list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> findByCustomized(Class<E> _clz, List<QueryParam> _params, List<QueryOrder> _orders) {
		DetachedCriteria dc = DetachedCriteria.forClass(_clz);
		parseAlias(dc, _params, _orders);
		if (!isNullParam(_params)) {
			for (QueryParam param : _params) {
				Criterion crit = parseParam(param);
				if (crit != null)
					dc.add(crit);
			}
		}
		if (!isNullParam(_orders)) {
			for (QueryOrder queryOrder : _orders) {
				Order order = parseOrder(queryOrder);
				if (order != null)
					dc.addOrder(order);
			}
		}
		return getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 根据SQL条件查询
	 */
	public <E> List<E> findByCustomizedSQL(Class<E> _clz, String sqlparams) {
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		QueryParam param = new QueryParam();
		param.setField(sqlparams);
		param.setOp(OP.sql);
		param.setValue(new Object[] {});
		paramList.add(param);

		return findByCustomized(_clz, paramList, null);
	}

	@SuppressWarnings("unchecked")
	public <E> List<T> findAllObject(Class<E> _clz) {
		DetachedCriteria dc = DetachedCriteria.forClass(_clz);
		return getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 根据定制的组合条件查询分页
	 * 
	 * @param <E>
	 * @param _pageNum
	 *            页码
	 * @param _pageSize
	 * @param _clz
	 *            待查询的对象的类
	 * @param _params
	 *            查询参数,如没有传空list或null
	 * @param _orders
	 *            查询排序,如没有传空list或null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> Page<E> findPageByCustomized(int _pageNum, int _pageSize, Class<E> _clz, List<QueryParam> _params,
			List<QueryOrder> _orders) {
		DetachedCriteria dc = DetachedCriteria.forClass(_clz);
		parseAlias(dc, _params, _orders);
		if (!isNullParam(_params)) {
			for (QueryParam param : _params) {
				Criterion crit = parseParam(param);
				if (crit != null)
					dc.add(crit);
			}
		}
		int totalCount = getCountByCriteria(dc);
		dc.setProjection(null);
		dc.setResultTransformer(Criteria.ROOT_ENTITY);
		Page<E> page = new Page<E>(_pageNum, _pageSize, totalCount);
		int startIndex = (page.getCurrentPageNumber() - 1) * _pageSize;
		if (!isNullParam(_orders)) {
			for (QueryOrder queryOrder : _orders) {
				Order order = parseOrder(queryOrder);
				if (order != null)
					dc.addOrder(order);
			}
		}
		List<E> list = findPageByCriteria(dc, _pageSize, startIndex);
		page.setElements(list);
		return page;
	}

	/**
	 * 根据传入的类和id查找对象
	 * 
	 * @param <E>
	 * @param _clz
	 * @param _id
	 * @return
	 */
	public <E> E findByPK(Class<E> _clz, PK _id) {
		return (E) getHibernateTemplate().get(_clz, _id);
	}

	/**
	 * 根据传入的类和id删除对象
	 * 
	 * @param <E>
	 * @param _clz
	 * @param _id
	 */
	public <E> void deleteByPK(Class<E> _clz, PK _id) {
		E instance = findByPK(_clz, _id);
		if (instance != null) {
			getHibernateTemplate().delete(instance);
		}
	}

	protected Criterion parseParam(QueryParam _param) {
		Criterion crit = null;
		OP op = _param.getOp();
		String field = _param.getField();
		Object[] value = _param.getValue();
		switch (op) {
		case equal:
			crit = Restrictions.eq(field, value[0]);
			break;
		case notEqual:
			crit = Restrictions.not(Restrictions.eq(field, value[0]));
			break;
		case greater:
			crit = Restrictions.gt(field, value[0]);
			break;
		case less:
			crit = Restrictions.lt(field, value[0]);
			break;
		case greaterEqual:
			crit = Restrictions.ge(field, value[0]);
			break;
		case lessEqual:
			crit = Restrictions.le(field, value[0]);
			break;
		case between:
			crit = Restrictions.between(field, value[0], value[1]);
			break;
		case like:
			crit = Restrictions.like(field, value[0]);
			break;
		case ilike:
			crit = Restrictions.ilike(field, value[0]);
			break;
		case or:
			Criterion crit1 = parseParam((QueryParam) value[0]);
			Criterion crit2 = parseParam((QueryParam) value[1]);
			crit = Restrictions.or(crit1, crit2);
			break;
		case sql:
			crit = Restrictions.sqlRestriction(field);
			break;
		default:
			break;
		}
		return crit;
	}

	protected Order parseOrder(QueryOrder _order) {
		Order order = null;
		if (_order.isAscOrder()) {
			order = Order.asc(_order.getField());
		} else {
			order = Order.desc(_order.getField());
		}
		return order;
	}

	/**
	 * 处理需用别名的情况
	 * 
	 * @param _detachedCriteria
	 * @param _params
	 * @param _orders
	 */
	protected void parseAlias(DetachedCriteria _detachedCriteria, List<QueryParam> _params, List<QueryOrder> _orders) {
		Set<String> aliasSet = new HashSet<String>();
		if (!isNullParam(_params)) {
			for (QueryParam param : _params) {
				String field = param.getField();
				if (field == null)
					continue;
				int index = field.lastIndexOf(".");
				if (index > 0) {
					String alias = field.substring(0, index);
					if (!aliasSet.contains(alias)) {
						_detachedCriteria.createAlias(alias, alias);
						aliasSet.add(alias);
					}
				}
			}
		}
		if (!isNullParam(_orders)) {
			for (QueryOrder order : _orders) {
				String field = order.getField();
				int index = field.lastIndexOf(".");
				if (index > 0) {
					String alias = field.substring(0, index);
					if (!aliasSet.contains(alias)) {
						_detachedCriteria.createAlias(alias, alias);
						aliasSet.add(alias);
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected boolean isNullParam(Object _obj) {
		if (_obj == null)
			return true;
		if (_obj instanceof List)
			return ((List) _obj).size() < 1;
		return true;
	}

}
