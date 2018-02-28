package util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Teste {
	public static boolean geraBackup(String usuario, String senha, String banco) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		String nroBackup = String.valueOf(cal.get(Calendar.DATE))
				+ String.valueOf(cal.get(Calendar.MONTH)) + String.valueOf(cal.get(Calendar.YEAR));
		File diretorio = new File("/home/backups");
		File bck = new File("/home/backups/" + banco + nroBackup + ".sql");
		// os zeros é para diferenciar um backup do outro

		// Cria diretório
		if (!diretorio.isDirectory()) {
			new File("/home/backups/").mkdir();
		} else {

		}

		// Cria Arquivo de Backup
		try {
			if (!bck.isFile()) {
				Runtime.getRuntime().exec(
						"mysqldump --user=" + usuario + " --password=" + senha + " --database " + banco
								+ " > " + bck);
			} else {
				while (bck.isFile()) {
					bck = new File("/home/backups/" + banco + nroBackup + ".sql");
				}

				Runtime.getRuntime().exec(
						"mysqldump --user=" + usuario + " --password=" + senha + " --database " + banco
								+ " > " + bck);
			}
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		/**PropertiesLoader propertiesLoader = new PropertiesLoader("conf.properties");
		String propriedade = propertiesLoader.getValor("data_fechamento_periodo_requisicao");
		System.out.println(propriedade);**/
		//geraBackup("root", "grpzjba", "operacional");
		//geraBackup("root", "grpzjba", "gzOper");
		System.out.println("Conectando sapiens sid...");
		//System.out.println(Util.loginSapiensSID());
	}
}
