<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>添加/编辑</title>
    <link href="${pageContext.request.contextPath}/pages/backend/css/skin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script>
    	
    	var id = '<s:property value="product.id"/>';
    	var oldName = '<s:property value="product.name"/>';
    	function saveProduct(){
    		if( checkName()  ){
    			$.ajax({
				type:"POST",
				url:"saveProduct",
				cache:false,
				data:$("#editForm").serialize(),
				success:function (data){
					if(data == 1){
						alert("用户名称已经存在");
					}else if(data == 2){
						alert("系统繁忙，请稍后再试");
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
    	
	
    </script>
  </head>
  <body>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <!-- 顶部 -->
  <tr>
    <td width="17" valign="top" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="${pageContext.request.contextPath}/pages/backend/images/content-bg.gif">
	    <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
	      <tr>
	        <td height="31"><div class="titlebt">编辑菜单</div></td>
	      </tr>
	    </table>
    </td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/pages/backend/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/pages/backend/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <!--内容 -->
  <tr>
  	<td valign="middle" background="${pageContext.request.contextPath}/pages/backend/images/mail_leftbg.gif">&nbsp;</td>
  	<td valign="top">
  		<form action="" id="editForm">
  		<s:hidden name="product.id"/>
  		<table class="form_table" width="100%">
  			<tr>
  				<td style="text-align: right;"><font color="red">*</font>名称</td>
  				<td><s:textfield name="product.name" id="nickname"/></td>
  			</tr>
  			<tr>
  				<td style="text-align: right;">品牌</td>
  				<td></td><s:select list="listBrand" headerKey="==请选择==" headerValue="0" listKey="id" listValue="name" name="brand.id"></s:select>
  			</tr>
  			<tr>
  				<td style="text-align: right;">型号</td>
  				<td></td><s:select list="listModel" headerKey="==请选择==" headerValue="0" listKey="id" listValue="name" name="model.id"></s:select>
  			</tr>
  			<tr>
  				<td style="text-align: right;"><font color="red">*</font>单价</td>
  				<td><s:textfield name="product.unitsPrice" id="unitsPrice"/></td>
  			</tr>
  			<tr>
  				<td style="text-align: right;"><font color="red">*</font>折扣</td>
  				<td><s:textfield name="product.discount" id="discount"/></td>
  			</tr>
  			<tr>
  				<td colspan="3" style="text-align: center;">
  					<input type="button" value="提交" onclick="saveProduct();"/>
  				</td>
  			</tr>
  		</table>
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

  	
  </body>
</html>
