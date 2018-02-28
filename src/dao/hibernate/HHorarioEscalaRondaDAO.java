package dao.hibernate;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import bean.HorarioEscalaRonda;
import dao.HorarioEscalaRondaDAO;

@Repository
@Transactional
public class HHorarioEscalaRondaDAO extends HGenericDAO<HorarioEscalaRonda>
		implements HorarioEscalaRondaDAO {

	public HHorarioEscalaRondaDAO() {
		super(HorarioEscalaRonda.class);
	}

	public HHorarioEscalaRondaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, HorarioEscalaRonda.class);
	}

	public void removerHorariosEscalaRonda() {
		String hql = "delete from horarioescalaronda";
		Query query = getSession().createQuery(hql);
   	query.executeUpdate();

	}

}
