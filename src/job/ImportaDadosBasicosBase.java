package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.Importer;
import util.ServiceFactory;

public class ImportaDadosBasicosBase extends TimerTask {
	static Logger	logger	= Logger.getLogger(ImportaDadosBasicosBase.class);

	@Override
	public void run() {
		Importer importer = (Importer) ServiceFactory.getBean("Importer");
		try {
			logger.info("Iniciando importa��o de tipos...");
			importer.importaTipos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			logger.info("Iniciando importa��o de produtos...");
			importer.importaProdutos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			logger.info("Iniciando importa��o de uniformes...");
			importer.importaUniformes();
		} catch (Exception e) {
		}

		try {
			logger.info("Iniciando importa��o de estoques...");
			importer.importaEstoques();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
