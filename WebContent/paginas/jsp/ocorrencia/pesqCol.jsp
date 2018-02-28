<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<div dojoType="dijit.Dialog" id="dialog1" title="Localizar Colaborador" execute="">
	<label for="empresa" class="preField">Empresa</label> 
	<input dojoType="dijit.form.FilteringSelect" store="empresaStore"
		class="medium" id="emp" name="empresa" /> <br />
	<label for="colaborador.id" class="preField">Colaborador</label> 
	<input dojoType="dijit.form.TextBox" class="medium"	id="col" name="nome"> <br />
	<button dojoType="dijit.form.Button" type="button" onclick="buscaCol()">Procurar</button>
	<div id="toBeReplaced"></div>
</div>