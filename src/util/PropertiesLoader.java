package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
	private Properties	properties;
	private String			nomeArquivoProperties;

	public PropertiesLoader(String nomeArquivoProperties) {
		this.nomeArquivoProperties = nomeArquivoProperties;
		properties = new Properties();

	}

	public String getValor(String chave) {
		FileInputStream in;
		try {
			in = new FileInputStream(nomeArquivoProperties);
			properties.load(in);
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(chave);
	}

	public boolean setValor(String chave, String valor) {
		properties.setProperty(chave, valor);
		try {
			
			FileInputStream in = new FileInputStream(this.nomeArquivoProperties);
			properties.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(this.nomeArquivoProperties);
			properties.setProperty(chave, valor);
			properties.store(out, null);
			out.close();
			return true;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	public String getNomeArquivoProperties() {
		return nomeArquivoProperties;
	}

	public void setNomeArquivoProperties(String nomeArquivoProperties) {
		this.nomeArquivoProperties = nomeArquivoProperties;
	}

}
