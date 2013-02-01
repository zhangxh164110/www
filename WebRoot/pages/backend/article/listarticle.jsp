<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include flush="true" page="../../../../starmos/common/pages/pageForm.jsp"></jsp:include>
<html>
  <head>
    <title>文章列表</title>
    <link href="${pageContext.request.contextPath}/pages/backend/css/skin.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/pages/backend/css/admin.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script>
		function addOrEdit( idVal ){
		    var url = '${pageContext.request.contextPath}/editArticle?id='+idVal;
		    window.location.href = url;
		}
		function changeStatus( status,idVal ){
			var url = '${pageContext.request.contextPath}/chageArticleStauts?id='+idVal+'&status='+status;
			$.post(url, null, function(data){
				if( data  == 1 ){
					alert("系统繁忙，请稍后");
				}else{
					window.location.reload();
				}
			});
		}
		
		function searchData(){
			var url = 'listArticle';
			document.getElementById('searchForm').action = url;
			document.getElementById('searchForm').submit();
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
	        <td height="31"><div class="titlebt">文章列表</div></td>
	      </tr>
	    </table>
    </td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/pages/backend/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
   <!--内容 -->
   <tr>
   	<td valign="middle" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif">&nbsp;</td>
  	<td valign="top"  class="main-td">
  	<form action="#" method="post" id="searchForm" name="searchForm">
  	<!-- 搜索 -->
	<div class="pageHeader">

		  <div class="searchBar">
		  	<table cellpadding="0" cellspacing="0" class="searchContent">
		  		<tr>
		  			<td>所属栏目：<s:select name="article.category.id" cssStyle="width:156px;" list="#request.listCatalog" listKey="id"  listValue="categoryName" headerKey="0" headerValue="请选择"></s:select></td>
		  			<td>
		  				标题：
		  				<s:textfield name="article.title" id="title" cssClass="textInput"/>
		  			</td>
		  			<td>
		  				关键字：
		  				<s:textfield name="article.keyword" id="keyword" cssClass="textInput" />
		  			</td>
		  			<td>
		  				发表时间：
		  				从<input type="text" readonly="readonly" class="textInput"  name="startTime" id="startTime" value="<s:property value="#request.startTime"/>" onclick="WdatePicker()"/>
		  				到<input type="text" readonly="readonly" class="textInput"  name="endTime" id="endTime" value="<s:property value="#request.endTime"/>" onclick="WdatePicker()"/>
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

		
		<div class="gridViewBtn">
			<a href="javascript:addOrEdit(0);">添加</a>
		</div>
  		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="gridView">
  			<tr>
	  			<th>所属栏目</th>
	  			<th>标题</th>
	  			<th>发表时间</th>
	  			<th>作者</th>
	  			<th>关键字</th>
	  			<th>访问量</th>
	  			<th>操作</th>
  			</tr>
  			<s:iterator value="pager.elements">
  				<tr>
  					<td>
  						<s:property value="category.name"/>
  					</td>
  					<td>
  						<s:property value="title"/>
  					</td>
  					<td>
  						<s:date name="publicDate" format="yyyy-MM-dd HH:mm"/>
  					</td>
  					<td>
  						<s:property value="author"/>
  					</td>
  					<td>
  						<s:property value="keyword"/>
  					</td>
  					<td>
  						<s:property value="readNum"/>
  					</td>
  					
  					<td>
  						<a href="javascript:addOrEdit('<s:property value="id"/>');">编辑</a>
  						&nbsp;&nbsp;&nbsp;&nbsp;
  						<a href="javascript:changeStatus('<s:property value="status"/>','<s:property value="id"/>');">
  							<s:if test="status==0">
  								下架
  							</s:if>
  							<s:elseif test="status==1">
  								上架
  							</s:elseif>
  						</a>
  					</td>
  				</tr>
  			</s:iterator>
  			</table>
  			<input type="hidden" id="urlforward" value="listArticle" />
  			<br/>
			<div id="pagination" valign="middle" ></div>
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
