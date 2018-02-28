package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.TurmaHorarioRonda;
import dao.TurmaHorarioRondaDAO;

@Repository
@Transactional
public class HTurmaHorarioRondaDAO  extends HGenericDAO<TurmaHorarioRonda> implements TurmaHorarioRondaDAO{

	public HTurmaHorarioRondaDAO(){
		super(TurmaHorarioRonda.class);
	}
	
	public HTurmaHorarioRondaDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, TurmaHorarioRonda.class);
	}
}
