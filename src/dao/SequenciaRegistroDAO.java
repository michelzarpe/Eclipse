package dao;

import bean.SequenciaRegistro;

public interface SequenciaRegistroDAO extends BaseDAO<SequenciaRegistro>{
	public SequenciaRegistro retSeqRegBd(int seqReg); 
}
