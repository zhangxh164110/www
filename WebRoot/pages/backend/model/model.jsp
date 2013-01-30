<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>添加/编辑菜单</title>
    <link href="${pageContext.request.contextPath}/pages/backend/css/skin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script>
    	
    	var id = '<s:property value="model.id"/>';
    	var oldName = '<s:property value="model.name"/>';
    	var arr= new Array();
    	$(function(){
    		<s:iterator value="#request.list">	
    				arr[arr.length]= new Array('<s:property value="brand.id"/>','<s:property value="category.name"/>');
 			 </s:iterator>
    	})
    	function saveModel(){
    		if( checkName() && checkPwd() && checkEnterPwd() ){
    			$.ajax({
				type:"POST",
				url:"saveModel",
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
    	
    	function checkName(){
			var name=$('#nickname').val();
			var nameSpan=$('#reg_username_span');
		    if( id !=0 && oldName!= $.trim(name) ){
		    	var a=false;
					$.ajax({
					url:"${pageContext.request.contextPath}/isNameModelExist",
					data:"name="+name,
					cache:false,
					async: false,
					success:function (data){
						if(data==1){
							nameSpan.html("<font color='#de731d'>名称已存在</font>");
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
	
	function getDetail( idVal ){
		$("#detailId").empty();
		var html = '==请选择==';
		for(var i=0;i<arr.length;i++){
			html +='<option value='+arr[i][0]+'>'+arr[i][1]+'</option>';
		}
		$("#detailId").html(html); 
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
	        <td height="31"><div class="titlebt">编辑型号</div></td>
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
  		<s:hidden name="user.role"  value="2"/>
  		<input type="hidden" name="oldName" value="<s:property value="model.name"/>">
  		<s:hidden name="model.id"/>
  		<table class="form_table" width="100%">
  			<tr>
  				<td style="text-align: right;"><font color="red">*</font>名称</td>
  				<td><s:textfield name="model.name" id="nickname"/><span id="reg_username_span"></span></td>
  			</tr>
  			<tr>
  				<td style="text-align: right;">品牌</td>
  				<td>
  				<s:select onchange="getDetail(this.value);" list="listBrand" id="typeId" headerKey="0" headerValue="==请选择==" listKey="id" listValue="name" name=""></s:select>
  				</td>
  			</tr>
  			<tr>
  				<td style="text-align: right;">分类</td>
  				<td>
	  				<select name="detailId" id="detailId">
	                       <option  value="0" selected>==请选择==</option>
	                   </select>
                  </td>
  			</tr>
  			<tr>
  				<td colspan="3" style="text-align: center;">
  					<input type="button" value="提交" onclick="saveModel();"/>
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
