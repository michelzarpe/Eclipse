package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.CentroDAO;
import bean.Centro;

@Repository
@Transactional
public class HCentroDAO extends HGenericDAO<Centro> implements CentroDAO {
	
	public HCentroDAO(){
		super(Centro.class);
	}
	
	public HCentroDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Centro.class);
	}
}
