var diaBloqueio;

function novaRequisicaoEstoque(tipoMaterial, descricao) {
	/**
	 * tipoMaterial determina se � : ME = MATERIAL DE EXPEDIENTE MI = MATERIAL
	 * DE INSTALA��O/FERRAMENTAS, LI = LIMPEZA
	 */
	diaBloqueio = getDiaBloqueioRequisicao();
	Ext.form.Field.prototype.msgTarget = 'side';

	var stReq = criaStoreReq(tipoMaterial);

	var smReq = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	Ext.util.Format.brMoney = function(v) {
		return "R$ " + Ext.util.Format.number(v, '0.000,00/i');
	}

	/** =========================MATERIAL============================================================================* */
	var materialTpl = new Ext.XTemplate(
			'<tpl for="."><div class="search-item"><h3><span>{desPro}</span></h3><h2>Código: {produtoId}</h2></div></tpl>');
	var storeMat = criaStoreMat('produto!listAllMatExp.action?produto.tipPro='
			+ tipoMaterial);
	var cbMat = new Ext.form.ComboBox({
		hiddenName : 'produto.id.codPro',
		fieldLabel : 'Material',
		store : storeMat,
		valueField : 'produtoId',
		displayField : 'produto.desPro',
		typeAhead : true,
		mode : 'remote',
		triggerAction : 'all',
		emptyText : 'Busca automática a partir de 4 caracteres...',
		selectOnFocus : true,
		width : 250,
		id : 'fMat',
		tpl : materialTpl,
		itemSelector : 'div.search-item',
		allowBlank : false,
		forceSelection : true,
		minChars : 3,
		queryParam : 'produto.desPro',
		listeners : {
			'select' : {
				fn : function(cb, rec, idx) {// exibe a unidade de medida
					var uniMed = rec.get('uniMed');
					Ext.getCmp('gridReqME').getSelectionModel().getSelected()
							.set("uniMed", uniMed);
					var preUni = rec.get('preUni');
					Ext.getCmp('gridReqME').getSelectionModel().getSelected()
							.set("preUni", preUni);
				}
			},// faz com que o tab gere o evento select
			afterrender : function(c) {
				c.keyNav.tab = function() {
					this.onViewClick(false);
				};
			},
			single : true
		}
	});

	var storeSetor = criaStoreReader('item','requisicaoEstoque!getSetores.action', [ 'id', 'name' ], 'id');

	var storeCentro = criaStoreReader('centro', 'centro!listByExample.action',[ 'id', 'nomCcu' ], 'id', 'storeCentro');

	var cmReq = new Ext.grid.ColumnModel(
			{
				columns : [
						new Ext.grid.RowNumberer(),
						smReq,
						{
							header : 'Cód.',
							width : 50,
							dataIndex : 'id',
							sortable : true,
							hidden : true
						},
						{
							header : 'Produto',
							width : 50,
							dataIndex : 'codPro',
							sortable : true,
							hidden : false
						},
						{
							header : 'Produto',
							dataIndex : 'codPro',
							width : 50,
							editor : cbMat,
							id : 'desPro',
							renderer : Ext.util.Format.comboRenderer(cbMat,	stReq),
							fixed : true
						},
						{// ao digitar a quantidade, calcular pre�o total
							header : 'Quantidade',
							dataIndex : 'qtdEme',
							width : 60,
							xtype : 'numbercolumn',
							align : 'right',
							format : '0',
							editor : {
								xtype : 'numberfield',
								allowBlank : false,
								allowNegative : false,
								allowDecimals : true,
								id : 'fQtdEme',
								name : 'qtdEme',
								minValue : 1,
								msgTarget : 'under',
								listeners : {
									blur : {
										fn : function(field) {
											var qtd = field.value;
											var pre = Ext.getCmp('gridReqME').getSelectionModel().getSelected().get('preUni');
											var preTot = qtd * pre;
											Ext.getCmp('gridReqME').getSelectionModel().getSelected().set("preTot", preTot);
											Ext.getCmp('gridReqME').getSelectionModel().getSelected().get("produto").preUni = parseFloat(pre);
										}
									}
								}
							}
						},
						{
							header : 'Un. Med.',
							dataIndex : 'uniMed',
							width : 50,
							fixed : true
						},
						{
							header : 'Pré Unit.',
							dataIndex : 'preUni',
							renderer : 'brMoney',
							width : 50,
							fixed : true
						},
						{
							header : 'Pré Tot',
							dataIndex : 'preTot',
							renderer : 'brMoney',
							width : 50,
							fixed : true
						},
						{
							header : 'Observações',
							dataIndex : 'obsEme',
							editor : new Ext.form.TextArea({
								enableKeyEvents : true,
								listeners : {
									blur : {
										fn : function(field) {
											if (Ext.getCmp('gridReqME').getSelectionModel().hasNext() == false) {
												novoRegistro(Ext.getCmp('gridReqME').getStore(),tipoMaterial);
											}
										}
									}
								}
							})
						}, {
							header : 'Solicitante',
							width : 100,
							dataIndex : 'codUsu',
							sortable : true,
							id : 'usuSol'
						}, {
							header : 'Situação',
							width : 100,
							dataIndex : 'sitEme',
							sortable : true
						} ]
			});
	var dataHoje = new Date();
	dataHoje.setMonth(dataHoje.getMonth() + 1);

	var gridReq = new Ext.grid.EditorGridPanel(
			{
				store : stReq,
				cm : cmReq,
				sm : smReq,
				id : 'gridReqME',
				height : 325,
				autoExpandColumn : 'desPro',
				title : 'Requisição de ' + descricao,
				frame : true,
				clicksToEdit : 1,
				loadMask : true,
				layout : 'fit',
				viewConfig : {
					getRowClass : MudaCorReq,
					autoFill : true
				},
				tbar : [
						{
							text : 'Competência(mês/ano):'
						},
						new Ext.form.DateField({
							name : 'cmpReq',
							width : 80,
							allowBlank : false,
							id : 'fCmpReq',
							msgTarget : 'under',
							format : 'm/Y',
							value : dataHoje
						}),
						"-",
						{
							text : 'Setor:'
						},
						{
							xtype : 'combo',
							fieldLabel : 'Setor',
							hiddenName : 'setor',
							store : storeSetor,
							valueField : 'id',
							displayField : 'name',
							typeAhead : true,
							mode : 'remote',
							triggerAction : 'all',
							emptyText : 'Informe seu setor...',
							selectOnFocus : true,
							width : 120,
							id : 'fSet',
							allowBlank : false,
							forceSelection : true,
							msgTarget : 'title'
						},
						{
							text : 'Centro de custo:'
						},
						{
							xtype : 'combo',
							fieldLabel : 'Centro',
							hiddenName : 'centro.id',
							store : storeCentro,
							valueField : 'id',
							displayField : 'nomCcu',
							typeAhead : true,
							mode : 'remote',
							triggerAction : 'all',
							emptyText : 'Selecione um centro...',
							selectOnFocus : true,
							width : 170,
							id : 'fCen',
							allowBlank : false,
							forceSelection : true
						},
						{
							text : 'Adicionar item',
							iconCls : 'icnAdd',
							id : 'btAdItem',
							handler : function() {
								if ((Ext.getCmp('fCmpReq').isValid() == true)
										&& (Ext.getCmp('fSet').isValid() == true)
										&& (Ext.getCmp('fCen').isValid() == true)) {
									if (verificaPeriodo() == true)
										novoRegistro(stReq, tipoMaterial);
									else {
										Ext.Msg
												.show({
													title : 'Aviso',
													msg : 'Per�odo encerrado. A data de encerramento é no dia '
															+ diaBloqueio
															+ ' de cada mês.',
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
									}
								} else {
									Ext.Msg
											.show({
												title : 'Aviso',
												msg : 'Informe a competência da requisição no formato mês/ano (Exemplo: 01/2010), o setor e centro de custo de destino do material',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.INFO
											});
								}
							}
						},
						"-",
						{
							text : 'Excluir',
							iconCls : 'icnExc',
							handler : function() {
								excluiReq('gridReqME');
							}
						},
						"-",
						{
							text : 'Imprimir',
							iconCls : 'icnPrt',
							handler : function() {
								if (Ext.getCmp('fCmpReq').isValid() == true) {
									imprimeReq(tipoMaterial, (Ext.getCmp('fCmpReq').getValue()).format('m/Y'));
								} else {
									Ext.Msg
											.show({
												title : 'Aviso',
												msg : 'Informe a competência da requisição no formato mês/ano. Exemplo: 01/2010',
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.INFO
											});
								}
							}
						} ]
			});

	/** N�o permitir altera��o em requisi��es ap�s serem aprovadas e precessadas * */
	gridReq.on('beforeedit', function(e) {
		var situacao = e.record.get('sitEme');
		if (situacao != "Digitado") {
			Ext.Msg.show({
				title : 'Alerta',
				msg : 'Item em processamento, não pode mais ser alterado.',
				buttons : Ext.MessageBox.OK,
				animEl : 'gridReqME',
				icon : Ext.MessageBox.WARNING
			});
			e.cancel = true;
		}
	});

	Ext
			.getCmp('fCmpReq')
			.on(
					'blur',
					function(e) {
						if (verificaPeriodo() == false) {// verifica se o
							// per�odo informado
							// ainda aceita requisi��es
							Ext.Msg
									.show({
										title : 'Aviso',
										msg : 'Período encerrado. A data de encerramento é no dia '
												+ diaBloqueio + ' de cada mês.',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						} else {
							if (e.isValid() == true) {
								if ((Ext.getCmp('fSet').isValid() == true)) {
									if (e.getValue() != '')
										carregaFormMatExp(tipoMaterial);
								} else {
									Ext.getCmp('fSet').focus();
								}
							} else {
								Ext.Msg
										.show({
											title : 'Aviso',
											msg : 'Informe a competência da requisição no formato mês/ano. Exemplo: 01/2010',
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.INFO
										});
							}
						}
					});

	Ext
			.getCmp('fSet')
			.on(
					'select',
					function(e, rec, idx) {
						if (verificaPeriodo() == false) {// verifica se o
							// per�odo informado
							// ainda aceita requisi��es
							Ext.Msg
									.show({
										title : 'Aviso',
										msg : 'Período encerrado. A data de encerramento é no dia '
												+ diaBloqueio + ' de cada mês.',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						} else {
							if (e.isValid() == true) {
								var validos = false;
								if (Ext.getCmp('fCmpReq').isValid() == false) {
									Ext.getCmp('fCmpReq').focus();
								}
								if (Ext.getCmp('fCen').isValid() == false) {
									Ext.getCmp('fCen').focus();
								}
								if ((Ext.getCmp('fCmpReq').isValid() == true)
										&& (Ext.getCmp('fCen').isValid() == true))
									carregaFormMatExp(tipoMaterial);

							} else {
								Ext.Msg.show({
									title : 'Aviso',
									msg : 'Informe o setor.',
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						}
					});

	Ext
			.getCmp('fCen')
			.on(
					'select',
					function(e, rec, idx) {
						if (verificaPeriodo() == false) {// verifica se o
							// per�odo informado
							// ainda aceita requisi��es
							Ext.Msg
									.show({
										title : 'Aviso',
										msg : 'Período encerrado. A data de encerramento é no dia '
												+ diaBloqueio + ' de cada mês.',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						} else {
							if (e.isValid() == true) {
								var validos = false;
								if (Ext.getCmp('fCmpReq').isValid() == false) {
									Ext.getCmp('fCmpReq').focus();
								}
								if (Ext.getCmp('fSet').isValid() == false) {
									Ext.getCmp('fSet').focus();
								}
								if ((Ext.getCmp('fCmpReq').isValid() == true)
										&& (Ext.getCmp('fSet').isValid() == true))
									carregaFormMatExp(tipoMaterial);

							} else {
								Ext.Msg.show({
									title : 'Aviso',
									msg : 'Informe o centro de custo.',
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						}
					});

	stReq.on('beforesave', function(st, dt) {
		myMask.msg = "Aguarde, gravando requisição...";
		myMask.show();
	});
	stReq.on('save', function(st, bc, dt) {
		myMask.hide();
	});
	var winME = new Ext.Window(
			{
				title : 'Requisição de estoque',
				width : 878,
				height : 400,
				layout : 'anchor',
				plain : true,
				border : false,
				id : 'wReqEst',
				constrain : true,
				tbar : new Ext.Toolbar({}),
				items : [ gridReq ],
				maximizable : true,
				maximized : false,
				closable : true,
				resizable : true,
				renderTo : document.body,
				forceLayout : true,
				buttons : [ {
					text : 'Gravar',
					id : 'btGravar',
					iconCls : 'icnGra',
					handler : function() {
						if (stReq.getModifiedRecords().length > 0) {
							if (validaReq('gridReqME') == true) {
								Ext
										.each(
												stReq.getModifiedRecords(),
												function(record) {
													record.data.produto.id.codPro = record.data.codPro;
													if (!record.isValid()) {
														Ext.Msg
																.show({
																	title : 'Aviso',
																	msg : 'Não foi possível enviar requisição. Verifique se todos os dados estão preenchidos.',
																	buttons : Ext.MessageBox.OK,
																	icon : Ext.MessageBox.ERROR
																});
													}
												});
								stReq.setBaseParam('setor', Ext.getCmp('fSet')
										.getValue());
								stReq.save();
							}
						}
					}
				} ]

			});
	winME.show();
	winME.center();
}

function carregaFormMatExp(tipoMaterial) {
	if (tipoMaterial == "ME")
		Ext.getCmp('gridReqME').store.proxy.conn.url = 'requisicaoEstoque!listReqExpByCmp.action';
	else if (tipoMaterial == "MI")
		Ext.getCmp('gridReqME').store.proxy.conn.url = 'requisicaoEstoque!listReqMatInsByCmp.action';
	else if (tipoMaterial == "LI")
		Ext.getCmp('gridReqME').store.proxy.conn.url = 'requisicaoEstoque!listReqMatLimByCmp.action';
	/**
	 * BUSCAR MATERIAL
	 */
	Ext.getCmp('gridReqME').store
			.load({
				params : {
					'cmpReq' : (Ext.getCmp('fCmpReq').getValue()).format('m/Y'),
					'setor' : Ext.getCmp('fSet').getValue(),
					'centro.id' : Ext.getCmp('fCen').getValue()
				},
				callback : function(records, options, success) {
					if (success == true) {
						if (Ext.getCmp('gridReqME').store.getTotalCount() == 0) {
							Ext.getCmp('gridReqME').store.removeAll();
						} else {
							Ext.Msg
									.show({
										title : 'Aviso',
										msg : 'Já existe requisições abertas para esta competência.',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO
									});
						}
					}
					novoRegistro(Ext.getCmp('gridReqME').store, tipoMaterial);
				}
			});
}

function imprimeReq(tipoMaterial, cmpReq) {
	window.location = 'requisicaoEstoque!imprimirRequisicao.action?cmpReq='
			+ cmpReq + '&tipoMaterial=' + tipoMaterial + "&setor="
			+ Ext.getCmp('fSet').getValue();
}

function processaRequisicaoEstoque(tipoMaterial, descricao) {
	var filters = new Ext.ux.grid.GridFilters({
		encode : false,
		local : true,
		filters : [ {
			type : 'string',
			dataIndex : 'centro.nomCcu',
			disabled : false
		}, {
			type : 'string',
			dataIndex : 'cmpReq'
		}, {
			type : 'string',
			dataIndex : 'codPro'
		} ]
	});

	var url = 'requisicaoEstoque!listAprovados.action?tipoMaterial='
			+ tipoMaterial;
	var rWriter = new Ext.data.JsonWriter({
		encode : true,
		writeAllFields : true
	});
	var stReq = new Ext.data.GroupingStore({
		reader : getReaderStoreReq(),
		sortInfo : {
			field : 'produto.desPro',
			direction : "ASC"
		},
		groupField : 'produto.desPro',
		url : url,
		writer : rWriter,
		autoSave : false,
		autoLoad : false
	});

	stReq.setDefaultSort('produto.desPro', 'ASC');

	stReq.on('beforesave', function(st, dt) {
		myMask.msg = "Aguarde, processando requisição(es)...";
		myMask.show();
	});

	var tb = new Ext.Toolbar({
		id : 'tbReq',
		items : [ new Ext.Button({
			text : 'Processar',
			iconCls : 'icnPrc',
			tooltip : 'Processa os as requisições selecionadas',
			handler : function() {
				processaReq(tipoMaterial);
			}
		}) ]
	});

	var sm = new Ext.grid.CheckboxSelectionModel({
		listeners : {
			rowselect : function(sm, rowIndex, rec) {
				/** marcar o record como modificado * */
				rec.set('processada', true);
			},
			rowdeselect : function(sm, rowIndex, rec) {
				/** marcar o record como não modificado* */
				rec.reject();
			}
		}
	});

	var cmReq = new Ext.grid.ColumnModel({
		columns : [ new Ext.grid.RowNumberer(), sm, {
			header : 'N Req.',
			width : 50,
			dataIndex : 'id',
			sortable : true,
			hidden : false
		}, {
			header : 'Cód.Produto',
			width : 70,
			dataIndex : 'codPro',
			sortable : true,
			hidden : false,
			filterable : true
		}, {
			header : 'Competência',
			dataIndex : 'cmpReq',
			filterable : true
		}, {
			header : 'Produto',
			dataIndex : 'produto.desPro',
			width : 180,
			id : 'desPro',
			sortable : true,
			filterable : true
		}, {
			header : 'Vlr. Uni.',
			dataIndex : 'preUni',
			width : 60,
			xtype : 'numbercolumn',
			renderer : 'brMoney',
			align : 'right',
			format : '0.00'
		}, {
			header : 'Qtd. Pedida',
			dataIndex : 'qtdEme',
			width : 60,
			xtype : 'numbercolumn',
			align : 'right',
			format : '0'
		}, {
			header : 'Qtd. Aprov.',
			dataIndex : 'qtdApr',
			width : 60,
			xtype : 'numbercolumn',
			align : 'right',
			format : '0'
		}, {
			header : 'Un. Med.',
			dataIndex : 'uniMed',
			width : 50
		}, {
			header : 'Observações',
			dataIndex : 'obsEme'
		}, {
			header : 'Solicitante',
			width : 100,
			dataIndex : 'codUsu',
			sortable : true,
			id : 'usuSol'
		}, {
			header : 'Unidade',
			width : 100,
			dataIndex : 'centro.nomCcu',
			sortable : true,
			id : 'nomCcu',
			filterable : true
		}, {
			header : 'Data Req.',
			width : 100,
			dataIndex : 'datReq',
			sortable : true,
			id : 'datReq'
		} ]
	});
	var sGrid = new Ext.grid.GridPanel(
			{
				store : stReq,
				loadMask : true,
				cm : cmReq,
				sm : sm,
				tbar : tb,
				id : 'gridReqs',
				plugins : [ filters ],
				autoExpandColumn : 'desPro',
				view : new Ext.grid.GroupingView(
						{
							groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Requisições" : "Requisição"]})',
							startCollapsed : false,
							groupByText : 'Agrupar por este campo',
							getRowClass : MudaCorReq,
							autoFill : true,
							forceFit : true
						})
			});

	var win = new Ext.Window({
		title : 'Processamento de requisições de ' + descricao,
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
	stReq.load({
		params : {
			start : 0,
			limit : 20
		}
	});
}

function processaReq(tipoMaterial) {
	var st = Ext.getCmp('gridReqs').store;

	/**st.proxy.on('exception', function(proxy, action, data, res, rs) {
		var errorData = Ext.util.JSON.decode(rs.responseText);
		console.log(rs);
	});**/

	st.proxy.setApi('update',
			'requisicaoEstoque!processar.action?tipoMaterial=' + tipoMaterial);
	st.on('save', function(st, bc, dt) {
		myMask.hide();
		st.reload();
	});

	st.save();
}

function aprovaRequisicaoEstoque(tipoMaterial, descricao) {
	var listAprovados = false;
	var stReq = criaStoreReq(tipoMaterial);
	stReq.proxy.setApi('read',
			'requisicaoEstoque!listDigitados.action?tipoMaterial='
					+ tipoMaterial);

	function pctChange(val) {
		return '<span style="color:green;">' + val + '</span>';
		return val;
	}

	var sm = new Ext.grid.CheckboxSelectionModel({
		listeners : {
			rowselect : function(sm, rowIndex, rec) {
				/** marcar o record como modificado * */
				rec.set('aprovada', true);
			},
			rowdeselect : function(sm, rowIndex, rec) {
				/** marcar o record como n�o modificado* */
				rec.reject();
			}
		}
	});

	var filters = new Ext.ux.grid.GridFilters({
		encode : true,
		local : false,
		filters : [
				{
					type : 'string',
					dataIndex : 'centro.nomCcu',
					disabled : false
				},
				{
					type : 'string',
					dataIndex : 'produto.desPro',
					disabled : false
				},
				{
					type : 'string',
					dataIndex : 'codPro',
					disabled : false
				},
				{
					type : 'string',
					dataIndex : 'setor',
					options : [ 'Administrativo', 'Almoxarifado', 'Comercial',
							'Contabilidade', 'Financeiro', 'Faturamento',
							'Informática', 'Jurídico', 'Monitoramento',
							'Operacional', 'Recursos Humanos', 'Tv Cidade',
							'Vivo' ],
					disabled : false
				} ]
	});

	var cmReq = new Ext.grid.ColumnModel({
		columns : [ new Ext.grid.RowNumberer(), sm, {
			header : 'Cód.',
			width : 50,
			dataIndex : 'id',
			sortable : true,
			hidden : true
		}, {
			header : 'Produto',
			width : 70,
			dataIndex : 'codPro',
			sortable : true,
			hidden : false,
			filterable : true
		}, {
			header : 'Competência',
			dataIndex : 'cmpReq'
		}, {
			header : 'Material',
			dataIndex : 'produto.desPro',
			width : 180,
			id : 'desPro',
			sortable : true,
			filterable : true
		}, {
			header : 'Vlr. Uni.',
			dataIndex : 'preUni',
			renderer : 'brMoney',
			width : 60,
			xtype : 'numbercolumn',
			align : 'right',
			format : '0.00'
		}, {
			header : 'Qtd. Pedida',
			dataIndex : 'qtdEme',
			width : 70,
			xtype : 'numbercolumn',
			align : 'right',
			format : '0.00'
		}, {
			header : 'Apr. Mês Ant.',
			dataIndex : 'qtdAprMesAnt',
			width : 70,
			align : 'right',
			format : '0.00'
		}, {
			header : 'Qtd. Aprov.',
			dataIndex : 'qtdApr',
			width : 70,
			align : 'right',
			format : '0.00',
			renderer : pctChange,
			editor : {
				xtype : 'numberfield',
				allowBlank : false,
				allowNegative : false,
				allowDecimals : true,
				id : 'fQtdApr',
				name : 'qtdApr',
				msgTarget : 'under'
			}
		}, {
			header : 'Un. Med.',
			dataIndex : 'uniMed',
			width : 50
		}, {
			header : 'Observações',
			dataIndex : 'obsEme'
		}, {
			header : 'Solicitante',
			width : 100,
			dataIndex : 'codUsu',
			sortable : true,
			id : 'usuSol'
		}, {
			header : 'Setor',
			width : 100,
			dataIndex : 'setor',
			sortable : true,
			id : 'fSetor',
			filterable : true
		}, {
			header : 'Unidade',
			width : 100,
			dataIndex : 'centro.nomCcu',
			sortable : true,
			id : 'nomCcu',
			filterable : true
		}, {
			header : 'Situação',
			width : 100,
			dataIndex : 'desSit',
			sortable : true,
			id : 'fDesSit'
		} ]
	});

	var gridReq = new Ext.grid.EditorGridPanel(
			{
				store : stReq,
				cm : cmReq,
				sm : sm,
				id : 'gridReqME',
				height : 325,
				autoExpandColumn : 'desPro',
				title : 'Requisições de ' + descricao,
				frame : true,
				clicksToEdit : 1,
				loadMask : true,
				layout : 'fit',
				plugins : [ filters ],
				viewConfig : {
					getRowClass : MudaCorReq,
					autoFill : true,
					forceFit : true
				},
				tbar : [
						{
							text : 'Aprovar',
							iconCls : 'icnAdd',
							handler : function() {
								aprovar(tipoMaterial);
							}
						},
						"-",
						{
							text : 'Imprimir',
							iconCls : 'icnPrt',
							handler : function() {
							}
						},
						"-",
						{
							text : 'Mostrar aprovados: '
									+ (listAprovados ? 'Sim' : 'Não'),
							enableToggle : true,
							handler : function(button, state) {
								listAprovados = (listAprovados === true) ? false
										: true;
								var text = 'Mostrar aprovados: '
										+ (listAprovados ? 'Sim' : 'N�o');
								stReq.setBaseParam('listAprovados',
										listAprovados);
								button.setText(text);
								gridReq.getStore().reload();
							}
						} ],
				bbar : new Ext.PagingToolbar({
					pageSize : 25,
					store : stReq,
					displayInfo : true,
					displayMsg : 'Exibindo itens de {0} até {1} de {2}',
					emptyMsg : "Nenhum item encontrado",
					id : 'sPag'
				})
			});

	var win = new Ext.Window({
		title : 'Aprovação de requisições de ' + descricao,
		renderTo : 'pnCenter',
		width : 830,
		height : 400,
		layout : 'fit',
		border : false,
		modal : true,
		maximizable : true,
		items : [ gridReq ]
	});
	win.show();

	stReq.on('load', function(st, records, opts) {
		Ext.each(records, function(record) {
			/**
			 * verificar a situa��o da requisi��o. Se estiver com situa��o
			 * 1(digitado), sugerir para o campo qtdApr o valor do campo qtdEme
			 */
			if (record.data.idSit == 1) {
				record.data.qtdApr = record.data.qtdEme;
				record.commit();
			}
		})
	});

	stReq.setDefaultSort('produto.desPro', 'ASC');

	stReq.load({
		params : {
			start : 0,
			limit : 25
		}
	});
}

function aprovar(tipoMaterial) {
	myMask.msg = "Aguarde, aprovando requisição(oes)...";
	myMask.show();
	var st = Ext.getCmp('gridReqME').store;
	st.proxy.setApi('update', 'requisicaoEstoque!aprovar.action?tipoMaterial='
			+ tipoMaterial);
	st.on('save', function(st, bc, dt) {
		st.reload();
		myMask.hide();
		// desmarcar a op��o para marcar todos os itens do SM
		var hd = Ext.fly(Ext.getCmp('gridReqME').getView().innerHd).child(
				'div.x-grid3-hd-checker');
		hd.removeClass('x-grid3-hd-checker-on');
	});
	st.save();
}

function verificaPeriodo() {
	// Data de realiza��o das requisi��es � at� dia 15/05/2010 para requisi��es
	// da compet�ncia 06/2010
	dataAtual = new Date();
	mesAtual = dataAtual.getMonth() + 2;// Janeiro = 0, somar + 1 por causa do
	// explicado acima
	if (mesAtual == 13)
		mesAtual = 1;
	diaAtual = dataAtual.getDate();
	mesCmp = (Ext.getCmp('fCmpReq').getValue()).format('m/Y');
	mesCmp = mesCmp.substring(0, 2);
	periodoAberto = false;
	if (diaAtual <= diaBloqueio) {
		if (mesCmp >= mesAtual)
			periodoAberto = true;
	} else {
		if (mesCmp > mesAtual)
			periodoAberto = true;
	}
	return periodoAberto;
}
/*******************************************************************************
 * Busca e Retorna o dia que o sistema passa a bloquear requisicoes de materiais
 * para o periodo aberto
 ******************************************************************************/
function getDiaBloqueioRequisicao() {
	Ext.Ajax.request({
		url : 'configuraSistema!lePropriedade.action',
		success : function(result, request) {
			diaBloqueio = parseInt(result.responseText);
		},
		failure : function(result, request) {
			alert(result.responseText);
		},
		params : {
			propriedade : "data_fechamento_periodo_requisicao"
		}
	});
}

function novoRegistro(store, tipoMaterial) {
	var grid = Ext.getCmp('gridReqME');
	grid.stopEditing();
	var idEmpresa = 0;
	if (tipoMaterial == "LI")
		idEmpresa = 23;
	else
		idEmpresa = 3;
	var rec = new store.recordType({
		"qtdEme" : "",
		"obsEme" : "",
		"produto" : {
			"id" : {
				"empresa" : {
					"id" : idEmpresa
				},
				"codPro" : "0000"
			},
			"preUni" : 0.0
		},
		"id" : 0,
		"cmpReq" : (Ext.getCmp('fCmpReq').getValue()).format('m/Y'),
		"setor" : Ext.getCmp('fSet').getValue(),
		"sitEme" : "Digitado",
		"centro" : {
			"id" : parseInt(Ext.getCmp('fCen').getValue())
		}
	});
	Ext.getCmp('fMat').store.setBaseParam('produto.empresa.id', idEmpresa);
	store.insert(store.getCount(), rec);
	grid.getSelectionModel().selectRow(store.getCount() - 1);
	grid.startEditing(store.getCount() - 1, grid.getColumnModel().getIndexById(
			'desPro'));
}

function listaAcompanhamentoMensal() {
	var formConsReq = new Ext.form.FormPanel({
		labelWidth : 70,
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		defaultType : 'textfield',
		items : [ new Ext.form.DateField({
			name : 'cmpReq',
			width : 80,
			allowBlank : false,
			id : 'fCmpReq',
			msgTarget : 'under',
			format : 'm/Y',
			fieldLabel : 'Competência'
		}) ]
	});

	var windowConfPesqReq = new Ext.Window(
			{
				title : 'Parametrização da consulta de requisições',
				width : 400,
				height : 300,
				minWidth : 350,
				minHeight : 200,
				layout : 'fit',
				plain : true,
				bodyStyle : 'padding: 5px',
				closeAction : 'close',
				buttonAlign : 'center',
				items : formConsReq,
				buttons : [ {
					text : 'Executar',
					handler : function() {
						window.location = 'requisicaoEstoque!listaAcompanhamentoMensal.action?cmpReq='
								+ Ext.getCmp('fCmpReq').getValue()
										.format('m/Y');
					}
				} ]
			});
	windowConfPesqReq.show();
	windowConfPesqReq.center();
}

function listaDistribuicaoMensal() {
	var storeTipMat = criaStoreReader('item', 'produto!listAllTipoMat.action',
			[ 'id', 'name' ], 'id', 'storeTipMat');

	var formConsReq = new Ext.form.FormPanel({
		labelWidth : 80,
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		defaultType : 'textfield',
		items : [ new Ext.form.DateField({
			name : 'cmpReq',
			width : 80,
			allowBlank : false,
			id : 'fCmpReq',
			msgTarget : 'under',
			format : 'm/Y',
			fieldLabel : 'Competência'
		}), {
			xtype : 'combo',
			fieldLabel : 'Tipo de material',
			hiddenName : 'tipoMaterial',
			store : storeTipMat,
			valueField : 'id',
			displayField : 'name',
			typeAhead : true,
			mode : 'remote',
			triggerAction : 'all',
			emptyText : 'Selecione o tipo de material...',
			selectOnFocus : true,
			width : 170,
			id : 'fTipMat',
			allowBlank : false,
			forceSelection : true
		} ]
	});

	var windowConfPesqReq = new Ext.Window(
			{
				title : 'Parametrização do protocolo de distribuição',
				width : 400,
				height : 300,
				minWidth : 350,
				minHeight : 200,
				layout : 'fit',
				plain : true,
				bodyStyle : 'padding: 5px',
				closeAction : 'close',
				buttonAlign : 'center',
				items : formConsReq,
				buttons : [ {
					text : 'Executar',
					handler : function() {
						url = 'requisicaoEstoque!listaDistribuicaoMensal.action?cmpReq='
								+ Ext.getCmp('fCmpReq').getValue()
										.format('m/Y')
								+ '&tipoMaterial='
								+ Ext.get('tipoMaterial').getValue();
						window.location = url;
					}
				} ]
			});
	windowConfPesqReq.show();
	windowConfPesqReq.center();
}