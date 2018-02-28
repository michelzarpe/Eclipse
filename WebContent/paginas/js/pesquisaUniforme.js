function geraTelaPesqUni() {
	var formTelaPesqUni = new Ext.form.FormPanel( {
		labelWidth :100,
		frame :true,
		bodyStyle :'padding:5px 5px 0',
		defaultType :'textfield',
		defaults : {
			width :250
		},
		items : [ {
			fieldLabel :'Nome ou parte do nome',
			name :'uniforme.desEpi',
			id :'fDesEpi',
			xtype :'textfield'
		},{
			fieldLabel :'Complemento',
			name :'uniforme.cplDes',
			id :'fCplDes',
			xtype :'textfield'
		}  ]
	});
	var windowTelaPesqUni = new Ext.Window( {
		title :'Parametrização da pesquisa de uniformes',
		width :450,
		height :250,
		minWidth :350,
		minHeight :250,
		layout :'fit',
		plain :true,
		bodyStyle :'padding: 5px',
		buttonAlign :'center',
		items :formTelaPesqUni,
		buttons : [ {
			text :'Executar',
			handler : function() {
				var leitorUni = criaLeitorUni();
				var stUni = new Ext.data.Store( {
					remoteSort :true,
					totalProperty :'totalCount',
					proxy :new Ext.data.HttpProxy( {
						url :'pesquisaUniforme!buscaXML.action?uniforme.tipoEPI.id=',
						method :'POST'
					}),
					reader :leitorUni,
					baseParams: formTelaPesqUni.getForm().getValues()
				});
				
				stUni.setDefaultSort('desEpi', 'DESC');
				
				var sm = new Ext.grid.CheckboxSelectionModel( {
					singleSelect :true
				});
				
				var sPag = Ext.getCmp('sPag');
				
				if (!sPag) {
					pagingBar = criaPaginador(stUni);
				}
				
				var sGridUni = Ext.getCmp('sGrid');
				if (!sGridUni) {
					sGridUni = criaGridUni(sm, pagingBar, stUni);
					stUni.load( {
						params : {
							start :0,
							limit :25
						}
					});
					sGridUni.render();
				} else {
					stUni.reload( {
						url :'pesquisaUniforme!buscaXML.action',
						params : {
							start :0,
							limit :25
						}
					});
					sGridUni.destroy();
					pagingBar = criaPaginador(stUni);
					sGridUni = criaGridUni(sm, pagingBar, stUni);
				}
				var win = new Ext.Window( {
					title :'Resultado da consulta de uniformes',
					width :830,
					renderTo: 'pnCenter',
					height :480,
					layout :'fit',
					border :false,
					modal :true,
					items : [ sGridUni ],
					maximizable : true
				});
				win.show();
			}
		} ]
	});
	windowTelaPesqUni.show();
	windowTelaPesqUni.center();
}