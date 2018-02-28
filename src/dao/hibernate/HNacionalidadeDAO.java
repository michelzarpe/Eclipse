package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Nacionalidade;
import dao.NacionalidadeDAO;

@Repository
@Transactional
public class HNacionalidadeDAO extends HGenericDAO<Nacionalidade> implements NacionalidadeDAO {
	
	public HNacionalidadeDAO(){
		super(Nacionalidade.class);
	}
	
	public HNacionalidadeDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Nacionalidade.class);
	}
}
