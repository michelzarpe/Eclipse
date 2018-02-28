package dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Empresa;
import dao.EmpresaDAO;

@Repository
@Transactional
public class HEmpresaDAO extends HGenericDAO<Empresa> implements EmpresaDAO {
	
	public HEmpresaDAO(){
		super(Empresa.class);
	}
	
	public HEmpresaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Empresa.class);
	}

	@Override
	public Empresa getEmpresaByEmpSap(int empSap) {
		Criteria criteria = getSession().createCriteria(Empresa.class);
		criteria.add(Restrictions.eq("empSap", empSap));
		Empresa empresa = new Empresa();
		empresa = (Empresa) criteria.uniqueResult();
		return empresa;
	}
}
