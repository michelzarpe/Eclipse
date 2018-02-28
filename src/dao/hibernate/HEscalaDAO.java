package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Escala;
import dao.EscalaDAO;

@Repository
@Transactional
public class HEscalaDAO extends HGenericDAO<Escala> implements EscalaDAO {
	
	public HEscalaDAO(){
		super(Escala.class);
	}

	public HEscalaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Escala.class);
	}

}
