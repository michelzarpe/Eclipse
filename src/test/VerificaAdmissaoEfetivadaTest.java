package test;

import job.Sincronizacao;
import job.SincronizacaoServlet;
import junit.framework.Test;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class VerificaAdmissaoEfetivadaTest extends TestCase implements Test {
	static Logger logger = Logger.getLogger(VerificaAdmissaoEfetivadaTest.class);
	@Autowired
	private Sincronizacao sincronizacao;
	
	public VerificaAdmissaoEfetivadaTest() {
		
	}
	
	public void testAdmissaoEfetivada() throws Exception {
		logger.info("Iniciando verificação de admissões efetivadas no SM.");
		sincronizacao.verificaAdmissaoEfetivada(null);
		logger.info("Admissões efetivadas com sucesso.");
	}
	
	public Sincronizacao getSincronizacao() {
		return sincronizacao;
	}

	public void setSincronizacao(Sincronizacao sincronizacao) {
		this.sincronizacao = sincronizacao;
	}

	public void testNotificaAdmissoes() throws Exception {
		logger.info("Iniciando verificação de admissões efetivadas no SM.");
		SincronizacaoServlet sincronizacaoAction = new SincronizacaoServlet();
		sincronizacaoAction.notificaAdmissao();	
		logger.info("Admissões efetivadas com sucesso.");
	}
	
	@Override
	protected void setUp() throws Exception {
		System.out.println("Iniciando...");
	}
	
	protected void tearDown() throws Exception {
		System.out.println("Finalizando...");
	}
}
