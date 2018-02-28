Ext.onReady(function() {
			if (tipoUsuario == "AAU") {
				geraListaSol();
			}
		});
// Carregar solicita��es com itens pendentes
function geraListaSol() {
	leitorX = criaLeitorSol();
	var st = new Ext.data.Store({
				remoteSort : true,
				proxy : new Ext.data.HttpProxy({
							url : 'itemSolicitacao!listSolicitacoesLiberar.action',
							method : 'POST'
						}),
				reader : leitorX
			});

	var smSol = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
			
	var sPag = new Ext.PagingToolbar({
				pageSize : 25,
				store : st,
				displayInfo : true,
				displayMsg : 'Exibindo itens de {0} até {1} de {2}',
				emptyMsg : "Nenhum item encontrado",
				id : 'sPag'
			});
			
	var sGrid = criaGridSol(smSol, sPag, st);

	var tb = sGrid.getTopToolbar();

	if ((tipoUsuario == "ASI") || (tipoUsuario == "AAU")) {
		tb.add({
					text : 'Mostrar negados',
					tooltip : 'Visualiza os itens negados',
					iconCls : 'icnView',
					handler : mostraNegados
				});
	}
	sGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, record) {
				geraListaItens(record);
			});
	var win = new Ext.Window({
				title : 'Liberação de itens',
				renderTo : 'pnCenter',
				width : 600,
				height : 400,
				minWidth : 300,
				minHeight : 200,
				layout : 'fit',
				border : false,
				modal : true,
				items : [sGrid],
				maximizable : true
			});
	win.show();
	st.setDefaultSort('colaborador.nomFun', 'ASC');
	st.load({
		params : {
		start : 0,
		limit : 25
		}
	});
}
function mostraNegados() {
	st.reload({
				params : {
					start : 0,
					limit : 25,
					mostraNegados : true
				}
			});
}
/**
 * permite a exibi��o de itens negados
 */
// Carrega itens da solicita��o selecionada
function geraListaItens(record) {
	leitorXML = geraLeitorItens();
	var storeItens = new Ext.data.Store({
				url : 'itemSolicitacao!libXML.action?idSol='
						+ record.get('solicitacao.id'),
				reader : leitorXML
			});
	var sm2 = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false
			});
	var tb = new Ext.Toolbar({
				id : 'tbItens',
				items : [new Ext.Button({
									text : 'Autorizar',
									iconCls : 'icnAut',
									tooltip : 'Autoriza o envio do item selecionado',
									handler : liberaItem
								}), new Ext.Button({
									text : 'Negar',
									iconCls : 'icnNeg',
									tooltip : 'Nega o envio do item selecionado',
									handler : negaItem
								})]
			});
	var gridItens = criaGridItens(storeItens, sm2, tb);
	var winDetail = criaJanelaItens(gridItens, record);

	winDetail.show();
	winDetail.center();
	storeItens.load();

}// final function geraListaItens
// Exibe tela de configura��o do relatério
function showConfReport(negLib) {
	var formConf = new Ext.form.FormPanel({
				labelWidth : 100,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				defaultType : 'datefield',
				width : 350,
				defaults : {
					width : 175
				},
				items : [{
							fieldLabel : 'Data inicial',
							name : 'datIni',
							id : 'datIni',
							xtype : 'datefield',
							endDateField : 'datFin',
							allowBlank : false
						}, {
							fieldLabel : 'Data final',
							name : 'datFin',
							id : 'datFin',
							xtype : 'datefield',
							startDateField : 'datIni',
							allowBlank : false
						}]
			});
	var windowConf = new Ext.Window({
				title : 'Parametrização do relatório',
				width : 350,
				height : 200,
				minWidth : 350,
				minHeight : 200,
				layout : 'fit',
				plain : true,
				bodyStyle : 'padding: 5px',
				closeAction : 'close',
				buttonAlign : 'center',
				items : formConf,
				buttons : [{
					text : 'Executar',
					handler : function() {
						if (negLib == 'lib')
							urlRel = 'report!reportLiberacoes.action?datIni='
									+ Ext.get('datIni').dom.value + '&datFin='
									+ Ext.get('datFin').dom.value;
						else if (negLib == 'neg')
							urlRel = 'report!reportNegacoes.action?datIni='
									+ Ext.get('datIni').dom.value + '&datFin='
									+ Ext.get('datFin').dom.value;
						window.location = urlRel;
					}
				}]
			});
	windowConf.show();
	windowConf.center();
}

// Executa a chamada para liberar o item selecionado
function liberaItem() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel()
			.getSelected();
	var formLibera = new Ext.form.FormPanel({
				baseCls : 'x-plain',
				labelWidth : 55,
				url : 'itemSolicitacao!autorizaItem.action',
				defaultType : 'textfield',
				items : [{
							fieldLabel : 'Motivo da liberacao',
							xtype : 'textarea',
							name : 'motLib',
							anchor : '100% -53',
							id : 'motLib',
							value : linhaSelecionada.get('motLib')
						}, {
							xtype : 'checkbox',
							boxLabel : 'Cobrar do colaborador',
							name : 'cobCol',
							id : 'cobCol',
							inputValue : 'true',
							labelStyle : 'display : none'
						}]
			});
	var windowLibera = new Ext.Window({
		title : 'Liberando item',
		width : 300,
		height : 200,
		minWidth : 300,
		minHeight : 200,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding: 5px',
		closeAction : 'close',
		buttonAlign : 'center',
		items : formLibera,
		buttons : [{
			text : 'OK',
			handler : function() {
				Ext.Ajax.request({
					url : 'itemSolicitacao!autorizaItem.action',
					params : {
						idSol : linhaSelecionada.get('id.solicitacao.id'),
						idUni : linhaSelecionada.get('id.uniforme.id'),
						motLib : Ext.getCmp('motLib').getValue(),
						cobCol : Ext.getCmp('cobCol').getValue()
					},
					success : function(result, request) {
						Ext.Msg.show({
							title : 'Sucesso',
							msg : result.responseText,
							buttons : Ext.MessageBox.OK,
							fn : function() {
								Ext.getCmp('gridItens').getStore().reload({
									url : 'itemSolicitacao!libXML.action?idSol='
											+ linhaSelecionada
													.get('id.solicitacao.id')
								});
							},
							animEl : 'gridItens',
							icon : Ext.MessageBox.INFO
						});
					},
					failure : function(result, request) {
						Ext.Msg.show({
									title : 'Falha',
									msg : result.responseText,
									buttons : Ext.MessageBox.OK,
									animEl : 'gridItens',
									icon : Ext.MessageBox.ERROR
								});
					}
				});
				windowLibera.close();
			}
		}]
	});
	windowLibera.show();
}

// Executa a chamada para negar o item selecionado
function negaItem() {
	if (Ext.getCmp('gridItens').getSelectionModel().getSelected()) {
		var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel()
				.getSelected();
		var formNega = new Ext.form.FormPanel({
					baseCls : 'x-plain',
					labelWidth : 55,
					url : 'itemSolicitacao!negaItem.action',
					defaultType : 'textfield',
					items : [{
								fieldLabel : 'Motivo',
								xtype : 'textarea',
								name : 'motLib',
								anchor : '100% -53',
								id : 'motLib',
								value : linhaSelecionada.get('motLib')
							}]
				});
		var windowNega = new Ext.Window({
			title : 'Negando item',
			width : 300,
			height : 200,
			minWidth : 300,
			minHeight : 200,
			layout : 'fit',
			plain : true,
			bodyStyle : 'padding: 5px',
			closeAction : 'close',
			buttonAlign : 'center',
			items : formNega,
			buttons : [{
				text : 'OK',
				handler : function() {
					Ext.Ajax.request({
						url : 'itemSolicitacao!negaItem.action',
						params : {
							idSol : linhaSelecionada.get('id.solicitacao.id'),
							idUni : linhaSelecionada.get('id.uniforme.id'),
							motLib : Ext.getCmp('motLib').getValue()
						},
						success : function(result, request) {
							Ext.Msg.show({
								title : 'Sucesso',
								msg : result.responseText,
								buttons : Ext.MessageBox.OK,
								fn : function() {
									Ext.getCmp('gridItens').getStore().reload({
										url : 'itemSolicitacao!libXML.action?idSol='
												+ linhaSelecionada
														.get('id.solicitacao.id')
									});
								},
								animEl : 'gridItens',
								icon : Ext.MessageBox.INFO
							});
						},
						failure : function(result, request) {
							Ext.Msg.show({
										title : 'Falha',
										msg : result.responseText,
										buttons : Ext.MessageBox.OK,
										animEl : 'gridItens',
										icon : Ext.MessageBox.ERROR
									});
						}
					});
					windowNega.close();
				}
			}]
		});
		windowNega.show();
	} else {
		Ext.Msg.show({
					title : 'Erro',
					msg : 'Selecione um item',
					buttons : Ext.MessageBox.OK,
					animEl : 'gridItens',
					icon : Ext.MessageBox.ERROR
				});
	}
}
