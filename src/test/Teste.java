package test;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import bean.Usuario;
import dao.ItemSolicitacaoDAO;
import dao.UsuarioDAO;

public class Teste {

	@Autowired
	@Qualifier("HUsuarioDAO")
	static UsuarioDAO				usuarioDAO;
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	static ItemSolicitacaoDAO	itemSolicitacaoDAO;

	public static void main(String[] args) throws Exception {
		List<Usuario> usuarios = usuarioDAO.listUsuariosAtivos();
		for (Iterator iterator = usuarios.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			System.out.println("Usuário: " + usuario.getNomFun());
		}
	}

}
