package dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import bean.SequenciaRegistro;
import dao.SequenciaRegistroDAO;

@Repository
@Transactional
public class HSequenciaRegistroDAO extends HGenericDAO<SequenciaRegistro>
		implements SequenciaRegistroDAO {
	public HSequenciaRegistroDAO() {
		super(SequenciaRegistro.class);
	}

	public HSequenciaRegistroDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, SequenciaRegistro.class);
	}

	@Override
	public SequenciaRegistro retSeqRegBd(int seqReg) {
		Criteria criteria = getSession().createCriteria(SequenciaRegistro.class);
		criteria.add(Restrictions.eq("seqReg", seqReg));
		SequenciaRegistro sequenciaRegistro = new SequenciaRegistro();
		sequenciaRegistro = (SequenciaRegistro) criteria.uniqueResult();
		return sequenciaRegistro;
		
	}
}
