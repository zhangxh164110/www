<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>用户登录</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<link href="${pageContext.request.contextPath}/pages/backend/css/login.css" rel="stylesheet" type="text/css" />
<script>
function checkCookie(){
	if(!(document.cookie || navigator.cookieEnabled)){
		return false;
	}
	return true;
}

function handleEnter(field, event) {
	if (event.keyCode == 13) { //回车
		if (field.name == "username") {
			if ($.trim($('#username').val()) != '') {
				$('#pwd').focus();
			}
			return false;
		}
		if (field.name == "pwd") {
			if ($.trim($('#pwd').val()) != '') {
				login();
			}
		}
		return true;
	}
}
	
function login(){
	if( !checkCookie() ){
    	alert("浏览器Cookie没启用，请重新设置Cookie");
    	return;
    }
	if ($("#username").val() == "") {
		alert('登入名不能为空');
		return;
	}
	if ($("#pwd").val() == "") {
		alert('登入密码不能为空');
		return;
	}
	$.ajax({
			type:"POST",
			url:"login",
			async: false,
			data:$("#loginForm").serialize(),
			success:function (data){
				if( data == -1 ){
					alert('用户名或密码错');
				}else if( data == -2 ){
					alert('无权限');
				}else{
					window.location.href = "backEndMain";
				}
			}
		});
		
}
</script>
</script>
</head>
<body>
 <form id="loginForm">
    <div id="login">
	
	     <div id="top">
		      <div id="top_left"><img src="${pageContext.request.contextPath}/pages/backend/images/login_03.gif" /></div>
			  <div id="top_center"></div>
		 </div>
		 
		 <div id="center">
		    
		      <div id="center_left"></div>
			  <div id="center_middle">
			       <div id="user">用 户
			         <input type="text" name="username" id="username" onkeypress="handleEnter(this, event);"/>
			       </div>
				   <div id="password">密   码
				     <input type="password" name="pwd" id="pwd" onkeypress="handleEnter(this, event);"/>
				   </div>
				   <div id="btn"><a href="javascript:login();">登录</a><a href="#">清空</a></div>
			  
			  </div>
			  <div id="center_right"></div>		 
		 </div>
		 <div id="down">
		      <div id="down_left">
			      <div id="inf">
                       <span class="inf_text">版本信息</span>
					   <span class="copyright">管理信息系统 2008 v2.0</span>
			      </div>
			  </div>
			  <div id="down_center"></div>		 
		 </div>

	</div>
	</form>
</body>
</html>
