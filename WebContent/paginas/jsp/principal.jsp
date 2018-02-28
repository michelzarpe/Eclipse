<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>webOper 2.0</title> 
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/css/newSkin.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/css/icones.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/ext-base.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/ext-js/ext-all.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/layout.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/ext-js/ext-lang-pt_BR.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/menuGeral.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commom.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/preCad.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisaColaborador.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisa.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/liberaSolicitacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisaUniforme.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/alteraSenha.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/tiposValidacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/filtroDemitidos.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/solicitacaoCompra.js'></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/jquery/jquery-2.0.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/fancytree/jquery.fancytree.js"></script>
<script type="text/javascript">
	$.noConflict();
	alert('principal');
</script>
</head>
<body>
	<div id="norte">
		<jsp:include page="../template/header.jsp"/>
		<div id="menu"></div>
	</div>
	<div id="centro"></div>
	<div id="sul">(Usuário logado: ${sessionScope.Usuario.id} - ${sessionScope.Usuario.nomFun})
		<!-- NAO REMOVER NUNCA ESTE BLOCO -->
		<s:hidden name="usuario.id" id="usuario.id">
			<s:param name="value">${sessionScope.Usuario.id}</s:param>
		</s:hidden>
		<s:hidden name="usuario.tipUsu" id="usuario.tipUsu">
			<s:param name="value">${sessionScope.Usuario.tipUsu}</s:param>
		</s:hidden>
		<s:hidden name="usuario.nomFun" id="usuario.nomFun">
			<s:param name="value">${sessionScope.Usuario.nomFun}</s:param>
		</s:hidden>
		<!-- ATÉ AQUI-------------------- -->
	</div>
</body>
</html>