<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>添加/编辑</title>
    <link href="${pageContext.request.contextPath}/pages/backend/css/skin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script>
    	var arr= new Array();
    	var arr2 = new Array();
    	$(function(){
    		<s:iterator value="#request.list">	
    				arr[arr.length]= new Array('<s:property value="brand.id"/>','<s:property value="category.name"/>','<s:property value="category.id"/>');
 			 </s:iterator>
 			 <s:iterator value="listModel">	
    				arr2[arr2.length]= new Array('<s:property value="id"/>','<s:property value="name"/>','<s:property value="category.id"/>','<s:property value="brand.id"/>');
 			 </s:iterator>
 			 var id='<s:property value="product.id"/>';
 			 if( id !=0 ){
	 			 initSelect();
	 			 initModelSelect();
 			 }
    	})
    	//联动分类
    	function getDetail( idVal ){
			$("#categoryId").empty();
			var html = '<option  value="0" selected>==请选择==</option>';
			for(var i=0;i<arr.length;i++){
				if( idVal == arr[i][0] ){
					html +='<option value='+arr[i][2]+'>'+arr[i][1]+'</option>';
				}
			}
			$("#categoryId").html(html); 
		}
		
		function initSelect(){
			var categoryId = '<s:property value="product.category.id"/>';
			var brandId = '<s:property value="product.brand.id"/>';
			getDetail(brandId);
			$("#categoryId").find("option[value="+categoryId+"]").attr("selected", "selected");
		}
		//联动型号,type1为品牌联动，type2为分类
		function getModelDetail( idVal,type ){
			$("#modelId").empty();
			var html = '<option  value="0" selected>==请选择==</option>';
			for(var i=0;i<arr2.length;i++){
				if( type == 1 ){
					if( idVal == arr2[i][3] ){
						html +='<option value='+arr2[i][0]+'>'+arr2[i][1]+'</option>';
					}
				}else{
					if( idVal == arr2[i][2] ){
						html +='<option value='+arr2[i][0]+'>'+arr2[i][1]+'</option>';
					}
				}
				
			}
			$("#modelId").html(html); 
		}
		
		function initModelSelect(){
			var categoryId = '<s:property value="product.category.id"/>';
			var brandId = '<s:property value="product.brand.id"/>';
			var modelId = '<s:property value="product.model.id"/>';
			if( categoryId !=0 ){
				getModelDetail(categoryId,2);
			}else{
				getModelDetail(brandId,1);
			}
			$("#modelId").find("option[value="+modelId+"]").attr("selected", "selected");
		}
	
	
	
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
				    if(data == 2){
						alert("系统繁忙，请稍后再试");
					}else{
					   var url = 'listProduct';
					   window.location.href = url;
					}
				}
			});
    		}else{
    			return;
    		}
    	}
    	
		function checkName() {
			if( $.trim($('#nickname').val())==''){
				alert('名称不能为空');
				return false;
			}
			if( $.trim($('unitsPrice').val())=='' ){
				alert('单价不能为空');
				return false;
			}
			if( !isNaN($.trim($('unitsPrice').val()))){
				alert('单价必须为数字');
				return false;
			}
			if( $.trim($('discount').val())=='' ){
				alert('折扣不能为空');
				return false;
			}
			if( !isNaN($.trim($('discount').val()))){
				alert('折扣必须为数字');
				return false;
			}
			if( $.trim($('count').val())=='' ){
				alert('总数不能为空');
				return false;
			}
			if( !isNaN($.trim($('count').val()))){
				alert('总数必须为数字');
				return false;
			}
			return true;
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
	        <td height="31"><div class="titlebt">编辑商品</div></td>
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
  				<td><s:select onchange="getDetail(this.value);getModelDetail(this.value,1);" id="brandId" list="listBrand" headerKey="0" headerValue="==请选择==" listKey="id" listValue="name" name="product.brand.id"></s:select></td>
  			</tr>
  			<tr>
  				<td style="text-align: right;">分类</td>
  				<td>
  					<select  onchange="getModelDetail(this.value,2);" id="categoryId" name="product.category.id">
                       <option  value="0" selected>==请选择==</option>
                    </select>
  				</td>
  			</tr>
  			<tr>
  				<td style="text-align: right;">型号</td>
  				<td>
  					<select id="modelId" name="product.model.id">
                       <option  value="0" selected>==请选择==</option>
                    </select>
  				</td>
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
  				<td style="text-align: right;"><font color="red">*</font>总数</td>
  				<td><s:textfield name="product.count" id="count"/></td>
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
