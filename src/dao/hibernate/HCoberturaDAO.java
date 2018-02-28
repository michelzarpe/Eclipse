package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Cobertura;
import dao.CoberturaDAO;

@Repository
@Transactional
public class HCoberturaDAO extends HGenericDAO<Cobertura> implements CoberturaDAO {
	
	public HCoberturaDAO(){
		super(Cobertura.class);
	}

	public HCoberturaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Cobertura.class);
	}
}
