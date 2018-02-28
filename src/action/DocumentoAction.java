package action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.DocumentoJSONResult;
import util.Util;
import bean.Documento;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.DocumentoDAO;

@Results({
		@Result(name = "txtRes", type = StreamResult.class, value = "inputStream", params = {
				"contentType", "application/json", "inputName", "inputStream" }),
		@Result(name = "jsonResult", type = DocumentoJSONResult.class, value = "JSONModel"),
		@Result(name = "stream", value = "inputStream", type = StreamResult.class, params = {
				"contentType", "${documento.formatoArquivo}; charset=UTF-8", "contentDisposition",
				"attachment;fileName=\"${documento.nomeArquivo}\"" }),
		@Result(name = "responseTXT", type = StreamResult.class, value = "inputStream", params = {
				"contentType", "text/plain", "inputName", "inputStream" }) })
public class DocumentoAction extends ActionSupport {

	private static final long	serialVersionUID	= 1L;
	static Logger					logger				= Logger.getLogger(DocumentoAction.class);
	private InputStream			inputStream;
	private Object					JSONModel;
	private Documento				documento			= new Documento();
	private String					mensagem				= "";
	private boolean				resultado;
	private List<Documento>		documentos			= new ArrayList<Documento>();
	private File					arquivo;
	private String					arquivoFileName;
	private String					arquivoContentType;
	@Autowired
	@Qualifier("HDocumentoDAO")
	private DocumentoDAO			documentoDAO;
	private boolean manterArquivo;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public String gravar() throws Exception {

		if (arquivo != null) {
			FileInputStream in = new FileInputStream(arquivo);
			byte[] dados = new byte[(int) arquivo.length()];
			in.read(dados);
			documento.setArquivo(dados);
			documento.setFormatoArquivo(arquivoContentType);
			documento.setNomeArquivo(arquivoFileName);
		}
		
		if((documento.getId() > 0) && (isManterArquivo() == true)){ //se configurado para manter o arquivo já gravado, então, carrega o documento do banco de dados
			Documento documentoBD = new Documento();
			documentoBD = documentoDAO.loadById(documento.getId());
			documento.setArquivo(documentoBD.getArquivo());
			documento.setFormatoArquivo(documentoBD.getFormatoArquivo());
			documento.setNomeArquivo(documentoBD.getNomeArquivo());
		}

		int idRet = 0;
		if (documento.getId() > 0) {
			documento.setDatAtu(util.convDateParaSql(new Date()));
		} else {
			documento.setDatAtu(util.convDateParaSql(new Date()));
			documento.setDatInc(util.convDateParaSql(new Date()));
		}
		documento.setUsuario((Usuario) util.recuperaSessao("Usuario"));
		if (documento.getDocPai().getId() > 0) {
			Documento docPai = new Documento();
			docPai = documentoDAO.loadById(documento.getDocPai().getId());
			documento.setDocPai(docPai);
		} else {
			documento.setDocPai(null);
		}
		if (documento.getId() > 0) {
			documentoDAO.merge(documento);
			idRet = documento.getId();
		} else
			idRet = (Integer) documentoDAO.insert(documento);
		if (idRet > 0) {
			inputStream = new ByteArrayInputStream(("Documento gravado com sucesso").getBytes("UTF-8"));
		} else {
			inputStream = new ByteArrayInputStream(("Documento não foi gravado.").getBytes("UTF-8"));
		}
		return "responseTXT";
	}

	public String verArquivo() {
		if ((documento != null) && (documento.getId() > 0)) {
			try {
				documento = documentoDAO.loadById(documento.getId());
				inputStream = new ByteArrayInputStream(documento.getArquivo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "stream";
	}

	public String teste() {
		try {
			inputStream = new ByteArrayInputStream(("Documento gravado com sucesso").getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "txtRes";
	}

	public String excluir() throws UnsupportedEncodingException {
		try {
			documentoDAO.delete(documento);
			inputStream = new ByteArrayInputStream(("{\"success\" : true, \"msg\" : \"Excluído com sucesso!\" }").getBytes("UTF-8"));
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("{\"success\" : false, \"msg\" : \"Documento não foi excluído!\" }").getBytes("UTF-8"));
			e.printStackTrace();
		}
		return "txtRes";
	}

	public String listAll() throws Exception {
		List<Documento> documentos = documentoDAO.listNivelRaiz();

		this.resultado = true;

		setJSONModel(documentos);
		return "jsonResult";
	}

	public String buscaFilhos() {
		Documento docGravado = new Documento();
		List<Documento> documentos = new ArrayList<Documento>();
		try {
			docGravado = documentoDAO.loadById(documento.getId());
			for (Documento documento : docGravado.getDocFilhos()) {
				documentos.add(documento);
			}
			this.resultado = true;
			setJSONModel(documentos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "jsonResult";
	}

	public DocumentoDAO getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object jSONModel) {
		JSONModel = jSONModel;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public String getArquivoFileName() {
		return arquivoFileName;
	}

	public void setArquivoFileName(String arquivoFileName) {
		this.arquivoFileName = arquivoFileName;
	}

	public String getArquivoContentType() {
		return arquivoContentType;
	}

	public void setArquivoContentType(String arquivoContentType) {
		this.arquivoContentType = arquivoContentType;
	}

	public boolean isManterArquivo() {
		return manterArquivo;
	}

	public void setManterArquivo(boolean manterArquivo) {
		this.manterArquivo = manterArquivo;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
	
	
}
