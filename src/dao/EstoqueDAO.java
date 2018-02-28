package dao;

import bean.Estoque;
import bean.Uniforme;

public interface EstoqueDAO extends BaseDAO<Estoque> {

	int getTotalEstoqueItem(Uniforme uniforme);

}
