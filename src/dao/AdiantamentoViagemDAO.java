package dao;

import java.util.List;

import bean.AdiantamentoViagem;
import bean.Usuario;

public interface AdiantamentoViagemDAO extends BaseDAO<AdiantamentoViagem> {

	List<AdiantamentoViagem> listPendentes(Usuario usuario);

}
