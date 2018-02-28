package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.ServiceFactory;

public class NotificaAdmissao extends TimerTask {
	static Logger	logger	= Logger.getLogger(NotificaAdmissao.class);
	String			path;

	public NotificaAdmissao(String path) {
		this.path = path;
	}

	public void run() {
		try {
			logger.info("########################### Iniciando notificação de admissões...");
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.notificaAdmissoes(path);
		} catch (Exception e) {
			logger.error("Erro tentanto notificar admissões. Causa: " + e.getCause());
		}
	}

}
