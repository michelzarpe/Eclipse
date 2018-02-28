package action;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.WebOperConstantes;
import bean.Acesso;
import bean.Usuario;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import dao.AcessoDAO;
import dao.UsuarioDAO;

@Results({
		@Result(name = "input", value = "/"),
		@Result(name = "success", type = ServletRedirectResult.class, value = "pesquisa!input.action") })
@ParentPackage("default")
public class LoginAction extends ActionSupport implements SessionAware {
	private static final long		serialVersionUID	= 1L;
	private String						codUsu;
	private String						senUsu;
	private Map<String, Usuario>	sessionMap;
	private InputStream				inputStream;
	@Autowired
	@Qualifier("HUsuarioDAO")
	private UsuarioDAO				usuarioDAO;
	@Autowired
	@Qualifier("HAcessoDAO")
	private AcessoDAO					acessoDAO;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String login() throws Exception {
		// UsuarioDAO usuarioDAO = Factory.criaUsuarioDAO();
		Usuario usuario = usuarioDAO.autentica(codUsu, senUsu);
		if (usuario == null) {
			addActionError("Não foi possível autenticar o usuário. Verifique seu usuário e senha.");
			return INPUT;
		} else {
			sessionMap.put(WebOperConstantes.USUARIO, usuario);
			registraAcesso(usuario);
		}
		return SUCCESS;
	}

	@RequiredStringValidator(message = "Forneçaseu código de usuário.")
	public String getCodUsu() {
		return codUsu;
	}

	@RequiredStringValidator(message = "Forneçasua senha de acesso.")
	public String getSenUsu() {
		return senUsu;
	}

	@SkipValidation
	public void registraAcesso(Usuario usuario) throws Exception {
		HttpSession sessaoHttp = ServletActionContext.getRequest().getSession(false);// se
		// nï¿½o tiver sessï¿½o ativa, nï¿½o cria outra nova  
		Acesso acesso;
		acesso = acessoDAO.getAcessoByIdSession(sessaoHttp.getId());
		if (acesso == null) {
			acesso = new Acesso();
			Date dataAtual = new Date();
			Timestamp t = new Timestamp(dataAtual.getTime());
			acesso.setDatAce(t);
			acesso.setIdSession(sessaoHttp.getId());
			acesso.setUsuario(usuario);
			acessoDAO.insert(acesso);
		}
	}

	public void setCodUsu(String codUsu) {
		this.codUsu = codUsu;
	}

	public void setSenUsu(String senUsu) {
		this.senUsu = senUsu;
	}

	@Override
	public void setSession(Map session) {
		this.sessionMap = session;

	}

	@SkipValidation
	public String logout() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Calendar calendar = Calendar.getInstance();
		if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
			try {
				HttpSession sessao = ServletActionContext.getRequest().getSession();
				Acesso acesso = acessoDAO.getAcessoByIdSession(sessao.getId());
				if (acesso != null) {
					acesso.setDatSai(calendar.getTime());
					acessoDAO.update(acesso);
				}
				((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
			} catch (IllegalStateException e) {
				System.err.println(e);
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return LOGIN;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

}
