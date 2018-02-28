function carregar() {
	var url = 'banco!carregar.action';
	var elemento = 'resposta';
	var retorno = new Ajax.Updater(elemento, url, {
		method :'get',
		evalScripts :true
	});
}