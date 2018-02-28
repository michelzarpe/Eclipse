function geraTelaPesqCol() {
	campos = [ 'id', 'name', 'codLoc' ];
	var storeEmpresa = criaStoreReader('empresa', 'empresa!listAllXML.action',campos, 'id', 'storeEmpresa');

	var formTelaPesqCol = new Ext.form.FormPanel({
		labelWidth : 100,
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		defaultType : 'datefield',
		defaults : {
			width : 250
		},
		items : [ {
			fieldLabel : 'Nome ou parte do nome',
			name : 'colaborador.nomFun',
			id : 'fNome',
			xtype : 'textfield'
		}, {
			fieldLabel : 'Cadastro',
			name : 'colaborador.numCad',
			id : 'fNumCad',
			xtype : 'textfield'
		}, {
			fieldLabel : 'CPF',
			name : 'colaborador.numCpf',
			id : 'fCpf',
			xtype : 'textfield'
		},{
			xtype : 'combo',
			fieldLabel : 'Empresa',
			hiddenName : 'colaborador.empresa.id',
			store : storeEmpresa,
			valueField : 'id',
			displayField : 'name',
			typeAhead : true,
			mode : 'remote',
			triggerAction : 'all',
			emptyText : '...',
			selectOnFocus : true,
			id : 'fNomEmp',
			allowBlank : false
		}, {
			xtype : 'checkbox',
			boxLabel : 'Buscar nos demitidos',
			name : 'buscaDemitidos',
			id : 'bDem',
			inputValue : 'true',
			labelStyle : 'display : none'
		} ]
	});
	var windowTelaPesqCol = new Ext.Window({
		title : 'Parametrização da pesquisa de colaborador',
		width : 450,
		height : 250,
		minWidth : 350,
		minHeight : 250,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding: 5px',
		buttonAlign : 'center',
		items : formTelaPesqCol,
		renderTo : 'pnCenter',
		buttons : [ {
			text : 'Executar',
			handler : function() {
				var urlPesqCol = 'pesquisaColaborador!buscaXML.action';
				var leitorCol = criaLeitorCol();
				var stCol = new Ext.data.Store({
					remoteSort : true,
					totalProperty : 'totalCount',
					proxy : new Ext.data.HttpProxy({
						url : urlPesqCol,
						method : 'GET'
					}),
					reader : leitorCol,
					baseParams : formTelaPesqCol.getForm().getValues()
				});

				var sm = new Ext.grid.CheckboxSelectionModel({
					singleSelect : true
				});
				var sPag = Ext.getCmp('sPag');
				if (!sPag) {
					pagingBar = criaPaginador(stCol);
				}

				sGridCol = criaGridCol(sm, pagingBar, stCol);

				var tb = sGridCol.getTopToolbar();
				tb.add({
					text : 'Ficha Admissional',
					iconCls : 'icnFic',
					tooltip : 'Imprime ficha admissional do colaborador',
					handler : imprimeFicha,
					id : 'btImpFic'
				});

				if ((tipoUsuario == "ASI") || (tipoUsuario == "AAD")) {
					tb.add({
						text : 'Excluir cadastro',
						iconCls : 'icnExc',
						tooltip : 'Excluir cadastro do colaborador',
						handler : excluiAdmissao,
						id : 'btExcAdm'
					});
				}

				var win = new Ext.Window({
					title : 'Resultado da pesquisa de colaborador',
					width : 830,
					height : 480,
					layout : 'fit',
					border : false,
					modal : true,
					renderTo : 'pnCenter',
					maximizable : true,
					items : [ sGridCol ],
					listeners : {
						'show' : function() {
						}
					}
				});
				win.show();
				win.center();

				stCol.setDefaultSort('col_nomFun', 'DESC');
				stCol.load({
					params : {
						start : 0,
						limit : 25
					}
				});
			}

		} ]
	});
	windowTelaPesqCol.show();
	windowTelaPesqCol.center();
}
