package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.EscalaRonda;
import dao.EscalaRondaDAO;

@Repository
@Transactional
public class HEscalaRondaDAO extends HGenericDAO<EscalaRonda> implements EscalaRondaDAO {
	
	public HEscalaRondaDAO(){
		super(EscalaRonda.class);
	}
	
	public HEscalaRondaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, EscalaRonda.class);
	}

}
