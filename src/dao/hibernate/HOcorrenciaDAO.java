package dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Ocorrencia;
import bean.Usuario;
import dao.OcorrenciaDAO;

@Repository
@Transactional
public class HOcorrenciaDAO extends HGenericDAO<Ocorrencia> implements OcorrenciaDAO {
	
	public HOcorrenciaDAO(){
		super(Ocorrencia.class);
	}

	public HOcorrenciaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Ocorrencia.class);
	}

	@Override
	public List<Ocorrencia> listByDateByUser(Date dataAtual, Usuario usuario) {
		Criteria criteria = getSession().createCriteria(Ocorrencia.class);
		criteria.add(Restrictions.eq("usuario.id", usuario.getId()));
		criteria.add(Restrictions.lt("datGer", dataAtual));
		return criteria.list();
	}

}
