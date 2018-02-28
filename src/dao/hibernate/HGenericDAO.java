package dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.BaseDAO;

@Repository
@Transactional
public class HGenericDAO<T> implements BaseDAO<T> {
	protected Class<T>		objClass	= null;
	static Logger				logger	= Logger.getLogger(HGenericDAO.class);
	@Autowired
	private SessionFactory	sessionFactory;
	
	public HGenericDAO(){
		
	}

	public HGenericDAO(Class<T> objClass) {
		this.setObjClass(objClass);
	}

	public HGenericDAO(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public HGenericDAO(final SessionFactory sessionFactory, Class<T> objClass) {
		this.setObjClass(objClass);
		this.sessionFactory = sessionFactory;
		System.out.println("Session factory usada : " + sessionFactory);
	}

	public void atualizar(Collection<T> objs) throws Exception {
		try {
			for (Iterator<T> iterator = objs.iterator(); iterator.hasNext();) {
				T obj = (T) iterator.next();
				getSession().update(obj);
			}
		} catch (Exception e) {
			logger.error("Erro atualizando coleção de objetos " + objs.getClass());
			throw e;
		}
	}

	public void delete(T obj) throws Exception {
		try {
			getSession().delete(obj);
		} catch (Exception e) {
			logger.error("Erro excluindo objeto " + this.objClass.getSimpleName() + " Causa: "
					+ e.getMessage());
			throw e;
		} finally {
			getSession().flush();
			getSession().clear();
		}
	}

	public Class<T> getObjClass() {
		return objClass;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void importar(List<T> objs) throws Exception {
		for (Iterator<T> iterator = objs.iterator(); iterator.hasNext();) {
			T obj = (T) iterator.next();
			try {
				getSession().saveOrUpdate(obj);
				getSession().flush();
			} catch (Exception e) {
				logger.error("Não importou objeto " + obj.toString());
				throw e;
			} finally {
				getSession().flush();
				getSession().clear();
			}
		}
	}

	public Serializable insert(T obj) throws Exception {
		Serializable idGerado = 0;
		try {
			idGerado = getSession().save(obj);
			getSession().flush();
		} catch (Exception e) {
			logger.error("Erro inserindo objeto " + this.objClass.getSimpleName() + ". Causa: "
					+ e.getMessage());
			throw e;
		} finally {
			getSession().clear();
		}
		return idGerado;
	}

	public List<T> listAll(String orderBy) throws Exception {
		List<T> result = new ArrayList<T>();
		try {
			Criteria criteria = getSession().createCriteria(this.objClass);
			criteria.addOrder(Order.asc(orderBy));
			criteria.setCacheable(true);
			result = criteria.list();
			logger.info("Todos os objetos " + this.objClass.getSimpleName() + " foram carregados");
		} catch (Exception e) {
			logger.error("Erro carregando lista de objetos " + this.objClass.getSimpleName()
					+ ". Causa: " + e.getMessage());
		}
		return result;
	}

	@Override
	public List<T> listByExample(T obj) throws Exception {
		List<T> resultado = new ArrayList<T>();
		try {
			Example exemplo = Example.create(obj).enableLike(MatchMode.ANYWHERE).excludeZeroes()
					.ignoreCase();
			Criteria criteria = getSession().createCriteria(this.objClass);
			criteria.add(exemplo);
			resultado = criteria.list();
		} catch (Exception e) {
			logger.error("Erro carregando lista de objetos via exemplo "
					+ this.objClass.getSimpleName() + ". Causa: " + e.getMessage());
		}
		return resultado;
	}

	@Transactional
	public T loadById(Serializable id) throws Exception {
		try {
			T objetoRetornado = (T) getSession().load(this.objClass, id);
			return objetoRetornado;
		} catch (Exception e) {
			logger.error("Erro ao tentar carregar objeto " + this.objClass.getName() + " com id " + id
					+ ":" + e.getMessage());
			throw e;
		}
	}

	@Override
	public void merge(T obj) throws Exception {
		try {
			getSession().merge(obj);
		} catch (Exception e) {
			throw e;
		}
	}

	public void saveOrUpdate(T obj) throws Exception {
		try {
			getSession().saveOrUpdate(obj);
			
		} catch (Exception e) {
			logger.error("Erro salvando objeto " + this.objClass.getName() + ". Causa: "
					+ e.getLocalizedMessage());
			throw e;
		} finally {
			getSession().flush();
			getSession().clear();
		}
	}

	public void setObjClass(Class<T> objClass) {
		this.objClass = objClass;
	}

	public void update(T obj) throws Exception {
		try {
			getSession().update(obj);
		} catch (Exception e) {
			logger.error("Erro atualizando objeto" + this.objClass.getName());
			throw e;
		}
	}

	public void updateWithCommit(T obj) throws Exception {
		try {
			getSession().update(obj);
			getSession().getTransaction().commit();
		} catch (Exception e) {
			logger.error("Erro atualizando objeto" + this.objClass.getName());
			getSession().getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void updateWithFlush(T obj) throws Exception {
		try {
			getSession().update(obj);
		} catch (Exception e) {
			logger.error("Erro atualizando objeto" + this.objClass.getName());
			throw e;
		} finally {
			try {
				getSession().flush();
				getSession().clear();
			} catch (Exception e) {
				throw e;
			}
		}
	}

	@Override
	public Date getDataAtual() {
		Date dataAtual = new Date();
		SQLQuery query = getSession().createSQLQuery("select current_timestamp");
		dataAtual = (Date) query.uniqueResult();
		return dataAtual;
	}
}
