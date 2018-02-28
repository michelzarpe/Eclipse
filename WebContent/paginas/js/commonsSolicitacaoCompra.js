function criaLeitorSolCompra() {
	leitorX = new Ext.data.XmlReader( {
		record : 'solicitacaoCompra',
		id : 'solicitacaoCompra.id',
		totalProperty : 'totalCount'
	}, [ {
		name : 'solicitacaoCompra.id',
		mapping : 'id'
	} ]);
	return leitorX;
}

function criaStoreJSONSolCompra() {
	var rProxy = new Ext.data.HttpProxy( {
		api : {
			create : 'solicitacaoCompra!gravar.action',
			read : 'solicitacaoCompra!listAll.action',
			update : 'solicitacaoCompra!gravar.action',
			destroy : 'solicitacaoCompra!excluiItem.action'
		}
	});
	Ext.Ajax.timeout = 3000000;

	/**
	 * Este trecho é para resolver o bug que tem na versão 3.1.1 onde não
	 * funciona o pruneModifiedRecords do Store e sempre fica sujeira depois que
	 * se remove um item.
	 */
	Ext.override(Ext.data.Store, {
		removeAll : function() {
			this.data.each( function(rec) {
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

	var rWriter = new Ext.data.JsonWriter( {
		encode : true,
		writeAllFields : true
	});

	var rJsonReader = getReaderStoreReq();

	Ext.data.DataProxy.addListener('write', function(proxy, action, result,
			res, rs) {
		Ext.MessageBox.alert("Sucesso", res.message);
	});
	var rJsonStore = new Ext.data.Store( {
		proxy : rProxy,
		storeId : 'sStore',
		autoLoad : false,
		remoteSort : true,
		autoSave : false,
		writer : rWriter,
		reader : rJsonReader,
		pruneModifiedRecords : true,
		reader : rJsonReader,
		listeners : {
			exception : function(proxy, type, action, o, response, args) {
				if (type === "remote") {
					Ext.MessageBox.alert("Erro", response.message
							+ " - Informe a administração do sistema.");
				} else {
					Ext.MessageBox.alert("Alerta",
							"Não foi possível carregar os dados da resposta.");
				}
			}
		}
	});
	rJsonStore.setDefaultSort('produto.desPro', 'ASC');

	return rJsonStore;
}

function getReaderStoreSolCom() {
	var rFields = getFieldsStoreSolCom();
	var rJsonReader = new Ext.data.JsonReader( {
		idProperty : 'id',
		root : 'map.items',
		totalProperty : 'map.totalCount',
		successProperty : 'map.success',
		messageProperty : 'map.msg',
		fields : rFields
	});
	return rJsonReader;
}

function getFieldsStoreSolCom() {
	var rFields = [ {
		name : 'solicitacaoCompra.id',
		type : 'int',
		defaultValue : 0,
		mapping : 'id'
	}, {
		name : 'solicitacaoCompra.cplPro',
		type : 'string',
		mapping : 'cplPro'
	}, {
		name : 'solicitacaoCompra.datSol',
		mapping : 'datSol'
	}, {
		name : 'cotacao',
		convert : function(v, rec) {
			return rec.cotacao;
		}
	}, {
		name : 'solicitacaoCompra.usuSol.codUsu',
		mapping : 'usuSol.codUsu'
	},{
		name : 'solicitacaoCompra.usuSol.centro.nomCcu',
		mapping : 'usuSol.centro.nomCcu'
	} ];
	return rFields;
}