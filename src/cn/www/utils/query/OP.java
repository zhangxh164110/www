package cn.www.utils.query;

public enum OP {
	equal, // 等于
	notEqual, // 不等于
	greater, // 大于
	less, // 小于
	between, // 介于...之间
	like, // 模糊查询字串,大小写敏感
	ilike, // 模糊查询字串,大小写不敏感
	greaterEqual, // 大于等于
	lessEqual, // 小于等于
	or, // 或运算
	sql
	// 原生sql
}
