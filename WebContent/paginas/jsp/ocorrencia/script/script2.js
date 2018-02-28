//utilizando Prototype
//evento onload a ser observado pelo Prototype
//necess�rio para que ele execute o m�todo init()
Event.observe(window, 'load', init, false);


//m�todo que ser� inicializado no carregamento da p�gina
function init(){
	Event.observe('btVisualizar', 'click', recuperar, false);
}

function recuperar(){
  	var url = 'banco!listar.action';
	//o retorno ser� acompanhado pelo m�todo resposta( )
	//usando o par�metro onComplete de Ajax.Request
	var retorno = new Ajax.Request(
						url, {
									method: 'get',
									onLoading:carregando,
									onComplete: resposta
							}
						);

}
//m�todo que oculta a mensagem de carregando
//limpa os resultados anteriores
//e processa a resposta do servidor
function resposta( resp ){
		$('output').style.visibility='hidden';
		limpaResultAnterior();
        processarXML(resp.responseXML);

}
//m�todo que exibe a mensagem de carregando
function carregando(){
	$('output').style.visibility='visible';
}


//m�todo que processa o XML
// retornado pelo servidor
function processarXML(obj){

		var dados = $A(obj.getElementsByTagName("banco"));

		dados.each(function(item){
			var codigo = item.getElementsByTagName("id")[0].firstChild.nodeValue;
			var nome =  item.getElementsByTagName("nomBan")[0].firstChild.nodeValue;
			mostrarDepartamentos(codigo, nome);

		});

}
// m�todo que limpa os resultados anteriores
function limpaResultAnterior(){
	var tBody = $("dados");
	var linhas = $A(tBody.childNodes);
	linhas.each(function(item){
		tBody.removeChild(tBody.childNodes[0]);
	});
}

//m�todo que exibe os dados na tabela
function mostrarDepartamentos(cel1, cel2) {
	var linha=$('dados').insertRow(0);
	var celula1=linha.insertCell(0);
	var celula2=linha.insertCell(1);
	celula1.innerHTML=cel1;
	celula2.innerHTML=cel2;
}