package test;

import job.SincronizacaoServlet;
import junit.framework.TestCase;

import org.apache.log4j.Logger;


public class NotificaAdmissaoTest extends TestCase {
	static Logger logger = Logger.getLogger(NotificaAdmissaoTest.class);

	@Override
	protected void setUp() throws Exception {
		System.out.println("Iniciando...");
	}

	protected void tearDown() throws Exception {
		System.out.println("Finalizando...");
	}
	
	public void testNotificaAdmissoes() throws Exception {
		logger.info("Iniciando verifica��o de admiss�es efetivadas no SM.");
		SincronizacaoServlet sincronizacaoAction = new SincronizacaoServlet();
		sincronizacaoAction.notificaAdmissao();	
		logger.info("Admiss�es efetivadas com sucesso.");
	}
}
