//utilizando prototype
//iniciando um método
Event.observe(window, 'load', init, false);

// método necessário para ocultar o botão btEnviar
// e também adicionar o evento onkeyup a caixa de texto
function init() {
	$('btEnviar').style.display = 'none';
	Event.observe('numero', 'keyup', recuperar, false);
}

/**
 * método necessário para recuperar os dados vindos do servidor observe que o
 * parâmetro é enviado e como é enviado
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