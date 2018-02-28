package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.BackupMysql;

public class GeraBackup extends TimerTask {
	static Logger	logger	= Logger.getLogger(GeraBackup.class);

	@Override
	public void run() {
		try {
			logger.info("Iniciando execução de backup em arquivo");
			BackupMysql.doBackup("root", "grpzjba", "gzOper");
			BackupMysql.doBackup("root", "grpzjba", "operacional");
			BackupMysql.doBackup("root", "grpzjba", "webdoc");
		} catch (Exception e) {
			logger.error("Erro na geração do backup. Causa: " + e.getLocalizedMessage());
		}
		logger.info("Geração de backup efetuada com sucesso.");
	}
}
