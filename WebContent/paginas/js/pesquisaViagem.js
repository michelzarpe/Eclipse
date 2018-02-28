function geraTelaPesqVia() {
	
	
	var formTelaPesqVia = new Ext.form.FormPanel({
		labelWidth : 130,
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		defaultType : 'textfield',
		defaults : {
			width : 215
		},
		items : [ {
			fieldLabel : 'Nome ou parte do nome do colaborador',
			name : 'viagem.colaborador.nomFun',
			id : 'colaborador.nomFun',
			xtype : 'textfield',
			anchor : '100%'
		}, {
			fieldLabel : 'Data saída',
			name : 'viagem.datSai',
			id : 'datSai',
			xtype : 'datefield'
		} ]
	});

	var windowTelaPesqVia = new Ext.Window({
		title : 'Parametrização da pesquisa de viagens',
		width : 450,
		height : 250,
		minWidth : 350,
		minHeight : 250,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding: 5px',
		buttonAlign : 'center',
		renderTo : 'pnCenter',
		items : formTelaPesqVia,
		buttons : [ {
			text : 'Executar',
			handler : function() {
				var leitorVia = criaLeitorViagem();
				var stVia = new Ext.data.Store({
					remoteSort : true,
					totalProperty : 'totalCount',
					proxy : new Ext.data.HttpProxy({
						url : 'viagem!listByExample.action',
						method : 'POST'
					}),
					reader : leitorVia,
					baseParams : formTelaPesqVia.getForm().getValues()
				});
				stVia.setDefaultSort('viagem.id', 'DESC');
				var sm = new Ext.grid.CheckboxSelectionModel({
					singleSelect : true
				});
				var pagingBar = new Ext.PagingToolbar({
					pageSize : 20,
					store : stVia,
					displayInfo : true,
					displayMsg : 'Exibindo viagens de {0} até {1} de {2}',
					emptyMsg : "Nenhuma viagem encontrada",
					id : 'sPag'
				});
				var tb = new Ext.Toolbar({
					id : 'tBarGridVia',
					items : [ {
						text : 'Excluir',
						iconCls : 'icnExc',
						tooltip : 'Excluir viagem',
						handler : excluiViagem,
						id : 'btExcVia'
					} ]
				});
				var gridVia = new Ext.grid.GridPanel({
					store : stVia,
					loadMask : true,
					cm : new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(),
							sm, {
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
							}, {
								header : 'Usuário',
								dataIndex : 'viagem.usuario.nomFun'
							} ]),
					sm : sm,
					tbar : tb,
					bbar : pagingBar,
					height : 350,
					id : 'sGridVia',
					viewConfig : {
						forceFit : true,
						getRowClass : MudaCorVia
					},
					frame : true,
					iconCls : 'icon-grid'
				});
				gridVia.getSelectionModel().on('rowselect',
						function(sm, rowIdx, r) {
							geraListaAdtDes(r, gridVia.getStore());
						});
				var win = new Ext.Window({
					title : 'Resultado da consulta de viagens',
					width : 830,
					height : 480,
					layout : 'fit',
					border : false,
					modal : true,
					items : [ gridVia ],
					maximizable : true,
					renderTo : 'pnCenter',
					id: 'pnResConVia'
				});
				win.show();
				stVia.setDefaultSort('id', 'DESC');
				stVia.load({
					params : {
						start : 0,
						limit : 25
					}
				});
			}
		} ]
	});
	windowTelaPesqVia.show();
	windowTelaPesqVia.center();
}
function alteraViagem() {
	var linhaSelecionada = Ext.getCmp('sGridVia').getSelectionModel()
			.getSelected();
	if (linhaSelecionada) {
		if (linhaSelecionada.get('viagem.situacao') == "AB") {
			geraTelaCadastroViagem('viagem!buscaViagem.action?viagem.id='
					+ linhaSelecionada.get('viagem.id'));
		} else {
			Ext.Msg
					.show({
						title : 'Aviso',
						msg : 'Todos os adiantamentos e despesas desta viagem já foram fechados com acerto. Ainda assim deseja alterar esta viagem?',
						buttons : Ext.MessageBox.YESNO,
						animEl : 'sGridVia',
						icon : Ext.MessageBox.QUESTION,
						fn : function(button) {
							if (button == 'yes') {
								geraTelaCadastroViagem('viagem!buscaViagem.action?viagem.id='
										+ linhaSelecionada.get('viagem.id'));
							}
						}
					});
		}
	} else {
		Ext.Msg.show({
			title : 'Aviso',
			msg : 'Selecione uma viagem',
			buttons : Ext.MessageBox.OK,
			animEl : 'sGridVia',
			icon : Ext.MessageBox.ERROR
		});
	}
}
function geraListaAdtDes(r, st) {
	var stAdt = new Ext.data.Store({
		remoteSort : true,
		totalProperty : 'totalCount',
		proxy : new Ext.data.HttpProxy(
				{
					url : 'viagem!listByExample.action?viagem.id='
							+ r.get('viagem.id'),
					method : 'POST'
				}),
		reader : criaLeitorAdiantamento()
	});
	stAdt.setDefaultSort('id.id', 'ASC');
	var stDes = new Ext.data.Store({
		remoteSort : true,
		totalProperty : 'totalCount',
		proxy : new Ext.data.HttpProxy(
				{
					url : 'viagem!listByExample.action?viagem.id='
							+ r.get('viagem.id'),
					method : 'POST'
				}),
		reader : criaLeitorDespesaViagem()
	});
	var gridAdiantamentos = criaGridAdt(r, stAdt);
	var gridDespesas = criaGridDes(r, stDes);
	var janelaDetalhes = criaJanelaDetalhes(
			[ gridAdiantamentos, gridDespesas ], r);
	janelaDetalhes.show();
	janelaDetalhes.center();
	janelaDetalhes.doLayout();
	stAdt.load();
	stDes.load();
}
