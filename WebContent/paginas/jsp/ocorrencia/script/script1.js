//utilizando prototype
//evento onload a ser observado pelo Prototype
//necess�rio para que ele execute o m�todo init()
Event.observe(window, 'load', init, false);

// m�todo que ser� inicializado no carregamento da p�gina
function init() {
	// chamada do efeito de Script.aculo.us
	new Effect.BlindDown('bnc', {
		delay :.1,
		duration :3
	});
	// evento onblur a ser observado pelo framework Prototype
	Event.observe('nomBan', 'blur', recuperar, false);
}

/**
 * M�todo respons�vel por transmitir os dados ao servidor
 */
function recuperar() {
	var url = 'banco!gravar.action';
	var params = 'nomBan=' + escape($F('nomBan')) + '&id='+ escape($F('id'));
	var target = 'resultado';
	// o retorno ser� acompanhado pleo m�todo resposta()
	// usando o par�metro onComplete de Ajax.Request
	var retorno = new Ajax.Request(url, {
		method :'post',
		parameters :params,
		onComplete :resposta,
		onLoading : carregando
	});
}
// m�todo respons�vel por captar a resposta do servidor
function resposta(resp) {
	var elemento = $('msg');
	// a resposta ser� decodificado do servidor
	// e ser� escrita na tag <span id='msg'/>
	elemento.innerHTML = resp.responseText;
	// novamente uma anima��o de Script.aculo.us
	// fazendo a resposta pulsar na tela do usu�rio
	new Effect.Pulsate('msg', {
		duration :1.5,
		from :0.0,
		to :1.0
	});
}

function carregando(){
	$('output').style.visibility='visible';
}