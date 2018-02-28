package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.ServiceFactory;

public class GeraRelatorioNaoAtendidos extends TimerTask {
	static Logger				logger	= Logger.getLogger(GeraRelatorioNaoAtendidos.class);
	String						path;

	public GeraRelatorioNaoAtendidos(String path) {
		this.path = path;
	}

	@Override
	public void run() {
		try {
			logger.info("Iniciando geração do relatório de falta de estoque");
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.geraRelatorioNaoAtendidos(path);
		} catch (Exception e) {
			logger.error("Erro na geração do relatório de falta de estoque. Causa: "
					+ e.getLocalizedMessage());
		}
		logger.info("Geração de relatório de falta de estoque efetuada com sucesso.");
	}

	
}
