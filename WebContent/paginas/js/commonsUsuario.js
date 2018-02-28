function criaLeitorUsuario() {
	leitorUsu = new Ext.data.XmlReader( {
		record : 'usuario',
		id : 'usuario.id',
		totalProperty : 'totalCount'
	}, [ {
		name : 'usuario.id',
		mapping : 'usuarioId'
	}, {
		name : 'usuario.codUsu',
		mapping : '@codUsu'
	}, {
		name : 'usuario.email',
		mapping : '@email'
	}, {
		name : 'usuario.recebeAviso',
		mapping : '@recebeAviso'
	}, {
		name : 'usuario.tipUsu',
		mapping : 'tipUsu > @id'
	}, {
		name : 'usuario.senUsu',
		mapping : '@senUsu'
	}, {
		name : 'novaSenha',
		mapping : '@senUsu'
	}, {
		name : 'usuario.gerente.id',
		mapping : 'gerente > @id'
	}, {
		name : 'usuario.nomFun',
		mapping : '@nomFun'
	}, {
		name : 'usuario.gerente.nomFun',
		mapping : 'gerente > @nomGer'
	}, {
		name : 'usuario.afastamento.id',
		mapping : 'afastamento > id'
	}, {
		name : 'usuario.afastamento.desSit',
		mapping : 'afastamento > desSit'
	} ]);
	return leitorUsu;
}
function criaGridUsu(sm, pb, st) {
	var tb = new Ext.Toolbar( {
		id : 'tBarGridUsu'
	});
	grid = new Ext.grid.GridPanel( {
		store : st,
		loadMask : true,
		cm : new Ext.grid.ColumnModel( [ new Ext.grid.RowNumberer(), sm, {
			header : 'Cod.',
			dataIndex : 'usuario.id',
			sortable : true,
			width : 20
		}, {
			header : "Colaborador",
			width : 100,
			dataIndex : 'usuario.nomFun',
			sortable : true
		}, {
			header : "Código",
			width : 50,
			dataIndex : 'usuario.codUsu',
			sortable : true
		}, {
			header : 'Gerente',
			dataIndex : 'usuario.gerente.nomFun',
			sortable : true
		}, {
			header : 'Tipo',
			width : 50,
			dataIndex : 'usuario.tipUsu',
			sortable : true
		}, {
			header : 'RecebeAviso',
			width : 50,
			dataIndex : 'usuario.recebeAviso',
			sortable : true
		} ]),
		sm : sm,
		tbar : tb,
		bbar : pb,
		height : 350,
		id : 'sGridUsu',
		viewConfig : {
			forceFit : true,
			getRowClass : MudaCorCol
		},
		frame : true,
		iconCls : 'icon-grid'
	});
	return grid;
}