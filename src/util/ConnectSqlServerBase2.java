package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class ConnectSqlServerBase2 {
	private static java.sql.Connection		con				= null;
	private final static String				url				= "jdbc:oracle:thin:@//";
	private final static String				serverName		= "192.168.1.223";
	private final static String				portNumber		= "1521";
	private String									databaseName	= "";
	private String									userName			= "";
	private String									password			= "";
	private static ConnectSqlServerBase2	conexaoSQL;
	static Logger									logger			= Logger
																					.getLogger(ConnectSqlServerBase2.class);

	private ConnectSqlServerBase2(String databasename, String username, String password) {
		this.databaseName = databasename;
		this.userName = username;
		this.password = password;
	}

	public static ConnectSqlServerBase2 getInstance(String databasename, String username,
			String password) {
		if (conexaoSQL == null) {
			conexaoSQL = new ConnectSqlServerBase2(databasename, username, password);
		}
		return conexaoSQL;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String getConnectionUrl() {
		return url + serverName + ":" + portNumber + "/" + databaseName;
	}

	public java.sql.Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = java.sql.DriverManager.getConnection(getConnectionUrl(), userName, password);
			if (con != null) {
				con.setAutoCommit(false);
			}
		} catch (Exception e) {
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
				logger.error("Erro: Nenhuma conexão ativa");
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
}
