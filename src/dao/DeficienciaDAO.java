package dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Deficiencia;

public interface DeficienciaDAO extends BaseDAO<Deficiencia> {
	public Deficiencia retDefBd(int codDef);
	public List <Deficiencia> retListDefic();
}
