package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.BancoDAO;
import bean.Banco;

@Repository
@Transactional
public class HBancoDAO extends HGenericDAO<Banco> implements BancoDAO {
	
	public HBancoDAO(){
		super(Banco.class);
	}
	
	public HBancoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Banco.class);
	}

	@Override
	public List<Banco> buscar(String campo, String valor) throws Exception {
		Criteria criteria = getSession().createCriteria(Banco.class);
		criteria.add(Restrictions.ilike(campo, valor, MatchMode.START));
		return criteria.list();
	}
}
