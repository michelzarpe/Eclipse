<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>webOper 2.0 - Módulo de Ocorrências</title>
	<style type="text/css">
        @import "../../../paginas/js/dojo-release/dijit/themes/tundra/tundra.css";
        @import "../../../paginas/js/dojo-release/dojo/resources/dojo.css";
        @IMPORT "css/ocorrencia.css";
		@import "../../../paginas/js/dojo-release/dojox/grid/resources/Grid.css";
    	@import "../../../paginas/js/dojo-release/dojox/grid/resources/nihiloGrid.css";
    	.dojoxGrid table {
	        margin: 0;
	    }
		
	</style> 
	<script type="text/javascript" src="../../../paginas/js/dojo-release/dojo/dojo.js" charset="utf-8" djConfig="isDebug: false, parseOnLoad: true"></script>
	<script type="text/javascript" src="script/ocorrencia.js"></script>
</head>
<body class="tundra">
	<div dojoType="dojo.data.ItemFileReadStore" jsId="empresaStore" url="empresa!listAll.action"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="ocorrenciaStore" url="motivoOcorrencia!listAll.action"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="escalaStore" url="escala!listAll.action"></div>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="cargoStore" url="cargo!listAll.action"></div>
	<div id="principal">
		<form action="ocorrencia!gravar.action" id="form1" method="post">
			<fieldset id="fdSet">
				<legend>Lançamento de ocorrência</legend>
				<label for="ocorrencia.datOcr" class="preField">Data inicial da ocorrência</label>
				<input type="text" name="ocorrencia.datOcr"><br/>
				<label for="ocorrencia.datTer" class="preField">Data de término</label>
				<input type="text" name="ocorrencia.datTer" dojoType="dijit.form.DateTextBox" constraints="{ datePattern:'dd/MM/yyyy', selector: 'date', formatLength: 'short'}"  onclick="alert(this.value);"><br/>
				
				<!--BUSCA PELO COLABORADOR -->
				<input type="hidden" name="ocorrencia.colaborador.id" id="idCol">
				<label for="ocorrencia.colaborador.nomFun" class="preField">Colaborador</label>
				<input type="text" dojoType="dijit.form.TextBox" name="ocorrencia.colaborador.nomFun" id="nomCol" style="width: 300px;">
				<a href="#" onclick="dijit.byId('dialog1').show()"><img border="0" align="middle"  src="<%=request.getContextPath()%>/paginas/img/find_text.png" /> Localizar</a>
				<br/>
				<jsp:include page="pesqCol.jsp"/>
				
				<label for="motivo.id" class="preField">Ocorrência</label>	 
				<input dojoType="dijit.form.FilteringSelect" autocomplete="false" store="ocorrenciaStore" class="medium" id="motivo" name="ocorrencia.motivo.id" onchange="buscaSubTipoMotivos();"/>
				<br/>
				<label for="subTipoMotivo.id" id="lbJus" style="display: none" class="preField">Justificativa</label>
				<span id="divSubTipo"></span>
				<div id="divTrcHor" style="display:none">
					<jsp:include page="horario.jsp"></jsp:include>
				</div>
				<div id="divTrcEsc" style="display:none">
      				<label for="novEsc" class="preField">Nova escala</label> 
      				<input dojoType="dijit.form.FilteringSelect" autocomplete="false" store="escalaStore" class="medium" id="novaEscala" name="ocorrencia.novEsc"/>
				</div>
				<div id="divTrcCar" style="display:none">
      				<label for="novCar" class="preField">Novo cargo</label>
					<input dojoType="dijit.form.FilteringSelect" autocomplete="true" store="cargoStore" class="medium" id="novoCargo" name="ocorrencia.novCar"/>
				</div>
				<div id="divTrcPos" style="display:none">
					<!-- BUSCA PELO NOVO LOCAL -->
					<label for="novoLocal" class="preField">Novo local</label>
					<input type="text" dojoType="dijit.form.TextBox" name="locCli" id="locCli" readonly="readonly" value="${locCli}"> 
					<input type="text" dojoType="dijit.form.TextBox" id="clienteLocId" name="clienteLocId" readonly="readonly" value="${clienteLocId}">
					<a href="#" onclick="abreLocaliza(3)"><img border="0" align="middle" src="<%=request.getContextPath()%>/paginas/img/businessman_find.png" />Localizar</a>
					<br/>
					<label for="novaCidade" class="preField">Nova cidade</label>
					<input type="text" dojoType="dijit.form.TextBox" name="locCid" id="locCid" readonly="readonly" value="${locCid}"> 
					<input type="text" dojoType="dijit.form.TextBox" id="cidadeLocId" name="cidadeLocId" readonly="readonly" value="${cidadeLocId}">
					<a href="#" onclick="abreLocaliza(4)"><img border="0" align="middle" src="<%=request.getContextPath()%>/paginas/img/find_text.png" />Localizar</a>
					<br/>
					<label for="novoPosto" class="preField">Novo posto</label>
					<input type="text" dojoType="dijit.form.TextBox" name="locPos" id="locPos" readonly="readonly" value="${locPos}"> 
					<input type="text" dojoType="dijit.form.TextBox" id="postoLocId" name="ocorrencia.novoLocal.codLoc" readonly="readonly" value="${postoLocId}">
					<a href="#" onclick="abreLocaliza(5)"><img border="0" align="middle" src="<%=request.getContextPath()%>/paginas/img/pawn_find.png" />Localizar</a>
					<jsp:include page="pesqLoc.jsp"/>
				</div>
				<input type="checkbox" name="ocorrencia.cobCli" value="true" id="cb2" dojoType="dijit.form.CheckBox"/>
				<label for="cb2">Cobrar do cliente</label>
				<br/>
				<input type="checkbox" name="ocorrencia.gerCob" value="true" id="cbGerCob" dojoType="dijit.form.CheckBox" />
				<label for="cbGerCob">Necessita cobertura</label>
				<br/>
				<label for="obsItm" class="preField">Observações</label><br/>
				<textarea dojoType="dijit.form.Textarea" style="width:300px" name="ocorrencia.obsItm" id="obsItm"></textarea>
				<br/>
				<button id="1465" dojoType="dijit.form.Button" iconClass="plusIcon" onclick="gravar();">Gravar</button>
				<div id="resultGravacao"></div>
			</fieldset>
		</form>
		<table dojoType="dojox.grid.DataGrid" store="empresaStore" query="{ name: '*' }" clientSort="true" style="width: 100%; height: 200px;" rowSelector="20px">
    		<thead>
        		<tr>
					<th width="5%" field="id">Código</th>
            		<th width="95%" field="name">Nome</th>
        		</tr>
    		</thead>
		</table>
	</div>
</body>
</html>