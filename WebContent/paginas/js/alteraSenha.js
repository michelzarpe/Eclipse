function criaTelaAltSen() {
	Ext.apply(Ext.form.VTypes, {
				password : function(val, field) {
					if (field.initialPassField) {
						var pwd = Ext.getCmp(field.initialPassField);
						return (val == pwd.getValue());
					}
					return true;
				},
				passwordText : 'Senhas digitadas não conferem'
			});
	var formTelaAltSen = new Ext.form.FormPanel({
				labelWidth : 100,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				defaults : {
					width : 120,
					inputType : 'password'
				},
				defaultType : 'textfield',
				items : [{
							xtype : 'label',
							text : 'Usuario: ' + nomeUsuario
						}, {
							fieldLabel : 'Senha Atual',
							name : 'senhaAtual',
							id : 'fSenAtu',
							vtype : 'password'
						}, {
							fieldLabel : 'Nova Senha',
							name : 'novaSenha',
							id : 'fNovSen',
							vtype : 'password'
						}, {
							fieldLabel : 'Confirmar nova senha',
							name : 'confirmacaoSenha',
							id : 'fConSen',
							vtype : 'password',
							initialPassField : 'fNovSen'
						}]
			});
	var windowTelaAltSen = new Ext.Window({
		title : 'Informar nova senha',
		width : 300,
		height : 200,
		minWidth : 350,
		minHeight : 250,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding: 5px',
		buttonAlign : 'center',
		items : formTelaAltSen,
		buttons : [{
					text : 'Alterar',
					handler : function() {
						formTelaAltSen.form.submit({
									url : 'alteraSenha!alteraSenha.action',
									waitMsg : 'Aguarde, alterando senha',
									success : function(frm, act) {
										Ext.Msg.show({
													title : 'Sucesso',
													msg : act.result.msg,
													buttons : Ext.MessageBox.OK,
													fn : function() {
														windowTelaAltSen
																.close();
													},
													icon : Ext.MessageBox.INFO
												});
									},
									failure : function(form, action) {
										Ext.Msg.show({
													title : 'Falha',
													msg : action.result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO
												});
									}
								});
					}
				}]
	});
	windowTelaAltSen.show();
}