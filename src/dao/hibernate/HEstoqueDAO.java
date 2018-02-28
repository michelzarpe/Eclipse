package dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Estoque;
import bean.Uniforme;
import dao.EstoqueDAO;

@Repository
@Transactional
public class HEstoqueDAO extends HGenericDAO<Estoque> implements EstoqueDAO {
	
	public HEstoqueDAO(){
		super(Estoque.class);
	}
	
	public HEstoqueDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Estoque.class);
	}

	/**
	 * Retorna a quantidade total do item no estoque, independente da empresa
	 */
	public int getTotalEstoqueItem(Uniforme uniforme) {
		Criteria criteria = getSession().createCriteria(Estoque.class);
		criteria.createCriteria("uniforme").add(Restrictions.eq("id", uniforme.getId()));
		criteria.setProjection(Projections.sum("qtdEst"));
		int total = 0;
		try {
			if (criteria.uniqueResult() != null)
				total = (Integer) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

}
