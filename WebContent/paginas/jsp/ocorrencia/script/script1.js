//utilizando prototype
//evento onload a ser observado pelo Prototype
//necessário para que ele execute o método init()
Event.observe(window, 'load', init, false);

// método que será inicializado no carregamento da página
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
 * Método responsável por transmitir os dados ao servidor
 */
function recuperar() {
	var url = 'banco!gravar.action';
	var params = 'nomBan=' + escape($F('nomBan')) + '&id='+ escape($F('id'));
	var target = 'resultado';
	// o retorno será acompanhado pleo método resposta()
	// usando o parâmetro onComplete de Ajax.Request
	var retorno = new Ajax.Request(url, {
		method :'post',
		parameters :params,
		onComplete :resposta,
		onLoading : carregando
	});
}
// método responsável por captar a resposta do servidor
function resposta(resp) {
	var elemento = $('msg');
	// a resposta será decodificado do servidor
	// e será escrita na tag <span id='msg'/>
	elemento.innerHTML = resp.responseText;
	// novamente uma animação de Script.aculo.us
	// fazendo a resposta pulsar na tela do usuário
	new Effect.Pulsate('msg', {
		duration :1.5,
		from :0.0,
		to :1.0
	});
}

function carregando(){
	$('output').style.visibility='visible';
}