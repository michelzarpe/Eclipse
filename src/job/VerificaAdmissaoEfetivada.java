package job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

public class VerificaAdmissaoEfetivada extends TimerTask {
	static Logger logger = Logger.getLogger(VerificaAdmissaoEfetivada.class);
	String path;

	public VerificaAdmissaoEfetivada(String path) {
		this.path = path;
	}

	@Override
	public void run() {
		logger.info("Iniciando verificação de admissões efetivadas no SM.");
		try {
			//Sincronizacao.verificaAdmissaoEfetivada(path);
			logger.info("Admissões efetivadas com sucesso.");
		} catch (Exception e) {
			logger.error("Erro na verificação de admissões efetivadas. Causa: " + e.getCause());
		}
	}
}
