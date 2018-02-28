package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;

import util.ConnectSqlServerBase2;

import com.opensymphony.xwork2.ActionSupport;

@Results({ @Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
		"inputName", "inputStream" }) })
public class UtilidadeAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private InputStream	inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String geraBase3() {
		/** Conectar à base do vetorh para poder fazer backup do banco Copia3, pois se entrar no copia3, dá erro de banco de dados em uso **/
		Connection connection = ConnectSqlServerBase2.getInstance("vetorh", "sa", "").getConnection();
		PreparedStatement pstmt = null;
		try {
			/** fazer backup para base3(Paralelo) com origem nos backups da base Vetorh **/
			String sql = "RESTORE DATABASE [Copia3] FROM VETORH WITH  FILE = 1,  NOUNLOAD,  STATS = 10";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			connection.close();
			/** mudar a conexão para o banco de dados Copia2 para poder excluir os registros não utilizados por esta base **/
			/** excluir dados de tabelas que não serão utilizados **/
			connection = ConnectSqlServerBase2.getInstance("copia3", "vetorh", "vetorh")
					.getConnection();
			connection.setAutoCommit(true);

			sql = "DELETE FROM [Copia3].[vetorh].[R046Ver]";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();

			connection.setAutoCommit(false);
			Statement stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia3]");
			stmt2.executeUpdate("IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r044mov_ed]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r044mov_ed]");
			connection.commit();
			stmt2.close();
			/*************************************/
			connection.setAutoCommit(true);
			sql = "DELETE FROM [Copia3].[vetorh].[R044MOV] WHERE CODEVE IN(18,22,34, 35, 36, 37, 38, 46, 543, 549, 558, 720, 69)";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();

			connection.setAutoCommit(false);
			stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia3]");
			stmt2.executeUpdate("IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r030emp_su]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r030emp_su]");
			connection.commit();
			stmt2.close();

			connection.setAutoCommit(true);
			sql = "UPDATE [Copia3].[vetorh].[R030EMP] SET INTSAP = 2";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			inputStream = new ByteArrayInputStream(("Base do paralelo gerada com sucesso!").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Geração de base do paralelo não executada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	/**
	 * Executar rotina que gera um backup da base Vetorh, excluindo dados de algumas tabelas, para testes. (BASE2)
	 * 
	 * @return String
	 * @throws Exception
	 * @throws SQLException
	 */
	public String geraBase2() {
		/** Conectar à base do vetorh para poder fazer backup do banco Copia2, pois se entrar no copia2, dá erro de banco de dados em uso **/
		Connection connection = ConnectSqlServerBase2.getInstance("vetorh", "sa", "").getConnection();
		PreparedStatement stmt = null;
		String sql = "";
		try {
			/** fazer backup para base2 com origem nos backups da base Vetorh **/
			sql = "RESTORE DATABASE [Copia2] FROM VETORH WITH  FILE = 1,  NOUNLOAD,  STATS = 10";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
			connection.close();

			/** mudar a conexão para o banco de dados Copia2 para poder excluir os registros não utilizados por esta base **/
			/** excluir dados de tabelas que não serão utilizados **/
			connection = ConnectSqlServerBase2.getInstance("copia2", "vetorh", "vetorh")
					.getConnection();
			connection.setAutoCommit(true);
			sql = "DELETE FROM [Copia2].[vetorh].[R040ACU]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040ASD]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040FEG]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040FEV]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040FEM]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040INV]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040PRG]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040PER]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040PRC]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040TOB]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R040TOT]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R046FFR]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R046FRF]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R046VER]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			/** excluir triggers para tabelas R026IRF, R026INF, R026INM, senão, não é possível excluir os registros **/
			/** ----------------------------- **/
			connection.setAutoCommit(false);
			Statement stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia2]");
			stmt2.executeUpdate("IF EXISTS(SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r026irf_sd]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r026irf_sd]");
			connection.commit();
			stmt2.close();
			/*************************************/
			stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia2]");
			stmt2.executeUpdate("IF EXISTS(SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r026inf_sd]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r026inf_sd]");
			connection.commit();
			stmt2.close();
			/*****************************************/
			stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia2]");
			stmt2.executeUpdate("IF EXISTS(SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r026inm_sd]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r026inm_sd]");
			connection.commit();
			stmt2.close();
			/*****************************************/

			connection = ConnectSqlServerBase2.getInstance("copia2", "vetorh", "vetorh")
					.getConnection();
			connection.setAutoCommit(true);
			sql = "DELETE FROM [Copia2].[vetorh].[R026IRF]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R026INF]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "DELETE FROM [Copia2].[vetorh].[R026INM]";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
			connection.close();

			/*****************************************/
			connection = ConnectSqlServerBase2.getInstance("copia2", "vetorh", "vetorh")
			.getConnection();
			stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia2]");
			stmt2.executeUpdate("IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r038afa_ed]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r038afa_ed]");
			connection.commit();
			stmt2.close();
			/*****************************************/

			connection = ConnectSqlServerBase2.getInstance("copia2", "vetorh", "vetorh")
					.getConnection();
			connection.setAutoCommit(true);
			sql = "delete from [Copia2].[vetorh].[R038Afa] where sitafa=2";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
			/**********************************************/
			stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia2]");
			stmt2.executeUpdate("IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r008evc_su]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r008evc_su]");
			connection.commit();
			stmt2.close();
			/*****************************************/

			/** ajustar eventos para não descontar do colaborador **/
			sql = "update [Copia2].[vetorh].[R008Evc] set rgrEsp=0 where codTab=1 and codEve=623";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			sql = "update [Copia2].[vetorh].[R008Evc] set rgrEsp=0 where codTab=1 and codEve=288";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			/**********************************************/
			stmt2 = connection.createStatement();
			stmt2.executeUpdate("USE [Copia2]");
			stmt2.executeUpdate("IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r008evb_sd]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1) DROP TRIGGER [vetorh].[r008evb_sd]");
			connection.commit();
			stmt2.close();
			/*****************************************/
			/** Remover a base 9113 do evento 288 **/
			sql = "delete from [Copia2].[vetorh].[R008Evb] where codTab=1 and codEve=288 and eveBas=9113";
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();

			inputStream = new ByteArrayInputStream(("Base de testes gerada com sucesso!").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Geração de base de testes não executada. Causa: " + e.getMessage()).getBytes());
		} finally {
			try {
				if (!connection.isClosed()) {
					connection.close();
				}
			} catch (Exception e) {

			}
		}
		return "sucTxt";
	}
}
