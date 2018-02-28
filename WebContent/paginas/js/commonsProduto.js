function criaStoreMat(urlBusca, tipoMaterial) {
	var campos = [ {
		name : 'produto.id.codPro',
		mapping : 'id > codPro'
	}, {
		name : 'produtoId',
		mapping : 'id > codPro'
	}, {
		name : 'produto.id.empresa.id',
		mapping : 'id > empresa > id'
	}, {
		name : 'produto.desPro',
		mapping : 'desPro'
	}, {
		name : 'desPro',
		mapping : 'desPro'
	}, {
		name : 'produto.cplPro',
		mapping : 'cplPro'
	}, 'cplPro', 'sitPro', 'uniMed', 'tipPro', 'preUni' ];

	var leitorMat = new Ext.data.XmlReader( {
		record : 'produto',
		id : 'produtoId',
		totalProperty : 'totalCount'
	}, campos);

	var storeMat = new Ext.data.Store( {
		remoteSort : false,
		id : 'storeMat',
		totalProperty : 'totalCount',
		autoLoad : false,
		proxy : new Ext.data.HttpProxy( {
			url : urlBusca,
			method : 'POST'
		}),
		reader : leitorMat
	});
	storeMat.setDefaultSort('produto.desPro', 'ASC');
	
	if ((tipoMaterial == "ME") || (tipoMaterial == "MI")) {
		storeMat.setBaseParam('produto.empresa.id', 3);
	}
	return storeMat;
}