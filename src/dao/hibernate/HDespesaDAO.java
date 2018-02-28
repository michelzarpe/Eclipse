package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.DespesaDAO;
import bean.Despesa;

@Repository
@Transactional
public class HDespesaDAO extends HGenericDAO<Despesa> implements DespesaDAO {
	
	public HDespesaDAO(){
		super(Despesa.class);
	}

	public HDespesaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Despesa.class);
	}

}
