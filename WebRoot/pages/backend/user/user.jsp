<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>添加/编辑用户</title>
    <link href="${pageContext.request.contextPath}/pages/backend/css/skin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script>
    	
    	var id = '<s:property value="user.id"/>';
    	var oldName = '<s:property value="user.userName"/>';
    	function saveUser(){
    		if( checkName() && checkPwd() && checkEnterPwd() && checkEmail() ){
    			$.ajax({
				type:"POST",
				url:"saveUser.action",
				cache:false,
				data:$("#registForm").serialize(),
				success:function (data){
					if(data == 1){
						alert("系统忙碌，请稍后再试");
					}else{
					   var url = 'listUser';
					   window.location.href = url;
					}
				}
			});
    		}else{
    			return;
    		}
    	}
    	
    	function checkName(){
		var name=$('#nickname').val();
		var nameSpan=$('#reg_username_span');
		if($.trim(name).length==0 || getStringLength2(name) > 10 || $.trim(name).length<3 || !/[^\d|chun]/.test(name)){
			nameSpan.html("<font color='#de731d'>请使用英文a-z及数字1-9组合,限10位字符</font>");
			return false;
		}else{
		    if( id !=0 && oldName!= $.trim(name) ){
		    	var a=false;
					$.ajax({
					url:"${pageContext.request.contextPath}/isNameExist",
					data:"nickname="+name,
					cache:false,
					async: false,
					success:function (data){
						if(data==1){
							nameSpan.html("<font color='#de731d'>用户已存在</font>");
							a=false;
						}else{
							nameSpan.html("");
							a=true;
						}
					}
				});
				return a;
		    }
			return true;
		}
	}
	
	function checkPwd(){
		var pwd=$('#pwd1').val();
		var pwdSpan=$('#reg_userpass_span');
		if( id == 0 ){
			if($.trim(pwd).length==0 || $.trim(pwd).length>16 || $.trim(pwd).length<6){
				pwdSpan.html("<font color='#de731d'>请输入6-16位的用户密码</font>");
				return false;
			}
		}
		pwdSpan.html("");
		
		var enterPwd = $("#pwd2");
	    var pwdSpan2 = $("#userEnterPass_span");
	    if(enterPwd.val() != '' && pwd != enterPwd.val()){
	         pwdSpan2.html("<font color='#de731d'>密码与重复密码不一致</font>");
	         return false;
	    }
	    
	    pwdSpan2.html("");	    
		return true;
	}
	
	
	
	function checkEnterPwd(){
	   var pwd = $("#pwd1");
	   var enterPwd = $("#pwd2");
	   var pwdSpan = $("#userEnterPass_span");
	   
	   if(pwd.val() != enterPwd.val()){
	      pwdSpan.html("<font color='#de731d'>密码与重复密码不一致</font>");
	      return false;
	   }
	   
	    pwdSpan.html("");
		return true;
	}
	function getStringLength2(str){
		var len = 0;
		var cnstrCount = 0; 
		for(var i = 0 ; i < str.length ; i++){  
			if(str.charCodeAt(i)>255){
				cnstrCount = cnstrCount + 1 ;
			}
		}
		len = str.length + cnstrCount;
		return len;
	}
    </script>
  </head>
  <body>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <!-- 顶部 -->
  <tr>
    <td width="17" valign="top" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="${pageContext.request.contextPath}/starmos/backstage/images/content-bg.gif">
	    <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
	      <tr>
	        <td height="31"><div class="titlebt">编辑用户</div></td>
	      </tr>
	    </table>
    </td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <!--内容 -->
  <tr>
  	<td valign="middle" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_leftbg.gif">&nbsp;</td>
  	<td valign="top">
  		<form action="regUser" id="registForm">
  		<s:hidden name="user.role"/>
  		<s:hidden name="user.id"/>
  		<input type="hidden" name="pwdVal" id="pwdVal" value="<s:property value="user.password"/>"/>
  		<s:hidden name="user.status" value="2"></s:hidden>
  		<table class="form_table" width="100%">
  			<tr>
  				<td style="text-align: right;"><font color="red">*</font>用户名</td>
  				<td><s:textfield name="user.nickName" id="nickname"/><span id="reg_username_span"></span></td>
  			</tr>
  			
  			
  			<tr>
  				<td style="text-align: right;"><font color="red">*</font>密码</td>
  				<td><s:password name="user.password" id="pwd1"/><span id="reg_userpass_span"></span></td>
  			</tr>
  			<tr>
  				<td style="text-align: right;"><font color="red">*</font>确认密码</td>
  				<td><input type="password" id="pwd2"/><span id="userEnterPass_span"></span></td>
  			</tr>
  			
  			<tr>
  				<td colspan="3" style="text-align: center;">
  					<input type="button" value="提交" onclick="saveUser();"/>
  				</td>
  			</tr>
  		</table>
  	</form>
  	</td>
  	<td background="${pageContext.request.contextPath}/starmos/backstage/images/mail_rightbg.gif">&nbsp;</td>
  </tr>
  
  <!-- 底部 -->
  <tr>
    <td valign="bottom" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/buttom_left2.gif" width="17" height="17" /></td>
    <td background="${pageContext.request.contextPath}/starmos/backstage/images/buttom_bgs.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/buttom_bgs.gif" width="17" height="17"></td>
    <td valign="bottom" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/buttom_right2.gif" width="16" height="17" /></td>
  </tr>
</table>

  	
  </body>
</html>
