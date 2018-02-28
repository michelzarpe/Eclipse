package dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.SolicitacaoCompra;
import dao.SolicitacaoCompraDAO;

@Repository
@Transactional
public class HSolicitacaoCompraDAO extends HGenericDAO<SolicitacaoCompra> implements
		SolicitacaoCompraDAO {
	
	public HSolicitacaoCompraDAO(){
		super(SolicitacaoCompra.class);
	}

	public HSolicitacaoCompraDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, SolicitacaoCompra.class);
	}

	public List<SolicitacaoCompra> listByExample(SolicitacaoCompra solicitacaoCompra)
			throws Exception {
		List<SolicitacaoCompra> requisicoes = new ArrayList<SolicitacaoCompra>();
		Example exemplo = Example.create(solicitacaoCompra).enableLike(MatchMode.ANYWHERE)
				.excludeZeroes().ignoreCase();
		exemplo.excludeProperty("datSol");
		Criteria criteria = getSession().createCriteria(SolicitacaoCompra.class);
		if (solicitacaoCompra.getId() > 0) {
			criteria.add(Restrictions.eq("id", solicitacaoCompra.getId()));
		}
		Date dataSolIni = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(solicitacaoCompra.getDatSol());
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		dataSolIni.setTime(cal.getTimeInMillis());
		
		Date dataSolFin = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(solicitacaoCompra.getDatSol());
		cal2.set(Calendar.HOUR, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		cal2.set(Calendar.MILLISECOND, 0);
		dataSolFin.setTime(cal2.getTimeInMillis());
		
		criteria.add(Restrictions.between("datSol", dataSolIni, dataSolFin));
		criteria.add(exemplo);
		requisicoes = criteria.list();
		return requisicoes;
	}
}
