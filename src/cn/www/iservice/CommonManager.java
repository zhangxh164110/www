package cn.www.iservice;

import java.util.List;

import cn.www.utils.query.Page;
import cn.www.utils.query.QueryOrder;
import cn.www.utils.query.QueryParam;


public interface CommonManager{

	public Long saveEntity(Object _obj);

	public void modifyEntity(Object _obj);

	public <E>E findEntityByPK(Class<E> _clz, Long _id);

	public void deleteEntity(Object _obj);

	public <E>List<E> findAll();

	public <E>void deleteEntityByPK(Class<E> _clz, Long _id);

	public <E>List<E> findByCustomized(Class<E> _clz, List<QueryParam> _params, List<QueryOrder> _orders);

	public <E>List<E> findByCustomizedSQL(Class<E> _clz, String sqlparams);

	public <E>Page<E> findPageByCustomized(int _pageNum, int _pageSize, Class<E> _clz, List<QueryParam> _params, List<QueryOrder> _orders);

}