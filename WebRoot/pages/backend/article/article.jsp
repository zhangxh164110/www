<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	 <link href="${pageContext.request.contextPath}/pages/backend/css/skin.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/pages/backend/css/admin.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/checkrole.js"></script>
	
	<title></title>
	<script type="text/javascript">
		$(pageInit);
		function pageInit(){
			initContent();
			$('#elm1').xheditor({upLinkUrl:"${pageContext.request.contextPath}/uploadPicture?flag=1",upImgUrl:"${pageContext.request.contextPath}/uploadPicture",upImgExt:"jpg,jpeg,gif,png"});
		}
        
        var subObjStr = '';
		var subObjNameStr = '';
		function addSubObj(){
			var catalogId = $('#catalogId').val();
			if( catalogId == ''){	
				alert('请选择所属栏目');
				return;
			}
			var url = '${pageContext.request.contextPath}/getCatalogByType?catalogId='+catalogId;
			openPopDiv('catalog_pop',url,'100',false);
		}
		
        function saveArticle() {
            var form = document.articleForm;
			if(!checkForm(form)) {
				return;
			}
            var htmlVal = $('#elm1').val();
			$('#content').val(htmlVal);
            if( $('#content').val() == '' ){
            	alert("内容不能为空！");
            	return;
            }
            if($('#articleId').val() == ''){
	       		$('#articleId').val('0');
	       	}
            var image = $('#myFile').val();
    		if ($.trim(image) != '') {
    			if (image.indexOf('.') != -1) {
    				var extention = image.substring(image.lastIndexOf('.') + 1);
    				var arr = [ "jpg", "jpeg", "bmp", "gif", "png", "JPG", "JPEG", "BMP", "GIF", "PNG" ];
    				var isValid = false;
    				for ( var i = 0; i < arr.length; i++) {
    					if (extention == arr[i]) {
    						isValid = true;
    						break;
    					}
    				}
    				if (!isValid) {
    					alert('请上传正确的图片，图片后缀名可为jpg,jpeg,bmp,gif,png');
    					return;
    				}
    			}else{
    				alert('请上传正确的图片，图片后缀名可为jpg,jpeg,bmp,gif,png');
    				return;
    			}
    		}
    		$('#subCatalogIds').val(subObjStr);
    		var form = document.getElementById("articleForm");
            form.action = "saveArticle";
            form.submit();
        }
        
       function initContent() {
			var id = '<s:property value="article.id"/>';
			if( id!=0 ){
		        $.post("loadArticleContent.action", {articleId:id}, function(data) {
		             if( data != 0 ){
		             	$('#elm1').val(data);
		             }
		        });
				$('#catalogId').val('<s:property value="article.catalog.id"/>');
			}
			
		}
		
		function deSubArticleCatalog(){
			var id = '<s:property value="article.id"/>';
			$.post("deSubArticleCatalog.action", {id:id}, function(data) {
	             if( data != 0 ){
	             	alert('清除失败');
	             }else{
	             	alert('清除成功');
	             }
	        });
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
	        <td height="31"><div class="titlebt">文章</div></td>
	      </tr>
	    </table>
    </td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/starmos/backstage/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
   <!--内容 -->
   <tr>
   	<td valign="middle" background="${pageContext.request.contextPath}/starmos/backstage/images/mail_leftbg.gif">&nbsp;</td>
  	<td valign="top"  class="main-td">
  		<s:form id="articleForm" name="articleForm" method="post" theme="simple" enctype="multipart/form-data">
  		<input type="hidden" name="subCatalogIds" id="subCatalogIds"/>
    	<s:hidden id="articleId" name="article.id"/>
    	<s:if test="article.id!=0">
	    	<s:hidden id="userId" name="article.user.id"/>
	    	<s:hidden id="articleLogo" name="article.logo"/>
    	</s:if>
    	
    	<s:hidden name="content" id="content"></s:hidden>
    	<table width="100%" cellpadding="2" cellspacing="2" class="form-tab" style="font-size: 12px;">
    		<tr>
    			<td width="120px;">
    				<font color="red">*</font>主栏目
    			</td>
    			<td colspan="3">
    				<select name="catalogId" id="catalogId" style="width:156px;" title="请选择主栏目~!">
    					<option value="">==请选择==</option>
    					<optgroup label="时尚资讯">
    						<s:iterator value="#request.listCatalog1">
    							<option value="<s:property value="id"/>"><s:property value="catalogName"/></option>
    						</s:iterator>
    						<optgroup label="校花我最红">
	    						<s:iterator value="#request.listCatalog5">
	    							<option value="<s:property value="id"/>"><s:property value="catalogName"/></option>
	    						</s:iterator>
    						</optgroup>
    					</optgroup>
    					<optgroup label="大事记">
    						<s:iterator value="#request.listCatalog2">
    							<option value="<s:property value="id"/>"><s:property value="catalogName"/></option>
    						</s:iterator>
    					</optgroup>
    					<optgroup label="事件行销">
    						<s:iterator value="#request.listCatalog3">
    							<option value="<s:property value="id"/>"><s:property value="catalogName"/></option>
    						</s:iterator>
    					</optgroup>
    					<optgroup label="模特">
    						<s:iterator value="#request.listCatalog4">
    							<option value="<s:property value="id"/>"><s:property value="catalogName"/></option>
    						</s:iterator>
    					</optgroup>
    					
    				</select>
    				<a href="javascript:addSubObj();" style="padding-left: 6px;">添加子栏目</a>
    				<s:if test="article.id!=0">
    					<a href="javascript:deSubArticleCatalog();" style="padding-left: 6px;">清空子栏目</a>
    				</s:if>
    			</td>
    		</tr>
    		<tr id="sub_catalog" style="display: none;">
    			<td>
    				子栏目
    			</td>
    			<td colspan="3">
    				<input type="text" name="sub_catalog_text" id="sub_catalog_text" style="width: 80%"/>
    			</td>
    		</tr>
    		<tr>
    			<td width="120px;">
    				<font color="red">*</font>栏目标题
    			</td>
    			<td colspan="3">
    				<s:textfield name="article.catalogTitle" id="catalogTitle" title="请输入栏目标题~!" cssStyle="width:200px;"/>
    			</td>
    			
    		</tr>
    		
    		<tr>
    			<td valign="bottom">
    				<font color="red">*</font>栏目缩略图
    			</td>
    			<td colspan="3">
    				<s:if test="article.id==0">
    					<s:file id="myFile"  name="myFile" label="Image File" cssStyle="width: 250px" title="请上传栏目缩略图~!"/>
    				</s:if>
    				<s:else>
    					<s:file id="myFile"  name="myFile" label="Image File" cssStyle="width: 250px"/>
    					<img src="${pageContext.request.contextPath}/images/a/<s:property value="article.logo"/>" width="220px" height="170px">
    				</s:else>
    				(220px*220px)
    			</td>
    		</tr>    		
    		
    		<tr>
    			<td>
    				<font color="red">*</font>发表时间
    			</td>
    			<td colspan="2">
    				<input type="text" title="请输入发表时间~!" name="article.publicDate" id="publicDate" value="<s:date name="article.publicDate" format="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td>
    				<font color="red">*</font>标题
    			</td>
    			<td colspan="3">
    				<s:textfield name="article.title" id="title" title="请输入标题~!" cssStyle="width:440px;font-size:12px;" />
    			</td>
    		</tr>
    		
    		<tr>
    			<td>
    				<font color="red">*</font>导读
    			</td>
    			<td colspan="3">
    				<s:textarea name="article.review" id="review" cssStyle="width:440px;font-size:12px;" title="请输入导读~!"></s:textarea>
    			</td>
    		</tr>
    		
    		<tr>
    			<td>
    				关键字
    			</td>
    			<td colspan="3">
    				<s:textfield name="article.keyword" id="keyword" cssStyle="width:440px;"/>(多个关键字用空格隔开)
    			</td>
    		</tr>
    		
    		<tr>
    			<td>
    				meta元素
    			</td>
    			<td colspan="3">
    				<s:textfield name="article.metaKeyword" id="metaKeyword" cssStyle="width:440px;"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td>
    				出处
    			</td>
    			<td colspan="3">
    				<s:textfield name="article.origin" id="origin"  cssStyle="width:440px;"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td>
    				作者
    			</td>
    			<td colspan="3">
    				<s:textfield name="article.author" id="author"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td>
    				责任编辑
    			</td>
    			<td colspan="3">
    				<s:textfield name="article.editorInCharge" id="editorInCharge"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td colspan="4" style="text-align: center;color: red;font-size: 14px;font-weight: bold;">分页请插入#gzit-page#</td>
    		</tr>
    		<tr>
    			<td>
    				<font color="red">*</font>内容
    			</td>
    			<td colspan="3">
    				<textarea id="elm1" name="elm1"  rows="30" cols="80" style="width: 96%" class="xheditor {skin:'default',html5Upload:false,upMultiple:'1',upImgUrl:'uploadPicture',upImgExt:'jpg,jpeg,gif,bmp,png',upLinkUrl:'uploadPicture',upLinkExt:'zip,rar,txt,docx,doc,xls,xlsx,pdf'}" >
					</textarea>
    			</td>
    		</tr>
    		
    		
    		
    		
    		
    		<tr>
    			<td colspan="4" style="text-align: center;">
    			   <input type="button" value="保存" onclick="javascript:saveArticle();">
    			</td>
    		</tr>
    	</table>
		</s:form>
  		
  		
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
	
	<div id="catalog_pop" class="popdiv_inner" style="width: 600px;">
		<div class="popdiv_inner_outter"></div>
		<div class="popdiv_close"></div>
		<div class="popdiv_handle" title="鼠标左键按住可拖动">子栏目</div>
		<div class="popdiv_content">
		</div>
	</div>
</body>
</html>