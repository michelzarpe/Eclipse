package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Deficiencia;
import bean.PostoTrabalho;
import dao.PostoTrabalhoDAO;



@Repository
@Transactional
public class HPostoTrabalhoDAO extends HGenericDAO<PostoTrabalho> implements PostoTrabalhoDAO {

	public HPostoTrabalhoDAO() {
		super(PostoTrabalho.class);
	}

	public HPostoTrabalhoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, PostoTrabalho.class);
	}
	
	@Override
	public PostoTrabalho retPosTraBd(String posTra) {
      Criteria criteria = getSession().createCriteria(PostoTrabalho.class);
		criteria.add(Restrictions.eq("posTra", posTra));
		PostoTrabalho postoTrabalho = new PostoTrabalho();
		postoTrabalho = (PostoTrabalho) criteria.uniqueResult();
		return postoTrabalho;
	}

	@Override
	public List<PostoTrabalho> retListPosTra() {
		Criteria criteria = getSession().createCriteria(PostoTrabalho.class);
		return criteria.list();
	}
	
	@Override
	public void removerPostoTrabalho() {
		String hql = "delete from postoTrabalho";
		Query query = getSession().createQuery(hql);
   	query.executeUpdate();

	}
}
