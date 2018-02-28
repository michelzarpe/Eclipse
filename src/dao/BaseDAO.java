package dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface BaseDAO<T> {

	void delete(final T obj) throws Exception;

	Serializable insert(T obj) throws Exception;

	T loadById(Serializable id) throws Exception;

	void update(final T obj) throws Exception;

	List<T> listAll(String orderBy) throws Exception;

	void importar(List<T> objs) throws Exception;

	void atualizar(Collection<T> objs) throws Exception;

	void saveOrUpdate(T obj) throws Exception;

	void updateWithFlush(T obj) throws Exception;

	void updateWithCommit(T obj) throws Exception;

	void merge(T obj) throws Exception;

	List<T> listByExample(T obj) throws Exception;

	Date getDataAtual();

}
