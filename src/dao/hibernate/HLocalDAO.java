package dao.hibernate;

import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Local;
import dao.LocalDAO;

@Repository
@Transactional
public class HLocalDAO extends HGenericDAO<Local> implements LocalDAO {
	public final Date data = new GregorianCalendar(2080, 06, 04).getTime();
	
	public HLocalDAO(){
		super(Local.class);
	}

	public HLocalDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Local.class);
	}

	public Local loadByNumLoc(int tabOrg, int numLoc) {
		Criteria criteria = getSession().createCriteria(Local.class);
		criteria.add(Restrictions.eq("tabOrg", tabOrg));
		criteria.add(Restrictions.eq("numLoc", numLoc));
		Local local = new Local();
		try {
			local = (Local) criteria.uniqueResult();
		} catch (Exception e) {
			System.err.println("Erro carregando Local: " + numLoc + " de tabela " + tabOrg
					+ ". Erro: " + e.getMessage());
		}
		return local;
	}

}
