package dao;

import java.util.List;

import bean.SubTipoMotivo;

public interface SubTipoMotivoDAO extends BaseDAO<SubTipoMotivo> {

	List<SubTipoMotivo> listAllByMotivo(int motivoId);

}
