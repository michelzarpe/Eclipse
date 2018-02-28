package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Motivo;
import dao.MotivoDAO;

@Repository
@Transactional
public class HMotivoDAO extends HGenericDAO<Motivo> implements MotivoDAO {
	
	public HMotivoDAO(){
		super(Motivo.class);
	}

	public HMotivoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Motivo.class);
	}

}
