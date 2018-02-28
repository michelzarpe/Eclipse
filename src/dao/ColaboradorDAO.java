package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import bean.Centro;
import bean.Cliente;
import bean.Colaborador;
import bean.Usuario;

public interface ColaboradorDAO extends BaseDAO<Colaborador> {

	List<Colaborador> busca(Map<String, Object> parametros);

	List<Colaborador> colaboradoresAtivos();

	List<Colaborador> listAllByFilial(Cliente cliente, Usuario usuario);

	List<Colaborador> listAllByLocal(int numLoc, Usuario usuario);

	List<Colaborador> listAtivosByEmp(int id, Usuario usuario);

	List<Colaborador> listBySituacao(int situacao) throws Exception;

	List<Colaborador> listBySituacao(int situacao, Integer start, Integer limit, String sort,
			String dir);

	List<Colaborador> listBySituacao(int situacao, Usuario usuario);
	
	List<Colaborador> listDemitidos(Date datIni, Date datFin, Centro centro) throws Exception;

	List<Colaborador> listSupervisores();

	List<Colaborador> listSupervisores(int numEmp);

	int loadByCPF(String cpf, Date datAdm, Date datInc, int horInc);
	
	Colaborador loadByCPF(String cpf) throws Exception;
	
	Colaborador loadByCPFEmp(String cpf, int numEmp) throws Exception;

	int loadByCPF(String cpf, int sitAfa, Date datAdm, Date datInc, int horInc);

	Colaborador loadByNumCad(long numCad, int numEmp);

	int loadIdByNumCad(long numCad, int numEmp) throws Exception;
	
	void normalizaCPF();
}
