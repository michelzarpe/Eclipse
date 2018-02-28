package dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import bean.ClasseEscalaRonda;
import bean.Usuario;
import dao.ClasseEscalaRondaDAO;

@Repository
@Transactional
public class HClasseEscalaRondaDAO extends HGenericDAO<ClasseEscalaRonda>
		implements ClasseEscalaRondaDAO {

	public HClasseEscalaRondaDAO() {
		super(ClasseEscalaRonda.class);
	}

	public HClasseEscalaRondaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, ClasseEscalaRonda.class);
	}
	

	@Transactional
	public ClasseEscalaRonda retClaEscBd(int claEsc) {
		Criteria criteria = getSession().createCriteria(ClasseEscalaRonda.class);
		criteria.add(Restrictions.eq("claEsc", claEsc));
		ClasseEscalaRonda classeEscalaRonda = new ClasseEscalaRonda();
		classeEscalaRonda = (ClasseEscalaRonda) criteria.uniqueResult();
		return classeEscalaRonda;
	}
}
