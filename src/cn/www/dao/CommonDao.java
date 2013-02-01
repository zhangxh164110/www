package cn.www.dao;


public interface CommonDao extends GenericDao<Object, Long> {
	//清空原来的和article 的Id相关的ArticleCategory数据
	public void delArticleCategory(long id);
}
