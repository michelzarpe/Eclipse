package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Cargo;
import dao.CargoDAO;

@Repository
@Transactional
public class HCargoDAO extends HGenericDAO<Cargo> implements CargoDAO {
	
	public HCargoDAO(){
		super(Cargo.class);
	}

	public HCargoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Cargo.class);
	}

}
