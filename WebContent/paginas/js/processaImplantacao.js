function processImpl() {
	leitorX = criaLeitorSol();
	st = new Ext.data.Store( {
		remoteSort : true,
		proxy : new Ext.data.HttpProxy( {
			url : 'solicitacao!listPendentesAdmissao.action',
			method : 'GET'
		}),
		reader : leitorX
	});
	var smSol = new Ext.grid.CheckboxSelectionModel( {
		singleSelect : true
	});
	var sPag = Ext.getCmp('sPag');
	if (!sPag) {
		pagingBar = criaPaginador(st);
	}
	
	var sGrid = criaGridSol(smSol, pagingBar, st);
	sGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, record) {
		geraListaItensPro(record);
	});
	
	var win = new Ext.Window( {
		title : 'Processamento de implantações',
		renderTo : 'pnCenter',
		width : 830,
		height : 400,
		layout : 'fit',
		border : false,
		modal : true,
		maximizable : true,
		items : [ sGrid ]
	});
	
	win.show();
	
	st.setDefaultSort('col_nomFun', 'ASC');
	st.load( {
		params : {
			start : 0,
			limit : 25
		}
	});
}
function geraListaItensPro(record) {
	leitorXML = geraLeitorItens();
	var storeItens = new Ext.data.Store( {
		url : 'itemSolicitacao!libXML.action?idSol=' + record.get('solicitacao.id'),
		reader : leitorXML
	});
	var sm2 = new Ext.grid.CheckboxSelectionModel( {
		singleSelect : false
	});
	var tb = new Ext.Toolbar( {
		id : 'tbItens',
		items : [ new Ext.Button( {
			text : 'Processar',
			iconCls : 'icnPrc',
			tooltip : 'Processa os itens selecionados',
			handler : processaItem
		}), new Ext.Button( {
			text : 'Imprimir DR',
			iconCls : 'icnPrt',
			tooltip : 'Imprime a DR da solicitação',
			handler : imprimeDR
		}), new Ext.Button( {
			text : 'Atender',
			iconCls : 'icnAte',
			tooltip : 'Atender o item selecionado',
			handler : atenderItem,
			id : 'btAte'
		}), new Ext.Button( {
			text : 'Estornar',
			iconCls : 'icnEst',
			tooltip : 'Estornar o envio',
			handler : estornarItem,
			id : 'btExt'
		}) ]
	});
	var gridItens = criaGridItens(storeItens, sm2, tb);
	var winDetail = criaJanelaItens(gridItens, record);
	winDetail.on('close', function() {
		winDetail.destroy();
		// st.reload();
		});
	winDetail.show();
	winDetail.center();
	storeItens.load();
}
function imprimeDR() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel().getSelected();
	if (Ext.getCmp('gridItens').getSelectionModel().getSelected()) {
		window.location = 'solicitacao!imprimirProtocolo.action?idSol=' + linhaSelecionada.get('id.solicitacao.id');
	} else {
		Ext.Msg.show( {
			title : 'Erro',
			msg : 'Selecione um item',
			buttons : Ext.MessageBox.OK,
			animEl : 'gridItens',
			icon : Ext.MessageBox.ERROR
		});
	}
}

function reimprimeDR() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel().getSelected();
	if (Ext.getCmp('gridItens').getSelectionModel().getSelected()) {
		window.location = 'solicitacao!reimprimirProtocolo.action?idSol=' + linhaSelecionada.get('id.solicitacao.id');
	} else {
		Ext.Msg.show( {
			title : 'Erro',
			msg : 'Selecione um item',
			buttons : Ext.MessageBox.OK,
			animEl : 'gridItens',
			icon : Ext.MessageBox.ERROR
		});
	}
}

function estornarItem() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel().getSelected();
	if (Ext.getCmp('gridItens').getSelectionModel().getSelected()) {
		Ext.Msg.show( {
			title : 'Confirmação!',
			msg : 'Isso fará com que a data de envio do item seja removida. Confirma estorno?',
			buttons : Ext.Msg.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : function(button) {
				if (button == 'yes') {
					Ext.Ajax.request( {
						url : 'itemSolicitacao!estornarItem.action',
						params : {
							idSol : linhaSelecionada.get('id.solicitacao.id'),
							idUni : linhaSelecionada.get('id.uniforme.id')
						},
						success : function(result, request) {
							Ext.Msg.show( {
								title : 'Sucesso',
								msg : result.responseText,
								buttons : Ext.MessageBox.OK,
								animEl : 'gridItens',
								icon : Ext.MessageBox.INFO,
								fn : function() {
									Ext.getCmp('gridItens').getStore().reload( {
										url : 'itemSolicitacao!libXML.action?idSol=' + linhaSelecionada.get('id.solicitacao.id'),
										params : {
											start : 0,
											limit : 25
										}
									});
								}
							});
						},
						failure : function(result, request) {
							Ext.Msg.show( {
								title : 'Falha',
								msg : result.responseText,
								buttons : Ext.MessageBox.OK,
								animEl : 'gridItens',
								icon : Ext.MessageBox.ERROR
							});
						}
					});
				}
			}
		});
	} else {
		Ext.Msg.show( {
			title : 'Erro',
			msg : 'Selecione um item...',
			buttons : Ext.MessageBox.OK,
			animEl : 'gridItens',
			icon : Ext.MessageBox.ERROR
		});
	}
}