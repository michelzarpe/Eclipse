function VerificaCPF() {
	var cpf = replaceAll(document.getElementById("cpf").value, ".", "");
	cpf = replaceAll(cpf, "-", "");
	if (cpf.localeCompare('') > 0)
		if (vercpf(cpf)) {
			document.getElementById("colaborador.empresa.id").focus();
		} else {
			errors = "1";
			if (errors) {
				Ext.Msg.show({
					title :'Aviso',
					msg :'CPF inválido',
					buttons :Ext.MessageBox.OK,
					icon :Ext.MessageBox.WARNING,
					fn : function() {
						document.getElementById("cpf").focus();
					}
				});
			}
		}
}
function vercpf(cpf) {
	if (cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111"
			|| cpf == "22222222222" || cpf == "33333333333"
			|| cpf == "44444444444" || cpf == "55555555555"
			|| cpf == "66666666666" || cpf == "77777777777"
			|| cpf == "88888888888" || cpf == "99999999999")
		return false;
	add = 0;
	for (i = 0; i < 9; i++)
		add += parseInt(cpf.charAt(i)) * (10 - i);
	rev = 11 - (add % 11);
	if (rev == 10 || rev == 11)
		rev = 0;
	if (rev != parseInt(cpf.charAt(9)))
		return false;
	add = 0;
	for (i = 0; i < 10; i++)
		add += parseInt(cpf.charAt(i)) * (11 - i);
	rev = 11 - (add % 11);
	if (rev == 10 || rev == 11)
		rev = 0;
	if (rev != parseInt(cpf.charAt(10)))
		return false;
	return true;
}

function replaceAll(str, from, to) {
	var idx = str.indexOf(from);
	while (idx > -1) {
		str = str.replace(from, to);
		idx = str.indexOf(from);
	}
	return str;
}