package dao;

import java.util.List;
import java.util.Map;

import bean.Cidade;
import bean.Cidade.Estado;

public interface CidadeDAO extends BaseDAO<Cidade> {
	List<Cidade> listAllByUF(String codEst);
	List<Cidade> busca(Map<String, Object> parametros);
}
