package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Deficiencia;
import bean.Vinculo;
import dao.VinculoDAO;

@Repository
@Transactional
public class HVinculoDAO extends HGenericDAO<Vinculo> implements VinculoDAO{

	public HVinculoDAO() {
		super(Vinculo.class);
	}

	public HVinculoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Vinculo.class);
	}
	
	@Override
	public Vinculo retVinBd(int codVin) {
		Criteria criteria = getSession().createCriteria(Vinculo.class);
		criteria.add(Restrictions.eq("codVin", codVin));
		Vinculo vinculo = new Vinculo();
		vinculo = (Vinculo) criteria.uniqueResult();
		return vinculo;
	}

	@Override
	public List<Vinculo> retListVinc() {
		Criteria criteria = getSession().createCriteria(Vinculo .class);
		return criteria.list();
	}

}
