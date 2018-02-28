package dao;

import java.util.List;

import bean.Colaborador;
import bean.Dependente;
import bean.Empresa;

public interface DependenteDAO extends BaseDAO<Dependente> {
	public List <Dependente> retDependentes(int colaborador);
}
