package dao;

import java.util.List;

import bean.Banco;


public interface BancoDAO extends BaseDAO<Banco> {
	public List<Banco> buscar(String campo, String valor) throws Exception;
}
