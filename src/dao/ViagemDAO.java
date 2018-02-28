package dao;

import java.util.List;

import bean.Colaborador;
import bean.Usuario;
import bean.Viagem;

public interface ViagemDAO extends BaseDAO<Viagem> {
	List<Viagem> getViagensAbertas(Colaborador colaborador, Usuario usuario);
}
