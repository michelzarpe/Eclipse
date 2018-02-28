/**
 * Cria a toolbar padr�o da grid de itens
 * 
 * @return toolbar
 */
function criaToolBarItens() {
	var tb = new Ext.Toolbar( {
		id : 'toolBar',
		items : [ new Ext.Button( {
			text : 'Excluir',
			iconCls : 'icnExc',
			tooltip : 'Excluir o item selecionado',
			handler : excluirItem,
			id : 'btExc'
		}), new Ext.Button( {
			text : 'Atender',
			iconCls : 'icnAte',
			tooltip : 'Atender o item selecionado',
			handler : atenderItem,
			id : 'btAte'
		}), new Ext.Button( {
			text : 'Processar',
			iconCls : 'icnPrc',
			tooltip : 'Processa os itens selecionados',
			handler : processaItem,
			id : 'btPro'
		}), new Ext.Button( {
			text : 'Imprimir DR',
			iconCls : 'icnPrt',
			tooltip : 'Imprime a DR da solicitação',
			handler : imprimeDR,
			id : 'btImp'
		}), new Ext.Button( {
			text : 'Estornar',
			iconCls : 'icnEst',
			tooltip : 'Estornar o envio',
			handler : estornarItem,
			id : 'btExt'
		}), "-", {
			text : 'Alterar',
			iconCls : 'icnCom',
			tooltip : 'Alterar solicitação',
			handler : alteraSolicitacao,
			id : 'btAltSol'
		}, "-" ]
	});
	if ((tipoUsuario != "ASI") && (tipoUsuario != "OAL") && (tipoUsuario != "AAL")) {
		// Ext.getCmp('btExc').hide();
		Ext.getCmp('btAte').hide();
		Ext.getCmp('btExt').hide();
		Ext.getCmp('btImp').hide();
		Ext.getCmp('btPro').hide();
		tb.add( {
			text : 'Imprimir Espelho',
			iconCls : 'icnPrt',
			tooltip : 'Imprime espelho da solicitação',
			handler : imprimeEspelho,
			id : 'btImpEsp'
		});
	}
	return tb;
}
/**
 * Configura o item selecionado para SL- Liberado, para passar na frente da fila
 * de envio.
 * 
 * @return void
 */
function atenderItem() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel().getSelected();
	if (linhaSelecionada) {
		if (linhaSelecionada.get('sitItm') != 'NA') {
			if (linhaSelecionada.get('sitItm') == 'SP') {
				Ext.Msg.show( {
					title : 'Alerta',
					msg : 'Solicitação bloqueada. Aguarde liberação para atender.',
					buttons : Ext.MessageBox.OK,
					animEl : 'gridItens',
					icon : Ext.MessageBox.WARNING
				});
			} else {
				Ext.Msg.show( {
					title : 'Aviso',
					msg : 'Item já atendido.',
					buttons : Ext.MessageBox.OK,
					animEl : 'gridItens',
					icon : Ext.MessageBox.INFO
				});
			}
		} else {
			Ext.Ajax.request( {
				url : 'itemSolicitacao!atendeItem.action',
				params : {
					idSol : linhaSelecionada.get('id.solicitacao.id'),
					idUni : linhaSelecionada.get('id.uniforme.id')
				},
				success : function(result, request) {
					Ext.Msg.show( {
						title : 'Sucesso',
						msg : result.responseText,
						buttons : Ext.MessageBox.OK,
						fn : function() {
							Ext.getCmp('gridItens').getStore().reload( {
								url : 'itemSolicitacao!libXML.action?idSol=' + linhaSelecionada.get('id.uniforme.id')
							});
						},
						animEl : 'gridItens',
						icon : Ext.MessageBox.INFO
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
	} else {
		Ext.Msg.show( {
			title : 'Aviso',
			msg : 'Selecione um item',
			buttons : Ext.MessageBox.OK,
			animEl : 'gridItens',
			icon : Ext.MessageBox.INFO
		});
	}
}
function excluirItem() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel().getSelected();
	var situacao = linhaSelecionada.get('sitItm');
	if (situacao.localeCompare("EN") == 0) {
		Ext.Msg.show( {
			title : 'Alerta',
			msg : 'Item já enviado, não pode ser excluído.',
			buttons : Ext.MessageBox.OK,
			animEl : 'gridItens',
			icon : Ext.MessageBox.WARNING
		});
	} else {
		if (Ext.getCmp('gridItens').getSelectionModel().getSelected()) {
			Ext.Msg.show( {
				title : 'Confirmar exclusão',
				msg : 'Tem certeza de que deseja excluir o item ' + linhaSelecionada.get('desEpi') + '?',
				buttons : Ext.Msg.YESNO,
				icon : Ext.MessageBox.QUESTION,
				fn : function(button) {
					if (button == 'yes') {
						var myMask = new Ext.LoadMask(Ext.getBody(), {
							msg : 'Aguarde, excluindo item...'
						});
						myMask.show();
						if (linhaSelecionada.phantom == false) {//registro � real
					Ext.Ajax.request( {
						url : 'itemSolicitacao!excluiItem.action',
						params : {
							idSol : linhaSelecionada.get('id.solicitacao.id'),
							idUni : linhaSelecionada.get('id.uniforme.id')
						},
						success : function(result, request) {
							myMask.hide();
							Ext.Msg.show( {
								title : 'Sucesso',
								msg : result.responseText,
								buttons : Ext.MessageBox.OK,
								fn : function() {
									Ext.getCmp('gridItens').getStore().remove(linhaSelecionada);
									if (Ext.getCmp('gridItens').getStore().getTotalCount() == 1)
										if (Ext.getCmp('fIdSol') != undefined)
											Ext.getCmp('fIdSol').setValue("");
								},
								animEl : 'gridItens',
								icon : Ext.MessageBox.INFO
							});
						},
						failure : function(result, request) {
							myMask.hide();
							Ext.Msg.show( {
								title : 'Falha',
								msg : result.responseText,
								buttons : Ext.MessageBox.OK,
								animEl : 'gridItens',
								icon : Ext.MessageBox.ERROR
							});
						}
					});
				} else {
					Ext.getCmp('gridItens').getStore().remove(linhaSelecionada);
					myMask.hide();
				}
			}
		}
			});
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
}
function processaItem() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel().getSelected();
	var records = Ext.getCmp('gridItens').getSelectionModel().getSelections();
	/** não deixar processar solicita��o bloqueada * */
	if (linhaSelecionada.get('sitItm') != 'SP') {
		var formProcessa = new Ext.form.FormPanel( {
			url : 'itemSolicitacao!gravarProcessoAgrupado.action',
			baseCls : 'x-plain',
			labelWidth : 90,
			defaultType : 'textfield',
			items : [ {
				fieldLabel : 'Solicitação',
				name : 'solicitacao.id',
				value : linhaSelecionada.get('id.solicitacao.id')
			}, {
				fieldLabel : 'Data de envio',
				xtype : 'datefield',
				name : 'itemSolicitacao.datEnv',
				allowBlank : false
			} ],
			buttons : [ {
				text : 'Processar',
				handler : function() {
					formProcessa.form.submit( {
						waitMsg : 'Aguarde, processando itens',
						success : function(frm, act) {
							Ext.Msg.show( {
								title : 'Sucesso',
								msg : act.result.msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO
							});
							winProcessa.hide();
							formProcessa.getForm().reset();
							Ext.getCmp('gridItens').getStore().reload( {
								params : {
									start : 0,
									limit : 25
								}
							});
						},
						failure : function(form, action) {
							Ext.Msg.show( {
								title : 'Falha',
								msg : action.result.msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO
							});
							formProcessa.getForm().reset();
						}
					})
				}
			}, {
				text : 'Sair',
				handler : function() {
					winProcessa.hide();
				}
			} ]
		});
		for (i = 0; i < records.length; i++) {
			var nome = 'itens[' + i + '].id.uniforme.id';
			formProcessa.add( {
				xtype : 'checkbox',
				fieldLabel : '',
				labelSeparator : '',
				boxLabel : records[i].data.desEpi,
				name : nome,
				inputValue : records[i].id,
				checked : true,
				hidden : true
			});
		}
		var winProcessa = new Ext.Window( {
			title : 'Processamento de implantações',
			width : 400,
			height : 200,
			minWidth : 300,
			minHeight : 200,
			layout : 'card',
			plain : true,
			bodyStyle : 'padding:3px;',
			buttonAlign : 'center',
			closeAction : 'hide',
			modal : true,
			animCollapse : true,
			activeItem : 0,
			items : [ formProcessa ]
		});
		winProcessa.show();
	} else {
		Ext.Msg.show( {
			title : 'Alerta',
			msg : 'Solicitação bloqueada. Aguarde liberação para processar.',
			buttons : Ext.MessageBox.OK,
			animEl : 'gridItens',
			icon : Ext.MessageBox.WARNING
		});
	}
}
function imprimeEspelho() {
	var linhaSelecionada = Ext.getCmp('sGrid').getSelectionModel().getSelected();
	//geraMediaPdf('solicitacao!imprimirEspelho.action?idSol=' + linhaSelecionada.get('solicitacao.id'));
	window.location = 'solicitacao!imprimirEspelho.action?idSol=' + linhaSelecionada.get('solicitacao.id');
}
function alteraSolicitacao() {
	var linhaSelecionada = Ext.getCmp('sGrid').getSelectionModel().getSelected();
	novaSolicitacao('pesquisa!buscaXML.action?solicitacao.id=' + linhaSelecionada.get('solicitacao.id'));
}
function criaLeitorSol() {
	leitorX = new Ext.data.XmlReader( {
		record : 'solicitacao',
		id : 'solicitacao.id',
		totalProperty: 'totalCount'
	}, [ {
		name : 'solicitacao.colaborador.nomFun',
		mapping : 'colaborador > @nomFun'
	}, {
		name : 'solicitacao.motivo.id',
		mapping : '@motivo'
	}, {
		name : 'solicitacao.motivo.desMtv',
		mapping : '@desMtv'
	}, {
		name : 'solicitacao.colaborador.id',
		mapping : 'colaborador > @id'
	}, {
		name : 'solicitacao.colaborador.empresa.id',
		mapping : 'colaborador > @empresa'
	}, {
		name : 'solicitacao.solicitante.nomFun',
		mapping : 'solicitante > @nomFun'
	}, {
		name : 'solicitacao.solicitante.id',
		mapping : 'solicitante > @id'
	}, {
		name : 'solicitacao.id',
		mapping : 'solicitacaoId'
	}, {
		name : 'solicitacao.datEnt',
		mapping : 'datEnt'
	},{
		name : 'solicitacao.datInc',
		mapping : '@datInc'
	}, {
		name : 'solicitacao.situacao',
		mapping : 'codSitSol'
	}, {
		name : 'solicitacao.colaborador.centro.id',
		mapping : 'colaborador > @centro'
	}, {
		name : 'solicitacao.supervisor.nomFun',
		mapping : 'supervisor > @nomFun'
	}, {
		name : 'solicitacao.supervisor.id',
		mapping : 'supervisor > @id'
	}, {
		name : 'solicitacao.colaborador.cargo.titCar',
		mapping : 'colaborador > @cargo'
	}, {
		name : 'solicitacao.numSeq',
		mapping : '@numSeq'
	}, {
		name : 'solicitacao.viaWeb',
		mapping : '@viaWeb'
	} ]);
	return leitorX;
}
function geraLeitorItens() {
	leitorXML = new Ext.data.XmlReader( {
		record : 'item',
		id : 'id'
	}, [ {
		name : 'desEpi',
		mapping : 'uniforme > desEpi'
	}, {
		name : 'cplDes',
		mapping : 'uniforme > cplDes'
	}, 'qtdEnt', 'mtvSol', 'nroItm', {
		name : 'sitItm',
		mapping : 'codSit'
	}, {
		name : 'sitItm.descricao',
		mapping : 'sitItm'
	}, 'motLib', 'datEnt', 'datPro', 'datEnv', 'datLib', 'datVen', 'datUltEnt', 'usuLib', 'entAut', 'datAtu', 'datNeg', 'datAte', 'datExp', 'datDev', {
		name : 'obsDis',
		mapping : 'obs'
	}, {
		name : 'id.solicitacao.id',
		mapping : '@solicitacaoId'
	}, {
		name : 'id.uniforme.id',
		mapping : 'uniforme > id'
	}, {
		name : 'id.uniforme.tipoEPI.id',
		mapping : 'uniforme > tipo > id'
	}, {
		name : 'id.uniforme.tipoEPI.desSvc',
		mapping : 'uniforme > tipo > desSvc'
	}, {
		name : 'id.solicitacao.motivo.id',
		mapping : 'solicitacao > @motivo'
	}, {
		name : 'usuarioLiberacao.id',
		mapping : 'usuLib > @id'
	}, {
		name : 'cobCol',
		mapping : '@cobCol'
	}, {
		name : 'oriReg',
		mapping : '@oriReg'
	}, {
		name : 'datInc',
		mapping : '@datInc'
	}, {
		name : 'id.uniforme.qtdMax',
		mapping : 'uniforme > qtdMax'
	} ]);
	return leitorXML;
}
function criaGridSol(sm, pb, st) {
	var tb = new Ext.Toolbar( {
		id : 'tBarGridSol'
	});
	grid = new Ext.grid.GridPanel( {
		store : st,
		loadMask : true,
		renderTo : 'pnCenter',
		cm : new Ext.grid.ColumnModel( [ sm, {
			header : 'Cod.',
			dataIndex : 'solicitacao.id',
			sortable : true,
			width : 70
		}, {
			id : 'colaboradorId',
			header : "Colaborador",
			width : 100,
			dataIndex : 'solicitacao.colaborador.nomFun',
			sortable : true
		}, {
			header : "Situação",
			dataIndex : 'solicitacao.situacao',
			sortable : true,
			width : 20,
			hidden : true
		}, {
			header : 'Cargo',
			dataIndex : 'solicitacao.colaborador.cargo.titCar',
			sortable : false,
			width : 70
		}, {
			header : 'Empresa',
			dataIndex : 'solicitacao.colaborador.empresa.id',
			sortable : true,
			width : 70
		}, {
			header : 'Centro',
			width : 70,
			dataIndex : 'solicitacao.colaborador.centro.id',
			sortable : true
		}, {
			header : "Solicitante",
			width : 100,
			dataIndex : 'solicitacao.solicitante.nomFun',
			sortable : true
		}, {
			header : "Supervisor",
			width : 100,
			dataIndex : 'solicitacao.supervisor.nomFun',
			sortable : true
		}, {
			header : "Motivo",
			width : 40,
			dataIndex : 'solicitacao.motivo.desMtv',
			sortable : true
		}, {
			header : "Data",
			dataIndex : 'solicitacao.datEnt',
			sortable : true,
			width : 70
		} ]),
		sm : sm,
		tbar : tb,
		bbar : pb,
		id : 'sGrid',
		viewConfig : {
			forceFit : true,
			getRowClass : MudaCorSol
		},
		frame : true
	});
	return grid;
}
function MudaCorSol(record, index) {
	var change = record.get('solicitacao.situacao');
	if (change == 'FE')
		return 'corFechado';
	if (change == 'AB')
		return 'corAberto';
}
// Altera a cor da fonte da linha, dependendo do status do item
function MudaCor(record, rowIndex) {
	/** quando h� expander, o getRowClass � outro. Verificar: RowExpander.js * */
	var change = record.get('sitItm');
	if ((change == 'SL') || (change == 'PL') || (change == 'LB') || (change == 'SAA'))
		return 'corLiberado';
	if ((change == 'PP') || (change == 'SP') || (change == 'NE'))
		return 'corBloqueado';
	if (change == 'NA')
		return 'corNaoAtendido';
	if (change == 'EN') 
		return 'corFechado';
}
function criaGridItens(storeItens, sm, tb) {
	var itemTplMarkup = [ '<h2><b>Item:</b> {desEpi} - {cplDes}<br/><b>Data da solicitação:</b> {datEnt} - <b>Data de inclusão do item:</b> {datInc}<br/><b>Data de envio:</b> {datEnv}<br/><b>Data da liberação:</b> {datLib}<br/><b>Data de emissão DR(processamento):</b> {datPro}<br/><b>Situação:</b><br/><b>Motivo da antecipação:</b> {mtvSol}<br/><b>Usuário que liberou:</b>{usuLib} <br/> <b>Obs:</b> {obsDis} </h2>' ];
	var itemTpl = new Ext.Template(itemTplMarkup);
	var expander = new Ext.ux.grid.RowExpander( {
		tpl : itemTpl
	});
	var cm = new Ext.grid.ColumnModel( {
		columns : [ expander, sm, {
			header : 'Cód.',
			sortable : true,
			dataIndex : 'id.uniforme.id'
		}, {
			header : 'Cód.Sol.',
			sortable : true,
			dataIndex : 'id.solicitacao.id'
		}, {
			id : 'nroItm',
			header : "Uniforme",
			dataIndex : 'desEpi',
			sortable : true
		}, {
			header : "Qtd",
			dataIndex : 'qtdEnt',
			sortable : true,
			xtype : 'numbercolumn',
			align : 'right',
			format : '0'
		}, {
			header : "Motivo da antecipação",
			dataIndex : 'mtvSol',
			sortable : true
		}, {
			header : "Codigo",
			dataIndex : 'sitItm',
			hidden : true
		}, {
			header : "Situação",
			dataIndex : 'sitItm.descricao',
			sortable : true
		}, {
			header : "Última Entrega",
			dataIndex : 'datUltEnt',
			sortable : true
		}, {
			header : "Vencimento",
			dataIndex : 'datVen',
			sortable : true
		}, {
			header : "Motivo liberação / negação",
			dataIndex : 'motLib',
			sortable : false
		}, {
			header : 'Cobrar?',
			dataIndex : 'cobCol'
		} ]
	});
	var grid = new Ext.grid.GridPanel( {
		store : storeItens,
		loadMask : true,
		id : 'gridItens',
		cm : cm,
		sm : sm,
		tbar : tb,
		viewConfig : {
			forceFit : true,
			getRowClass : MudaCor
		},
		width : 600,
		height : 250,
		plugins : expander
	});
	return grid;
}
/**
 * Cria janela para mostrar os itens da solicita��o
 * 
 * @param gridItens
 * @param data
 * @return
 */
function criaJanelaItens(gridItens, record) {
	var window = new Ext.Window( {
		id : 'winDetail',
		title : 'Itens da solicitação ' + record.get('solicitacao.id') + ' de ' + record.get('solicitacao.colaborador.nomFun') + ' (' + record.get('solicitacao.colaborador.cargo.id.titCar') + ')',
		width : 600,
		height : 250,
		autoHeight : true,
		layout : 'fit',
		border : false,
		modal : true,
		items : [ gridItens ],
		renderTo : 'pnCenter',
		buttons : [ {
			text : 'Fechar',
			handler : function() {
				window.close();
			}
		} ]
	});
	return window;
}
