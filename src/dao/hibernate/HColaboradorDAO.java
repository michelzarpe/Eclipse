package dao.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Centro;
import bean.Cliente;
import bean.Colaborador;
import bean.Dependente;
import bean.Usuario;
import dao.ColaboradorDAO;

@Repository
@Transactional
public class HColaboradorDAO extends HGenericDAO<Colaborador> implements ColaboradorDAO {
	static Logger	logger	= Logger.getLogger(HColaboradorDAO.class);

	public HColaboradorDAO() {
		super(Colaborador.class);
	}

	public HColaboradorDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Colaborador.class);
	}

	@Override
	public Colaborador loadById(Serializable id) throws Exception {
		Query query = getSession().createQuery("from Colaborador where id=:id");
		query.setInteger("id", (Integer) id);
		Colaborador colaborador = new Colaborador();
		try {
			colaborador = (Colaborador) query.uniqueResult();
		} catch (Exception e) {
			colaborador = null;
		}
		return colaborador;
	}

	@Override
	public List<Colaborador> busca(Map<String, Object> parametros) {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;
		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			if (campo.equals("id"))
				criterion = Restrictions.eq("id", valor);
			if (campo.equals("numCad"))
				criterion = Restrictions.eq("numCad", valor);
			if (campo.equals("numCpf"))
				criterion = Restrictions.ilike("numCpf", (String) valor, MatchMode.START);
			if (campo.equals("nomFun"))
				criterion = Restrictions.ilike("nomFun", (String) valor, MatchMode.START);
			if (campo.equals("empresa.id"))
				criterion = Restrictions.eq("empresa.id", valor);
			if (campo.equals("notFindDemitidos"))
				criterion = Restrictions.ne("afastamento.id", valor);
			/**if (campo.equals("usuario")) {
				Criteria criteria2 = getSession().createCriteria(Usuario.class)
						.add(Restrictions.eq("id", valor))
						.setFetchMode("centrosPermitidos", FetchMode.SELECT);
				criteria2.setCacheable(true);
				Usuario usuario = (Usuario) criteria2.uniqueResult();
				criterion = Restrictions.in("centro", usuario.getCentrosPermitidos());
			}**/
			criteria.setCacheable(true);
			criteria.add(criterion);
		}
		criteria.addOrder(Order.asc("nomFun"));
		List<Colaborador> colList = criteria.list();
		
		
		for (Colaborador colaboradorTeste : colList) {
			for (Dependente dependenteTeste : colaboradorTeste.getDependentes()) {
				  System.out.println(dependenteTeste.getNomDep());
			}
		}
	
		return colList;
	}

	public List<Colaborador> colaboradoresAtivos() {
		Query query = getSession().createQuery(
				"from Colaborador where afastamento.id<>7 order by nomFun");
		List<Colaborador> colaSet = new LinkedList<Colaborador>();
		for (Iterator<Colaborador> iterator = query.list().iterator(); iterator.hasNext();) {
			Colaborador colaborador = (Colaborador) iterator.next();
			colaSet.add(colaborador);
		}
		return colaSet;
	}

	public List<Colaborador> listAllByFilial(Cliente cliente, Usuario usuario) {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		criteria.addOrder(Order.asc("nomFun"));
		criteria.add(Restrictions.ne("afastamento.id", new Integer(7)));
		criteria.add(Restrictions.eq("cliente.id", cliente.getId()));
		criteria.add(Restrictions.in("centro", usuario.getCentrosPermitidos()));
		List<Colaborador> colaboradores = criteria.list();
		return colaboradores;
	}

	@Deprecated
	public List<Colaborador> listAllByLocal(int numLoc, Usuario usuario) {
		logger.info("[HColaboradorDAO] Número do local: " + numLoc);
		Query query = getSession().createQuery(
				"from Colaborador where afastamento.id<>7 and local.numLoc=:local order by nomFun");
		query.setInteger("local", numLoc);
		@SuppressWarnings("unchecked")
		List<Colaborador> colaboradores = query.list();
		if (colaboradores != null)
			return colaboradores;
		else
			return null;
	}

	@Override
	public List<Colaborador> listAtivosByEmp(int id, Usuario usuario) {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		criteria.add(Restrictions.ne("afastamento.id", 7));
		criteria.add(Restrictions.eq("empresa.id", id));
		criteria.add(Restrictions.in("centro",
				((Usuario) getSession().load(Usuario.class, usuario.getId())).getCentrosPermitidos()));
		criteria.addOrder(Order.asc("nomFun"));
		List<Colaborador> colaboradores = criteria.list();
		if (colaboradores != null)
			return colaboradores;
		else
			return null;
	}

	@Override
	public List<Colaborador> listBySituacao(int situacao) throws Exception {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		criteria.add(Restrictions.eq("afastamento.id", situacao));
		criteria.addOrder(Order.asc("empresa.id")).addOrder(Order.asc("usuCad.id"));
		List<Colaborador> colaboradores = criteria.list();
		return colaboradores;
	}

	@Override
	public List<Colaborador> listBySituacao(int situacao, Integer start, Integer limit, String sort,
			String dir) {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		criteria.add(Restrictions.eq("afastamento.id", situacao));
		// criteria.setMaxResults(limit);
		// criteria.setFirstResult(start);
		if (dir.equals("ASC")) {
			criteria.addOrder(Order.asc(sort));
		} else if (dir.equals("DESC")) {
			criteria.addOrder(Order.desc(sort));
		}
		criteria.setCacheable(true);
		List<Colaborador> colaboradores = criteria.list();
		return colaboradores;
	}

	public List<Colaborador> listBySituacao(int situacao, Usuario usuario) {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		criteria.add(Restrictions.eq("afastamento.id", situacao));
		// quando for apenas busca por colaboradores não admitidos no vetorh:
		 if (situacao == 1000) {
		 criteria.add(Restrictions.eq("usuCad.id", usuario.getId()));
		 }  /**else {
		criteria.add(Restrictions.in("centro", usuario.getCentrosPermitidos()));
	   }**/
		criteria.addOrder(Order.asc("nomFun"));
		List<Colaborador> colaboradores = criteria.list();
		return colaboradores;
	}

	@Override
	public List<Colaborador> listSupervisores() {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		criteria.add(Restrictions.ne("afastamento.id", 7));
		criteria.add(Restrictions.or(Restrictions.eq("funcao", 2),
				Restrictions.eq("supervisor", true)));
		criteria.addOrder(Order.asc("nomFun"));
		List<Colaborador> colaboradores = criteria.list();
		return colaboradores;
	}

	@Override
	public List<Colaborador> listSupervisores(int numEmp) {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		criteria.add(Restrictions.ne("afastamento.id", 7));
		criteria.add(Restrictions.eq("empresa.id", numEmp));
		criteria.createCriteria("cargo").add(Restrictions.ilike("titCar", "Super", MatchMode.START));
		criteria.addOrder(Order.asc("nomFun"));
		List<Colaborador> colaboradores = criteria.list();
		return colaboradores;
	}

	public int loadByCPF(String cpf, Date datAdm, Date datInc, int horInc) {
		int idColaborador = 0;
		Query query = getSession().createQuery(
				"from ColaboradorEfetivo where numCpf=:numCpf and "
						+ "datAdm=:datAdm and datInc=:datInc and horInc=:horInc");
		query.setString("numCpf", cpf);
		query.setDate("datAdm", datAdm);
		query.setDate("datInc", datInc);
		query.setInteger("horInc", horInc);
		Colaborador colaborador = new Colaborador();
		colaborador = (Colaborador) query.uniqueResult();
		if (colaborador != null)
			idColaborador = colaborador.getId();
		return idColaborador;
	}

	@Override
	public Colaborador loadByCPF(String cpf) throws Exception {
		Query query = getSession().createQuery(
				"from Colaborador where numCpf=:cpf and afastamentoId<>7");
		query.setString("cpf", cpf);
		Colaborador colaborador = new Colaborador();
		try {
			colaborador = (Colaborador) query.uniqueResult();
		} catch (Exception e) {
			colaborador = null;
			throw e;
		}
		return colaborador;
	}

	@Override
	/** Atualiza todos os cpfs dos colaboradores para o formato sem pontos e hífens, e sem os zeros na frente **/
	public void normalizaCPF() {
		Query query = getSession().createSQLQuery("update Colaborador set numCpf=cast((replace((replace(numCpf, '.', '')), '-', '')) as unsigned) where id>0");
		query.executeUpdate();
	}

	public int loadByCPF(String cpf, int sitAfa, Date datAdm, Date datInc, int horInc) {
		int idColaborador = 0;
		Query query = getSession()
				.createQuery(
						"from ColaboradorEfetivo where numCpf=:numCpf and "
								+ "afastamento.id=:sitAfa and datAdm=:datAdm and datInc=:datInc and horInc=:horInc");
		query.setString("numCpf", cpf);
		query.setInteger("sitAfa", sitAfa);
		query.setDate("datAdm", datAdm);
		query.setDate("datInc", datInc);
		query.setInteger("horInc", horInc);
		Colaborador colaborador = new Colaborador();
		colaborador = (Colaborador) query.uniqueResult();
		if (colaborador != null)
			idColaborador = colaborador.getId();
		return idColaborador;
	}

	@Override
	public Colaborador loadByNumCad(long numCad, int numEmp) {
		Query query = getSession().createQuery(
				"from Colaborador where numCad=:numCad and empresa.id=:numEmp");
		query.setLong("numCad", numCad);
		query.setInteger("numEmp", numEmp);
		Colaborador colaborador = new Colaborador();
		try {
			colaborador = (Colaborador) query.uniqueResult();
		} catch (Exception e) {
			colaborador = null;
		}
		return colaborador;
	}

	/**
	 * Retorna um int com o id do colaborador
	 * 
	 * @param numCad
	 *           - cadastro Vetorh
	 * @param numEmp
	 *           - empresa Vetorh
	 * @throws Exception
	 */
	public int loadIdByNumCad(long numCad, int numEmp) throws Exception {
		int idColaborador = 0;
		try {
			Query query = getSession().createQuery(
					"select id from Colaborador where numCad=:numCad and empresa.id=:numEmp");
			query.setLong("numCad", numCad);
			query.setInteger("numEmp", numEmp);
			try {
				Object object = query.uniqueResult();
				if (object != null) {
					idColaborador = (Integer) query.uniqueResult();
				}
			} catch (NonUniqueResultException e) {
				logger.error("Erro: encontrado mais do que um colaborador com o cadastro:" + numCad
						+ " da empresa: " + numEmp);
				throw new Exception("Erro ao tentar sincronizar colaboradores webOper2.0 e sistemas Senior: encontrado mais do que um colaborador com o cadastro:"
						+ numCad + " da empresa: " + numEmp
						+ " na webOper2.0. Voce deve ir em Colaboradores - Consulta - Colaboradores e pesquisar pelo cadastro "
						+ numCad + ", excluir o cadastro indevido e tentar sincronizar novamente.");
			} catch (NullPointerException e) {
				idColaborador = 0;
				throw e;
			}
		} catch (Exception e) {
			throw e;
		}
		return idColaborador;
	}

	@Override
	public List<Colaborador> listDemitidos(Date datIni, Date datFin, Centro centro) throws Exception {
		Criteria criteria = getSession().createCriteria(Colaborador.class);
		try {
			criteria.add(Restrictions.between("datDem", datIni, datFin));
			if (centro.getId() > 0)
				criteria.add(Restrictions.eq("centro", centro));
			criteria.addOrder(Order.asc("empresa.id"));
			criteria.addOrder(Order.asc("centro.id"));
			criteria.addOrder(Order.asc("nomFun"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return criteria.list();
	}

	@Override
	public Colaborador loadByCPFEmp(String cpf, int numEmp) throws Exception {
		Query query = getSession().createQuery(
				"from Colaborador where numCpf=:cpf and afastamentoId<>7 and empresaId=:emp");
		query.setString("cpf", cpf);
		query.setInteger("emp", numEmp);
		Colaborador colaborador = new Colaborador();
		try {
			colaborador = (Colaborador) query.uniqueResult();
		} catch (Exception e) {
			colaborador = null;
			throw e;
		}
		return colaborador;
	}
}
