function montaTelaUsuario(url) {
	campos = ['colaboradorId', {
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
			}];
	var storeCol = criaStoreReader('colaborador','colaborador!listAllXML.action', campos,'colaboradorId', 'storeCol');
	var storeGer = criaStoreReader('colaborador','colaborador!listAllXML.action', campos,'colaboradorId', 'storeGer');
	var colaboradorTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{col__nomFun}</span></h3><h2>C�digo: {numCad} Empresa: {empresaId} - {nomEmp}</h2></div></tpl>');
	var storeTipUsu = criaStoreReader('item', 'tipoUsuario!listAllXML.action',	['id', 'name'], 'id', 'storeTipUsu');
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
			});
	stCentros.setDefaultSort('id', 'ASC');**/
	storeTipUsu.load();

	Ext.apply(Ext.form.VTypes, {
				password : function(val, field) {
					if (field.initialPassField) {
						var pwd = Ext.getCmp(field.initialPassField);
						return (val == pwd.getValue());
					}
					return true;
				},
				passwordText : 'Senhas digitadas não conferem'
			});

	var tb = new Ext.Toolbar({
				id : 'tBarGridUsu',
				items : [{
							text : 'Adicionar outro Usuário',
							iconCls : 'icnAdd',
							handler : function() {
								Ext.getCmp('fUsuario').form.reset();
								Ext.getCmp('fAcao').setValue("INSERT");
							}
						}]
			});
	var simple = new Ext.form.FormPanel({
				url : 'usuario!gravar.action',
				labelWidth : 75,
				reader : criaLeitorUsuario(),
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				width : 350,
				height : 200,
				defaultType : 'textfield',
				id : 'fUsuario',
				defaults : {
					allowBlank : false
				},
				items : [{
							xtype : 'hidden',
							fieldLabel : "Ação",
							name : "acao",
							width : 100,
							id : 'fAcao'
						}, {
							xtype : 'combo',
							store : storeCol,
							id : 'fCol',
							hiddenName : 'usuario.id',
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
							xtype : 'textfield',
							fieldLabel : "Login",
							name : "usuario.codUsu",
							width : 100,
							maxLength : 15
						}, {
							xtype : 'textfield',
							fieldLabel : "E-mail",
							name : "usuario.email",
							width : 300,
							vtype : 'email'
						}, {
							fieldLabel : '',
							xtype : 'checkbox',
							labelSeparator : '',
							boxLabel : 'Recebe workflow por e-mail',
							name : 'usuario.recebeAviso',
							labelSeparator : '',
							inputValue : true,
							width : 250
						}, {
							xtype : 'combo',
							fieldLabel : 'Tipo',
							hiddenName : 'usuario.tipUsu',
							store : storeTipUsu,
							valueField : 'id',
							displayField : 'name',
							typeAhead : true,
							mode : 'remote',
							triggerAction : 'all',
							emptyText : 'Selecione um tipo de usuário...',
							selectOnFocus : true,
							width : 250,
							id : 'fTipUsu',
							allowBlank : false,
							forceSelection : true
						}, {
							fieldLabel : 'Senha',
							name : 'novaSenha',
							id : 'fNovSen',
							vtype : 'password',
							inputType : 'password'
						}, {
							fieldLabel : 'Confirmar senha',
							name : 'usuario.senUsu',
							id : 'fConSen',
							vtype : 'password',
							initialPassField : 'fNovSen',
							inputType : 'password'
						}, {
							xtype : 'combo',
							store : storeGer,
							id : 'fGer',
							hiddenName : 'usuario.gerente.id',
							fieldLabel : 'Gerente',
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
						}, /**{
							xtype : 'fieldset',
							title : 'Centros permitidos',
							autoHeight : true,
							layout : 'column',
							id : 'fsRateio',
							items : [new Ext.form.CheckboxGroup({
										fieldLabel : '',
										hideLabel : true,
										labelSeparator : '',
										itemCls : 'x-check-group-alt',
										columns : 5,
										id : 'cbgCentros',
										items : [{
													hideLabel : true,
													hidden : true,
													disabled : true
												}],
										listeners : {
											afterrender : function(e) {
												criaCheckCentros(stCentros);
											}
										}
									})]
						}**/]
			});
	var win = new Ext.Window({
		frame : true,
		width : 605,
		height : 420,
		layout : 'fit',
		border : false,
		id : 'fJanelaCadUsu',
		items : [simple],
		defaultButton : 'bGravar',
		maximizable : true,
		closable : false,
		renderTo : 'pnCenter',
		defaultType : 'textfield',
		buttons : [{
					text : 'Gravar',
					iconCls : 'icnGra',
					id: 'bGravar',
					handler : function() {
						simple.form.submit({
									waitMsg : 'Aguarde, gravando usuário',
									success : function(frm, act) {
										Ext.Msg.show({
													title : 'Sucesso',
													msg : act.result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO
												});
									},
									failure : function(form, action) {
										Ext.Msg.show({
													title : 'Falha',
													msg : action.result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO
												});
									}
								})
					}
				}, {
					text : 'Cancelar',
					iconCls : 'icnCan',
					handler : function() {
						Ext.Msg.show({
									title : 'Sair?',
									msg : 'Deseja realmente sair?',
									buttons : Ext.Msg.YESNOCANCEL,
									icon : Ext.MessageBox.QUESTION,
									fn : function(button) {
										if (button == 'yes')
											win.destroy();
									}
								});
					}
				}, {
					text : 'Novo',
					iconCls : 'icnAdd',
					handler : function() {
						Ext.getCmp('fUsuario').form.reset();
						Ext.getCmp('fAcao').setValue("INSERT");
					}
				}]
	});

	win.on('show', function(e) {
				if (url)
					carregaFormUsuario(url);
				else
					Ext.getCmp('fAcao').setValue("INSERT");
			});
	win.show();
	win.center();
}
function carregaFormUsuario(url) {
	var formUsu = Ext.getCmp('fUsuario');
	var storeCol = Ext.getCmp('fCol').store;
	var storeGer = Ext.getCmp('fGer').store;
	Ext.getCmp('fAcao').setValue("UPDATE");
	if (url) {
		formUsu.getForm().load({
			url : url,
			waitMsg : 'Aguarde, carregando dados',
			success : function(form, action) {
				if (action.response.responseXML.childNodes.length > 0) {
					if (Ext.getCmp('fCol').getValue() != "") {
						storeCol.proxy.conn.url = 'colaborador!listAllXML.action?colaborador.id='
								+ Ext.getCmp('fCol').getValue();
						storeCol.addListener('load', function() {
									var codigo = storeCol.getById(Ext.getCmp('fCol').getValue()).get('colaboradorId');
									Ext.getCmp('fCol').setValue(codigo);
								});
						storeCol.reload({
							callback : function(r, options, success) {
								storeCol.purgeListeners();
								storeCol.proxy.conn.url = 'colaborador!listAllXML.action';
							}
						});
					}
					if (Ext.getCmp('fGer').getValue() != "") {
						storeGer.proxy.conn.url = 'colaborador!listAllXML.action?colaborador.id='
								+ Ext.getCmp('fGer').getValue();
						storeGer.addListener('load', function() {
									var codigo = storeGer.getById(Ext
											.getCmp('fGer').getValue())
											.get('colaboradorId');
									Ext.getCmp('fGer').setValue(codigo);
								});
						storeGer.reload({
							callback : function(r, options, success) {
								storeGer.purgeListeners();
								storeGer.proxy.conn.url = 'colaborador!listAllXML.action';
							}
						});
					}
					//stCentros.loadData(action.response.responseXML);
					//populaCentros(stCentros);
				}
			}
		});
	}
}
function montaTelaListagemUsuario() {
	var urlPesqUsu = 'usuario!listAllXML.action?usuario.id=';
	var leitorUsu = criaLeitorUsuario();
	var stUsu = new Ext.data.Store({
				remoteSort : true,
				totalProperty : 'totalCount',
				proxy : new Ext.data.HttpProxy({
							url : urlPesqUsu,
							method : 'GET'
						}),
				reader : leitorUsu
			});
	stUsu.setBaseParam('notFindDemitidos', true);
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	var sPag = Ext.getCmp('sPag');
	if (!sPag) {
		pagingBar = criaPaginador(stUsu);
	}
	sGridUsu = criaGridUsu(sm, pagingBar, stUsu);
	var win = new Ext.Window({
		title : 'Resultado da pesquisa de usuários',
		width : 830,
		height : 400,
		layout : 'fit',
		border : false,
		modal : true,
		renderTo : 'pnCenter',
		maximizable : true,
		items : [sGridUsu],
		tbar : ['-', {
			text : 'Alterar',
			iconCls : 'icnCom',
			handler : function() {
				usuarioSelecionado = sGridUsu.getSelectionModel().getSelected();
				if (usuarioSelecionado) {
					montaTelaUsuario('usuario!listByExample.action?usuario.id='
							+ usuarioSelecionado.get('usuario.id'));
				} else {
					Ext.Msg.show({
								title : 'Erro',
								msg : 'Selecione um usuário!',
								buttons : Ext.MessageBox.OK,
								animEl : 'sGridUsu',
								icon : Ext.MessageBox.ERROR
							});
				}
			}
		}, '-', {
			text : 'Novo usuário',
			iconCls : 'icnAdd',
			handler : function() {
				montaTelaUsuario('');
			}
		},
		"-",
		new Ext.form.Checkbox({
			fieldLabel : '',
			boxLabel : 'Exibir usuários inativos',
			name : 'notFindDemitidos',
			id : 'notFindDemitidos',
			width : 290,
			listeners : {
				check : function(chk, marcado) {
					if (marcado == true) {
						stUsu.setBaseParam(
								'notFindDemitidos', false);
						stUsu.reload();
					} else {
						stUsu.setBaseParam(
								'notFindDemitidos', true);
						stUsu.reload();
					}
				}
			}
		})],
		listeners : {
			'show' : function() {
			}
		}
	});
	win.show();
	win.center();
	stUsu.setDefaultSort('nomFun', 'DESC');
	stUsu.load({
				params : {
					start : 0,
					limit : 25
				}
			});
}