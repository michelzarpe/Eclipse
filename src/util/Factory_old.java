package util;

import org.hibernate.SessionFactory;

import dao.hibernate.HAcessoDAO;
import dao.hibernate.HAdiantamentoViagemDAO;
import dao.hibernate.HAfastamentoDAO;
import dao.hibernate.HBancoDAO;
import dao.hibernate.HCargoDAO;
import dao.hibernate.HCentroDAO;
import dao.hibernate.HCidadeDAO;
import dao.hibernate.HClienteDAO;
import dao.hibernate.HCoberturaDAO;
import dao.hibernate.HColaboradorDAO;
import dao.hibernate.HCondicaoPagamentoDAO;
import dao.hibernate.HDespesaDAO;
import dao.hibernate.HDespesaViagemDAO;
import dao.hibernate.HDocumentoDAO;
import dao.hibernate.HEmpresaDAO;
import dao.hibernate.HEscalaDAO;
import dao.hibernate.HEscalaRondaDAO;
import dao.hibernate.HEstoqueDAO;
import dao.hibernate.HFornecedorDAO;
import dao.hibernate.HGrauInstrucaoDAO;
import dao.hibernate.HHierarquiaDAO;
import dao.hibernate.HItemSolicitacaoDAO;
import dao.hibernate.HLocalDAO;
import dao.hibernate.HMotivoDAO;
import dao.hibernate.HMotivoOcorrenciaDAO;
import dao.hibernate.HMotivoViagemDAO;
import dao.hibernate.HOcorrenciaDAO;
import dao.hibernate.HProdutoDAO;
import dao.hibernate.HRequisicaoEstoqueDAO;
import dao.hibernate.HSolicitacaoCompraDAO;
import dao.hibernate.HSolicitacaoDAO;
import dao.hibernate.HSubTipoMotivoDAO;
import dao.hibernate.HTipoEPIDAO;
import dao.hibernate.HUniformeDAO;
import dao.hibernate.HUsuarioDAO;
import dao.hibernate.HViagemDAO;

public class Factory_old {
	private static SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
		
	public static HAcessoDAO criaAcessoDAO() {
		HAcessoDAO hAcessoDAO = null;
		//hAcessoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hAcessoDAO;
	}

	public static HCentroDAO criaCentroDAO() {
		HCentroDAO hCentroDAO = new HCentroDAO(sessionFactory);
		//hCentroDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hCentroDAO;
	}

	public static HEmpresaDAO criaEmpresaDAO() {
		HEmpresaDAO hEmpresaDAO = new HEmpresaDAO(sessionFactory);
		//hEmpresaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hEmpresaDAO;
	}

	public static HUsuarioDAO criaUsuarioDAO() {
		HUsuarioDAO hUsuarioDAO = null;
		//UsuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hUsuarioDAO;
	}

	public static HEscalaRondaDAO criaEscalaRondaDAO() {
		HEscalaRondaDAO hEscalaRondaDAO = new HEscalaRondaDAO(sessionFactory);
		//hEscalaRondaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hEscalaRondaDAO;
	}

	public static HAfastamentoDAO criaAfastamentoDAO() {
		HAfastamentoDAO hAfastamentoDAO = new HAfastamentoDAO(sessionFactory);
		//hAfastamentoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hAfastamentoDAO;
	}

	public static HCargoDAO criaCargoDAO() {
		HCargoDAO hCargoDAO = new HCargoDAO(sessionFactory);
		//hCargoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hCargoDAO;
	}

	public static HUniformeDAO criaUniformeDAO() {
		HUniformeDAO hUniformeDAO = new HUniformeDAO(sessionFactory);
		//hUniformeDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hUniformeDAO;
	}

	public static HTipoEPIDAO criaTipoEPIDAO() {
		HTipoEPIDAO hTipoEPIDAO = new HTipoEPIDAO(sessionFactory);
		//hTipoEPIDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hTipoEPIDAO;
	}

	public static HClienteDAO criaClienteDAO() {
		HClienteDAO hClienteDAO = new HClienteDAO(sessionFactory);
		//hClienteDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hClienteDAO;
	}

	public static HMotivoDAO criaMotivoDAO() {
		HMotivoDAO hMotivoDAO = new HMotivoDAO(sessionFactory);
		//hMotivoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hMotivoDAO;
	}

	public static HLocalDAO criaLocalDAO() {
		HLocalDAO hLocalDAO = new HLocalDAO(sessionFactory);
		//hLocalDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hLocalDAO;
	}

	public static HHierarquiaDAO criaHierarquiaDAO() {
		HHierarquiaDAO hHierarquiaDAO = new HHierarquiaDAO(sessionFactory);
		//hHierarquiaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hHierarquiaDAO;
	}

	public static HColaboradorDAO criaColaboradorDAO() {
		HColaboradorDAO hColaboradorDAO = new HColaboradorDAO(sessionFactory);
		//hColaboradorDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hColaboradorDAO;
	}

	public static HSolicitacaoDAO criaSolicitacaoDAO() {
		HSolicitacaoDAO hSolicitacaoDAO = new HSolicitacaoDAO(sessionFactory);
		//hSolicitacaoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hSolicitacaoDAO;
	}

	public static HItemSolicitacaoDAO criaItemSolicitacaoDAO() {
		HItemSolicitacaoDAO hItemSolicitacaoDAO = new HItemSolicitacaoDAO(sessionFactory);
		//hItemSolicitacaoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hItemSolicitacaoDAO;
	}

	public static HBancoDAO criaBancoDAO() {
		HBancoDAO hBancoDAO = new HBancoDAO(sessionFactory);
		//hBancoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hBancoDAO;
	}

	public static HCidadeDAO criaCidadeDAO() {
		HCidadeDAO hCidadeDAO = new HCidadeDAO(sessionFactory);
		//hCidadeDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hCidadeDAO;
	}

	public static HGrauInstrucaoDAO criaGrauInstrucaoDAO() {
		HGrauInstrucaoDAO hGrauInstrucaoDAO = new HGrauInstrucaoDAO(sessionFactory);
		//hGrauInstrucaoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hGrauInstrucaoDAO;
	}

	public static HEscalaDAO criaEscalaDAO() {
		HEscalaDAO hEscalaDAO = new HEscalaDAO(sessionFactory);
		//hEscalaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hEscalaDAO;
	}

	public static HEstoqueDAO criaEstoqueDAO() {
		HEstoqueDAO hEstoqueDAO = new HEstoqueDAO(sessionFactory);
		//hEstoqueDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hEstoqueDAO;
	}

	public static HOcorrenciaDAO criaOcorrenciaDAO() {
		HOcorrenciaDAO hOcorrenciaDAO = new HOcorrenciaDAO(sessionFactory);
		//hOcorrenciaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hOcorrenciaDAO;
	}

	public static HCoberturaDAO criaCoberturaDAO() {
		HCoberturaDAO hCoberturaDAO = new HCoberturaDAO(sessionFactory);
		//hCoberturaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hCoberturaDAO;
	}

	public static HMotivoOcorrenciaDAO criaMotivoOcorrenciaDAO() {
		HMotivoOcorrenciaDAO hMotivoOcorrenciaDAO = new HMotivoOcorrenciaDAO(sessionFactory);
		//hMotivoOcorrenciaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hMotivoOcorrenciaDAO;
	}

	public static HSubTipoMotivoDAO criaSubTipoMotivoDAO() {
		HSubTipoMotivoDAO hSubTipoMotivoDAO = new HSubTipoMotivoDAO(sessionFactory);
		//hSubTipoMotivoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hSubTipoMotivoDAO;
	}

	public static HViagemDAO criaViagemDAO() {
		HViagemDAO hViagemDAO = new HViagemDAO(sessionFactory);
		//hViagemDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hViagemDAO;
	}

	public static HAdiantamentoViagemDAO criaAdiantamentoViagemDAO() {
		HAdiantamentoViagemDAO hAdiantamentoViagemDAO = new HAdiantamentoViagemDAO(sessionFactory);
		//hAdiantamentoViagemDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hAdiantamentoViagemDAO;
	}

	public static HDespesaViagemDAO criaDespesaViagemDAO() {
		HDespesaViagemDAO hDespesaViagemDAO = new HDespesaViagemDAO(sessionFactory);
		//hDespesaViagemDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hDespesaViagemDAO;
	}

	public static HMotivoViagemDAO criaMotivoViagemDAO() {
		HMotivoViagemDAO hMotivoViagemDAO = new HMotivoViagemDAO(sessionFactory);
		//hMotivoViagemDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hMotivoViagemDAO;
	}

	public static HDespesaDAO criaDespesaDAO() {
		HDespesaDAO hDespesaDAO = new HDespesaDAO(sessionFactory);
		//hDespesaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hDespesaDAO;
	}

	public static HProdutoDAO criaProdutoDAO() {
		HProdutoDAO hProdutoDAO = new HProdutoDAO(sessionFactory);
		//hProdutoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hProdutoDAO;
	}

	public static HRequisicaoEstoqueDAO criaRequisicaoEstoqueDAO() {
		HRequisicaoEstoqueDAO hrEstoqueDAO = new HRequisicaoEstoqueDAO(sessionFactory);
		//hrEstoqueDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hrEstoqueDAO;
	}

	public static HSolicitacaoCompraDAO criaSolicitacaoCompraDAO() {
		HSolicitacaoCompraDAO hCompraDAO = new HSolicitacaoCompraDAO(sessionFactory);
		//hCompraDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hCompraDAO;
	}

	public static HFornecedorDAO criaFornecedorDAO() {
		HFornecedorDAO hFornecedorDAO = new HFornecedorDAO(sessionFactory);
		//hFornecedorDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hFornecedorDAO;
	}

	public static HCondicaoPagamentoDAO criaCondicaoPagamentoDAO() {
		HCondicaoPagamentoDAO hCondicaoPagamentoDAO = new HCondicaoPagamentoDAO(sessionFactory);
		//hCondicaoPagamentoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hCondicaoPagamentoDAO;
	}

	public static HDocumentoDAO criaDocumentoDAO() {
		HDocumentoDAO hDocumentoDAO = new HDocumentoDAO(sessionFactory);
		//hDocumentoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return hDocumentoDAO;
	}
}
