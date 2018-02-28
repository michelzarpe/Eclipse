function novaSolicitacaoCompraServico() {
	campos = ['id', 'name'];
	var storeCondicaoPgt = criaStoreReader('condicaoPagamento','condicaoPagamento!listAllCompras.action', campos, 'id', 'stConPgt');

	var stSolCom = criaStoreJSONSolCompra();

	var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "Aguarde..."
			});

	var frmOrc = new Ext.form.FormPanel({
				url : 'solicitacaoCompra!gravar.action',
				reader : getReaderStoreSolCom(),
				id : 'fSol',
				labelAlign : 'left',
				bodyStyle : 'padding:5px',
				items : [{
							layout : 'column',
							border : false,
							items : [{
										columnWidth : .3,
										border : false,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													fieldLabel : 'N Solicitação.',
													name : 'solicitacaoCompra.id',
													id : 'solId',
													width : 75
												}]
									}, {
										columnWidth : .3,
										border : false,
										layout : 'form',
										items : [{
													xtype : 'displayfield',
													fieldLabel : 'Data da solicitação',
													name : 'solicitacaoCompra.datSol'
												}]
									}]
						}, {
							xtype : 'textarea',
							fieldLabel : 'Detalhamento do serviço',
							name : 'solicitacaoCompra.cplPro',
							anchor : '95%',
							value : 'Serviço de conserto de porta',
							height : 50
						}, {
							xtype : 'tabpanel',
							plain : true,
							activeTab : 0,
							height : 235,
							deferredRender : false,
							id : 'tabPanelCot',
							defaults : {
								bodyStyle : 'padding:10px'
							},
							items : [montaTabCotacao(0, storeCondicaoPgt),
									montaTabCotacao(1, storeCondicaoPgt),
									montaTabCotacao(2, storeCondicaoPgt)]
						}]
			});

	Ext.getCmp('solId').on('blur', function(el) {
		var idSol = el.getValue();
		if (idSol != "") {
			/** fazer o load para a solicita��o informada pelo c�digo * */
			frmOrc.form.load({
						url : 'solicitacaoCompra!consulta.action',
						params : {
							'solicitacaoCompra.id' : idSol
						},
						waitMsg : 'Aguarde, carregando dados da solicitação...',
						success : function(form, action) {
							var cotacao = action.result.data.cotacao[0];
							if (cotacao != undefined)
								loadCotacoes(cotacao, 0);
							cotacao = action.result.data.cotacao[1];
							if (cotacao != undefined)
								loadCotacoes(cotacao, 1);
							cotacao = action.result.data.cotacao[2];
							if (cotacao != undefined)
								loadCotacoes(cotacao, 2);
						}
					})
		}
	});

	var winSolCompraServico = new Ext.Window({
		title : 'Solicitação de compra de serviço',
		width : 800,
		layout : 'anchor',
		plain : true,
		border : false,
		id : 'wSolComSer',
		constrain : true,
		tbar : new Ext.Toolbar({}),
		items : [frmOrc],
		maximizable : true,
		maximized : false,
		closable : true,
		resizable : true,
		renderTo : 'pnCenter',
		forceLayout : true,
		buttons : [{
			text : 'Gravar',
			id : 'btGravar',
			iconCls : 'icnGra',
			handler : function() {
				frmOrc.form.submit({
					submitEmptyText : false,
					waitMsg : 'Aguarde, gravando solicitação...',
					success : function(form, action) {
						var idSol = action.result.id;
						if ((isNaN(idSol) == true) || (idSol == 0)) {
							Ext.Msg.show({
										title : 'Falha',
										msg : "Não foi possível gravar a solicitação",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						} else {
							Ext.Msg.show({
										title : 'Sucesso',
										msg : action.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO
									});
							Ext.getCmp('solId').setValue(idSol);
						}
					},
					failure : function(form, action) {
						if (action.failureType === Ext.form.Action.CONNECT_FAILURE) {
							Ext.Msg.alert('Erro', 'Status:'
											+ action.response.status + ': '
											+ action.response.statusText);
						} else {
							Ext.Msg.show({
										title : 'Falha',
										msg : action.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
					}
				});
			}
		}]
	});
	frmOrc.form.on('beforeaction', function(frm, act) {
		if (act.type == 'submit') {
			var cnpj1 = Ext.getCmp('cnpj0').getValue();
			var cnpj2 = Ext.getCmp('cnpj1').getValue();
			var cnpj3 = Ext.getCmp('cnpj2').getValue();
			var cpf1 = Ext.getCmp('cpf0').getValue();
			var cpf2 = Ext.getCmp('cpf1').getValue();
			var cpf3 = Ext.getCmp('cpf2').getValue();
			var isAtivoCot2 = true;
			var isAtivoCot3 = true;

			if ((cnpj2 == "") || (cnpj2 == "000.000.000/0000-00")) {
				Ext.getCmp('cnpj1').disable();
				Ext.getCmp('txtIdFor[1]').disable();
				isAtivoCot2 = false;
			}
			if ((cnpj3 == "") || (cnpj3 == "000.000.000/0000-00")) {
				Ext.getCmp('cnpj2').disable();
				Ext.getCmp('txtIdFor[2]').disable();
				isAtivoCot3 = false;
			}

			if ((cpf2 == "") || (cpf2 == "000.000.000-00")) {
				Ext.getCmp('cpf1').disable();
				Ext.getCmp('txtIdFor[1]').disable();
				if (isAtivoCot2 != true)
					isAtivoCot2 = false;
			} else {
				isAtivoCot2 = true;
				Ext.getCmp('txtIdFor[1]').enable();
			}
			if ((cpf3 == "") || (cpf3 == "000.000.000-00")) {
				Ext.getCmp('cpf2').disable();
				Ext.getCmp('txtIdFor[2]').disable();
				if (isAtivoCot3 != true)
					isAtivoCot3 = false;
			} else {
				isAtivoCot3 = true;
				Ext.getCmp('txtIdFor[2]').enable();
			}
			if (isAtivoCot2 == false) {
				var elementos = Ext.getCmp('tabCotacao1').items.items[0].items.items;
				disableElements(elementos);
				Ext.getCmp('preCot1').disable();
				Ext.getCmp('przEnt1').disable();
			}
			if (isAtivoCot3 == false) {
				var elementos = Ext.getCmp('tabCotacao2').items.items[0].items.items;
				disableElements(elementos);
				Ext.getCmp('preCot2').disable();
				Ext.getCmp('przEnt2').disable();
			}
			Ext.getCmp('cnpj0').setValue(removeMascaraCNPJ(cnpj1));
			Ext.getCmp('cnpj1').setValue(removeMascaraCNPJ(cnpj2));
			Ext.getCmp('cnpj2').setValue(removeMascaraCNPJ(cnpj3));

			Ext.getCmp('cpf0').setValue(removeMascaraCPF(cpf1));
			Ext.getCmp('cpf1').setValue(removeMascaraCPF(cpf2));
			Ext.getCmp('cpf2').setValue(removeMascaraCPF(cpf3));
		}
	});
	winSolCompraServico.show();
	winSolCompraServico.center();
}

function montaTabCotacao(seq, storeCondicaoPgt) {
	if (seq == 0)
		permiteVazio = true
	else
		permiteVazio = true;
	var tabCotacao = {
		title : 'Cotação ' + (seq + 1),
		layout : 'column',
		border : false,
		id : 'tabCotacao' + seq,
		items : [{
			columnWidth : .5,
			border : false,
			layout : 'form',
			items : [{
						xtype : 'radiogroup',
						fieldLabel : 'Tipo pessoa',
						id : 'rdTipFor' + seq,
						items : [{
									boxLabel : 'Física',
									name : 'tipFor' + seq,
									inputValue : 'F'
								}, {
									boxLabel : 'Jurídica',
									name : 'tipFor' + seq,
									inputValue : 'J'
								}],
						listeners : {
							'change' : function(rdGroup, rdChecked) {
								if (rdChecked.inputValue == 'F') {
									Ext.getCmp('cpf' + seq).enable();
									Ext.getCmp('cnpj' + seq).disable();
								} else if (rdChecked.inputValue == 'J') {
									Ext.getCmp('cpf' + seq).disable();
									Ext.getCmp('cnpj' + seq).enable();
								}
							}
						}
					}, {
						xtype : 'textfield',
						fieldLabel : 'Cód. Forn.',
						name : 'cotacoes[' + seq + '].fornecedor.id',
						allowBlank : permiteVazio,
						id : 'txtIdFor[' + seq + ']',
						width : 50,
						hidden : true,
						hideLabel : true
					}, {
						xtype : 'cnpjfield',
						fieldLabel : 'CNPJ Fornecedor',
						name : 'cotacoes[' + seq + '].fornecedor.cnpj',
						allowBlank : permiteVazio,
						id : 'cnpj' + seq,
						vtype : 'cnpj',
						listeners : {
							change : function(e, novo, velho) {
								var cnpjFornecedor = removeMascaraCNPJ(e
										.getValue());
								buscaDadosFornecedor(cnpjFornecedor, seq, 'J');
							}
						}
					}, {
						xtype : 'cpffield',
						fieldLabel : 'CPF',
						name : 'cotacoes[' + seq + '].fornecedor.cgcCpf',
						vtype : 'cpf',
						id : 'cpf' + seq,
						allowBlank : permiteVazio,
						listeners : {
							blur : function(e, novo, velho) {
								myMask.msg = "Aguarde, pesquisando fornecedor..."
								myMask.show();
								var cpfFornecedor = removeMascaraCPF(e
										.getValue());
								buscaDadosFornecedor(cpfFornecedor, seq, 'F');
							}
						}
					}, {
						fieldLabel : 'Quantidade cotada',
						name : 'cotacoes[' + seq + '].qtdCot',
						xtype : 'numberfield',
						allowBlank : permiteVazio,
						minValue : 1,
						allowDecimals : true,
						allowNegative : false,
						width : 100,
						id : 'qtdCot' + seq
					}, {
						layout : 'column',
						border : false,
						id : 'colPrePra' + seq,
						items : [{
									columnWidth : .4,
									border : false,
									layout : 'form',
									items : {
										fieldLabel : 'Preço',
										name : 'cotacoes[' + seq + '].preCot',
										xtype : 'numberfield',
										allowBlank : permiteVazio,
										minValue : 1,
										allowDecimals : true,
										allowNegative : false,
										width : 45,
										decimalSeparator : '.',
										id : 'preCot' + seq
									}
								}, {
									columnWidth : .6,
									border : false,
									layout : 'form',
									items : {
										fieldLabel : 'Prazo de entrega',
										name : 'cotacoes[' + seq + '].przEnt',
										xtype : 'datefield',
										allowBlank : permiteVazio,
										width : 80,
										id : 'przEnt' + seq
									}
								}]
					}, {
						xtype : 'combo',
						fieldLabel : 'Cond.Pagamento',
						hiddenName : 'cotacoes[' + seq
								+ '].condicaoPagamento.id',
						store : storeCondicaoPgt,
						valueField : 'id',
						displayField : 'name',
						typeAhead : true,
						mode : 'remote',
						triggerAction : 'all',
						emptyText : 'Selecione uma condição de pagamento...',
						selectOnFocus : true,
						width : 150,
						id : 'fConPgt[' + seq + ']',
						allowBlank : permiteVazio,
						forceSelection : true
					}, {
						fieldLabel : 'Observações',
						name : 'cotacoes[' + seq + '].obsCot',
						xtype : 'textarea',
						allowBlank : permiteVazio,
						anchor : '95%',
						height : 30,
						id : 'obsCot' + seq
					}, {
						xtype : 'hidden',
						fieldLabel : 'Id cotação',
						name : 'cotacoes[' + seq + '].id',
						id : 'idCot' + seq
					}]
		}, {
			columnWidth : .5,
			border : false,
			layout : 'form',
			items : [new Ext.Panel({
						id : 'pnInfoFor[' + seq + ']',
						padding : '15px',
						items : [{
									xtype : 'displayfield',
									fieldLabel : 'Fornecedor',
									value : 'Fornecedor:',
									id : 'lbNomFor[' + seq + ']'
								}, {
									xtype : 'displayfield',
									fieldLabel : 'Endereçoo',
									value : 'Endereço: ',
									id : 'lbEndFor[' + seq + ']'
								}, {
									xtype : 'displayfield',
									fieldLabel : 'Cidade',
									value : 'Cidade: ',
									id : 'lbCidFor[' + seq + ']'
								}, {
									xtype : 'displayfield',
									fieldLabel : 'Fone',
									value : 'Fone: ',
									id : 'lbFonFor[' + seq + ']'
								}, {
									xtype : 'displayfield',
									fieldLabel : 'Fax',
									value : 'Fax: ',
									id : 'lbFaxFor[' + seq + ']'
								}, {
									xtype : 'displayfield',
									fieldLabel : 'Email',
									value : 'Email: ',
									id : 'lbIntNet[' + seq + ']'
								}]
					})]
		}],
		listeners : {
			'activate' : function(tab) {
				if ((tab.id == 'tabCotacao1') || (tab.id == 'tabCotacao2')) {
					var elementos = Ext.getCmp(tab.id).items.items[0].items.items;
					enableElements(elementos);
					Ext.getCmp('preCot' + seq).enable();
					Ext.getCmp('przEnt' + seq).enable();
				}
			}
		}
	}
	return tabCotacao;
}

function buscaDadosFornecedor(cgcCpf, seq, tipPes) {
	Ext.Ajax.request({
		url : 'fornecedor!findByExample.action?fornecedor.cgcCpf=' + cgcCpf,
		success : function(result, request) {
			if (result.responseXML.childNodes[0].childNodes[1] != undefined) {
				Ext
						.getCmp('txtIdFor[' + seq + ']')
						.setValue(result.responseXML.childNodes[0].childNodes[1].attributes[8].nodeValue);
				Ext
						.getCmp('lbNomFor[' + seq + ']')
						.setValue('Fornecedor: '
								+ result.responseXML.childNodes[0].childNodes[1].attributes[0].nodeValue);
				Ext
						.getCmp('lbEndFor[' + seq + ']')
						.setValue('Endere�o  : '
								+ result.responseXML.childNodes[0].childNodes[1].attributes[2].nodeValue);
				Ext
						.getCmp('lbCidFor[' + seq + ']')
						.setValue('Cidade    : '
								+ result.responseXML.childNodes[0].childNodes[1].attributes[3].nodeValue);
				Ext
						.getCmp('lbFonFor[' + seq + ']')
						.setValue('Fone      : '
								+ result.responseXML.childNodes[0].childNodes[1].attributes[5].nodeValue);
				Ext
						.getCmp('lbFaxFor[' + seq + ']')
						.setValue('Fax       : '
								+ result.responseXML.childNodes[0].childNodes[1].attributes[6].nodeValue);
				Ext
						.getCmp('lbIntNet[' + seq + ']')
						.setValue('E-mail    : '
								+ result.responseXML.childNodes[0].childNodes[1].attributes[7].nodeValue);
				myMask.hide();
			} else {
				Ext.Msg.show({
					title : 'Fornecedor não encontrado. ',
					msg : 'Fornecedor não encontrado no banco de dados. Deseja efetuar um pré-cadastro?',
					buttons : Ext.Msg.YESNOCANCEL,
					fn : function(btn) {
						if (btn == 'yes') {
							geraTelaPreCadastroFornecedor(cgcCpf, tipPes, {
										gravou : function(resultado) {
											if (resultado == true) {
												buscaDadosFornecedor(cgcCpf,
														seq, tipPes);
												Ext.getCmp('qtdCot' + seq)
														.focus();
											}
										}
									});
						}
					},
					icon : Ext.MessageBox.QUESTION
				});
				myMask.hide();
			}
		},
		failure : function(result, request) {
			myMask.hide();
			Ext.Msg.show({
						title : 'Falha',
						msg : 'Falha ao tentar acessar o servidor',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
		}
	});
}

function removeMascaraCPF(cpf) {
	cpf = cpf.replace(".", "");
	cpf = cpf.replace(".", "");
	cpf = cpf.replace("-", "");
	return cpf;
}

function removeMascaraCNPJ(cnpj) {
	cnpj = cnpj.replace(".", "");
	cnpj = cnpj.replace(".", "");
	cnpj = cnpj.replace("/", "");
	cnpj = cnpj.replace("-", "");
	return cnpj;
}

function disableElements(elementos) {
	for (var i = 0; i < elementos.length; i++) {
		elementos[i].disable();
	}
}

function enableElements(elementos) {
	for (var i = 0; i < elementos.length; i++) {
		elementos[i].enable();
	}
}

function loadCotacoes(cotacao, seq) {
	Ext.getCmp('qtdCot' + seq).setValue(cotacao.qtdCot);
	Ext.getCmp('preCot' + seq).setValue(cotacao.preCot);
	Ext.getCmp('przEnt' + seq).setValue(cotacao.przEnt);
	Ext.getCmp('obsCot' + seq).setValue(cotacao.obsCot);
	Ext.getCmp('fConPgt[' + seq + ']').store.load({
				callback : function(records, options, success) {
					if (success == true) {
						Ext.getCmp('fConPgt[' + seq + ']')
								.setValue(cotacao.condicaoPagamento.id);
					}
				}
			});
	Ext.getCmp('idCot' + seq).setValue(cotacao.id);
	var fornecedor = cotacao.fornecedor;
	if (fornecedor.tipFor == "F") {
		Ext.getCmp('cpf' + seq).enable();
		Ext.getCmp('cpf' + seq).setValue(fornecedor.cgcCpf);
		Ext.getCmp('cnpj' + seq).disable();
		Ext.getCmp('rdTipFor' + seq).items.items[0].checked = true;
		Ext.getCmp('rdTipFor' + seq).setValue("F");
		buscaDadosFornecedor(fornecedor.cgcCpf, seq, "F");
	} else if (fornecedor.tipFor == "J") {
		Ext.getCmp('cnpj' + seq).enable();
		Ext.getCmp('cnpj' + seq).setValue(fornecedor.cgcCpf);
		Ext.getCmp('cpf' + seq).disable();
		Ext.getCmp('rdTipFor' + seq).items.items[1].checked = true;
		Ext.getCmp('rdTipFor' + seq).setValue("J");
		buscaDadosFornecedor(fornecedor.cgcCpf, seq, "J");
	}
	Ext.getCmp('txtIdFor[' + seq + ']').setValue(fornecedor.id);
}