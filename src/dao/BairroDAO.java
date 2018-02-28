package dao;

import java.util.List;
import java.util.Map;

import bean.Bairro;
import bean.Cidade;

public interface BairroDAO extends BaseDAO<Bairro> {
	List <Bairro> listAllByCid(int codCid);
	Bairro listAllByCid(int codCid, int codBai);
	List <Bairro> busca(Map<String, Object> parametros);
	public void removerBairros();
}
