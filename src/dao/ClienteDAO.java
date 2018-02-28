package dao;

import java.util.List;
import java.util.Map;

import bean.Cliente;

public interface ClienteDAO extends BaseDAO<Cliente> {

	List<Cliente> listAllByEmp(int idEmpresa);
	List<Cliente> busca(Map<String, Object> parametros);
}
