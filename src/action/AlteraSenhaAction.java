package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.views.tiles.TilesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.Util;

import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.UsuarioDAO;

@Results({
		@Result(name = "success", value = "tile.alterarSenha", type = TilesResult.class),
		@Result(name = "input", value = "tile.alterarSenha", type = TilesResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }) })
public class AlteraSenhaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	static Logger					logger				= Logger.getLogger(AlteraSenhaAction.class);
	private String					senhaAtual;
	private String					novaSenha;
	private String					confirmacaoSenha;
	private int						idUsuario;
	private InputStream			inputStream;
	@Autowired
	@Qualifier("HUsuarioDAO")
	private UsuarioDAO			usuarioDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @SkipValidation
	 * @Validations(requiredFields = {
	 * @RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "senhaAtual", message = "Você precisa digitar a sua atual senha."),
	 * @RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "novaSenha", message = "Você precisa digitar a nova senha."),
	 * @RequiredFieldValidator(type = ValidatorType.FIELD, fieldName = "confirmacaoSenha", message = "Você precisa confirmar a nova senha.") })
	 **/
	public String alterarSenha() {
		try {
			Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
			usuarioSessao.setSenUsu(novaSenha);
			usuarioDAO.update(usuarioSessao);
			addActionMessage("Senha alterada com sucesso.");
			return "success";
		} catch (Exception e) {
			addActionError("Não foi possível alterar sua senha. Causa: " + e.getMessage());
			return INPUT;
		}
	}

	public String alteraSenha() {
		try {
			Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
			if (!usuarioSessao.getSenUsu().equals(this.getSenhaAtual())) {
				inputStream = new ByteArrayInputStream(
						("{success: false, msg:'A senha digitada nao confere com a senha atual do seu usuario'}")
								.getBytes());
			} else {
				usuarioSessao.setSenUsu(novaSenha);
				usuarioDAO.update(usuarioSessao);
				inputStream = new ByteArrayInputStream(
						("{success:true, msg: 'Senha alterada com sucesso'}").getBytes());
			}
			return "sucTxt";
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("{success:false, msg:'Não foi possível alterar sua senha. Causa: " + e.getMessage() + "'")
							.getBytes());
			return "sucTxt";
		}
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
