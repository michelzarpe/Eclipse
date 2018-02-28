dojo.require("dojo.parser");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojox.data.QueryReadStore");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.Dialog");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboBox");
dojo.require("dijit.form.TimeTextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.Textarea");
dojo.require("dojox.grid.DataGrid");

function buscaCol() {
	dojo
			.xhrGet( {
				url :"pesquisaColaborador!pesquisaCol.action",
				load : function(responseObject, ioArgs) {
					var textBuffer = [ "A busca retornou "
							+ responseObject.items.length + " colaboradores:" ];
					for ( var i = 0; i < responseObject.items.length; i++) {
						var link1 = document.createElement("a");
						link1.href = "javascript: selectCol('"
								+ responseObject.items[i].name + "', "
								+ responseObject.items[i].id + ")";
						link1.innerHTML = responseObject.items[i].name;
						dojo.byId("toBeReplaced").appendChild(link1);
						dojo.byId("toBeReplaced").appendChild(
								document.createElement("br"));
						textBuffer.push(responseObject.items[i].id + " - "
								+ responseObject.items[i].name);
					}
					return responseObject;
				},
				error : function(response, ioArgs) {
					dojo.byId("toBeReplaced").innerHTML = "Um erro ocorreu, com resposta: "
							+ response;
					return response;
				},
				form :"form1",
				content : {
					nome :dijit.byId('col').value,
					empresa :dijit.byId('emp').value
				},
				handleAs :"json"
			});
}

function selectCol(nome, id) {
	document.getElementById("nomCol").value = nome;
	document.getElementById("idCol").value = id;
	dojo.byId('col').value = "";
	dojo.byId("toBeReplaced").innerHTML = "";
	dijit.byId('dialog1').hide();
}

function buscaSubTipoMotivos() {
	try {
		dijit.byId('subTipoSelect').destroy();
		document.getElementById('lbJus').style.display = "none";
	} catch (err) {
	}

	var motivoId = dijit.byId('motivo').value;
	if (motivoId == 43) {// troca de posto
		dojo.byId('divTrcPos').style.display = '';
		dojo.byId('divTrcEsc').style.display = 'none';
		dojo.byId('divTrcHor').style.display = '';
		dojo.byId('divTrcCar').style.display = 'none';
	} else if (motivoId == 40) {// troca de escala
		dojo.byId('divTrcEsc').style.display = '';
		dojo.byId('divTrcHor').style.display = '';
		dojo.byId('divTrcCar').style.display = 'none';
		dojo.byId('divTrcPos').style.display = 'none';
	} else if (motivoId == 41) {// troca de horário
		dojo.byId('divTrcEsc').style.display = 'none';
		dojo.byId('divTrcHor').style.display = '';
		dojo.byId('divTrcCar').style.display = 'none';
		dojo.byId('divTrcPos').style.display = 'none';
	} else if (motivoId == 42) {// troca de cargo
		dojo.byId('divTrcEsc').style.display = 'none';
		dojo.byId('divTrcHor').style.display = '';
		dojo.byId('divTrcCar').style.display = '';
		dojo.byId('divTrcPos').style.display = 'none';
	} else if (motivoId == 1) {// falta justificada
		createSubTipoSelect(motivoId);
		dojo.byId('divTrcEsc').style.display = 'none';
		dojo.byId('divTrcHor').style.display = '';
		dojo.byId('divTrcCar').style.display = 'none';
		dojo.byId('divTrcPos').style.display = 'none';
	} else {
		dojo.byId('divTrcEsc').style.display = 'none';
		dojo.byId('divTrcHor').style.display = '';
		dojo.byId('divTrcCar').style.display = 'none';
		dojo.byId('divTrcPos').style.display = 'none';
	}
}

function createSubTipoSelect(motivo) {
	document.getElementById('lbJus').style.display = "none";
	var subStore = new dojox.data.QueryReadStore( {
		url :'subTipoMotivo!listAllByMotivo.action?motivoId=' + motivo
	});
	var gotList = function(items, request) {
		if (items.length > 0) {
			// cria o div que vai receber o combo de subTipos
			divCombo = document.createElement('div');
			divCombo.id = 'divCombo';
			document.getElementById('divSubTipo').appendChild(divCombo);

			// Cria o select de subTipos
			var _combobox = new dijit.form.ComboBox( {
				id :"subTipoSelect",
				name :"subTipoMotivo.id",
				value :"Selecione",
				store :subStore,
				searchAttr :"name",
				autoComplete :false
			}, divCombo);
			document.getElementById('lbJus').style.display = "";
		} else {
			dijit.byId('subTipoSelect').destroy();
		}
	}
	var gotError = function(error, request) {
		alert("The request to the store failed. " + error);
	}

	subStore.fetch( {
		onComplete :gotList,
		onError :gotError
	});
}

function abreLocaliza(nivel) {
	divNivAnt = document.createElement('div');
	divNivAnt.id = 'divEmp';
	document.getElementById('selEmp').appendChild(divNivAnt);
	if (nivel == "3") {
		var locStore = new dojo.data.ItemFileReadStore( {
			url :'local!listAllEmpresas.action'
		});
		var _combobox = new dijit.form.FilteringSelect( {
			id :"nivelAnterior",
			name :"nivelAnterior",
			value :"Selecione",
			store :locStore,
			searchAttr :"name",
			autoComplete :false
		}, divNivAnt);
		locStore.fetch();
		dijit.byId('dialog2').show();
	}
	dojo.byId('nivel').value = nivel;
	if (nivel == '4') {
		if (dojo.byId('clienteLocId').value == "") {
			alert('Selecione antes o cliente...')
		} else {
			var txtLocal = new dijit.form.TextBox( {
				name :'nivelAnterior',
				id :'nivelAnterior',
				trim :true,
				propercase :true,
				value :dojo.byId('clienteLocId').value
			}, divNivAnt);
			document.getElementById("selEmp").style.display = '';
			var lbBusca = document.getElementById("lbBusca");
			lbBusca.removeChild(lbBusca.lastChild);
			lbBusca.appendChild(document.createTextNode("Nome da cidade"));
			dijit.byId('dialog2').titleNode.innerHTML = "Localizar cidade...";
			dijit.byId('dialog2').show();
		}
	} else if (nivel == '5') {
		if (dojo.byId('cidadeLocId').value == "") {
			alert('Selecione antes a cidade...')
		} else {
			var txtLocal = new dijit.form.TextBox( {
				name :'nivelAnterior',
				id :'nivelAnterior',
				trim :true,
				propercase :true,
				value :dojo.byId('cidadeLocId').value
			}, divNivAnt);
			document.getElementById("selEmp").style.display = '';
			var lbBusca = document.getElementById("lbBusca");
			lbBusca.removeChild(lbBusca.lastChild);
			lbBusca.appendChild(document.createTextNode("Nome do posto"));
			dijit.byId('dialog2').titleNode.innerHTML = "Localizar posto...";
			dijit.byId('dialog2').show();
		}
	}
}

function localiza() {
	niv = document.getElementById('nivel').value;
	var campoNome;
	var campoId;
	if (niv == "3") {
		nomeObjRet = " cliente(s)";
		campoNome = 'locCli';
		campoId = 'clienteLocId';
	} else if (niv == "4") {
		nomeObjRet = " cidade(s)";
		campoNome = 'locCid';
		campoId = 'cidadeLocId';
	} else if (niv == "5") {
		nomeObjRet = " posto(s)";
		campoNome = 'locPos';
		campoId = 'postoLocId';
	}
	dojo
			.xhrGet( {
				url :"pesquisaLocal!listResultJson.action",
				load : function(responseObject, ioArgs) {
					var textBuffer = [ "A busca retornou "
							+ responseObject.items.length + nomeObjRet + ":" ];
					dojo.byId("resultado").innerHTML = textBuffer;
					dojo.byId('resultado').appendChild(
							document.createElement("br"));
					for ( var i = 0; i < responseObject.items.length; i++) {
						var quebra = document.createElement("br");
						var link1 = document.createElement("a");
						link1.href = "javascript: fillInfo('"
								+ responseObject.items[i].name + "', '"
								+ responseObject.items[i].id + "', '"
								+ campoNome + "', '" + campoId + "')";
						link1.innerHTML = responseObject.items[i].name;
						dojo.byId("resultado").appendChild(link1);
						dojo.byId("resultado").appendChild(quebra);
					}
					return responseObject;
				},
				error : function(response, ioArgs) {
					dojo.byId("resultado").innerHTML = "Um erro ocorreu, com resposta: "
							+ response;
					return response;
				},
				form :"form1",
				content : {
					dadoBusca :dojo.byId('dadoBusca').value,
					nivelAnterior :dijit.byId('nivelAnterior').value,
					nivel :dojo.byId('nivel').value
				},
				handleAs :"json"
			});
}

function fechaPesquisa() {
	dijit.byId('nivelAnterior').destroy();
	document.getElementById("selEmp").style.display = 'none';
	dojo.byId('dadoBusca').value = "";
	dojo.byId("resultado").innerHTML = "";
	dijit.byId('dialog2').hide();
}

function fillInfo(nome, id, campoNome, campoId) {
	document.getElementById(campoNome).value = nome;
	document.getElementById(campoId).value = id;
	fechaPesquisa();
}

function gravar() {
	dojo
			.xhrPost( {
				url : "ocorrencia!gravar.action",
				load : function(responseObject, ioArgs) {
					dojo.byId("resultGravacao").innerHTML = "Gravado com sucesso!";
					return responseObject;
				},
				error : function(response, ioArgs) {
					dojo.byId("resultGravacao").innerHTML = "Um erro ocorreu, com resposta: "
							+ response;
					return response;
				},
				form :"form1",
				handleAs :"json"
			});
}