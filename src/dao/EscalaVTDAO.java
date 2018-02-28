package dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import bean.EscalaVT;

@Repository
@Transactional
public interface EscalaVTDAO extends BaseDAO<EscalaVT>{
	public EscalaVT retEscVt(int escVt);
	public List <EscalaVT> retListEscVt();
}
