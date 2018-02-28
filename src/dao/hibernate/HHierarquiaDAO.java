package dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import util.NivelOrganograma;
import bean.Hierarquia;
import dao.HierarquiaDAO;

@Repository
@Transactional
public class HHierarquiaDAO extends HGenericDAO<Hierarquia> implements HierarquiaDAO {

	public HHierarquiaDAO() {
		super(Hierarquia.class);
	}

	public final Date	data	= new GregorianCalendar(2080, 06, 04).getTime();

	public HHierarquiaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Hierarquia.class);
	}

	public List<Hierarquia> listAllNivelEmpresa() {
		Query query = getSession().createQuery(
				"from Hierarquia where length(codLoc)=2 and datFim<:data order by local.nomLoc");
		query.setDate("data", data);
		return query.list();
	}

	public List<Hierarquia> listAllNivelFilial(String codLoc) {
		Query query = getSession()
				.createQuery(
						"from Hierarquia where codLoc like :codLoc and length(codLoc)=5 and datFim<:data order by local.nomLoc");
		query.setString("codLoc", codLoc + "%");
		query.setDate("data", data);
		return query.list();
	}

	public List<Hierarquia> listAllNivelCliente(String codLoc) {
		Query query = getSession()
				.createQuery(
						"from Hierarquia where codLoc like :codLoc and length(codLoc)=10 and datFim<:data order by local.nomLoc");
		query.setString("codLoc", codLoc + "%");
		query.setDate("data", data);
		List<Hierarquia> hieList = query.list();
		return hieList;
	}

	public List<Hierarquia> listAllNivelCidade(String codLoc) {
		Query query = getSession()
				.createQuery(
						"from Hierarquia where codLoc like :codLoc and length(codLoc)=15 and datFim<:data order by local.nomLoc");
		query.setString("codLoc", codLoc + "%");
		query.setDate("data", data);
		return query.list();
	}

	public List<Hierarquia> listAllNivelPosto(String codLoc) {
		Query query = getSession()
				.createQuery(
						"from Hierarquia where codLoc like :codLoc and length(codLoc)=20 and datFim<:data order by local.nomLoc");
		query.setString("codLoc", codLoc + "%");
		query.setDate("data", data);
		return query.list();
	}

	public Hierarquia loadByCodLoc(String codLoc) {
		Query query = getSession().createQuery("from Hierarquia where codLoc =:codLoc");
		query.setString("codLoc", codLoc);
		List<Hierarquia> hierarquias = query.list();
		if (!hierarquias.isEmpty()) {
			Hierarquia hierarquia = hierarquias.get(0);
			return hierarquia;
		} else
			return null;
	}

	@Override
	public Hierarquia loadLocalByNivel(String codLoc, NivelOrganograma nivel) {
		Criteria criteria = getSession().createCriteria(Hierarquia.class);
		criteria.add(Restrictions.le("datFim", data));
		String loc = "";

		switch (nivel.ordinal()) {
			case 0:
				loc = codLoc.substring(0, 2);
				break;
			case 1:
				loc = codLoc.substring(0, 5);
				break;
			case 2:
				loc = codLoc.substring(0, 10);
				break;
			case 3:
				loc = codLoc.substring(0, 15);
				break;
			case 4:
				loc = codLoc.substring(0, 20);
				break;
			default:
				break;
		}
		criteria.add(Restrictions.eq("codLoc", loc));
		Hierarquia hierarquia = (Hierarquia) criteria.uniqueResult();
		return hierarquia;
	}

	@Override
	public List<Hierarquia> busca(String dadoBusca, String nivelAnterior, String nivel, String codLoc) {
		Criteria criteria = getSession().createCriteria(Hierarquia.class, "tab01");
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Hierarquia.class, "tab02");
		subCriteria.add(Restrictions.eqProperty("tab02.id.tabOrg", "tab01.id.tabOrg"));
		subCriteria.add(Restrictions.eqProperty("tab02.codLoc", "tab01.codLoc"));
		subCriteria.setProjection(Projections.max("id.datIni"));

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 31);
		cal.set(Calendar.MONTH, 12);
		cal.set(Calendar.YEAR, 1950);
		if (codLoc.equals("")) {
			Criterion criterion = null;
			if (nivel.equals("2"))
				criterion = Restrictions.sqlRestriction("length({alias}.codLoc) = ?", 6,
						Hibernate.INTEGER);
			else if (nivel.equals("3"))
				criterion = Restrictions.sqlRestriction("length({alias}.codLoc) = ?", 11,
						Hibernate.INTEGER);
			else if (nivel.equals("4"))
				criterion = Restrictions.sqlRestriction("length({alias}.codLoc) = ?", 16,
						Hibernate.INTEGER);
			else if (nivel.equals("5"))
				criterion = Restrictions.sqlRestriction("length({alias}.codLoc) = ?", 21,
						Hibernate.INTEGER);
			// código do local começando com...
			Criterion criterionB = Restrictions.ilike("codLoc", (String) nivelAnterior,
					MatchMode.START);
			criteria.add(Restrictions.and(criterion, criterionB));
			criteria.add(Subqueries.propertyEq("id.datIni", subCriteria));
			criteria
					.createCriteria("local")
					.add(Restrictions.ilike("nomLoc", (String) dadoBusca, MatchMode.ANYWHERE))
					.add(Restrictions.or(Restrictions.gt("datExt", new Date()),
							Restrictions.lt("datExt", cal.getTime())));

		} else
			criteria.add(Restrictions.eq("codLoc", codLoc));

		List<Hierarquia> hierarquias = criteria.list();
		return hierarquias;
	}

	@Override
	public Hierarquia getHierarquiaAtual(int numLoc) {
		Query query = getSession()
				.createQuery(
						"from Hierarquia h where h.local.id.numLoc =:numLoc and h.id.datIni in (select max(b.id.datIni) from Hierarquia b where b.local.id.numLoc=:numLoc)");
		query.setInteger("numLoc", numLoc);
		Hierarquia hierarquia = (Hierarquia) query.uniqueResult();
		return hierarquia;
	}

}
