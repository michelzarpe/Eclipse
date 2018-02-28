<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<ul id="tablist">
	<li><a href="home.action"><img border="0" align="middle"
		src="<%=request.getContextPath()%>/paginas/img/home.png" /> Home</a></li>
	<c:if test="${sessionScope.Usuario.tipUsu.id == 4}">
		<li><a href="colaborador!preCadastro.action"> <img border="0"
			align="middle"
			src="<%=request.getContextPath()%>/paginas/img/user1_new.png" />
		Pré-Cadastro de Colaborador</a></li>
		<li><a href="colaborador.action"> <img border="0"
			align="middle"
			src="<%=request.getContextPath()%>/paginas/img/user1_new.png" />
		Colaborador - Cadastro Completo</a></li>
		<li><a href="solicitacao.action"><img border="0"
			align="middle"
			src="<%=request.getContextPath()%>/paginas/img/note_new.png" />
		Solicitação</a></li>
	</c:if>
	<c:if test="${sessionScope.Usuario.tipUsu.id != 2}">
		<li><a href="pesquisa.action"><img border="0" align="middle"
			src="<%=request.getContextPath()%>/paginas/img/note_find.png" />
		Pesquisa Solicitações</a></li>
	</c:if>
	<li><a href="pesquisaColaborador.action"><img border="0"
		align="middle"
		src="<%=request.getContextPath()%>/paginas/img/user1_find.png" />
	Localizar Colaborador</a></li>
	<li><a href="<%=request.getContextPath()%>/paginas/jsp/troca.jsp">
			<img border="0"	align="middle" src="<%=request.getContextPath()%>/paginas/img/transform.png" />Trocas
		</a>
	</li>
	<!--<li><a href="relatorio.action">Relatórios</a></li> -->
	<c:if test="${sessionScope.Usuario.tipUsu.id == 6}">
		<li><a href="sincronizacao.action"><img border="0"
			align="middle"
			src="<%=request.getContextPath()%>/paginas/img/download.png" />
		Sincronizar dados com SM</a></li>
		<li><a
			href="<%=request.getContextPath()%>/paginas/jsp/ocorrencia/ocorrencia.jsp"><img
			border="0" align="middle"
			src="<%=request.getContextPath()%>/paginas/img/download.png" />
		Ocorrência (alfa)</a></li>
		<li><a href="usuario.action"><img border="0" align="middle"
			src="<%=request.getContextPath()%>/paginas/img/download.png" />
		Usuários</a></li>
	</c:if>
	<li><a href="alteraSenha!input.action"><img border="0"
		align="middle"
		src="<%=request.getContextPath()%>/paginas/img/lock_preferences.png" />
	Alterar senha</a></li>
	<c:if test="${sessionScope.Usuario.tipUsu.id == 1}">
		<li><a href="usuario.action">Usuários</a></li>
	</c:if>
	<li><a href="login!logout.action"><img border="0"
		align="middle"
		src="<%=request.getContextPath()%>/paginas/img/door2.png" /> Sair</a></li>
</ul>
