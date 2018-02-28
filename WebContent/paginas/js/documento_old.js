function geraTelaCadastroDocumento() {

	var tree = new Ext.tree.TreePanel({
		useArrows : true,
		autoScroll : true,
		animate : true,
		enableDD : true,
		containerScroll : true,
		border : false,
		dataUrl : 'paginas/js/testeTree.txt',
		root : {
			nodeType : 'async',
			text : 'Ext JS',
			draggable : false,
			id : 'source'
		}
	});
	
	var treeEditor = new Ext.tree.TreeEditor(tree, {}, {
	    cancelOnEsc: true,
	    completeOnEnter: true,
	    selectOnFocus: true,
	    allowBlank: false
	});
	
	treeEditor.on("complete", function(node) {
	    alert(node.startValue + ' -> ' + node.editNode.text);
	});
	
	/**treeEditor.on('beforestartedit', function(ed, boundEl, value) {
	    if (ed.editNode.leaf)
	      return false;
	});**/


	var fpDoc = new Ext.form.FormPanel({
		url : 'documento!gravar.action',
		labelWidth : 75,
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : 350,
		id : 'fpDoc',
		items : [ tree ]
	});
	var campos = [ 'id', 'nomDoc', 'docPai' ];
	var stDoc = criaStoreReader('documento', 'documento!listAll.action',
			campos, 'id');

	var wnDoc = new Ext.Window(
			{
				title : 'SGQ - Sistema de Gestão da Qualidade',
				width : 605,
				height : 505,
				layout : 'fit',
				border : false,
				id : 'wnDoc',
				items : [ fpDoc ],
				defaultButton : 'bGravar',
				maximizable : true,
				closable : false,
				buttons : [
						{
							text : 'Gravar',
							iconCls : 'icnGra',
							id : 'bGravar',
							handler : function() {
								simple.form
										.submit({
											waitMsg : 'Aguarde, gravando árvore de documentos',
											success : function(frm, act) {
												Ext.Msg
														.show({
															title : 'Sucesso',
															msg : act.result.msg,
															buttons : Ext.MessageBox.OK,
															icon : Ext.MessageBox.INFO
														});
												gravou = true;
											},
											failure : function(form, action) {
												Ext.Msg
														.show({
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
									Ext.Msg
											.show({
												title : 'Abandonar alterações?',
												msg : 'Você está fechando a tela sem salvar. Deseja abandonar as alterações e sair?',
												buttons : Ext.Msg.YESNOCANCEL,
												icon : Ext.MessageBox.QUESTION,
												fn : function(button) {
													if (button == 'yes')
														wnDoc.destroy();
												}
											});
								} else {
									wnDoc.destroy();
								}
							}
						} ]
			});

	wnDoc.show();
	wnDoc.center();

	tree.getRootNode().expand();
}