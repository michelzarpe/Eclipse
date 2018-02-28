<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div dojoType="dijit.Dialog" id="dialog2" title="Localizar cliente..." execute="">
	<s:hidden name="nivel" id="nivel" label="Nivel" />
	<br/>
	<div id="selEmp">
		<label class="preField" for="nivelAnterior">Empresa do cliente</label>
	</div> 
	<label class="preField" for="dadoBusca" id="lbBusca">Nome do cliente</label>
	<input type="text" dojoType="dijit.form.TextBox" name="dadoBusca" maxlength="40" /><br />
	<input type="button" dojoType="dijit.form.Button" onclick="localiza();" value="Pesquisar"> 
	<input type="button" dojoType="dijit.form.Button" onclick="fechaPesquisa()" value="Cancelar">
	<div id="resultado"></div>
</div>