package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class ConnectSqlServer {
	private static java.sql.Connection	con				= null;
	private final static String			url				= "jdbc:oracle:thin:@";
	private final static String			serverName		= "192.168.1.225";
	private final static String			portNumber		= "1521";
	private final static String			databaseName	= "orcl";
	private final static String			userName			= "vetorh";
	private final static String			password			= "vetorh";
	private static ConnectSqlServer		conexaoSQL;
	static Logger								logger			= Logger.getLogger(ConnectSqlServer.class);

	public ConnectSqlServer() {}

	public static ConnectSqlServer getInstance() {
		if (conexaoSQL == null) {
			conexaoSQL = new ConnectSqlServer();
		}
		return conexaoSQL;
	}

	private static String getConnectionUrl() {
		return url + serverName + ":" + portNumber + ":" + databaseName;
	}

	public java.sql.Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = java.sql.DriverManager.getConnection(this.getConnectionUrl(), userName, password);

			if (con != null) {
				con.setAutoCommit(false);
			}

		} catch (Exception e) {
			System.out.println("Não Funcionando CONEXÃO");
			e.printStackTrace();
		}

		return con;
	}

	public void displayDbProperties() {
		java.sql.DatabaseMetaData dm = null;
		java.sql.ResultSet rs = null;
		try {
			con = this.getConnection();
			if (con != null) {
				dm = con.getMetaData();
				logger.info("Driver Information");
				logger.info("\tDriver Name: " + dm.getDriverName());
				logger.info("\tDriver Version: " + dm.getDriverVersion());
				logger.info("\nDatabase Information ");
				logger.info("\tDatabase Name: " + dm.getDatabaseProductName());
				logger.info("\tDatabase Version: " + dm.getDatabaseProductVersion());
				logger.info("Avalilable Catalogs ");
				rs = dm.getCatalogs();
				while (rs.next()) {
					logger.info("\tcatalog: " + rs.getString(1));
				}
				rs.close();
				rs = null;
				closeConnection();
			} else
				logger.error("Erro: Nenhuma conexão ATIVA");

		} catch (Exception e) {
			e.printStackTrace();
		}
		dm = null;
	}

	public static void closeConnection() {
		try {
			if (con != null)
				con.close();
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ConnectSqlServer myDbTest = new ConnectSqlServer();
		myDbTest.displayDbProperties();
	}

	public void closeConnection(PreparedStatement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
