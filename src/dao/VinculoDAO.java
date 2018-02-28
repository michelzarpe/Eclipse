package dao;

import java.util.List;

import bean.Vinculo;

public interface VinculoDAO extends BaseDAO<Vinculo> {
	public Vinculo retVinBd(int codVin);
	public List <Vinculo> retListVinc();
}
