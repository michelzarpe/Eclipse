//método responsável por gerar o objeto XMLHttp
function objXMLHttp(){
	if(window.XMLHttpRequest){ // Mozilla, Safari...
		var objetoXMLHttp = new XMLHttpRequest();
		return objetoXMLHttp;
	}
	else if (window.ActiveXObject){//IE
		//as versões disponíveis de objetos XMLHttp do IE
		var versoes = ["MSXML2.XMLHttp.6.0", 
		               "MSXML2.XMLHttp.5.0", 
		               "MSXML2.XMLHttp.4.0", 
		               "MSXML2.XMLHttp.3.0",
		               "MSXML2.XMLHttp.2.0", 
		               "Microsoft.XMLHttp" ];
		/** Verifica as versões do objeto XMLHttp usado pelo IE e o retorna **/
		for (var i = 0; i < versoes.length; i++){
			try{
				var objetoXMLHttp = new ActiveXObject(versoes[i]);
				return objetoXMLHttp;
			} catch(ex){
				//nada
			}
		}
	}
	return false;
}

/**
	Método responsável por enviar o formulário
**/
function enviar_form(formulario){
	alert("Enviar form");
	//captura o valor dos campos do formulário
	var dados = "nomBan="+formulario.nomBan.value+"&id="+formulario.id.value;
	
	//pega o objeto XMLHttp ou XMLHttpRequest
	var oXMLHttp = objXMLHttp();
	//indica a forma como será enviado o formulário
	oXMLHttp.open("POST", "banco!gravar.action", true);
	oXMLHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	//verifica se já foi carregada a resposta pelo servidor
	oXMLHttp.onreadystatechange = function (){
		if (oXMLHttp.readyState ==4){
			if (oXMLHttp.status ==200){
				mensagem(oXMLHttp.responseText);
			} else {
				mensagem("Ocorreu o erro: " + oXMLHttp.statusText);
			}
		}
	};
	oXMLHttp.send(dados);
	return false;
}

/**
 Método responsável por exibir a mensagem ao usuário
**/
function mensagem(msg){
	document.getElementById('msg').innerHTML = msg;
}