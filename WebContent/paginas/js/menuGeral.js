function criaMenuGeral() {
	var tb = Ext.getCmp('pnTop').getBottomToolbar();

	// cadastro para colaborador da empresa
	var menuCol = new Ext.menu.Menu({
		id : 'mainMenu',
		style : {overflow : 'visible'},
		items : [ {
			text : 'Pr�-Cadastro',
			handler : onItemClick,
			id : 'btPreCad'
		},{
			text : 'Cadastro Completo',
			handler : onItemClick,
			id : 'btCadCom'
		},{
			text : 'Viagem',
			handler : onItemClick,
			id : 'btCadVia'
		}, "-", {
			text : 'Consulta',
			// handler :onItemClick,
			id : 'subConCol',
			menu : [ {
				text : 'Colaboradores',
				handler : onItemClick,
				id : 'btConCol'
			}, {
				text : 'Viagens',
				handler : onItemClick,
				id : 'btConVia'
			} ]
		}, {
			text : 'Relat�rios',
			menu : [ {
				text : 'Adiantamentos pendentes',
				handler : function() {
					window.location = 'viagem!geraRelatorioAdtPendente.action';
				}
			} ]
		} ]
	});
	
	// cadastro do novo usuário do sistema
	var menuUsu = new Ext.menu.Menu({
		id : 'menuUsu',
		style : {overflow : 'visible'},
		items : [ {
			text : 'Cadastro',
			handler : onItemClick,
			id : 'btCadUsu'
		}, {
			text : 'Listar',
			handler : onItemClick,
			id : 'btListUsu'
		} ]
	});

	//Segurança da qualidade 
	var menuSGQ = new Ext.menu.Menu({
		id : 'menuSGQ',	style : {overflow : 'visible'},
		items : [ {
			text : 'Documentos',
			id : 'mnDoc',
			menu : {
				items : [ {
					text : 'Cadastro',
					handler : function() {
						geraTelaCadastroDocumento('cadastro');
					},
					id : 'mnCadSGQ'
				}, {
					text : 'Visualizar todos',
					handler : function() {
						geraTelaCadastroDocumento('visualizacao');
					}
				} ]
			}

		} ]
	});

	if ((tipoUsuario != 'SGQ') && (tipoUsuario != 'ASI')) {
		menuSGQ.items.get('mnDoc').menu.items.get('mnCadSGQ').setVisible(false);
	}

	
	// compras 
	var menuReqExp = new Ext.menu.Menu(
			{
				id : 'menuReq', style : {overflow : 'visible'},
				items : [
						{
							text : 'Fornecedor',
							menu : {
								items : [ {
									text : 'Pr�-Cadastro',
									handler : function() {
										/**
										 * criar tela de pr�-cadastro de fornecedor *
										 */
										geraTelaPreCadastroFornecedor()
									}
								} ]
							}
						},
						{
							text : 'Material de expediente',
							id : 'subME',
							menu : {
								items : [ {
									text : 'Requisitar',
									handler : function() {
										novaRequisicaoEstoque("ME",	"Material de Expediente")
									}
								} ]

							}
						},
						{
							text : 'Material de instala��o/ferramentas',
							id : 'subMI',
							menu : {
								items : [ {
									text : 'Requisitar',
									handler : function() {
										novaRequisicaoEstoque("MI",	"Material de Instala��o/Ferramentas")
									}
								} ]

							}
						},
						{
							text : 'Material de limpeza',
							id : 'subLI',
							menu : {
								items : [ {
									text : 'Requisitar',
									handler : function() {
										novaRequisicaoEstoque("LI",	"Material de Limpeza")
									}
								} ]
							}
						},
						{
							text : 'Material carga de posto (Breve!)',
							id : 'subMC',
							menu : {
								items : [ {
									text : 'Requisitar',
									handler : function() {
										novaRequisicaoMaterialCargaPosto();
									}
								} ]

							}
						},
						{
							text : 'Servi�os (Breve!)',
							id : 'subSRV',
							menu : {
								items : [ {
									text : 'Requisitar',
									handler : function() {
										novaSolicitacaoCompraServico();
									}
								} ]
							}
						},
						{
							text : 'Outras compras (Breve!)',
							id : 'subOUC',
							handler : novaSolicitacaoCompra
						},
						"-",
						{
							text : 'Consulta',
							menu : {
								items : [{	text : 'Requisi��es de Estoque',
											menu : {
												items : [{text : 'Material Expediente',
															handler : function() {
																abreConsultaReq("ME","Material de Expediente")}
														},
														{  text : 'Material Instala��o/Ferramentas',
															handler : function() {
															abreConsultaReq("MI","Material de Instala��o/Ferramentas")
															}
														},
														{
															text : 'Material de limpeza',
															handler : function() {
																abreConsultaReq("LI","Material de Limpeza")
															}
														} ]
											}
										}, {
											text : 'Solicita��es de Compra',
											menu : {
												items : [ { text : 'Servi�os',
													handler : function() {
														abreConsultaSol();
													}
												} ]
											}
										} ]
							}
						} ]
			});

	tb.add({
		text : 'Home',
		iconCls : 'icnHom',
		handler : onItemClick,
		id : 'btHome'
	}, '-', {
		text : 'Colaboradores',
		iconCls : 'icnCol',
		menu : menuCol,
		id : 'mnCol'
	}, '-', new Ext.Toolbar.SplitButton({text : 'Uniformes',id : 'mnUni',
		tooltip : {
			text : 'Processos de uniformes...',
			title : 'Uniformes'
		},
		iconCls : 'icnUni',
		menu : {items : [ {text : 'Solicita��es',
							id : 'subUni',
							menu : [ {text : 'Nova',
									  id : 'btNovSol',
									  handler : onItemClick
								   } ]
			}, "-", {
				text : 'Consulta',
				menu : {
					items : [ {
						text : 'Solicita��es efetuadas',
						id : 'btConSol',
						handler : onItemClick
					}, {
						text : 'Cadastro de Uniformes',
						id : 'btConUni',
						handler : onItemClick
					} ]
				}
			} ]
		}
	}), '-', {
		text : 'Compras',
		iconCls : 'icnCmp',
		menu : menuReqExp,
		id : 'mnCom'
	}, '-', {
		text : 'SGQ',
		iconCls : 'icnSGQ',
		menu : menuSGQ,
		id : 'mnSGQ'
	}, '-', {
		text : 'Relat�rios',
		id : 'mnRel',
		tooltip : {
			text : 'Pesquisar solicita��es, colaboradores...',
			title : 'Pesquisa'
		},
		iconCls : 'icnRel',
		menu : {
			items : [ {
				text : 'Solicita��es liberadas',
				handler : onItemClick,
				id : 'btRelLib'
			}, {
				text : 'Solicita��es negadas',
				handler : onItemClick,
				id : 'btRelNeg'
			}, {
				text : 'Colaboradores demitidos',
				handler : onItemClick,
				id : 'btColDem'
			}, {
				text : 'Pend�ncias de Envio',
				id : 'btPenEnv',
				handler : function() {
					montaTelaFiltrosRelPen()
				}
			} ]
		}
	}, {
		text : 'Alterar Senha',
		iconCls : 'icnAlt',
		id : 'btAltSen',
		handler : onItemClick
	}, '-', {
		text : 'Ver solicita��es pendentes',
		iconCls : 'icnSolPen',
		id : 'btSolPen',
		handler : onItemClick
	}, '-', {
		text : 'Ver adminiss�es abertas',
		iconCls : 'icnAdm',
		id : 'btAdmAbe',
		handler : onItemClick
	}, '-');

	
	// Configura��o de visao por usuário
	// USUÁRIO -> OCO (OPERADOR DE COMPRAS)
	
	//Vendas
	if (tipoUsuario == 'OCO') {
		tb.items.get('mnRel').setVisible(false);
		tb.items.get('mnUni').setVisible(false);
		tb.items.get('btSolPen').setVisible(false);
		tb.items.get('btAdmAbe').setVisible(false);
		tb.items.get('mnCol').menu.items.get('btPreCad').setVisible(false);
		tb.items.get('mnCol').menu.items.get('btCadCom').setVisible(false);
		tb.items.get('mnCol').menu.items.get('subConCol').menu.items.get('btConCol').setVisible(false);
	}

	// Menu Compras
	// Processamento liberado para ASI, AAL e OAL	
	var menuComprasME = tb.items.get('mnCom').menu.items.get('subME').menu;
	var menuComprasMI = tb.items.get('mnCom').menu.items.get('subMI').menu;
	var menuComprasMC = tb.items.get('mnCom').menu.items.get('subMC').menu;
	var menuComprasSRV = tb.items.get('mnCom').menu.items.get('subSRV').menu;
	var menuComprasOUC = tb.items.get('mnCom').menu.items.get('subOUC').menu;
	var menuComprasLI = tb.items.get('mnCom').menu.items.get('subLI').menu;
	
	if ((tipoUsuario == "ASI") || (tipoUsuario == "AAL") || (tipoUsuario == "OAL")) {
		tb.items.get('mnCom').setVisible(true);
		tb.items.get('mnCom').menu.add({
			text : 'Relat�rio de acompanhamento',
			handler : function() {
				listaAcompanhamentoMensal()
			}
		});
		tb.items.get('mnCom').menu.add({
			text : 'Relat�rio de distribui��o',
			handler : function() {
				listaDistribuicaoMensal()
			}
		});

		// Aprovação liberado apenas para ASI e AAL
		if (tipoUsuario != "OAL") {
			menuComprasME.add({	text : 'Aprovar',
					handler : function() {
						aprovaRequisicaoEstoque("ME", "Material de Expediente")
					}
			});
			menuComprasMI.add({text : 'Aprovar',
					handler : function() {
						aprovaRequisicaoEstoque("MI","Material de Instala��o/Ferramentas")
					}
			});
			menuComprasLI.add({text : 'Aprovar',
					handler : function() {
						aprovaRequisicaoEstoque("LI", "Material de Limpeza")
					}
			});
			
			tb.items.get('mnCom').menu.add({
				text : 'Configura��o',
				handler : function() {
					exibeTelaConfCompras(); // arquivo confCompras.js
				}
			});
		}
		menuComprasME.add({text : 'Processar',
				handler : function() {
					processaRequisicaoEstoque("ME", "Material de Expediente")
				}
		});
		menuComprasLI.add({text : 'Processar',
				handler : function() {
					processaRequisicaoEstoque("LI", "Material de Limpeza")
				}
		});
		menuComprasMI.add({text : 'Processar',
				handler : function() {
					processaRequisicaoEstoque("MI","Material de Instala��o/Ferramentas")
				}
		});
		menuComprasMC.add({text : 'Processar',
				handler : processaRequisicaoEstoque
		});
	}

	
	var menuRel = tb.items.get('mnRel').menu;
	var menuUni = tb.items.get('mnUni').menu.items.get('subUni').menu;
	
	if ((tipoUsuario == "ASI") || (tipoUsuario == "AAL") || (tipoUsuario == "AAU")) {
		menuUni.add({
			text : 'Liberar solicita��o bloqueada',
			id : 'btLibSol',
			handler : onItemClick
		});
	}
	if ((tipoUsuario == "ASI") || (tipoUsuario == "AAL") || (tipoUsuario == "OAL") || (tipoUsuario == "AAU") || (tipoUsuario == "ACO") || (tipoUsuario == "AAD")) {
		 if (tipoUsuario != "AAU") {
			menuUni.add({text : 'Processar Implanta��es',
				id : 'btProcImp',
				handler : onItemClick
			});
		}
		menuRel.add({text : 'Implanta��es Pendentes',
							id : 'btImpPen',
							handler : function() {
								window.location = 'sincronizacao!geraRelatorioPendImpl.action';
							}
						},
						{	text : 'Falta de estoque',
							id : 'btFalEst',
							handler : function() {
								window.location = 'sincronizacao!geraRelatorioNaoAtendidos.action';
							}
						},
						{	text : 'Itens a liberar',
							id : 'btItmLib',
							handler : function() {
								window.location = 'sincronizacao!geraRelatorioLiberacoes.action?mandaEmail=false';
							}
						});
		
		if (tipoUsuario != "AAU") {
			tb.add({text : 'Sincroniza��o',
						id : 'mnSM',
						iconCls : 'icnSin',
						tooltip : {
							text : 'Sincronizar dados com sistemas Senior...',
							title : 'Sincroniza��o'
						},
						menu : {
							items : [{text : 'Estoques',
										menu : {
											items : [ {
												text : 'Atualizar',
												handler : onItemClick,
												id : 'btAtuEst'
											} ]
										}
									},
									{text : 'Importar',
										menu : {
											items : [{	text : 'Cidades',
														handler : onItemClick,
														id : 'btImpCid',
														menu : {
															items : [{text : 'Atualizar',
																		handler : onItemClick,
																		id : 'btAtuCid'
																	},
																	{text : 'Sincronizar',
																		handler : onItemClick,
																		id : 'btSincCid'
																	} ]
														}
													},
													{text : 'Situa��es de afastamento',
														handler : onItemClick,
														id : 'btImpSit'
													},
													{text : 'Deficiencia',
														handler : onItemClick,
														id : 'btImpDefic'
													},
													{text : 'Natureza De Despesa',
														handler : onItemClick,
														id : 'btImpNatDes'
													},
													{text : 'Sindicatos',
														handler : onItemClick,
														id : 'btImpSind'
													},
													{text : 'Posto de Trabalho',
														handler : onItemClick,
														id : 'btImpPostTrab'
													},{	text : 'Vinculo',
														handler : onItemClick,
														id : 'btImpVinc'
													},{	text : 'Escala Vale Transporte',
														handler : onItemClick,
														id : 'btImpEscValTra'
													},
													{text : 'Bancos',
														handler : onItemClick,
														id : 'btImpBan'
													},
													{text : 'Cargos',
														handler : onItemClick,
														id : 'btImpCar',
														menu : {
															items : [{text : 'Atualizar',
																		handler : onItemClick,
																		id : 'btAtuCar'
																	},
																	{text : 'Sincronizar',
																		handler : onItemClick,
																		id : 'btSincCar'
																	} ]
														}
													},
													{text : 'Centros de Custo',
														handler : onItemClick,
														id : 'btImpCen'
													},
													{text : 'Clientes',
														handler : onItemClick,
														id : 'btImpCli',
														menu : {
															items : [{text : 'Atualizar',
																		handler : onItemClick,
																		id : 'btAtuCli'
																	},
																	{text : 'Sincronizar',
																		handler : onItemClick,
																		id : 'btSincCli'
																	} ]
														}
													},
													{text : 'Empresas',
														handler : onItemClick,
														id : 'btImpEmp'
													},
													{text : 'Escalas',
														handler : onItemClick,
														id : 'btImpEsc',
														menu : {
															items : [{text : 'Atualizar',
																		handler : onItemClick,
																		id : 'btAtuEsc'
																	}]
														}
													},
													{text : 'Graus de instru��o',
														handler : onItemClick,
														id : 'btImpGra'
													},
													{text : 'Locais do organograma',
														handler : onItemClick,
														id : 'btImpLoc',
														menu : {
															items : [
																	{text : 'Atualizar',
																		handler : onItemClick,
																		id : 'btAtuLoc'
																	},
																	{text : 'Sincronizar',
																		handler : onItemClick,
																		id : 'btSincLoc'
																	} ]
														}
													},
													{text : 'Motivos de solicita��o',
														handler : onItemClick,
														id : 'btImpMot'
													},
													{text : 'Nacionalidades',
														handler : onItemClick,
														id : 'btImpNac'
													},
													{text : 'Tipos de uniformes',
														handler : onItemClick,
														id : 'btImpTip'
													},
													{text : 'Uniformes',
														handler : onItemClick,
														id : 'btImpUni'
													},
													{text : 'Produtos',
														handler : onItemClick,
														id : 'btImpPro'
													},
													{text : 'Fornecedores',
														handler : function() {
															sincroniza('Aguarde, atualizando fornecedores...','sincronizacao!atualizaFornecedor.action');
														},
														id : 'btImpFor'
													},
													{text : 'Condi��es de pagamento',
														handler : function() {
															sincroniza('Aguarde, atualizando condi��es de pagamento...','sincronizacao!atualizaCondicaoPagamento.action');
														},
														id : 'btImpConCpg'
													},
													{
														text : 'Colaboradores',
														menu : {
															items : [
																	{text : 'Demitidos',
																		handler : onItemClick,
																		id : 'btImpDem'
																	},
																	{text : 'Atualizados',
																		handler : onItemClick,
																		id : 'btImpCol'
																	},
																	{text : 'Sincronizar todos',
																		menu : {
																			items : [{text : 'ONSEG VIGILANCIA',
																						handler : onItemClick,
																						id : 'btImp01'
																					},
																					{text : 'ONSEG SISTEMAS',
																						handler : onItemClick,
																						id : 'btImp03'
																					},
																					{text : 'ONSERVICE',
																						handler : onItemClick,
																						id : 'btImp05'
																					},
																					{text : 'ZANARDO',
																						handler : onItemClick,
																						id : 'btImp09'
																					},
																					{text : 'TEMPORARIOS',
																						handler : onItemClick,
																						id : 'btImp21'
																					},
																					{text : 'TERCEIRIZADOS',
																						handler : onItemClick,
																						id : 'btImp23'
																					} ]
																		}
																	} ]
														}
													} ]
										}
									}/**,
									{
										text : 'Backup',
										handler : function() {
											sincroniza(
													'Aguarde, efetuando backup do banco de dados...',
													'sincronizacao!backup.action');
										}
									},
									{
										text : 'Gerar Base2',
										handler : function() {
											sincroniza(
													'Aguarde, gerando Base de testes do Vetorh. Isto levar� alguns minutos...',
													'utilidade!geraBase2.action');
										}
									},
									{
										text : 'Gerar Base Paralelo',
										handler : function() {
											sincroniza(
													'Aguarde, gerando Base paralelo do Vetorh. Isto levar� alguns minutos...',
													'utilidade!geraBase3.action');
										}
									}**/ ]
						}
					});
			if (tipoUsuario == "ASI") {
				tb.add({text : 'Usuarios Sistema',
						iconCls : 'icnCol',
						menu : menuUsu
				}, '-');
			}
		}
	}
	function onItemClick(item) {
		if (item.id == 'btConSol') {
			geraParametros();
		} else if (item.id == 'btConCol') {
			geraTelaPesqCol();
		} else if (item.id == 'btAltSen') {
			criaTelaAltSen();
		} else if (item.id == 'btConUni') {
			geraTelaPesqUni();
		} else if (item.id == 'btHome') {
			exibeHome();
		} else if (item.id == 'btRelLib') {
			showConfReport('lib');
		} else if (item.id == 'btRelNeg') {
			showConfReport('neg');
		} else if (item.id == 'btPreCad') {
			geraTelaPreCadastro();
		} else if (item.id == 'btCadCom') {
			geraTelaCadastroCompleto();
		} else if (item.id == 'btAtuEst') {
			sincroniza('Aguarde, atualizando estoques...','sincronizacao!verificaEstoque.action');
		} else if (item.id == 'btAtuCid') {
			sincroniza('Aguarde, atualizando cidades...','sincronizacao!atualizaCidades.action');
		} else if (item.id == 'btSincCid') {
			sincroniza('Aguarde, sincronizando cidades...','sincronizacao!sincronizaCidades.action');
		} else if (item.id == 'btImpSit') {
			sincroniza('Aguarde, atualizando situa��es...','sincronizacao!atualizaAfastamentos.action');
		} else if (item.id == 'btImpBan') {
			sincroniza('Aguarde, atualizando bancos...','sincronizacao!atualizaBancos.action');
		} else if (item.id == 'btAtuCar') {
			sincroniza('Aguarde, atualizando cargos...','sincronizacao!atualizaCargos.action');
		} else if (item.id == 'btSincCar') {
			sincroniza('Aguarde, sincronizando cargos...','sincronizacao!sincronizaCargos.action');
		} else if (item.id == 'btImpCen') {
			sincroniza('Aguarde, atualizando centros...','sincronizacao!atualizaCentros.action');
		} else if (item.id == 'btAtuCli') {
			sincroniza('Aguarde, atualizando clientes...','sincronizacao!atualizaClientes.action');
		} else if (item.id == 'btSincCli') {
			sincroniza('Aguarde, sincronizando clientes...','sincronizacao!sincronizaClientes.action');
		} else if (item.id == 'btImpEmp') {
			sincroniza('Aguarde, atualizando empresas...','sincronizacao!atualizaEmpresas.action');
		} else if (item.id == 'btAtuEsc') {
			 sincroniza('Aguarde, atualizando escalas, isso pode levar alguns minutos...','sincronizacao!atualizaEscalasRonda.action');
		} else if (item.id == 'btImpGra') {
			sincroniza('Aguarde, atualizando graus de instru��o...','sincronizacao!atualizaGrauInstrucao.action');
		} else if (item.id == 'btAtuLoc') {
			sincroniza('Aguarde, atualizando locais...','sincronizacao!atualizaLocais.action');
		} else if (item.id == 'btSincLoc') {
			sincroniza('Aguarde, sincronizando locais...','sincronizacao!sincronizaLocais.action');
		} else if (item.id == 'btImpMot') {
			sincroniza('Aguarde, atualizando motivos...','sincronizacao!atualizaMotivos.action');
		} else if (item.id == 'btImpTip') {
			sincroniza('Aguarde, atualizando tipos...','sincronizacao!atualizaTipos.action');
		} else if (item.id == 'btImpUni') {
			sincroniza('Aguarde, atualizando uniformes...','sincronizacao!atualizaUniformes.action');
		} else if (item.id == 'btImpDem') {
			sincroniza('Aguarde, atualizando demitidos...','sincronizacao!atualizaDemitidos.action');
		} else if (item.id == 'btImpCol') {
			sincroniza('Aguarde, atualizando colaboradores...','sincronizacao!atualizaColaboradores.action');
		} else if (item.id == 'btImpNSol') {
			sincroniza('Aguarde, atualizando solicita��es...','sincronizacao!atualizaDistribuicao.action');
		} else if (item.id == 'btImpTSol') {
			sincroniza('Aguarde, importando todas as  solicita��es...','sincronizacao!importaDistribuicoes.action');
		} else if (item.id == 'btExpSol') {
			sincroniza('Aguarde, exportando solicita��es...','sincronizacao!exportaSolicitacoesLiberadas.action');
		} else if (item.id == 'btExpSAdm') {
			sincroniza('Aguarde, exportando solicita��es de admitidos...','sincronizacao!exportaSolicitacoesAdmitidos.action');
		}else if (item.id == 'btImpEscValTra'){
			 sincroniza('Aguarde, importando Escala de Vale Transporte','sincronizacao!atualizaEscaValTransporte.action');
		}else if (item.id == 'btImpSind'){
			 sincroniza('Aguarde, importando sindicatos','sincronizacao!atualizaSindicato.action');
		}else if (item.id == 'btImpDefic'){
			 sincroniza('Aguarde, importando deficiencias','sincronizacao!atualizaDeficiencia.action');
		}else if (item.id == 'btImpPostTrab'){
			 sincroniza('Aguarde, importando postos de trabalho','sincronizacao!atualizaPostoTrabalho.action');
		}else if (item.id == 'btImpNatDes'){
			 sincroniza('Aguarde, importando as naturezas de despesas','sincronizacao!atualizaNaturezaDespesa.action');
		}else if (item.id == 'btImpVinc'){
			 sincroniza('Aguarde, importando os vinculos','sincronizacao!atualizaVinculo.action');
		}else if (item.id == 'btLibSol') {
			geraListaSol();
		} else if (item.id == 'btProcImp') {
			processImpl();
		} else if (item.id == 'btNovSol') {
			novaSolicitacao();
		} else if (item.id == 'btSolPen') {
			minhasSolicitacoes();
		} else if (item.id == 'btAdmAbe') {
			minhasAdmissoes();
		} else if (item.id == 'btCadVia') {
			geraTelaCadastroViagem();
		} else if (item.id == 'btConVia') {
			geraTelaPesqVia();
		} else if (item.id == 'btColDem') {
			geraTelaFiltroDemitidos();
		} else if (item.id == 'btCadUsu') {
			montaTelaUsuario('');
		} else if (item.id == 'btListUsu') {
			montaTelaListagemUsuario();
		} else if (item.id == 'btImpPro') {
			sincroniza('Aguarde, atualizando produtos...','sincronizacao!atualizaProduto.action');
		} else if (item.id == 'btImp01') {
			sincroniza('Aguarde, atualizando colaboradores ONSEG VIGILANCIA...','sincronizacao!atualizaTodosColaboradores.action?numEmp=1');
		} else if (item.id == 'btImp03') {
			sincroniza('Aguarde, atualizando colaboradores ONSEG SISTEMAS...','sincronizacao!atualizaTodosColaboradores.action?numEmp=3');
		} else if (item.id == 'btImp05') {
			sincroniza('Aguarde, atualizando colaboradores ONSERVICE...','sincronizacao!atualizaTodosColaboradores.action?numEmp=5');
		} else if (item.id == 'btImp09') {
			sincroniza('Aguarde, atualizando colaboradores ZANARDO...','sincronizacao!atualizaTodosColaboradores.action?numEmp=9');
		} else if (item.id == 'btImp21') {
			sincroniza('Aguarde, atualizando colaboradores TEMPORARIOS...','sincronizacao!atualizaTodosColaboradores.action?numEmp=21');
		} else if (item.id == 'btImp23') {
			sincroniza('Aguarde, atualizando colaboradores TERCEIRIZADOS...','sincronizacao!atualizaTodosColaboradores.action?numEmp=23');
		} else if (item.id == 'btImpNac') {
			sincroniza('Aguarde, importanto nacionalidades...','sincronizacao!sincronizaNacionalidades.action');
		}
	}
	tb.add({text : 'Sair',
		iconCls : 'icnSai',
		id : 'btSai',
		handler : function() {
			window.location = 'login!logout.action';
		}
	});
	Ext.getCmp('pnTop').doLayout(true, true);
}

function montaTelaFiltrosRelPen() {
	var storeCentro = criaStoreReader('centro', 'centro!listAllXML.action', ['id', 'nomCcu' ], 'id', 'storeCentro');
	var formConf = new Ext.form.FormPanel({
		labelWidth : 100,
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		defaultType : 'datefield',
		width : 350,
		defaults : {width : 175},
		items : [ {
			xtype : 'combo',
			fieldLabel : 'Centro',
			hiddenName : 'codCcu',
			store : storeCentro,
			valueField : 'id',
			displayField : 'nomCcu',
			typeAhead : true,
			mode : 'remote',
			triggerAction : 'all',
			emptyText : 'Todos...',
			selectOnFocus : true,
			width : 170,
			id : 'fCen',
			allowBlank : true,
			forceSelection : true
		} ]
	});
	var windowConf = new Ext.Window({
		title : 'Parametriza��o do relat�rios de solicita��es pendentes',
		width : 350,
		height : 200,
		minWidth : 350,
		minHeight : 200,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding: 5px',
		closeAction : 'close',
		buttonAlign : 'center',
		items : formConf,
		buttons : [ {
			text : 'Executar',
			handler : function() {
				urlRel = 'sincronizacao!geraRelatorioPendencias.action?codCcu='+ Ext.getCmp('fCen').getValue();
				window.location = urlRel;
			}
		} ]
	});
	windowConf.show();
	windowConf.center();
}