function geraTelaCadastroDocumento(opcao) {
	
	loadCSS("/webOper2.0/paginas/bootstrap/css/bootstrap.css");
	loadJS("/webOper2.0/paginas/bootstrap/js/bootstrap.js");

	if ($("#dialog").hasClass('ui-dialog-content') == true) {// se a dialog
		// j� foi
		// inicilizada,
		// apenas abrir
		$("#dialog").dialog("open");
	} else {// sen�o, inicializar tudo
		$.noConflict();
		// $.ui.fancytree.debugLevel = 1; // silence debug output
		$('#dialog').dialog({
			autoOpen : false,
			minWidth : 500,
			modal : true,
			title : "SGQ - Sistema de Gest�o da Qualidade",
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "explode",
				duration : 1000
			},
			close : function(event, ui) {
				$(":ui-fancytree").fancytree("destroy");
				$(this).dialog('close');
				removejscssfile("bootstrap.js", "js") //remove all occurences of "somescript.js" on page
				removejscssfile("bootstrap.css", "css") //remove all occurences "somestyle.css" on page
			},
			buttons : {
				'Fechar' : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#dialog").dialog("open");
	}
	
	$.contextMenu( 'destroy' );//destr�i todos os menus de contexto. �nica forma de fazer sumir o menu de contexto para o caso de visualiza��o apenas
	
	inicializaTree(opcao);
}

function inicializaTree(opcao) {
	if (opcao == 'cadastro') {
		$("#tree").fancytree({
			extensions : [ 'contextMenu' ],
			source : {
				url : "documento!listAll.action"
			},
			lazyload : function(e, data) {
				data.result = $.ajax({
					url : 'documento!buscaFilhos.action',
					dataType : 'json',
					data : {
						"documento.id" : data.node.key
					},
				})
			},
			contextMenu : {
				menu : {
					'edit' : {
						'name' : 'Alterar',
						'icon' : 'edit'
					},
					'iFile' : {
						'name' : 'Inserir Arquivo',
						'icon' : 'cut'
					},
					'iPasta' : {
						'name' : 'Inserir Pasta',
						'icon' : 'copy'
					},
					'iPastaRaz' : {
						'name' : 'Inserir Pasta na Raiz',
						'icon' : 'copy'
					},
					'delete' : {
						'name' : 'Excluir',
						'icon' : 'delete'
					}
				},
				actions : function(node, action, options) {
					if (action == 'edit') {// alterar arquivo ou pasta
						abreTelaEdicao(node, 'U');
					} else if (action == 'iFile') {// incluir arquivo
						abreTelaEdicao(node, 'IF');
					} else if (action == 'iPasta') {// incluir pasta
						abreTelaEdicao(node, 'IP');
					} else if (action == 'delete') {// excluir arquivo ou pasta
						excluir(node);
					} else if (action == 'iPastaRaz') {// excluir pasta na raiz
						abreTelaEdicao(node, 'IPR');
					}
				}
			},
			activate : function(event, data) {
				var node = data.node;
				if (node.data.href) {
					window.open(node.data.href, "_self");
				}
			}
		});

	} else if (opcao == 'visualizacao') {
		$("#tree").fancytree({
			source : {
				url : "documento!listAll.action"
			},
			lazyload : function(e, data) {
				data.result = $.ajax({
					url : 'documento!buscaFilhos.action',
					dataType : 'json',
					data : {
						"documento.id" : data.node.key
					},
				})
			},
			activate : function(event, data) {
				var node = data.node;
				if (node.data.href) {
					window.open(node.data.href, "_self");
				}
			}
		})
	}
}

function excluir(node) {
	$("#dialog-confirm").attr("title", "Confirmar exclusão");
	$("#dialog-confirm")
			.html(
					"<p><span class='ui-icon ui-icon-alert' style='float: left; margin: 0 7px 20px 0;'></span>Tem certeza que deseja excluir este arquivo/pasta e todo seu conteúdo?</p>");
	$("#dialog-confirm").dialog(
			{
				resizable : false,
				height : 140,
				modal : true,
				minWidth : 600,
				buttons : {
					"Sim" : function() {
						$
								.ajax({
									type : "POST",
									url : "documento!excluir.action",
									data : {
										"documento.id" : node.key
									},
									success : function(data) {
										alert(data.msg);
										var tree = $("#tree").fancytree(
												"getRootNode").tree.reload();// recarrega
										// os dados
										// atualizados
									}
								});
						$(this).dialog("close");
					},
					"N�o" : function() {
						$(this).dialog("close");
					}
				}
			});
}

function abreTelaEdicao(node, action) {
	$("#dialog-doc").removeClass("hide");
	var nodePai = "";
	var docPaiId = 0;
	var id = 0;
	var nomDoc = "";
	var tituloJanela = "";

	if (action == 'U') {// atualiza��o de n�
		nodePai = node.getParent();
		if (nodePai.isRoot()) {
			docPaiId = '';
		} else {
			docPaiId = nodePai.key;
		}
	} else if ((action == 'IF') || (action == 'IP')) {// inserir documento ou
		// inserir pasta
		nodePai = node;
		docPaiId = node.key;
	}

	if (action == 'U') {
		id = node.key;
		nomDoc = node.title;
		tituloJanela = "Atualizando dados do documento";
		$("#grpArq").show();
		$("#dialog-doc").attr("title", tituloJanela);
	} else if (action == 'IF') {
		id = 0;
		nomDoc = "Novo arquivo";
		tituloJanela = "Inserindo documento";
		$("#grpArq").show();
		$("#dialog-doc").attr("title", tituloJanela);
	} else if (action == 'IP') {
		id = 0;
		nomDoc = "Nova pasta";
		tituloJanela = "Inserindo pasta de documentos";
		$("#grpArq").hide();
		$("#dialog-doc").attr("title", tituloJanela);
	} else if (action == 'IPR') {
		id = 0;
		nomDoc = "Nova pasta";
		docPaiId = null;
		tituloJanela = "Inserindo pasta de documentos na raíz";
		$("#grpArq").hide();
		$("#dialog-doc").attr("title", tituloJanela);
	}

	$("#id").val(id);
	$("#nomDoc").val(nomDoc);
	$("#docPai").val(docPaiId);

	$("#dialog-doc").dialog({
		autoOpen : true,
		width : 700,
		modal : true,
		close : function() {
			$("#dialog-doc").dialog('destroy');
			$("#dialog-doc").addClass("hide");
			$('#result').hide();
			var tree = $("#tree").fancytree("getRootNode").tree.reload();
		}
	});

}