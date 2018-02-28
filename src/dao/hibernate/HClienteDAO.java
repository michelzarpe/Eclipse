package dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Cliente;
import dao.ClienteDAO;

@Repository
@Transactional
public class HClienteDAO extends HGenericDAO<Cliente> implements ClienteDAO {
	
	public HClienteDAO(){
		super(Cliente.class);
	}

	public HClienteDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Cliente.class);
	}

	@Override
	public List<Cliente> listAllByEmp(int idEmpresa) {
		Criteria criteria = getSession().createCriteria(Cliente.class);
		criteria.add(Restrictions.eq("empresa.id", idEmpresa));
		criteria.addOrder(Order.asc("razSoc"));
		criteria.add(Restrictions.isNotEmpty("colaboradores"));
		List<Cliente> clientes = criteria.list();
		return clientes;
	}

	@Override
	public List<Cliente> busca(Map<String, Object> parametros) {
		Criteria criteria = getSession().createCriteria(Cliente.class);
		criteria.addOrder(Order.asc("razSoc"));
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;
		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			if (campo.equals("razSoc"))
				criterion = Restrictions.ilike("razSoc", (String) valor, MatchMode.ANYWHERE);
			if (campo.equals("empresaId"))
				criterion = Restrictions.eq("id.empresaId", Integer.parseInt((String) valor));
			criteria.add(criterion);
		}
		List<Cliente> clientes = criteria.list();
		return clientes;
	}

	@Override
	public List<Cliente> listByExample(Cliente cliente) throws Exception {
		List<Cliente> resultado = new ArrayList<Cliente>();
		try {
			Example exemplo = Example.create(cliente).enableLike(MatchMode.ANYWHERE).excludeZeroes()
					.ignoreCase();
			Criteria criteria = getSession().createCriteria(Cliente.class);

			Example exemploEmp = Example.create(cliente.getEmpresa()).enableLike(MatchMode.ANYWHERE)
					.excludeZeroes().ignoreCase();

			criteria.add(exemplo).createCriteria("empresa").add(exemploEmp);
			if (cliente.getEmpresa().getId() > 0) {
				criteria.add(Restrictions.eq("empresa.id", cliente.getEmpresa().getId()));
			}
			criteria.addOrder(Order.asc("razSoc"));
			resultado = criteria.list();
		} catch (Exception e) {
			logger.error("Erro carregando lista de objetos via exemplo "
					+ this.objClass.getSimpleName() + ". Causa: " + e.getMessage());
		}
		return resultado;

	}
}
