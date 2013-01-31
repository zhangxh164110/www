<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include flush="true" page="../pagerForm.jsp"></jsp:include>
<html>
  <head>
    <title>品牌</title>
   
    <link href="${pageContext.request.contextPath}/pages/backend/css/skin.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/pages/backend/css/admin.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script>
		function addOrEdit( idVal ){
			window.location.href = '${pageContext.request.contextPath}/editProduct?id='+idVal;
		}
		function searchData(){
			var url = '${pageContext.request.contextPath}/lisetProduct';
			document.getElementById('searchForm').action = url;
			document.getElementById('searchForm').submit();
		}
    </script>
    
  </head>
  <body>
  <form action="#" method="post" id="searchForm" name="searchForm">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <!-- 顶部 -->
  <tr>
    <td width="17" valign="top" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="${pageContext.request.contextPath}/pages/backend/images/content-bg.gif">
	    <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
	      <tr>
	        <td height="31"><div class="titlebt">商品列表</div></td>
	      </tr>
	    </table>
    </td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/pages/backend/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
   <!--内容 -->
   <tr>
   	<td valign="middle" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif">&nbsp;</td>
  	<td valign="top"  class="main-td">
	
	<!-- 搜索 -->
	<div class="pageHeader">
	  <div class="searchBar">
	  	<table cellpadding="0" cellspacing="0" class="searchContent">
	  		<tr>
	  			<td>名称：<s:textfield name="product.name"></s:textfield></td>
	  			<td>品牌：<s:select list="listBrand" headerKey="0" headerValue="==请选择==" listKey="id" listValue="name" name="product.brand.id"></s:select></td>
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
	
	
	<!-- list集合页 -->
  		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="gridView">
  			<tr>
	  			<th>名称</th>
	  			<th>关联品牌</th>
	  			<th>关联型号</th>
	  			<th>单价</th>
	  			<th>折扣</th>
	  			<th>录入时间</th>
	  			<th>总数</th>
	  			<th>销售数</th>
	  			<th>操作</th>
  			</tr>
  			<s:iterator value="pager.elements">
  				<tr>
  					<td>
  						<s:property value="name"/>&nbsp;
  					</td>
  					<td>
  						<s:property value="brand.name"/>&nbsp;
  					</td>
  					<td>
  						<s:property value="model.name"/>&nbsp;
  					</td>
  					<td>
  						<s:property value="unitsPrice"/>&nbsp;
  					</td>
  					<td>
  						<s:property value="discount"/>
  						&nbsp;
  					</td>
  					<td>
  						<s:date name="createDate" format="yyyy-MM-dd"/>
  						&nbsp;
  					</td>
  					<td>
  						<s:property value="count"/>
  						&nbsp;
  					</td>
  					<td>
  						<s:property value="sellCount"/>
  						&nbsp;
  					</td>
  					<td>
  						<a href="javascript:addOrEdit('<s:property value="id"/>');">编辑</a>
  						&nbsp;|&nbsp;
  						<a href="javascript:delObj('delProduct','<s:property value="id"/>');">删除</a>
  					</td>
  				</tr>
  			</s:iterator>
  		</table>
  		<input type="hidden" id="urlforward" value="lisetProduct" />
  		<br/>
		<div id="pagination" valign="middle" class="pagination" ></div>
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

  </form>  
  <script>
		paginationCommon(1);
	</script>	
  </body>
</html>
