package dao.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.ItemSolicitacao;
import bean.Solicitacao;
import bean.Uniforme;
import bean.Usuario;
import bean.ItemSolicitacao.Situacao;
import bean.Usuario.TipoUsuario;
import dao.ItemSolicitacaoDAO;

@Repository
@Transactional
public class HItemSolicitacaoDAO extends HGenericDAO<ItemSolicitacao> implements ItemSolicitacaoDAO {
	static Logger logger = Logger.getLogger(HItemSolicitacaoDAO.class);
	
	public HItemSolicitacaoDAO(){
		super(ItemSolicitacao.class);
	}

	public HItemSolicitacaoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, ItemSolicitacao.class);
	}

	@Override
	public Date getDataPermitidaTroca(int idColaborador, Uniforme uniforme, Date dataConsulta) {
		Query query = getSession()
				.createQuery("select max(i.datEnv) as ultimaData from ItemSolicitacao i "
						+ "where i.id.solicitacao.colaborador.id=:col "
						+ "and i.id.uniforme.tipoEPI.id=:tipo and i.datEnv<=:dataConsulta "
						+ "and ((i.datDev is null) or (i.datDev='1900-12-31'))");
		query.setInteger("col", idColaborador);
		query.setInteger("tipo", uniforme.getTipoEPI().getId());
		query.setDate("dataConsulta", dataConsulta);
		query.setCacheable(true);
		Date dataTroca = (Date) query.uniqueResult();
		Date retorno = null;
		if (dataTroca != null) {
			retorno = dataTroca;
		}
		return retorno;
	}

	/**
	 * Retorna o saldo a solicitar do item para o colaborador. Pega a quantidade
	 * máxima e diminui o último pedido
	 */
	public int getSaldo(int idCol, Uniforme uniforme, Date dataFinal, Date dataInicial)
			throws Exception {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.createAlias("solicitacao", "sol");
		criteria.add(Restrictions.eq("sol.colaborador.id", idCol));
		criteria.createAlias("uniforme", "uni");
		criteria.add(Restrictions.eq("uni.tipoEPI.id", uniforme.getTipoEPI().getId()));
		criteria.add(Restrictions.between("datEnv", dataInicial, dataFinal));
		criteria.setProjection(Projections.sum("qtdEnt"));
		Integer qtdTot = (Integer) criteria.uniqueResult();
		int saldo = 0;
		if (qtdTot != null) {
			saldo = (uniforme.getQtdMax() - qtdTot);
			if (saldo < 0)
				saldo = 0;
		} else
			// se não encontrou nada, então pode pedir a quantidade máxima, pois
			// quer dizer que não teve solicitação desse tipo de item nos
			// últimos dias de validade
			saldo = uniforme.getQtdMax();
		return saldo;
	}

	/**
	 * Retorna a soma da quantidade de itens LB, SL E SAA que ainda não foram
	 * exportados para o SM, independente de empresa
	 */
	public int getTotalSLItem(int codEpi, boolean consideraPL) {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.LB);
		situacoes.add(Situacao.SL);
		situacoes.add(Situacao.SAA);
		if (consideraPL) {
			situacoes.add(Situacao.PL);
		}
		criteria.add(Restrictions.eq("id.uniforme.id", codEpi));
		criteria.add(Restrictions.in("sitItm", situacoes));
		criteria.setProjection(Projections.sum("qtdEnt"));
		int total = 0;
		if (criteria.uniqueResult() != null)
			total = (Integer) criteria.uniqueResult();
		return total;
	}

	/**
	 * Retorna a soma da quantidade de itens LB, SL E SAA que ainda não foram
	 * exportados para o SM
	 */
	public int getTotalSLItem(int codEpi, int codEmp) {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.LB);
		situacoes.add(Situacao.SL);
		situacoes.add(Situacao.SAA);
		criteria.createCriteria("solicitacao").createCriteria("colaborador").createCriteria(
				"empresa").add(Restrictions.eq("id", codEmp));
		criteria.add(Restrictions.eq("id.uniforme.id", codEpi));
		criteria.add(Restrictions.in("sitItm", situacoes));
		criteria.setProjection(Projections.sum("qtdEnt"));
		int total = 0;
		if (criteria.uniqueResult() != null)
			total = (Integer) criteria.uniqueResult();
		return total;
	}

	@Override
	@Deprecated
	public List<ItemSolicitacao> getTransferencias() throws Exception {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.LB);
		situacoes.add(Situacao.SL);
		situacoes.add(Situacao.SAA);
		criteria.add(Restrictions.in("sitItm", situacoes));
		criteria.createCriteria("solicitacao").createCriteria("colaborador").createCriteria(
				"empresa", "emp").addOrder(Order.asc("emp.id"));
		criteria.createAlias("uniforme", "uni");
		criteria.addOrder(Order.asc("uni.id"));
		return criteria.list();
	}

	/**
	 * Verifica se o item que está sendo solicitado está dentro do prazo de
	 * troca.
	 */
	@Deprecated
	public boolean isForaPrazo(int idColaborador, Uniforme uniforme) {
		Query query = getSession()
				.createQuery("select max(i.id.solicitacao.datEnt) from ItemSolicitacao i where i.id.solicitacao.colaborador.id=:col and i.id.uniforme.tipoEPI.id=:tipo");
		query.setInteger("col", idColaborador);
		query.setInteger("tipo", uniforme.getTipoEPI().getId());
		Date dataTroca = (Date) query.uniqueResult();
		Calendar calendar = GregorianCalendar.getInstance();
		boolean retorno = false;
		if (dataTroca != null) {
			calendar.setTime(dataTroca);
			calendar.add(Calendar.DATE, uniforme.getDiaVal());
			dataTroca = calendar.getTime();
			Date dataAtual = new Date();
			if (dataTroca.compareTo(dataAtual) == 1)
				retorno = true;
		}
		return retorno;

	}

	/**
	 * Retorna lista de itens com as situações informadas que sejam do centro de
	 * custos do usuário
	 */
	public List<ItemSolicitacao> listByPrevDate(List<Situacao> situacoes, int qtdDias,
			List<Order> ordens, Usuario usuario) {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.add(Restrictions.in("sitItm", situacoes));
		List<ItemSolicitacao> itens = new ArrayList<ItemSolicitacao>();
		for (Order order : ordens) {
			criteria.addOrder(order);
		}
		if (usuario.getCentrosPermitidos().size() > 0) {
			criteria.createCriteria("solicitacao").createCriteria("colaborador").add(
					Restrictions.in("centro", usuario.getCentrosPermitidos()));
			itens = criteria.list();
		}
		return itens;
	}

	@Override
	public List<ItemSolicitacao> listBySituacao(List<Situacao> situacoes, List<Integer> motivos,
			boolean onlyImpl) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.add(Restrictions.in("sitItm", situacoes));
		criteria.createAlias("solicitacao", "sol");
		criteria.add(Restrictions.gt("sol.datEnt", cal.getTime()));
		criteria.createAlias("sol.colaborador", "col");
		criteria.add(Restrictions.in("sol.motivo.id", motivos));
		if (onlyImpl) {
			criteria.add(Restrictions.eq("col.numCad", new Long(0)));
		}
		criteria.addOrder(Order.asc("col.empresa.id"));
		criteria.addOrder(Order.asc("col.centro.id"));
		return criteria.list();
	}

	/**
	 * Retorna lista de itens liberados no período informado
	 */
	public List<Solicitacao> listLiberados(Date datIni, Date datFin, Usuario usuario)
			throws Exception {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(datIni);
		calendar.add(Calendar.DATE, -1);

		Calendar calendarFin = Calendar.getInstance();
		calendarFin.setTime(datFin);
		calendarFin.add(Calendar.DATE, 1);
		criteria.add(Restrictions.between("datLib", calendar.getTime(), calendarFin.getTime()));
		if (usuario.getTipUsu().equals(TipoUsuario.OUN)) {
			criteria.createAlias("solicitacao.solicitante", "sol");
			criteria.add((Restrictions.eq("sol.id", (Integer) usuario.getId())));
		}
		return criteria.list();
	}

	/**
	 * Retorna lista de itens não atendidos por falta de estoque, ordenado por
	 * uniforme
	 */
	public List<ItemSolicitacao> listNaoAtendidos() throws Exception {
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.NA);
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.createCriteria("solicitacao").createCriteria("colaborador").createCriteria(
				"empresa").addOrder(Order.asc("id"));
		criteria.createCriteria("uniforme").addOrder(Order.asc("id"));
		criteria.add(Restrictions.in("sitItm", situacoes));
		return criteria.list();
	}

	@Override
	public List<Solicitacao> listNegados(Date datIni, Date datFin, Usuario usuario)
			throws Exception {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.add(Restrictions.between("datNeg", datIni, datFin));
		if (usuario.getTipUsu().equals(TipoUsuario.OUN)) {
			criteria.createAlias("solicitacao.solicitante", "sol");
			criteria.add((Restrictions.eq("sol.id", (Integer) usuario.getId())));
		}
		return criteria.list();
	}

	@Override
	public List<ItemSolicitacao> listPendentesAdmissao() throws Exception {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.createAlias("solicitacao.solicitante", "sol");
		criteria.addOrder(Order.asc("sol.centro.id"));
		criteria.add(Restrictions.eq("sitItm", Situacao.SAA)).createCriteria("solicitacao")
				.createCriteria("colaborador").add(Restrictions.eq("afastamento.id", 1000))
				.addOrder(Order.asc("empresa.id"));
		return criteria.list();
	}

	public List<ItemSolicitacao> listPendentesEnvio(int codCcu) throws Exception {
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.PL);
		situacoes.add(Situacao.NA);
		situacoes.add(Situacao.SAA);
		situacoes.add(Situacao.SL);
		situacoes.add(Situacao.LB);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = (Date) format.parse("2009-01-12");
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.createAlias("solicitacao", "sol").createAlias("sol.solicitante", "soli");
		if (codCcu > 0) {
			criteria.add(Restrictions.eq("soli.centro.id", codCcu));
		}
		criteria.addOrder(Order.asc("soli.centro.id"));
		criteria.addOrder(Order.asc("soli.empresa.id"));
		criteria.add(Restrictions.ge("sol.datEnt", new Date(startDate.getTime())));
		criteria.add(Restrictions.in("sitItm", situacoes));
		return criteria.list();
	}

	@Override
	public ItemSolicitacao loadByChaveVetorh(int numEmp, int numCad, int codEpi, Date datEnt) {
		Query query = getSession().createQuery("from ItemSolicitacao where "
				+ "id.solicitacao.colaborador.empresa.id=:emp "
				+ "and id.solicitacao.colaborador.numCad=:cad "
				+ "and id.uniforme.id=:uni and id.solicitacao.datEnt=:ent");
		query.setInteger("emp", numEmp);
		query.setInteger("cad", numCad);
		query.setInteger("uni", codEpi);
		query.setDate("ent", datEnt);
		return (ItemSolicitacao) query.uniqueResult();
	}

	@Override
	public ItemSolicitacao loadById(int idSol, int idUni) {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.add(Restrictions.eq("id.solicitacao.id", idSol));
		criteria.add(Restrictions.eq("id.uniforme.id", idUni));
		return (ItemSolicitacao) criteria.uniqueResult();
	}

}
