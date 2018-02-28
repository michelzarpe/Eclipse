<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%   
    response.setHeader("Cache-Control", "no-cache");   
    response.setHeader("Pragma", "no-cache");   
    response.setDateHeader("Expires", 0);   
%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>webOper 2.0</title>
 
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/css/newSkin.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/css/icones.css" />

<!--  css plugins jquery -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/css/GridFilters.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/css/RangeMenu.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/js/fancytree/skin-win8/ui.fancytree.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/css/jquery-ui/themes/cupertino/jquery-ui.css" />
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/paginas/js/fancytree/extensions/contextmenu/css/jquery.contextMenu.css" />

<!-- Bootstrap styles -->
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/paginas/bootstrap/css/bootstrap.css">-->


<!-- plugins jquery -->
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/jquery/jquery-2.0.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/fancytree/jquery.fancytree-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/fancytree/extensions/contextmenu/js/jquery.contextMenu-1.6.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/fancytree/extensions/contextmenu/js/jquery.fancytree.contextMenu.js"></script>

<!-- bootstrap -->
<!--<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/bootstrap/js/bootstrap.js"></script>-->

<!-- scripts ext js -->
<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/ext-base.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/ext-js/ext-all.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/ext-js/RowExpander.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/ext-js/CheckColumn.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/ext-js/ext-lang-pt_BR.js'></script>
<!-- arquivos do site -->
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/layout.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/validaCPF.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/novaSolicitacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/processaImplantacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/myHome.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/menuGeral.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commom.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/preCad.js'></script>
<script charset="UTF-8" type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/cadastroCompleto.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commonsColaborador.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisaColaborador.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commonsSolicitacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commonsViagem.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commonsSolicitacaoCompra.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commonsRequisicaoEstoque.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisa.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/liberaSolicitacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisaUniforme.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/alteraSenha.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/tiposValidacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/viagem.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisaViagem.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/filtroDemitidos.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/usuario.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commonsUsuario.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/ext-js/uxmedia.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/SID.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/solicitacaoCompra.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/requisicaoEstoque.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/commonsProduto.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/requisicaoMaterialCargaPosto.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/solicitacaoCompraServico.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisaRequisicao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/preCadFor.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/pesquisaSolicitacao.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/confCompras.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/paginas/js/documento.js'></script>
<!-- extensions -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/menu/RangeMenu.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/menu/ListMenu.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/GridFilters.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/filter/Filter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/filter/StringFilter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/filter/DateFilter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/filter/ListFilter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/filter/NumericFilter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/paginas/js/ext-js/gridfilters/filter/BooleanFilter.js"></script>

<style type="text/css">
span.fancytree-node.cssPdf > span.fancytree-title {
  color: maroon;
}
span.fancytree-node.cssPdf > span.fancytree-icon {
  background-image: url("<%=request.getContextPath()%>/paginas/js/img/pdf.gif");
  background-position: 0 0;
}
</style>

</head>

<sj:head debug="true" compressed="true" jqueryui="true" />
<script type="text/javascript">
       $.subscribe('before', function(event,data) {
    	   $('#result').show();
    	   $('#result').removeClass('alert-error alert-success');
    	   $('#result').addClass('alert alert-info');
           $('#result').html('Carregando ...');
       });
       
       $.subscribe('success', function(event,data) {
    	   $('#result').removeClass('alert-error alert-info');
    	   $('#result').addClass('alert alert-success');
           $('#result').html(event.originalEvent.request.responseText);
       });
          
       $.subscribe('errorState', function(event,data) {
    	   $('#result').removeClass('alert-info alert-success');
    	   $('#result').addClass('alert alert-error');
           $('#result').html('Erro ao tentar gravar documento: status ' + event.originalEvent.status + ": " + event.originalEvent.request.status);
       });
    </script> 
<body>
	<div id="dialog">
		<div id="tree">
			<div id="context-menu-one"></div>
		</div>
	</div>
	<div id="dialog-confirm"></div>
	<div id="dialog-doc" style="display: none">
	
	<div id="result" hidden="true" class="alert"></div>
	
	<img id="indicator" src="<%= request.getContextPath()%>/paginas/images/indicator.gif" alt="Carregando..." style="display:none"/>
	
	<s:form id="frmDoc" enctype="multipart/form-data" cssClass="form-horizontal" >
	 	<s:hidden name="documento.id" id="id" />
	 	<s:hidden name="documento.docPai.id" id="docPai" />
	 	<div class="form-group">	
	 		<h6><label for="nomDoc" class="col-lg-3 control-label">Nome do documento</label></h6>
			<div class="col-lg-9">
	 			<input type="text" name="documento.nomDoc" id="nomDoc" size="30" class="form-control input-sm" placeholder="Nome do documento" />
	 		</div>
	 	</div>
		<div class="form-group" id="grpArq">
			<h6><label class="col-lg-3 control-label" for="arquivo">Arquivo relacionado</label></h6>
			<div class="col-lg-9"> 
				<input type="file" id="arquivo" name="arquivo">
			</div>
			<div class="col-lg-3">
			</div>
			<div class="col-lg-9">
				<p class="help-block">Localize o arquivo que será exibido nesse documento.</p>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-lg-3">
			</div>	
	 		<label class="checkbox-inline  col-lg-3">
  			<input type="checkbox" value="true" name="manterArquivo"> Manter arquivo
			</label>
	 	</div>
			
	 	<s:url id="gravaDoc" value="documento!gravar.action"/>
		 	
	 	<sj:submit id="formSubmit2"	href="%{gravaDoc}" targets="result"	value="Gravar" indicator="indicator" 
	 	onClickTopics="before" onSuccessTopics="success" onErrorTopics="errorState" button="true" clearForm="true"/>
				 	
		</s:form>
		 
	</div>
    <div id="west" class="x-hide-display"> </div>
    <div id="center2" class="x-hide-display"></div>
    <div id="center1" class="x-hide-display">
    </div>
    <div id="props-panel" class="x-hide-display" style="width:200px;height:200px;overflow:hidden;"></div>
    <div id="south" class="x-hide-display">
        <p>(Usuário logado: ${sessionScope.Usuario.id} - ${sessionScope.Usuario.nomFun})</p>
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
