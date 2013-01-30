<%@ page language="java" pageEncoding="utf-8"%>
<jsp:directive.page import="cn.www.utils.CookieUtil"/>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="www" uri="/www" %>
<%
	String www_userid = CookieUtil.getCookieValue(request,"www_userid");
	String www_username = CookieUtil.getCookieValue( request,"www_username" );
	String www_status = CookieUtil.getCookieValue( request,"www_status");
	String www_role = CookieUtil.getCookieValue( request,"www_role");
%>
<% if( www_userid == null || www_userid.trim().length()<=0 ){%>
	<s:set var="www_userid" value="null"></s:set>
<%}else{ %>
	<s:set var="www_userid"><www:cookie value="www_userid"/></s:set>
<%} %>

<% if( www_username == null || www_username.trim().length()<=0 ){%>
	<s:set var="www_username" value="null"></s:set>
<%}else{ %>
	<s:set var="www_username"><www:cookie value="www_username"/></s:set>
<%} %>

<% if( www_status == null || www_status.trim().length()<=0 ){%>
	<s:set var="www_status" value="null"></s:set>
<%}else{ %>
	<s:set var="www_status"><www:cookie value="www_status"/></s:set>
<%} %>

<% if( www_role == null || www_role.trim().length()<=0 ){%>
	<s:set var="www_role" value="null"></s:set>
<%}else{ %>
	<s:set var="www_role"><www:cookie value="www_userid"/></s:set>
<%} %>
