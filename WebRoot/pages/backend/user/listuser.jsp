<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include flush="true" page="../pagerForm.jsp"></jsp:include>
<html>
  <head>
    <title>用户</title>
   
    <link href="${pageContext.request.contextPath}/pages/backsend/css/skin.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
     <style type="text/css">
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			background-color: #EEF2FB;
		}
	</style>
    <script>
		function addOrEdit( idVal ){
			window.location.href = '${pageContext.request.contextPath}/addOrEditUser?id='+idVal;
		}
		function changeStatus( sta,idVal ){
			var url = '${pageContext.request.contextPath}/chageUserStauts?id='+idVal+'&status='+sta;
			$.post(url, null, function(data){
				if( data  == 1 ){
					alert("系统繁忙，请稍后");
				}else{
					window.location.reload();
				}
			});
		}
    </script>
    
  </head>
  <body>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <!-- 顶部 -->
  <tr>
    <td width="17" valign="top" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="${pageContext.request.contextPath}/pages/backend/images/content-bg.gif">
	    <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
	      <tr>
	        <td height="31"><div class="titlebt">用户列表</div></td>
	      </tr>
	    </table>
    </td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/pages/backend/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
   <!--内容 -->
   <tr>
   	<td valign="middle" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif">&nbsp;</td>
  	<td valign="top"  class="main-td">
  	<div class="margin-up-down clear"></div>
	
	<div class="gridViewBtn">
		<a href="javascript:addOrEdit(0);">添加</a>
	</div>
	
	
	<!-- list集合页 -->
  		<form action="regUser" id="userForm">
  		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="gridView">
  			<tr>
	  			<th>用户名</th>
	  			<th>性别</th>
	  			<th>操作</th>
  			</tr>
  			<s:iterator value="pager.elements">
  				<tr>
  					<td>
  						<s:property value="userName"/>
  					</td>
  					<td>
  						<s:if test="sex==0">
  							女
  						</s:if>
  						<s:elseif test="sex==1">
  							男
  						</s:elseif>
  						&nbsp;
  					</td>
  					<td>
  						<a href="javascript:addOrEdit('<s:property value="id"/>');">编辑</a>
  						&nbsp;&nbsp;&nbsp;&nbsp;
  						<a href="javascript:changeStatus('<s:property value="status"/>','<s:property value="id"/>');">
  							<s:if test="role!=0">
	  							<s:if test="status==1">
	  								启用
	  							</s:if>
	  							<s:elseif test="status==2">
	  								禁用
	  							</s:elseif>
  							</s:if>
  						</a>
  					</td>
  				</tr>
  			</s:iterator>
  		</table>
  		<input type="hidden" id="urlforward" value="listUser.action" />
		<div id="pagination" valign="middle" class="pagination" ></div>
  	   </form>
  	</td>
  	<td background="${pageContext.request.contextPath}/pages/backend/images/mail_rightbg.gif">&nbsp;</td>
   </tr>
 
  
  <!-- 底部 -->
  <tr>
    <td valign="bottom" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/buttom_left2.gif" width="17" height="17" /></td>
    <td background="${pageContext.request.contextPath}/pages/backend/images/buttom_bgs.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/buttom_bgs.gif" width="17" height="17"></td>
    <td valign="bottom" background="${pageContext.request.contextPath}/pages/backend/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/buttom_right2.gif" width="16" height="17" /></td>
  </tr>
</table>

    
  <script>
		paginationCommon(1);
	</script>	
  </body>
</html>
