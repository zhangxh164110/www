package cn.www.utils.query;

/**
 * 查询参数
 */
public class QueryParam {
	/**
	 * 查询针对的字段
	 */
	private String field;
	/**
	 * 查询条件的操作符
	 */
	private OP op;
	/**
	 * 查询条件的值
	 */
	private Object[] value;

	public String getField() {
		return this.field;
	}

	public void setField(String _field) {
		this.field = _field;
	}

	public OP getOp() {
		return this.op;
	}

	public void setOp(OP _op) {
		this.op = _op;
	}

	public Object[] getValue() {
		return this.value;
	}

	public void setValue(Object[] _value) {
		this.value = _value;
	}
}
