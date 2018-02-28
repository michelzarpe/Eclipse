package dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Colaborador;
import bean.Dependente;
import bean.Empresa;
import dao.DependenteDAO;

@Repository
@Transactional
public class HDependenteDAO extends HGenericDAO<Dependente> implements DependenteDAO{
	public HDependenteDAO(){
		super(Dependente.class);
	}

	public HDependenteDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Dependente.class);
	}
	
	@Override
	public List<Dependente> retDependentes(int colaborador) {
		Query query = getSession().createQuery("from dependentes where id.colaborador.id=:numcad");
		query.setInteger("numcad",colaborador);
		List<Dependente> list = query.list();
		return list;
	}

}
