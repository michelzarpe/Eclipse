package dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Documento;
import dao.DocumentoDAO;

@Repository
@Transactional
public class HDocumentoDAO extends HGenericDAO<Documento> implements DocumentoDAO {
	
	public HDocumentoDAO(){
		super(Documento.class);
	}
		
	public HDocumentoDAO(final SessionFactory sessionFactory) {
		super(sessionFactory, Documento.class);
	}

	/** retorna todos os documentos criados no nível raiz **/
	
	/** 
	 * BUSCAR APENAS QUANDO O PAI FOR NULO. USANDO A PROPRIEDADE ID COMO NULLA NÃO FUNCIONA 
	 */
	@Override
	public List<Documento> listNivelRaiz() {
		Criteria criteria = getSession().createCriteria(Documento.class);
		criteria.add(Restrictions.isNull("docPai"));
		List<Documento> documentos = criteria.list();
		return documentos;
	}
	
}
