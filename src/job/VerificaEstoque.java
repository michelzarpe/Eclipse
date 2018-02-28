package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.ServiceFactory;

public class VerificaEstoque extends TimerTask {

	static Logger	logger	= Logger.getLogger(VerificaEstoque.class);

	@Override
	public void run() {
		logger.info("########################### Iniciando verificação de estoque");
		Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
		try {
			sincronizacao.verificaEstoque();
			logger.info("Verificação de estoque finalizada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
