package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import util.Util;
import bean.Centro;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.UsuarioDAO;

@Results({
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }),
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class) })
public class UsuarioAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Usuario				usuario;
	private int						id;
	private Object					xmlModel;
	private String					totalCount;
	private String					acao;
	private String					start;
	private String					limit;
	private InputStream			inputStream;
	private boolean				notFindDemitidos;
	private Class					tipo					= Usuario.class;
	
	@Autowired
	@Qualifier("HUsuarioDAO")
	private UsuarioDAO			usuarioDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public List<Centro> getCentros() {
		return centros;
	}

	public void setCentros(List<Centro> centros) {
		this.centros = centros;
	}

	private List<Centro>	centros	= new ArrayList<Centro>();

	public boolean isNotFindDemitidos() {
		return notFindDemitidos;
	}

	public void setNotFindDemitidos(boolean notFindDemitidos) {
		this.notFindDemitidos = notFindDemitidos;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String gravar() {
		try {
			for (Centro centro : centros) {
				if ((!usuario.getCentrosPermitidos().contains(centro)) && (centro != null))
					usuario.getCentrosPermitidos().add(centro);
			}
			// ColaboradorDAO colaboradorDAO = Factory.criaColaboradorDAO();
			// Colaborador colUsuario = colaboradorDAO.loadById(usuario.getId());
			Usuario usuarioBanco = usuarioDAO.loadById(usuario.getId());
			if (acao.equals("UPDATE")) {
				/** aqui copiar os dados do usuario para o usuarioBanco **/
				usuarioBanco.setCodUsu(usuario.getCodUsu());
				usuarioBanco.setSenUsu(usuario.getSenUsu());
				usuarioBanco.setTipUsu(usuario.getTipUsu());
				usuarioBanco.setEmail(usuario.getEmail());
				usuarioBanco.setRecebeAviso(usuario.isRecebeAviso());
				usuarioBanco.setCentrosPermitidos(usuario.getCentrosPermitidos());
				usuarioBanco.setGerente(usuario.getGerente());
				//HibernateUtil.getSessionFactory().getCurrentSession().evict(usuario);
				
				usuarioDAO.updateWithFlush(usuarioBanco);
			} else {
				usuarioDAO.insert(usuario);
				setAcao("UPDATE");
				// gravar();
			}
			inputStream = new ByteArrayInputStream(
					("{success:true, msg: 'CADASTRO REALIZADO COM SUCESSO'}").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("{success:false, msg: 'CADASTRO NAO FOI REALIZADO'}").getBytes());
		}
		return "sucTxt";
	}

	public String listAllXML() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (usuario.getId() > 0)
			parametros.put("id", usuario.getId());
		if ((usuario.getNomFun() != null) && (!usuario.getNomFun().equals(""))) {
			parametros.put("nomFun", usuario.getNomFun());
		}
		if (this.isNotFindDemitidos()) {// n�o trazer demitidos
			parametros.put("notFindDemitidos", 7);
		}
		parametros.put("usuario", ((Usuario) util.recuperaSessao("Usuario")).getId());
		List<Usuario> usuarios = usuarioDAO.busca(parametros);
		List<Usuario> usuList = new ArrayList<Usuario>();
		usuList.addAll(usuarios.subList(Integer.valueOf(this.start),
				util.calculaUltimoIndice(usuarios, start, limit)));
		this.totalCount = String.valueOf(usuarios.size());
		setXmlModel(usuarios);
		return "resultXML";
	}

	public String listByExample() throws Exception {
		List<Usuario> usuarios = usuarioDAO.listByExample(usuario);
		this.totalCount = String.valueOf(usuarios.size());
		this.setXmlModel(usuarios);
		return "resultXML";
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
