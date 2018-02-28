package dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.NaturezaDespesa;

@Repository
@Transactional
public interface NaturezaDespesaDAO extends BaseDAO<NaturezaDespesa> {
	public NaturezaDespesa retNatDes(int natDes);
	public List <NaturezaDespesa> retListNatDes();
}

