function novaSolicitacao(url) {
	campos = [ 'colaboradorId', 'sexo', {
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
	}, {
		name : 'codCar',
		mapping : 'cargo > codigo'
	} ];
	var storeCol = criaStoreReader('colaborador','colaborador!listAllXML.action?notFindDemitidos=true', campos,'colaboradorId', 'storeCol');
	var colaboradorTpl = new Ext.XTemplate(
			'<tpl for="."><div class="search-item"><h3><span>{col__nomFun}</span></h3><h2>Código: {numCad} Empresa: {empresaId} - {nomEmp}</h2></div></tpl>');
	var storeSup = criaStoreReader('colaborador','colaborador!listAllSupervisoresXML.action', campos,'colaboradorId');
	campos = [ 'id', 'name' ];
	var storeMotivo = criaStoreReader('motivo', 'motivo!listAllXML.action',	campos, 'id');
	/** Cria grid de lan�amento de itens * */
	/** lista de tipos * */
	var leitorX = new Ext.data.XmlReader({
		record : 'tipo',
		id : 'id',
		totalProperty : 'totalCount',
		fields : [ 'id', 'desSvc' ]
	});
	var storeTipo = new Ext.data.Store({
		remoteSort : true,
		totalProperty : 'totalCount',
		proxy : new Ext.data.HttpProxy({
			url : 'tipoEPI!listAllXML.action',
			method : 'POST'
		}),
		reader : leitorX
	});
	storeTipo.load();
	/** cria lista de uniformes * */
	leitorUni = criaLeitorUni();
	var storeUni = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'uniforme!listAllXML.action',
			method : 'POST'
		}),
		baseParams : {
			'uniforme.sitEpi' : 'A'
		},
		reader : leitorUni
	});
	/** lista de itens * */
	leitorXML = geraLeitorItens();
	var st = new Ext.data.Store({
		remoteSort : true,
		totalProperty : 'totalCount',
		proxy : new Ext.data.HttpProxy({
			url : 'solicitacao!gravar.action',
			method : 'POST'
		}),
		reader : leitorXML,
		pruneModifiedRecords : true
	});
	st.setDefaultSort('desEpi', 'ASC');
	var uniformeTpl = new Ext.XTemplate(
			'<tpl for="."><div class="search-item"><h3><span>{desEpi}</span></h3><h2>{cplDes} - (Cód.: {uniformeId})</h2></div></tpl>');
	var comboUni = new Ext.form.ComboBox({
		fieldLabel : 'Uniforme',
		hiddenName : 'uniforme.id',
		store : storeUni,
		valueField : 'uniformeId',
		displayField : 'desEpi',
		typeAhead : true,
		mode : 'local',
		triggerAction : 'all',
		emptyText : 'Selecione o uniforme',
		selectOnFocus : true,
		width : 350,
		id : 'fUni',
		tpl : uniformeTpl,
		itemSelector : 'div.search-item',
		allowBlank : false,
		editable : false,
		listeners : {
			'select' : {
				fn : function() {// configura quantidade m�xima a ser
					// solicitada
					var qtdMax = this.store.getById(this.value).get('qtdMax');
					Ext.getCmp('gridItens').getSelectionModel().getSelected()
							.set("id.uniforme.qtdMax", qtdMax);
				}
			}
		}
	});
	var comboTipo = new Ext.form.ComboBox({
		fieldLabel : 'Tipo',
		hiddenName : 'uniforme.tipoEPI.id',
		store : storeTipo,
		valueField : 'id',
		displayField : 'desSvc',
		typeAhead : true,
		mode : 'local',
		triggerAction : 'all',
		emptyText : 'Selecione o tipo de uniforme...',
		selectOnFocus : true,
		width : 350,
		id : 'fTipUni',
		allowBlank : false,
		editable : false,
		listeners : {
			'select' : {
				fn : function() {
					storeUni.load({
						params : {
							'uniforme.tipoEPI.id' : this.getValue(),
							'sexo'  : storeCol.getById(Ext.getCmp('fCol').getValue()).get('sexo'),
							'empresa' : storeCol.getById(Ext.getCmp('fCol').getValue()).get('empresaId'), 
						}
					});
					Ext.getCmp('fUni').clearValue();
				}
			}
		}
	});
	var smSol = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	var cmSol = new Ext.grid.ColumnModel({
		columns : [ new Ext.grid.RowNumberer(), smSol, {
			header : "Tipo",
			dataIndex : 'id.uniforme.tipoEPI.id',
			width : 130,
			editor : comboTipo,
			renderer : Ext.util.Format.comboRenderer(comboTipo, st),
			fixed : true
		}, {
			header : 'Uniforme',
			dataIndex : 'id.uniforme.id',
			width : 250,
			editor : comboUni,
			renderer : Ext.util.Format.comboRenderer(comboUni, st),
			fixed : true
		}, {
			header : 'Quantidade',
			dataIndex : 'qtdEnt',
			width : 60,
			xtype : 'numbercolumn',
			align : 'right',
			format : '0',
			editor : {
				xtype : 'numberfield',
				allowBlank : false,
				allowNegative : false,
				allowDecimals : false,
				id : 'fQtdEnt',
				name : 'qtdEnt',
				minValue : 1,
				msgTarget : 'under'
			}
		}, {
			header : 'Qtd Max',
			dataIndex : 'id.uniforme.qtdMax',
			hidden : true
		}, {
			header : 'Observações',
			dataIndex : 'obsDis',
			editor : new Ext.form.TextArea({})
		}, {
			header : 'Situação',
			dataIndex : 'sitItm',
			hidden : true
		}, {
			header : 'Situação',
			dataIndex : 'sitItm.descricao'
		}, {
			header : 'Motivo antecipação',
			dataIndex : 'mtvSol',
			editor : new Ext.form.TextArea({
				id : 'fMtvSol',
				isFormField : false
			})
		} ]
	});
	var grid = new Ext.grid.EditorGridPanel(
			{
				store : st,
				cm : cmSol,
				sm : smSol,
				stripeRows : true,
				height : 350,
				autoWidth : true,
				title : 'Itens desta solicitação',
				id : 'gridItens',
				clicksToEdit : 1,
				tbar : [
						{
							text : 'Adicionar item',
							iconCls : 'icnAdd',
							handler : function() {
								if ((Ext.getCmp('fCol').getValue() > 0)
										&& (Ext.getCmp('fMot').getValue() > 0)
										&& (Ext.getCmp('fSup').getValue() > 0)) {
									grid.stopEditing();
									if (st.getCount() > 0)
										idSol = st.getAt(0).get(
												'id.solicitacao.id')
									else
										idSol = 0;
									var novoItem = new Ext.data.Record({
										'id.solicitacao.id' : idSol,
										'id.uniforme.id' : '',
										'id.uniforme.tipoEPI.id' : '',
										'sitItm' : 'SL',
										'sitItm.descricao' : 'Solicitado'
									});
									st.insert(st.getCount(), novoItem);
									grid.getView().refresh();
									grid.getSelectionModel().selectRow(0);
									grid.startEditing(st.getCount() + 1, 0);
								} else {
									Ext.Msg
											.show({
												title : 'Alerta',
												msg : 'Preencha os campos Colaborador, Motivo e Supervisor antes de adicionar um item.',
												buttons : Ext.MessageBox.OK,
												animEl : 'gridItens',
												icon : Ext.MessageBox.WARNING
											});
								}
							}
						}, '-', {
							text : 'Excluir item',
							iconCls : 'icnExc',
							tooltip : 'Excluir o item selecionado',
							handler : excluirItem
						} ],
				viewConfig : {
					// forceFit :true,
					getRowClass : MudaCor
				}
			});
	// Verifica��es antes de editar o item na grid
	grid
			.on(
					'beforeedit',
					function(e) {
						// Se o item j� estiver como enviado, n�o permite
						// altera��o
						var situacao = e.record.get('sitItm');
						if (situacao.localeCompare('EN') == 0) {
							Ext.Msg
									.show({
										title : 'Alerta',
										msg : 'Item já enviado, não pode ser alterado.',
										buttons : Ext.MessageBox.OK,
										animEl : 'gridItens',
										icon : Ext.MessageBox.WARNING
									});
							e.cancel = true;
						} else {
							// se o campo a ser alterado � o uniforme, n�o
							// permite
							if (e.record.phantom == false) {// se o registro for
								// real,
								// n�o
								// pode alterar
								if (((e.field.localeCompare('id.uniforme.id') == 0) || (e.field
										.localeCompare('id.uniforme.tipoEPI.id') == 0))) {
									Ext.Msg
											.show({
												title : 'Alerta',
												msg : 'Alteração de item não permitida. Exclua este item e insira o item correto.',
												buttons : Ext.MessageBox.OK,
												animEl : 'gridItens',
												icon : Ext.MessageBox.WARNING
											});
									e.cancel = true;
								}
							}
						}
					});
	grid
			.on(
					'afteredit',
					function(e) {
						if (e.field.localeCompare('id.uniforme.id') == 0) {
							if (e.value > 0) {
								// verifica a validade do item
								Ext.Ajax
										.request({
											url : 'itemSolicitacao!verificaValidade.action',
											params : {
												idUni : Ext.getCmp('gridItens')
														.getSelectionModel()
														.getSelected()
														.get('id.uniforme.id'),
												idCol : Ext.getCmp('fCol')
														.getValue()
											},
											success : function(result, request) {
												retorno = result.responseText;
												if (retorno == 0) {// fora
													// do
													// prazo
													// obrigar preenchimento
													// motivo
													// antecipa��o
													Ext.getCmp('fMtvSol').allowBlank = false;
													e.record.set(
															'sitItm.descricao',
															'Bloqueado');
													e.record
															.set('sitItm', 'SP');
													Ext.Msg
															.show({
																title : 'Aviso',
																msg : 'O item solicitado não está no prazo de troca. Informe o motivo da antecipação.',
																buttons : Ext.MessageBox.OK,
																icon : Ext.MessageBox.WARNING,
																fn : function(
																		btn) {
																	var nroColuna = e.grid
																			.getColumnModel()
																			.findColumnIndex(
																					"mtvSol");
																	e.grid
																			.startEditing(
																					e.row,
																					nroColuna);
																}
															});
												} else {
													Ext.getCmp('fMtvSol').allowBlank = true;
													Ext
															.getCmp('gridItens')
															.getSelectionModel()
															.getSelected()
															.set(
																	"id.uniforme.qtdMax",
																	retorno);
													if (retorno < Ext
															.getCmp('gridItens')
															.getSelectionModel()
															.getSelected()
															.get('qtdEnt'.value))// se a
														// quantidade
														// pedida pelo
														// usu�rio n�o
														// for a mesma
														// quantidade
														// liberada
														Ext.Msg
																.show({
																	title : 'Alerta',
																	msg : "Quantidade maior que a permitida: "
																			+ retorno,
																	buttons : Ext.MessageBox.OK,
																	icon : Ext.MessageBox.ERROR,
																	fn : function() {
																		e.grid
																				.startEditing(
																						e.row,
																						4);// reabre
																		// para
																		// edi��o
																		// a
																		// quantidade
																	}
																});
												}
											},
											failure : function(result, request) {
												Ext.Msg
														.show({
															title : 'Falha',
															msg : 'Erro ao tentar buscar validade do item',
															buttons : Ext.MessageBox.OK,
															icon : Ext.MessageBox.INFO
														});
											}
										});
							}
						} else if (e.field
								.localeCompare('id.uniforme.tipoEPI.id') == 0) {
							e.grid.startEditing(e.row, 2);
						}
					});
	var formSol = new Ext.form.FormPanel({
		labelWidth : 75,
		url : 'solicitacao!gravar.action',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		maximizable : true,
		reader : criaLeitorSol(),
		id : 'formSol',
		items : [ {
			xtype : 'hidden',
			fieldLabel : 'Código',
			id : 'fIdSol',
			name : 'solicitacao.id',
			readOnly : true
		}, new Ext.form.DateField({
			fieldLabel : 'Data da solicitação',
			name : 'solicitacao.datEnt',
			width : 100,
			id : 'fDatEnt',
			hidden : true,
			hideLabel : true
		}), new Ext.form.DateField({
			fieldLabel : 'Data de inclusão',
			name : 'solicitacao.datInc',
			width : 100,
			id : 'fDatInc',
			hidden : true,
			hideLabel : true
		}), {
			xtype : 'combo',
			store : storeCol,
			id : 'fCol',
			hiddenName : 'solicitacao.colaborador.id',
			fieldLabel : 'Colaborador',
			displayField : 'colaborador.nomFun',
			valueField : 'colaboradorId',
			typeAhead : true,
			mode : 'remote',
			itemSelector : 'div.search-item',
			emptyText : 'Busca automática a partir de 4 caracteres...',
			loadingText : 'Procurando...',
			pageSize : 10,
			width : 350,
			tpl : colaboradorTpl,
			triggerClass : 'x-form-search-trigger',
			triggerAction : 'all',
			hideTrigger : true,
			minChars : 4,
			queryParam : 'colaborador.nomFun',
			allowBlank : false,
			forceSelection : true
		}, {
			xtype : 'combo',
			fieldLabel : 'Motivo da solicitação',
			hiddenName : 'solicitacao.motivo.id',
			store : storeMotivo,
			valueField : 'id',
			displayField : 'name',
			typeAhead : true,
			mode : 'remote',
			triggerAction : 'all',
			emptyText : 'Selecione um motivo...',
			width : 350,
			id : 'fMot',
			allowBlank : false,
			forceSelection : true,
			listeners : {
				beforequery : function(qe) {
					delete qe.combo.lastQuery;
				}
			}
		}, {
			xtype : 'combo',
			store : storeSup,
			hiddenName : 'solicitacao.supervisor.id',
			fieldLabel : 'Supervisor',
			displayField : 'colaborador.nomFun',
			valueField : 'colaboradorId',
			typeAhead : true,
			mode : 'remote',
			emptyText : 'Selecione um supervisor...',
			width : 350,
			triggerAction : 'all',
			id : 'fSup',
			tpl : colaboradorTpl,
			triggerClass : 'x-form-search-trigger',
			itemSelector : 'div.search-item',
			allowBlank : false,
			forceSelection : true,
			lastQuery : '',
			queryParam : 'colaborador.nomFun',
			listeners : {
				beforequery : function(qe) {
					delete qe.combo.lastQuery;
				}
			}
		}, grid ]
	});
	Ext.getCmp('fCol').on(
			'blur',
			function(e) {
				if (e.getValue() != '')
					carregaForm(
							"solicitacao!findByDateCol.action?solicitacao.colaborador.id="
									+ Ext.getCmp('fCol').getValue(), true);
			});

	var win = new Ext.Window(
			{
				title : 'Solicitação de uniformes',
				width : 830,
				height : 480,
				layout : 'fit',
				border : false,
				id : 'wSol',
				items : [ formSol ],
				buttons : [
						{
							text : 'Gravar',
							id : 'btGravar',
							iconCls : 'icnGra',
							handler : function() {
								// validar todos os dados antes de gravar
								// verificar se h� modifica��es a serem feitas
								if (st.getModifiedRecords().length > 0) {
									if (validaSolicitacao('gridItens', st) == true) {
										var records = st.getModifiedRecords(), fields = st.fields.items;
										var params = {};
										var colunasGrid = grid.getColumnModel().columns;
										for ( var i = 0; i < records.length; i++) {
											for ( var j = 0; j < fields.length; j++) {
												if ((fields[j].name
														.localeCompare('usuarioLiberacao.id')) != 0)
													params['itens[' + i + '].'
															+ fields[j].name] = records[i]
															.get(fields[j].name);
											}
										}
										params['solicitacao.id'] = Ext.getCmp(
												'fIdSol').getValue();
										params['solicitacao.supervisor.id'] = Ext
												.getCmp('fSup').getValue();
										params['solicitacao.colaborador.id'] = Ext
												.getCmp('fCol').getValue();
										params['solicitacao.motivo.id'] = Ext
												.getCmp('fMot').getValue();
										if (Ext.getCmp('fDatEnt').getValue() != '')
											params['solicitacao.datEnt'] = Ext
													.getCmp('fDatEnt')
													.getValue().format('d/m/Y');
										if (Ext.getCmp('fDatInc').getValue() != '')
											params['solicitacao.datInc'] = Ext
													.getCmp('fDatInc')
													.getValue().format('d/m/Y');
										Ext.Ajax
												.request({
													url : 'solicitacao!gravar.action',
													params : params,
													method : 'POST',
													beforerequest : function(
															con, opts) {
														Ext.getCmp('btGravar')
																.disable();
													},
													requestcomplete : function(
															con, res, opts) {
														Ext.getCmp('btGravar')
																.disable();
													},
													success : function(result,
															request) {
														Ext.Msg
																.show({
																	title : 'Sucesso',
																	msg : 'Solicitação gravada com sucesso',
																	buttons : Ext.MessageBox.OK,
																	icon : Ext.MessageBox.INFO,
																	fn : function() {
																		novoId = parseInt(result.responseText);
																		if (isNaN(novoId) == false)// retorna
																			// id
																			// da
																			// solicita��o
																			if (novoId > 0)
																				carregaForm(
																						'pesquisa!buscaXML.action?solicitacao.id='
																								+ novoId,
																						false);
																	}
																});
														st
																.on(
																		'update',
																		/**
																		 * ao
																		 * gravar
																		 * o
																		 * item
																		 * no
																		 * store,
																		 * configurar
																		 * o
																		 * record
																		 * para
																		 * real*
																		 */
																		function(
																				st,
																				record,
																				operation) {
																			if (operation
																					.localeCompare('commit') == 0) {
																				record.phantom = false;
																			}
																		});
														st.commitChanges();
													},
													failure : function(result,
															request) {
														Ext.Msg
																.show({
																	title : 'Falha',
																	msg : result.responseText,
																	buttons : Ext.MessageBox.OK,
																	icon : Ext.MessageBox.INFO
																});
													}
												});
									}
								} else {
									Ext.Msg
											.show({
												title : 'Aviso',
												msg : 'Nenhuma modificação foi feita. Nada será gravado.',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.INFO
											});
								}
							}
						},
						{
							text : 'Cancelar',
							iconCls : 'icnCan',
							handler : function() {
								if (st.getModifiedRecords().length > 0) {
									Ext.Msg
											.show({
												title : 'Abandonar alterações?',
												msg : 'Você está fechando a tela sem salvar. Deseja abandonar as alterações e sair?',
												buttons : Ext.Msg.YESNOCANCEL,
												icon : Ext.MessageBox.QUESTION,
												fn : function(button) {
													if (button == 'yes')
														win.destroy();
												}
											});
								} else {
									win.destroy();
								}
							}
						} ],
				tbar : [ '-', {
					text : 'Imprimir Espelho',
					iconCls : 'icnPrt',
					handler : imprimeEspelhoItens
				}, '-', {
					text : 'Nova soliciação',
					iconCls : 'icnPrt',
					handler : function() {
						Ext.getCmp('formSol').form.reset();
						Ext.getCmp('gridItens').store.removeAll();
					}
				} ],
				listeners : {
					'show' : function() {
						storeMotivo.load();
						if (url) {
							carregaForm(url, false);
						}
					}
				}
			});
	win.show();
	win.center();
}
function validaSolicitacao(gridId, store) {
	var grid = Ext.getCmp(gridId);
	var records = store.getModifiedRecords();
	var fields = store.fields.items;
	var colunasGrid = grid.getColumnModel().columns;
	for ( var row = 0; row < records.length; row++) {
		var record = records[row];
		for ( var col = 0; col < colunasGrid.length; col++) {
			var nroColuna = grid.getColumnModel().findColumnIndex(
					fields[col].name);
			if (nroColuna > 0) {
				var cellEditor = grid.getColumnModel().getCellEditor(nroColuna,
						row);
				if (cellEditor != undefined) {
					var columnName = grid.colModel.getDataIndex(nroColuna);
					var columnValue = record.data[columnName];
					var motivo = store.getAt(store.indexOfId(record.id)).get(
							'mtvSol');
					var qtdMax = store.getAt(store.indexOfId(record.id)).get(
							'id.uniforme.qtdMax');
					var qtdEnt = store.getAt(store.indexOfId(record.id)).get(
							'qtdEnt');
					var situacao = store.getAt(store.indexOfId(record.id)).get(
							'sitItm');
					if ((situacao == "SP") && (motivo == "")) {
						grid.startEditing(store.indexOfId(record.id), grid
								.getColumnModel().findColumnIndex('mtvSol'));
						return false;
					}
					if (qtdEnt > qtdMax) {
						Ext.Msg
								.show({
									title : 'Alerta',
									msg : "Quantidade maior que a permitida: "
											+ qtdMax,
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR,
									fn : function() {
										grid.startEditing(store
												.indexOfId(record.id), grid
												.getColumnModel()
												.findColumnIndex('qtdEnt'));
									}
								});
						return false;
					}
					cellEditor.field.setValue(columnValue);
					if (!cellEditor.field.isValid()) {
						grid
								.startEditing(store.indexOfId(record.id),
										nroColuna);
						return false;
					}
				}
			}
		}
	}
	return true;
}
function carregaForm(urlDados, exibeMsg) {
	var formSol = Ext.getCmp('formSol');
	if (urlDados != "") {
		formSol
				.getForm()
				.load(
						{
							url : urlDados,
							waitMsg : 'Aguarde, preenchendo dados...',
							success : function(form, action) {
								if (action.response.responseXML.childNodes.length > 0) {
									if (exibeMsg) {
										Ext.Msg
												.show({
													title : 'Aviso',
													msg : 'Já existe uma  solicitação aberta para este colaborador.',
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO
												});
									}
									preencheForm(action.response.responseXML);
								}
							},
							failure : function(form, action) {
								Ext.getCmp('fMot').clearValue();
								Ext.getCmp('fSup').clearValue();
								Ext.getCmp('fDatEnt').reset();
								Ext.getCmp('fDatInc').reset();
								Ext.getCmp('fIdSol').reset();
								Ext.getCmp('gridItens').store.removeAll();
							}
						});
	}
}
function preencheForm(dados) {
	var storeCol = Ext.getCmp('fCol').store;
	var storeSup = Ext.getCmp('fSup').store;
	var st = Ext.getCmp('gridItens').store;
	/** preenche colaborador * */
	if (Ext.getCmp('fCol').getValue() != "") {
		storeCol.proxy.conn.url = 'colaborador!listAllXML.action?colaborador.id='
				+ Ext.getCmp('fCol').getValue();
		storeCol.addListener('load', function() {
			if (storeCol.getById(Ext.getCmp('fCol').getValue()) != undefined) {
				var codigo = storeCol.getById(Ext.getCmp('fCol').getValue())
						.get('colaboradorId');
				Ext.getCmp('fCol').setValue(codigo);
			}
		});
		storeCol.reload({
			callback : function(r, options, success) {
				storeCol.proxy.conn.url = 'colaborador!listAllXML.action';
			}
		});
	}
	/** preenche supervisor * */
	if (Ext.getCmp('fSup').getValue() != "") {
		storeSup.proxy.conn.url = 'colaborador!listAllXML.action?colaborador.id='
				+ Ext.getCmp('fSup').getValue();
		storeSup.addListener('load', function() {
			if (storeSup.getById(Ext.getCmp('fSup').getValue()) != undefined) {
				var codigo = storeSup.getById(Ext.getCmp('fSup').getValue())
						.get('colaboradorId');
				Ext.getCmp('fSup').setValue(codigo);
			}
		});
		storeSup
				.reload({
					callback : function(r, options, success) {
						storeSup.proxy.conn.url = 'colaborador!listAllSupervisoresXML.action';
					}
				});
	}
	st.proxy.conn.url = 'itemSolicitacao!libXML.action?idSol='
			+ Ext.getCmp('fIdSol').getValue();
	st.reload();
}
function imprimeEspelhoItens() {
	var linhaSelecionada = Ext.getCmp('gridItens').getSelectionModel()
			.getSelected();
	window.location = 'solicitacao!imprimirEspelho.action?idSol='
			+ Ext.getCmp('fIdSol').getValue();
}