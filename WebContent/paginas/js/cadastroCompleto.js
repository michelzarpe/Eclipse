
var podeIntegrar = false;

function geraTelaCadastroCompleto(url) {
	var campos = [ 'id', 'nomCid', 'estCid' ];
	var storeCidadeEnd = criaStoreReader('cidade', 'cidade!listAllXML.action', campos, 'id', 'storeCidade');
	storeCidadeEnd.load();
	var tabs = new Ext.TabPanel({ id : 'tbPanel', activeTab : 0, defaults : { layout : 'form' }, layoutOnTabChange : true, anchor : '100% 100%', bodyStyle : 'padding:5px',
		items : [ criaTabDadosCad(), criaTabEnd(), criaTabDoc(), criaTabDadAdm(),criaTabCompAdm(), criaTabHor(), criaTabLocal(), criaTabDadBan(), criaTabDadVig(), criaTabObs(), criaTabDep()] });
	var formCol = new Ext.form.FormPanel({ url : 'colaborador!gravar.action?colaborador.local.id.tabOrg=2', frame : true, bodyStyle : 'padding:5px', autoWidth : true, defaultType : 'textfield',
		reader : criaLeitorCol(), id : 'fCadCon', items : [ tabs ] });

	var win = new Ext.Window({
		title : 'Cadastro completo de colaborador',
		width : 800,
		height : 400,
		layout : 'fit',
		border : false,
		id : 'fJanelaCadCom',
		items : [ formCol ],
		defaultButton : 'bGravar',
		maximizable : true,
		maximized : true,
		closable : false,
		renderTo : 'pnCenter',
		buttons : [ { text : 'Gravar', id : 'bGravar', iconCls : 'icnGra', handler : function() {

			
			if (ValidaDadosBancarios()) {		
				if(Ext.getCmp('gridDep').getStore().getCount() > 0){
					 var params = {}; var i = 0;
					 Ext.getCmp('gridDep').getStore().each(function(record) {
							var fields = Ext.getCmp('gridDep').getStore().fields.items;
							
							var colunasGrid =  Ext.getCmp('gridDep').getColumnModel().columns;
							
							for (var j = 0; j < fields.length; j++) {
								if (record.get(fields[j].name) != undefined) {
									if ((fields[j].name == 'datNas') || (fields[j].name == 'datInv') || (fields[j].name == 'iniTut') || (fields[j].name == 'datTut')) {
										if ((record.get(fields[j].name) != undefined) && (record.get(fields[j].name) != '')) {
										  	 params['dependentes['+ i+ '].'+ fields[j].name] = record.get(fields[j].name).dateFormat('d/m/Y');
										  	
										  }
									 } else{
										if(fields[j].name == 'grauInstrucao'){
											params['dependentes['+ i+ '].'+ fields[j].name+'.id'] = record.get(fields[j].name);
										}else{	
										 params['dependentes['+ i+ '].'+ fields[j].name] = record.get(fields[j].name);
										}
									 }
								}
							}
							i++;								
					 });
				}
				
				console.log(params);
				
				
				formCol.form.submit({waitMsg : 'Aguarde, gravando cadastro do colaborador', params: params, success : function(frm, act) {
					Ext.Msg.show({ title : 'Sucesso', msg : act.result.msg, buttons : Ext.MessageBox.OK, icon : Ext.MessageBox.INFO });
					gravou = true;
					podeIntegrar = true;
					Ext.getCmp('fSitAdm').setValue("C");
					Ext.getCmp('btIntegrar').enable();}, 
				failure : function(form, action) {
					Ext.Msg.show({title : 'Falha', msg : action.result.msg, buttons : Ext.MessageBox.OK, icon : Ext.MessageBox.ERROR });
					gravou = false;
					podeIntegrar = false;
				}})
			}
		} }, { text : 'Cancelar', handler : fecharJanela, iconCls : 'icnCan' }, 
		{ text : 'Integrar', id:'btIntegrar', disabled: true, handler : function() {
			if (podeIntegrar == true) {
				Ext.Msg.show({ title : 'Integrar Colaborador?', msg : 'Tem certeza que deseja integrar o colaborador para admissão?', buttons : Ext.Msg.YESNOCANCEL, icon : Ext.MessageBox.QUESTION, fn : function(button) {
						if (button == 'yes'){
							 formCol.form.submit({ url : 'colaborador!integrar.action?colaborador.local.id.tabOrg=2', waitMsg : 'Aguarde, integrando colaborador com administração de pessoal!', success : function(frm, act) {
								Ext.Msg.show({ title : 'Sucesso', msg : act.result.msg, buttons : Ext.MessageBox.OK, icon : Ext.MessageBox.INFO });
								podeIntegrar = false;
								Ext.getCmp('fSitAdm').setValue("G");
							}, failure : function(form, action) {
								Ext.Msg.show({ title : 'Falha no processo de integração, informe ao administrador do sistema.', msg : action.result.msg, buttons : Ext.MessageBox.OK, icon : Ext.MessageBox.ERROR });
								podeIntegrar = false;
							} });
						}
					} });
			}
			
			// executar refresh na tela de myHome.js
			if (Ext.getCmp('sGridCol')) {
				var storeColHome = Ext.getCmp('sGridCol').store;
				storeColHome.setDefaultSort('nomFun', 'ASC');
				storeColHome.load({ params : { start : 0, limit : 25 } });
			}
		}, 
		iconCls : 'icnInt'}],
		listeners : { 'show' : function() {
			/**
			 * Ativar todas as tabs antes de fazer o load dos dados. Se não
			 * ativar, não aparece dado algum, por causa do deferredRender=false
			 * na configuração da TabPanel
			 */
			tabs.setActiveTab(1);
			tabs.setActiveTab(2);
			tabs.setActiveTab(3);
			tabs.setActiveTab(4);
			tabs.setActiveTab(5);
			tabs.setActiveTab(6);
			tabs.setActiveTab(7);
			tabs.setActiveTab(8);
			tabs.setActiveTab(9);
			tabs.setActiveTab(10);
			tabs.setActiveTab(11);
			tabs.setActiveTab(0);
			/** ------ * */
			if (url) {
				Ext.getCmp('fEmp').store.reload();
				Ext.getCmp('fCar').store.reload();
				Ext.getCmp('fCla').store.reload();
				Ext.getCmp('fBan').store.reload();
				Ext.getCmp('fMot').store.reload();
				Ext.getCmp('fEstCiv').store.reload();
				Ext.getCmp('fTipSan').store.reload();
				Ext.getCmp('fGraIns').store.reload();
				Ext.getCmp('fMeiTrans').store.reload();
				Ext.getCmp('fRacCor').store.reload();
				Ext.getCmp('fEstNas').store.reload();
				Ext.getCmp('fPaiNas').store.reload();
				Ext.getCmp('fDef').store.reload();
				Ext.getCmp('fSin').store.reload();
				Ext.getCmp('fVinc').store.reload();
				Ext.getCmp('fNatDes').store.reload();
				Ext.getCmp('fEscVT').store.reload();
				Ext.getCmp('fAdmEso').store.reload();
				Ext.getCmp('fCatEso').store.reload();
				Ext.getCmp('fCatAnt').store.reload();		
				Ext.getCmp('fCatSef').store.reload();
				Ext.getCmp('fCodEtb').store.reload();
				Ext.getCmp('fTipAdmHfi').store.reload();
				Ext.getCmp('fIndAdm').store.reload();
				Ext.getCmp('fTipApo').store.reload();
				Ext.getCmp('fOnuSce').store.reload();
				Ext.getCmp('fLisRai').store.reload();
				Ext.getCmp('fRatEve').store.reload();
				Ext.getCmp('fRec13S').store.reload();
				Ext.getCmp('fRecAdi').store.reload();
				Ext.getCmp('fEmiCar').store.reload();
				Ext.getCmp('fResOnu').store.reload();
				Ext.getCmp('fSocSinHsi').store.reload();
				Ext.getCmp('fNac').store.reload();
				Ext.getCmp('FEstEnd').store.reload();
				Ext.getCmp('fPosDef').store.reload();
				Ext.getCmp('fTipSal').store.reload();
				Ext.getCmp('fEstCtp').store.reload();


			
				formCol.getForm().load({ url : url, waitMsg : 'Aguarde, preenchendo dados...', success : function(form, action) {
					
					if(action.response.responseXML.getElementsByTagName('sitAdm')[0].firstChild.nodeValue == "C"){
						Ext.getCmp('btIntegrar').enable();
						podeIntegrar = true;
					}
					
					if (action.response.responseXML.getElementsByTagName('pagSin')[0].firstChild.nodeValue == "true") {
						Ext.getCmp('fRdPagSinS').setValue(true);
					} else {
						Ext.getCmp('fRdPagSinN').setValue(true);
					}
				
					
					/*dependentes*/
					dados = action.response.responseXML;
					var stDep = Ext.getCmp('gridDep').store;
					stDep.loadData(dados);
				
			
					
					//meio de transporte
					if (Ext.getCmp('fMeiTrans').getValue() == "C" ) {
						Ext.getCmp('fQtdMes').allowBlank = true;
						Ext.getCmp('fQtdMes').disable();
						Ext.getCmp('fQtdMes').reset();
						Ext.getCmp('fVlrMes').allowBlank = true;
						Ext.getCmp('fVlrMes').disable();
						Ext.getCmp('fVlrMes').reset();
						Ext.getCmp('fEscVT').allowBlank = true;
						Ext.getCmp('fEscVT').disable();
						Ext.getCmp('fEscVT').reset();
						Ext.getCmp('fDatIniVT').allowBlank = true;
						Ext.getCmp('fDatIniVT').disable();
						Ext.getCmp('fDatIniVT').reset();
						Ext.getCmp('fDatFimVT').allowBlank = true;
						Ext.getCmp('fDatFimVT').disable();
						Ext.getCmp('fDatFimVT').reset();
						Ext.getCmp('fQtdkm').allowBlank = false;
						Ext.getCmp('fQtdkm').enable();
						Ext.getCmp('fvlrCbt').allowBlank = false;
						Ext.getCmp('fvlrCbt').enable();
						
					}else {
						if (Ext.getCmp('fMeiTrans').getValue() == "O" ) {
							Ext.getCmp('fQtdMes').allowBlank = false;
							Ext.getCmp('fQtdMes').enable();
							Ext.getCmp('fVlrMes').allowBlank = false;
							Ext.getCmp('fVlrMes').enable();
							Ext.getCmp('fEscVT').allowBlank = false;
							Ext.getCmp('fEscVT').enable();
							Ext.getCmp('fDatIniVT').allowBlank = false;
							Ext.getCmp('fDatIniVT').enable();
							Ext.getCmp('fDatFimVT').allowBlank = false;
							Ext.getCmp('fDatFimVT').enable();
							Ext.getCmp('fQtdkm').allowBlank = true;
							Ext.getCmp('fQtdkm').disable();
							Ext.getCmp('fQtdkm').reset();
							Ext.getCmp('fvlrCbt').allowBlank = true;
							Ext.getCmp('fvlrCbt').disable();
							Ext.getCmp('fvlrCbt').reset();
						}
					}
					
					
					
					//postoTrabalho
					if (Ext.getCmp('fPosTra').getValue() != "" || Ext.getCmp('fPosTra').getValue() != undefined) {
						Ext.getCmp('fPosTra').store.proxy.getConnection().extraParams = { 'colaborador.postoTrabalho.posTra' : Ext.getCmp('fPosTra').getValue() };
						Ext.getCmp('fPosTra').store.addListener('load', function() {
							var posTra = Ext.getCmp('fPosTra').store.getById(Ext.getCmp('fPosTra').getValue()).get('posTra');
							var  desPos = Ext.getCmp('fPosTra').store.getById(Ext.getCmp('fPosTra').getValue()).get('desPos');	
							Ext.getCmp('fPosTra').setValue(posTra);
							Ext.getCmp('fPosTra').setRawValue(desPos);
						});
						Ext.getCmp('fPosTra').store.reload({ callback : function(r, options, success) {
							Ext.getCmp('fPosTra').store.purgeListeners();
						} });
					}
					
					//Deficiencia
					if (Ext.getCmp('fPosDef').getValue() == "S") {
						Ext.getCmp('fDef').store.proxy.getConnection().extraParams = { 'colaborador.deficiencia.codDef' : Ext.getCmp('fDef').getValue() };
						Ext.getCmp('fDef').store.addListener('load', function() {
							var codDef = Ext.getCmp('fDef').store.getById(Ext.getCmp('fDef').getValue()).get('codDef');
							var  desDef = Ext.getCmp('fDef').store.getById(Ext.getCmp('fDef').getValue()).get('desDef');	
							Ext.getCmp('fDef').setValue(codDef);
							Ext.getCmp('fDef').setRawValue(desDef);
							Ext.getCmp('fDef').enable();
						});
						Ext.getCmp('fDef').store.reload({ callback : function(r, options, success) {
							Ext.getCmp('fDef').store.purgeListeners();
						} });
					}else{
						Ext.getCmp('fDef').allowBlank = true;
						Ext.getCmp('fDef').disable();
						Ext.getCmp('fDef').reset()
					}
		
					
					// Exibindo Escala do Colaborador.
					Ext.getCmp('fEscRon').store.proxy.conn.url = 'escalaHorarioRondaMZ!listFiltrosXML.action?claesc=' + Ext.getCmp('fCla').getValue();
					Ext.getCmp('fEscRon').store.addListener('load', function() {
						var codEsc = Ext.getCmp('fEscRon').store.getById(Ext.getCmp('fEscRon').getValue()).get('id');
						var  nomEsc = Ext.getCmp('fEscRon').store.getById(Ext.getCmp('fEscRon').getValue()).get('nomesc');
						Ext.getCmp('fEscRon').setValue(codEsc);
						Ext.getCmp('fEscRon').setRawValue(nomEsc);
						
						Ext.getCmp('fHorSem').setValue(Ext.getCmp('fHorSem').getValue()/60);
						Ext.getCmp('fHorMes').setRawValue(Ext.getCmp('fHorMes').getValue()/60);
						
					    Ext.getCmp ('fHorSem').disable();
						Ext.getCmp ('fHorMes').disable(); 
						Ext.getCmp ('fHorEsc').store.proxy.conn.url = 'horarioRonda!listAllXML.action?codEsc='+Ext.getCmp('fEscRon').getValue();
   					    Ext.getCmp ('fHorEsc').store.reload();
						
					});
					Ext.getCmp('fEscRon').store.reload({ callback : function(r, options, success) {
						Ext.getCmp('fEscRon').store.purgeListeners();
					} });
					  

					// exibe nome da unidade
					Ext.getCmp('fUni').store.proxy.conn.url = 'local!listAllXML.action?nivel=2&nivelAnterior=' + Ext.getCmp('fEmpLoc').getValue();
					Ext.getCmp('fUni').store.addListener('load', function() {
						var nomeLocal = Ext.getCmp('fUni').store.getById(Ext.getCmp('fUni').getValue()).get('nomLoc');
						var codigoLocal = Ext.getCmp('fUni').store.getById(Ext.getCmp('fUni').getValue()).get('codLoc');
						Ext.getCmp('fUni').setValue(codigoLocal);
						Ext.getCmp('fUni').setRawValue(codigoLocal + " - " + nomeLocal);
					});
					Ext.getCmp('fUni').store.reload({ callback : function(r, options, success) {
						Ext.getCmp('fUni').store.purgeListeners();
					} });
					
					// exibe nome do cliente
					Ext.getCmp('fCli').store.proxy.conn.url = 'local!listAllXML.action?nivel=3&nivelAnterior=' + Ext.getCmp('fUni').getValue();
					Ext.getCmp('fCli').store.addListener('load', function() {
						var nomeLocal = Ext.getCmp('fCli').store.getById(Ext.getCmp('fCli').getValue()).get('nomLoc');
						var codigoLocal = Ext.getCmp('fCli').store.getById(Ext.getCmp('fCli').getValue()).get('codLoc');
						Ext.getCmp('fCli').setValue(codigoLocal);
						Ext.getCmp('fCli').setRawValue(codigoLocal + " - " + nomeLocal);
					});
					Ext.getCmp('fCli').store.reload({ callback : function(r, options, success) {
						Ext.getCmp('fCli').store.purgeListeners();
					} });
					storeCidadeLoc.proxy.conn.url = 'local!listAllXML.action?nivel=4&nivelAnterior=' + Ext.getCmp('fCli').getValue();
					
					// exibe nome da cidade
					storeCidadeLoc.addListener('load', function() {
						var nomeLocal = storeCidadeLoc.getById(Ext.getCmp('fCid').getValue()).get('nomLoc');
						var codigoLocal = storeCidadeLoc.getById(Ext.getCmp('fCid').getValue()).get('codLoc');
						Ext.getCmp('fCid').setValue(codigoLocal);
						Ext.getCmp('fCid').setRawValue(codigoLocal + " - " + nomeLocal);
					});
					storeCidadeLoc.reload({ callback : function(r, options, success) {
						storeCidadeLoc.purgeListeners();
					} });
					
					// exibe nome do posto
					storePosto.proxy.conn.url = 'local!listAllXML.action?nivel=5&nivelAnterior=' + Ext.getCmp('fCid').getValue();
					storePosto.addListener('load', function() {
						var nomeLocal = storePosto.getById(Ext.getCmp('fPos').getValue()).get('nomLoc');
						var codigoLocal = storePosto.getById(Ext.getCmp('fPos').getValue()).get('numLoc');
						var codLoc = storePosto.getById(Ext.getCmp('fPos').getValue()).get('codLoc');
						Ext.getCmp('fPos').setValue(codigoLocal);
						Ext.getCmp('fPos').setRawValue(codLoc + " - " + nomeLocal);
					});
					storePosto.reload({ callback : function(r, options, success) {
						storePosto.purgeListeners();
					} });

					// exibe nome da cidade de
					// nascimento
					if (Ext.getCmp('fCidNas').getValue() != "") {
						Ext.getCmp('fCidNas').store.proxy.getConnection().extraParams = { 'cidade.id' : Ext.getCmp('fCidNas').getValue() };
						Ext.getCmp('fCidNas').store.addListener('load', function() {
							var codigoCidadeNas = Ext.getCmp('fCidNas').store.getById(Ext.getCmp('fCidNas').getValue()).get('id');
							Ext.getCmp('fCidNas').setValue(codigoCidadeNas);
						});
						Ext.getCmp('fCidNas').store.reload({ callback : function(r, options, success) {
							Ext.getCmp('fCidNas').store.purgeListeners();
						} });
						Ext.getCmp('fCidNas').store.proxy.getConnection().extraParams = { 'cidade.id' : '' };
					}

					// exibe nome da cidade de
					// resid�ncia
					var storeCidadeEnd = Ext.getCmp('fCidEnd').store;
					if (Ext.getCmp('fCidEnd').getValue() != "") {
						storeCidadeEnd.proxy.getConnection().extraParams = { 'cidade.id' : Ext.getCmp('fCidEnd').getValue() };
						storeCidadeEnd.addListener('load', function() {
							var codigoCidadeEnd = storeCidadeEnd.getById(Ext.getCmp('fCidEnd').getValue()).get('id');
							Ext.getCmp('fCidEnd').setValue(codigoCidadeEnd);
						});
						storeCidadeEnd.reload({ callback : function(r, options, success) {
							storeCidadeEnd.purgeListeners();
						} });
						storeCidadeEnd.proxy.getConnection().extraParams = { 'cidade.id' : '' };
					}

					// bairro
					Ext.getCmp('fBaiEnd').store.proxy.conn.url = 'bairro!listAllXML.action?codCid=' + Ext.getCmp('fCidEnd').getValue();
					Ext.getCmp('fBaiEnd').store.addListener('load', function() {
						var codBai = Ext.getCmp('fBaiEnd').store.getById(Ext.getCmp('fBaiEnd').getValue()).get('codBai');	
						var  nomBai = Ext.getCmp('fBaiEnd').store.getById(Ext.getCmp('fBaiEnd').getValue()).get('nomBai');
						Ext.getCmp('fBaiEnd').setValue(codBai);
						Ext.getCmp('fBaiEnd').setRawValue(nomBai);	
					});
					Ext.getCmp('fBaiEnd').store.reload({ callback : function(r, options, success) {
						Ext.getCmp('fBaiEnd').store.purgeListeners();
					} });
					
					
					
					// exibe nome do colaborador
					// substituído
					var storeCol = Ext.getCmp('fColSubs').store;
					if (Ext.getCmp('fColSubs').getValue() != "") {
						storeCol.proxy.conn.url = 'colaborador!listAllXML.action?notFindDemitidos=false&colaborador.id=' + Ext.getCmp('fColSubs').getValue();
						storeCol.addListener('load', function() {
							var codigoSubstituido = storeCol.getById(Ext.getCmp('fColSubs').getValue()).get('colaboradorId');
							Ext.getCmp('fColSubs').setValue(codigoSubstituido);
						});
						storeCol.reload({ callback : function(r, options, success) {
							storeCol.purgeListeners();
							storeCol.proxy.conn.url = 'colaborador!listAllXML.action';
						} });
					}
				} });

			}
		} },
		
		tbar : [ new Ext.Toolbar({
			id : 'tBarCadCon',
			items : [
					{ text : 'Ficha Admissional', iconCls : 'icnFic', tooltip : 'Imprime ficha admissional do colaborador', handler : imprimeFicha, id : 'btImpFic' },
					"-",
					new Ext.form.Checkbox({ fieldLabel : '', boxLabel : 'Incluir demitidos na busca de colaboradores substituídos', name : 'notFindDemitidos', id : 'notFindDemitidos', width : 290,
						listeners : { check : function(chk, marcado) {
							if (marcado == true) {
								Ext.getCmp('fColSubs').store.setBaseParam('notFindDemitidos', false);
							} else {
								Ext.getCmp('fColSubs').store.setBaseParam('notFindDemitidos', true);
							}
						} } }) ] }) ] });
	win.show();
	win.center();
}

function fecharJanela() {
	if (gravou == false) {
		Ext.Msg.show({ title : 'Abandonar alterações?', msg : 'Você está fechando a tela sem salvar. Deseja abandonar as alterações e sair?', buttons : Ext.Msg.YESNOCANCEL,
			icon : Ext.MessageBox.QUESTION, fn : function(button) {
				if (button == 'yes')
					Ext.getCmp('fJanelaCadCom').destroy();
			} });
	} else {
		Ext.getCmp('fJanelaCadCom').destroy();
	}
	// executar refresh na tela de myHome.js
	if (Ext.getCmp('sGridCol')) {
		var storeColHome = Ext.getCmp('sGridCol').store;
		storeColHome.setDefaultSort('nomFun', 'ASC');
		storeColHome.load({ params : { start : 0, limit : 25 } });
	}
}

function criaTabDadosCad() {
	var campos = [ 'id', 'nomCid', 'estCid' ];
	var storeCidadeNas = criaStoreReader('cidade', 'cidade!listAllXML.action', campos, 'id', 'storeCidade');
	var cidadeTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{nomCid}, {estCid}</span></h3></div></tpl>');
	campos = [ 'id', 'name' ];
	var storeEstCiv = criaStoreReader('item', 'estadoCivil!listAllXML.action', campos, 'id', 'storeEstCiv');
	var storeGraIns = criaStoreReader('grauInstrucao', 'grauInstrucao!listAllXML.action', campos, 'id', 'storeGraIns');
	var storeTipSan = criaStoreReader('item', 'tipoSanguineo!listAllXML.action', campos, 'id', 'storeTipSan');
	var storeRacCor = criaStoreReader('item', 'racaCor!listAllXML.action', campos, 'id', 'storeRacCor');
	var storeNac = criaStoreReader('nacionalidade', 'nacionalidade!listAllXML.action', campos, 'id', 'storeNac');
	var storeTipSal = criaStoreReader('item', 'tipoSalario!listAllXML.action', campos, 'id', 'storeTipSal');
	campos = [ 'codDef', 'desDef' ];
	var storeDef = criaStoreReader('deficiencia', 'deficiencia!listAllXML.action', campos, 'codDef', 'storeDef');
	campos = ['id','name'];
	var storeListSN = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeListSN');
	var storeEstNas = criaStoreReader('item', 'estCtp!listAllXML.action', campos, 'id', 'storeEstNas');
	var storePaiNas = criaStoreReader('item', 'paiNas!listAllXML.action', campos, 'id', 'storePaiNas');
	
	var cbCidadeNas = new Ext.form.ComboBox({ store : storeCidadeNas, id : 'fCidNas', hiddenName : 'colaborador.cciNas.id', fieldLabel : 'Cidade nascimento', displayField : 'nomCid',
		valueField : 'id', typeAhead : true, mode : 'remote', itemSelector : 'div.search-item', emptyText : 'Busca automática a partir de 4 caracteres...', loadingText : 'Procurando...',
		pageSize : 10, width : 350, tpl : cidadeTpl, triggerClass : 'x-form-search-trigger', triggerAction : 'all', hideTrigger : true, minChars : 3, queryParam : 'cidade.nomCid', allowBlank : true,
		forceSelection : true, autoSelect : false });

	var tabDadosCad = {
		title : 'Dados pessoais',
		labelWidth : 100,
		labelAlign : 'right',
		items : [
				{ fieldLabel : 'Situação', name : 'sitAdm', id : 'fSitAdm', xtype : 'hidden' },
				{ fieldLabel : 'Id', name : 'colaborador.id', width : 50, xtype : 'hidden', id : 'fIdCol' },
				{
					layout : 'column',
					items : [
							{ columnWidth : .4, layout : 'form',
								items : { fieldLabel : 'Nome', name : 'colaborador.nomFun', width : 350, allowBlank : true, id : 'fNomFun', xtype : 'textfield', maxLength : 60 } },
							{ columnWidth : .3, layout : 'form', items : [ new Ext.form.DateField({ fieldLabel : 'Data de nascimento', name : 'colaborador.datNas', id:'fDatNasci', width : 80, allowBlank : true }) ] } ] },
				cbCidadeNas,
				{
					layout : 'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [ { xtype : 'radio', fieldLabel : 'Sexo', boxLabel : 'Feminino', name : 'colaborador.sexo', inputValue : 'F', allowBlank : true },
										{ xtype : 'radio', boxLabel : 'Masculino', labelSeparator : '', name : 'colaborador.sexo', inputValue : 'M', allowBlank : true } ] },
							{
								columnWidth : .30,
								layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'Estado civil', hiddenName : 'colaborador.estCiv', store : storeEstCiv, valueField : 'id', displayField : 'name',
									typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um estado civil...', selectOnFocus : true, width : 120, id : 'fEstCiv',
									allowBlank : true, forceSelection : true } ] },
							{
								columnWidth : .20,
								layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'Nacionalidade', hiddenName : 'colaborador.nacionalidade.id', store : storeNac, valueField : 'id', displayField : 'name',
									typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione a nacionalidade...', selectOnFocus : true, width : 120, id : 'fNac',
									allowBlank : true, forceSelection : true } ] } ] },
				{
					layout : 'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'Estado de Nascimento', hiddenName : 'colaborador.estNas', store : storeEstNas, valueField : 'id', displayField : 'name',
									typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 150, id : 'fEstNas',
									allowBlank : true, forceSelection : true } ] },
							{
								columnWidth : .33,
								layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'País de Nascimento', hiddenName : 'colaborador.paiNas', store : storePaiNas, valueField : 'id', displayField : 'name',
									typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 150, id : 'fPaiNas', allowBlank : true,
									forceSelection : true } ] },
							{
								columnWidth : .33,
								layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'Tipo Salário', hiddenName : 'colaborador.tipSal', store : storeTipSal, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 150, id : 'fTipSal', allowBlank : true,
											forceSelection : true }]}]},
				{
					layout : 'column',
					items : [
							{ columnWidth : .33, layout : 'form', items : [ { fieldLabel : 'Nº de dependentes até 14 anos',name : 'colaborador.numDep', width : 25, xtype : 'textfield' } ] },
							{ columnWidth : .30, layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'Tipo Sanguíneo', hiddenName : 'colaborador.tipSan', store : storeTipSan, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um tipo sanguíneo...', selectOnFocus : true, width : 50, id : 'fTipSan',
											allowBlank : true, forceSelection : true } ] },
      									  {	columnWidth : .30,
											layout : 'form',
											items : [ { xtype : 'combo', fieldLabel : 'Raça Cor', hiddenName : 'colaborador.racCor', store : storeRacCor, valueField : 'id', displayField : 'name',
											     		typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione raça..', selectOnFocus : true, width : 120, id : 'fRacCor', allowBlank : true,
														forceSelection : true } ] } ] },								


				{
					layout : 'column',
					items : [
							{columnWidth :0.33 , layout : 'form', items : [ { fieldLabel : 'E-mail Particular', name : 'colaborador.emaPar',id : 'fEmaPar', width : 300, xtype : 'textfield', vtype: 'email' } ] },
							{columnWidth :0.33, layout : 'form', items : [ { fieldLabel : 'E-mail Comercial', name : 'colaborador.emaCom',id : 'fEmaCom', width : 300, xtype : 'textfield',vtype: 'email' } ] }
							] },	
											
				{
					xtype : 'fieldset',
					title : 'Formação Escolar',
					autoHeight : true,
					items : [
							{
								layout : 'column',
								items : [
										{columnWidth : .33, layout : 'form',
											items : [ { xtype : 'combo', fieldLabel : 'Grau de instrução', hiddenName : 'colaborador.grauInstrucao.id', store : storeGraIns, valueField : 'id',
												displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um grau de instrução...', selectOnFocus : true,
												width : 160, id : 'fGraIns', allowBlank : true, forceSelection : true, listeners : { 'blur' : { fn : function(field) {
													validaInsCur(field);// tiposValidacao.js
												} } } } ] },
										{columnWidth : .3, layout : 'form',
											items : [ new Ext.form.DateField({ fieldLabel : 'Data de conclusão', name : 'colaborador.datCon', width : 80, allowBlank : true, format : 'd/m/Y',
												id : 'fDatCon' }) ] } ] },
							{
								layout : 'column',
								items : [
										{ columnWidth : .33, layout : 'form', items : [ { fieldLabel : 'Curso', name : 'colaborador.nomCur', width : 250, id : 'fNomCur', xtype : 'textfield' } ] },
										{ columnWidth : .3, layout : 'form', items : [ { fieldLabel : 'Instituição', name : 'colaborador.nomIns', width : 250, id : 'fNomIns', xtype : 'textfield' } ] } ] } ] },
										
				{
					xtype : 'fieldset',
					title : 'Portador de Deficiência Física',
					autoHeight : true,
					labelWidth : 130,
					id : 'fdPorDefFis',
					items : [ {	columnWidth : .33,
								layout : 'form',
								items : [ {xtype : 'combo', fieldLabel : 'Possui Deficiência', hiddenName : 'colaborador.defFis', store : storeListSN, valueField : 'id', displayField : 'name',
				   			    		   typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fPosDef',
											allowBlank : true, forceSelection : true, listeners : { 'blur' : { fn : function(field) {
														        if (field.getValue() == 'S') {
																	Ext.getCmp('fDef').allowBlank = false;
																	Ext.getCmp('fDef').forceSelection = true;
																	Ext.getCmp('fDef').blankText = 'Campo obrigatório, de deficiência';
																	Ext.getCmp('fDef').enable();
																} else {
																	Ext.getCmp('fDef').allowBlank = true;
																	Ext.getCmp('fDef').disable();
																	Ext.getCmp('fDef').reset();
																}
												} } } } ] },
								{columnWidth : .30,
								 layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'Deficiência', hiddenName : 'colaborador.deficiencia.codDef', store : storeDef, valueField : 'codDef', displayField : 'desDef',
										  	typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione a deficiência...', selectOnFocus : true, width : 240, id : 'fDef', disabled: true,
											allowBlank : true, forceSelection : true, listeners : { beforequery : function(qe) {
																													delete qe.combo.lastQuery;
																													campo = 'fPosDef'  
																														 if ((Ext.getCmp(campo).value == undefined) || (Ext.getCmp(campo).value == "")) {
																															 Ext.Msg.show( {
																																	title : 'Alerta',
																																	msg : 'Verifique se possui Deficiencia.',
																																	buttons : Ext.MessageBox.OK,
																																	icon : Ext.MessageBox.WARNING
																																});
																																qe.cancel = true;
																														 }
												                                                                   } 
								                                                                   }
													} ] },]  
											 }
										] }
	return tabDadosCad;
}

function criaTabEnd() {
	
	var campos = [ 'codBai', 'nomBai'];
	var storeBairro = criaStoreReader('bairro', 'bairro!listAllXML.action', campos, 'codBai', 'storeBairro');
	
	var cbBairros = new Ext.form.ComboBox({xtype : 'combo', fieldLabel : 'Bairro', hiddenName : 'colaborador.bairro.id.codBai', store : storeBairro, valueField : 'codBai', displayField : 'nomBai',
		typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fBaiEnd',
		allowBlank : true});
	
	var campos = [ 'id', 'nomCid', 'estCid' ];
	var storeCidadeEnd = criaStoreReader('cidade', 'cidade!listAllXML.action', campos, 'id', 'storeCidade');
	
	var cidadeTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{nomCid}, {estCid}</span></h3></div></tpl>');
	
	campos = ['id','name'];
	var storeEstCtp = criaStoreReader('item', 'estCtp!listAllXML.action', campos, 'id', 'storeEstCtp');
	
	
	var cbCidadeRes = new Ext.form.ComboBox({ xtype : 'combo', store : storeCidadeEnd, id : 'fCidEnd', hiddenName : 'colaborador.cidEnd.id', fieldLabel : 'Cidade', displayField : 'nomCid',
		valueField : 'id', typeAhead : true, mode : 'remote', itemSelector : 'div.search-item', emptyText : 'Busca automática a partir de 4 caracteres...', loadingText : 'Procurando...',
		pageSize : 10, width : 350, tpl : cidadeTpl, triggerClass : 'x-form-search-trigger', triggerAction : 'all', hideTrigger : true, minChars : 3, queryParam : 'cidade.nomCid', allowBlank : true,
		forceSelection : true, listeners : {
			'select' : {
				fn : function(combo, record, index) {
					if ((Ext.getCmp('FEstEnd').value == undefined) || (Ext.getCmp('FEstEnd').value == "") || (Ext.getCmp('FEstEnd').value == "   ")) {
					     Ext.getCmp ('fCidEnd').clearValue ();
					     Ext.getCmp ('fCidEnd').lastQuery = null;	
						 Ext.Msg.show({title : 'Alerta', msg : 'Escolha antes o Estado.', buttons : Ext.MessageBox.OK, icon : Ext.MessageBox.WARNING});
						  
					}else{
					    Ext.getCmp ('fCidEnd').setRawValue (record.data.name);
					    Ext.getCmp ('fCidEnd').setValue (record.data.id);
					    var combo = Ext.getCmp ('fBaiEnd');
					    combo.clearValue ();
					    combo.store.proxy.conn.url = 'bairro!listAllXML.action';
					    combo.store.baseParams = {'codCid' : record.data.id};   
					    Ext.getCmp ('fCidEnd').collapse();
					    combo.lastQuery = null;		    
				   }
				}
			}
		}
	});
	
	
	var tabEnd = {
		title : 'Endereço',
		labelWidth : 72,
		items : [
				{ layout : 'column',
					items : [{ columnWidth : .4, layout : 'form', items : [ { fieldLabel : 'Rua', xtype : 'textfield', name : 'colaborador.endRua', width : 350, allowBlank : true, id : 'fEndRua' } ] },
							{ columnWidth : .2, layout : 'form', items : [ { fieldLabel : 'Nº', xtype : 'textfield', name : 'colaborador.endNum', width : 50, allowBlank : true, id : 'fEndNum', maxLength : 6 } ] },
							{ columnWidth : .3, layout : 'form', items : [ { fieldLabel : 'Complemento', xtype : 'textfield', name : 'colaborador.endCpl', maxLength : 25, width : 250 } ] } ] },
				{layout : 'column',
								items : [ { columnWidth : .3, layout : 'form', items : [ {xtype : 'combo', fieldLabel : 'Estado', hiddenName : 'colaborador.codEst', store : storeEstCtp, valueField : 'id', displayField : 'name',
									typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'FEstEnd',
									allowBlank : true, onSelect : function (record){
										Ext.getCmp ('FEstEnd').setRawValue (record.data.name);
									    Ext.getCmp ('FEstEnd').setValue (record.data.id);
										var combo = Ext.getCmp ('fCidEnd');
										combo.clearValue ();
										combo.store.proxy.conn.url = 'cidade!listAllXML.action';
										combo.store.baseParams = {'cidade.estCid' : record.data.id, 'codEst' : record.data.id, };
										Ext.getCmp ('FEstEnd').collapse ();
										combo.lastQuery = null;
									}	
								}]}]},
				{layout : 'column',
					items : [ { columnWidth : .4, layout : 'form', items : [ cbCidadeRes ] },
						{ columnWidth : .3, layout : 'form', items : [ cbBairros ] },
						{ columnWidth : .3, layout : 'form', items : [ { fieldLabel : 'CEP', xtype : 'numberfield', name : 'colaborador.cep', width : 90, allowBlank : true, maxLength : 9 } ] } ] },
				
				{ fieldLabel : 'Pessoa para contato', name : 'colaborador.nomCon', width : 200, xtype : 'textfield', maxLength : 20, allowBlank : true },
				{ layout : 'column',
					items : [{ columnWidth : .3, layout : 'form', items : [ { fieldLabel : 'DDD', xtype : 'textfield', name : 'colaborador.dddTel', width : 25, allowBlank : true, id : 'fdddTel', maxLength : 2 } ] },
						 { columnWidth : .3, layout : 'form', items : [ { fieldLabel : 'Telefone residencial', xtype : 'textfield', name : 'colaborador.numTel', width : 100, allowBlank : true } ] }] 
				},		
				{ layout : 'column',
					items : [{ columnWidth : .3, layout : 'form', items : [ { fieldLabel : 'DDD 2', xtype : 'textfield', name : 'colaborador.numDdd2', width : 25, allowBlank : true, id : 'fnumDdd2', maxLength : 2 } ] },
						 { columnWidth : .3, layout : 'form', items : [ { fieldLabel : 'Celular', xtype : 'textfield', name : 'colaborador.numCel', width : 100 , allowBlank : true} ] }]
				},
				{ fieldLabel : 'Outro telefone', xtype : 'textfield', name : 'colaborador.fonCon', width : 100 } ], 
		listeners : { 'activate' : function(p) {p.doLayout();} }
	}
	return tabEnd;
}

function criaTabDoc() {
	
	campos = [ 'id', 'name'];
	var storeEstCtp = criaStoreReader('item', 'estCtp!listAllXML.action', campos, 'id', 'storeEstCtp');
	
	var patt1 = new RegExp("[0-9]{11}");
	var tabDoc = {
		title : 'Documentos',
		labelWidth : 100,
		items : [
				{ xtype : 'textfield', fieldLabel : 'CPF', name : 'colaborador.numCpf', width : 100, id : 'cpf', width : 100, regex : patt1, regexText : 'Informe apenas números',
					listeners : { 'blur' : { fn : function(field, newVal, oldVal) {
						VerificaCPF();
					} } }, allowBlank : true },
				{
					layout : 'column',
					items : [
							{ columnWidth : .33, layout : 'form', items : [ { fieldLabel : 'Nº RG', name : 'colaborador.numCid', xtype : 'textfield', width : 100, allowBlank : true } ] },
							{ columnWidth : .33, layout : 'form',
								items : [ { fieldLabel : 'Órgão Emissor RG', name : 'colaborador.emiCid', xtype : 'textfield', width : 100, maxLength : 6, allowBlank : true } ] },
							{ columnWidth : .33, layout : 'form', items : [ new Ext.form.DateField({ fieldLabel : 'Data emissão', name : 'colaborador.dexCid', width : 100, allowBlank : true }) ] } ] },
				{
					layout : 'column',
					items : [ { columnWidth : .33, layout : 'form', items : [ { fieldLabel : 'Nº CTPS', name : 'colaborador.numCtp', width : 100, allowBlank : true, xtype : 'numberfield' } ] },
							{ columnWidth : .33, layout : 'form', items : [ { fieldLabel : 'Série da CTPS', name : 'colaborador.serCtp', xtype : 'textfield', width : 100, allowBlank : true } ] } ] },
				{
					layout : 'column',
					items : [{columnWidth : .33, layout : 'form', items : [ {xtype : 'textfield', fieldLabel : 'Digito verificador Ctp', name : 'colaborador.digCar', width : 100, id : 'FDigCar', allowBlank : true} ] },
							 {columnWidth : .33, layout : 'form', items : [ new Ext.form.DateField({ fieldLabel : 'Data Exp Ctp', name : 'colaborador.dexCtp', width : 100, allowBlank : true }) ] },
							 {columnWidth : .33, layout : 'form',
								          	items : [{xtype : 'combo', fieldLabel : 'Estado Ctp', hiddenName : 'colaborador.estCtp', store : storeEstCtp, valueField : 'id', displayField : 'name',
								          			 typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fEstCtp',
										        	 allowBlank : true, forceSelection : true }]}]},				
				{
					layout : 'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [ { fieldLabel : 'Nº PIS', name : 'colaborador.numPis', width : 100, xtype : 'numberfield', baseChars : '0123456789',
									allowNegative : false, id : 'fNumPis' } ] },
							{ columnWidth : .33, layout : 'form',
								items : [ { fieldLabel : 'Nº Cert. Reservista', name : 'colaborador.numRes', xtype : 'textfield', width : 100, id : 'fNumRes', maxLength : 13 } ] } ] },
				{
					layout : 'column',
					items : [
							{ columnWidth : .33, layout : 'form',
								items : [ { fieldLabel : 'Título de eleitor', name : 'colaborador.numEle', xtype : 'textfield', width : 100, allowBlank : true, maxLength : 12 } ] },
							{ columnWidth : .33, layout : 'form', items : [ { fieldLabel : 'Zona', name : 'colaborador.zonEle', xtype : 'textfield', width : 50, allowBlank : true, maxLength : 3 } ] },
							{ columnWidth : .33, layout : 'form',
								items : [ { fieldLabel : 'Sessão', name : 'colaborador.secEle', xtype : 'textfield', width : 50, allowBlank : true, maxLength : 4 } ] } ] },
				{
					layout : 'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [ { fieldLabel : 'Nº Carteira de Habilitação', name : 'colaborador.numCnh', xtype : 'textfield', width : 100, id : 'fNumCnh',
									listeners : { 'blur' : { fn : function(field) {
										if (field.getValue() != "") {
											Ext.getCmp('fCatCnh').allowBlank = false;
										} else {
											Ext.getCmp('fCatCnh').allowBlank = true;
										}
									} } } } ] },
							{ columnWidth : .33, layout : 'form', items : [ { fieldLabel : 'Categoria da CNH', name : 'colaborador.catCnh', xtype : 'textfield', width : 100, id : 'fCatCnh' } ] } ] } ] }
	return tabDoc;
}

function criaTabDadAdm() {
	campos = [ 'id', 'name', 'codLoc' ];
	var storeEmpresa = criaStoreReader('empresa', 'empresa!listAllXML.action', campos, 'id', 'storeEmpresa');
	campos = [ 'id', 'name' ];
	var storeCargo = criaStoreReader('cargo', 'cargo!listAllXML.action?cargo.id=', campos, 'id', 'storeCargo');

	var storeMotivo = criaStoreReader('motivo', 'motivo!listAllXML.action', campos, 'id', 'storeMotivo');
	var storeMeiTrans = criaStoreReader('item', 'meioTransporte!listAllXML.action', [ 'id', 'name' ], 'id', 'storeMeiTrans');
	var colaboradorTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{col__nomFun}</span></h3><h2>C�digo: {numCad} Empresa: {empresaId} - {nomEmp}</h2></div></tpl>');
	campos = [ 'colaboradorId', { name : 'colaborador.nomFun', mapping : '@nomFun' }, { name : 'col__nomFun', mapping : '@nomFun' }, 'numCad', { name : 'empresaId', mapping : 'empresa > id' },
			{ name : 'nomEmp', mapping : 'empresa > name' } ];
	var storeCol = criaStoreReader('colaborador', 'colaborador!listAllXML.action', campos, 'colaboradorId', 'storeCol');
	storeCol.setBaseParam('notFindDemitidos', true);// pr�-configurado para n�o
	// listar os demitidos
	campos = [ 'codSin', 'nomSin' ];
	var storeSindicato = criaStoreReader('sindicato', 'sindicato!listAllXML.action', campos, 'codSin', 'storeSindicato');
	
	campos = ['posTra', 'desPos' ];
	var storePostoTrabalho = criaStoreReader('postoTrabalho', 'postoTrabalho!listAllXML.action', campos, 'posTra', 'storePostoTrabalho');
	
	campos = [ 'natDes', 'nomNat' ];
	var storeNaturezaDespesa = criaStoreReader('naturezaDespesa', 'naturezaDespesa!listAllXML.action', campos, 'natDes', 'storeNaturezaDespesa');
	
	campos = [ 'codVin', 'desVin' ];
	var storeVinculo = criaStoreReader('vinculo', 'vinculo!listAllXML.action', campos, 'codVin', 'storeVinculo');
	
	campos = [ 'escVtr', 'nomEvt' ];
	var storeEscalaVT = criaStoreReader('escalaVT', 'escalaVT!listAllXML.action', campos, 'escVtr', 'storeEscalaVT');
	
	campos = ['id','name'];
	var storeAdmeSo = criaStoreReader('item', 'admeSo!listAllXML.action', campos, 'id', 'storeAdmeSo');
	var storeCateSo = criaStoreReader('item', 'cateSo!listAllXML.action', campos, 'id', 'storeCateSo');
	var storeCatAnt = criaStoreReader('item', 'catAnt!listAllXML.action', campos, 'id', 'storeCatAnt');
	var patt1 = new RegExp("[0-9]{14}");
	
	var tabDadosAdm = {
		title : 'Dados da admissão',
		labelWidth : 100,
		labelAlign : 'right',
		items : [
				{
					layout : 'column',
					items : [ {
						columnWidth : .6,
						layout : 'form',
						items : [ { xtype : 'combo', fieldLabel : 'Empresa', hiddenName : 'colaborador.empresa.id', store : storeEmpresa, valueField : 'id', displayField : 'name', typeAhead : true,
							mode : 'remote', triggerAction : 'all', emptyText : 'Selecione uma empresa...', selectOnFocus : true, width : 300, id : 'fEmp', allowBlank : true, forceSelection : true } ] }
					] },
				{
					layout : 'column',
					items : [{columnWidth : .6,layout : 'form',
							items : [ { xtype : 'combo', fieldLabel : 'Sindicato', hiddenName : 'colaborador.sindicato.codSin', store : storeSindicato, valueField : 'codSin', displayField : 'nomSin',
										typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um Sindicato...', selectOnFocus : true, width : 600, id : 'fSin', allowBlank : true,
										forceSelection : true} ] } ] },	
				{
					layout : 'column',
					items : [{columnWidth : .6,layout : 'form',
							items : [ { xtype : 'combo', fieldLabel : 'Posto Trabalho', hiddenName : 'colaborador.postoTrabalho.posTra', store : storePostoTrabalho, valueField : 'posTra', displayField : 'desPos',
										typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um Posto de Trabalho...', selectOnFocus : true, width : 600, id : 'fPosTra', allowBlank : true,
										forceSelection : true} ] } ] },
	
				{
					layout : 'column',
					items : [{columnWidth : .2,  layout : 'form', items : [ { xtype : 'combo', fieldLabel : 'Vinculo', hiddenName : 'colaborador.vinculo.codVin', store : storeVinculo, valueField : 'codVin', displayField : 'desVin',
																			typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um Vinvulo...', selectOnFocus : true, width : 150, id : 'fVinc', allowBlank : true,
																			forceSelection : true} ] },
							{columnWidth : .25,  layout : 'form', items : [ { xtype : 'combo', fieldLabel : 'Cargo', hiddenName : 'colaborador.cargo.id.codCar', store : storeCargo, valueField : 'id', displayField : 'name',
																			typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um cargo...', selectOnFocus : true, width : 200, id : 'fCar', allowBlank : true,
																			forceSelection : true, listeners : { 'blur' : { fn : function(field) {
																				ValidaDadosVigilante(field);// tiposValidacao.js
																			} } } } ] },
							{columnWidth : .25,  layout : 'form', items : [ { xtype : 'combo', fieldLabel : 'Natureza De Despesa', hiddenName : 'colaborador.naturezaDespesa.natDes', store : storeNaturezaDespesa, valueField : 'natDes', displayField : 'nomNat',
																							typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione a Natureza de Despesa...', selectOnFocus : true, width : 150, id : 'fNatDes', allowBlank : true,
																							forceSelection : true} ] }] },
				{
					layout : 'column',
					items : [
							{columnWidth : .2, layout : 'form',
								items : [ { xtype : 'combo', fieldLabel : 'Motivo da admissão', hiddenName : 'colaborador.motivo.id', store : storeMotivo, valueField : 'id', displayField : 'name',
									typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um motivo...', selectOnFocus : true, width : 120, id : 'fMot', allowBlank : true,
									forceSelection : true, listeners : { 'blur' : { fn : function(field) {
										if (field.getValue() == 1) {
											Ext.getCmp('fColSubs').allowBlank = false;
											Ext.getCmp('fColSubs').blankText = 'Para substituído, campo obrigatório';
											Ext.getCmp('fColSubs').enable();
										} else {
											Ext.getCmp('fColSubs').allowBlank = true;
											Ext.getCmp('fColSubs').disable();
											Ext.getCmp('fColSubs').reset();
										}
									} } } } ] },
							{columnWidth : .2, layout : 'form', items : [ new Ext.form.DateField({ fieldLabel : 'Data de admissão', name : 'colaborador.datAdm', width : 100, allowBlank : true }) ] },
							{columnWidth : .2, layout : 'form', items : [ { xtype : 'numberfield', allowNegative : false, decimalSeparator : '.',minValue: 1, fieldLabel : 'Percentual FGTS', name : 'colaborador.perJur', width : 50 } ] } ] },
				{
					layout : 'column',
					items : [
							{columnWidth : .2,
								layout : 'form',
								items : [ { xtype : 'numberfield', allowNegative : false, decimalSeparator : '.', fieldLabel : 'Salário Inicial', name : 'colaborador.valSal', allowBlank : true,
									width : 80 } ] },
							{columnWidth : .2, layout : 'form',
								items : [ { xtype : 'numberfield', allowNegative : false, decimalSeparator : '.', fieldLabel : 'Valor de Adicionais', name : 'colaborador.vlrAdi', width : 80 } ] },
							{columnWidth : .5,
								layout : 'form',
								items : [ { xtype : 'combo', store : storeCol, id : 'fColSubs', hiddenName : 'colaborador.colabSubs.id', fieldLabel : 'Colaborador substituído',
									displayField : 'colaborador.nomFun', valueField : 'colaboradorId', typeAhead : true, mode : 'remote', itemSelector : 'div.search-item',
									emptyText : 'Busca automática a partir de 4 caracteres...', loadingText : 'Procurando...', pageSize : 10, width : 350, tpl : colaboradorTpl,
									triggerClass : 'x-form-search-trigger', triggerAction : 'all', hideTrigger : true, minChars : 4, queryParam : 'colaborador.nomFun', forceSelection : true,
									listeners : { beforequery : function(qe) {
										delete qe.combo.lastQuery;
									} } } ] } ] },
				{
					xtype : 'fieldset',
					title : 'Vale Transporte',
					autoHeight : true,
					items : [ {layout : 'column',
								items : [
								{columnWidth : .2, layout : 'form',
										items : [ { xtype : 'combo', fieldLabel : 'Meio de transporte', hiddenName : 'colaborador.meiTrans', store : storeMeiTrans, valueField : 'id',
											displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um meio de transporte...', selectOnFocus : true,
											width : 120, id : 'fMeiTrans', forceSelection : true,  listeners : { 'blur' : { fn : function(field) {
												if (field.getValue() == 'C') {
													Ext.getCmp('fQtdMes').allowBlank = true;
													Ext.getCmp('fQtdMes').disable();
													Ext.getCmp('fQtdMes').reset();
													
													Ext.getCmp('fVlrMes').allowBlank = true;
													Ext.getCmp('fVlrMes').disable();
													Ext.getCmp('fVlrMes').reset();

													Ext.getCmp('fEscVT').allowBlank = true;
													Ext.getCmp('fEscVT').disable();
													Ext.getCmp('fEscVT').reset();
													
													Ext.getCmp('fDatIniVT').allowBlank = true;
													Ext.getCmp('fDatIniVT').disable();
													Ext.getCmp('fDatIniVT').reset();
													
													Ext.getCmp('fDatFimVT').allowBlank = true;
													Ext.getCmp('fDatFimVT').disable();
													Ext.getCmp('fDatFimVT').reset();
													
													Ext.getCmp('fQtdkm').allowBlank = false;
													Ext.getCmp('fQtdkm').enable();
													
													Ext.getCmp('fvlrCbt').allowBlank = false;
													Ext.getCmp('fvlrCbt').enable();
													
												}else{
												   if (field.getValue() == 'O') {
													Ext.getCmp('fQtdMes').allowBlank = false;
													Ext.getCmp('fQtdMes').enable();
													
													Ext.getCmp('fVlrMes').allowBlank = false;
													Ext.getCmp('fVlrMes').enable();

													Ext.getCmp('fEscVT').allowBlank = false;
													Ext.getCmp('fEscVT').enable();
													
													Ext.getCmp('fDatIniVT').allowBlank = false;
													Ext.getCmp('fDatIniVT').enable();

													Ext.getCmp('fDatFimVT').allowBlank = false;
													Ext.getCmp('fDatFimVT').enable();
													
													Ext.getCmp('fQtdkm').allowBlank = true;
													Ext.getCmp('fQtdkm').disable();
													Ext.getCmp('fQtdkm').reset();
													
													Ext.getCmp('fvlrCbt').allowBlank = true;
													Ext.getCmp('fvlrCbt').disable();
													Ext.getCmp('fvlrCbt').reset();
												  }	
												} 
											} } } } ] },
								{columnWidth : .2,layout : 'form',
										items : [ {xtype : 'combo', fieldLabel : 'Escala VT', hiddenName : 'colaborador.escalaVT.escVtr', disabled: true, store : storeEscalaVT, valueField : 'escVtr',
											displayField : 'nomEvt', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione uma Escala VT...', selectOnFocus : true,
											width : 120, id : 'fEscVT', forceSelection : true } ] },	
								{columnWidth : .2, layout : 'form',
									items : [ { xtype : 'numberfield', allowNegative : false, minValue: 1, decimalSeparator : '.', fieldLabel : 'Qtd. VT Mês', name : 'colaborador.qvtMes',disabled: true, id : 'fQtdMes', width : 80 } ] },
								{columnWidth : .2, layout : 'form',
									items : [ { xtype : 'numberfield', allowNegative : false, minValue: 1, decimalSeparator : '.', fieldLabel : 'Valor VT Mês', name : 'colaborador.vvtMes',disabled: true, id : 'fVlrMes', width : 80 } ] }
										] },
							{ layout : 'column',
								items : [{columnWidth : .2, layout : 'form',
										  items : [  new Ext.form.DateField({id : 'fDatIniVT', fieldLabel : 'Data de inicio Escala VT', name : 'colaborador.iniEvt',disabled: true, width : 80, allowBlank : true }) ] },
										 {columnWidth : .2, layout : 'form',
										  items : [  new Ext.form.DateField({id : 'fDatFimVT', fieldLabel : 'Data Final Escala VT', name : 'colaborador.fimEvt', disabled: true,width : 80, allowBlank : true }) ] },
										 {columnWidth : .2, layout : 'form',
												items : [ { xtype : 'numberfield', allowNegative : false, minValue: 1, decimalSeparator : '.', fieldLabel : 'Qtd. Km', name : 'colaborador.qvtKm',disabled: true, id : 'fQtdkm', width : 80 } ] },
										 {columnWidth : .2, layout : 'form',
												items : [ { xtype : 'numberfield', allowNegative : false, minValue: 3, decimalSeparator : '.', fieldLabel : 'Valor Combustível', name : 'colaborador.vlrCbt',disabled: true,id : 'fvlrCbt', width : 80 } ] }
											] }				
					 ]},
				{
					xtype : 'fieldset',
					title : 'eSocial',
					autoHeight : true,
					labelWidth : 94,
					layout : 'column',
						      items : [{columnWidth : .4, layout : 'form',
							          	items : [{xtype : 'combo', fieldLabel : 'Tipo Admissão eSocial', hiddenName : 'colaborador.admeSo', store : storeAdmeSo, valueField : 'id', displayField : 'name',
							          			 typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fAdmEso',
							          			 forceSelection : true },
							          			 {columnWidth : .33, layout : 'form', items : [ new Ext.form.DateField({ fieldLabel : 'Data Admissão Anterior', name : 'colaborador.admAnt', width : 100}) ]},
							          			 { xtype : 'textfield', fieldLabel : 'CNPJ Anterior', name : 'colaborador.cnpjAn', width : 100, id : 'fDatAdmAnt', width : 100, maxLength : 14}]},
								      { columnWidth : .33, layout : 'form', 
								        items : [{xtype : 'combo', fieldLabel : 'Categoria eSocial', hiddenName : 'colaborador.cateSo', store : storeCateSo, valueField : 'id', displayField : 'name',
								        		 typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fCatEso',
								        		 forceSelection : true },
								        		 { xtype : 'combo', fieldLabel : 'Categoria eSocial Anterior', hiddenName : 'colaborador.catAnt', store : storeCatAnt, valueField : 'id', displayField : 'name',
								        	       typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fCatAnt',
								        	       forceSelection : true },
								        	      { xtype : 'textfield', fieldLabel : 'Matricula Anterior', name : 'colaborador.matAnt', width : 100, id : 'FMatAnt', width : 100}]}]
				}						
		] }
	return tabDadosAdm;
}

function criaTabCompAdm(){
	campos = [ 'id', 'name'];
	
	var storeCatSef = criaStoreReader('item', 'catSef!listAllXML.action', campos, 'id', 'storeCatSef');
	var storeCodEtb = criaStoreReader('item', 'codEtb!listAllXML.action', campos, 'id', 'storeCodEtb');
	var storeTipAdmHfi = criaStoreReader('item', 'tipAdmHfi!listAllXML.action', campos, 'id', 'storeTipAdmHfi');	
	var storeIndAdm = criaStoreReader('item', 'indAdm!listAllXML.action', campos, 'id', 'storeIndAdm');
	var storeTipApo = criaStoreReader('item', 'tipApo!listAllXML.action', campos, 'id', 'storeTipApo');
	var storeTipCon = criaStoreReader('item', 'tipCon!listAllXML.action', campos, 'id', 'storeTipCon');	
	var storeOnuSce = criaStoreReader('item', 'onuSce!listAllXML.action', campos, 'id', 'storeOnuSce');	
	var storeLisRai = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeLisRai');
	var storeRatEve = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeRatEve');
	var storeRec13S = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeRec13S');
	var storeRecAdi = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeRecAdi');
	var storeResOnu = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeResOnu');
	var storeSocSinHsi = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeSocSinHsi');
	var storeEmiCar = criaStoreReader('item', 'litSimNao!listAllXML.action', campos, 'id', 'storeEmiCar');
	
	var tabDadosAdm = {
			title : 'Dados Complementares',
			labelWidth : 100,
			labelAlign : 'right',
			items : [
				{
					layout : 'column',
					items : [
							{columnWidth : .22, layout : 'form',
								items : [ {  xtype : 'textfield', fieldLabel : 'Numero da Conta FGTS', name : 'colaborador.conFgt', width : 100, id : 'FConFgt'} ] },
							{columnWidth : .22, layout : 'form', items : [ new Ext.form.DateField({ fieldLabel : 'Data da opção FGTS', name : 'colaborador.datOpc', width : 100}) ] },
							{columnWidth : .22, layout : 'form',
								 items : [ {  xtype : 'textfield', fieldLabel : 'Quantidade dependentes de IR', name : 'colaborador.depIrf', width : 25, id : 'FDepIrf'} ] },
							 ] },
				
				{
				 	items : [{columnWidth : .22, layout : 'form',
						        items : [{xtype : 'combo', fieldLabel : 'Categoria Sefip', hiddenName : 'colaborador.catSef', store : storeCatSef, valueField : 'id', displayField : 'name',
									     	typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 700, id : 'fCatSef',
				   					        forceSelection : true }]}]},	
				{
				   	layout : 'column',
					items : [{columnWidth : .4, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Estabilidade', hiddenName : 'colaborador.codEtb', store : storeCodEtb, valueField : 'id', displayField : 'name',
											 typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fCodEtb',
											 forceSelection : true }]},
							 {columnWidth : .22, layout : 'form', 
								 items : [ new Ext.form.DateField({ fieldLabel : 'Data Inicio da Estabilidade', name : 'colaborador.iniEtbHeb', width : 100}) ] },											
							 {columnWidth : .22, layout : 'form', 
								 items : [ new Ext.form.DateField({ fieldLabel : 'Data Fim da Estabilidade', name : 'colaborador.fimEtbHeb', width : 100}) ] }]},			 
				 {
					items : [{columnWidth : .22, layout : 'form',
				  	          	 items : [{xtype : 'combo', fieldLabel : ' Tipo Admissão', hiddenName : 'colaborador.tipAdmHfi', store : storeTipAdmHfi, valueField : 'id', displayField : 'name',
								     	   typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fTipAdmHfi',
										   forceSelection : true }]}]},
				{
					items : [{columnWidth : .22, layout : 'form',
					    	     items : [{xtype : 'combo', fieldLabel : 'Indicativo de Admissão', hiddenName : 'colaborador.indAdm', store : storeIndAdm, valueField : 'id', displayField : 'name',
								            typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fIndAdm',
								            forceSelection : true }]}]},
				{
				    items : [{columnWidth : .22, layout : 'form',
							     items : [{xtype : 'combo', fieldLabel : 'Aposentadoria', hiddenName : 'colaborador.tipApo', store : storeTipApo, valueField : 'id', displayField : 'name',
								  		    typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fTipApo',
											forceSelection : true }]}]},
				{
				    items : [{columnWidth : .22, layout : 'form',											
							  items : [{xtype : 'combo', fieldLabel : 'Tipo Contrato', hiddenName : 'colaborador.tipCon', store : storeTipCon, valueField : 'id', displayField : 'name',
								         typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 300, id : 'fTipCon',
										 forceSelection : true }]}]},							 
				{
					layout : 'column',
					items : [{columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Ônus da cessão', hiddenName : 'colaborador.onuSce', store : storeOnuSce, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 150, id : 'fOnuSce',
											forceSelection : true }]},
							 {columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Constar na RAIS', hiddenName : 'colaborador.lisRai', store : storeLisRai, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fLisRai',
											forceSelection : true }]},
							 {columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Entra no rateio de eventos', hiddenName : 'colaborador.ratEve', store : storeRatEve, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fRatEve',
											forceSelection : true }]}]},
				{
					layout : 'column',
					items : [{columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Recebe 13 salário', hiddenName : 'colaborador.rec13S', store : storeRec13S, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fRec13S',
											forceSelection : true }]},
							{columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Recebe Adiantamento salarial', hiddenName : 'colaborador.recAdi', store : storeRecAdi, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fRecAdi',
											forceSelection : true }]},
							{columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Ressarcimento Ônus', hiddenName : 'colaborador.resOnu', store : storeResOnu, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fResOnu',
											forceSelection : true }]}]},
				{
					layout : 'column',
					items : [{columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Sindicalizado', hiddenName : 'colaborador.socSinHsi', store : storeSocSinHsi, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fSocSinHsi',
											forceSelection : true }]},
							 {columnWidth : .22, layout : 'form',
								items : [{xtype : 'combo', fieldLabel : 'Emitir cartão ponto', hiddenName : 'colaborador.emiCar', store : storeEmiCar, valueField : 'id', displayField : 'name',
											typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione...', selectOnFocus : true, width : 100, id : 'fEmiCar',
											forceSelection : true }]}]},
				{
					xtype : 'fieldset',title : 'Contribuição Sindical', autoHeight : true, labelWidth : 400, id : 'fRdPagSin',
					items : [ {xtype : 'radiogroup', fieldLabel : 'Realizou o pagamento da Contribuição Sindical referente ao ano em exercício?',
							   columns : [ 100, 100 ],
							   items : [ { boxLabel : 'Sim', name : 'colaborador.pagSin', inputValue : true, value : true, id : 'fRdPagSinS' },
								         { boxLabel : 'Não', name : 'colaborador.pagSin', inputValue : false, id : 'fRdPagSinN' } ] } ] }											
											
											
			] }  
	return tabDadosAdm;
}

function criaTabLocal() {
	campos = [ { name : 'nomLoc', mapping : 'local > nomLoc', convert : function(v, rec) {
		var local = Ext.DomQuery.selectNode('local', rec);
		var codLoc = Ext.DomQuery.selectValue("codLoc", rec);
		return v + " - " + codLoc;
	} }, 'codLoc', { name : 'numLoc', mapping : 'local > id > numLoc' } ];
	var storeUnidade = criaStoreReader('hierarquia', 'local!listAllXML.action?nivel=2', campos, 'codLoc', 'storeUnidade');
	var storeCliente = criaStoreReader('hierarquia', 'local!listAllXML.action?nivel=3', campos, 'codLoc', 'storeCliente');
	storeCidadeLoc = criaStoreReader('hierarquia', 'local!listAllXML.action?nivel=4', campos, 'codLoc', 'storeCidadeLoc');
	storePosto = criaStoreReader('hierarquia', 'local!listAllXML.action?nivel=5', campos, 'numLoc', 'storePosto');
	campos = [ 'id', 'name', 'codLoc' ];
	var storeEmpresa = criaStoreReader('empresa', 'empresa!listAllXML.action', campos, 'id', 'storeEmpresa');
	var tabLocal = {
		title : 'Local',
		labelWidth : 120,
		autoHeight : true,
		defaults : { xtype : 'textfield', anchor : '100%' },
		items : [ { // fieldset
			xtype : 'fieldset',
			title : 'Local de trabalho',
			autoHeight : true,
			defaults : { border : false, style : 'padding: 0px' },
			items : [ {
				columnWidth : 0.5,
				layout : 'form',
				defaults : { xtype : 'textfield' },
				items : [
						{ xtype : 'checkbox', fieldLabel : '', boxLabel : 'Mesmo local do substituído', name : 'colaborador.mesmoLocal', width : 200, labelSeparator : '', inputValue : true,
							listeners : { 'check' : function() {
								if (this.checked) {
									Ext.getCmp('fEmpLoc').disable();
									Ext.getCmp('fEmpLoc').reset();
									Ext.getCmp('fCli').disable();
									Ext.getCmp('fCli').reset();
									Ext.getCmp('fCid').disable();
									Ext.getCmp('fCid').reset();
									Ext.getCmp('fPos').disable();
									Ext.getCmp('fPos').reset();
								} else {
									Ext.getCmp('fEmpLoc').enable();
									Ext.getCmp('fCli').enable();
									Ext.getCmp('fCid').enable();
									Ext.getCmp('fPos').enable();
								}
							} } },
						{ xtype : 'combo', fieldLabel : 'Empresa', hiddenName : 'empresaLocId', store : storeEmpresa, valueField : 'codLoc', displayField : 'name', typeAhead : true, mode : 'remote',
							triggerAction : 'all', emptyText : 'Selecione uma empresa...', selectOnFocus : true, width : 350, id : 'fEmpLoc', allowBlank : true, forceSelection : true,
							onSelect : function(record) {
								Ext.getCmp('fEmpLoc').setRawValue(record.data.codLoc + " - " + record.data.name);
								Ext.getCmp('fEmpLoc').setValue(record.data.codLoc);
								Ext.get('empresaLocId').value = record.data.codLoc;
								var combo = Ext.getCmp('fUni');
								combo.store.proxy.conn.url = 'local!listAllXML.action?nivel=2';
								combo.store.baseParams = { nivelAnterior : record.data.codLoc };
								combo.clearValue();
								Ext.getCmp('fCid').clearValue();
								Ext.getCmp('fPos').clearValue();
								combo.lastQuery = null;
								Ext.getCmp('fEmpLoc').collapse();
							} }, criaComboLocal(storeUnidade, 'fUni', 'Unidade', 'unidadeLocId', 'codLoc', false), criaComboLocal(storeCliente, 'fCli', 'Cliente', 'clienteLocId', 'codLoc', false),
						criaComboLocal(storeCidadeLoc, 'fCid', 'Cidade', 'cidadeLocId', 'codLoc', false), criaComboLocal(storePosto, 'fPos', 'Posto', 'colaborador.local.id.numLoc', 'numLoc', false) ] } ] } ] }
	return tabLocal;
}

function criaTabDadBan() {
	campos = [ 'id', 'name' ];
	var storeBanco = criaStoreReader('banco', 'banco!listAllXML.action', campos, 'id', 'storeBanco');
	var tabDadosBanco = {
		title : 'Dados bancários',
		labelWidth : 120,
		defaults : { xtype : 'textfield' },
		items : [
				{ xtype : 'checkbox', fieldLabel : '', boxLabel : 'Pagar com cheque', name : 'colaborador.pgtChq', width : 200, labelSeparator : '', id : 'fPgtChq', inputValue : true,
					listeners : { 'check' : function() {
						if (this.checked) {
							Ext.getCmp('fBan').disable();
							Ext.getCmp('fBan').reset();
							Ext.getCmp('fCodAge').disable();
							Ext.getCmp('fCodAge').reset();
							Ext.getCmp('fDigAge').disable();
							Ext.getCmp('fDigAge').reset();
							Ext.getCmp('fConBan').disable();
							Ext.getCmp('fConBan').reset();
							Ext.getCmp('fDigCon').disable();
							Ext.getCmp('fDigCon').reset();
						} else {
							Ext.getCmp('fBan').enable();
							Ext.getCmp('fCodAge').enable();
							Ext.getCmp('fDigAge').enable();
							Ext.getCmp('fConBan').enable();
							Ext.getCmp('fDigCon').enable();
						}
					} } },
				{ fieldLabel : '', xtype : 'checkbox', labelSeparator : '', boxLabel : 'Conta Poupança', name : 'colaborador.conPou', labelSeparator : '', inputValue : true, width : 200 },
				{ xtype : 'combo', fieldLabel : 'Banco', hiddenName : 'colaborador.banco.id', store : storeBanco, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote',
					triggerAction : 'all', emptyText : 'Selecione um banco...', selectOnFocus : true, width : 350, id : 'fBan', forceSelection : true },
				{ fieldLabel : 'Agência', xtype : 'textfield', name : 'colaborador.codAge', id : 'fCodAge', width : 90 },
				{ xtype : 'textfield', fieldLabel : 'Dígito Agência', name : 'colaborador.digAge', id : 'fDigAge', width : 50, maxLength : 2 },
				{ xtype : 'textfield', fieldLabel : 'Conta Corrente', name : 'colaborador.conBan', id : 'fConBan', width : 90 },
				{ xtype : 'textfield', fieldLabel : 'Dígito Conta Corrente', name : 'colaborador.digCon', maxLength : 3, id : 'fDigCon', width : 50 } ] }
	return tabDadosBanco;
}

function criaTabDadVig() {
	var tabDadosVigi = {
		title : 'Dados vigilante',
		labelWidth : 150,
		items : [
				{ xtype : 'textfield', name : 'colaborador.numDRT', fieldLabel : 'DRT nº', id : 'fNumDRT', maxLength : 30 },
				{ xtype : 'textfield', name : 'colaborador.numDip', fieldLabel : 'Certificado de Formação', id : 'fNumDip', maxLength : 25 },
				{ xtype : 'textfield', name : 'colaborador.orgDip', fieldLabel : 'Órgão expedidor do Certificado de Formação', id : 'fOrgDip', maxLength : 10 },
				{ xtype : 'datefield', name : 'colaborador.datFor', fieldLabel : 'Data do Curso de Formação', id : 'fDatFor' },
				{ xtype : 'datefield', name : 'colaborador.datRec', fieldLabel : 'Data do Curso de Reciclagem' },
				{
					xtype : 'fieldset',
					title : 'Habilitações',
					autoHeight : true,
					labelWidth : 0,
					items : [
							{ fieldLabel : '', xtype : 'checkbox', labelSeparator : '', boxLabel : 'Possui Extensão em Segurança Pessoal Privada', name : 'colaborador.extSegPes', labelSeparator : '',
								inputValue : true, width : 250 },
							{ fieldLabel : '', xtype : 'checkbox', labelSeparator : '', boxLabel : 'Possui Extensão em Transporte de Valores', name : 'colaborador.extTnsVal', labelSeparator : '',
								inputValue : true, width : 250 },
							{
								layout : 'column',
								items : [
										{
											columnWidth : .3,
											layout : 'form',
											items : [ { fieldLabel : '', xtype : 'checkbox', labelSeparator : '', boxLabel : 'Possui Carteira Nacional de Vigilante (CNV)',
												name : 'colaborador.temCNV', labelSeparator : '', inputValue : true, width : 250 } ] },
										{ columnWidth : .3, layout : 'form', items : [ { xtype : 'textfield', name : 'colaborador.numCnv', fieldLabel : 'Nº da CNV', id : 'fNumCnv' } ] },
										{ columnWidth : .3, layout : 'form', items : [ { xtype : 'datefield', name : 'colaborador.vctCNV', fieldLabel : 'Vencimento da CNV', id : 'fVctCNV' } ] } ] } ] } ] }
	return tabDadosVigi;
}

function criaTabObs() {
	var tabObs = { title : 'Observações', labelWidth : 120, defaults : { xtype : 'textarea' }, items : [ { fieldLabel : 'Observação', name : 'colaborador.obs', width : 350, height : 200 } ] }
	return tabObs;
}
function criaTabDep(){
	campos = ['id','name'];
	var storeSexo = criaStoreReader('item', 'sexo!listAllXML.action', campos, 'id', 'storeSexo');
	storeSexo.load();
	var storeGrauParantesco = criaStoreReader('item', 'grauParantesco!listAllXML.action', campos, 'id', 'storeGrauParantesco');
	storeGrauParantesco.load();
	var storeTipoDependenteESocial = criaStoreReader('item', 'tipoDependenteESocial!listAllXML.action', campos, 'id', 'storeTipoDependenteESocial');
	storeTipoDependenteESocial.load();
	var storeLitSimNaoDependente = criaStoreReader('item', 'litSimNaoDependente!listAllXML.action', campos, 'id', 'storeLitSimNaoDependente');
	storeLitSimNaoDependente.load();
	var storeEstadoCivilDependente = criaStoreReader('item', 'estadoCivilDependente!listAllXML.action', campos, 'id', 'storeEstadoCivilDependente');
	storeEstadoCivilDependente.load();
	var storeGraInsDep = criaStoreReader('grauInstrucao', 'grauInstrucao!listAllXML.action', campos, 'id', 'storeGraInsDep');
	storeGraInsDep.load();
	
	
	var textFieldNomDep = new Ext.form.TextField({name :'nomDep', id:'nomDep'});
	var textFieldNumCpf = new Ext.form.TextField({name :'numCpf', id:'numCpf'});
	var textFieldNomMae = new Ext.form.TextField({name :'nomMae', id:'nomMae'});
	
	var comboSexo = new Ext.form.ComboBox({hiddenName : 'sexo', store : storeSexo, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione o Sexo...', selectOnFocus : true, width : 70, id : 'fSexo', allowBlank : true});
	var comboGrauParantesco = new Ext.form.ComboBox({hiddenName : 'grauParantesco', store : storeGrauParantesco, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione o Grau de Parent...', selectOnFocus : true, width : 250, id : 'fGraPar', allowBlank : true});		
	var comboListSimNaoDependentePenJud = new Ext.form.ComboBox({hiddenName : 'penJud', store : storeLitSimNaoDependente, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione o Sim ou Não...', selectOnFocus : true, width : 150, id : 'fPenJud', allowBlank : true}); 
	var comboListSimNaoDependenteAviImp = new Ext.form.ComboBox({hiddenName : 'aviImp', store : storeLitSimNaoDependente, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione o Sim ou Não...', selectOnFocus : true, width : 150, id : 'fAviImp', allowBlank : true}); 
	var comboListSimNaoDependenteAuxCre = new Ext.form.ComboBox({hiddenName : 'auxCre', store : storeLitSimNaoDependente, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione o Sim ou Não...', selectOnFocus : true, width : 150, id : 'fAuxCre', allowBlank : true}); 
	var comboTipoDependenteESocial = new Ext.form.ComboBox({hiddenName : 'tipoDependenteESocial', store : storeTipoDependenteESocial, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione Tipo de dependente Esocial...', selectOnFocus : true, width : 150, id : 'fTipDep', allowBlank : true});
	var comboEstadoCivilDependente = new Ext.form.ComboBox({hiddenName : 'estCiv', store : storeEstadoCivilDependente, valueField : 'id', displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione Estado Civil...', selectOnFocus : true, width : 150, id : 'fEstCivDep', allowBlank : true});  
	var comboGraInsDep = new Ext.form.ComboBox({hiddenName : 'grauInstrucao', store : storeGraInsDep, valueField : 'id',
		displayField : 'name', typeAhead : true, mode : 'remote', triggerAction : 'all', emptyText : 'Selecione um grau de instrução...', selectOnFocus : true,
		width : 160, id : 'fGraInsDep', allowBlank : false, forceSelection : true}); 
	
	var stDep = new Ext.data.Store({remoteSort : true, 
		totalProperty : 'totalCount', 
		id : 'stDep', 
		proxy : new Ext.data.HttpProxy({url : 'colaborador!gravar.action?colaborador.local.id.tabOrg=2',method : 'POST'}), 
		reader : criaLeitorDependente()});
	
	var smDep = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
	var cmDep = new Ext.grid.ColumnModel({
		
		columns : [new Ext.grid.RowNumberer(), smDep, 
			      {header : 'Dependente', dataIndex : 'codDep', hidden : true},
				  {header : 'Nome', dataIndex :'nomDep', xtype:'gridcolumn', width : 250, fixed : true, editor : textFieldNomDep},
				  {header : 'Sexo', dataIndex :'sexo', editor :comboSexo, width : 90,  fixed : true, renderer : Ext.util.Format.comboRenderer(comboSexo, stDep)},
				  {header : 'Estado Civil', dataIndex :'estCiv',  width : 200, fixed : true, editor :comboEstadoCivilDependente, renderer : Ext.util.Format.comboRenderer(comboEstadoCivilDependente, stDep)},
				  {header : 'Grau Parent.',  dataIndex :'grauParantesco', fixed : true, width : 150, editor :comboGrauParantesco, renderer : Ext.util.Format.comboRenderer(comboGrauParantesco, stDep)},
				  {header : 'Tip. Dep Esocial',  dataIndex :'tipoDependenteESocial', width : 200, fixed : true,  editor :comboTipoDependenteESocial, renderer : Ext.util.Format.comboRenderer(comboTipoDependenteESocial, stDep)},
				  {header : 'CPF',  dataIndex :'numCpf', xtype:'gridcolumn', width : 100, fixed : true, editor : textFieldNumCpf},
				  {header : 'Mãe', dataIndex :'nomMae', xtype:'gridcolumn', width : 250, fixed : true, editor : textFieldNomMae},
				  {header : 'Data Nascimento', dataIndex : 'datNas', xtype : 'datecolumn', format : 'd/m/Y',  fixed : true, editor : {width : 100, format : 'd/m/Y', xtype : 'datefield', name : 'datNas', width : 100, id : 'fdatNas', AallowBlank : true}},
				  {header : 'Data Invalidez', dataIndex : 'datInv', xtype : 'datecolumn', format : 'd/m/Y',  fixed : true, editor : {width : 100, format : 'd/m/Y', xtype : 'datefield', name : 'datInv', width : 100, id : 'fdatInv', AallowBlank : true}},
				  {header : 'Data Ini Tutela', dataIndex : 'iniTut', xtype : 'datecolumn', format : 'd/m/Y',  fixed : true, editor : {width : 100, format : 'd/m/Y', xtype : 'datefield', name : 'iniTut', width : 100, id : 'finiTut', AallowBlank : true}},
				  {header : 'Data Fim Tutela', dataIndex : 'datTut', xtype : 'datecolumn', format : 'd/m/Y',  fixed : true, editor : {width : 100, format : 'd/m/Y', xtype : 'datefield', name : 'datTut', width : 100, id : 'fdatTut', AallowBlank : true}},
				  {header : 'Idade Limi. IR', dataIndex : 'limIrf', width : 100,  fixed : true, xtype : 'numbercolumn',  format : '0', align : 'right', fixed : true, editor : {xtype : 'numberfield', allowBlank : true, allowNegative : false, id : 'fLimIrf', name : 'limIrf'}},
				  {header : 'Idade Limi. Sal.Família', dataIndex : 'limSaf', width : 100,  fixed : true, xtype : 'numbercolumn',  format : '0', align : 'right', fixed : true, editor : {xtype : 'numberfield', allowBlank : true, allowNegative : false, id : 'fLimSaf', name : 'limSaf'}},
				  {header : 'Pensão Judicial', dataIndex :'penJud',  fixed : true, editor :comboListSimNaoDependentePenJud, renderer : Ext.util.Format.comboRenderer(comboListSimNaoDependentePenJud, stDep)},
				  {header : 'Aviso Impresso', dataIndex :'aviImp',  width : 100, fixed : true, editor :comboListSimNaoDependenteAviImp, renderer : Ext.util.Format.comboRenderer(comboListSimNaoDependenteAviImp, stDep)},
				  {header : 'Auxilio Creche', dataIndex :'auxCre',  width : 100, fixed : true, editor :comboListSimNaoDependenteAuxCre, renderer : Ext.util.Format.comboRenderer(comboListSimNaoDependenteAuxCre, stDep)},
				  {header : 'Grau de Instrução', dataIndex :'grauInstrucao',  width : 170,   fixed : true, editor :comboGraInsDep, renderer : Ext.util.Format.comboRenderer(comboGraInsDep, stDep)},
				  {header : 'Qtd Meses de Ax. Creche', dataIndex : 'nomCre', width : 100,  fixed : true, xtype : 'numbercolumn',  format : '0', align : 'right', fixed : true, editor : {xtype : 'numberfield', allowBlank : true, allowNegative : false, id : 'fNomCre', name : 'nomCre'}}  
				  ]});

	var gridDependentes = new Ext.grid.EditorGridPanel({
		store : stDep,
	    cm : cmDep,
		sm : smDep,
		stripeRows : true,
		height : 300,
		autoWidth : true,
		frame : true,
		layout : 'auto',
		title : 'Dependentes do Colaborador',
		id : 'gridDep',
		clicksToEdit : 1,
		tbar : [{text : 'Adicionar', iconCls: 'icnAdd', handler : function(){
			     var novaLinha = new Ext.data.Record({'nomDep':'','grauParantesco':'','tipoDependenteESocial':'','sexo':'','limIrf':'','limSaf':'','datNas':'','numCpf':'','nomMae':'','penJud':'','estCiv':'','grauInstrucao':'','datInv':'','iniTut':'','datTut':'','aviImp':'','auxCre':'','nomCre':''});
			     stDep.insert(stDep.getCount(),novaLinha);
			     gridDependentes.getView().refresh();
			     gridDependentes.getSelectionModel().selectRow(0);
			     gridDependentes.startEditing(stDep.getCount()+1,0);
		}},
		"-", 
		{text : 'Excluir',iconCls : 'icnExc', handler : function(){
			var linhaSelecionada = Ext.getCmp('gridDep').getSelectionModel().getSelected();
			if (linhaSelecionada) {
				Ext.Msg.show({title : 'Confirmar exclusão', msg : 'Tem certeza de que deseja excluir o dependente?', buttons : Ext.Msg.YESNO, icon : Ext.MessageBox.QUESTION,
					fn : function(button) {
						if (button == 'yes') {
							var myMask = new Ext.LoadMask(Ext.getBody(), {msg : 'Aguarde, excluindo dependente...'});
							myMask.show();
							Ext.Ajax.request({url : 'dependente!excluir.action',
								  params : {'codDep' : linhaSelecionada.get('codDep')},
								  success : function(result, request) {
								   	     myMask.hide();
									     Ext.Msg.show({title : 'Sucesso', msg : result.responseText, buttons : Ext.MessageBox.OK,
												 fn : function() {Ext.getCmp('gridDep').getStore().remove(linhaSelecionada);},
												 animEl : 'gridDep',
												 icon : Ext.MessageBox.INFO});
									    },
								  failure : function(result, request) {
									    myMask.hide();
									    Ext.Msg.show({title : 'Falha', msg : result.responseText, buttons : Ext.MessageBox.OK, animEl : 'gridDep', icon : Ext.MessageBox.ERROR});
								  }
								});
						}
					}
				
				});
				} else {
				Ext.Msg.show({title : 'Alerta', msg : 'Selecione um Dependente', buttons : Ext.MessageBox.OK, animEl : 'gridDep', icon : Ext.MessageBox.ERROR});
			}	
		} }]});
	
	var tabDep = { title:'Dependente', items:[gridDependentes]}
	return tabDep;
}

function criaTabHor (){
	
	campos = ['id','name'];
	var storeclasseEscalaRonda = criaStoreReader ('classeEscalaRonda','classeEscalaRonda!listAllXML.action',campos,'id','storeclasseEscalaRonda');
	
	campos = ['id','horSem','horMes','nomesc'];
	var storeEscalaRonda = criaStoreReader ('escalaHorarioRondaMZ','escalaHorarioRondaMZ!listFiltrosXML.action',campos,'id','storeEscalaRonda');
	
	var tabHorario = {
		title : 'Escala',
		labelWidth : 120,
		autoHeight : true,
		defaults : {xtype : 'textfield',anchor : '100%'},
		items : [{
			xtype : 'fieldset',
			title : 'Seleção de Escala',
			autoHeight : true,
			defaults : {border : false,style : 'padding: 0px'},
			items : [{
				columnWidth : 0.5,
				layout : 'form',
				defaults : {xtype : 'textfield'},
				items : [
					{xtype : 'combo',
					fieldLabel : 'Classe Escala',
					hiddenName : 'classeEscalaRonda.id',
					store : storeclasseEscalaRonda,
					valueField : 'id',
					displayField : 'name',
					typeAhead : true,
					mode : 'remote',
					triggerAction : 'all',
					emptyText : 'Selecione uma classe....',
					selectOnFocus : true,
					width : 350,
					id : 'fCla',
					allowBlank : true,
					forceSelection : true,
					onSelect : function (record){
						Ext.getCmp ('fCla').setRawValue (record.data.name);
						Ext.getCmp ('fCla').setValue (record.data.id);
						var combo = Ext.getCmp ('fEscRon');
						combo.clearValue ();
						combo.store.proxy.conn.url = 'escalaHorarioRondaMZ!listFiltrosXML.action';
						combo.store.baseParams = {'claesc' : record.data.id};
						Ext.getCmp ('fCla').collapse ();
						combo.lastQuery = null;
					}},criaComboEscala(storeEscalaRonda, 'fEscRon', 'Escala', 'colaborador.escalaHorarioRondaMZ.id', 'id',false)]}]},
					{
						xtype : 'fieldset',
						title : 'Informação da Escala',
						layout : 'column',
						autoHeight : true,
						defaults : {border : false,style : 'padding: 0px'},
						items : [{
							columnWidth : 0.5,
							layout : 'form',
							defaults : {xtype : 'textfield',width : 80},
							items : [{fieldLabel : 'Hora Semanal',name : 'horSemRon',xtype : 'textfield',id : 'fHorSem'},{fieldLabel : 'Hora Mês',name : 'horMesRon',xtype : 'textfield',id : 'fHorMes'}]}]},
					{
						xtype : 'fieldset',
						title : 'Horários',
					    layout : 'column',
					    autoHeight : true,
					    defaults : {border : false,style : 'padding: 0px'},
					    items : [{
							columnWidth : 2.0,
							layout : 'form',
							defaults : {xtype : 'textfield',width : 461},
							items : [criaGridHoras()]}]			   
					}
		]}
	return tabHorario;
}

function criaComboEscala(store, idComponente, label, hidName, campoValor,esconderTrigger) {
	var search = new Ext.form.ComboBox({ 
				labelWidth : 80,
				store : store,
				id : idComponente,
				hiddenName : hidName,
				fieldLabel : label,
				displayField : 'nomesc',
				valueField : campoValor,
				typeAhead : true,
				mode : 'remote',
				itemSelector : 'div.search-item',
				emptyText : 'Busca automática a partir de 4 caracteres...',
				loadingText : 'Procurando Escala...',
				pageSize : 10,
				width : 400,
				triggerClass : 'x-form-search-trigger',
				triggerAction : 'all',
				minChars : 4,
				allowBlank : true,
				hideTrigger : esconderTrigger,
				forceSelection : true,
				lastQuery : " ",
				tpl : new Ext.XTemplate('<tpl for="."><div class="search-item">','<h3><span>{nomesc}</span></h3></div></tpl>'),
				queryParam : 'nomesc',
				listeners : {
					'select' : {
						fn : function(combo, record, index) {
						      Ext.getCmp ('fHorSem').reset();
							  Ext.getCmp ('fHorSem').setRawValue ((record.data.horSem)/60);
							  Ext.getCmp ('fHorSem').disable();
							  
							  Ext.getCmp ('fHorMes').reset();
							  Ext.getCmp ('fHorMes').setRawValue ((record.data.horMes)/60);
							  Ext.getCmp ('fHorMes').disable(); 
							  
							  Ext.getCmp ('fHorEsc').store.proxy.conn.url = 'horarioRonda!listAllXML.action';
							  Ext.getCmp ('fHorEsc').store.baseParams = {'codEsc' : record.data.id};
							  Ext.getCmp ('fHorEsc').store.reload();
						
						}
					}
				
				}
			});
	
	if (idComponente == 'fEscRon') {
		 search.on('beforequery', function(qe) {
			 campo = 'fCla'  
				 if ((Ext.getCmp(campo).value == undefined) || (Ext.getCmp(campo).value == "")) {
					 Ext.Msg.show( {
							title : 'Alerta',
							msg : 'Escolha antes a Classe.',
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.WARNING
						});
						qe.cancel = true;
				 }
		 });
	} 
	return search;
}

function criaGridHoras(){
	campos = ['id','name', 'numSeq', 'diaSem'];
	var storeHorarioRonda = criaStoreReader ('horarioRonda','horarioRonda!listAllXML.action',campos,'numSeq','storeHorarioRonda');
	
    var grid = new Ext.grid.GridPanel({
        store: storeHorarioRonda,
        columns: [
        	  {
              	header   : 'Dia da Semana', 
                  width    : 200,  
                  dataIndex: 'diaSem'
              },
        	{
                header   : 'Código', 
                width    : 60,  
                dataIndex: 'id'
            },
            {
            	id       :'horario',
            	header   : 'Horário', 
                width    : 200,  
                dataIndex: 'name'
            }
        ],
        height: 400, 
        width: 461,
        title: 'Horarios da Escala',
        stateful: true,
        stateId: 'fHorEsc', 
        id: 'fHorEsc'
    });
    
    return grid;
}

function validadorCNPJ() {

	if (validarCNPJ(document.getElementById("fCnpj").value)) {
			document.getElementById("colaborador.empresa.id").focus();
		} else {
			errors = "1";
			if (errors) {
				Ext.Msg.show( {
					title :'Aviso',
					msg :'CNPJ inválido',
					buttons :Ext.MessageBox.OK,
					icon :Ext.MessageBox.WARNING,
					fn : function() {
						document.getElementById("fCnpj").focus();
					}
				});
			}
		}
}

function validarCNPJ(cnpj) {
    cnpj = cnpj.replace(/[^\d]+/g,'');
    if(cnpj == '') return false;
    if (cnpj.length != 14) return false;
 
    // Elimina CNPJs invalidos conhecidos
    if (cnpj == "00000000000000" || 
        cnpj == "11111111111111" || 
        cnpj == "22222222222222" || 
        cnpj == "33333333333333" || 
        cnpj == "44444444444444" || 
        cnpj == "55555555555555" || 
        cnpj == "66666666666666" || 
        cnpj == "77777777777777" || 
        cnpj == "88888888888888" || 
        cnpj == "99999999999999")
        return false;
         
    // Valida DVs
    tamanho = cnpj.length - 2
    numeros = cnpj.substring(0,tamanho);
    digitos = cnpj.substring(tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(0))
        return false;
         
    tamanho = tamanho + 1;
    numeros = cnpj.substring(0,tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(1))
          return false;
           
    return true;
    
}




