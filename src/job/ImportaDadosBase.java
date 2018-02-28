package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.Importer;
import util.ServiceFactory;

public class ImportaDadosBase extends TimerTask {
	static Logger	logger	= Logger.getLogger(ImportaDadosBase.class);

	@Override
	public void run() {
		Importer importer = (Importer) ServiceFactory.getBean("Importer");
		try {
			logger.info("Iniciando importação de empresas...");
			importer.importaEmpresas();
		} catch (Exception e) {
			logger.error("Erro importando empresas " + e.getMessage());
		}
		try {
			logger.info("Iniciando importação de centros...");
			importer.importaCentros();
		} catch (Exception e) {
			logger.error("Erro importando centros " + e.getMessage());
		}

		try {
			logger.info("Iniciando importação de afastamentos...");
			importer.importaAfastamentos();
		} catch (Exception e) {
			logger.error("Erro importando afastamentos " + e.getMessage());
		}

		try {
			logger.info("Iniciando importação de grau de instrução...");
			importer.importaGrauInstrucao();
		} catch (Exception e) {
			logger.error("Erro importando graus de instrução " + e.getMessage());
		}
	}

}
