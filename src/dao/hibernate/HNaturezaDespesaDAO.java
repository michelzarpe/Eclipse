package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import bean.NaturezaDespesa;
import dao.NaturezaDespesaDAO;

@Repository
@Transactional
public class HNaturezaDespesaDAO extends HGenericDAO<NaturezaDespesa> implements NaturezaDespesaDAO{

	public HNaturezaDespesaDAO() {
		super(NaturezaDespesa.class);
	}

	public HNaturezaDespesaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, NaturezaDespesa.class);
	}
	
	@Override
	public NaturezaDespesa retNatDes(int natDes) {
      Criteria criteria = getSession().createCriteria(NaturezaDespesa.class);
		criteria.add(Restrictions.eq("natDes", natDes));
		NaturezaDespesa naturezaDespesa = new NaturezaDespesa();
		naturezaDespesa = (NaturezaDespesa) criteria.uniqueResult();
		return naturezaDespesa;
	}

	@Override
	public List<NaturezaDespesa> retListNatDes() {
		Criteria criteria = getSession().createCriteria(NaturezaDespesa.class);
		return criteria.list();
	}
	

}
