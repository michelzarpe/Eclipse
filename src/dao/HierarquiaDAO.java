package dao;

import java.util.List;

import util.NivelOrganograma;
import bean.Hierarquia;

public interface HierarquiaDAO extends BaseDAO<Hierarquia> {

	List<Hierarquia> listAllNivelEmpresa();

	List<Hierarquia> listAllNivelFilial(String codLoc);

	List<Hierarquia> listAllNivelCliente(String codLoc);

	List<Hierarquia> listAllNivelCidade(String codLoc);

	List<Hierarquia> listAllNivelPosto(String codLoc);

	Hierarquia loadByCodLoc(String codLoc);

	Hierarquia loadLocalByNivel(String codLoc, NivelOrganograma nivel);

	List<Hierarquia> busca(String dadoBusca, String nivelAnterior, String nivel, String codLoc);

	Hierarquia getHierarquiaAtual(int numLoc);
}
