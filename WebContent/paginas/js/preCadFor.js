function geraTelaPreCadastroFornecedor(cgcCpf, tipPes) {
	var lastArgument = arguments[arguments.length - 1];
	var retorno = false;
	var storeCidades = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'cidade!listEstados.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'map.items'
						}, [{
									name : 'sigUFS'
								}, {
									name : 'nomUFS'
								}])
			});
	storeCidades.load();
	var simple = new Ext.form.FormPanel({
				url : 'fornecedor!gravaPreCadastro.action',
				labelWidth : 75,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				width : 250,
				defaultType : 'combo',
				id : 'fSimple',
				defaults : {
					allowBlank : false
				},
				items : [{
							xtype : 'radiogroup',
							fieldLabel : 'Tipo pessoa',
							id : 'rdTipFor',
							items : [{
										boxLabel : 'Física',
										name : 'fornecedor.tipFor',
										inputValue : 'F'
									}, {
										boxLabel : 'Jurídica',
										name : 'fornecedor.tipFor',
										inputValue : 'J'
									}]
						}, {
							xtype : 'cnpjfield',
							fieldLabel : 'CNPJ',
							name : 'fornecedor.cgcCpf',
							vtype : 'cnpj',
							id : 'cnpj',
							listeners : {
								change : function(e, novo, velho) {
									var cnpjFornecedor = e.getValue();
									cnpjFornecedor = cnpjFornecedor.replace(".", "");
									cnpjFornecedor = cnpjFornecedor.replace(".", "");
									cnpjFornecedor = cnpjFornecedor.replace("/", "");
									cnpjFornecedor = cnpjFornecedor.replace("-", "");
									cnpj = parseFloat(cnpjFornecedor);
								}
							}
						}, {
							xtype : 'cpffield',
							fieldLabel : 'CPF',
							name : 'fornecedor.cgcCpf',
							vtype : 'cpf',
							id : 'cpf',
							listeners : {
								change : function(e, novo, velho) {
									var cpfFornecedor = e.getValue();
									cpfFornecedor = cpfFornecedor.replace(".","");
									cpfFornecedor = cpfFornecedor.replace(".","");
									cpfFornecedor = cpfFornecedor.replace("-","");
									cpf = parseFloat(cpfFornecedor);
								}
							}
						}, {
							xtype : 'textfield',
							fieldLabel : "Nome",
							name : "fornecedor.nomFor",
							width : 300,
							maxLength : 100
						}, {
							xtype : 'textfield',
							fieldLabel : "Apelido",
							name : "fornecedor.apeFor",
							width : 150,
							maxLength : 50
						}, {
							xtype : 'textfield',
							fieldLabel : "Endereço",
							name : "fornecedor.endFor",
							width : 300,
							maxLength : 100
						}, {
							xtype : 'textfield',
							fieldLabel : "Cidade",
							name : "fornecedor.cidFor",
							width : 300,
							maxLength : 60
						}, {
							id : 'statesCombo',
							store : storeCidades,
							displayField : 'nomUFS',
							valueField : 'sigUFS',
							hiddenName : 'fornecedor.sigUfs',
							typeAhead : true,
							mode : 'local',
							fieldLabel : 'Estado',
							anchor : '70%',
							forceSelection : true,
							triggerAction : 'all',
							emptyText : 'Selecione um estado...',
							selectOnFocus : true
						}]
			});
	Ext.getCmp('rdTipFor').on('change', function(rdGroup, rdChecked) {
				if (rdChecked.inputValue == 'F') {
					Ext.getCmp('cpf').enable();
					Ext.getCmp('cnpj').disable();
				} else if (rdChecked.inputValue == 'J') {
					Ext.getCmp('cpf').disable();
					Ext.getCmp('cnpj').enable();
				}
			});
	if (tipPes == 'J') {
		Ext.getCmp('cnpj').value = cgcCpf;
	} else if (tipPes == 'F') {
		Ext.getCmp('cpf').value = cgcCpf;
	}
	var win = new Ext.Window({
		title : 'Pré-cadastro de fornecedor',
		width : 450,
		height : 300,
		layout : 'fit',
		border : false,
		id : 'fJanelaPreCadFor',
		items : [simple],
		defaultButton : 'bGravar',
		maximizable : true,
		closable : true,
		renderTo : 'pnCenter',
		buttons : [{
			text : 'Gravar',
			iconCls : 'icnGra',
			id : 'bGravar',
			handler : function() {
				var cpfFornecedor = Ext.getCmp('cpf').getValue();
				cpfFornecedor = cpfFornecedor.replace(".", "");
				cpfFornecedor = cpfFornecedor.replace(".", "");
				cpfFornecedor = cpfFornecedor.replace("-", "");
				cpf = parseFloat(cpfFornecedor);
				Ext.getCmp('cpf').setValue(cpfFornecedor);
				Ext.Ajax.request({
							url : 'fornecedor!gravaPreCadastro.action',
							method : 'POST',
							params : simple.form.getValues(),
							success : function(result, request) {
								var jsonData = Ext.util.JSON
										.decode(result.responseText);
								var boolResult = jsonData.success;
								var resultMessage = jsonData.msg;
								if (boolResult == true) {
									Ext.Msg.show({
												title : 'Sucesso',
												msg : resultMessage,
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.INFO
											});
									retorno = true;
								} else {
									Ext.Msg.show({
												title : 'Falha',
												msg : resultMessage,
												buttons : Ext.MessageBox.OK,
												icon : Ext.MessageBox.ERROR
											});
									retorno = false;
								}
							},
							failure : function(result, request) {
								Ext.Msg.show({
											title : 'Falha',
											msg : 'Falha ao tentar acessar o servidor',
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.ERROR
										});
								retorno = false;
							}
						});
			}
		}]
	});
	win.show();
	win.center();
	win.on('close', function(p) {
				lastArgument.gravou.call(this, retorno);
				win.destroy();
			});
}