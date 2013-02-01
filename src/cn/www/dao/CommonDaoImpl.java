package cn.www.dao;



public class CommonDaoImpl extends GenericHibernateDaoImpl<Object, Long> implements CommonDao {
	//
	public void delArticleCategory(long id){
		String sql = "delete from a_articleCategory where articleId="+id;
		this.getSession().createSQLQuery( sql ).executeUpdate();
	}
}
