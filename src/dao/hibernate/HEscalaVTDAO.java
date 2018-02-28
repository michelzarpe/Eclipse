package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import bean.EscalaVT;
import dao.EscalaVTDAO;

@Repository
@Transactional
public class HEscalaVTDAO  extends HGenericDAO<EscalaVT> implements EscalaVTDAO {
	
	public HEscalaVTDAO() {
		super(EscalaVT.class);
	}

	public HEscalaVTDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, EscalaVT.class);
	}

	@Override
	public EscalaVT retEscVt(int escVt) {
      Criteria criteria = getSession().createCriteria(EscalaVT.class);
		criteria.add(Restrictions.eq("escVt", escVt));
		EscalaVT escalaVT = new EscalaVT();
		escalaVT = (EscalaVT) criteria.uniqueResult();
		return escalaVT;
	}

	@Override
	public List<EscalaVT> retListEscVt() {
		Criteria criteria = getSession().createCriteria(EscalaVT.class);
		return criteria.list();
	}

}
