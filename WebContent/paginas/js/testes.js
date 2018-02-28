function criaTabDadosCad() {
	var campos = [ 'id', 'nomCid', 'estCid' ];
	var storeCidadeNas = criaStoreReader('cidade', 'cidade!listAllXML.action',campos, 'id', 'storeCidade');
	var cidadeTpl = new Ext.XTemplate('<tpl for="."><div class="search-item"><h3><span>{nomCid}, {estCid}</span></h3></div></tpl>');
	campos = [ 'id', 'name' ];
	var storeEstCiv = criaStoreReader('item', 'estadoCivil!listAllXML.action',campos, 'id', 'storeEstCiv');
	var storeGraIns = criaStoreReader('grauInstrucao','grauInstrucao!listAllXML.action', campos, 'id', 'storeGraIns');
	var storeTipSan = criaStoreReader('item','tipoSanguineo!listAllXML.action', campos, 'id', 'storeTipSan');
	var tabDadosCad = {
		title : 'Dados pessoais',
		labelWidth : 100,
		defaults : {
			xtype : 'textfield'
		},
		items : [ {
			fieldLabel : 'Situa��o',
			name : 'sitAdm',
			id : 'fSitAdm',
			xtype : 'hidden'
		}, {
			fieldLabel : 'Id',
			name : 'colaborador.id',
			width : 50,
			xtype : 'hidden',
			id : 'fIdCol'
		}, {
			fieldLabel : 'Nome',
			name : 'colaborador.nomFun',
			width : 350,
			allowBlank : false,
			id : 'fNomFun'
		}, {
			layout : 'column',
			items : [ {
				columnWidth : .33,
				layout : 'form',
				items : [ new Ext.form.DateField( {
					fieldLabel : 'Data de nascimento',
					name : 'colaborador.datNas',
					width : 100,
					allowBlank : false
				}) ]
			}, {
				columnWidth : .33,
				layout : 'form',
				items : [ {
					fieldLabel : 'Filiação - Mãe',
					name : 'colaborador.nomMae',
					width : 350,
					allowBlank : false,
					id : 'fNomMae'
				} ]
			}, {
				columnWidth : .33,
				layout : 'form',
				items : [ {
					fieldLabel : 'Filiação - Pai',
					name : 'colaborador.nomPai',
					width : 350,
					allowBlank : false,
					id : 'fNomPai'
				} ]
			} ]
		}, {
			xtype : 'combo',
			store : storeCidadeNas,
			id : 'fCidNas',
			hiddenName : 'colaborador.cciNas.id',
			fieldLabel : 'Cidade nascimento',
			displayField : 'nomCid',
			valueField : 'id',
			typeAhead : true,
			mode : 'remote',
			itemSelector : 'div.search-item',
			emptyText : 'Busca automática a partir de 4 caracteres...',
			loadingText : 'Procurando...',
			pageSize : 10,
			width : 350,
			tpl : cidadeTpl,
			triggerClass : 'x-form-search-trigger',
			triggerAction : 'all',
			hideTrigger : true,
			minChars : 3,
			queryParam : 'cidade.nomCid',
			allowBlank : false,
			forceSelection : true
		}, {
			xtype : 'radio',
			fieldLabel : 'Sexo',
			boxLabel : 'Feminino',
			name : 'colaborador.sexo',
			inputValue : 'F',
			allowBlank : false
		}, {
			xtype : 'radio',
			boxLabel : 'Masculino',
			labelSeparator : '',
			name : 'colaborador.sexo',
			inputValue : 'M',
			allowBlank : false
		}, {
			xtype : 'combo',
			fieldLabel : 'Estado civil',
			hiddenName : 'colaborador.estCiv',
			store : storeEstCiv,
			valueField : 'id',
			displayField : 'name',
			typeAhead : true,
			mode : 'remote',
			triggerAction : 'all',
			emptyText : 'Selecione um estado civil...',
			selectOnFocus : true,
			width : 350,
			id : 'fEstCiv',
			allowBlank : false,
			forceSelection : true
		}, {
			fieldLabel : 'Nome do pai',
			name : 'colaborador.nomPai',
			width : 350,
			allowBlank : false,
			id : 'fNomPai'
		}, {
			fieldLabel : 'N de dependentes até 14 anos',
			name : 'colaborador.numDep',
			width : 25
		}, {
			xtype : 'combo',
			fieldLabel : 'Grau de instrução',
			hiddenName : 'colaborador.grauInstrucao.id',
			store : storeGraIns,
			valueField : 'id',
			displayField : 'name',
			typeAhead : true,
			mode : 'remote',
			triggerAction : 'all',
			emptyText : 'Selecione um grau de instrução...',
			selectOnFocus : true,
			width : 350,
			id : 'fGraIns',
			allowBlank : false,
			forceSelection : true
		}, {
			xtype : 'combo',
			fieldLabel : 'Tipo Sanguíneo',
			hiddenName : 'colaborador.tipSan',
			store : storeTipSan,
			valueField : 'id',
			displayField : 'name',
			typeAhead : true,
			mode : 'remote',
			triggerAction : 'all',
			emptyText : 'Selecione um tipo sanguíneo...',
			selectOnFocus : true,
			width : 350,
			id : 'fTipSan',
			allowBlank : false,
			forceSelection : true
		} ]
	}
	return tabDadosCad;
}