<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>文章具体内容</title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/starmos/backstage/js/checkrole.js"></script>
    <script>
		
    </script>
  </head>
  <body>
  	<div class="conten_div">
    	<div style="width: 100%;border: 1px solid red;" id="third_top">顶部</div>
    	<!-- 具体内容，左边 -->
    	<div class="fl content_left" style="border: 1px solid red;">
    		<div>
    			<s:property value="#request.displayContent" escape="false"/>
    		</div>
    		<span style="clear: both;width: 100%;border: 1px solid red;text-align: center;display: block;">
    		  ${ep.link }
    		</span>  
    	</div>
    	<!-- 具体内容，右边 -->
    	<div class="fr content_right" style="border: 1px solid red;">右边</div>
    	<div style="width: 100%;clear: both;border: 1px solid red;" id="third_bottom">底部</div>
    </div>
  </body>	
</html>
