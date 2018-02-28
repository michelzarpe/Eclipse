package dao;

import java.util.Date;
import java.util.List;

import bean.Acesso;

public interface AcessoDAO extends BaseDAO<Acesso> {
	Acesso getAcessoByIdSession(String idSession)  throws Exception;
	List<Acesso> listByDate(Date data);
}
