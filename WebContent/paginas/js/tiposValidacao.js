function ValidaHorario(field) {
	if (field.getValue() == 6) {// 6X1
		Ext.getCmp('fHorIni').allowBlank = false;
		Ext.getCmp('fHorFin').allowBlank = false;
		Ext.getCmp('fHorIniSab').allowBlank = false;
		Ext.getCmp('fHorFinSab').allowBlank = false;
		Ext.getCmp('fHorIni').blankText = 'Para escala 6 X 1 - horário obrigatório';
		Ext.getCmp('fHorFin').blankText = 'Para escala 6 X 1 - horário obrigatório';
		Ext.getCmp('fHorIniSab').blankText = 'Para escala 6 X 1 - horário obrigatório';
		Ext.getCmp('fHorFinSab').blankText = 'Para escala 6 X 1 - horário obrigatório';
		Ext.getCmp('fHorDia').allowBlank = false;
	} else if ((field.getValue() == 7) || (field.getValue() == 10)) {// SDF e
		// DF
		Ext.getCmp('fHorIni').allowBlank = true;
		Ext.getCmp('fHorFin').allowBlank = true;
		Ext.getCmp('fHorIniSab').allowBlank = false;
		Ext.getCmp('fHorFinSab').allowBlank = false;
		Ext.getCmp('fHorIniSab').blankText = 'Para escala ' + field.getRawValue() + ' - horário obrigatório';
		Ext.getCmp('fHorFinSab').blankText = 'Para escala ' + field.getRawValue() + ' - horário obrigatório';
		Ext.getCmp('fHorDia').allowBlank = false;
	} else if (field.getValue() == 9) {// volantes
		Ext.getCmp('fHorIni').allowBlank = true;
		Ext.getCmp('fHorFin').allowBlank = true;
		Ext.getCmp('fHorIniSab').allowBlank = true;
		Ext.getCmp('fHorFinSab').allowBlank = true;
		Ext.getCmp('fHorDia').allowBlank = true;
	} else {// demais escalas (5x2, 12X36, 3x4, 4x3, 2x5)
		Ext.getCmp('fHorIni').allowBlank = false;
		Ext.getCmp('fHorFin').allowBlank = false;
		Ext.getCmp('fHorIniSab').allowBlank = true;
		Ext.getCmp('fHorFinSab').allowBlank = true;
		Ext.getCmp('fHorIni').blankText = 'Para escala ' + field.getRawValue() + ' - horário obrigatório';
		Ext.getCmp('fHorFin').blankText = 'Para escala ' + field.getRawValue() + ' - horário obrigatório';
		Ext.getCmp('fHorDia').allowBlank = false;
	}
}

function ValidaDadosVigilante(field) {
	if ((field.getValue() == '000000001') || (field.getValue() == '000000126') || (field.getValue() == '000000151')) {// vigilante, vigilante lider,
																// vigilante - seguran�a pessoal
		Ext.getCmp('fNumDRT').allowBlank = false;
		Ext.getCmp('fNumDip').allowBlank = false;
		Ext.getCmp('fOrgDip').allowBlank = false;
		Ext.getCmp('fDatFor').allowBlank = false;

		Ext.getCmp('fNumDRT').blankText = 'Para vigilante, campo obrigatório';
		Ext.getCmp('fNumDip').blankText = 'Para vigilante, campo obrigatório';
		Ext.getCmp('fOrgDip').blankText = 'Para vigilante, campo obrigatório';
		Ext.getCmp('fDatFor').blankText = 'Para vigilante, campo obrigatório';
	} else {
		Ext.getCmp('fNumDRT').allowBlank = true;
		Ext.getCmp('fNumDip').allowBlank = true;
		Ext.getCmp('fOrgDip').allowBlank = true;
		Ext.getCmp('fDatFor').allowBlank = true;
	}
}
function ValidaDadosBancarios() {
	if (!Ext.getCmp('fPgtChq').getValue()) {
		Ext.getCmp('fBan').allowBlank = false;
		Ext.getCmp('fCodAge').allowBlank = false;
		Ext.getCmp('fDigAge').allowBlank = true;
		Ext.getCmp('fConBan').allowBlank = false;
		Ext.getCmp('fDigCon').allowBlank = false;
		// ativar campos
		Ext.getCmp('fBan').enable();
		Ext.getCmp('fCodAge').enable();
		Ext.getCmp('fDigAge').enable();
		Ext.getCmp('fConBan').enable();
		Ext.getCmp('fDigCon').enable();
	} else {
		Ext.getCmp('fBan').allowBlank = true;
		Ext.getCmp('fCodAge').allowBlank = true;
		Ext.getCmp('fDigAge').allowBlank = true;
		Ext.getCmp('fConBan').allowBlank = true;
		Ext.getCmp('fDigCon').allowBlank = true;
		// desativar campos
		Ext.getCmp('fBan').disable();
		Ext.getCmp('fCodAge').disable();
		Ext.getCmp('fDigAge').disable();
		Ext.getCmp('fConBan').disable();
		Ext.getCmp('fDigCon').disable();
	}
	return true;
}

function validaInsCur(field){
	if(field.getValue()>=8){/** superior incompleto e acima disso **/
		Ext.getCmp('fDatCon').allowBlank = false;
		Ext.getCmp('fNomCur').allowBlank = false;
		Ext.getCmp('fNomIns').allowBlank = false;
	} else {
		Ext.getCmp('fDatCon').allowBlank = true;
		Ext.getCmp('fNomCur').allowBlank = true;
		Ext.getCmp('fNomIns').allowBlank = true;
	}
}