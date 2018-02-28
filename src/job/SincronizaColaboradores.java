package job;

import java.sql.BatchUpdateException;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.Importer;
import util.ServiceFactory;

public class SincronizaColaboradores extends TimerTask {

	static Logger	logger	= Logger.getLogger(SincronizaColaboradores.class);

	@Override
	public void run() {
		try {
			logger.info("Iniciando sincronização de colaboradores...");
			Importer importer = (Importer) ServiceFactory.getBean("Importer");
			importer.atualizaColaboradores();
		} catch (BatchUpdateException e) {
			logger.error("Erro na sincronização de colaboradores. Causa: " + e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("Erro na sincronização de colaboradores. Causa: " + e.getLocalizedMessage());
		}
	}

}
