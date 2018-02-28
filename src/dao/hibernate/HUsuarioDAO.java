package dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Usuario;
import dao.UsuarioDAO;

@Repository
@Transactional
public class HUsuarioDAO extends HGenericDAO<Usuario> implements UsuarioDAO {
	
	public HUsuarioDAO(){
		super(Usuario.class);
	}

	public HUsuarioDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Usuario.class);
	}

	@Transactional
	public Usuario autentica(String codUsu, String senUsu) {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("codUsu", codUsu));
		criteria.add(Restrictions.eq("senUsu", senUsu));
		criteria.add(Restrictions.ne("afastamento.id", 7));
		criteria.setFetchMode("centrosPermitidos", FetchMode.JOIN);
		Usuario usuario = new Usuario();
		usuario = (Usuario) criteria.uniqueResult();
		return usuario;
	}

	@Override
	public Usuario loadByNumCad(int codEmp, long numCad) {
		Query query = getSession().createQuery("from Usuario where empresa.id=:emp and numCad=:cad");
		query.setInteger("emp", codEmp);
		query.setLong("cad", numCad);
		Usuario usuario = new Usuario();
		usuario = (Usuario) query.uniqueResult();
		return usuario;
	}

	@Override
	public List<Usuario> listReceptoresEmail(String tipUsu) {
		Query query = getSession().createQuery(
				"from Usuario where afastamento.id<>7 and recebeAviso=true and tipUsu=:tipo");
		query.setString("tipo", tipUsu);
		List<Usuario> usuarios = query.list();
		return usuarios;
	}

	@Override
	public List<Usuario> busca(Map<String, Object> parametros) throws Exception {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.addOrder(Order.asc("nomFun"));
		Iterator<String> iterator = parametros.keySet().iterator();
		Criterion criterion = null;
		while (iterator.hasNext()) {
			String campo = (String) iterator.next();
			Object valor = parametros.get(campo);
			if (campo.equals("id"))
				criterion = Restrictions.eq("id", valor);
			if (campo.equals("nomFun"))
				criterion = Restrictions.ilike("nomFun", (String) valor, MatchMode.START);
			if (campo.equals("empresa.id"))
				criterion = Restrictions.eq("empresa.id", valor);
			if (campo.equals("notFindDemitidos"))
				criterion = Restrictions.ne("afastamento.id", valor);
			if (campo.equals("usuario")) {
				Criteria criteria2 = getSession().createCriteria(Usuario.class)
						.add(Restrictions.eq("id", valor))
						.setFetchMode("centrosPermitidos", FetchMode.SELECT);
				Usuario usuario = (Usuario) criteria2.uniqueResult();
				criterion = Restrictions.in("centro", usuario.getCentrosPermitidos());
			}
			criteria.add(criterion);
		}
		List<Usuario> colList = criteria.list();
		return colList;
	}

	@Override
	public List<Usuario> listByExample(Usuario usuario) throws Exception {
		List<Usuario> resultado = new ArrayList<Usuario>();
		try {
			Example exemplo = Example.create(usuario).enableLike(MatchMode.ANYWHERE).excludeZeroes()
					.ignoreCase();
			exemplo.excludeProperty("conPou");
			exemplo.excludeProperty("extSegPes");
			exemplo.excludeProperty("extTnsVal");
			exemplo.excludeProperty("mesmoLocal");
			exemplo.excludeProperty("pgtChq");
			exemplo.excludeProperty("supervisor");
			exemplo.excludeProperty("temCNV");
			exemplo.excludeProperty("recebeAviso");
			exemplo.excludeProperty("pagSin");
			Criteria criteria = getSession().createCriteria(Usuario.class);
			if (usuario.getId() > 0) {
				criteria.add(Restrictions.eq("id", usuario.getId()));
			}
			criteria.add(exemplo);
			resultado = criteria.list();
		} catch (Exception e) {
			throw e;
		}
		return resultado;
	}

	@Override
	public Serializable insert(Usuario usuario) throws Exception {
		try {
			Map<String, Object> propriedades = new HashMap<String, Object>();
			propriedades.put("id", usuario.getId());
			propriedades.put("codUsu", usuario.getCodUsu());
			propriedades.put("senUsu", usuario.getSenUsu());
			propriedades.put("tipUsu", usuario.getTipUsu().name());
			propriedades.put("email", usuario.getEmail());
			propriedades.put("recebeAviso", usuario.isRecebeAviso());
			propriedades.put("gerenteId", usuario.getGerente().getId());
			SQLQuery query = getSession().createSQLQuery(
					"insert into Usuario(id, codUsu, senUsu, tipUsu, email, recebeAviso, gerenteId) "
							+ "values (:id,:codUsu,:senUsu,:tipUsu,:email,:recebeAviso, :gerenteId)");
			query.setProperties(propriedades);
			query.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		return usuario.getId();
	}

	@Override
	@Transactional
	public List<Usuario> listUsuariosAtivos() {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("afastamento.id", 1));
		criteria.add(Restrictions.eq("recebeAviso", true));
		return criteria.list();
	}

}
