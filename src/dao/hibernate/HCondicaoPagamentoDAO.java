package dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.CondicaoPagamento;
import dao.CondicaoPagamentoDAO;

@Repository
@Transactional
public class HCondicaoPagamentoDAO extends HGenericDAO<CondicaoPagamento> implements
		CondicaoPagamentoDAO {
	
	public HCondicaoPagamentoDAO(){
		super(CondicaoPagamento.class);
	}
	
	public HCondicaoPagamentoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, CondicaoPagamento.class);
	}

	@Override
	public List<CondicaoPagamento> listByExample(CondicaoPagamento condicaoPagamento)
			throws Exception {
		List<CondicaoPagamento> coList = new ArrayList<CondicaoPagamento>();
		try {
			Example exemplo = Example.create(condicaoPagamento).enableLike(MatchMode.ANYWHERE)
					.excludeZeroes().ignoreCase();
			Criteria criteria = getSession().createCriteria(CondicaoPagamento.class);
			criteria.addOrder(Order.asc("desCpg"));
			criteria.add(exemplo);
			coList = criteria.list();
		} catch (Exception e) {

		}
		return coList;
	}
}
