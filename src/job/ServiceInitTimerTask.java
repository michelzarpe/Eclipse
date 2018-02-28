package job;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Component;

@Component
public class ServiceInitTimerTask extends HttpServlet implements Servlet {
	private static final long	serialVersionUID	= 1L;

	public void destroy() {

	}

	public ServletConfig getServletConfig() {
		return null;
	}

	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext context = servletConfig.getServletContext();
		String path = context.getRealPath("/paginas/reports/");
		Timer timer = new Timer(true);
		Calendar calendar = Calendar.getInstance();
		ManagerTask managerTask = new ManagerTask();
		Date horaProgramada = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		int dataInicio = calendar.get(Calendar.DAY_OF_MONTH);
		long intervalo = 24 * 60 * 60 * 1000; // cada 24 horas

		/**
		 * Tarefa 1 - Atualiza estoque e verifica se tem algum item não atendido que possa ser atendido por que o estoque chegou
		 */
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 5);
		calendar.set(Calendar.DATE, dataInicio);
		horaProgramada = calendar.getTime();
		managerTask.agendaHorario(horaProgramada, intervalo, new VerificaEstoque(), timer);

		/**
		 * Tafefa 2 - Gera e envia relatório de solicitações que precisam de liberação aos Administradores de Almoxarifado
		 */
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.DATE, dataInicio);
		horaProgramada = calendar.getTime();
		managerTask.agendaHorario(horaProgramada, intervalo, new GeraRelatorioLiberacoes(path), timer);

		/**
		 * Tarefa 4 - notificar novas admissões ao RH
		 */
		calendar.set(Calendar.HOUR_OF_DAY, 3);
		calendar.set(Calendar.DATE, dataInicio);
		horaProgramada = calendar.getTime();
		managerTask.agendaHorario(horaProgramada, intervalo, new NotificaAdmissao(path), timer);
		/**
		 * Tarefa 5 - Atualização do banco de dados que é base para colaboradores e solicitações
		 */
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.DATE, dataInicio);
		horaProgramada = calendar.getTime();
		managerTask.agendaHorario(horaProgramada, intervalo, new ImportaDadosBase(), timer);

		/**
		 * Tarefa 6 - atualização da base a cada 1 hora
		 */
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.DATE, dataInicio);
		horaProgramada = calendar.getTime();
		intervalo = 60 * 60 * 1000;
		managerTask.agendaHorario(horaProgramada, intervalo, new ImportaDadosBasicosBase(), timer);

		/**
		 * Tarefa 7 - Atualiza lista de estoques a cada 15 minutos
		 */
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.DATE, dataInicio);
		horaProgramada = calendar.getTime();
		intervalo = 15 * 60 * 1000;
		managerTask.agendaHorario(horaProgramada, intervalo, new ImportaEstoques(), timer);

		/**
		 * criar agendamento para enviar 3 dias antes, um lembrete sobre o término do prazo de requisição de materiais à cada 30 dias
		 **/
		horaProgramada = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		dataInicio = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.HOUR_OF_DAY, 4);
		calendar.set(Calendar.DATE, dataInicio);
		horaProgramada = calendar.getTime();
		intervalo = 24 * 60 * 60 * 1000;
		String pathTexto = context.getRealPath("/paginas/");
		managerTask.agendaHorarioComAtraso(horaProgramada, intervalo,
				new EmailLembretePrazoRequisicao(pathTexto), timer);

		/** gera backup da base de dados do sistema - diariamente **/
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.DATE, dataInicio);
		intervalo = 24 * 60 * 60 * 1000; // cada 24 horas
		horaProgramada = calendar.getTime();
		managerTask.agendaHorario(horaProgramada, intervalo, new GeraBackup(), timer);

		/**
		 * Tarefa 8 - Sincroniza colaboradores Vetorh - web à cada 10 minutos
		 */
/*
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.DATE, dataInicio - 1);
		horaProgramada = new Date();
		intervalo = 2 * 60 * 1000;
		managerTask.agendaHorario(horaProgramada, intervalo, new SincronizaColaboradores(), timer);
*/
	}

	public void service(ServletRequest service, ServletResponse response) throws ServletException,
			IOException {

	}

}
