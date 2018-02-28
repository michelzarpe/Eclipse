package util;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;

public class CompilaJs {
	private static String	path;
	private static String	pathScript	= "/src/main/webapp";
	private static String	pathOut		= "/src/main/webapp/scripts-out/";

	public static void main(String[] args) {
		System.out.println("Concatenando arquivos js");
		path = args[0];
		String retorno = concatenaArquivo(false);
		criaArquivo(retorno, "javascript.js");
	}

	private static void criaArquivo(String conteudo, String name) {
		FileOutputStream saida = null;
		PrintStream fileSaida = null;
		try {
			try {
				saida = new FileOutputStream(path + pathOut + name);
				fileSaida = new PrintStream(saida);
				fileSaida.print(conteudo);
			} finally {
				if (saida != null)
					saida.close();
				if (fileSaida != null)
					fileSaida.close();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private static String concatenaArquivo(boolean portal) {
		String scripts_globais[] = new String[] { "/scripts/prototype.js",
				"/scripts/prototype.improvements.js", "/scripts/scriptaculous.js",
				"/scripts/effects.js", "/scripts/global.js" };

		StringBuffer conteudo = new StringBuffer();
		try {
			appendJavaScripts(scripts_globais, conteudo);

		} catch (Exception ex) {
			System.out.println("Error: Compilação compressJavaScript: " + ex.getMessage());
		}
		String resultado = StringUtils.EMPTY;
		resultado = conteudo.toString();
		return resultado;
	}

	private static void appendJavaScripts(String scripts[], StringBuffer conteudo) throws Exception {
		/**for (int i = 0; i < scripts.length; ++i) {
			File file = new File(path + pathScript + scripts[i]);
			String dados = FileCopyUtils.copyToString(new InputStreamReader(new FileInputStream(file),
					"iso-8859-1"));
			if ("/scripts/scriptaculous.js".equals(scripts[i])) {
				dados = StringUtils.remove(dados, "Scriptaculous.load();");
			}
			conteudo.append(dados);
		}**/
	}

	public static String getPathScript() {
		return pathScript;
	}

	public static void setPathScript(String pathScript) {
		CompilaJs.pathScript = pathScript;
	}
}
