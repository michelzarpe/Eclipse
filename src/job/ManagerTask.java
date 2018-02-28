package job;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ManagerTask {

	public void agendaHorario(Date dataAgendamento, long intervalo, TimerTask tarefa, Timer timer) {
		timer.scheduleAtFixedRate(tarefa, dataAgendamento, intervalo);
		System.out.println("Agendando tarefa " + tarefa.getClass());
	}

	public void agendaHorarioComAtraso(Date dataAgendamento, long intervalo, TimerTask tarefa,Timer timer) {
		timer.schedule(tarefa, dataAgendamento, intervalo);
		System.out.println("Agendando tarefa " + tarefa.getClass());
	}
}
