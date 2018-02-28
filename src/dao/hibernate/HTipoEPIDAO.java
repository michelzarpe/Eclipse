package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.TipoEPI;
import dao.TipoEPIDAO;

@Repository
@Transactional
public class HTipoEPIDAO extends HGenericDAO<TipoEPI> implements TipoEPIDAO {

	public HTipoEPIDAO() {
		super(TipoEPI.class);
	}

	public HTipoEPIDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, TipoEPI.class);
	}

	@Override
	public List<TipoEPI> listTiposAtivos() throws Exception {
		Criteria criteria = getSession().createCriteria(TipoEPI.class);
		criteria.createAlias("uniformes", "uni").add(Restrictions.eq("uni.sitEpi", "A"));
		criteria.addOrder(Order.asc("desSvc"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

}
