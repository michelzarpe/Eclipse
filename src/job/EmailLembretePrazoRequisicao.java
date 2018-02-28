package job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.PropertiesLoader;
import util.ServiceFactory;
import util.Util;
import bean.Usuario;
import dao.UsuarioDAO;

public class EmailLembretePrazoRequisicao extends TimerTask {
	static Logger	logger	= Logger.getLogger(EmailLembretePrazoRequisicao.class);
	String			path;

	public EmailLembretePrazoRequisicao(String path) {
		this.path = path;
	}

	public void run() {
		try {
			PropertiesLoader propertiesLoader = new PropertiesLoader(path);
			String valProp = propertiesLoader.getValor("data_fechamento_periodo_requisicao");
			int datReq = Integer.valueOf(valProp);
			if (datReq > 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				if ((cal.get(Calendar.DAY_OF_MONTH) >= (datReq-2)) && (cal.get(Calendar.DAY_OF_MONTH) <= datReq)) {
					List<Usuario> usuarios = new ArrayList<Usuario>();
					UsuarioDAO usuarioDAO = (UsuarioDAO) ServiceFactory.getBean("HUsuarioDAO");
					Util util = (Util) ServiceFactory.getBean("Util");
					usuarios = usuarioDAO.listUsuariosAtivos();
					String texto = "LEMBRETE: \n ENCERRA-SE DIA "+valProp+" O PRAZO PARA REQUISICAO DE MATERIAIS DE EXPEDIENTE, LIMPEZA E INSTALACAO.";
					texto += "CASO O DIA "+valProp+" SEJA UM FINAL DE SEMANA, CONSIDERAR O ULTIMO DIA PARA REQUISICOES COMO ULTIMO DIA UTIL ANTERIOR A ESTA DATA, ";
					texto += "POIS APOS A DATA DO DIA "+valProp+" O SISTEMA IRA BLOQUEAR QUALQUER REQUISICAO.";
					texto += "[Este e um email automatico gerado pelo sistema webOper 2.0. Nao responda.]\n";
					for (Usuario usuario : usuarios) {
						List<Usuario> usu = new ArrayList<Usuario>();
						usu.add(usuario);
						util.mandaEmail("weboper@grupozanardo.com.br", usu, "[webOper] "
								+ "LEMBRETE PRAZO REQUISIÇÕES", texto, path + "//TextoLembrete.txt");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Erro tentanto notificar admissï¿½es. Causa: " + e.getCause());
		}
	}
}
