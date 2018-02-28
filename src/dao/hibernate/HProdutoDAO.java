package dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Produto;
import bean.Produto.Situacao;
import bean.Produto.TipoProduto;
import dao.ProdutoDAO;

@Repository
@Transactional
public class HProdutoDAO extends HGenericDAO<Produto> implements ProdutoDAO {
	
	public HProdutoDAO(){
		super(Produto.class);
	}

	public HProdutoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Produto.class);
	}

	@Override
	public List<Produto> listAllByTipo(TipoProduto tipo) {
		Criteria criteria = getSession().createCriteria(Produto.class);
		criteria.add(Restrictions.eq("tipPro", tipo));
		criteria.add(Restrictions.eq("sitPro", Situacao.A));
		return criteria.list();
	}

	@Override
	public List<Produto> listByExample(Produto produto) throws Exception {
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			Example exemplo = Example.create(produto).enableLike(MatchMode.ANYWHERE)
					.excludeZeroes().ignoreCase();
			Criteria criteria = getSession().createCriteria(Produto.class);
			Example exemploEmp = Example.create(produto.getEmpresa())
					.enableLike(MatchMode.ANYWHERE).excludeZeroes().ignoreCase();
			criteria.add(exemplo).createCriteria("empresa").add(exemploEmp);
			if (produto.getEmpresa().getId() > 0) {
				criteria.add(Restrictions.eq("empresa.id", produto.getEmpresa().getId()));
			}
			if ((produto.getId().getCodPro() != null) && (!produto.getId().getCodPro().equals(""))) {
				criteria.add(Restrictions.eq("id", produto.getId()));
			}
			produtos = criteria.list();
		} catch (Exception e) {
			logger.error("Erro carregando lista de objetos via exemplo "
					+ this.objClass.getSimpleName() + ". Causa: " + e.getMessage());
		}
		return produtos;
	}
}
