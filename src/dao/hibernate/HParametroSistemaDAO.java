package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.ParametroSistema;
import dao.ParametroSistemaDAO;

@Repository
@Transactional
public class HParametroSistemaDAO extends HGenericDAO<ParametroSistema> implements
		ParametroSistemaDAO {
	public HParametroSistemaDAO() {
		super(ParametroSistema.class);
	}

	public HParametroSistemaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, ParametroSistema.class);
	}
}
