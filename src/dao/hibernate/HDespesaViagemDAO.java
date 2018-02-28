package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.DespesaViagemDAO;
import bean.DespesaViagem;


@Repository
@Transactional
public class HDespesaViagemDAO extends HGenericDAO<DespesaViagem> implements DespesaViagemDAO {
	
	public HDespesaViagemDAO(){
		super(DespesaViagem.class);
	}

	public HDespesaViagemDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, DespesaViagem.class);
	}

}
