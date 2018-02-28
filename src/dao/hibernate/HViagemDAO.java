package dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Colaborador;
import bean.Usuario;
import bean.Viagem;
import dao.ViagemDAO;

@Repository
@Transactional
public class HViagemDAO extends HGenericDAO<Viagem> implements ViagemDAO {
	
	public HViagemDAO(){
		super(Viagem.class);
	}

	public HViagemDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Viagem.class);
	}

	@Override
	public List<Viagem> listByExample(Viagem viagem) throws Exception {
		List<Viagem> viagens = new ArrayList<Viagem>();
		try {
			Example exemplo = Example.create(viagem).enableLike(MatchMode.ANYWHERE).excludeZeroes()
					.ignoreCase();
			Criteria criteria = getSession().createCriteria(Viagem.class);

			if (viagem.getId() > 0) {
				criteria.add(Restrictions.eq("id", viagem.getId()));
			}
			/** Busca em colaboradores **/
			if (viagem.getColaborador() != null) {
				Example exCol = Example.create(viagem.getColaborador()).enableLike(
						MatchMode.ANYWHERE).excludeZeroes().ignoreCase();
				exCol.excludeProperty("conPou");
				exCol.excludeProperty("mesmoLocal");
				exCol.excludeProperty("pgtChq");
				exCol.excludeProperty("supervisor");
				exCol.excludeProperty("pagSin");
				exCol.excludeProperty("temCNV");
				exCol.excludeProperty("extSegPes");
				exCol.excludeProperty("extTnsVal");

				criteria.setFetchMode("colaborador", FetchMode.SELECT);

				criteria.add(exemplo).createCriteria("colaborador").setCacheable(true).add(exCol);

				// ** Buscar pelo id do colaborador **/
				if (viagem.getColaborador().getId() > 0) {
					criteria
							.add(Restrictions.eq("colaborador.id", viagem.getColaborador().getId()));
				}
			}
			criteria.setCacheable(true);
			viagens = criteria.list();
		} catch (Exception e) {
			logger.error("Erro carregando lista de objetos via exemplo "
					+ this.objClass.getSimpleName() + ". Causa: " + e.getMessage());
			throw e;
		}
		return viagens;
	}

	@Override
	public List<Viagem> getViagensAbertas(Colaborador colaborador, Usuario usuario) {
		Criteria criteria = getSession().createCriteria(Viagem.class);
		criteria.createCriteria("colaborador").add(
				Restrictions.eq("id", colaborador.getId())).add(
				Restrictions.in("centro", usuario.getCentrosPermitidos()));
		Criterion critDatAce =Restrictions.isNull("datAce");
		criteria.createCriteria("adiantamentos").add(critDatAce);
		criteria.list();
		Criteria criteria2 = getSession().createCriteria(Viagem.class);
		criteria2.createCriteria("colaborador").add(
				Restrictions.eq("id", colaborador.getId())).add(
				Restrictions.in("centro", usuario.getCentrosPermitidos()));
		criteria2.createCriteria("despesas").add(critDatAce);
		criteria2.list();
		Set<Viagem> viagensSet = new HashSet<Viagem>();
		viagensSet.addAll(criteria.list());
		viagensSet.addAll(criteria2.list());
		List<Viagem> viagens = new ArrayList<Viagem>();
		for (Viagem viagem : viagensSet) {
			viagens.add(viagem);
		}
		return viagens;
	}
}
