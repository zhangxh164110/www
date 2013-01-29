<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include flush="true" page="../../../../starmos/common/pages/pageForm.jsp"></jsp:include>
<html>
  <head>
    <title>模特资料</title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/starmos/common/js/My97DatePicker/WdatePicker.js"></script>
    <link href="${pageContext.request.contextPath}/starmos/backstage/css/skin.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/starmos/backstage/css/admin.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/starmos/backstage/js/checkrole.js"></script>
	<script type="text/javascript">
	
	function searchData(){
		var url = 'listRegUser.action';
		document.getElementById('searchForm').action = url;
		document.getElementById('searchForm').submit();
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
	function editUserInfo( idVal ){
		var url = '${pageContext.request.contextPath}/editUserInfo?id='+idVal;
		window.open( url );
	}
	</script>

</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <!-- 顶部 -->
  <tr>
    <td width="17" valign="top" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="${pageContext.request.contextPath}/starmos/backstage/images/content-bg.gif">
	    <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
	      <tr>
	        <td height="31"><div class="titlebt">会员列表</div></td>
	      </tr>
	    </table>
    </td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <!--内容 -->
  <tr>
  	<td valign="middle" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_leftbg.gif">&nbsp;</td>
<td valign="top"  class="main-td">
<!-- 搜索 -->
<form action="#" method="post" id="searchForm" name="searchForm">
<div class="pageHeader">
  <div class="searchBar">
  	<table cellpadding="0" cellspacing="0" class="searchContent">
  		<tr>
  			<td>昵称：<input type="text" name="user.nickName" class="textInput" /></td>
  			<td>
  				性别：
  				<s:radio name="user.sex" list="#{0:'女',1:'男'}" />
  			</td>
  			<td>
  				职业类型：
  				<s:select name="user.workType" id="workType" headerKey="999" headerValue="请选择" list="#{'个人会员':'个人会员','机构会员':'机构会员','模特儿':'模特儿','才艺表演':'才艺表演','摄影造型':'摄影造型','影视':'影视','设计插画':'设计插画','经纪人':'经纪人','其它':'其它','参赛选手':'参赛选手'}"></s:select>
  			</td>
  			<td>
  				会员状态：
  				<s:select name="user.status" id="status" headerKey="999" headerValue="请选择" list="#{0:'未激活',1:'禁用',2:'正常'}"></s:select>
  			</td>
  			
  		</tr>
  	</table>
  </div>
  <div>
  	<table style="width: 100%" cellpadding="0" cellspacing="0">
  		<tr>
  			<td align="right"><input type="button" class="pointer" value="检索" onclick="searchData();"/> &nbsp;&nbsp;</td>
  		</tr>
  	</table>
  </div>
  
 
</div>
<div class="margin-up-down clear"></div>
<!-- list集合页 -->
<table cellpadding="0" cellspacing="0" class="gridView">
	<tr>
		   <th>昵称</th>
           <th>性别</th>
           <th>电子邮箱</th>
           <th>会员状态</th>
           <th>模特信息</th>
           <th>注册时间</th>
           <th>访问量</th>
           <th>操作</th>
	</tr>
	<s:iterator value="#request.listUser">
		<tr>
			<td>
				<s:property value="nickName"/>
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
				<s:property value="email"/>
			</td>
			<td>
				<s:if test="status==0">
				 	未激活
				</s:if>
				<s:elseif test="status==1">
					禁用
				</s:elseif>
				<s:elseif test="status==2">
					正常
				</s:elseif>
			</td>
			<td>
				<s:if test="modelStatus==0">
				 	不是
				</s:if>
				<s:elseif test="modelStatus==1">
					VIP模特
				</s:elseif>
			</td>
			<td>
				<s:date name="regDate" format="yyyy-MM-dd"/>
			</td>
			<td>
				<s:property value="readNum"/>
			</td>
			<td>
				<a href="javascript:changeStatus('<s:property value="status"/>','<s:property value="id"/>');">
					<s:if test="status==1">
						启用
					</s:if>
					<s:elseif test="status==2||status==0">
						禁用
					</s:elseif>
					
					&nbsp;&nbsp;
					<a href="javascript:editUserInfo('<s:property value="id"/>');">编辑会员资料</a>
				</a>
			</td>
		</tr>
	</s:iterator>
</table>
 <input type="hidden" id="urlforward" value="listRegUser" />
  <div id="pagination" valign="middle" class="pagination" ></div>
		
</form>
<!-- 分页 -->
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

<script>
paginationCommon(1);
</script>
</body>
</html>