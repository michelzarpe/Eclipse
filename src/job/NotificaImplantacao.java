package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.ServiceFactory;

public class NotificaImplantacao extends TimerTask {
	static Logger	logger	= Logger.getLogger(NotificaImplantacao.class);
	String			path;

	public NotificaImplantacao(String path) {
		this.path = path;
	}

	public void run() {
		try {
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.notificaSolicitacaoNovosColaboradores(path);
		} catch (Exception e) {
			logger.error("Erro na notificação de solicitações de implantação. Causa: " + e.getCause());
		}
	}

}
