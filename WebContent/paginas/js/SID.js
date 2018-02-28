function loginSapiens() {
	Ext.Ajax.request({
				url : 'sapiens!loginSapiensSID.action',
				success : function(result, request) {
				},
				failure : function(result, request) {
				}
			});
}

function insereSolicitacao(){
	Ext.Ajax.request({
				url : 'sapiens!novaSolicitacao.action',
				success : function(result, request) {
				},
				failure : function(result, request) {
				}
			});
}

function insereRequisicao(){
	Ext.Ajax.request({
				url : 'sapiens!novaRequisicao.action',
				success : function(result, request) {
				},
				failure : function(result, request) {
				}
			});
}