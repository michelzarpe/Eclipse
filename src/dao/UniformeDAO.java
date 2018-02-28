package dao;

import java.util.List;
import java.util.Set;

import bean.Caracteristica;
import bean.Uniforme;

public interface UniformeDAO extends BaseDAO<Uniforme> {

	List<Uniforme> listAllByTipoEPI(int tipoEPI, String sitEpi);
	
	List<Uniforme> listByCaracteristica(int tipoEpi, String sitEpi, Caracteristica caracteristicaSexo, Caracteristica caracteristicaSegmento);
}
