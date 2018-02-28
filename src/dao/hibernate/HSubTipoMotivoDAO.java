package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.SubTipoMotivo;
import dao.SubTipoMotivoDAO;

@Repository
@Transactional
public class HSubTipoMotivoDAO extends HGenericDAO<SubTipoMotivo> implements SubTipoMotivoDAO {
	
	public HSubTipoMotivoDAO(){
		super(SubTipoMotivo.class);
	}
	
	public HSubTipoMotivoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, SubTipoMotivo.class);
	}

	@Override
	public List<SubTipoMotivo> listAllByMotivo(int motivoId) {
		Criteria criteria = getSession().createCriteria(SubTipoMotivo.class).add(
				Restrictions.eq("motivo.id", motivoId));
		return criteria.list();
	}

}
