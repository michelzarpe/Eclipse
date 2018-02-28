<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%   
    response.setHeader("Cache-Control", "no-cache");   
    response.setHeader("Pragma", "no-cache");   
    response.setDateHeader("Expires", 0);   
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>webOper 2.0 - Grupo Zanardo</title>
	<style type="text/css">@import url( <%=request.getContextPath()%>/paginas/css/main.css );</style>
</head>
<body>
	<jsp:include page="scripts.jsp" />
	<s:head theme="ajax" debug="false" />
	<div id="content">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>
		<div id="tabs">
			<tiles:insertAttribute name="menu" />
		</div>
		<div>
			<center><tiles:insertAttribute name="mensagens" /> </center>
			<tiles:insertAttribute name="body" />
		</div>
		<div id="footer">
			<p><tiles:insertAttribute name="footer" /></p>
		</div>
	</div>
	</body>
</html>
