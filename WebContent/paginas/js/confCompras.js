/**
 * Exibe tela de configuraï¿½ï¿½o do mï¿½dulo de compras
 */
function exibeTelaConfCompras() {
	var frmConf = new Ext.form.FormPanel({
		url : 'preCadastro!gravar.action?colaborador.local.id.tabOrg=2',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : 860,
		height : 372,
		defaultType : 'textfield',
		id : 'frmConf',
		// tbar : new Ext.Toolbar({}),
		defaults : {
			allowBlank : false
		},
		items : [ {
			xtype : 'textfield',
			labelWidth : 300,
			fieldLabel : "Data de fechamento do período de requisição mensal:",
			name : "data_fechamento_periodo_requisicao",
			width : 100,
			id : 'propDatFecPerReq'
		} ,  {
			xtype : 'textfield',
			labelWidth : 300,
			fieldLabel : "IP do servidor SapiensSID:",
			name : "servidor_sapienssid",
			width : 100,
			id : 'propIpSapSid'
		}, {
			xtype : 'textfield',
			labelWidth : 300,
			fieldLabel : "Empresa para processamento de material de expediente:",
			name : "empresa_baixa_material_expediente",
			width : 100,
			id : 'propEmpBaiMatExp'
		} ]
	});

	var winConf = new Ext.Window({
		title : 'Configuração do módulo de compras',
		width : 878,
		height : 400,
		layout : 'anchor',
		plain : true,
		border : false,
		id : 'wConfComp',
		constrain : true,
		// tbar : new Ext.Toolbar({}),
		items : [ frmConf ],
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
				Ext.Ajax.request({
					url : 'configuraSistema!gravaPropriedade.action',
					success : function(result, request) {
						alert(result.responseText);
					},
					failure : function(result, request) {
						alert(result.responseText);
					},
					params : {
						propriedade : "data_fechamento_periodo_requisicao",
						valor : Ext.getCmp('propDatFecPerReq').getValue()
					}
				});
				Ext.Ajax.request({
					url : 'configuraSistema!gravaPropriedade.action',
					success : function(result, request) {
						alert(result.responseText);
					},
					failure : function(result, request) {
						alert(result.responseText);
					},
					params : {
						propriedade : "servidor_sapienssid",
						valor : Ext.getCmp('propIpSapSid').getValue()
					}
				});
				Ext.Ajax.request({
					url : 'configuraSistema!gravaPropriedade.action',
					success : function(result, request) {
						alert(result.responseText);
					},
					failure : function(result, request) {
						alert(result.responseText);
					},
					params : {
						propriedade : "empresa_baixa_material_expediente",
						valor : Ext.getCmp('propEmpBaiMatExp').getValue()
					}
				});
			}
		} ]
	});
	winConf.show();
	winConf.center();

	var vlrProp = "";
	Ext.Ajax.request({
		url : 'configuraSistema!lePropriedade.action',
		success : function(result, request) {
			vlrProp = result.responseText;
			Ext.getCmp('propDatFecPerReq').setValue(vlrProp);
		},
		failure : function(result, request) {
			alert(result.responseText);
		},
		params : {
			propriedade : "data_fechamento_periodo_requisicao"
		}
	});
	
	Ext.Ajax.request({
		url : 'configuraSistema!lePropriedade.action',
		success : function(result, request) {
			vlrProp = result.responseText;
			Ext.getCmp('propIpSapSid').setValue(vlrProp);
		},
		failure : function(result, request) {
			alert(result.responseText);
		},
		params : {
			propriedade : "servidor_sapienssid"
		}
	});
	
	Ext.Ajax.request({
		url : 'configuraSistema!lePropriedade.action',
		success : function(result, request) {
			vlrProp = result.responseText;
			Ext.getCmp('propEmpBaiMatExp').setValue(vlrProp);
		},
		failure : function(result, request) {
			alert(result.responseText);
		},
		params : {
			propriedade : "empresa_baixa_material_expediente"
		}
	});
}