package cn.www.serviceimpl;

import java.util.List;

import cn.www.dao.CommonDao;
import cn.www.iservice.CommonManager;
import cn.www.utils.query.Page;
import cn.www.utils.query.QueryOrder;
import cn.www.utils.query.QueryParam;


@SuppressWarnings({"unchecked"})
public class CommonManagerImpl implements CommonManager{
	private CommonDao commonDao;

	public Long saveEntity(Object _obj){
		return commonDao.save(_obj);
	}

	public void modifyEntity(Object _obj){
		commonDao.update(_obj);
	}

	public <E>E findEntityByPK(Class<E> _clz, Long _id){
		return commonDao.findByPK(_clz, _id);
	}

	public void deleteEntity(Object _obj){
		commonDao.delete(_obj);
	}

	public <E>void deleteEntityByPK(Class<E> _clz, Long _id){
		commonDao.deleteByPK(_clz, _id);
	}


	public void setCommonDao(CommonDao commonDao){
		this.commonDao = commonDao;
	}

	public <E>List<E> findAll(){
		return (List<E>)this.commonDao.findAll();
	}

	public <E>List<E> findByCustomized(Class<E> _clz, List<QueryParam> _params, List<QueryOrder> _orders){
		return commonDao.findByCustomized(_clz, _params, _orders);
	}

	public <E>List<E> findByCustomizedSQL(Class<E> _clz, String sqlparams){
		return commonDao.findByCustomizedSQL(_clz, sqlparams);
	}

	public <E>Page<E> findPageByCustomized(int _pageNum, int _pageSize, Class<E> _clz, List<QueryParam> _params, List<QueryOrder> _orders){
		return commonDao.findPageByCustomized(_pageNum, _pageSize, _clz, _params, _orders);
	}

}
