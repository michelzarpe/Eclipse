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
import bean.EscalaHorarioRondaMZ;
import dao.EscalaHorarioRondaMZDAO;
import org.hibernate.Query;

@Repository
@Transactional
public class HEscalaHorarioRondaMZDAO extends HGenericDAO<EscalaHorarioRondaMZ> implements EscalaHorarioRondaMZDAO {

	public HEscalaHorarioRondaMZDAO() {
		super(EscalaHorarioRondaMZ.class);
	}

	public HEscalaHorarioRondaMZDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, EscalaHorarioRondaMZ.class);
	}

	public EscalaHorarioRondaMZ retEscBd(int codEsc) {
		Criteria criteria = getSession().createCriteria(EscalaHorarioRondaMZ.class);
		criteria.add(Restrictions.eq("id", codEsc));
		EscalaHorarioRondaMZ escalaHorarioRondaMZ = new EscalaHorarioRondaMZ();
		escalaHorarioRondaMZ = (EscalaHorarioRondaMZ) criteria.uniqueResult();
		return escalaHorarioRondaMZ;
	}

	@Override
	public List<EscalaHorarioRondaMZ> retEscClasse(int claesc) {
		Query query = getSession().createQuery("from escalaHorarioRondaMZ where classeEscalaRonda.claEsc=:claesc");
		query.setInteger("claesc", claesc);
		List<EscalaHorarioRondaMZ> list = query.list();
		return list;
	}

	@Override
	public List<EscalaHorarioRondaMZ> busca(Map<String, Object> parametros) {
		Criteria criteria = getSession().createCriteria(EscalaHorarioRondaMZ.class);
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;
		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			
			if (campo.equals("id"))
				criterion = Restrictions.eq("id", valor);// campos tabela, valor filtro
	
			if (campo.equals("nomesc"))
				criterion = Restrictions.ilike("nomesc", (String) valor, MatchMode.ANYWHERE);
               	
			if (campo.equals("horSem"))
				criterion = Restrictions.eq("horSem", valor); // campos tabela, valor filtro
			
			if (campo.equals("horMes"))
				criterion = Restrictions.eq("horMes", valor); // campos tabela, valor filtro

			if (campo.equals("claEsc"))     //FK_claesc
				criterion = Restrictions.eq("classeEscalaRonda.claEsc", valor); // campos tabela, valor filtro
			

			criteria.add(criterion);
		}
		criteria.addOrder(Order.asc("id"));
		List<EscalaHorarioRondaMZ> listEsc = criteria.list();
		return listEsc;
	}

}