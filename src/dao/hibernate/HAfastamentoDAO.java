package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Afastamento;
import dao.AfastamentoDAO;

@Repository
@Transactional
public class HAfastamentoDAO extends HGenericDAO<Afastamento> implements
		AfastamentoDAO {
	
	public HAfastamentoDAO(){
		super(Afastamento.class);
	}

	public HAfastamentoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Afastamento.class);
	}

}
