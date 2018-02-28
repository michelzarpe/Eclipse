package dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Centro;
import bean.Cliente;
import bean.Filter;
import bean.Produto;
import bean.RequisicaoEstoque;
import bean.Usuario;
import bean.Produto.TipoProduto;
import bean.RequisicaoEstoque.Setor;
import bean.RequisicaoEstoque.Situacao;
import bean.Usuario.TipoUsuario;
import dao.RequisicaoEstoqueDAO;

@Repository
@Transactional
public class HRequisicaoEstoqueDAO extends HGenericDAO<RequisicaoEstoque> implements
		RequisicaoEstoqueDAO {
	
	public HRequisicaoEstoqueDAO(){
		super(RequisicaoEstoque.class);
	}
	
	public HRequisicaoEstoqueDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, RequisicaoEstoque.class);
	}

	/**
	 * Retorna a quantidade aprovada do item na competência anterior, para o usuário solicitante.
	 */
	public double buscaQtdAprMesAnt(RequisicaoEstoque requisicao) {
		int mesCmp = 0;
		int anoCmp = 0;
		try {
			mesCmp = Integer.valueOf(requisicao.getCmpReq().substring(0, 2));
			anoCmp = Integer.valueOf(requisicao.getCmpReq().substring(3, 7));
		} catch (NumberFormatException e) {
			// TODO: handle exception~
			System.err.println("Erro na conversão da competência string para mês e ano int.");
			throw e;
		}

		Calendar cal = Calendar.getInstance();
		cal.set(anoCmp, mesCmp, 20);
		cal.add(Calendar.MONTH, -2);
		SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");

		String cmpAnt = format.format(cal.getTime());
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpAnt));
		criteria.add(Restrictions.eq("produto", requisicao.getProduto()));
		criteria.add(Restrictions.eq("usuSol", requisicao.getUsuSol()));
		criteria.add(Restrictions.eq("centro", requisicao.getCentro()));
		criteria.setProjection(Projections.property("qtdApr"));
		Double qtdAprMesAnt = (Double) criteria.uniqueResult();
		if (qtdAprMesAnt != null)
			return qtdAprMesAnt;
		else
			return 0.00;
	}

	public List<RequisicaoEstoque> listByExample(RequisicaoEstoque requisicaoEstoque,
			TipoProduto tipo, Usuario solicitante) throws Exception {
		List<RequisicaoEstoque> requisicoes = new ArrayList<RequisicaoEstoque>();
		Example exemplo = Example.create(requisicaoEstoque).enableLike(MatchMode.ANYWHERE)
				.excludeZeroes().ignoreCase();
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		if (requisicaoEstoque.getId() > 0) {
			criteria.add(Restrictions.eq("id", requisicaoEstoque.getId()));
		}
		Produto produto = new Produto();
		if (requisicaoEstoque.getProduto() == null) {
			produto.setTipPro(tipo);
			produto.setRequisitavel(true);
			requisicaoEstoque.setProduto(produto);
		} else {
			produto = requisicaoEstoque.getProduto();
		}
		Example exemploProduto = Example.create(requisicaoEstoque.getProduto())
				.enableLike(MatchMode.ANYWHERE).excludeZeroes().ignoreCase();
		criteria.add(exemplo).createCriteria("produto").add(exemploProduto)
				.add(Restrictions.eq("tipPro", tipo));
		criteria.createCriteria("usuSol").add(
				Restrictions.in("centro", solicitante.getCentrosPermitidos()));
		criteria.addOrder(Order.asc("centro.id"));
		criteria.addOrder(Order.asc("setor"));
		requisicoes = criteria.list();
		return requisicoes;
	}

	@Override
	/*
	 * * retorna requisições ainda não processadas trazendo apenas do tipo de produto especificado em tipoProduto
	 */
	public List<RequisicaoEstoque> listPendentes(TipoProduto tipoProduto) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("numEme", 0));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", tipoProduto));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listPendentes(TipoProduto tipoProduto, Cliente cliente) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("numEme", 0));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", tipoProduto));
		if (cliente != null)
			criteria.createCriteria("cliente").add(Restrictions.eq("id", cliente.getId()));
		return criteria.list();
	}

	/**
	 * Retorna os items pendentes de acordo com o tipo de produto e a situação Se o usuário for administrador, mostra de todos os usuários. Se não for
	 * administrador, mostra somente os lançados por ele
	 */
	public List<RequisicaoEstoque> listPendentes(TipoProduto tipoProduto, List<Situacao> situacoes,
			Usuario usuSol, String sort, String dir, List<Filter> filtros) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.in("sitEme", situacoes));
		criteria.createAlias("produto", "produto");
		if (!(usuSol.getTipUsu().equals(TipoUsuario.AAL))
				&& !(usuSol.getTipUsu().equals(TipoUsuario.ASI)))
			criteria.add(Restrictions.eq("usuSol", usuSol));
		criteria.add(Restrictions.eq("produto.tipPro", tipoProduto));
		if (filtros.size() > 0) {
			for (Iterator<Filter> iterator = filtros.iterator(); iterator.hasNext();) {
				Filter filtro = (Filter) iterator.next();
				if (filtro.getField().startsWith("centro")) {
					criteria.createAlias("centro", "centro");
					criteria
							.add(Restrictions.like(filtro.getField(), filtro.getValue(), MatchMode.START));
				} else if (filtro.getField().startsWith("codPro")) {
					criteria.add(Restrictions.ilike("produto.id.codPro", filtro.getValue(),
							MatchMode.START));
				} else if (filtro.getField().startsWith("produto")) {
					criteria.add(Restrictions.ilike(filtro.getField(), filtro.getValue(),
							MatchMode.START));
				} else if (filtro.getField().startsWith("setor")) {
					String setor = filtro.getValue().toUpperCase();
					criteria.add(Restrictions.eq("setor", Setor.valueOf(setor)));
				}
			}
		}
		if ((dir != null) && (sort != null)) {
			if (dir.equals("ASC")) {
				criteria.addOrder(Order.asc(sort));
			} else if (dir.equals("DESC")) {
				criteria.addOrder(Order.desc(sort));
			}
		}
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listPendentes(TipoProduto tipoProduto, Situacao situacao) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("sitEme", situacao));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", tipoProduto))
				.addOrder(Order.asc("desPro"));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqExpByCmp(Usuario usuario, String cmpReq) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.ME));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		criteria.addOrder(Order.asc("centro.id"));
		criteria.addOrder(Order.asc("setor"));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqExpByCmp(Usuario usuario, String cmpReq, Setor setor) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.add(Restrictions.eq("setor", setor));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.ME))
				.addOrder(Order.asc("desPro"));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqMatInsByCmp(Usuario usuario, String cmpReq) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.MI));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		criteria.addOrder(Order.asc("centro.id"));
		criteria.addOrder(Order.asc("setor"));
		return criteria.list();
	}

	public List<RequisicaoEstoque> listReqMatInsByCmp(Usuario usuario, String cmpReq, Setor setor) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.add(Restrictions.eq("setor", setor));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.MI))
				.addOrder(Order.asc("desPro"));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqMatLimByCmp(Usuario usuario, String cmpReq) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.LI));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		criteria.addOrder(Order.asc("centro.id"));
		criteria.addOrder(Order.asc("setor"));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqMatLimByCmp(Usuario usuario, String cmpReq, Setor setor) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.add(Restrictions.eq("setor", setor));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.LI))
				.addOrder(Order.asc("desPro"));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqExpByCmp(Usuario usuario, String cmpReq, Setor setor,
			Centro centro) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.add(Restrictions.eq("setor", setor));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.ME))
				.addOrder(Order.asc("desPro"));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		criteria.createCriteria("centro").add(Restrictions.eq("id", centro.getId()));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqMatInsByCmp(Usuario usuario, String cmpReq, Setor setor,
			Centro centro) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.add(Restrictions.eq("setor", setor));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.MI))
				.addOrder(Order.asc("desPro"));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		criteria.createCriteria("centro").add(Restrictions.eq("id", centro.getId()));
		return criteria.list();
	}

	@Override
	public List<RequisicaoEstoque> listReqMatLimByCmp(Usuario usuario, String cmpReq, Setor setor,
			Centro centro) {
		Criteria criteria = getSession().createCriteria(RequisicaoEstoque.class);
		criteria.add(Restrictions.eq("cmpReq", cmpReq));
		criteria.createCriteria("produto").add(Restrictions.eq("tipPro", TipoProduto.LI));
		criteria.createCriteria("usuSol").add(Restrictions.eq("centro", usuario.getCentro()));
		criteria.createCriteria("centro").add(Restrictions.eq("id", centro.getId()));
		return criteria.list();
	}

}
