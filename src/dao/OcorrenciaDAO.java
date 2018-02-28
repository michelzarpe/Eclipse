package dao;

import java.util.Date;
import java.util.List;

import bean.Ocorrencia;
import bean.Usuario;

public interface OcorrenciaDAO extends BaseDAO<Ocorrencia> {

	List<Ocorrencia> listByDateByUser(Date dataAtual, Usuario usuario);

}
