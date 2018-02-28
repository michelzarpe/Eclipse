function geraTelaFiltroDemitidos() {
	var storeCentro = criaStoreReader('centro', 'centro!listAllXML.action', [
					'id', 'nomCcu'], 'id', 'storeCentro');
	var formConf = new Ext.form.FormPanel({
				labelWidth : 100,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				defaultType : 'datefield',
				width : 350,
				defaults : {
					width : 175
				},
				items : [{
							fieldLabel : 'Data inicial',
							name : 'datIni',
							id : 'datIni',
							xtype : 'datefield',
							endDateField : 'datFin',
							allowBlank : false
						}, {
							fieldLabel : 'Data final',
							name : 'datFin',
							id : 'datFin',
							xtype : 'datefield',
							startDateField : 'datIni',
							allowBlank : false
						}, {
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
							width : 200,
							id : 'fCen',
							forceSelection : true
						}, {
							xtype : 'fieldset',
							title : 'Tipo de arquivo',
							autoHeight : true,
							width : 300,
							items : [{
										xtype : 'radiogroup',
										columns : 2,
										hideLabel : true,
										id : 'rgTipoArquivo',
										allowBlank : false,
										anchor : '90%',
										items : [{
													boxLabel : 'Excel',
													name : 'tipoArquivo',
													inputValue : 'xls'
												}, {
													boxLabel : 'Pdf',
													name : 'tipoArquivo',
													inputValue : 'pdf'
												}]
									}]
						}]
			});
	var windowConf = new Ext.Window({
		title : 'Parametrização do relatório de demitidos',
		width : 400,
		height : 300,
		minWidth : 350,
		minHeight : 200,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding: 5px',
		closeAction : 'close',
		buttonAlign : 'center',
		items : formConf,
		buttons : [{
			text : 'Executar',
			handler : function() {
				if (formConf.getForm().isValid()) {
					urlRel = 'colaborador!listDemitidos.action?datIni='
							+ Ext.get('datIni').dom.value + '&datFin='
							+ Ext.get('datFin').dom.value + '&tipoArquivo='
							+ Ext.getCmp('rgTipoArquivo').getValue().inputValue;
					if (Ext.getCmp('fCen').value != undefined)
						urlRel = urlRel + '&centro.id='
								+ Ext.getCmp('fCen').value;
					else
						urlRel = urlRel + '&centro.id=' + 0;
					if (Ext.getCmp('rgTipoArquivo').getValue().inputValue == 'pdf')
					   window.location = urlRel;
					else if (Ext.getCmp('rgTipoArquivo').getValue().inputValue == 'xls')
						window.location = urlRel;
				}
			}
		}]
	});
	windowConf.show();
	windowConf.center();
}
