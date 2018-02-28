package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import bean.Usuario;

public interface UsuarioDAO extends BaseDAO<Usuario> {

	Usuario autentica(String codUsu, String senUsu);

	List<Usuario> listReceptoresEmail(String tipUsu);
	
	List<Usuario> listUsuariosAtivos();

	Usuario loadByNumCad(int codEmp, long numCad);

	public Serializable insert(Usuario obj) throws Exception;

	List<Usuario> busca(Map<String, Object> parametros)throws Exception;
}
