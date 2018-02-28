function geraParametros() {
	campos = [ 'id', 'name' ];
	var storeSitItm = criaStoreReader('item', 'situacaoItem!listAllXML.action',	campos, 'id');
	var formConf = new Ext.form.FormPanel( {
		labelWidth :100,
		frame :true,
		bodyStyle :'padding:5px 5px 0',
		defaultType :'datefield',
		width :350,
		autoHeight :true,
		items : [ {
			fieldLabel :'Nome ou parte do nome do colaborador',
			name :'colaborador.nomFun',
			id :'colaborador.nomFun',
			xtype :'textfield',
			anchor :'100%'
		}, {
			fieldLabel :'Cadastro do colaborador',
			name :'colaborador.numCad',
			id :'colaborador.numCad',
			xtype :'textfield',
			widht :50
		}, {
			fieldLabel :'Data de solicitação inicial',
			name :'datIni',
			id :'datIni',
			xtype :'datefield',
			endDateField :'datFin' // id of the end date field
		}, {
			fieldLabel :'Data de solicitação final',
			name :'datFin',
			id :'datFin',
			xtype :'datefield',
			startDateField :'datIni' // id of the start date field
		}, {
			fieldLabel :'Data de processamento',
			name :'datPro',
			id :'datPro',
			xtype :'datefield',
			startDateField :'datPro' // id of the start date field
		}, {
			fieldLabel :'Código da solicitação',
			name :'solicitacao.id',
			id :'idSol',
			xtype :'textfield'
		}, {
			fieldLabel :'N da DR',
			name :'solicitacao.numSeq',
			id :'numSeq',
			xtype :'textfield',
			width :50
		}]
	});
	var windowConf = new Ext.Window( {
		title :'Parametrização da pesquisa de solicições',
		width :500,
		height :300,
		minWidth :300,
		minHeight :200,
		autoHeight :true,
		layout :'fit',
		renderTo :'pnCenter',
		plain :true,
		bodyStyle :'padding: 5px',
		buttonAlign :'center',
		modal :true,
		items :formConf,
		buttons : [ {
			text :'Executar',
			handler : function() {
				leitorX = criaLeitorSol();
				var stSol = new Ext.data.Store( {
					remoteSort :true,
					totalProperty :'totalCount',
					proxy :new Ext.data.HttpProxy( {
						url :'pesquisa!buscaXML.action',
						method :'POST'
					}),
					reader :leitorX,
					baseParams :formConf.getForm().getValues()
				});
				var sm = new Ext.grid.CheckboxSelectionModel( {
					singleSelect :true
				});
				var sPag = Ext.getCmp('sPag');
				if (!sPag) {
					pagingBar = criaPaginador(stSol);
				}
				sGrid = criaGridSol(sm, pagingBar, stSol);

				var tb = sGrid.getTopToolbar();

				sGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
					geraListaItensPesq(r, stSol);
				});

				var win = new Ext.Window( {
					title :'Resultado da pesquisa de solicitações',
					renderTo :'pnCenter',
					width :830,
					height :480,
					layout :'fit',
					border :false,
					modal :true,
					maximizable :true,
					items : [ sGrid ]
				});
				win.show();
				win.center();
				stSol.setDefaultSort('col_nomFun', 'DESC');
				stSol.load( {
					params : {
						start :0,
						limit :25
					}
				});
			}// function
		} ]
	});
	windowConf.show();
	windowConf.center();
}

function geraListaItensPesq(record, st) {
	leitorXML = geraLeitorItens();
	var storeItens = new Ext.data.Store( {
		url :'itemSolicitacao!libXML.action?idSol=' + record
				.get('solicitacao.id'),
		reader :leitorXML
	});
	var sm2 = new Ext.grid.CheckboxSelectionModel( {
		singleSelect :false
	});

	var tb = new Ext.Toolbar( {
		id : 'toolBar',
		items : [ new Ext.Button( {
			text : 'Excluir',
			iconCls : 'icnExc',
			tooltip : 'Excluir o item selecionado',
			handler : excluirItem,
			id : 'btExc'
		}), new Ext.Button( {
			text : 'Atender',
			iconCls : 'icnAte',
			tooltip : 'Atender o item selecionado',
			handler : atenderItem,
			id : 'btAte'
		}), new Ext.Button( {
			text : 'Processar',
			iconCls : 'icnPrc',
			tooltip : 'Processa os itens selecionados',
			handler : processaItem,
			id : 'btPro'
		}), new Ext.Button( {
			text : 'Reimprimir DR',
			iconCls : 'icnPrt',
			tooltip : 'Reimprime a DR da solicitação',
			handler : reimprimeDR,
			id : 'btImp'
		}), new Ext.Button( {
			text : 'Estornar',
			iconCls : 'icnEst',
			tooltip : 'Estornar o envio',
			handler : estornarItem,
			id : 'btExt'
		}), "-", {
			text : 'Alterar',
			iconCls : 'icnCom',
			tooltip : 'Alterar solicitação',
			handler : alteraSolicitacao,
			id : 'btAltSol'
		}, "-" ]
	});
	var gridItens = criaGridItens(storeItens, sm2, tb);
	var winDetail = criaJanelaItens(gridItens, record);
	winDetail.on('close', function() {
		winDetail.destroy();
	});

	winDetail.show();
	winDetail.center();
	storeItens.load();
}
