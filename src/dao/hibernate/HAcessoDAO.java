package dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Acesso;
import dao.AcessoDAO;

@Repository
@Transactional
public class HAcessoDAO extends HGenericDAO<Acesso> implements AcessoDAO {

	public HAcessoDAO() {
		super(Acesso.class);
	}

	public HAcessoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Acesso.class);
	}

	@Override
	public List<Acesso> listByDate(Date data) {
		Criteria criteria = getSession().createCriteria(Acesso.class);
		criteria.add(Restrictions.gt("datAce", data));
		criteria.addOrder(Order.desc("datAce"));
		criteria.setCacheable(true);
		List<Acesso> list = criteria.list();
		return list;
	}

	@Override
	public Acesso getAcessoByIdSession(String idSession) throws Exception {
		Criteria criteria = getSession().createCriteria(Acesso.class);
		criteria.add(Restrictions.eq("idSession", idSession));
		criteria.setCacheable(true);
		return (Acesso) criteria.uniqueResult();
	}

}
