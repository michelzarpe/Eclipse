function abreConsultaSol() {
	var frmConSol = new Ext.form.FormPanel({
				labelWidth : 70,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				defaultType : 'textfield',
				items : [new Ext.form.DateField({
									name : 'solicitacaoCompra.datSol',
									width : 80,
									allowBlank : false,
									id : 'fDatSol',
									msgTarget : 'under',
									fieldLabel : 'Data da solicitação'
								}), {
							name : 'solicitacaoCompra.id',
							fieldLabel : 'Número',
							xtype : 'textfield'
						}]
			});

	var windowConfPesqSol = new Ext.Window({
		title : 'Parametrização da consulta de solicitações de serviço',
		width : 400,
		height : 300,
		minWidth : 350,
		minHeight : 200,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding: 5px',
		closeAction : 'close',
		buttonAlign : 'center',
		items : frmConSol,
		buttons : [{
			text : 'Executar',
			handler : function() {
				var url = 'solicitacaoCompra!listByExample.action';
				var storeSol = new Ext.data.GroupingStore({
							reader : getReaderStoreSolCom(),
							sortInfo : {
								field : 'solicitacaoCompra.id',
								direction : "ASC"
							},
							groupField : 'solicitacaoCompra.usuSol.codUsu',
							url : url,
							autoLoad : false,
							baseParams : frmConSol.getForm().getValues()
						});

				var sm = new Ext.grid.CheckboxSelectionModel({
							singleSelect : true
						});
				var cmSol = new Ext.grid.ColumnModel({
					columns : [new Ext.grid.RowNumberer(), sm, {
								header : 'N.',
								dataIndex : 'solicitacaoCompra.id',
								sortable : true,
								width : 20
							}, {
								dataIndex : 'solicitacaoCompra.datSol',
								header : 'Data da solicitação'
							}, {
								dataIndex : 'solicitacaoCompra.cplPro',
								header : 'Descrição do serviço'
							}, {
								dataIndex : 'solicitacaoCompra.usuSol.codUsu',
								header : 'Solicitante'
							}, {
								dataIndex : 'solicitacaoCompra.usuSol.centro.nomCcu',
								header : 'Centro'
							}]
				});
				var gridSol = new Ext.grid.GridPanel({
					store : storeSol,
					loadMask : true,
					cm : cmSol,
					sm : sm,
					autoExpandColumn : 'solicitacaoCompra.cplPro',
					view : new Ext.grid.GroupingView({
						groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Solicitações" : "Solicitação"]})',
						startCollapsed : false,
						groupByText : 'Agrupar por este campo',
						getRowClass : MudaCorSol,
						autoFill : true,
						forceFit : true
					})
				});
				var win = new Ext.Window({
							title : 'Resultado da consulta de solicitações',
							width : 830,
							height : 480,
							layout : 'fit',
							border : false,
							modal : true,
							items : [gridSol],
							maximizable : true,
							renderTo : document.body
						});
				win.show();
				storeSol.setDefaultSort('solicitacaoCompra.id', 'ASC');
				storeSol.load({
							params : {
								start : 0,
								limit : 25
							}
						});
			}
		}]
	});

	windowConfPesqSol.show();
	windowConfPesqSol.center();
}