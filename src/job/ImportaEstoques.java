package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.Importer;
import util.ServiceFactory;

public class ImportaEstoques extends TimerTask {
	static Logger	logger	= Logger.getLogger(ImportaEstoques.class);

	@Override
	public void run() {
		try {
			Importer importer = (Importer) ServiceFactory.getBean("Importer");
			importer.importaEstoques();
		} catch (Exception e) {
			logger.error("Erro na atualização de estoques: " + e.getCause());
		}
	}

}
