 <html>  
<head> 
	测试freemark 
</head>
<body>
	

<table>
	<#--Freemarker遍历list-->
	<#list userList as user>
		<tr>
			<td>用户名：${user.userName}</td>
			<td>密  码：${user.userPassword}</td>s
			<td>年  龄: ${user.age}</td>
		</tr>
	</#list>
</table>
<br/><br/>

<#--Freemarker遍历list并应用list隐含变量item_index-->
item_index使用：<br/>
<#list userList as user>
第${user_index+1}个用户<br/>
	用户名：${user.userName}<br/>
	密  码：${user.userPassword}<br/>
	年  龄: ${user.age}<br/>
</#list>
<#--Freemarker遍历list并应用list隐含变量item_has_next-->
item_has_next,size使用：<br/>
<#list userList as user>

	用户名：${user.userName}<br/>
	密  码：${user.userPassword}<br/>
	年  龄: ${user.age}<br/>
	<#if !user_has_next>
	共有${userList?size}最后一个用户是:${user.userName}<br/>
	</#if>
</#list>
<#--Freemarker遍历list并按用户年龄升序排序-->

按用户年龄升序排序：<br/>
<#list userList?sort_by("age") as user>

	用户名：${user.userName}<br/>
	密  码：${user.userPassword}<br/>
	年  龄: ${user.age}<br/>
	
</#list>

<#--Freemarker遍历list并按用户年龄降序排序-->

按用户年龄降序排序：<br/>
<#list userList?sort_by("age")?reverse as user>

	用户名：${user.userName}<br/>
	密  码：${user.userPassword}<br/>
	年  龄: ${user.age}<br/>
	
</#list>
<#--Freemarker遍历list当用户年龄大于21岁时，停止输出-->
list中应用break:
<#list userList?sort_by("age")?reverse as user>

	用户名：${user.userName}<br/>
	密  码：${user.userPassword}<br/>
	年  龄: ${user.age}<br/>
	<#if (user.age>21) >
		<#break>
	</#if>
</#list>

<script>
	alert(1232);
</script>
</body>