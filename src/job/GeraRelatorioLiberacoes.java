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
			logger.info("########################### Iniciando gera��o do relat�rio de libera��es");
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.geraRelatorioLiberacoes(path, true);
		} catch (Exception e) {
			logger.error("Erro na gera��o do relat�rio de libera��es. Causa: "
					+ e.getLocalizedMessage());
		}
		logger.info("Gera��o de relat�rio de libera��es efetuada com sucesso.");
	}

}
