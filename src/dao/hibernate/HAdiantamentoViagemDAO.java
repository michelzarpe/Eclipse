package dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.AdiantamentoViagem;
import bean.Usuario;
import dao.AdiantamentoViagemDAO;

@Repository
@Transactional
public class HAdiantamentoViagemDAO extends HGenericDAO<AdiantamentoViagem> implements
		AdiantamentoViagemDAO {

	public HAdiantamentoViagemDAO() {
		super(AdiantamentoViagem.class);
	}

	public HAdiantamentoViagemDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, AdiantamentoViagem.class);
	}

	public List<AdiantamentoViagem> listPendentes(Usuario usuario) {
		Criteria criteria = getSession().createCriteria(AdiantamentoViagem.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		criteria.add(Restrictions.isNull("datAce"));
		List<AdiantamentoViagem> adiantamentos = new ArrayList<AdiantamentoViagem>();
		adiantamentos = criteria.list();
		return adiantamentos;
	}

}
