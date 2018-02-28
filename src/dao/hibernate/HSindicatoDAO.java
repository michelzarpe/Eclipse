package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Sindicato;
import dao.SindicatoDAO;

@Repository
@Transactional
public class HSindicatoDAO extends HGenericDAO<Sindicato> implements SindicatoDAO{
	
	public HSindicatoDAO() {
		super(Sindicato.class);
	}

	public HSindicatoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Sindicato.class);
	}
	@Override
	public Sindicato retSinBd(int codSin) {
		Criteria criteria = getSession().createCriteria(Sindicato.class);
		criteria.add(Restrictions.eq("codSin", codSin));
		Sindicato sindicato = new Sindicato();
		sindicato = (Sindicato) criteria.uniqueResult();
		return sindicato;
	}

	@Override
	public List<Sindicato> retListSind() {
		Criteria criteria = getSession().createCriteria(Sindicato.class);
		return criteria.list();
	}

}
