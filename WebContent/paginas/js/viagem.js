var storeSol = criaStoreReader('colaborador','colaborador!listAllXML.action?notFindDemitidos=true', [
				'colaboradorId', {
					name : 'colaborador.nomFun',
					mapping : '@nomFun'
				}, {
					name : 'col__nomFun',
					mapping : '@nomFun'
				}, 'numCad', {
					name : 'empresaId',
					mapping : 'empresa > id'
				}, {
					name : 'nomEmp',
					mapping : 'empresa > name'
				}], 'colaboradorId', 'storeSol');
/**var stCentros = new Ext.data.Store({
	remoteSort : true,
	totalProperty : 'totalCount',
	url : '',
	storeId : 'stCentros',
	reader : new Ext.data.XmlReader({
		record : 'centro',
		id : 'id',
		totalProperty : 'totalCount'
	}, [{
		name : 'id',
		mapping : '@id'
	}, {
		name : 'nomCcu',
		mapping : '@nomCcu'
	}])
});**/

function geraTelaCadastroViagem(url) {
	campos = ['colaboradorId', {name : 'colaborador.nomFun',mapping : '@nomFun'}, 
							   {name : 'col__nomFun',   	mapping : '@nomFun'}, 
				     'numCad', {name : 'empresaId',mapping : 'empresa > id'    },
				               {name : 'nomEmp',mapping : 'empresa > name'}];
	
	var storeCol = criaStoreReader('colaborador','colaborador!listAllXML.action', campos, 'colaboradorId','storeCol');
	storeCol.setBaseParam('notFindDemitidos', true);/*nao listar os demitidos*/
	
	var colaboradorTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{col__nomFun}</span></h3><h2>Código: {numCad} Empresa: {empresaId} - {nomEmp}</h2></div></tpl>');
	var storeMotivo = criaStoreReader('motivo','motivoViagem!listAllXML.action', ['id', 'desMtv'], 'id');
	var storeEmpresa = criaStoreReader('empresa', 'empresa!listAllXML.action',['id', 'name', 'codLoc'], 'id');
	
	
	var stAdt = new Ext.data.Store({remoteSort : true,
		totalProperty : 'totalCount',	
		proxy : new Ext.data.HttpProxy({url : 'viagem!gravar.action',method : 'POST'}),
		reader : criaLeitorAdiantamento()});
	stAdt.setDefaultSort('id', 'ASC');

	var stDes = new Ext.data.Store({
		remoteSort : true,
		totalProperty : 'totalCount',
		proxy : new Ext.data.HttpProxy({url : 'viagem!gravar.action',method : 'POST'}),
		reader : criaLeitorDespesaViagem()});
	
	var smAdt = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
	var cmAdt = new Ext.grid.ColumnModel({
			columns : [new Ext.grid.RowNumberer(), smAdt, 
				     { header : 'Viagem',
					  dataIndex : 'id.viagem.id',
					  hidden : true
					  }, {header : 'Id',
						  dataIndex : 'id.id',
						  hidden : true
					  }, {
						  header : 'Valor',
						  dataIndex : 'vlrAdt',
						  width : 70,
						  xtype : 'numbercolumn',
						  align : 'right',
						  fixed : true,
						  editor : {xtype : 'numberfield',allowBlank : false, allowNegative : false, decimalSeparator : ',', id : 'fVlrAdt', name : 'vlrAdt', minValue : '1'}
					  }, {
						  header : 'Data',
						  dataIndex : 'datAdt',
						  xtype : 'datecolumn',
						  format : 'd/m/Y',
						  fixed : true,
						  editor : {xtype : 'datefield',name : 'datAdt',width : 100,id : 'fDatAdt',format : 'd/m/Y',allowBlank : false}}]
	});
	
	var gridAdiantamento = new Ext.grid.EditorGridPanel({
				store : stAdt,
				cm : cmAdt,
				sm : smAdt,
				frame : true,
				layout : 'auto',
				height : 230,
				title : 'Adiantamentos desta viagem',
				id : 'gridAdts',
				clicksToEdit : 1,
				viewConfig : {forceFit : true,getRowClass : MudaCorViagem},
				tbar : [{text : 'Adicionar adiantamento',
						 iconCls : 'icnAdd',
						 handler : function() {
								  if (stAdt.getCount() < 3) {
									  var novoItem = new Ext.data.Record({'vlrAdt' : '0,00','datAdt' : ''});
									  stAdt.insert(stAdt.getCount(), novoItem);
									  gridAdiantamento.getView().refresh();
									  gridAdiantamento.getSelectionModel().selectRow(0);
									  gridAdiantamento.startEditing(stAdt.getCount() + 1, 0);
								} else {
									Ext.Msg.show({title : 'Alerta',	msg : 'O máximo de adiantamentos que você pode solicitar é 3',buttons : Ext.MessageBox.OK, animEl : 'gridAdts',icon : Ext.MessageBox.ERROR});
								}
							}
						}, "-", {
							text : 'Imprimir adiantamento',
							iconCls : 'icnPrt',
							handler : function() {imprimeAdt();}
						}, "-", {
							text : 'Excluir',
							iconCls : 'icnExc',
							handler : function() {excluiAdt();}
						}] });
	
	
	
		gridAdiantamento.getSelectionModel().on('rowselect',function(sm, rowIndex, record) {
				/* TODO filtrar o store de despesas com as despesas do adiantamento selecionado */
				Ext.getCmp('gridDes').getStore().filter('adiantamentoViagem.id.id', record.get('id.id'));
			});

	var smDes = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
	var storeDespesa = criaStoreReader('despesa', 'despesa!listAllXML.action',['id', 'desDes'], 'id', 'storeDespesa');
	storeDespesa.load();
	var comboDespesa = new Ext.form.ComboBox({hiddenName : 'id.despesa.id',store : storeDespesa, valueField : 'id', displayField : 'desDes', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione uma despesa...', selectOnFocus : true, width : 350, id : 'fDes', allowBlank : false});
	
	var cmDes = new Ext.grid.ColumnModel({
		columns : [new Ext.grid.RowNumberer(), smDes,
			{header : 'Viagem',
			dataIndex : 'id.viagem.id',
			hidden : true
		}, {
			header : 'Despesa',
			dataIndex : 'id.despesa.id',
			width : 130,
			editor : comboDespesa,
			renderer : Ext.util.Format.comboRenderer(comboDespesa, stDes),
			fixed : true
		}, {
			header : 'Valor',
			dataIndex : 'vlrDes',
			width : 70,
			xtype : 'numbercolumn',
			align : 'right',
			fixed : true,
			editor : {xtype : 'numberfield',allowBlank : false,allowNegative : false,decimalSeparator : ',',id : 'fVlrDes',	name : 'vlrDes',minValue : '1'}
		}, {
			header : 'Adiantamento',
			dataIndex : 'adiantamentoViagem.id.id',
			hidden : true
		}, {
			header : 'Viagem',
			dataIndex : 'adiantamentoViagem.id.viagem.id',
			hidden : true
		}]
	});
	var gridDespesas = new Ext.grid.EditorGridPanel({
		store : stDes,
		cm : cmDes,
		sm : smDes,
		stripeRows : true,
		height : 230,
		autoWidth : true,
		frame : true,
		title : 'Despesas desta viagem',
		id : 'gridDes',
		clicksToEdit : 1,
		viewConfig : {forceFit : true, getRowClass : MudaCorViagem},
		tbar : [{
			text : 'Adicionar despesa',
			iconCls: 'icnAdd',
			handler : adicionarDespesa
		}, "-", {
			text : 'Imprimir acerto',
			iconCls : 'icnPrt',
			handler : function() {imprimeAcerto();}
		}, "-", {
			text : 'Excluir',
			iconCls : 'icnExc',
			handler : function() {excluiDes();}
		}]
	});
	
	var formViagem = new Ext.form.FormPanel({
		labelWidth : 72,
		url : 'viagem!gravar.action',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		maximizable : true,
		reader : criaLeitorViagem(),
		id : 'form',
		items : [{
			name : 'viagem.id',
			xtype : 'hidden',
			id : 'fIdViagem'
		}, {
			name : 'viagem.situacao',
			xtype : 'hidden',
			id : 'fSitVia',
			disabled : true
		}, {
			name : 'viagem.datInc',
			xtype : 'hidden'
		}, {
			name : 'viagem.datSol',
			xtype : 'hidden'
		}, {
			layout : 'column',
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'combo',
					store : storeCol,
					id : 'fCol',
					hiddenName : 'viagem.colaborador.id',
					fieldLabel : 'Colaborador',
					displayField : 'colaborador.nomFun',
					valueField : 'colaboradorId',
					typeAhead : true,
					mode : 'remote',
					itemSelector : 'div.search-item',
					emptyText : 'Busca automática a partir de 4 caracteres...',
					loadingText : 'Procurando...',
					pageSize : 10,
					width : 300,
					tpl : colaboradorTpl,
					triggerClass : 'x-form-search-trigger',
					triggerAction : 'all',
					hideTrigger : true,
					minChars : 4,
					queryParam : 'colaborador.nomFun',
					allowBlank : false,
					forceSelection : true,
					listeners : {beforequery : function(qe) {
									delete qe.combo.lastQuery;
									}
								}
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'combo',
					store : storeSol,
					id : 'fSol',
					hiddenName : 'viagem.solicitante.id',
					fieldLabel : 'Solicitante',
					displayField : 'colaborador.nomFun',
					valueField : 'colaboradorId',
					typeAhead : true,
					mode : 'remote',
					itemSelector : 'div.search-item',
					emptyText : 'Busca automática a partir de 4 caracteres...',
					loadingText : 'Procurando...',
					pageSize : 10,
					width : 300,
					tpl : colaboradorTpl,
					triggerClass : 'x-form-search-trigger',
					triggerAction : 'all',
					hideTrigger : true,
					minChars : 4,
					queryParam : 'colaborador.nomFun',
					allowBlank : false,
					forceSelection : true
				}]
			}]
		}, {
			layout : 'column',
			items : [{
				columnWidth : .33,
				layout : 'form',
				items : [{
					xtype : 'combo',
					fieldLabel : 'Empresa',
					hiddenName : 'viagem.empresa.id',
					store : storeEmpresa,
					valueField : 'id',
					displayField : 'name',
					typeAhead : true,
					mode : 'remote',
					triggerAction : 'all',
					emptyText : 'Selecione uma empresa...',
					selectOnFocus : true,
					forceSelection : true,
					width : 160,
					id : 'fEmp',
					allowBlank : false
				}]
			}, {
				columnWidth : .33,
				layout : 'form',
				items : [{
					xtype : 'combo',
					fieldLabel : 'Motivo',
					hiddenName : 'viagem.motivo.id',
					store : storeMotivo,
					valueField : 'id',
					displayField : 'desMtv',
					typeAhead : true,
					mode : 'remote',
					triggerAction : 'all',
					emptyText : 'Selecione um motivo...',
					selectOnFocus : true,
					id : 'fMot',
					anchor : '80%',
					allowBlank : false,
					forceSelection : true
				}]
			}]
		}, {
			layout : 'column',
			items : [{columnWidth : 1,layout : 'form',
				items : [{xtype : 'textarea', name : 'viagem.cplMtv', fieldLabel : 'Complemento', width : 350}]
			}]
		    }, {
			    layout : 'column',
			    items : [{columnWidth : 0.45,layout : 'form',items : [{xtype : 'textfield',name : 'viagem.itnVgm',fieldLabel : 'Itinerário', allowBlank : false,width : 250,height : 25}]
			}, {
				columnWidth : 0.27,
				layout : 'form',
				items : [new Ext.form.DateField({fieldLabel : 'Data saída',name : 'viagem.datSai', width : 100,id : 'fDatSai',allowBlank : false})]
			}, {
				columnWidth : 0.27,
				layout : 'form',
				items : [new Ext.form.DateField({fieldLabel : 'Data chegada',name : 'viagem.datChe', width : 100, id : 'fDatche'})]
			}]
		},{
			layout : 'column',
			items : [{columnWidth : .5, layout : 'form', baseCls : 'x-plain', bodyStyle : 'padding:5px 0 5px 5px', items : [gridAdiantamento]}, 
				     {columnWidth : .5, layout : 'form', baseCls : 'x-plain', bodyStyle : 'padding:5px 0 5px 5px', items : [gridDespesas]}]
		}]
	});

	
	Ext.getCmp('fCol').on('blur',function(e) {
							    	var codigo = Ext.getCmp('fCol').getValue();
							    	if (codigo != "") {
							    		/** verificar se tem viagem pendente de acerto, * */
							    		var myMask = new Ext.LoadMask(Ext.getBody(), {msg : 'Aguarde, verificando viagens...'});
							    		myMask.show();
							    		Ext.Ajax.request({url : 'viagem!findByDateCol.action',params : {'viagem.colaborador.id' : Ext.getCmp('fCol').getValue()},
   										    success : function(result, request) {
   										    myMask.hide();
											/* listar as viagens pendentes do colaborador e pergunta se deseja utilizar alguma delas */
											if (result.responseXML.getElementsByTagName('viagem').length > 0) {
												/* carregar os dados da viagem na tela, para alteração */
												Ext.Msg.show({title : 'Viagens encontradas',msg : 'Foram encontradas viagens abertas para este colaborador. Deseja visualizá-las?.',
															buttons : Ext.MessageBox.YESNO,	icon : Ext.MessageBox.QUESTION,
															fn : function(button) {
																if (button == 'yes') {
																	geraTelaViagensColaborador(result.responseXML);
																}
															}
														});
											} else {
												novaViagem();
												Ext.getCmp('fCol').setValue(codigo);
											}
										},
										failure : function(result, request) {
											myMask.hide();
											Ext.Msg.show({title : 'Falha',msg : result.responseText,buttons : Ext.MessageBox.OK,icon : Ext.MessageBox.ERROR});
										}
									});
						}
					});
	
	var win = new Ext.Window({title : 'Viagem', width : 830, height : 650,layout : 'fit', layoutConfig : {padding : '5', align : 'left'	},
				border : false,	id : 'wVia', renderTo : 'pnCenter',
				items : [formViagem],
				tbar : [new Ext.Toolbar({id : 'tBar',
							items : [{text : 'Nova viagem', iconCls : 'icnFic', tooltip : 'Incluir nova viagem', handler : novaViagem},
									"-",
									{text : 'Excluir',iconCls : 'icnExc',tooltip : 'Exclui esta viagem, seus adiantamentos e despesas',handler : excluiViagem},
									"-",
									new Ext.form.Checkbox({fieldLabel : '', boxLabel : 'Incluir demitidos na busca de colaboradores', name : 'notFindDemitidos', id : 'notFindDemitidos', width : 270,
												listeners : {check : function(chk, marcado) {
															 		 		if (marcado == true) {
															 		 			Ext.getCmp('fCol').store.setBaseParam('notFindDemitidos',false);
															 		 		} else {
															 		 			Ext.getCmp('fCol').store.setBaseParam('notFindDemitidos',true);
														}
													}
												}
											})]
						})],
				resizable : false,
				maximizable : true,
				buttons : [{text : 'Gravar',
							iconCls : 'icnGra',
							handler : function() {
									  if ((gridAdiantamento.getStore().getCount() > 0) || (gridDespesas.getStore().getCount() > 0)) {
										  	if ((validateEditorGridPanel('gridAdts')) && (validateEditorGridPanel('gridDes'))) {
										var params = {}; var i = 0;
										stAdt.each(function(record) {
												   var fields = stAdt.fields.items;
												   var colunasGrid = gridAdiantamento.getColumnModel().columns;
													for (var j = 0; j < fields.length; j++) {
														if (record.get(fields[j].name) != undefined) {
															if ((fields[j].name == 'datAdt') || (fields[j].name == 'datAce')) {
																// formatar a data
																if ((record.get(fields[j].name) != undefined) && (record.get(fields[j].name) != '')) {
																	params['adiantamentos['+ i+ '].'+ fields[j].name] = record.get(fields[j].name).dateFormat('d/m/Y');
																}
															} else
																params['adiantamentos['+ i+ '].'+ fields[j].name] = record.get(fields[j].name);
														}
													}
													i++;
												});
										i = 0;
										stDes.each(function(record) {
													var fields = stDes.fields.items;
													var colunasGrid = gridDespesas.getColumnModel().columns;
													for (var j = 0; j < fields.length; j++) {
														 campo = fields[j].name;
													    	if (record.get(campo) != undefined) {
													    		if (((campo == 'id.viagem.id') && (record.get(campo).length > 0)) || ((campo != 'id.viagem.id') && (campo != 'adiantamentoViagem.id.viagem.id')) || ((campo == 'adiantamentoViagem.id.viagem.id') && (record.get(campo).length > 0))) {
																if (campo == 'datAce') {
																	if ((record.get(campo) != undefined) && (record.get(campo) != ''))
																		params['despesas['+ i+ '].'+ fields[j].name] = (record.get(fields[j].name)).dateFormat('d/m/Y');
																} else {
																	params['despesas['+ i+ '].'	+ campo] = record.get(campo);
																}
															}
														}
													}
													i++;
												});
										
										formViagem.getForm().submit({url : 'viagem!gravar.action', params : params,
															success : function(form,action) {
																Ext.Msg.show({title : 'Sucesso',msg : action.result.msg, buttons : Ext.MessageBox.OK, icon : Ext.MessageBox.INFO,
																			fn : function(button) {
																				// retorna id da viagem
																				novoId = parseInt(action.result.viagemId);
																				if (isNaN(novoId) == false)
																				  	 if (novoId > 0) {
																					 	 carregaFormViagem('viagem!listByExample.action?viagem.id='+ novoId);
																						Ext.getCmp('fIdViagem').setValue(novoId);
																					}
																				Ext.getCmp('gridAdts').store.commitChanges();
																				Ext.getCmp('gridDes').store.commitChanges();
																			}
																		});
															},
															failure : function(form,action) {
																Ext.Msg.show({title : 'Falha',msg : action.result.msg, buttons : Ext.MessageBox.OK, icon : Ext.MessageBox.INFO});
															}
														});
									}
								} else {
									Ext.Msg.show({title : 'Aviso',msg : 'Você não inseriu nenhum adiantamento e/ou despesa. Nada será gravado.',buttons : Ext.Msg.OK,icon : Ext.MessageBox.INFO});
								}
							}
						}, {
							text : 'Cancelar',
							iconCls : 'icnCan',
							handler : function() {
								Ext.Msg.show({title : 'Sair?',msg : 'Tem certeza de que deseja sair?',buttons : Ext.Msg.YESNO,icon : Ext.MessageBox.QUESTION,
									fn : function(button) {
										if (button == 'yes')
											win.destroy();
									}
								});
							}
						}]
			});
	win.on('restore', function(e) {this.center(); this.doLayout();});
	win.on('maximize', function(e) {this.center(); this.doLayout();});
	/** Se a url estiver configurada, carrega o form com os dados * */
	win.on('show', function(e) {if (url) carregaFormViagem(url);});
	win.show();
	win.center();
}
function carregaFormViagem(url) {
	Ext.getCmp('fMot').store.reload();
	Ext.getCmp('fEmp').store.reload();
	var formViagem = Ext.getCmp('form');
	var storeCol = Ext.getCmp('fCol').store;
	var storeSol = Ext.getCmp('fSol').store;
	var stAdt = Ext.getCmp('gridAdts').store;
	var stDes = Ext.getCmp('gridDes').store;
	if (url) {
		formViagem.getForm().load({	url : url,
							waitMsg : 'Aguarde, carregando dados',
							success : function(form, action) {
								if (action.response.responseXML.childNodes.length > 0) {
									if (Ext.getCmp('fCol').getValue() != "") {
										storeCol.proxy.conn.url = 'colaborador!listAllXML.action?colaborador.id='+ Ext.getCmp('fCol').getValue();
										storeCol.addListener('load',function() {
															var codigo = storeCol.getById(Ext.getCmp('fCol').getValue()).get('colaboradorId');
															Ext.getCmp('fCol').setValue(codigo);
										});
										storeCol.reload({callback : function(r, options, success) {
											            storeCol.purgeListeners();
														storeCol.proxy.conn.url = 'colaborador!listAllXML.action';
													}
												});
									}
									if (Ext.getCmp('fSol').getValue() != "") {
										storeSol.proxy.conn.url = 'colaborador!listAllXML.action?colaborador.id='+ Ext.getCmp('fSol').getValue();
										storeSol.addListener('load',function() {
																		 var codigo = storeSol.getById(
																			Ext.getCmp('fSol').getValue()).get('colaboradorId');
															Ext.getCmp('fSol').setValue(codigo);
									});
										storeSol.reload({ allback : function(r,	options, success) {
														storeSol.purgeListeners();
														storeSol.proxy.conn.url = 'colaborador!listAllXML.action';
													}
										});
									}
									stAdt.loadData(action.response.responseXML);
									stDes.loadData(action.response.responseXML);
									/**stCentros
											.loadData(action.response.responseXML);
									populaCentros(stCentros);**/
								}
							}
						});
	}
}
function imprimeAdt() {
	var linhaSelecionada = Ext.getCmp('gridAdts').getSelectionModel()
			.getSelected();
	if (linhaSelecionada) {
		window.location = 'adiantamento!imprimirAdiantamento.action?viagemId='
				+ linhaSelecionada.get('id.viagem.id') + '&adiantamentoId='
				+ linhaSelecionada.get('id.id')
	} else {
		Ext.Msg.show({
			title : 'Alerta',
			msg : 'Selecione um item',
			buttons : Ext.MessageBox.OK,
			animEl : 'gridAdts',
			icon : Ext.MessageBox.ERROR
		});
	}
}
function excluiAdt() {
	var linhaSelecionada = Ext.getCmp('gridAdts').getSelectionModel().getSelected();
	if (linhaSelecionada) {
		Ext.Msg.show({title : 'Confirmar exclusão', msg : 'Tem certeza de que deseja excluir o adiantamento de '+ linhaSelecionada.get('datAdt') + '?', buttons : Ext.Msg.YESNO, icon : Ext.MessageBox.QUESTION,
					fn : function(button) {
						if (button == 'yes') {
							var myMask = new Ext.LoadMask(Ext.getBody(), {msg : 'Aguarde, excluindo adiantamento...'});
							myMask.show();
							Ext.Ajax.request({url : 'adiantamento!excluir.action',  params : {'adiantamentoViagem.id.viagem.id' : linhaSelecionada.get('id.viagem.id'),'adiantamentoViagem.id.id' : linhaSelecionada.get('id.id')},
										      success : function(result, request) {
										    	  	    myMask.hide();
										    	  	    Ext.Msg.show({title : 'Sucesso',msg : result.responseText,buttons : Ext.MessageBox.OK,
														fn : function() {Ext.getCmp('gridAdts').getStore().remove(linhaSelecionada);},animEl : 'gridAdts', icon : Ext.MessageBox.INFO});},
											  failure : function(result, request) {
												   myMask.hide();
												   Ext.Msg.show({title : 'Falha', msg : result.responseText, buttons : Ext.MessageBox.OK, animEl : 'gridAdts', icon : Ext.MessageBox.ERROR
											});
										}
									});
						}
					}
				});
	} else {
		Ext.Msg.show({title : 'Alerta', msg : 'Selecione um adiantamento', buttons : Ext.MessageBox.OK, animEl : 'gridAdts', icon : Ext.MessageBox.ERROR});
	}
}

function excluiDes() {
	var linhaSelecionada = Ext.getCmp('gridDes').getSelectionModel().getSelected();
	if (linhaSelecionada) {
		Ext.Msg.show({title : 'Confirmar exclusão', msg : 'Tem certeza de que deseja excluir a despesa '+ linhaSelecionada.get('desDes') + '?',
			buttons : Ext.Msg.YESNO, icon : Ext.MessageBox.QUESTION, fn : function(button) {
					if (button == 'yes') {
							var myMask = new Ext.LoadMask(Ext.getBody(), {msg : 'Aguarde, excluindo despesa...'});
					myMask.show();
					Ext.Ajax.request({url : 'despesaViagem!excluir.action',
						params : {'despesaViagem.id.viagem.id' : linhaSelecionada.get('id.viagem.id'),
							'despesaViagem.id.centro.id' : linhaSelecionada.get('id.centro.id'),
							'despesaViagem.id.despesa.id' : linhaSelecionada.get('id.despesa.id')
						},
						success : function(result, request) {
								myMask.hide();
								Ext.Msg.show({title : 'Sucesso', msg : result.responseText, buttons : Ext.MessageBox.OK,
								fn : function() {Ext.getCmp('gridDes').getStore().remove(linhaSelecionada);},
								animEl : 'gridDes',
								icon : Ext.MessageBox.INFO
							});
						},
						failure : function(result, request) {
							myMask.hide();
							Ext.Msg.show({title : 'Falha', msg : result.responseText, buttons : Ext.MessageBox.OK, animEl : 'gridDes', icon : Ext.MessageBox.ERROR});
						}
					});
				}
			}
		});
	} else {
		Ext.Msg.show({title : 'Alerta', msg : 'Selecione uma despesa', buttons : Ext.MessageBox.OK, animEl : 'gridDes', icon : Ext.MessageBox.ERROR});
	}
}
function imprimeAcerto() {
	var idViagem = 0;
	var imprime = true;
	if (Ext.getCmp('fIdViagem') != undefined)
		idViagem = Ext.getCmp('fIdViagem').getValue();
	else {
		idViagem = Ext.getCmp('sGridVia').getSelectionModel().getSelected()
				.get('viagem.id');
		if (Ext.getCmp('sGridVia').getSelectionModel().getSelected().get(
				'viagem.situacao') == "AB")
			imprime = false;
	}
	if (imprime) {
		var mensagemErro;
		var gridAdts = Ext.getCmp("gridAdts");
		/** verificar se tem adiantamentos lan�ados */
		if (getCountAdiantamento() > 0) {
			/** verificar se o adiantamento est� selecionado * */
			var adtSelecionado = gridAdts.getSelectionModel().getSelected();
			if (adtSelecionado) {
				/** verifica se a viagem est� gravada * */
				if (Ext.getCmp('fIdViagem')) {
					mensagemErro = 'É preciso gravar a viagem antes de imprimir o acerto.';
				} else {
					var linhaSelecionada = Ext.getCmp('sGridVia')
							.getSelectionModel().getSelected();
					if (linhaSelecionada) {
						idViagem = linhaSelecionada.get('viagem.id');
					}
					mensagemErro = "Selecione uma viagem.";
				}
				if (idViagem > 0) {
					var urlAcerto = 'viagem!imprimirAcerto.action?viagem.id='
							+ idViagem + "&adiantamentoViagem.id.id="
							+ adtSelecionado.get('id.id');
					window.location = urlAcerto;
					if (Ext.getCmp('fIdViagem') != undefined)
						carregaFormViagem(
								'viagem!listByExample.action?viagem.id='
										+ idViagem);
				} else {
					Ext.Msg.show({
						title : 'Alerta',
						msg : mensagemErro,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			} else {
				Ext.Msg
						.show({
							title : 'Aviso',
							msg : 'Escolha o adiantamento para o qual deseja imprimir o acerto',
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
			}
		} else {
			window.location = 'viagem!imprimirAcerto.action?viagem.id='
					+ idViagem;
			if (Ext.getCmp('fIdViagem') != undefined)
				carregaFormViagem('viagem!listByExample.action?viagem.id='
						+ idViagem);
		}
	} else {
		Ext.Msg
				.show({
					title : 'Aviso',
					msg : 'Esta viagem ainda está aberta. O acerto só poderá ser impresso clicando em "Alterar Viagem" e depois em "Imprimir Acerto".',
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
	}
}
function criaCheckCentros(stCentros) {
	var storeCentro = criaStoreReader('centro', 'centro!listByExample.action',
			['id', 'nomCcu'], 'id', 'storeCentro');
	var group = Ext.getCmp('cbgCentros');
	storeCentro.load({
		callback : function(r, o, s) {
			var i = 0;
			storeCentro.each(function(record) {
				var col = group.panel.items.get(group.items.getCount()
						% group.panel.items.getCount());
				var item = new Ext.form.Checkbox({
					boxLabel : record.get('nomCcu'),
					inputValue : record.get('id'),
					id : 'centros[' + record.get('id') + ']',
					name : 'centros[' + i + '].id',
					itemCls : 'x-form-itemCentro',
					width : 100
				});
				if (stCentros) {
					if (stCentros.getById(record.get('id')) != undefined) {
						item.checked = true;
					}
				}
				i++;
				group.items.add(item);
				col.add(item);
				Ext.getCmp('cbgCentros').panel.doLayout();
			});
		}
	});
}
function populaCentros(stCentros) {
	stCentros.each(function(record) {
		var item = Ext.getCmp('centros[' + record.get('id') + ']');
		item.setValue(true);
	});
}
function excluiViagem() {
	var situacao;
	var viagemId;
	if (Ext.getCmp('fSitVia')) {
		situacao = Ext.getCmp('fSitVia');
		viagemId = Ext.getCmp('fIdViagem').getValue();
	} else {
		var linhaSelecionada = Ext.getCmp('sGridVia').getSelectionModel()
				.getSelected();
		situacao = linhaSelecionada.get('viagem.situacao');
		viagemId = linhaSelecionada.get('viagem.id');
	}
	if ((situacao == 'AB') || (tipoUsuario == "ASI")) {
		Ext.Msg.show({
			title : 'Confirmar exclusão',
			msg : 'Tem certeza de que deseja excluir esta viagem?',
			buttons : Ext.Msg.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : function(button) {
				if (button == 'yes') {
					var myMask = new Ext.LoadMask(Ext.getBody(), {
						msg : 'Aguarde, excluindo viagem...'
					});
					myMask.show();
					Ext.Ajax.request({
						url : 'viagem!excluir.action',
						params : {
							'viagem.id' : viagemId
						},
						success : function(result, request) {
							myMask.hide();
							Ext.Msg.show({
								title : 'Sucesso',
								msg : result.responseText,
								buttons : Ext.MessageBox.OK,
								fn : function() {
									if (Ext.getCmp('sGridVia')) {
										Ext.getCmp('sGridVia').store.reload();
									} else {
										novaViagem()
									}
								},
								icon : Ext.MessageBox.INFO
							});
						},
						failure : function(result, request) {
							myMask.hide();
							Ext.Msg.show({
								title : 'Falha',
								msg : result.responseText,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
						}
					});
				}
			}
		});
	} else {
		Ext.Msg.show({
			title : 'Aviso',
			msg : 'Viagem fechada. Não pode ser excluída.',
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
	}
}
function novaViagem() {
	Ext.getCmp('form').form.reset();
	Ext.getCmp('gridDes').store.removeAll();
	Ext.getCmp('gridAdts').store.removeAll();
	// Ext.getCmp('wVia').doLayout();
}
function adicionarDespesa() {
	var storeAdt = Ext.getCmp("gridAdts").getStore();
	var stDes = Ext.getCmp('gridDes').getStore();
	var gridDespesas = Ext.getCmp('gridDes');
	var gridAdts = Ext.getCmp("gridAdts");
	/** verificar se tem adiantamento lan�ado */
	/** se tem adiantamento lan�ado, verifica se est� selecionado * */
	if (getCountAdiantamento() > 0) {
		var adtSelecionado = gridAdts.getSelectionModel().getSelected();
		if (adtSelecionado) {
			/**
			 * estando selecionado o adiantamento/s, proceder com a inser��o da
			 * despesa *
			 */
			/**
			 * confirmar se � para fazer o acerto do adiantamento selecionado *
			 */
			/** se o adiantamento n�o for real, n�o permite lan�ar a despesa * */
			if (adtSelecionado.get('id.id') == undefined) {
				Ext.Msg.show({
							title : 'Aviso',
							msg : 'Antes de fazer o acerto deste adiantamento, grave-o.',
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
			} else {
				Ext.Msg.show({title : 'Confirmar adiantamento',	msg : 'Adiantamento selecionado: '+ adtSelecionado.get('vlrAdt')+ " de "+ adtSelecionado.get('datAdt').format('d/m/Y')+ '. Confirma acerto desse adiantamento?',buttons : Ext.Msg.YESNO,icon : Ext.MessageBox.QUESTION,
					fn : function(button) {				}
								if (button == 'yes') {
									/** continuar com o lan�amento da despesa * */
									/**
									 * TODO identificar a despesa como sendo do
									 * adiantamento selecionado *
									 */
									var novoItem = new Ext.data.Record({'id.centro.id' : '','vlrDes' : '0,00','id.viagem.id' : Ext.getCmp('fIdViagem').getValue(),'adiantamentoViagem.id.id' : adtSelecionado.get('id.id'),'adiantamentoViagem.id.viagem.id' : adtSelecionado.get('id.viagem.id')});
									stDes.insert(stDes.getCount(), novoItem);
									gridDespesas.getView().refresh();
									gridDespesas.getSelectionModel().selectRow(0);
									gridDespesas.startEditing(stDes.getCount() + 1, 0);
								}
							}
						});
			}
		} else {
			Ext.Msg
					.show({
						title : 'Aviso',
						msg : 'Escolha o adiantamento para o qual deseja fazer o acerto',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.INFO
					});
		}
	} else {
		Ext.Msg
				.show({
					title : 'Aviso',
					msg : 'Nenhuma adiantamento encontrado. Deseja incluir despesa sem adiantamento?',
					buttons : Ext.Msg.YESNO,
					icon : Ext.MessageBox.QUESTION,
					fn : function(button) {
						if (button == 'yes') {
							var novoItem = new Ext.data.Record({
								'id.centro.id' : '',
								'vlrDes' : '0,00',
								'id.viagem.id' : Ext.getCmp('fIdViagem')
										.getValue()
							});
							stDes.insert(stDes.getCount(), novoItem);
							gridDespesas.getView().refresh();
							gridDespesas.getSelectionModel().selectRow(0);
							gridDespesas.startEditing(stDes.getCount() + 1, 0);
						}
					}
				});
	}

}
/** Retorna a quantidade de adiantamentos lan�ados */
function getCountAdiantamento() {
	var storeAdt = Ext.getCmp("gridAdts").getStore();
	var qtdAdt = 0;
	// fazer contar tamb�m os adiantamentos ainda n�o gravados no banco
	if (storeAdt.getModifiedRecords().length > 0) {
		storeAdt.each(function(record) {
			if (record.phantom == true) {// se ainda n�o for real
				// conta
				qtdAdt++;
			}
		});
	}
	qtdAdt = qtdAdt + storeAdt.getCount();
	return qtdAdt;
}


function geraTelaViagensColaborador(dados) {
	Ext.getCmp('fMot').store.reload();
	Ext.getCmp('fEmp').store.reload();
	var stAdt = Ext.getCmp('gridAdts').store;
	var stDes = Ext.getCmp('gridDes').store;
	
	var leitorVia = criaLeitorViagem();
	
	var formViagem = Ext.getCmp('form');
	var viagemSelecionada;
	var stVia = new Ext.data.Store({remoteSort : true, totalProperty : 'totalCount', reader : leitorVia});
	stVia.loadData(dados);
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true};
	
	var gridVia = new Ext.grid.GridPanel({
		store : stVia,
		loadMask : true,
		cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
			header : 'Cod.',
			dataIndex : 'viagem.id',
			sortable : true,
			width : 20
		}, {
			header : 'Colaborador',
			dataIndex : 'viagem.colaborador.nomFun'
		}, {
			header : 'Data saída',
			dataIndex : 'viagem.datSai'
		}]),
		sm : sm,
		height : 100,
		id : 'sGridVia',
		viewConfig : {forceFit : true,getRowClass : MudaCorVia
		},
		frame : true,
		iconCls : 'icon-grid'
	});

	gridVia.getSelectionModel().on('rowselect', function(sm, rowIndex, record) {
		viagemSelecionada = record;
	});

	var win = new Ext.Window(
			{
				title : 'Viagens encontradas',
				width : 400,
				height : 200,
				layout : 'fit',
				border : false,
				modal : true,
				items : [{	xtype : 'label',
							text : 'As seguintes viagens foram encontradas para o colaborador selecionado. Selecione a viagem que deseja alterar e clique em OK.'
						}, gridVia],
				buttons : [{
					text : 'OK',
					iconCls : 'icnGra',
					handler : function() {
						/** preencher form * */
						stAdt.loadData(dados);
						stAdt.filter('id.viagem.id', viagemSelecionada.get('viagem.id'));
						
						stDes.loadData(dados);
						stDes.filter('id.viagem.id', viagemSelecionada.get('viagem.id'))
						formViagem.getForm().loadRecord(viagemSelecionada);
						if (Ext.getCmp('fSol').getValue() != "") {
							storeSol.proxy.conn.url = 'colaborador!listAllXML.action?colaborador.id='+ Ext.getCmp('fSol').getValue();
							storeSol.addListener('load', function() {
								var codigo = storeSol.getById(Ext.getCmp('fSol').getValue()).get('colaboradorId');
								Ext.getCmp('fSol').setValue(codigo);
							});
							storeSol.reload({callback : function(r, options, success) {
											storeSol.purgeListeners();
											storeSol.proxy.conn.url = 'colaborador!listAllXML.action';
										}
									});
							stCentros.loadData(dados);
							populaCentros(stCentros);
						}
						win.destroy();
					}
				}],
				renderTo : 'pnCenter'
			});
	win.show();
	stVia.setDefaultSort('id', 'DESC');
}




function validateEditorGridPanel(gridId) {
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var records = store.getModifiedRecords();
	var fields = store.fields.items;
	var colunasGrid = grid.getColumnModel().columns;
	for (var row = 0; row < records.length; row++) {
		var record = records[row];
		for (var col = 0; col < colunasGrid.length; col++) {
			if (fields[col] != undefined) {
				var nroColuna = grid.getColumnModel().findColumnIndex(
						fields[col].name);
				if (nroColuna > 0) {// �ndice da coluna
					var cellEditor = grid.getColumnModel().getCellEditor(
							nroColuna, row);
					if (cellEditor != undefined) {
						var columnName = grid.colModel.getDataIndex(nroColuna);
						var columnValue = record.data[columnName];
						if (!cellEditor.field.isValid()) {
							grid.startEditing(store.indexOfId(record.id),
									nroColuna);
							return false;
						}
					}
				}
			}
		}
	}
	return true;
}
