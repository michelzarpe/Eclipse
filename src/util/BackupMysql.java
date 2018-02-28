package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupMysql {
	/**
	 * Cria um shell temporário e executa este. Exclusivo para linux. Salva o backup na pasta /opt/ultraBackup/
	 **/
	public static String doBackup(String usuario, String senha, String banco)
			throws RemoteException, IOException, InterruptedException {
		// faz o backup e retorna o nome do arquivo...

		StringBuffer s = new StringBuffer();

		s.append("#!/bin/bash\n");
		s.append("\n");

		// COLOCAR NESTAS VARIAVEIS A INFORMACAO DE SERVIDOR, SENHA, USUARIO, ETC DO MYSQL
		s.append("SERVER=localhost\n");
		s.append("USER=" + usuario + "\n");
		s.append("PORT=3306\n");
		s.append("PASS=" + senha + "\n");
		s.append("DB=" + banco + "\n");
		s.append("\n");

		File tmpDir = File.createTempFile("backupUltra", null);
		tmpDir.delete();
		tmpDir.mkdirs();

		s.append("TMPDIR=" + tmpDir.getAbsolutePath() + "\n");

		s.append("rm $TMPDIR\n");
		s.append("mkdir $TMPDIR\n");

		s.append("\n");
		s.append("mkdir -p /opt/ultraBackup\n");
		s.append("\n");
		s.append("mysqldump -h $SERVER --port $PORT -u $USER --password=$PASS --add-drop-database $DB --add-drop-table --disable-keys --extended-insert --add-locks --single-transaction --databases $DB > $TMPDIR/$DB.sql\n");
		s.append("\n");

		s.append("\n");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
		String TGZ = banco + "-" + sdf.format(new Date()) + ".tar.gz";

		s.append("TGZ=/tmp/" + TGZ + "\n");
		s.append("\n");
		s.append("cd $TMPDIR\n");

		// String[] names = tmpDir.getAbsolutePath().split("[" + File.separator + "]");

		s.append("tar czf $TGZ *\n");
		s.append("\n");
		s.append("rm -fr $TMPDIR\n");
		s.append("\n");
		s.append("mv $TGZ /opt/ultraBackup\n");
		s.append("\n");

		File scriptBackup = File.createTempFile("backupUltra", ".sh");

		scriptBackup.createNewFile();
		scriptBackup.setExecutable(true);
		BufferedWriter bw = new BufferedWriter(new FileWriter(scriptBackup));
		bw.write(s.toString());
		bw.flush();
		bw.close();

		Process p = Runtime.getRuntime().exec(scriptBackup.getAbsolutePath());
		int resultado = p.waitFor();

		if (resultado == 0) {
			scriptBackup.delete();
			return "/opt/ultraBackup/" + TGZ;
		} else {
			throw new RemoteException("Saida de erro: " + resultado);
		}
	}
}
