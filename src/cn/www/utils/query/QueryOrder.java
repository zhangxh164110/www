package cn.www.utils.query;

/**
 * 查询排序
 */
public class QueryOrder {
	/**
	 * 需排序的字段
	 */
	private String field;
	/**
	 * 排序的顺序,true为顺序,false为倒序
	 */
	private boolean isAscOrder;

	public String getField() {
		return this.field;
	}

	public void setField(String _field) {
		this.field = _field;
	}

	public boolean isAscOrder() {
		return this.isAscOrder;
	}

	public void setAscOrder(boolean _isAscOrder) {
		this.isAscOrder = _isAscOrder;
	}
}
