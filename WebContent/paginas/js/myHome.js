/*
I - Cadastro imcompleto <Falta cadastrar mais informações>
C - Cadastro completo
G - Integrado <Passagem das informações para o Administração de pessoal>
H - Homologado <todas as informações estão corretas e foi feita a admissão. Caso rejeitado volta para Incompleto>
*/


function minhasAdmissoes() {
	var campos = [{name : 'colaborador.id',	mapping : 'colaboradorId'}, 
				  {name : 'nomFun',  		mapping : '@nomFun'   	 }, 
				  'numCad', 
				  {name : 'empresaId',      mapping : 'empresa > id' }, 
				  {name : 'empresa_id',		mapping : 'empresa > name'}, 
				  {name : 'centro_id',		mapping : 'centro > nomCcu'}, 'sitAdm', 'datAdm', 
				  {name : 'cargo_id_codCar',mapping : 'cargo > name'}, 
				  {name : 'codUsu',			mapping : 'usuCad > codUsu'	}];

	var leitorX = new Ext.data.XmlReader({record : 'colaborador',id : 'colaborador.id',totalProperty: 'totalCount'}, campos);

	var stxCol = new Ext.data.Store({remoteSort : true,id : 'stxCol',proxy : new Ext.data.HttpProxy({url : 'home!listAdm.action',method : 'POST'}), reader : leitorX});

	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
	
	var pagingBarCol = new Ext.PagingToolbar({pageSize : 25, store : stxCol, displayInfo : true, displayMsg : 'Exibindo colaboradores {0} - {1} of {2}', emptyMsg : "Nenhum colaborador encontrado"});

	var gridCol = new Ext.grid.GridPanel({
		store : stxCol,	
		loadMask : true,
		cm : new Ext.grid.ColumnModel([sm, 
			{header : 'Cod.',dataIndex : 'colaborador.id', sortable : true, width : 50,fixed : true},
			{header : 'Status',	width : 20,	dataIndex : 'sitAdm', sortable : true },
			{header : "Colaborador", dataIndex : 'nomFun', sortable : true, width : 150, fixed : true},
			{header : 'Data admissão', dataIndex : 'datAdm', sortable : true, width : 50 }, 
			{header : 'Cargo', dataIndex : 'cargo_id_codCar', sortable : true }, 
			{header : 'Empresa', dataIndex : 'empresa_id', sortable : true }, 
			{header : 'Usuário', width : 100, dataIndex : 'codUsu', sortable : true}, 
			{header : 'Centro', width : 100, dataIndex : 'centro_id', sortable : true}]),
		sm : sm,
		tbar : [new Ext.Toolbar({id : 'tBar',
						items : [
							{   text : 'Ficha Admissional',
								iconCls : 'icnFic',
								tooltip : 'Imprime ficha admissional do colaborador',
								handler : imprimeFicha,
								id : 'btImpFic'
							}, "-", {
								text : 'Completar/Alterar cadastro',
								iconCls : 'icnCom',
								tooltip : 'Completa os dados do prá cadastro',
								handler : completaAdmissao,
								id : 'btComAdm'
							}, "-",  {
								text : 'Excluir cadastro',
								iconCls : 'icnExc',
								tooltip : 'Excluir cadastro do colaborador',
								handler : excluiAdmissao,
								id : 'btExcAdm'
							}, "-", {
								text : 'Integrar com Adm Pessoal',
								iconCls : 'icnInt',
								tooltip : 'Integação com Administração de Pessoal',
								handler : integraAdmissao,
								id : 'btIntegra'
							}]
				})],
		bbar : pagingBarCol,
		id : 'sGridCol',
		viewConfig : {forceFit : true, getRowClass : MudaCorAdm},
		frame : true,
		iconCls : 'icon-grid'
	});

	var win = new Ext.Window({
				title : 'Admissões abertas',
				renderTo : 'pnCenter',
				layout : 'fit',
				height : 300,
                autoWidth: true,
				closeAction : 'close',
				plain : true,
				modal : true,
				maximizable : true,
				items : [gridCol]
			});
	win.show(this);

	stxCol.setDefaultSort('nomFun', 'ASC');
	stxCol.load({params : {start : 0,limit : 25}});
}

function minhasSolicitacoes() {
	// grid de solicitações pendentes
	leitorX = criaLeitorSol();
	
	var stSol = new Ext.data.Store({remoteSort : true,	totalProperty : 'totalCount',proxy : new Ext.data.HttpProxy({url : 'home!listSol.action',method : 'POST'}),reader : leitorX});
	var smSol = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
	var sPag = Ext.getCmp('sPag');
	
	if (!sPag) {
		pagingBar = criaPaginador(stSol);
	}
	
	var sGrid = criaGridSol(smSol, pagingBar, stSol);

	sGrid.getSelectionModel().on('rowselect', function(smSol, rowIdx, record) {
								geraListaItensHome(record, stSol);
								});
	var tb = sGrid.getTopToolbar();
	
	tb.add({
				text : 'Imprimir Espelho',
				iconCls : 'icnPrt',
				tooltip : 'Imprime espelho da solicitação',
				handler : imprimeEspelho,
				id : 'btImpEsp'
			}, "-", {
				text : 'Alterar',
				iconCls : 'icnCom',
				tooltip : 'Alterar solicitação',
				handler : alteraSolicitacao,
				id : 'btAltSol'
			});

	var win = new Ext.Window({title : 'Solicitações pendentes',renderTo : 'pnCenter', layout : 'fit', width : 500, height : 300, border : false, modal : true, maximizable : true, items : [sGrid]});
	win.show();
	stSol.setDefaultSort('solicitacao.motivo.id', 'DESC');
	stSol.load({params : {start : 0,limit : 25}});
}

function integraAdmissao(){
	var linhaSelecionada = Ext.getCmp('sGridCol').getSelectionModel().getSelected();
	if(linhaSelecionada){
		if(linhaSelecionada.get('sitAdm')=='C'){
			var urlPesqCol = 'pesquisaColaborador!buscaXMLFull.action?colaborador.id='+ linhaSelecionada.get('colaborador.id')+ '&colaborador.empresa.id='+ linhaSelecionada.get('empresaId') + '&buscaDemitidos=false';
			geraTelaCadastroCompleto(urlPesqCol);// cadastroCompleto.js
		}else{
			if(linhaSelecionada.get('sitAdm')=='I')
				Ext.Msg.show({title : 'Aviso',msg : 'Colaborador '+linhaSelecionada.get('nomFun')+' não está com o cadastro completo',	buttons : Ext.MessageBox.OK,animEl : 'sGridCol',icon : Ext.MessageBox.ERROR});
			if(linhaSelecionada.get('colaborador.sitAdm')=='H')
				Ext.Msg.show({title : 'Aviso',msg : 'Colaborador '+linhaSelecionada.get('nomFun')+' já está Homologado.', buttons : Ext.MessageBox.OK,animEl : 'sGridCol',icon : Ext.MessageBox.ERROR});
		}
	}else{
		Ext.Msg.show({title : 'Aviso',msg : 'Selecione um colaborador',	buttons : Ext.MessageBox.OK,animEl : 'sGridCol',icon : Ext.MessageBox.ERROR});
	}
}


function completaAdmissao() {
	var linhaSelecionada = Ext.getCmp('sGridCol').getSelectionModel().getSelected();
	if (linhaSelecionada) {
		var urlPesqCol = 'pesquisaColaborador!buscaXMLFull.action?colaborador.id='+ linhaSelecionada.get('colaborador.id')+ '&colaborador.empresa.id='+ linhaSelecionada.get('empresaId') + '&buscaDemitidos=false';
		geraTelaCadastroCompleto(urlPesqCol);// cadastroCompleto.js
	} else {
		Ext.Msg.show({title : 'Aviso',msg : 'Selecione um colaborador',	buttons : Ext.MessageBox.OK,animEl : 'sGridCol',icon : Ext.MessageBox.ERROR});
	}
}

function excluiAdmissao() {
	var linhaSelecionada = Ext.getCmp('sGridCol').getSelectionModel().getSelected();
	if (linhaSelecionada) {
		Ext.Msg.show({title : 'Confirmar exclusão',	msg : 'Tem certeza de que deseja excluir a admissão de '+ linhaSelecionada.get('nomFun') + '?',
			buttons : Ext.Msg.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : function(button) {
				if (button == 'yes') {
					var myMask = new Ext.LoadMask(Ext.getBody(), {msg : 'Aguarde, tentando excluir admissão...'});
					myMask.show();
					Ext.Ajax.request({url : 'colaborador!excluir.action',params : {	id : linhaSelecionada.get('colaborador.id')},
						success : function(result, request) {
							myMask.hide();
							if (result.responseText.localeCompare('UNI') == 0) {
								Ext.Msg.show({title : 'Aviso',
									msg : 'Esse colaborador tem uniformes enviados. Solicite exclusão do uniforme ao almoxarifado se este já foi devolvido.',
									buttons : Ext.MessageBox.OK,
									animEl : 'sGridCol',
									icon : Ext.MessageBox.WARNING
								});
							} else if (result.responseText.localeCompare('OK') == 0) {
								Ext.Msg.show({title : 'Aviso',msg : result.responseText,buttons : Ext.MessageBox.OK,
											fn : function() {
												Ext.getCmp('sGridCol').getStore().remove(linhaSelecionada);
											},
											animEl : 'sGridCol',
											icon : Ext.MessageBox.INFO
										});
							} else {
								Ext.Msg.show({title : 'Aviso',msg : 'Erro ao tentar excluir colaborador.',buttons : Ext.MessageBox.OK,animEl : 'sGridCol',icon : Ext.MessageBox.ERROR});
							}
						},
						failure : function(result, request) {
							myMask.hide();
							Ext.Msg.show({title : 'Aviso',msg : result.responseText,buttons : Ext.MessageBox.OK,animEl : 'sGridCol',icon : Ext.MessageBox.ERROR});
						}
					});
				}
			}
		})
	} else {
		Ext.Msg.show({title : 'Aviso',msg : 'Selecione um colaborador',	buttons : Ext.MessageBox.OK,animEl : 'sGridCol',icon : Ext.MessageBox.ERROR	});
	}
}

function geraListaItensHome(record, st) {
	leitorXML = geraLeitorItens();
	var storeItens = new Ext.data.Store({url : 'itemSolicitacao!libXML.action?idSol='+ record.get('solicitacao.id'),reader : leitorXML});
	var sm2 = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
	var tb = criaToolBarItens();
	var gridItens = criaGridItens(storeItens, sm2, tb);
	var winDetail = criaJanelaItens(gridItens, record);
	winDetail.show();
	winDetail.center();
	storeItens.load();
}