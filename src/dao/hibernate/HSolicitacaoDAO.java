package dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Colaborador;
import bean.ItemSolicitacao;
import bean.Solicitacao;
import bean.Usuario;
import bean.ItemSolicitacao.Situacao;
import dao.SolicitacaoDAO;

@Repository
@Transactional
public class HSolicitacaoDAO extends HGenericDAO<Solicitacao> implements SolicitacaoDAO {
	
	public HSolicitacaoDAO(){
		super(Solicitacao.class);
	}
	

	public HSolicitacaoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Solicitacao.class);
	}

	@Override
	public List<Solicitacao> busca(Map<String, Object> parametros) {
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.setFetchMode("itensSolicitacao", FetchMode.SELECT);
		criteria.createAlias("colaborador", "col");
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;
		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			if (campo.equals("colaborador.nomFun"))
				criterion = Restrictions.ilike("col.nomFun", (String) valor, MatchMode.ANYWHERE);
			if (campo.equals("datIni"))
				criterion = Restrictions.between("datEnt", parametros.get("datIni"),
						parametros.get("datFin"));
			if (campo.equals("colaborador.numCad"))
				criterion = Restrictions.eq("col.numCad", (Long) valor);
			if (campo.equals("solicitacao.numSeq"))
				criterion = Restrictions.eq("numSeq", (Integer) valor);
			if (campo.equals("usuario")) {
				criterion = Restrictions.in("col.centro", ((Usuario) valor).getCentrosPermitidos());
			}
			criteria.add(criterion);
		}
		criteria.setCacheable(true);
		List<Solicitacao> solicitacoes = criteria.list();
		return solicitacoes;
	}

	@Override
	public List<Solicitacao> busca(Map<String, Object> parametros, int start, int limit,
			String sort, String dir) {
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.createAlias("colaborador", "col");
		// paginação
		criteria.setMaxResults(limit);
		criteria.setFirstResult(start);

		if (dir.equals("ASC")) {
			criteria.addOrder(Order.asc(sort));
		} else if (dir.equals("DESC")) {
			criteria.addOrder(Order.desc(sort));
		}
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;

		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			if (campo.equals("colaborador.nomFun"))
				criterion = Restrictions.ilike("col.nomFun", (String) valor, MatchMode.ANYWHERE);
			if (campo.equals("datIni"))
				criterion = Restrictions.between("datEnt", parametros.get("datIni"),
						parametros.get("datFin"));
			if (campo.equals("id"))
				criterion = Restrictions.eq("id", valor);
			if (campo.equals("colaborador.numCad"))
				criterion = Restrictions.eq("col.numCad", (Long) valor);
			if (campo.equals("solicitacao.numSeq"))
				criterion = Restrictions.eq("numSeq", (Integer) valor);
			if (campo.equals("datPro")) {// data de envio
				criteria.createAlias("itensSolicitacao", "itens");
				criterion = Restrictions.between("itens.datPro", parametros.get("datPro"),	parametros.get("datPro"));
			}
			criteria.add(criterion);
		}
		criteria.setCacheable(true);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);/** não retornar itens duplicados, ou seja, retornar apenas as solicitacoes, não item a item**/
		List<Solicitacao> solicitacoes = criteria.list();
		return solicitacoes;
	}

	/**
	 * Busca todas as solicitações da data atual
	 * 
	 * @param motivo
	 *           0 = Ambos (Substituição e Implantação) 1 = Substituição 2 = Implantação
	 */
	public List<Solicitacao> listAllByActualDate(int motivo) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		if (motivo == 1) {// substituição
			List<Situacao> situacoes = new ArrayList<Situacao>();
			situacoes.add(Situacao.LB);
			situacoes.add(Situacao.SL);
			criteria.createCriteria("itensSolicitacao").add(Restrictions.in("sitItm", situacoes));
			criteria.add(Restrictions.eq("viaWeb", true));
			criteria.createCriteria("colaborador").add(Restrictions.ne("afastamento.id", 1000));

		} else if (motivo == 2) {
			criteria.createCriteria("colaborador").addOrder(Order.asc("empresa.id"))
					.addOrder(Order.asc("centro.id")).add(Restrictions.eq("afastamento.id", 1000));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public List<Solicitacao> listAllByActualDateByUsuario(Usuario usuario) {
		Criteria solCriteria = getSession().createCriteria(Solicitacao.class).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY);
		solCriteria.add(Restrictions.eq("solicitante.id", usuario.getId()));
		Criteria colCriteria = solCriteria.createCriteria("itensSolicitacao");
		List<Situacao> situList = new ArrayList<Situacao>();
		situList.add(Situacao.SL);
		situList.add(Situacao.SP);
		situList.add(Situacao.SAA);
		situList.add(Situacao.NA);
		situList.add(Situacao.LB);
		colCriteria.add(Restrictions.in("sitItm", situList));

		return solCriteria.list();
	}

	/**
	 * Retorna todas as solicitações do usuário logado que ainda não foram inseridas no SM
	 */
	@Override
	public List<Solicitacao> listAllByActualDateByUsuario(Usuario usuario, Integer start,
			Integer limit, String sort, String dir) {
		Criteria solCriteria = getSession().createCriteria(Solicitacao.class).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY);
		solCriteria.add(Restrictions.eq("solicitante.id", usuario.getId()));
		solCriteria.setMaxResults(limit);
		solCriteria.setFirstResult(start);
		solCriteria.createAlias("colaborador", "col");
		if (dir.equals("ASC")) {
			solCriteria.addOrder(Order.asc(sort));
		} else if (dir.equals("DESC")) {
			solCriteria.addOrder(Order.desc(sort));
		}
		Criteria colCriteria = solCriteria.createCriteria("itensSolicitacao");
		List<Situacao> situList = new ArrayList<Situacao>();
		situList.add(Situacao.SL);
		situList.add(Situacao.SP);
		situList.add(Situacao.SAA);
		situList.add(Situacao.NA);
		situList.add(Situacao.LB);
		colCriteria.add(Restrictions.in("sitItm", situList));
		solCriteria.setCacheable(true);
		solCriteria.setCacheRegion("solicitacoesAbertas");
		return solCriteria.list();
	}

	/**
	 * Lista solicitacoes pela empresa do colaborador
	 */
	public List<Solicitacao> listAllByEmp(int numEmp) {
		Criteria criteria = getSession().createCriteria(Solicitacao.class).createCriteria("colaborador")
				.add(Restrictions.eq("empresa.id", numEmp));
		return criteria.list();
	}

	/**
	 * Lista as solicitações de "qtdDiasAtras" dias atrás, de colaboradores efetivos que tenham sido feitas via web e ainda não foram importadas para o
	 * SM
	 */
	public List<Solicitacao> listAllByPrevDate() {
		Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, qtdDiasAtras);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.createAlias("solicitante", "sol");
		criteria.addOrder(Order.asc("sol.centro.id"));
		criteria.addOrder(Order.asc("sol.empresa.id"));
		criteria.add(Restrictions.lt("datEnt", cal.getTime()));
		criteria.add(Restrictions.eq("viaWeb", true));
		criteria.add(Restrictions.eq("numSeq", 0));

		criteria.createCriteria("colaborador").add(Restrictions.ne("afastamento.id", 1000));

		return criteria.list();
	}

	/**
	 * Lista as solicitações da situação indicada, com recurso de paginação Usado na autorização de itens bloqueados
	 */
	public List<Solicitacao> listByPrevDate(List<Situacao> situacoes, int qtdDias, int start,
			int limit, String sort, String dir, Usuario usuario) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, qtdDias);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.createCriteria("colaborador", "colaborador");
		criteria.createAlias("solicitante", "solicitante");
		criteria.createAlias("supervisor", "supervisor");
		criteria.createAlias("motivo", "motivo");
		// paginação
		criteria.setMaxResults(limit);
		criteria.setFirstResult(start);
		if (sort.length() >= 12) {
			String s = sort.substring(0, 12);
			if (s.equals("solicitacao.")) {
				sort = sort.substring(12, sort.length());
			}
		}
		// ordenação
		if (dir.equals("ASC")) {
			criteria.addOrder(Order.asc(sort));
		} else if (dir.equals("DESC")) {
			criteria.addOrder(Order.desc(sort));
		} else {
			criteria.addOrder(Order.asc("colaborador.nomFun"));
		}
		criteria.add(Restrictions.in("colaborador.centro",
				((Usuario) getSession().load(Usuario.class, usuario.getId())).getCentrosPermitidos()));
		criteria.add(Restrictions.lt("datEnt", cal.getTime()));
		criteria.createCriteria("itensSolicitacao").add(Restrictions.in("sitItm", situacoes));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	/**
	 * Lista apenas as solicitações com itens nas situações indicadas Usado na exportação para o SM, dos itens liberados
	 */
	public List<Solicitacao> listByPrevDate(List<Situacao> situacoes, int qtdDias, List<Order> ordens) {
		Criteria criteria = getSession().createCriteria(Solicitacao.class).setFetchMode(
				"itensSolicitacao", FetchMode.SELECT);
		criteria.createAlias("solicitante", "sol");
		criteria.createAlias("colaborador", "col");
		for (Order order : ordens) {
			criteria.addOrder(order);
		}
		criteria.createCriteria("itensSolicitacao").add(Restrictions.in("sitItm", situacoes));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	/**
	 * Lista as solicitações que não tem número sequencial cadastrado, ou seja, que é igual a 0 (zero)
	 */
	public List<Solicitacao> listPendentes(Integer start, Integer limit, String sort, String dir) {
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.NA);
		situacoes.add(Situacao.SAA);
		situacoes.add(Situacao.SL);
		situacoes.add(Situacao.PL);
		situacoes.add(Situacao.LB);
		situacoes.add(Situacao.SP);
		criteria.createCriteria("itensSolicitacao").setCacheable(true)
				.add(Restrictions.in("sitItm", situacoes));
		criteria.createAlias("colaborador", "colaborador");
		criteria.createAlias("motivo", "motivo");
		criteria.createAlias("solicitante", "solicitante");
		criteria.createAlias("supervisor", "supervisor");
		criteria.setMaxResults(limit);
		criteria.setFirstResult(start);
		if (dir.equals("ASC")) {
			criteria.addOrder(Order.asc(sort));
		} else if (dir.equals("DESC")) {
			criteria.addOrder(Order.desc(sort));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setCacheable(true);
		return criteria.list();
	}

	/**
	 * Lista as solicitações de implantação, apenas as novas
	 */
	public List<Solicitacao> listPendentesAdmissao() {
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.NA);
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.createAlias("solicitante", "sol");
		criteria.addOrder(Order.asc("sol.centro.id"));
		criteria.createCriteria("colaborador").add(Restrictions.eq("afastamento.id", 1000))
				.addOrder(Order.asc("empresa.id"));
		criteria.createCriteria("itensSolicitacao").add(Restrictions.in("sitItm", situacoes));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List<Solicitacao> listPendentesAdmissao(Integer start, Integer limit, String sort,
			String dir) throws Exception {
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.createAlias("solicitante", "sol");
		criteria.createAlias("colaborador.empresa", "emp");
		criteria.createCriteria("colaborador", "col").add(Restrictions.eq("afastamento.id", 1000));
		criteria.setMaxResults(limit);
		criteria.setFirstResult(start);
		if (dir.equals("ASC")) {
			criteria.addOrder(Order.asc(sort));
		} else if (dir.equals("DESC")) {
			criteria.addOrder(Order.desc(sort));
		}
		return criteria.list();
	}

	/**
	 * Retorna lista de solicitações de um colaborador
	 */
	public List<Solicitacao> listPendentesAdmissaoByColaborador(Colaborador colaborador) {
		Criteria criteria = getSession().createCriteria(Solicitacao.class).add(
				Restrictions.eq("colaborador.id", colaborador.getId()));
		criteria.setFetchMode("itensSolicitacao", FetchMode.SELECT);
		return criteria.list();
	}

	/**
	 * Gera uma Lista de Ocorrências que possuem itens que não foram enviados
	 */
	public List<Solicitacao> listPendentesEnvio(List<Situacao> situacoes) throws Exception {
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.setFetchMode("itensSolicitacao", FetchMode.SELECT);
		criteria.createAlias("itensSolicitacao", "item").add(
				Restrictions.in("item.sitItm", situacoes));
		criteria.createAlias("solicitante", "sol");
		criteria.addOrder(Order.asc("sol.centro.id"));
		criteria.addOrder(Order.asc("sol.empresa.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	/** Lista as solicitações em aberto para o colaborador especificado **/
	public List<Solicitacao> listSolAberto(Colaborador colaborador) throws Exception {
		List<Situacao> situList = new ArrayList<Situacao>();
		situList.add(Situacao.SL);
		situList.add(Situacao.SP);
		situList.add(Situacao.SAA);
		situList.add(Situacao.NA);
		situList.add(Situacao.LB);
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.add(Restrictions.eq("colaborador.id", colaborador.getId()));
		criteria.createAlias("itensSolicitacao", "item")
				.add(Restrictions.in("item.sitItm", situList));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	/**
	 * Retorna todas as solicitações que contenham a itens nas situações especificadas no parâmetro situacoes e que o colaborador já tenha sido
	 * admitido
	 */
	public List<Solicitacao> listSolAdmitidos(List<Situacao> situacoes) throws Exception {
		Criteria criteria = getSession().createCriteria(Solicitacao.class);
		criteria.createAlias("colaborador", "col").add(Restrictions.ne("col.afastamento.id", 1000));
		criteria.createAlias("itensSolicitacao", "item").add(
				Restrictions.in("item.sitItm", situacoes));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	/**
	 * Retorna as solicitações do colaborador se houver dentre os itens algum que esteja com situação Enviado - Aguardando Admissão Utilizado durante a
	 * exclusão do colaborador
	 */
	public List<ItemSolicitacao> litAllByCol(int id) {
		Criteria criteria = getSession().createCriteria(ItemSolicitacao.class);
		criteria.add(Restrictions.eq("sitItm", Situacao.EN)).createCriteria("solicitacao")
				.createCriteria("colaborador").add(Restrictions.eq("id", id));
		return criteria.list();
	}

	public Solicitacao loadByActualDate(int idCol) {
		Query query = getSession()
				.createQuery("from Solicitacao where datEnt=current_date and colaborador.id=:col");
		query.setInteger("col", idCol);
		Solicitacao solicitacao = (Solicitacao) query.uniqueResult();
		return solicitacao;
	}

	/**
	 * Retorna uma solicitação com base no campo chave do vetor
	 */
	public Solicitacao loadByChaveVetorh(int numEmp, int numCad, Date datEnt) {
		Query query = getSession().createQuery("from Solicitacao where "
				+ "colaborador.empresa.id=:emp and colaborador.numCad=:cad and datEnt=:ent");
		query.setInteger("emp", numEmp);
		query.setInteger("cad", numCad);
		query.setDate("ent", datEnt);
		Solicitacao solicitacao = (Solicitacao) query.uniqueResult();
		return solicitacao;
	}

	@Override
	public List<Solicitacao> listByExample(Solicitacao solicitacao) throws Exception {
		List<Solicitacao> resultado = new ArrayList<Solicitacao>();
		try {
			Example exemplo = Example.create(solicitacao).enableLike(MatchMode.ANYWHERE)
					.excludeZeroes().ignoreCase();
			exemplo.excludeProperty("assPro");
			exemplo.excludeProperty("viaWeb");
			Criteria criteria = getSession().createCriteria(Solicitacao.class);

			if (solicitacao.getId() > 0) {
				criteria.add(Restrictions.eq("id", solicitacao.getId()));
			}
			criteria.add(exemplo);
			if (solicitacao.getColaborador().getId() > 0) {
				criteria.add(Restrictions.eq("colaborador.id", solicitacao.getColaborador().getId()));
			}
			criteria.addOrder(Order.desc("datEnt"));
			resultado = criteria.list();
		} catch (Exception e) {
			logger.error("Erro carregando lista de objetos via exemplo "
					+ this.objClass.getSimpleName() + ". Causa: " + e.getMessage());
		}
		return resultado;
	}
}
