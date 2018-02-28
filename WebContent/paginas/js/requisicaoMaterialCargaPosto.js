function novaRequisicaoMaterialCargaPosto() {
	Ext.form.Field.prototype.msgTarget = 'side';
	var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "Aguarde..."
			});
	var stReq = criaStoreReq("MC");

	var smReq = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});

	/** =========================MATERIAL============================================================================* */
	var materialTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{desPro}</span></h3><h2>Código: {produtoId}</h2></div></tpl>');
	var storeMat = criaStoreMat('produto!listAllMatExp.action?produto.tipPro=MC');

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
				editable : false,
				forceSelection : true
			});

	var storeMotivo = criaStoreReader('item', 'motivo!listMotivosReqMC.action',
			['id', 'name'], 'id');
	var cmReq = new Ext.grid.ColumnModel({
				columns : [new Ext.grid.RowNumberer(), smReq, {
							header : 'C�d.',
							width : 50,
							dataIndex : 'id',
							sortable : true,
							hidden : true
						}, {
							header : 'Produto',
							width : 50,
							dataIndex : 'codPro',
							sortable : true,
							hidden : false
						}, {
							header : 'Produto',
							dataIndex : 'codPro',
							width : 50,
							editor : cbMat,
							id : 'desPro',
							renderer : Ext.util.Format.comboRenderer(cbMat,
									stReq),
							fixed : true
						}, {
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
								allowDecimals : false,
								id : 'fQtdEme',
								name : 'qtdEme',
								minValue : 1,
								msgTarget : 'under'
							}
						}, {
							header : 'Observações',
							dataIndex : 'obsEme',
							editor : new Ext.form.TextArea({})
						}, {
							header : 'Solicitante',
							width : 100,
							dataIndex : 'codUsu',
							sortable : true,
							id : 'usuSol'
						}]
			});

	var gridReq = new Ext.grid.EditorGridPanel({
		region : 'south',
		split : true,
		width : 200,
		margins : '3 0 3 3',
		cmargins : '3 3 3 3',
		store : stReq,
		cm : cmReq,
		sm : smReq,
		clicksToEdit : 1,
		stripeRows : true,
		autoExpandColumn : 'desPro',
		title : 'Requisições pendentes de Material carga de posto',
		id : 'griMatCarPos',
		anchor : '50%',
		forceValidation : true,
		loadMask: true,
		tbar : [{
			text : 'Adicionar item',
			iconCls : 'icnAdd',
			handler : function() {
				if ((Ext.getCmp('fSup').getValue() > 0)
						&& (Ext.getCmp('fCli').getValue() > 0)
						&& (Ext.getCmp('fEmp').getValue() > 0)
						&& (Ext.getCmp('fMot').getValue() != '')) {
					gridReq.stopEditing();
					rec = new stReq.recordType({
								"qtdEme" : 0,
								"obsEme" : "teste",
								"produto" : {
									"id" : {
										"empresa" : {
											"id" : parseInt(Ext.getCmp('fEmp')
													.getValue())
										},
										"codPro" : "0000"
									}
								},
								"supervisor" : {
									"id" : parseInt(Ext.getCmp('fSup')
											.getValue())
								},
								"motReq" : Ext.getCmp('fMot').getValue(),
								"cliente" : {
									"id" : {
										"codFil" : parseInt(Ext.getCmp('fCli')
												.getValue()),
										"empresaId" : parseInt(Ext
												.getCmp('fEmp').getValue())
									}
								},
								"conCli" : Ext.getCmp('fCon').getValue(),
								"id" : 0
							});
					rec.commit();
					stReq.add(rec);
					gridReq.getView().refresh();
					gridReq.getSelectionModel().selectRow(0);
					gridReq.startEditing(stReq.getCount() + 1, 0);
					Ext.getCmp('fMat').store.setBaseParam('produto.empresa.id',
							Ext.getCmp('fEmp').getValue());
				} else {
					Ext.Msg.show({
						title : 'Alerta',
						msg : 'Preencha os campos Empresa, Supervisor, Cliente e Motivo antes de adicionar um item.',
						buttons : Ext.MessageBox.OK,
						animEl : 'griMatCarPos',
						icon : Ext.MessageBox.WARNING
					});
				}
			}
		}, "-", {
			text : 'Excluir',
			iconCls : 'icnExc',
			handler : function() {
				excluiRec('griMatCarPos');
			}
		}]
	});

	/** ===========================SUPERVISOR======================================================================* */
	var colaboradorTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{col__nomFun}</span></h3><h2>Código: {numCad} Empresa: {empresaId} - {nomEmp}</h2></div></tpl>');

	var storeSup = criaStoreReader('colaborador','colaborador!listAllSupervisoresXML.action', 
			        ['colaboradorId', {
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
					}], 'colaboradorId');
	/** ============================CLIENTE====================================================================* */

	var clienteTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{razSoc}</span></h3><h2>CGC/CPF: {cgcCpf}</h2></div></tpl>');

	var storeCli = criaStoreReader('cliente', 'cliente!listByExample.action', [
					{
						name : 'cliente.id.codFil',
						mapping : 'id > @codFil'
					}, {
						name : 'cliente.id.empresaId',
						mapping : 'id > @empresaId'
					}, {
						name : 'cliente.razSoc',
						mapping : '@razSoc'
					}, {
						name : 'razSoc',
						mapping : '@razSoc'
					}, {
						name : 'cliente.numCgc',
						mapping : '@numCgc'
					}, {
						name : 'cgcCpf',
						mapping : '@numCgc'
					}], 'storeCli');

	/** =========================EMPRESA===================================================================================* */
	var storeEmpresa = criaStoreReader('empresa', 'empresa!listAllXML.action',
			['id', 'name', 'codLoc'], 'id');

	var tb = new Ext.Toolbar({
				id : 'tBarGridReq',
				items : [{
							text : 'Nova',
							iconCls : 'icnAdd',
							handler : function() {
								Ext.getCmp('formMC').form.reset();
								Ext.getCmp('griMatCarPos').store.removeAll();
							}
						}]
			});

	var formMC = new Ext.form.FormPanel({
		region : 'center',
		split : false,
		margins : '3 3 3 0',
		labelWidth : 55,
		labelAlign : 'left',
		defaults : {
			autoScroll : true
		},
		autoHeight : true,
		url : 'requisicaoEstoque!gravar.action?tipoMaterial=MC',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		reader : stReq.reader,
		id : 'formMC',
		buttonAlign : 'center',
		anchor : '50%',
		monitorValid : true,
		items : [{
					layout : 'column',
					items : [{
								columnWidth : .33,
								layout : 'form',
								items : [{
											xtype : 'hidden',
											fieldLabel : 'Id da requisição',
											id : 'fIdReq',
											name : 'id',
											readOnly : true
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								items : [{
											xtype : 'hidden',
											fieldLabel : 'Número da requisição',
											id : 'fNumEme',
											name : 'numEme',
											readOnly : true
										}]
							}, {
								columnWidth : .2,
								layout : 'form',
								items : [new Ext.form.DateField({
											fieldLabel : 'Data da requisição',
											name : 'datReq',
											width : 100,
											id : 'fDatReq',
											hidden : true,
											hideLabel : true
										})]
							}]
				}, {
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							fieldLabel : 'Empresa',
							hiddenName : 'produto.empresa.id',
							store : storeEmpresa,
							hideLabel : false,
							valueField : 'id',
							displayField : 'name',
							typeAhead : true,
							mode : 'remote',
							triggerAction : 'all',
							emptyText : 'Selecione a empresa...',
							selectOnFocus : true,
							forceSelection : true,
							width : 250,
							id : 'fEmp',
							onSelect : function(record) {
								Ext.getCmp('fCli').clearValue();
								Ext.getCmp('fCli').lastQuery = null;
								storeCli.setBaseParam('cliente.empresa.id',
										record.get('id'));
								Ext.getCmp('fEmp')
										.setRawValue(record.data.name);
								Ext.getCmp('fEmp').setValue(record.id);
								Ext.getCmp('fEmp').collapse();
							}
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'combo',
									store : storeSup,
									hiddenName : 'supervisor.id',
									fieldLabel : 'Supervisor',
									displayField : 'colaborador.nomFun',
									valueField : 'colaboradorId',
									hideLabel : false,
									typeAhead : true,
									mode : 'remote',
									emptyText : 'Selecione um supervisor...',
									width : 250,
									triggerAction : 'all',
									id : 'fSup',
									tpl : colaboradorTpl,
									triggerClass : 'x-form-search-trigger',
									itemSelector : 'div.search-item',
									forceSelection : true,
									lastQuery : '',
									msgTarget : 'title',
									queryParam : 'colaborador.nomFun',
									listeners : {
										beforequery : function(qe) {
											delete qe.combo.lastQuery;
										}
									}
								}]
					}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .5,
								layout : 'form',
								items : [{
											xtype : 'combo',
											store : storeCli,
											hiddenName : 'cliente.id.codFil',
											fieldLabel : 'Cliente',
											displayField : 'cliente.razSoc',
											valueField : 'cliente.id.codFil',
											hideLabel : false,
											typeAhead : true,
											mode : 'remote',
											emptyText : 'Selecione um cliente...',
											width : 250,
											triggerAction : 'all',
											id : 'fCli',
											tpl : clienteTpl,
											triggerClass : 'x-form-search-trigger',
											itemSelector : 'div.search-item',
											forceSelection : true,
											lastQuery : '',
											msgTarget : 'title',
											queryParam : 'cliente.razSoc',
											listeners : {
												beforequery : function(qe) {
													delete qe.combo.lastQuery;
												}
											}
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'combo',
											fieldLabel : 'Motivo',
											hiddenName : 'motReq',
											store : storeMotivo,
											valueField : 'id',
											displayField : 'name',
											typeAhead : true,
											mode : 'remote',
											triggerAction : 'all',
											emptyText : 'Selecione um motivo...',
											width : 110,
											id : 'fMot',
											allowBlank : false,
											msgTarget : 'title',
											forceSelection : true,
											listeners : {
												beforequery : function(qe) {
													delete qe.combo.lastQuery;
												}
											}
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								items : [{
											fieldLabel : 'Contato',
											name : 'conCli',
											width : 110,
											msgTarget : 'title',
											xtype : 'textfield',
											id : 'fCon'
										}]
							}]
				}]
	});
	Ext.getCmp('fCli').on('blur', function(e) {
				if (e.getValue() != '')
					carregaFormMatCarPos();
			});

	stReq.on('beforesave', function(st, dt) {
				myMask.msg = "Aguarde, gravando requisição...";
				myMask.show();
			});
	stReq.on('save', function(st, bc, dt) {
				myMask.hide();
			});
	var winMC = new Ext.Window({
		title : 'Requisição de estoque',
		width : 800,
		height : 400,
		layout : 'border',
		plain : true,
		border : false,
		id : 'wReqEst',
		constrain : true,
		tbar : tb,
		items : [formMC, gridReq],
		maximizable : false,
		maximized : false,
		closable : true,
		resizable : false,
		renderTo : 'pnCenter',
		forceLayout : true,
		buttons : [{
			text : 'Gravar',
			id : 'btGravar',
			iconCls : 'icnGra',
			handler : function() {
				if (stReq.getModifiedRecords().length > 0) {
					if (validaReq('griMatCarPos') == true) {
						Ext.each(stReq.getModifiedRecords(), function(record) {
							record.data.produto.id.codPro = record.data.codPro;
							if (!record.isValid()) {
								Ext.Msg.show({
									title : 'Aviso',
									msg : 'Não foi possível enviar requisição. Verifique se todos os dados estão preenchidos.',
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
							}
						});
						var retorno = stReq.save();
					}
				}
			}
		}]

	});
	winMC.show();
	winMC.center();
}

function carregaFormMatCarPos() {
	Ext.getCmp('griMatCarPos').store.load({
		params : {
			'requisicaoEstoque.cliente.id.codFil' : Ext.getCmp('fCli')
					.getValue(),
			'requisicaoEstoque.cliente.id.empresaId' : Ext.getCmp('fEmp')
					.getValue()
		},
		callback : function(records, options, success) {
			if (success == true) {
				if (Ext.getCmp('griMatCarPos').store.getTotalCount() == 0)
					Ext.getCmp('griMatCarPos').store.removeAll();
				else {
					Ext.Msg.show({
						title : 'Aviso',
						msg : 'Já existe uma requisição aberta para este cliente.',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.INFO
					});
					Ext.getCmp('fSup').store.load({
						params : {
							id : records[0].data.supervisor.id
						},
						callback : function(recs, opts, suc) {
							if (suc == true)
								Ext
										.getCmp('fSup')
										.setValue(records[0].data.supervisor.id);
						}
					});
					Ext.getCmp('fCon').setValue(records[0].data.conCli);
					Ext.getCmp('fMot').store.load({
								callback : function(recs, opts, suc) {
									if (suc == true)
										Ext
												.getCmp('fMot')
												.setValue(records[0].data.motReq);
								}
							});

				}
			}
		}
	});
}
