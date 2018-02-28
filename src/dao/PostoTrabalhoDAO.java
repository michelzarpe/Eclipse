package dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.PostoTrabalho;

@Repository
@Transactional
public interface PostoTrabalhoDAO extends BaseDAO<PostoTrabalho> {
	public PostoTrabalho retPosTraBd(String posTra);
	public List <PostoTrabalho> retListPosTra();
	public void removerPostoTrabalho();

}
