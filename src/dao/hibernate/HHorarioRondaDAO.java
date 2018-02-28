package dao.hibernate;



import java.util.List;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import bean.HorarioRonda;
import dao.HorarioRondaDAO;

@Repository
@Transactional
public class HHorarioRondaDAO extends HGenericDAO<HorarioRonda> implements HorarioRondaDAO {
	public HHorarioRondaDAO(){
		super(HorarioRonda.class);
	}
	
	public HHorarioRondaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, HorarioRonda.class);
	}

	@Override
	public HorarioRonda retCodHorBd(int codHor) {
      Criteria criteria = getSession().createCriteria(HorarioRonda.class);
		criteria.add(Restrictions.eq("codhor", codHor));
		HorarioRonda horarioRonda= new HorarioRonda();
		horarioRonda = (HorarioRonda) criteria.uniqueResult();
		return horarioRonda;
	}

	@Override
	public List<HorarioRonda> retHorRonda(int codEsc) {
	   int numSeq = 1;
		HorarioRonda horarioRonda = new HorarioRonda();
		Query query = getSession().createQuery("select (hor.horarioRonda.codhor) as codhor, (hor.horarioRonda.deshor) as deshor from horarioescalaronda hor "+
      "where hor.codEsc.id=:codEsc order by hor.seqReg.seqReg asc");
		query.setInteger("codEsc", codEsc);
		query.setResultTransformer(Transformers.aliasToBean(HorarioRonda.class));
		List<HorarioRonda> list = query.list();
		return list;
	}

	
	
}
