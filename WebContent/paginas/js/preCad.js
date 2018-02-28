var storeCidadeLoc;
var storePosto;
var gravou = false;


function geraTelaPreCadastro() {
	var campos = [ 'id', 'name', 'codLoc' ];
	var storeEmpresa = criaStoreReader('empresa', 'empresa!listAllXML.action',campos, 'id');
	campos = [ 'id', 'name' ];
	var storeEscala = criaStoreReader('escala', 'escala!listAllXML.action', campos, 'id');
	var storeCargo = criaStoreReader('cargo', 'cargo!listAllXML.action', campos, 'id');
	
	campos = [ 'id', 'nomCcu' ];
	var storeCentro = criaStoreReader('centro', 'centro!listAllXML.action', campos, 'id');
	
	campos = [ {
		name : 'nomLoc',
		mapping : 'local > nomLoc',
		convert : function(v, rec) {
			var local = Ext.DomQuery.selectNode('local', rec);
			var codLoc = Ext.DomQuery.selectValue("codLoc", rec);
			return v + " - " + codLoc;
		}
	}, 'codLoc', {
		name : 'numLoc',
		mapping : 'local > id > numLoc'
	} ];
	var storeUnidade = criaStoreReader('hierarquia','local!listAllXML.action?nivel=2', campos, 'codLoc');
	var storeCliente = criaStoreReader('hierarquia','local!listAllXML.action?nivel=3', campos, 'codLoc');
	storeCidadeLoc = criaStoreReader('hierarquia','local!listAllXML.action?nivel=4', campos, 'codLoc');
	storePosto = criaStoreReader('hierarquia','local!listAllXML.action?nivel=5', campos, 'numLoc');
	var tb = new Ext.Toolbar( {id : 'tBarGridCad',items : [ {text : 'Adicionar outro Pré-Cadastro',	iconCls : 'icnAdd',
		handler : function() {
					Ext.getCmp('fSimple').form.reset();
			}
		} ]
	});
	
	var patt1=new RegExp("[0-9]{11}");	
	
	var simple = new Ext.form.FormPanel(
			{
				url : 'preCadastro!gravar.action?colaborador.local.id.tabOrg=2',
				labelWidth : 75,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				width : 350,
				defaultType : 'combo',
				id : 'fSimple',
				tbar : tb,
				defaults : {
					allowBlank : false
				},
				items : [
						{
							xtype : 'textfield',
							fieldLabel : "Nome",
							name : "colaborador.nomFun",
							width : 300
						},
						{
							xtype : 'textfield',
							fieldLabel : 'CPF',
							name : 'colaborador.numCpf',
							width : 100,
							id : 'cpf',
							width : 100,
							regex : patt1,
							regexText : 'Informe apenas números',
							listeners : {
								'blur' : {
									fn : function(field, newVal, oldVal) {
										VerificaCPF();
									}
								}
							},
							allowBlank : false
						},
						{
							xtype : "datefield",
							fieldLabel : "Admissão",
							name : "colaborador.datAdm",
							width : 100
						},
						{
							xtype : 'combo',
							fieldLabel : 'Empresa',
							hiddenName : 'colaborador.empresa.id',
							store : storeEmpresa,
							valueField : 'id',
							displayField : 'name',
							typeAhead : true,
							mode : 'remote',
							triggerAction : 'all',
							emptyText : 'Selecione uma empresa...',
							selectOnFocus : true,
							forceSelection : true,
							width : 350,
							id : 'fEmp',
							onSelect : function(record) {
								Ext.getCmp('fEmp').setRawValue(record.data.name);
								Ext.getCmp('fEmp').setValue(record.id);
								Ext.get('colaborador.empresa.id').value = record.id;
								var combo = Ext.getCmp('fUni');
								combo.store.proxy.conn.url = 'local!listAllXML.action?nivel=2';
								combo.store.baseParams = {nivelAnterior : record.data.codLoc};
								combo.clearValue();
								Ext.getCmp('fCli').clearValue();
								Ext.getCmp('fCid').clearValue();
								Ext.getCmp('fPos').clearValue();
								combo.lastQuery = null;
								Ext.getCmp('fEmp').collapse();
							}
						},
						{
							xtype : 'combo',
							fieldLabel : 'Escala',
							hiddenName : 'colaborador.escala.id',
							store : storeEscala,
							valueField : 'id',
							displayField : 'name',
							forceSelection : true,
							typeAhead : true,
							mode : 'remote',
							triggerAction : 'all',
							emptyText : 'Selecione uma escala...',
							selectOnFocus : true,
							width : 350,
							id : 'fEsc'
						},
						{
							xtype : 'combo',
							fieldLabel : 'Cargo',
							emptyText : 'Busca automática a partir de 4 caracteres...',
							hiddenName : 'colaborador.cargo.id.codCar',
							store : storeCargo,
							valueField : 'id',
							displayField : 'name',
							typeAhead : true,
							mode : 'remote',
							triggerAction : 'all',
							forceSelection : true,
							selectOnFocus : true,
							emptyText : 'Selecione um cargo...',
							selectOnFocus : true,
							width : 350,
							id : 'fCar',
							queryParam : 'cargo.titCar'
						},
						/**{
							xtype : 'combo',
							fieldLabel : 'Centro',
							hiddenName : 'colaborador.centro.id',
							store : storeCentro,
							valueField : 'id',
							displayField : 'nomCcu',
							typeAhead : true,
							forceSelection : true,
							mode : 'remote',
							triggerAction : 'all',
							emptyText : 'Selecione um centro...',
							selectOnFocus : true,
							width : 350,
							id : 'fCen'
						},**/
						{
							xtype : 'radiogroup',
							fieldLabel : 'Sexo',
							columns : [ 100, 100 ],
							vertical : true,
							items : [ {
								boxLabel : 'Feminino',
								name : 'colaborador.sexo',
								inputValue : 'F'
							}, {
								boxLabel : 'Masculino',
								name : 'colaborador.sexo',
								inputValue : 'M'
							} ]
						},
						criaComboLocal(storeUnidade, 'fUni', 'Unidade',	'unidadeLocId', 'codLoc', false),
						criaComboLocal(storeCliente, 'fCli', 'Cliente','clienteLocId', 'codLoc', false),
						criaComboLocal(storeCidadeLoc, 'fCid', 'Contrato/Cidade','cidadeLocId', 'codLoc', false),
						criaComboLocal(storePosto, 'fPos', 'Posto',	'colaborador.local.id.numLoc', 'numLoc', false) ]

			});
	var win = new Ext.Window(
			{
				title : 'Pré-cadastro de colaborador',
				width : 605,
				height : 505,
				layout : 'fit',
				border : false,
				id : 'fJanelaPreCad',
				items : [ simple ],
				defaultButton : 'bGravar',
				maximizable : true,
				closable : false,
				buttons : [
						{
							text : 'Gravar',
							iconCls : 'icnGra',
							id : 'bGravar',
							handler : function() {
								simple.form.submit( {
									waitMsg : 'Aguarde, gravando pré-cadastro',
									success : function(frm, act) {
										Ext.Msg.show( {
											title : 'Sucesso',
											msg : act.result.msg,
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.INFO
										});
										gravou = true;
									},
									failure : function(form, action) {
										Ext.Msg.show( {
											title : 'Falha',
											msg : action.result.msg,
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.INFO
										});
										gravou = false;
									}
								})
							}
						},
						{
							text : 'Cancelar',
							iconCls : 'icnCan',
							handler : function() {
								if (gravou == false) {
									Ext.Msg	.show({
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
						} ]
			});

	win.show();
	win.center();
}
function criaComboLocal(store, idComponente, label, hidName, campoValor,esconderTrigger) {
	var resultTpl = new Ext.XTemplate('<tpl for="."><div class="search-item">',	'<h3><span>{nomLoc}</span></h3></div></tpl>');
	var search = new Ext.form.ComboBox(
			{  
				labelWidth : 80,
				store : store,
				id : idComponente,
				hiddenName : hidName,
				fieldLabel : label,
				displayField : 'nomLoc',
				valueField : campoValor,
				typeAhead : true,
				mode : 'remote',
				itemSelector : 'div.search-item',
				emptyText : 'Busca automática a partir de 4 caracteres...',
				loadingText : 'Procurando...',
				pageSize : 10,
				width : 450,
				tpl : resultTpl,
				triggerClass : 'x-form-search-trigger',
				triggerAction : 'all',
				minChars : 4,
				allowBlank : false,
				hideTrigger : esconderTrigger,
				forceSelection : true,
				lastQuery : " ",
				listeners : {
					'select' : {
						fn : function(field, newVal, oldVal) {
							if (field.id == 'fUni') {
								var combo = Ext.getCmp('fCli');
								combo.store.proxy.conn.url = 'local!listAllXML.action?nivel=3';
								combo.store.baseParams = {
									nivelAnterior : newVal.id
								};
								combo.clearValue();
								Ext.getCmp('fCli').clearValue();
								Ext.getCmp('fCid').clearValue();
								Ext.getCmp('fPos').clearValue();
								combo.lastQuery = null;
							} else if (field.id == 'fCli') {
								var combo = Ext.getCmp('fCid');
								combo.store.proxy.conn.url = 'local!listAllXML.action?nivel=4';
								combo.store.baseParams = {
									nivelAnterior : newVal.id
								};
								combo.clearValue();
								Ext.getCmp('fCid').clearValue();
								Ext.getCmp('fPos').clearValue();
								combo.lastQuery = null;
							} else if (field.id == 'fCid') {
								var combo = Ext.getCmp('fPos');
								combo.store.proxy.conn.url = 'local!listAllXML.action?nivel=5';
								combo.store.baseParams = {
									nivelAnterior : newVal.id
								};
								combo.clearValue();
								combo.lastQuery = null;
							}
						}
					}
				},
				queryParam : 'nomLoc'
			});
	
	if (idComponente == 'fUni') {
		search.on('beforequery', function(qe) {
			if (Ext.getCmp('fEmpLoc'))
				campo = 'fEmpLoc'
			else
				campo = 'fEmp'
			if ((Ext.getCmp(campo).value == undefined) || (Ext.getCmp(campo).value == "")) {
				Ext.Msg.show( {
					title : 'Alerta',
					msg : 'Escolha antes a empresa.',
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.WARNING
				});
				qe.cancel = true;
			}
		});
	} else if (idComponente == 'fCli') {
			search.on('beforequery', function(qe) {
				if ((Ext.getCmp('fUni').value == undefined)	|| (Ext.getCmp('fUni').value == "")) {
					Ext.Msg.show( {
						title : 'Alerta',
						msg : 'Escolha antes a unidade.',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING
					});
					qe.cancel = true;
				}
			});
	} else if (idComponente == 'fCid') {
		search.on('beforequery', function(qe) {
			if ((Ext.getCmp('fCli').value == undefined)	|| (Ext.getCmp('fCli').value == "")) {
				Ext.Msg.show( {
					title : 'Alerta',
					msg : 'Escolha antes o cliente.',
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.WARNING
				});
				qe.cancel = true;
			}
		});
	} else if (idComponente == 'fPos') {
		search.on('beforequery', function(qe) {
			if ((Ext.getCmp('fCid').value == undefined)	|| (Ext.getCmp('fCid').value == "")) {
				Ext.Msg.show( {
					title : 'Alerta',
					msg : 'Escolha antes o contrato/cidade.',
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.WARNING
				});
				qe.cancel = true;
			}
		});
	}
	return search;
}