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
			logger.info("Iniciando gera��o do relat�rio de falta de estoque");
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.geraRelatorioNaoAtendidos(path);
		} catch (Exception e) {
			logger.error("Erro na gera��o do relat�rio de falta de estoque. Causa: "
					+ e.getLocalizedMessage());
		}
		logger.info("Gera��o de relat�rio de falta de estoque efetuada com sucesso.");
	}

	
}
