package dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Caracteristica;
import bean.Uniforme;
import dao.UniformeDAO;

@Repository
@Transactional
public class HUniformeDAO extends HGenericDAO<Uniforme> implements UniformeDAO {
	
	public HUniformeDAO(){
		super(Uniforme.class);
	}

	public HUniformeDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Uniforme.class);
	}

	public List<Uniforme> listAllByTipoEPI(int tipoEPI, String sitEpi) {
		Query query = getSession()
				.createQuery("from Uniforme where tipoEPI.id=:tipoEPI and sitEpi=:sitEpi");
		query.setInteger("tipoEPI", tipoEPI);
		query.setString("sitEpi", sitEpi);
		List<Uniforme> uniformes = query.list();
		return uniformes;
	}

	@Override
	public List<Uniforme> listByExample(Uniforme uniforme) throws Exception {
		List<Uniforme> resultado = new ArrayList<Uniforme>();
		try {
			Example exemplo = Example.create(uniforme).enableLike(MatchMode.ANYWHERE).excludeZeroes()
					.ignoreCase();
			Criteria criteria = getSession().createCriteria(Uniforme.class);

			if (uniforme.getId() > 0) {
				criteria.add(Restrictions.eq("id", uniforme.getId()));
			}
			Example exemploTipoEPI = Example.create(uniforme.getTipoEPI())
					.enableLike(MatchMode.ANYWHERE).excludeZeroes().ignoreCase();

			criteria.setFetchMode("tipoEPI", FetchMode.SELECT);
			criteria.add(exemplo).createCriteria("tipoEPI").add(exemploTipoEPI);
			if (uniforme.getTipoEPI().getId() > 0) {
				criteria.add(Restrictions.eq("tipoEPI.id", uniforme.getTipoEPI().getId()));
			}
			resultado = criteria.list();
		} catch (Exception e) {
			logger.error("Erro carregando lista de objetos via exemplo "
					+ this.objClass.getSimpleName() + ". Causa: " + e.getMessage());
		}
		return resultado;
	}

	@Override
	/**
	 * Retorna apenas os uniformes definidos pela característica do segmento(ADM, VIG, ASS) e também pela
	 * característica de sexo (FEM, MSC), e nesse caso sempre adiciona os UNISEX
	 */
	public List<Uniforme> listByCaracteristica(int tipoEpi, String sitEpi,
			Caracteristica caracteristicasSexo, Caracteristica caracteristicaSegmento) {

		Criteria criteria = getSession().createCriteria(Uniforme.class, "u");
		criteria.add(Restrictions.eq("tipoEPI.id", tipoEpi));
		criteria.add(Restrictions.eq("sitEpi", sitEpi));
		criteria.createAlias("caracteristicas", "caracteristicas");
		criteria
				.add(Restrictions
						.sqlRestriction("{alias}.id in(select Uniforme_id from Uniforme_Caracteristica where caracteristicas_id='"
								+ caracteristicaSegmento.getId()
								+ "' and Uniforme_id in(select Uniforme_id from Uniforme_Caracteristica where caracteristicas_id='"
								+ caracteristicasSexo.getId() + "' or caracteristicas_id='UNI' ))"));
		return criteria.list();
	}
}
