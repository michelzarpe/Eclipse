package job;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import util.ServiceFactory;
import bean.ItemSolicitacao;
import dao.ItemSolicitacaoDAO;

public class GeraRelatorioPendencias extends TimerTask {
	static Logger	logger	= Logger.getLogger(GeraRelatorioPendencias.class);
	String			path;

	public GeraRelatorioPendencias(String path) {
		this.path = path;
	}

	@Override
	public void run() {
		try {
			logger.info("Iniciando exportação de solicitações para SM");
			Sincronizacao sincronizacao = (Sincronizacao) ServiceFactory.getBean("Sincronizacao");
			ItemSolicitacaoDAO itemSolicitacaoDAO = (ItemSolicitacaoDAO) ServiceFactory
					.getBean("HItemSolicitacaoDAO");
			try {
				List<ItemSolicitacao> itens = itemSolicitacaoDAO.listPendentesEnvio(0);
				sincronizacao.geraRelatorioPendencias(itens, path);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Exportação de solicitações finalizada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
