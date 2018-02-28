package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.GrauInstrucao;
import dao.GrauInstrucaoDAO;

@Repository
@Transactional
public class HGrauInstrucaoDAO extends HGenericDAO<GrauInstrucao> implements GrauInstrucaoDAO {
	
	public HGrauInstrucaoDAO(){
		super(GrauInstrucao.class);
	}
	
	public HGrauInstrucaoDAO(final SessionFactory sessionFactory){
		super(sessionFactory, GrauInstrucao.class);
	}
}
