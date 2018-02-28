package dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import bean.Bairro;
import dao.BairroDAO;

@Repository
@Transactional
public class HBairroDAO extends HGenericDAO<Bairro> implements BairroDAO {

	public HBairroDAO(){
		super(Bairro.class);
	}
	
	public HBairroDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Bairro.class);
	}
	
	@Override
	public List<Bairro> listAllByCid(int codCid) {
		Query query = getSession().createQuery("from bairro where codCid=:codCid");
		query.setInteger("codCid", codCid);
		return query.list();
	}

	@Override
	public List<Bairro> busca(Map<String, Object> parametros) {
		Criteria criteria = getSession().createCriteria(Bairro.class);
		criteria.addOrder(Order.asc("nomBai"));
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;
		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			if (campo.equals("nomBai"))
				criterion = Restrictions.ilike("nomBai", (String) valor, MatchMode.START);
			if (campo.equals("codCid"))
				criterion = Restrictions.eq("codCid", valor);
			if (campo.equals("codBai"))
				criterion = Restrictions.eq("codBai", valor);
			criteria.add(criterion);
		}
		criteria.setCacheable(true);
		List<Bairro> bairros = criteria.list();
		return bairros;
	}

	@Override
	public void removerBairros() {
		String hql = "delete from bairro";
		Query query = getSession().createQuery(hql);
   	query.executeUpdate();

	}

	@Override
	public Bairro listAllByCid(int codCid, int codBai) {
		Query query = getSession().createQuery("from bairro where codCid=:codCid and codBai=:codBai");
		query.setInteger("codCid", codCid);
		query.setInteger("codBai", codBai);
		return (Bairro) query.uniqueResult();
	}
}
