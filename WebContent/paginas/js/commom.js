var usuarioId;
var tipoUsuario;
var nomeUsuario;
var not = {
	QT : 'Get QuickTime: <a href="http://www.apple.com/quicktime/download" target="_fdownload">Here</a>',
	FLASH : {
		tag : 'span',
		cls : 'noJoy',
		html : '<p>Note: The Ext.MediaPanel Demo requires Flash Player 8.0 or higher. The latest version of Flash Player is available at the <a href="http://www.adobe.com/go/getflashplayer" target="_fdownload">Adobe Flash Player Download Center</a>.</p>'
	},
	FLASHV : {
		tag : 'span',
		cls : 'noJoy',
		html : '<p>Note: The Ext.MediaPanel Demo requires Flash Player {0} or higher. The latest version of Flash Player is available at the <a href="http://www.adobe.com/go/getflashplayer" target="_fdownload">Adobe Flash Player Download Center</a>.</p>'
	},
	PDF : 'Get Acrobat Reader: <a href="http://www.adobe.com/products/acrobat/readstep2.html" target="_fdownload">Here</a>',
	REAL : 'Get RealPlayer Plugin: <a href="http://www.realplayer.com/" target="_fdownload">Here</a>',
	OFFICE : 'MSOffice Is not installed',
	JWP : '<p>FLV Player can handle (FLV, but also MP3, H264, SWF, JPG, PNG and GIF). It also supports RTMP and HTTP (Lighttpd) streaming, RSS, XSPF and ASX playlists, a wide range of flashvars (variables), an extensive  javascript API and accessibility features.</p>'
			+ 'Get FLV Player <a href="http://www.jeroenwijering.com/?item=JW_FLV_Player" target="_fdownload">Here</a>.',
	JWROT : 'The JW Image Rotator (built with Adobe\'s Flash) enables you to show photos in sequence, with fluid transitions between them. It supports rotation of an RSS, XSPF or ASX playlists with JPG, GIF and PNG images.'
			+ '<p>Get JW ImageRotator <a href="http://www.jeroenwijering.com/?item=JW_Image_Rotator" target="_fdownload">Here</a>.'
};

var myMask = new Ext.LoadMask(Ext.getBody(), {
	msg : "Aguarde..."
});

Ext.onReady(function() {
			Ext.QuickTips.init();

			/**
			 * sobrescrever o método beforeBlur para que não mude o valor do
			 * campo ao sair dele, quando tiver nomes(displayValue) iguais *
			 */
			Ext.override(Ext.form.ComboBox, {
				beforeBlur : function() {

				}
			});

			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

			Ext.data.DataProxy.addListener('write', function(proxy, action,
					result, res, rs) {
				Ext.MessageBox.alert("Sucesso", res.message);
			});

			Ext.data.DataProxy.addListener('exception', function(proxy, type,
					action, options, res) {
				if (type === 'remote') {
					Ext.Msg.show({
						title : 'REMOTE EXCEPTION',
						msg : res.message,
						icon : Ext.MessageBox.ERROR,
						buttons : Ext.Msg.OK
					});
				}
			});

			var viewport = new Ext.Viewport(
					{
						layout : 'border',
						items : [
								new Ext.Panel(
										{
											region : 'north',
											id : 'pnTop',
											height : 60,
											forceLayout : true,
											layout : 'fit',
											bbar : new Ext.Toolbar({
												items : []
											}),
											html : '<p><font face="Verdana" size="4"><b>webOper 2.0 - Grupo Zanardo</b></font></p>'
										}),
								{
									region : 'south',
									contentEl : 'south',
									split : true,
									height : 50,
									minSize : 100,
									maxSize : 200,
									collapsible : true,
									title : 'Informações da sua sessão',
									margins : '0 0 0 0'
								},
								new Ext.Panel(
										{
											region : 'center',
											id : 'pnCenter',
											html : '<br/><br/><br/><br/><CENTER><img style="margin-top:6px" border="0" src="paginas/js/logo.jpg" width="400" /></CENTER>',
											tbar : new Ext.Toolbar({
												items : []
											})
										}) ]
					});
			usuarioId = document.getElementById('usuario.id').value;
			tipoUsuario = document.getElementById('usuario.tipUsu').value;
			nomeUsuario = document.getElementById('usuario.nomFun').value;
			Ext.form.Field.prototype.msgTarget = 'side';
			criaMenuGeral();
			/**
			 * Renderização personalizada para combos, para que exibam o nome do
			 * item*
			 */
			// se o store do combo não estiver carregado, pega do store que
			// carregou os
			// dados no form
			Ext.util.Format.comboRenderer = function(combo, st) {
				return function(value) {
					var record = combo.findRecord(combo.valueField, value);
					return record ? record.get(combo.displayField) : st
							.getById(value) ? st.getById(value).get(
							combo.displayField) : st.getAt(st.find(
							combo.valueField, value)) ? st.getAt(
							st.find(combo.valueField, value)).get(
							combo.displayField) : combo.valueNotFoundText;
				}
			}

		});
function remover(descricao) {
	var txt = 'Tem certeza de que deseja excluir o registro de ' + descricao
			+ '?';
	if (confirm(txt)) {
		return true;
	} else {
		return false;
	}
}

function criaPaginador(st) {
	pagingBar = new Ext.PagingToolbar({
		pageSize : 20,
		store : st,
		displayInfo : true,
		displayMsg : 'Exibindo itens de {0} até {1} de {2}',
		emptyMsg : "Nenhum item encontrado",
		id : 'sPag'
	});
	return pagingBar;
}

function criaLeitorUni() {
	leitorUni = new Ext.data.XmlReader({
		record : 'uniforme',
		id : 'uniformeId',
		totalProperty : 'totalCount'
	}, [ {
		name : 'desEpi',
		mapping : 'desEpi'
	}, 'uniformeId', 'cplDes', 'diaVal', 'qtdMax', {
		name : 'uniforme.usoUniforme',
		mapping : 'usoUniforme'
	} ]);
	return leitorUni;
}
function criaGridUni(sm, pb, st, tb) {
	grid = new Ext.grid.GridPanel({
		store : st,
		loadMask : true,
		cm : new Ext.grid.ColumnModel([ sm, {
			header : 'Cod.',
			dataIndex : 'uniformeId',
			sortable : true,
			width : 20
		}, {
			header : "Descrição",
			width : 150,
			dataIndex : 'desEpi',
			sortable : true
		}, {
			header : "Complemento",
			width : 150,
			dataIndex : 'cplDes',
			sortable : true
		}, {
			header : 'Validade (dias)',
			width : 50,
			dataIndex : 'diaVal'
		}, {
			header : 'Quantidade máxima por colaborador',
			width : 50,
			dataIndex : 'qtdMax'
		} ]),
		sm : sm,
		tbar : [ new Ext.Toolbar({
			id : 'tBar'
		}) ],
		bbar : pb,
		renderTo : document.body,
		height : 350,
		width : 'auto',
		id : 'sGridUni',
		viewConfig : {
			autoFill : true
		}
	});
	return grid;
}

function sincroniza(textoMascara, urlSinc) {
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : textoMascara
	});
	myMask.show();
	Ext.Ajax.request({
		url : urlSinc,
		timeout : 10000000,
		success : function(result, request) {
			myMask.hide();
			Ext.Msg.show({
				title : 'Sucesso',
				msg : result.responseText,
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		},
		failure : function(result, request) {
			myMask.hide();
			Ext.Msg.show({
				title : 'Falha',
				msg : result.responseText,
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			recarrega = false;
		}
	});
}
function criaStoreReader(record, url, campos, id, nomeStore) {
	var leitorX = new Ext.data.XmlReader({
		record : record,
		id : id,
		totalProperty : 'totalCount'
	}, campos);
	var stX = new Ext.data.Store({
		remoteSort : true,
		id : nomeStore,
		proxy : new Ext.data.HttpProxy({
			url : url,
			method : 'POST'
		}),
		reader : leitorX
	});
	return stX;
}
function MudaCorViagem(record, index) {
	var change = record.get('datAce');
	if (change == '')
		return 'corBloqueado';
	else
		return 'corLiberado';
}

function geraMediaPdf(url) {
	var p = new Ext.ux.MediaWindow({
		id : 'PDFViewerWin',
		renderTo : document.body,
		height : 400,
		width : 600,
		maximizable : true,
		title : 'ArquivoPdf',
		mediaMask : {
			msg : 'Aguarde, carregando pdf...',
			autoHide : 3000
		},
		autoMask : true,
		mediaCfg : {
			mediaType : 'PDF',
			url : url,
			autoSize : true,
			unsupportedText : not['PDF'],
			params : {
				page : 1
			}
		}
	});
	p.show();
	p.center();
}

function maskDate(obj) {
	var data = obj.getValue();
	if (data.length == 2) {
		data = data + '/';
		obj.setValue(data);
		return true;
	}
}

// ******** TEXTFIELD COM FORMATAÇÃO DE MOEDA *****************/
Ext.ux.MoneyField = function(config) {
	var defConfig = {
		autocomplete : 'off',
		allowNegative : true,
		format : 'BRL',
		currency : 'R$',
		showCurrency : false
	};
	Ext.applyIf(config, defConfig);
	Ext.ux.MoneyField.superclass.constructor.call(this, config);
};

Ext.extend(
				Ext.ux.MoneyField,
				Ext.form.TextField,
				{

					/*
					 * initComponent:function() { },
					 */

					initEvents : function() {
						Ext.ux.MoneyField.superclass.initEvents.call(this);
						this.el.on("keydown", this.stopEventFunction, this);
						this.el.on("keyup", this.mapCurrency, this);
						this.el.on("keypress", this.stopEventFunction, this);
					},

					KEY_RANGES : {
						numeric : [ 48, 57 ],
						padnum : [ 96, 105 ]
					},

					isInRange : function(charCode, range) {
						return charCode >= range[0] && charCode <= range[1];
					},

					formatCurrency : function(evt, floatPoint, decimalSep,
							thousandSep) {
						floatPoint = !isNaN(floatPoint) ? Math.abs(floatPoint)
								: 2;
						thousandSep = typeof thousandSep != "string" ? ","
								: thousandSep;
						decimalSep = typeof decimalSep != "string" ? "."
								: decimalSep;
						var key = evt.getKey();

						if (this.isInRange(key, this.KEY_RANGES["padnum"])) {
							key -= 48;
						}

						this.sign = (this.allowNegative && (key == 45 || key == 109)) ? "-"
								: (key == 43 || key == 107 || key == 16) ? ""
										: this.sign;

						var character = (this.isInRange(key,
								this.KEY_RANGES["numeric"]) ? String
								.fromCharCode(key) : "");
						var field = this.el.dom;
						var value = (field.value.replace(/\D/g, "").replace(
								/^0+/g, "") + character).replace(/\D/g, "");
						var length = value.length;

						if (character == "" && length > 0 && key == 8) {
							length--;
							value = value.substr(0, length);
							evt.stopEvent();
						}

						if (field.maxLength + 1 && length >= field.maxLength)
							return false;

						length <= floatPoint
								&& (value = new Array(floatPoint - length + 2)
										.join("0")
										+ value);
						for ( var i = (length = (value = value.split("")).length)- floatPoint; (i -= 3) > 0; value[i - 1] += thousandSep);
						floatPoint && floatPoint < length && (value[length - ++floatPoint] += decimalSep);
						field.value = (this.showCurrency && this.currencyPosition == 'left' ? this.currency : '')
								+ (this.sign ? this.sign : '')
								+ value.join("")
								+ (this.showCurrency && this.currencyPosition != 'left' ? this.currency : '');
					},

					mapCurrency : function(evt) {
						switch (this.format) {
						case 'BRL':
							this.currency = 'R$';
							this.currencyPosition = 'left';
							this.formatCurrency(evt, 2, ',', '.');
							break;

						case 'EUR':
							this.currency = ' €';
							this.currencyPosition = 'right';
							this.formatCurrency(evt, 2, ',', '.');
							break;

						case 'USD':
							this.currencyPosition = 'left';
							this.currency = '$';
							this.formatCurrency(evt, 2);
							break;

						default:
							this.formatCurrency(evt, 2);
						}
					},

					stopEventFunction : function(evt) {
						var key = evt.getKey();

						if (this.isInRange(key, this.KEY_RANGES["padnum"])) {
							key -= 48;
						}

						if (((key >= 41 && key <= 122) || key == 32 || key == 8 || key > 186)
								&& (!evt.altKey && !evt.ctrlKey)) {
							evt.stopEvent();
						}
					},

					getCharForCode : function(keyCode) {
						var chr = '';
						switch (keyCode) {
						case 48:
						case 96: // 0 and numpad 0
							chr = '0';
							break;

						case 49:
						case 97: // 1 and numpad 1
							chr = '1';
							break;

						case 50:
						case 98: // 2 and numpad 2
							chr = '2';
							break;

						case 51:
						case 99: // 3 and numpad 3
							chr = '3';
							break;

						case 52:
						case 100: // 4 and numpad 4
							chr = '4';
							break;

						case 53:
						case 101: // 5 and numpad 5
							chr = '5';
							break;

						case 54:
						case 102: // 6 and numpad 6
							chr = '6';
							break;

						case 55:
						case 103: // 7 and numpad 7
							chr = '7';
							break;

						case 56:
						case 104: // 8 and numpad 8
							chr = '8';
							break;

						case 57:
						case 105: // 9 and numpad 9
							chr = '9';
							break;

						case 45:
						case 189:
						case 109:
							chr = '-';
							break;

						case 43:
						case 107:
						case 187:
							chr = '+';
							break;

						default:
							chr = String.fromCharCode(keyCode); // key pressed
																// as a
							// lowercase string
							break;
						}
						return chr;
					}
				});

Ext.ComponentMgr.registerType('moneyfield', Ext.ux.MoneyField);
// ************** FIM TEXTFIELD COMO MOEDA ******************************

// ******** TEXTFIELD COM FORMATAÇÃO DE CNPJ ***************
Ext.ux.CNPJField = function(config) {
	var defConfig = {
		autocomplete : 'off',
		width : 140,
		soNumero : false,
		maxLength : (this.soNumero) ? 15 : 19
	};
	Ext.applyIf(config, defConfig);
	Ext.ux.CNPJField.superclass.constructor.call(this, config);
};

Ext.extend(Ext.ux.CNPJField, Ext.form.TextField,
				{
					initEvents : function() {
						Ext.ux.CNPJField.superclass.initEvents.call(this);
						this.el.on("keydown", this.stopEventFunction, this);
						this.el.on("keyup", this.formatCNPJ, this);
						this.el.on("keypress", this.stopEventFunction, this);
						this.el.on("focus", this.startCNPJ, this);
					},

					KEY_RANGES : {
						numeric : [ 48, 57 ],
						padnum : [ 96, 105 ]
					},

					isInRange : function(charCode, range) {
						return charCode >= range[0] && charCode <= range[1];
					},

					stopEventFunction : function(evt) {
						var key = evt.getKey();

						if (this.isInRange(key, this.KEY_RANGES["padnum"])) {
							key -= 48;
						}

						if (((key >= 41 && key <= 122) || key == 32 || key == 8 || key > 186)
								&& (!evt.altKey && !evt.ctrlKey)) {
							evt.stopEvent();
						}
					},

					startCNPJ : function() {
						var field = this.el.dom;
						if (field.value == '') {
							field.value = '';
							if (this.soNumero) {
								field.value = '000000000000000';
							} else {
								field.value = '000.000.000/0000-00';
							}
						}
					},

					formatCNPJ : function(evt) {
						var key = evt.getKey();

						if (this.isInRange(key, this.KEY_RANGES["padnum"])) {
							key -= 48;
						}

						var character = (this.isInRange(key,
								this.KEY_RANGES["numeric"]) ? String
								.fromCharCode(key) : "");
						var field = this.el.dom;
						var value = (field.value.replace(/\D/g, "").substr(1) + character)
								.replace(/\D/g, "");
						var length = value.length;

						if (character == "" && length > 0 && key == 8) {
							length--;
							value = value.substr(0, length);
							evt.stopEvent();
						}

						if (field.maxLength + 1 && length >= field.maxLength)
							return false;

						if (length < 15) {
							var qtn = '';
							for ( var i = 0; i < 15 - length; i++) {
								qtn = qtn + '0';
							}
							value = qtn + value;
							length = 15;
						}

						if (this.soNumero) {
							field.value = value;
						} else {
							var result = '';
							result = value.substr(0, 3) + '.'
									+ value.substr(3, 3) + '.'
									+ value.substr(6, 3) + '/'
									+ value.substr(9, 4) + '-'
									+ value.substr(13);
							field.value = result;
						}
					}
				});

Ext.ComponentMgr.registerType('cnpjfield', Ext.ux.CNPJField);
// ******** FIM TEXTFIELD COM FORMATAÇÃO DE CNPJ ***************

// ******** TEXTFIELD COM FORMATAÇÃO DE CPF ***************
Ext.ux.CPFField = function(config) {
	var defConfig = {
		autocomplete : 'off',
		width : 100,
		soNumero : false,
		maxLength : (this.soNumero) ? 11 : 14
	};
	Ext.applyIf(config, defConfig);
	Ext.ux.CPFField.superclass.constructor.call(this, config);
};

Ext.extend(Ext.ux.CPFField, Ext.form.TextField,
				{
					initEvents : function() {
						Ext.ux.CPFField.superclass.initEvents.call(this);
						this.el.on("keydown", this.stopEventFunction, this);
						this.el.on("keyup", this.formatCPF, this);
						this.el.on("keypress", this.stopEventFunction, this);
						this.el.on("focus", this.startCPF, this);
					},

					KEY_RANGES : {
						numeric : [ 48, 57 ],
						padnum : [ 96, 105 ]
					},

					isInRange : function(charCode, range) {
						return charCode >= range[0] && charCode <= range[1];
					},

					stopEventFunction : function(evt) {
						var key = evt.getKey();

						if (this.isInRange(key, this.KEY_RANGES["padnum"])) {
							key -= 48;
						}

						if (((key >= 41 && key <= 122) || key == 32 || key == 8 || key > 186) && (!evt.altKey && !evt.ctrlKey)) {
							evt.stopEvent();
						}
					},

					startCPF : function() {
						var field = this.el.dom;
						if (field.value == '') {
							field.value = '';
							if (this.soNumero) {
								field.value = '00000000000';
							} else {
								field.value = '000.000.000-00';
							}
						}
					},

					formatCPF : function(evt) {
						var key = evt.getKey();

						if (this.isInRange(key, this.KEY_RANGES["padnum"])) {
							key -= 48;
						}

						var character = (this.isInRange(key,
								this.KEY_RANGES["numeric"]) ? String
								.fromCharCode(key) : "");
						var field = this.el.dom;
						var value = (field.value.replace(/\D/g, "").substr(1) + character)
								.replace(/\D/g, "");
						var length = value.length;

						if (character == "" && length > 0 && key == 8) {
							length--;
							value = value.substr(0, length);
							evt.stopEvent();
						}

						if (field.maxLength + 1 && length >= field.maxLength)
							return false;

						if (length < 11) {
							var qtn = '';
							for ( var i = 0; i < 11 - length; i++) {
								qtn = qtn + '0';
							}
							value = qtn + value;
							length = 11;
						}

						if (this.soNumero) {
							field.value = value;
						} else {
							var result = '';
							result = value.substr(0, 3) + '.'
									+ value.substr(3, 3) + '.'
									+ value.substr(6, 3) + '-'
									+ value.substr(9);
							field.value = result;
						}
					}
				});

Ext.ComponentMgr.registerType('cpffield', Ext.ux.CPFField);
// ******** FIM TEXTFIELD COM FORMATAÇÃO DE CPF ***************

// ************** FUNÇÕES DE VALIDAÇÕES *************************************

// Validação de CPF
function validacpf(CPF) {
	var i;
	s = CPF.replace(/\D/g, "");
	if (parseInt(s) == 0) {
		return false;
	}
	var c = s.substr(0, 9);
	var dv = s.substr(9, 2);
	var d1 = 0;
	for (i = 0; i < 9; i++) {
		d1 += c.charAt(i) * (10 - i);
	}
	if (d1 == 0) {
		return false;
	}
	d1 = 11 - (d1 % 11);
	if (d1 > 9)
		d1 = 0;
	if (dv.charAt(0) != d1) {
		return false;
	}
	d1 *= 2;
	for (i = 0; i < 9; i++) {
		d1 += c.charAt(i) * (11 - i);
	}
	d1 = 11 - (d1 % 11);
	if (d1 > 9)
		d1 = 0;
	if (dv.charAt(1) != d1) {
		return false;
	}
	return true;
}; // Termina validação CPF

// Validação de CNPJ
function VerifyCNPJ(CNPJ) {
	CNPJ = CNPJ.replace(/\D/g, "");
	CNPJ = CNPJ.replace(/^0+/, "");
	if (parseInt(CNPJ) == 0) {
		return false;
	} else {
		g = CNPJ.length - 2;
		if (RealTestaCNPJ(CNPJ, g) == 1) {
			g = CNPJ.length - 1;
			if (RealTestaCNPJ(CNPJ, g) == 1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
};

function RealTestaCNPJ(CNPJ, g) {
	var VerCNPJ = 0;
	var ind = 2;
	var tam;
	for (f = g; f > 0; f--) {
		VerCNPJ += parseInt(CNPJ.charAt(f - 1)) * ind;
		if (ind > 8) {
			ind = 2;
		} else {
			ind++;
		}
	}
	VerCNPJ %= 11;
	if (VerCNPJ == 0 || VerCNPJ == 1) {
		VerCNPJ = 0;
	} else {
		VerCNPJ = 11 - VerCNPJ;
	}
	if (VerCNPJ != parseInt(CNPJ.charAt(g))) {
		return (0);
	} else {
		return (1);
	}
}; // Termina validação CNPJ

// ********************* FIM VALIDAÇÕES ***********************

// ********************* VTYPES *******************************

Ext.apply(Ext.form.VTypes, {
	cpf : function(val, field) {
		return validacpf(val);
	},

	cpfText : 'CPF não é válido!'
});

Ext.apply(Ext.form.VTypes, {
	cnpj : function(val, field) {
		return VerifyCNPJ(val);
	},

	cnpjText : 'CNPJ não é válido!'
});

// ******************** FIM VTYPES ***********************//


function loadCSS(url) {
    var lnk = document.createElement('link');
    lnk.setAttribute('type', "text/css" );
    lnk.setAttribute('rel', "stylesheet" );
    lnk.setAttribute('href', url );
    document.getElementsByTagName("head").item(0).appendChild(lnk);
}

function loadJS(url) {
    var lnk = document.createElement('script');
    lnk.setAttribute('type', "text/javascript" );
    lnk.setAttribute('src', url );
    document.getElementsByTagName("head").item(0).appendChild(lnk);
}

function removejscssfile(filename, filetype){
	var targetelement=(filetype=="js")? "script" : (filetype=="css")? "link" : "none" //determine element type to create nodelist from
	var targetattr=(filetype=="js")? "src" : (filetype=="css")? "href" : "none" //determine corresponding attribute to test for
	var allsuspects=document.getElementsByTagName(targetelement)
	for (var i=allsuspects.length; i>=0; i--){ //search backwards within nodelist for matching elements to remove
		if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)!=null && allsuspects[i].getAttribute(targetattr).indexOf(filename)!=-1)
			allsuspects[i].parentNode.removeChild(allsuspects[i]) //remove element by calling parentNode.removeChild()
 	}
}

	
