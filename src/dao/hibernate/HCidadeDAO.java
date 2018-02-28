package dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Cidade;
import bean.Cidade.Estado;
import dao.CidadeDAO;

@Repository
@Transactional
public class HCidadeDAO extends HGenericDAO<Cidade> implements CidadeDAO {
	
	public HCidadeDAO(){
		super(Cidade.class);
	}
	
	public HCidadeDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Cidade.class);
	}

	public List<Cidade> listAllByUF(String codEst) {
		Criteria criteria = getSession().createCriteria(Cidade.class);
		criteria.add(Restrictions.eq("estCid", codEst));
		criteria.addOrder(Order.asc("nomCid"));
		List<Cidade> cidades = criteria.list();
		return cidades;
	}

	@Override
	public List<Cidade> busca(Map<String, Object> parametros) {
		Criteria criteria = getSession().createCriteria(Cidade.class);
		criteria.addOrder(Order.asc("nomCid"));
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;
		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			if (campo.equals("nomCid"))
				criterion = Restrictions.ilike("nomCid", (String) valor, MatchMode.START);
			if (campo.equals("estCid")){
				criterion = Restrictions.eq("estCid", Estado.valueOf((String) valor));

			}
			if (campo.equals("id"))
				criterion = Restrictions.eq("id", valor);
			criteria.add(criterion);
		}
		criteria.setCacheable(true);

		List<Cidade> cidades = criteria.list();
		return cidades;
	}

}
