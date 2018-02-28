package dao;

import java.util.List;

import bean.Documento;

public interface DocumentoDAO extends BaseDAO<Documento> {
	List<Documento> listNivelRaiz();
}
