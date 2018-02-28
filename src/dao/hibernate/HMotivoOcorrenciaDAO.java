package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.MotivoOcorrencia;
import dao.MotivoOcorrenciaDAO;

@Repository
@Transactional
public class HMotivoOcorrenciaDAO extends HGenericDAO<MotivoOcorrencia> implements MotivoOcorrenciaDAO {
	
	public HMotivoOcorrenciaDAO(){
		super(MotivoOcorrencia.class);
	}
	
	public HMotivoOcorrenciaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, MotivoOcorrencia.class);
	}

}
