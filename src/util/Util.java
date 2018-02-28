package util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import bean.Empresa;
import bean.Estoque;
import bean.Hierarquia;
import bean.ItemSolicitacao;
import bean.ItemSolicitacao.Situacao;
import bean.Solicitacao;
import bean.Uniforme;
import bean.Usuario;

import com.opensymphony.xwork2.ActionContext;

import dao.ColaboradorDAO;
import dao.EstoqueDAO;
import dao.HierarquiaDAO;
import dao.ItemSolicitacaoDAO;
import dao.SolicitacaoDAO;

@Service("Util")
public class Util {
	static Logger					logger	= Logger.getLogger(Util.class);
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO		colaboradorDAO;
	@Autowired
	@Qualifier("HEstoqueDAO")
	private EstoqueDAO			estoqueDAO;
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	private ItemSolicitacaoDAO	itemSolicitacaoDAO;
	@Autowired
	@Qualifier("HHierarquiaDAO")
	private HierarquiaDAO		hierarquiaDAO;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO		solicitacaoDAO;

	public Util() {
		super();
	}

	public int calculaUltimoIndice(List lista, String start, String limit) {
		int ultIdx = (Integer.valueOf(start) + Integer.valueOf(limit));
		if (ultIdx > lista.size())
			ultIdx = lista.size();
		return ultIdx;
	}

	public java.sql.Date convDateParaSql(java.util.Date data) {
		java.sql.Date dataJDBC = new java.sql.Date(0);
		if (data != null) {
			dataJDBC = new java.sql.Date(data.getTime());
		}
		return dataJDBC;
	}

	public boolean normalizaCPF() {
		boolean retorno = true;
		try {
			colaboradorDAO.normalizaCPF();
		} catch (Exception e) {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Retorna a data atual em formato Timestamp, ou seja, formato de data e hora
	 * 
	 * @return
	 */
	public Timestamp dataAtual() {
		Date dataAtual = new Date();
		Timestamp t = new Timestamp(dataAtual.getTime());
		return t;
	}

	/**
	 * Verifica se existe estoque para o item selecionado, e na empresa selecionada
	 * 
	 * @param codEpi
	 * @param numEmp
	 * @return
	 * @throws Exception
	 */
	public boolean existeEstoque(Uniforme uniforme, Empresa empresa, ItemSolicitacao itemSolicitacao)
			throws Exception {
		Estoque estoque = estoqueDAO.loadById(new Estoque.Id(uniforme, empresa));
		int qtdEst = estoque.getQtdEst();
		int totalSolicitado = itemSolicitacaoDAO.getTotalSLItem(uniforme.getId(), empresa.getId());
		if ((qtdEst - totalSolicitado) >= itemSolicitacao.getQtdEnt()) {
			return true;
		} else
			return false;
	}

	/**
	 * Verifica se existe estoque para o item, independente de empresa
	 * 
	 * @param uniforme
	 * @param itemSolicitacao
	 * @return
	 * @throws Exception
	 */
	public boolean existeEstoque(Uniforme uniforme, ItemSolicitacao itemSolicitacao,
			boolean isInsert, boolean consideraPL) throws Exception {
		int qtdEst = estoqueDAO.getTotalEstoqueItem(uniforme);
		int totalSolicitado = 0;
		if (isInsert == true)
			totalSolicitado = itemSolicitacaoDAO.getTotalSLItem(uniforme.getId(), consideraPL);
		else
			totalSolicitado = itemSolicitacaoDAO.getTotalSLItem(uniforme.getId(), consideraPL)
					- itemSolicitacao.getQtdEnt();
		if ((qtdEst - totalSolicitado) >= itemSolicitacao.getQtdEnt()) {
			return true;
		} else {
			return false;
		}
	}

	public String geraLocalPai(int numLoc) {
		String locPai = "";
		Hierarquia hierarquia = new Hierarquia();
		hierarquia = hierarquiaDAO.getHierarquiaAtual(numLoc);
		Hierarquia locEmp = hierarquiaDAO.loadByCodLoc(hierarquia.getCodLoc().substring(0, 2));
		locPai = locEmp.getLocal().getNomLoc();
		Hierarquia locUni = hierarquiaDAO.loadByCodLoc(hierarquia.getCodLoc().substring(0, 6));
		locPai = locPai + " - " + locUni.getLocal().getNomLoc();
		Hierarquia locCli = hierarquiaDAO.loadByCodLoc(hierarquia.getCodLoc().substring(0, 11));
		locPai = locPai + " - " + locCli.getLocal().getNomLoc();
		Hierarquia locCid = hierarquiaDAO.loadByCodLoc(hierarquia.getCodLoc().substring(0, 16));
		locPai = locPai + " - " + locCid.getLocal().getNomLoc();
		Hierarquia locPos = hierarquiaDAO.loadByCodLoc(hierarquia.getCodLoc().substring(0, 21));
		locPai = locPai + " - " + locPos.getLocal().getNomLoc();
		return locPai;
	}

	public void geraRelatorio(Collection dados, String relatorioJasper, String fileName,
			Map parametros, String tipoArquivo) throws JRException, IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream sos = null;
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dados);
		InputStream reportStream = ServletActionContext.getServletContext().getResourceAsStream(
				"/paginas/reports/" + relatorioJasper);
		if (tipoArquivo.equals("pdf")) {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);  
			sos = response.getOutputStream();
			JasperPrint print = JasperFillManager.fillReport(reportStream, parametros, ds);
			JRPdfExporter pdf = new JRPdfExporter();
			pdf.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
			pdf.setParameter(JRExporterParameter.JASPER_PRINT, print);
			pdf.exportReport();
		} else if (tipoArquivo.equals("xls")) {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
			JasperPrint print = JasperFillManager.fillReport(reportStream, parametros, ds);
			JRXlsExporter xls = new JRXlsExporter();
			xls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Demitidos.xls");
			xls.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
			xls.setParameter(JRExporterParameter.JASPER_PRINT, print);
			xls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
					Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
			xls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
			xls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
			xls.exportReport();
		}
	}

	public void geraRelatorio(String relatorioJasper, String fileName, Map parametros,
			String tipoArquivo) throws JRException, IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream sos = null;
		InputStream reportStream = ServletActionContext.getServletContext().getResourceAsStream(
				"/paginas/reports/" + relatorioJasper);
		if (tipoArquivo.equals("pdf")) {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);
			sos = response.getOutputStream();
			JasperPrint print = JasperFillManager.fillReport(reportStream, parametros);
			JRPdfExporter pdf = new JRPdfExporter();
			pdf.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
			pdf.setParameter(JRExporterParameter.JASPER_PRINT, print);
			pdf.exportReport();
		} else if (tipoArquivo.equals("xls")) {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
			JasperPrint print = JasperFillManager.fillReport(reportStream, parametros);
			JRXlsExporter xls = new JRXlsExporter();
			xls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Demitidos.xls");
			xls.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
			xls.setParameter(JRExporterParameter.JASPER_PRINT, print);
			xls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
					Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
			xls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			xls.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
			xls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
			xls.exportReport();
		}
	}

	public void geraRelatorioArquivo(Collection dados, String relatorioJasper, String fileNamePdf,
			Map parametros) throws JRException {
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dados);
		try {
			JasperRunManager.runReportToPdfFile(relatorioJasper, fileNamePdf, parametros, ds);
		} catch (JRException e) {
			logger.error("Erro na montagem do relatório " + relatorioJasper + e.getMessage());
			throw e;
		}

	}

	/**public void geraRelatorioArquivo(Object dado, String relatorioJasper, String fileNamePdf,
			Map parametros) throws JRException {
		Collection col = new ArrayList();
		col.add(dado);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(col);
		try {
			JasperRunManager.runReportToPdfFile(relatorioJasper, fileNamePdf, parametros, ds);
		} catch (JRException e) {
			logger.error("Erro na montagem do relatório " + relatorioJasper + e.getMessage());
			throw e;
		}

	}**/

	/**public void geraRelatorioPdf(Collection dados, InputStream reportStream,
			HttpServletResponse res, String relatorioPdf, Map parametros) throws IOException {
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dados);
		res.setContentType("application/pdf");
		res.setHeader("Content-Disposition:", "attachment; filename=" + relatorioPdf);
		try {
			JasperRunManager.runReportToPdfStream(reportStream, res.getOutputStream(), parametros, ds);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}**/

	/**public void geraRelatorioPdfInBrowser(Collection dados, HttpServletResponse response,
			Map parametros, String relatorioJasper) throws JRException, IOException {
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dados);
		String path = ServletActionContext.getServletContext().getRealPath("/paginas/reports/");
		JasperReport relJasper = (JasperReport) JRLoader.loadObject(path + "\\" + relatorioJasper);
		HttpServletResponse res = ServletActionContext.getResponse();
		byte[] reportPdf = JasperRunManager.runReportToPdf(relJasper, parametros, ds);
		res.setContentType("application/pdf");
		res.setContentLength(reportPdf.length);
		res.setHeader("Content-Disposition:", "inline; filename=" + "NOMEDOARQUIVO.PDF");
		res.addHeader("Content-Disposition:", "inline; filename=" + "NOMEDOARQUIVO.PDF");
		ServletOutputStream ouputStream = res.getOutputStream();
		ouputStream.write(reportPdf, 0, reportPdf.length);
		ouputStream.flush();
		ouputStream.close();

	}**/

	public void mandaEmail(String host, String from, List<Usuario> to, String subject, String texto) {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties, null);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			for (Iterator iterator = to.iterator(); iterator.hasNext();) {
				Usuario usuario = (Usuario) iterator.next();
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(from));
			}
			message.setSubject(subject);
			message.setText(texto);
			Transport.send(message);
		} catch (AddressException e) {
			logger.error("Erro no endereço: " + e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			logger.error("Erro na mensagem: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Envia um e-mail para uma lista de destinatários, informado no parâmetro to - list
	 * 
	 * @param host
	 * @param from
	 * @param to
	 * @param subject
	 * @param texto
	 * @throws UnsupportedEncodingException
	 */
	public boolean mandaEmail(String from, List<Usuario> to, String subject, String texto,
			String fileName) {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.grupozanardo.com.br");
		properties.put("mail.smtp.user", "workflow@grupozanardo.com.br");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.transport.protocol", "smtp");

		Session session = Session.getDefaultInstance(properties, null);
		Message message = new MimeMessage(session);
		try {
			for (Iterator<Usuario> iterator = to.iterator(); iterator.hasNext();) {
				Usuario usuario = (Usuario) iterator.next();
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
			}
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(from));
			message.setSubject(subject);
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(texto);
			mbp1.setHeader("Content-Type", "text/html; charset=UTF-8");
			mbp1.addHeaderLine("MIME-Version: 1.0");
			mbp1.addHeaderLine("Content-Type: text/html; charadd=UTF-8");
			mbp1.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
			mbp1.addHeaderLine("Content-Disposition: inline");
			mbp1.setContentLanguage(new String[] { "pt-BR" });
			// cria a segunda parte da mensage
			MimeBodyPart mbp2 = new MimeBodyPart();

			// anexa o arquivo na mensagem
			if (!fileName.equals("")) {
				FileDataSource fds = new FileDataSource(fileName);
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
			}

			// cria a Multipart
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			if (!fileName.equals("")) {
				mp.addBodyPart(mbp2);
			}

			// adiciona a Multipart na mensagem
			message.setContent(mp, "text/html; charset=UTF-8");

			// configura a data: cabecalho
			message.setSentDate(new Date());

			// message.setText(texto);
			Transport tr = session.getTransport("smtp");
			tr.connect("smtp.grupozanardo.com.br", "workflow@grupozanardo.com.br", "swgs5634");
			message.saveChanges();
			tr.sendMessage(message, message.getAllRecipients());

			tr.close();
			return true;
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Recupera um objeto da sessão do usuário
	 * 
	 * @param nome
	 * @return
	 */
	public Object recuperaSessao(String nome) {
		Map sessionMap = ActionContext.getContext().getSession();
		Object object = sessionMap.get(nome);
		return object;
	}

	/**
	 * Grava um objeto na sessão do usuário
	 * 
	 * @param nome
	 * @param conteudo
	 */
	public void registraSessao(String nome, Object conteudo) {
		Map sessionMap = ActionContext.getContext().getSession();
		sessionMap.put(nome, conteudo);
	}

	/**
	 * Remove um objeto da sessão do usuário
	 * 
	 * @param nome
	 */
	public void removeDaSessao(String nome) {
		Map sessionMap = ActionContext.getContext().getSession();
		sessionMap.remove(nome);
	}

	public void verificaSituacaoSolicitacao(Solicitacao solicitacao) throws Exception {
		int qtdAberto = 0;
		int qtdFechado = 0;
		for (ItemSolicitacao item : solicitacao.getItensSolicitacao()) {
			if ((item.getSitItm().equals(Situacao.EAA)) || (item.getSitItm().equals(Situacao.EN)))
				qtdFechado++;
			else
				qtdAberto++;
		}
		if (qtdAberto > 0)
			solicitacao.setSituacao(bean.Solicitacao.Situacao.AB);
		else
			solicitacao.setSituacao(bean.Solicitacao.Situacao.FE);
		solicitacaoDAO.updateWithFlush(solicitacao);
	}

	public String loginSapiensSID(String ipSapiensSID) {
		try {
			/**
			 * BASE TESTE: http://192.168.1.222/sapiensweb/conector?SIS=CO&LOGIN=SID&BASE=TESTE&ACAO=EXESENHA&NOMUSU=agendador&SENUSU=agendador BASE
			 * OFICIAL http://192.168.1.222/sapiensweb/conector?SIS=CO&LOGIN=SID&BASE=OFICIAL&ACAO=EXESENHA&NOMUSU=agendador&SENUSU=agendador
			 **/
			
			
			String urlSID = "http://"+ipSapiensSID+":8080/sapiensweb/conector?SIS=CO&LOGIN=SID&ACAO=EXESENHA&NOMUSU=agendador&SENUSU=agendador";
			URL url = new URL(urlSID);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer sb = new StringBuffer();
			// gerando um arquivo com o conteúdo consumido.
			String linha;
			while ((linha = br.readLine()) != null) {
				sb.append(linha);
				// sb.append(linha + "\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String acaoSID(String urlSID) {
		try {
			URL url = new URL(urlSID);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer sb = new StringBuffer();
			// gerando um arquivo com o conteúdo consumido.
			String linha;
			while ((linha = br.readLine()) != null) {
				sb.append(linha);
				// sb.append(linha + "\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public void lePropriedades(String nomeArquivo) {
		Properties properties = new Properties();
		InputStream in = this.getClass().getResourceAsStream(nomeArquivo);
		try {
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ColaboradorDAO getColaboradorDAO() {
		return colaboradorDAO;
	}

	public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
		this.colaboradorDAO = colaboradorDAO;
	}

	public EstoqueDAO getEstoqueDAO() {
		return estoqueDAO;
	}

	public void setEstoqueDAO(EstoqueDAO estoqueDAO) {
		this.estoqueDAO = estoqueDAO;
	}

	public ItemSolicitacaoDAO getItemSolicitacaoDAO() {
		return itemSolicitacaoDAO;
	}

	public void setItemSolicitacaoDAO(ItemSolicitacaoDAO itemSolicitacaoDAO) {
		this.itemSolicitacaoDAO = itemSolicitacaoDAO;
	}

	public HierarquiaDAO getHierarquiaDAO() {
		return hierarquiaDAO;
	}

	public void setHierarquiaDAO(HierarquiaDAO hierarquiaDAO) {
		this.hierarquiaDAO = hierarquiaDAO;
	}

	public SolicitacaoDAO getSolicitacaoDAO() {
		return solicitacaoDAO;
	}

	public void setSolicitacaoDAO(SolicitacaoDAO solicitacaoDAO) {
		this.solicitacaoDAO = solicitacaoDAO;
	}
}