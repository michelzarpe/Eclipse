package action;

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

import util.Util;
import bean.Colaborador;
import bean.Hierarquia;

import com.opensymphony.xwork2.ActionSupport;

import dao.ColaboradorDAO;

@Results({ @Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
		"inputName", "inputStream" }) })
public class FichaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private int						id;
	private InputStream			inputStream;
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO		colaboradorDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void imprimir() throws Exception {
		Colaborador colaborador = colaboradorDAO.loadById(this.id);
		Map<String, String> parametros = new HashMap<String, String>();
		for (Hierarquia hierarquia : colaborador.getLocal().getHierarquias()) {
			parametros.put("LocalOrganograma", hierarquia.getCodLoc());
		}
		List<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		util.geraRelatorio(colaboradores, "FichaAdmissional.jasper", colaborador.getNomFun()
				+ ".pdf", parametros, "pdf");
	}

	public void imprimirAdmissao() throws Exception {
		try {
			Colaborador colaborador = colaboradorDAO.loadById(this.id);
			Map<String, String> parametros = new HashMap<String, String>();
			try {
				for (Hierarquia hierarquia : colaborador.getLocal().getHierarquias()) {
					parametros.put("LocalOrganograma", hierarquia.getCodLoc());
				}
			} catch (NullPointerException e) {
				System.err.println("Erro buscando local no organograma do colaborador " + this.id);
			}
			List<Colaborador> colaList = new ArrayList<Colaborador>();
			colaList.add(colaborador);
			util.geraRelatorio(colaList, "FichaAdmissional.jasper",
					"Ficha" + colaborador.getNomFun() + ".pdf", parametros, "pdf");

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ColaboradorDAO getColaboradorDAO() {
		return colaboradorDAO;
	}

	public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
		this.colaboradorDAO = colaboradorDAO;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

}
