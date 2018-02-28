package dao;

import java.util.List;
import java.util.Map;

import bean.EscalaHorarioRondaMZ;

public interface EscalaHorarioRondaMZDAO extends BaseDAO<EscalaHorarioRondaMZ>{
	public EscalaHorarioRondaMZ retEscBd(int codEsc);
	public List <EscalaHorarioRondaMZ> retEscClasse(int claesc);
	public List<EscalaHorarioRondaMZ> busca(Map<String, Object> parametros);
}
