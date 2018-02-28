function abreConsultaReq(tipoMaterial, descricao) {
	var dataHoje = new Date();
	dataHoje.setMonth(dataHoje.getMonth() + 1);

	var formConsReq = new Ext.form.FormPanel({
				labelWidth : 70,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				defaultType : 'textfield',
				items : [new Ext.form.DateField({
							name : 'cmpReq',
							width : 80,
							allowBlank : false,
							id : 'fCmpReq',
							msgTarget : 'under',
							format : 'm/Y',
							fieldLabel : 'Competência'
						})]
			});

	var windowConfPesqReq = new Ext.Window({
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
		buttons : [{
			text : 'Executar',
			handler : function() {
				var url = 'requisicaoEstoque!listByExample.action?tipoMaterial='+ tipoMaterial;
				var rWriter = new Ext.data.JsonWriter({
							encode : true,
							writeAllFields : true
						});
				var cmpReq = Ext.getCmp('fCmpReq').getValue().format('m/Y');
				var storeReq = new Ext.data.GroupingStore({
							reader : getReaderStoreReq(),
							sortInfo : {
								field : 'produto.desPro',
								direction : "ASC"
							},
							groupField : 'codUsu',
							url : url,
							writer : rWriter,
							autoSave : false,
							autoLoad : false,
							baseParams : {
								'requisicaoEstoque.cmpReq' : cmpReq
							}
						});

				var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
				var cmReq = new Ext.grid.ColumnModel({
							columns : [new Ext.grid.RowNumberer(), sm, {
										header : 'N.',
										dataIndex : 'id',
										sortable : true,
										width : 20
									}, {
										header : 'Comp.',
										dataIndex : 'cmpReq',
										sortable : true
									}, {
										header : 'Cód. Produto',
										dataIndex : 'codPro',
										sortable : true
									}, {
										header : 'Desc. Produto',
										dataIndex : 'produto.desPro',
										sortable : true
									}, {
										header : 'Qtd.Req.',
										dataIndex : 'qtdEme',
										sortable : true
									}, {
										header : 'Qtd.Apr.',
										dataIndex : 'qtdApr',
										sortable : true
									}, {
										header : 'Situação',
										width : 100,
										dataIndex : 'sitEme',
										sortable : true
									}, {
										header : 'Usuário',
										dataIndex : 'codUsu',
										sortable : true
									}, {
										header : 'Data',
										dataIndex : 'datReq',
										sortable : true
									}]
						});
				var gridReq = new Ext.grid.GridPanel({
					store : storeReq,
					loadMask : true,
					cm : cmReq,
					sm : sm,
					tbar : new Ext.Toolbar({
								id : 'tBarGridReq'
							}),
					id : 'gridReqs',
					autoExpandColumn : 'desPro',
					view : new Ext.grid.GroupingView({
						groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Requisições" : "Requisição"]})',
						startCollapsed : false,
						groupByText : 'Agrupar por este campo',
						getRowClass : MudaCorReq,
						autoFill : true,
						forceFit : true
					})
				});
				var tb = gridReq.getTopToolbar();
				tb.add({
					text : 'Imprimir espelho',
					iconCls : 'icnFic',
					tooltip : 'Imprime espelho da requisição',
					handler : function() {
						window.location = 'requisicaoEstoque!imprimirRequisicao.action?cmpReq='
								+ cmpReq + '&tipoMaterial=' + tipoMaterial;
					},
					id : 'btImpFic'
				});
				var win = new Ext.Window({
							title : 'Resultado da consulta de requisições',
							width : 830,
							height : 480,
							layout : 'fit',
							border : false,
							modal : true,
							items : [gridReq],
							maximizable : true,
							renderTo : document.body
						});
				win.show();
				storeReq.setDefaultSort('produto.desPro', 'ASC');
				storeReq.load({
							params : {
								start : 0,
								limit : 25
							}
						});
			}
		}]
	});

	windowConfPesqReq.show();
	windowConfPesqReq.center();
}
