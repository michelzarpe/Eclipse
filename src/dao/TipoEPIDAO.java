package dao;

import java.util.List;

import bean.TipoEPI;

public interface TipoEPIDAO extends BaseDAO<TipoEPI> {
	List<TipoEPI> listTiposAtivos() throws Exception;
}
