/** Gera pdf com ficha admissional, abrindo na tela * */
function imprimeFicha() {
	var sitAdm = "";
	var idCol = 0;
	if (Ext.getCmp('fSitAdm')) {
		sitAdm = Ext.getCmp('fSitAdm').getValue();
		idCol = Ext.getCmp('fIdCol').getValue();
	} else {
		sitAdm = Ext.getCmp('sGridCol').getSelectionModel().getSelected().get('colaborador.afastamento.id');
		idCol = Ext.getCmp('sGridCol').getSelectionModel().getSelected().get('colaborador.id');
	}
	
	if (sitAdm != "") {
		if (sitAdm != "I") {
			var myMask = new Ext.LoadMask(Ext.getBody(), {msg : "Por favor, aguarde. Gerando ficha ..."});
			myMask.show();
			if (idCol > 0) {
				window.location = "ficha!imprimirAdmissao.action?id=" + idCol;
				myMask.hide();
			} else {
				myMask.hide();
				Ext.Msg.show( {
					title : 'Erro',
					msg : 'Selecione um colaborador!',
					buttons : Ext.MessageBox.OK,
					animEl : 'sGridCol',
					icon : Ext.MessageBox.ERROR
				});
			}
		} else {
			Ext.Msg.show( {
				title : 'Aviso',
				msg : 'Você não pode imprimir ficha de admissão imcompleta',
				buttons : Ext.MessageBox.OK,
				animEl : 'sGridCol',
				icon : Ext.MessageBox.WARNING
			});
		}
	} else {
		Ext.Msg.show( {
			title : 'Aviso',
			msg : 'Selecione um colaborador',
			buttons : Ext.MessageBox.OK,
			animEl : 'sGridCol',
			icon : Ext.MessageBox.ERROR
		});
	}
}
function criaLeitorCol() {
	leitorCol = new Ext.data.XmlReader( {
		record : 'colaborador',
		id : 'colaborador.id',
		totalProperty : 'totalCount'
	}, [ {name : 'colaborador.id',	mapping : 'colaboradorId'}, 
		 {name : 'colaborador.numCad', mapping : 'numCad'},
		 {name : 'colaborador.nomFun', mapping : '@nomFun'}, 
		 {name : 'colaborador.empresa.id', mapping : 'empresa > id'}, 
		 {name : 'colaborador.banco.id', mapping : 'banco > id'},
		 {name : 'colaborador.empresa.nomEmp', mapping : 'empresa > name'}, 
		 {name : 'colaborador.afastamento.id', mapping : 'afastamento > id'},
		 {name : 'colaborador.afastamento.desSit', mapping : 'afastamento > desSit'}, 
		 {name : 'colaborador.centro.id', mapping : 'centro > id'},
		 {name : 'colaborador.centro.nomCcu', mapping : 'centro > nomCcu'}, 
		 {name : 'colaborador.datNas', mapping : 'datNas'}, 
		 {name : 'colaborador.cciNas.id', mapping : 'cciNas > id'},
		 {name : 'colaborador.cciNas.nomCid', mapping : 'cciNas > nomCid'},
		 {name : 'colaborador.numCpf', mapping : 'numCpf'}, 
		 {name : 'colaborador.numCid', mapping : 'numCid'}, 
		 {name : 'colaborador.emiCid', mapping : 'emiCid'}, 
		 {name : 'colaborador.numCtp', mapping : 'numCtp'}, 
		 {name : 'colaborador.serCtp', mapping : 'serCtp'}, 
		 {name : 'colaborador.numPis', mapping : 'numPis'},
		 {name : 'colaborador.numEle', mapping : 'numEle'},
		 {name : 'colaborador.numCnh', mapping : 'numCnh'},
		 {name : 'colaborador.catCnh', mapping : 'catCnh'},
		 {name : 'colaborador.endRua', mapping : 'endRua'},
		 {name : 'colaborador.cep',    mapping : 'cep'   },
		 {name : 'colaborador.nomCon', mapping : 'nomCon'}, 
		 {name : 'colaborador.numTel', mapping : 'numTel'}, 
		 {name : 'colaborador.numCel', mapping : 'numCel'}, 
		 {name : 'colaborador.fonCon', mapping : 'fonCon'},
		 {name : 'colaborador.sexo',   mapping : '@sexo' },
		 {name : 'colaborador.numDep', mapping : 'numDep'},
		 {name : 'colaborador.datAdm', mapping : 'datAdm'},
		 {name : 'colaborador.valSal', mapping : 'valSal'},
		 {name : 'colaborador.numDRT', mapping : 'numDRT'},
		 {name : 'colaborador.numDip', mapping : 'numDip'},
		 {name : 'colaborador.orgDip', mapping : 'orgDip'},
		 {name : 'colaborador.datFor', mapping : 'datFor'},
		 {name : 'colaborador.datRec', mapping : 'datRec'},
		 {name : 'colaborador.conBan', mapping : 'conBan'},
		 {name : 'colaborador.digCon', mapping : 'digCon'},
		 {name : 'colaborador.codAge', mapping : 'codAge'},
		 {name : 'colaborador.digAge', mapping : 'digAge'},
		 {name : 'colaborador.pgtChq', mapping : '@pgtChq'},
		 {name : 'colaborador.conPou', mapping : '@conPou'},
		 {name : 'colaborador.horIni', mapping : 'horIni'},
		 {name : 'colaborador.horFin', mapping : 'horFin'}, 
		 {name : 'colaborador.horIni2',mapping : 'horIni2'},
		 {name : 'colaborador.horFin2',mapping : 'horFin2'},
		 {name : 'colaborador.horIniSab', mapping : 'horIniSab'},
		 {name : 'colaborador.horFinSab', mapping : 'horFinSab'},
		 {name : 'colaborador.horIniSab2',mapping : 'horIniSab2'},
		 {name : 'colaborador.horFinSab2',mapping : 'horFinSab2'},
		 {name : 'colaborador.obs',	mapping : 'obs'},
		 {name : 'colaborador.mesmoLocal', mapping : '@mesmoLocal'},
		 {name : 'colaborador.supervisor', mapping : '@supervisor'},
		 {name : 'colaborador.cargo.id.codCar', mapping : 'cargo > id'}, 
		 {name : 'colaborador.cargo.id.name', mapping : 'cargo > name'},
		 {name : 'colaborador.cidEnd.id',mapping : 'cidEnd > id'},
		 {name : 'colaborador.colabSubs.id', mapping : 'colabSubs > colaboradorId'},
		 {name : 'colaborador.motivo.id',	mapping : 'motivo > id'},
		 {name : 'colaborador.tipSan', mapping : 'tipSan'},
		 {name : 'colaborador.grauInstrucao.id', mapping : 'grauInstrucao > id'},
		 {name : 'colaborador.estCiv', mapping : 'estCiv'},
		 {name : 'colaborador.local.id.numLoc', mapping : 'local > id > numLoc'},
		 {name : 'empresaId', mapping : 'empresa > id'},
		 {name : 'empresaLocId', mapping : 'local > hierarquias > items> empresa'},
		 {name : 'clienteLocId', mapping : 'local > hierarquias > items > cliente > codLoc'},
		 {name : 'unidadeLocId', mapping : 'local > hierarquias > items > unidade > codLoc'},
		 {name : 'cidadeLocId', mapping : 'local > hierarquias > items > cidade > codLoc'},
		 {name : 'sitAdm', mapping : 'sitAdm'},
		 {name : 'colaborador.temCNV', mapping : '@temCNV'},
		 {name : 'colaborador.extSegPes', mapping : '@extSegPes'},
		 {name : 'colaborador.extTnsVal', mapping : '@extTnsVal'},
		 {name : 'colaborador.vctCNV', mapping : 'vctCNV'},
		 {name : 'colaborador.numCnv', mapping : 'numCnv'},
		 {name : 'colaborador.endCpl', mapping : 'endCpl'},
		 {name : 'colaborador.endNum', mapping : 'endNum'}, 
		 {name : 'colaborador.dexCid', mapping : 'dexCid'},
		 {name : 'colaborador.nomMae', mapping : 'nomMae'},
		 {name : 'colaborador.nomPai', mapping : 'nomPai'}, 
		 {name : 'colaborador.nomCur', mapping : 'nomCur'}, 
		 {name : 'colaborador.nomIns', mapping : 'nomIns'}, 
		 {name : 'colaborador.datCon', mapping : 'datCon'}, 
		 {name : 'colaborador.qvtMes', mapping : 'qvtMes'},
		 {name : 'colaborador.vvtMes', mapping : 'vvtMes'}, 
		 {name : 'colaborador.vlrAdi', mapping : 'vlrAdi'},
		 {name : 'colaborador.meiTrans', mapping : 'meiTrans'},
		 {name : 'colaborador.racCor', mapping : 'racCor'}, 
		 {name : 'colaborador.pagSin', mapping : 'pagSin'},
		 {name : 'colaborador.zonEle', mapping : 'zonEle'},
		 {name : 'colaborador.secEle', mapping : 'secEle'},
		 {name : 'colaborador.numRes', mapping : 'numRes'},
		 {name : 'colaborador.nacionalidade.id', mapping : 'nacionalidade > id'},
		 {name : 'colaborador.nacionalidade.desNac', mapping : 'nacionalidade > name'},
		 {name : 'colaborador.escalaHorarioRondaMZ.id', mapping : 'escalaHorarioRondaMZ > id'},
		 {name : 'classeEscalaRonda.id', mapping : 'escalaHorarioRondaMZ > classeEscalaRonda > id'},
		 {name : 'horSemRon', mapping : 'escalaHorarioRondaMZ > horSem'},
		 {name : 'horMesRon', mapping : 'escalaHorarioRondaMZ > horMes'},
		 {name : 'colaborador.digCar', mapping : 'digCar'}, 
		 {name : 'colaborador.dddTel', mapping : 'dddTel'},
		 {name : 'colaborador.numDdd2', mapping : 'numDdd2'},
		 {name : 'colaborador.emaPar', mapping : 'emaPar'},
		 {name : 'colaborador.emaCom', mapping : 'emaCom'},
		 {name : 'colaborador.defFis', mapping : 'defFis'}, 
		 {name : 'colaborador.deficiencia.codDef', mapping : 'deficiencia > codDef'},
		 {name : 'colaborador.estNas', mapping : 'estNas'}, 
		 {name : 'colaborador.paiNas', mapping : 'paiNas'},
		 {name : 'colaborador.codEst', mapping : 'codEst'},
		 {name : 'colaborador.dexCtp', mapping : 'dexCtp'},
		 {name : 'colaborador.estCtp', mapping : 'estCtp'},
		 {name : 'colaborador.sindicato.codSin', mapping : 'sindicato > codSin'},
		 {name : 'colaborador.sindicato.nomSin', mapping : 'sindicato > nomSin'},
		 {name : 'colaborador.postoTrabalho.idPosTrab', mapping : 'postoTrabalho > idPosTrab'},
		 {name : 'colaborador.postoTrabalho.posTra', mapping : 'postoTrabalho > posTra'},
		 {name : 'colaborador.postoTrabalho.desPos', mapping : 'postoTrabalho > desPos'},
		 {name : 'colaborador.postoTrabalho.estPos', mapping : 'postoTrabalho > estPos'},
		 {name : 'colaborador.vinculo.codVin', mapping : 'vinculo > codVin'},
		 {name : 'colaborador.vinculo.desVin', mapping : 'vinculo > desVin'},
		 {name : 'colaborador.escalaVT.escVtr',mapping : 'escalaVT > escVtr'},
		 {name : 'colaborador.escalaVT.nomEvt',mapping : 'escalaVT > nomEvt'},
		 {name : 'colaborador.naturezaDespesa.natDes', mapping : 'naturezaDespesa > natDes'},
		 {name : 'colaborador.naturezaDespesa.nomNat', mapping : 'naturezaDespesa > nomNat'},
		 {name : 'colaborador.perJur', mapping : 'perJur'}, 
		 {name : 'colaborador.locTraHlo', mapping : 'locTraHlo'},
		 {name : 'colaborador.iniEvt', mapping : 'iniEvt'},
		 {name : 'colaborador.fimEvt', mapping : 'fimEvt'},
		 {name : 'colaborador.admeSo', mapping : 'admeSo'},
		 {name : 'colaborador.admAnt', mapping : 'admAnt'},
		 {name : 'colaborador.cnpjAn', mapping : 'cnpjAn'},
		 {name : 'colaborador.cateSo', mapping : 'cateSo'},
		 {name : 'colaborador.catAnt', mapping : 'catAnt'},
		 {name : 'colaborador.matAnt', mapping : 'matAnt'},
		 {name : 'colaborador.conFgt', mapping : 'conFgt'},
		 {name : 'colaborador.datOpc', mapping : 'datOpc'},
		 {name : 'colaborador.depIrf', mapping : 'depIrf'},
		 {name : 'colaborador.catSef', mapping : 'catSef'},
		 {name : 'colaborador.codEtb', mapping : 'codEtb'},
		 {name : 'colaborador.iniEtbHeb', mapping : 'iniEtbHeb'},
		 {name : 'colaborador.fimEtbHeb', mapping : 'fimEtbHeb'},
		 {name : 'colaborador.tipAdmHfi', mapping : 'tipAdmHfi'},
		 {name : 'colaborador.indAdm', mapping : 'indAdm'},
		 {name : 'colaborador.tipApo', mapping : 'tipApo'},
		 {name : 'colaborador.tipCon', mapping : 'tipCon'},
		 {name : 'colaborador.onuSce', mapping : 'onuSce'},
		 {name : 'colaborador.lisRai', mapping : 'lisRai'},
		 {name : 'colaborador.ratEve', mapping : 'ratEve'},
		 {name : 'colaborador.rec13S', mapping : 'rec13S'},
		 {name : 'colaborador.recAdi', mapping : 'recAdi'},
		 {name : 'colaborador.resOnu', mapping : 'resOnu'},
		 {name : 'colaborador.socSinHsi', mapping : 'socSinHsi'},
		 {name : 'colaborador.emiCar', mapping : 'emiCar'},
		 {name : 'colaborador.tipSal', mapping : 'tipSal'},
	     {name : 'colaborador.bairro.id.codBai', mapping : 'bairro > id > codBai'},
	     {name : 'colaborador.vlrCbt', mapping : 'vlrCbt'},
	     {name : 'colaborador.qvtKm', mapping : 'qvtKm'}	
	 ]);
	return leitorCol;
}
function criaGridCol(sm, pb, st) {
	var tb = new Ext.Toolbar( {
		id : 'tBarGridCol'
	});
	grid = new Ext.grid.GridPanel( {
		store : st,
		loadMask : true,
		cm : new Ext.grid.ColumnModel( [ new Ext.grid.RowNumberer(), sm, {
			header : 'Cod.',
			dataIndex : 'colaborador.id',
			sortable : true,
			width : 20
		}, {
			header : "Cadastro",
			width : 100,
			dataIndex : 'colaborador.numCad',
			sortable : true
		},{
			header : "Colaborador",
			width : 100,
			dataIndex : 'colaborador.nomFun',
			sortable : true
		}, {
			header : "Data de admimissão",
			width : 50,
			dataIndex : 'colaborador.datAdm',
			sortable : true
		}, {
			header : 'Empresa',
			dataIndex : 'colaborador.empresa.nomEmp',
			sortable : true
		}, {
			header : 'Centro',
			width : 50,
			dataIndex : 'colaborador.centro.nomCcu',
			sortable : true
		}, {
			header : 'Situação',
			width : 50,
			dataIndex : 'colaborador.afastamento.desSit',
			sortable : true
		} ]),
		sm : sm,
		tbar : tb,
		bbar : pb,
		height : 350,
		id : 'sGridCol',
		viewConfig : {
			forceFit : true,
			getRowClass : MudaCorCol
		},
		frame : true,
		iconCls : 'icon-grid'
	});
	return grid;
}
function MudaCorAdm(record, index) {
	var change = record.get('sitAdm');
	if (change == 'I') //vermelho
		return 'corBloqueado';
	else if (change == 'C') //roxo
		return 'corLiberado';
	else if (change == 'T') //preto
		return 'corIntegrado';
	else if (change == 'H')
		return 'corHomologado'; //verde
}
function MudaCorCol(record, index, nomeCampo) {
	var change = record.get(record.node.localName + ".afastamento.id");
	if (change == 1)
		return 'corLiberado';
	else if (change == 7)
		return 'corBloqueado';
	else
		return 'corNaoAtendido';
}


function criaLeitorDependente() {
	var leitorDependente = new Ext.data.XmlReader({record : 'dependente',totalProperty : 'totalCount'}, 
			        [{  name : 'codDep', mapping : 'codDep'},
			         {  name : 'nomDep', mapping : 'nomDep'},
			         {  name : 'grauParantesco', mapping : 'grauParantesco'}, 
			         {  name : 'tipoDependenteESocial', mapping : 'tipoDependenteESocial'}, 
			         {	name : 'sexo', mapping : 'sexo'}, 
			         {	name : 'limIrf', mapping : 'limIrf'}, 
			         {	name : 'limSaf', mapping : 'limSaf'}, 
			         {	name : 'datNas', mapping : 'datNas', type : 'date', dateFormat : 'd/m/Y'}, 
			         {  name : 'numCpf', mapping : 'numCpf'}, 
			         {  name : 'nomMae', mapping : 'nomMae'}, 
			         {	name : 'penJud', mapping : 'penJud'}, 
			         {  name : 'estCiv', mapping : 'estCiv'}, 
			         {  name : 'grauInstrucao',	mapping : 'grauInstrucao > id'}, 
			         {	name : 'datInv', mapping : 'datInv', type : 'date', dateFormat : 'd/m/Y'}, 
			         {	name : 'iniTut', mapping : 'iniTut', type : 'date', dateFormat : 'd/m/Y'}, 
			         {	name : 'datTut', mapping : 'datTut', type : 'date', dateFormat : 'd/m/Y'}, 
			         {  name : 'aviImp', mapping : 'aviImp'}, 
			         {  name : 'auxCre', mapping : 'auxCre'}, 
			         {	name : 'nomCre', mapping : 'nomCre'}]);
	return leitorDependente;
}
