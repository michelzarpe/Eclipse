package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.ServiceFactory;

public class GeraRelatorioLiberacoes extends TimerTask {
	static Logger	logger	= Logger.getLogger(GeraRelatorioLiberacoes.class);
	String			path;

	public GeraRelatorioLiberacoes(String path) {
		this.path = path;
	}

	@Override
	public void run() {
		try {
			logger.info("########################### Iniciando geração do relatório de liberações");
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.geraRelatorioLiberacoes(path, true);
		} catch (Exception e) {
			logger.error("Erro na geração do relatório de liberações. Causa: "
					+ e.getLocalizedMessage());
		}
		logger.info("Geração de relatório de liberações efetuada com sucesso.");
	}

}
