package job;

import javax.servlet.http.HttpServlet;

import util.ServiceFactory;

public class SincronizacaoServlet extends HttpServlet {
	private static final long	serialVersionUID	= 1L;
	private int						idSol;

	public int getIdSol() {
		return idSol;
	}

	public void setIdSol(int idSol) {
		this.idSol = idSol;
	}

	public boolean notificaAdmissao() {
		String path = getServletContext().getRealPath("/paginas/reports/");
		try {
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.notificaAdmissoes(path);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean notificaSolicitacaoAdm() {
		String path = getServletContext().getRealPath("/paginas/reports/");
		try {
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			sincronizacao.notificaSolicitacaoNovosColaboradores(path);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
