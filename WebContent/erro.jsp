<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" isErrorPage="true"%>
<html> 
<head> 
   <title>Página de Erro</title>  
</head> 

<body bgcolor="RED"> 

<!-- AQUI VOCÊ ENFEITA A PAG--> 
<h3>Ocorreu um erro: <%= exception.getMessage() %></h3> 

</body> 
</html> 
