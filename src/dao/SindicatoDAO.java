package dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Sindicato;

@Repository
@Transactional
public interface SindicatoDAO extends BaseDAO<Sindicato> {
	public Sindicato retSinBd(int codSin);
	public List <Sindicato> retListSind();
}
