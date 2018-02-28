package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.MotivoViagem;
import dao.MotivoViagemDAO;

@Repository
@Transactional
public class HMotivoViagemDAO extends HGenericDAO<MotivoViagem> implements MotivoViagemDAO {
	
	public HMotivoViagemDAO(){
		super(MotivoViagem.class);
	}

	public HMotivoViagemDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, MotivoViagem.class);
	}

}
