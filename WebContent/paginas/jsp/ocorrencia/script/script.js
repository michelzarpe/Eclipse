//utilizando prototype
//iniciando um m�todo
Event.observe(window, 'load', init, false);

// m�todo necess�rio para ocultar o bot�o btEnviar
// e tamb�m adicionar o evento onkeyup a caixa de texto
function init() {
	$('btEnviar').style.display = 'none';
	Event.observe('numero', 'keyup', recuperar, false);
}

/**
 * m�todo necess�rio para recuperar os dados vindos do servidor observe que o
 * par�metro � enviado e como � enviado
 * 
 */
function recuperar() {
	var url = 'banco!calcular.action';
	var params = 'numero=' + escape($F('numero'));
	var target = 'resultado';
	var retorno = new Ajax.Updater(target, url, {
		method :'post',
		parameters :params
	});
}