function novaSolicitacaoCompra() {
	var formSolCompras = new Ext.form.FormPanel( {
		labelWidth : 75,
		url : 'solicitacaoCompra!gravar.action',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		maximizable : true,
		//reader : criaLeitorSol(),
		id : 'formSolCompras'
	});
	var winSolCompra = new Ext.Window( {
		title : 'Solicitação de compras',
		width : 800,
		height : 400,
		layout : 'fit',
		border : false,
		id : 'wSolCompras',
		items : [ formSolCompras ],
		maximizable : true,
		maximized : true,
		closable : true,
		renderTo : 'pnCenter',
		buttons : [ {
			text : 'Gravar',
			id : 'btGravar',
			iconCls : 'icnGra',
			handler : function() {
			}
		} ]
	});
	winSolCompra.show();
	winSolCompra.center();
}