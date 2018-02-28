<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
	<title>webOper 2.0 - Grupo Zanardo</title>
	<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/css/login.css" />
	<s:head parseContent = "true" />
</head>
<body>

<div id="content">
	<div id="header">
		<p id="top_info">Grupo Zanardo<br />
		Por favor, efetue login para acesso ao sistema.</p>
		<div id="logo">
			<h1><a href="#">webOper <span class="title">2.0</span></a></h1>
			<p>Módulo de Admissão e Uniformes</p>
		</div>
	</div>
	<div id="tabs">
		<br><br><br><br>
	</div>
	<div>
		<s:actionerror theme="ajax" cssClass="msgErro"/> 
		<s:form action="login!login.action" method="POST" validate="true" namespace="/">
			<s:textfield name="codUsu" label="Usuário" cssClass="search"  />
			<s:password name="senUsu" label="Senha" cssClass="search"  />
			<s:submit value="Entrar" cssClass="button" />
		</s:form>
	</div>
	<div class="footer">
		<jsp:include page="../template/footer.jsp"></jsp:include>
	</div>
</div>

</body>
</html>