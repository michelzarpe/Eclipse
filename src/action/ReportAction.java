package action;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.Util;

import bean.Solicitacao;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.ItemSolicitacaoDAO;

@Results({ @Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
		"inputName", "inputStream" }) })
public class ReportAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Date					datIni;
	private Date					datFin;
	private InputStream			inputStream;
	static Logger					logger				= Logger.getLogger(ReportAction.class);
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	private ItemSolicitacaoDAO	itemSolicitacaoDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Date getDatIni() {
		return datIni;
	}

	public void setDatIni(Date datIni) {
		this.datIni = datIni;
	}

	public Date getDatFin() {
		return datFin;
	}

	public void setDatFin(Date datFin) {
		this.datFin = datFin;
	}

	public void reportLiberacoes() {
		try {
			Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
			List<Solicitacao> solicitacoes = itemSolicitacaoDAO.listLiberados(datIni, datFin, usuario);
			Map<Object, Object> parametros = new HashMap<Object, Object>();
			util.geraRelatorio(solicitacoes, "Liberacoes.jasper",
					"Liberacoes" + new Date().getTime() + ".pdf", parametros, "pdf");
		} catch (Exception e) {
			logger.error("Erro gerando relatorio de solicitações." + e.getMessage());
		}

	}

	public void reportNegacoes() {
		try {
			Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
			List<Solicitacao> solicitacoes = itemSolicitacaoDAO.listNegados(datIni, datFin, usuario);
			Map<Object, Object> parametros = new HashMap<Object, Object>();
			util.geraRelatorio(solicitacoes, "Negacoes.jasper", "Negações" + new Date().getTime()
					+ ".pdf", parametros, "pdf");
		} catch (Exception e) {
			logger.error("Erro gerando relatorio de negações." + e.getMessage());
		}
	}

	public ItemSolicitacaoDAO getItemSolicitacaoDAO() {
		return itemSolicitacaoDAO;
	}

	public void setItemSolicitacaoDAO(ItemSolicitacaoDAO itemSolicitacaoDAO) {
		this.itemSolicitacaoDAO = itemSolicitacaoDAO;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
