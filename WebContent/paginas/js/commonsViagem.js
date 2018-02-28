/**
 * Cria um XmlReader que recebe dados de viagens
 * 
 * @return Ext.data.XmlReader
 */
function criaLeitorViagem() {
	var leitorDeViagens = new Ext.data.XmlReader({
				record : 'viagem',
				id : 'viagem.id',
				totalProperty : 'totalCount'
			}, [{
						name : 'viagem.id',
						mapping : '@viagemId'
					}, {
						name : 'viagem.datSol',
						mapping : '@datSol'
					}, {
						name : 'viagem.itnVgm',
						mapping : '@itnVgm'
					}, {
						name : 'viagem.datSai',
						mapping : '@datSai'
					}, {
						name : 'viagem.datChe',
						mapping : '@datChe'
					}, {
						name : 'viagem.colaborador.id',
						mapping : 'colaborador > @id'
					}, {
						name : 'viagem.colaborador.nomFun',
						mapping : 'colaborador > @nomFun'
					}, {
						name : 'viagem.colaborador.numCad',
						mapping : 'colaborador > @numCad'
					}, {
						name : 'viagem.solicitante.id',
						mapping : 'solicitante > @id'
					}, {
						name : 'viagem.solicitante.nomFun',
						mapping : 'solicitante > @nomFun'
					}, {
						name : 'viagem.solicitante.numCad',
						mapping : 'solicitante > @numCad'
					}, {
						name : 'viagem.usuario.id',
						mapping : 'usuario > @id'
					}, {
						name : 'viagem.usuario.nomFun',
						mapping : 'usuario > @nomFun'
					}, {
						name : 'viagem.usuario.numCad',
						mapping : 'usuario > @numCad'
					}, {
						name : 'viagem.motivo.id',
						mapping : 'motivo > @id'
					}, {
						name : 'viagem.motivo.desMtv',
						mapping : 'motivo > @desMtv'
					}, {
						name : 'viagem.situacao',
						mapping : 'situacao > @id'
					}, {
						name : 'viagem.situacao.descricao',
						mapping : 'situacao > @descricao'
					}, {
						name : 'viagem.cplMtv',
						mapping : '@cplMtv'
					}, {
						name : 'viagem.datInc',
						mapping : '@datInc'
					}, {
						name : 'viagem.empresa.id',
						mapping : 'empresa > @id'
					}]);
	return leitorDeViagens;
}
/**
 * Cria um XmlReader que recebe dados de adiantamentos
 * 
 * @return Ext.data.XmlReader
 */
function criaLeitorAdiantamento() {
	var leitorAdt = new Ext.data.XmlReader({
				record : 'adiantamento',
				id : 'id',
				totalProperty : 'totalCount'
			}, [{
						name : 'id.id',
						mapping : '@id'
					}, {
						name : 'datAdt',
						mapping : '@datAdt',
						type : 'date',
						dateFormat : 'd/m/Y'
					}, {
						name : 'vlrAdt',
						mapping : '@vlrAdt'
					}, {
						name : 'id.viagem.id',
						mapping : '@viagemId'
					}, {
						name : 'datAce',
						mapping : '@datAce',
						type : 'date',
						dateFormat : 'd/m/Y'
					}]);
	return leitorAdt;
}
/**
 * Cria um XmlReader que recebe dados de despesas
 * 
 * @return Ext.data.XmlReader
 */
function criaLeitorDespesaViagem() {
	var leitorDesVia = new Ext.data.XmlReader({
				record : 'despesaViagem',
				totalProperty : 'totalCount'
			}, [{
						name : 'id.viagem.id',
						mapping : '@viagemId'
					}, {
						name : 'id.centro.id',
						mapping : '@centroId'
					}, {
						name : 'id.despesa.id',
						mapping : '@despesaId'
					}, {
						name : 'vlrDes',
						mapping : '@vlrDes'
					}, {
						name : 'desDes',
						mapping : '@desDes'
					}, {
						name : 'nomCcu',
						mapping : '@nomCcu'
					}, {
						name : 'adiantamentoViagem.id.id',
						mapping : '@adiantamentoId'
					}, {
						name : 'adiantamentoViagem.id.viagem.id',
						mapping : '@viagemAdtId'
					}, {
						name : 'datAce',
						mapping : '@datAce',
						type : 'date',
						dateFormat : 'd/m/Y'
					}]);
	return leitorDesVia;
}
/**
 * Altera a cor da linha de uma grid conforme a situaï¿½ï¿½o da viagem. Se Fechado =
 * Azul, Se Aberto = Vermelho
 * 
 * @param {}
 *            record
 * @param {}
 *            index
 * @return {String}
 */
function MudaCorVia(record, index) {
	var change = record.get('viagem.situacao');
	if (change == 'FE')
		return 'corFechado';
	if (change == 'AB')
		return 'corAberto';
}
/** CRIA tela com a relaï¿½ï¿½o de adiantamentos da viagem * */
function criaGridAdt(r, st) {
	var smAdt = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	var cmAdt = new Ext.grid.ColumnModel({
				columns : [new Ext.grid.RowNumberer(), smAdt, {
							header : 'Viagem',
							dataIndex : 'id.viagem.id',
							hidden : true
						}, {
							header : 'Id',
							dataIndex : 'id.id',
							hidden : true
						}, {
							header : 'Valor',
							dataIndex : 'vlrAdt',
							width : 20,
							xtype : 'numbercolumn',
							align : 'right'
						}, {
							header : 'Data depósito',
							dataIndex : 'datAdt',
							xtype : 'datecolumn',
							format : 'd/m/Y'
						}, {
							header : 'Data Acerto',
							dataIndex : 'datAce',
							xtype : 'datecolumn',
							format : 'd/m/Y'
						}]
			});
	var gridAdiantamento = new Ext.grid.GridPanel({
				store : st,
				cm : cmAdt,
				sm : smAdt,
				frame : true,
				loadMask : true,
				layout : 'auto',
				height : 150,
				autoWidth : true,
				title : 'Adiantamentos desta viagem',
				id : 'gridAdts',
				clicksToEdit : 1,
				viewConfig : {
					forceFit : true,
					getRowClass : MudaCorViagem
				},
				tbar : [{
							text : 'Alterar viagem',
							iconCls : 'icnAlt',
							handler : function() {
								alteraViagem()
							}
						}]
			});
	return gridAdiantamento;
}
/** CRIA tela com a relaï¿½ï¿½o de despesas da viagem * */
function criaGridDes(r, stDes) {
	var smDes = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	var cmDes = new Ext.grid.ColumnModel({
				columns : [new Ext.grid.RowNumberer(), smDes, {
							header : 'Viagem',
							dataIndex : 'id.viagem.id',
							hidden : true
						}, {
							header : 'Despesa',
							dataIndex : 'desDes',
							width : 130,
							fixed : true
						}, {
							header : 'Centro',
							dataIndex : 'nomCcu',
							width : 130,
							fixed : true
						}, {
							header : 'Valor',
							dataIndex : 'vlrDes',
							width : 70,
							xtype : 'numbercolumn',
							align : 'right'
						}, {
							header : 'Data Acerto',
							dataIndex : 'datAce',
							xtype : 'datecolumn',
							format : 'd/m/Y'
						}]
			});
	var gridDespesas = new Ext.grid.EditorGridPanel({
				store : stDes,
				loadMask : true,
				cm : cmDes,
				sm : smDes,
				stripeRows : true,
				height : 150,
				autoWidth : true,
				frame : true,
				layout : 'auto',
				title : 'Despesas desta viagem',
				id : 'gridDes',
				clicksToEdit : 1,
				viewConfig : {
					forceFit : true,
					getRowClass : MudaCorViagem
				},
				tbar : [{
							text : 'Reimprimir acerto',
							iconCls : 'icnPrt',
							handler : function() {
								imprimeAcerto()
							}
						}]
			});
	return gridDespesas;
}
/**
 * Cria tela de exibiï¿½ï¿½o de detalhes da viagem, com grid de adiantamentos e de
 * despesas *
 */
function criaJanelaDetalhes(items, record) {
	var window = new Ext.Window({
				id : 'winDetailViagem',
				title : 'Detalhes da viagem ' + record.get('viagem.id')
						+ ' de ' + record.get('viagem.colaborador.nomFun'),
				width : 600,
				height : 350,
				autoHeight : true,
				layout : 'auto',
				border : false,
				modal : true,
				items : items,
				renderTo : 'pnResConVia',
				buttons : [{
							text : 'Fechar',
							handler : function() {
								window.close();
							}
						}]
			});
	return window;
}