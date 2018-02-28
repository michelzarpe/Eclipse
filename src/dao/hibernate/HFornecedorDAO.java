package dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Fornecedor;
import dao.FornecedorDAO;

@Repository
@Transactional
public class HFornecedorDAO extends HGenericDAO<Fornecedor> implements FornecedorDAO {
	
	public HFornecedorDAO(){
		super(Fornecedor.class);
	}

	public HFornecedorDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Fornecedor.class);
	}

}
