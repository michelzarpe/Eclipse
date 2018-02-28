package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Deficiencia;
import dao.DeficienciaDAO;

@Repository
@Transactional
public class HDeficienciaDAO extends HGenericDAO<Deficiencia> implements DeficienciaDAO{
	public HDeficienciaDAO() {
		super(Deficiencia.class);
	}

	public HDeficienciaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Deficiencia.class);
	}
	@Override
	public Deficiencia retDefBd(int codDef) {
      Criteria criteria = getSession().createCriteria(Deficiencia.class);
		criteria.add(Restrictions.eq("codDef", codDef));
		Deficiencia deficiencia = new Deficiencia();
		deficiencia = (Deficiencia) criteria.uniqueResult();
		return deficiencia;
	}

	@Override
	public List<Deficiencia> retListDefic() {

		Criteria criteria = getSession().createCriteria(Deficiencia .class);
			return criteria.list();
	}

}
