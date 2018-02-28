function criaStoreReq(tipoMaterial) {
	var rProxy = new Ext.data.HttpProxy({
		api : {
			create : 'requisicaoEstoque!gravar.action?tipoMaterial='
					+ tipoMaterial,
			read : 'requisicaoEstoque!findByDateCli.action?tipoMaterial='
					+ tipoMaterial,
			update : 'requisicaoEstoque!gravar.action?tipoMaterial='
					+ tipoMaterial,
			destroy : 'requisicaoEstoque!excluiItem.action'
		}
	});
	Ext.Ajax.timeout = 3000000;

	/**
	 * Este trecho � para resolver o bug que tem na vers�o 3.1.1 onde n�o
	 * funciona o pruneModifiedRecords do Store e sempre fica sujeira depois que
	 * se remove um item.
	 */
	Ext.override(Ext.data.Store, {
		removeAll : function() {
			this.data.each(function(rec) {
				rec.join(null);
			});
			this.data.clear();
			if (this.snapshot) {
				this.snapshot.clear();
			}
			if (this.pruneModifiedRecords) {
				this.modified = [];
			}
			this.fireEvent('clear', this);
		}
	});

	var rWriter = new Ext.data.JsonWriter({
		encode : true,
		writeAllFields : true
	});

	var rJsonReader = getReaderStoreReq();

	var rJsonStore = new Ext.data.Store({
		proxy : rProxy,
		storeId : 'rStore',
		autoLoad : false,
		remoteSort : true,
		autoSave : false,
		writer : rWriter,
		reader : rJsonReader,
		pruneModifiedRecords : true
	});
	rJsonStore.setDefaultSort('produto.desPro', 'ASC');

	return rJsonStore;
}

/** valida os campos da grid de requisi��es * */
function validaReq(idGrid) {
	var grid = Ext.getCmp(idGrid);
	var records = grid.store.getModifiedRecords();
	var fields = grid.store.fields.items;
	var colunasGrid = grid.getColumnModel().columns;
	for ( var row = 0; row < records.length; row++) {
		var record = records[row];
		for ( var col = 0; col < colunasGrid.length; col++) {
			var nroColuna = grid.getColumnModel().findColumnIndex(
					fields[col].name);
			if (nroColuna > 0) {
				var cellEditor = grid.getColumnModel().getCellEditor(nroColuna,
						row);
				if (cellEditor != undefined) {
					if (!cellEditor.field.isValid()) {
						grid.startEditing(grid.store.indexOfId(record.id),
								nroColuna);
						return false;
					}
				}
			}
		}
	}
	return true;
}

function excluiReq(idGrid) {
	var linhaSelecionada = Ext.getCmp(idGrid).getSelectionModel().getSelected();
	if (linhaSelecionada) {
		Ext.Msg.show({
			title : 'Confirmar exclusão',
			msg : 'Tem certeza de que deseja excluir o item '
					+ linhaSelecionada.get('produto.desPro') + '?',
			buttons : Ext.Msg.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : function(button) {
				if (button == 'yes') {
					Ext.getCmp(idGrid).store.remove(linhaSelecionada);
					if (linhaSelecionada.phantom == false) {
						linhaSelecionada.markDirty();
						Ext.getCmp(idGrid).store.save();
					}
				}
			}
		})
	} else {
		Ext.Msg.show({
			title : 'Alerta',
			msg : 'Selecione o item que deseja excluir!',
			buttons : Ext.MessageBox.OK,
			animEl : idGrid,
			icon : Ext.MessageBox.ERROR
		});
	}
}

function getFieldsStoreReq() {
	var rFields = [ {
		name : 'id',
		type : 'int',
		defaultValue : 0
	}, {
		name : 'qtdEme',
		type : 'float'
	}, {
		name : 'obsEme',
		type : 'string'
	}, {
		name : 'codUsu',
		mapping : 'usuSol.codUsu'
	}, {
		name : 'produto',
		convert : function(v, rec) {
			return rec.produto;
		}
	}, {
		name : 'usuSol',
		convert : function(v, rec) {
			return rec.usuSol
		}
	}, {
		name : 'codPro',
		mapping : 'produto.id.codPro',
		allowBlank : false
	}, {
		name : 'produtoId',
		mapping : 'produto.id.codPro'
	}, {
		name : 'produto.desPro',
		mapping : 'produto.desPro'
	}, {
		name : 'supervisor',
		convert : function(v, rec) {
			return rec.supervisor;
		}
	}, {
		name : 'centro',
		convert : function(v, rec) {
			return rec.centro
		}
	}, {
		name : 'cliente',
		convert : function(v, rec) {
			return rec.cliente
		}
	}, 'motReq', 'conCli', 'datReq', 'cmpReq', 'centro.id', 'centro.nomCcu',
			'qtdApr', 'sitEme', 'desSit', 'idSit', {
				name : 'uniMed',
				mapping : 'produto.uniMed'
			}, 'preUni', 'preTot', 'setor', 'desSit', 'qtdAprMesAnt',
			'aprovada', 'processada' ];

	return rFields;
}

function getReaderStoreReq() {
	var rFields = getFieldsStoreReq();

	var rJsonReader = new Ext.data.JsonReader({
		idProperty : 'id',
		root : 'map.items',
		totalProperty : 'map.totalCount',
		successProperty : 'map.success',
		messageProperty : 'map.msg',
		fields : rFields
	});
	return rJsonReader;
}

function MudaCorReq(record, index, nomeCampo) {
	var change = record.data.idSit;
	if (change == 1)
		return 'corDigitado';
	else if (change == 2)
		return 'corAprovado';
	else if (change == 3)
		return 'corCompras';
	else if (change == 4)
		return 'corAtendido';
	else if (change == 5)
		return 'corAtendido';
	else if (change == 9)
		return 'corCancelado';
}
