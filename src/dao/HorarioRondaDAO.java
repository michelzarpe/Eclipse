package dao;

import java.util.List;
import bean.HorarioRonda;

public interface HorarioRondaDAO extends BaseDAO<HorarioRonda> {
	public HorarioRonda retCodHorBd(int codHor);
	public List<HorarioRonda> retHorRonda(int codEsc);
}
