package util.other;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 工具类 - C3P0数据源
 * 
 * */
public class C3P0_Util {
	private static DataSource oracleDs;
	private static DataSource sqlserverDs;
	static{
		try {
			oracleDs = new ComboPooledDataSource("oracle");
			sqlserverDs = new ComboPooledDataSource("sqlserver");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到Oracle数据源链接。
	 * 
	 * @return 数据源链接
	 */	
	public static Connection getOracleConnection() {
		Connection con = null;
		try {
			con = oracleDs.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * 得到SqlServer数据源链接。
	 * 
	 * @return 数据源链接
	 */	
	public static Connection getSqlServerConnection() {
		Connection con = null;
		try {
			con = sqlserverDs.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static DataSource getOracleDataSource(){
		return oracleDs;
	}
	public static DataSource getSqlServerDataSource(){
		return sqlserverDs;
	}
	public static void startCommit(Connection con) {
		try {
			if(con!=null) con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void closeCommit(Connection con) {
		try {
			if(con!=null) con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commit(Connection con){
			try {
				if(con!=null)
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public static void rollback(Connection con){
		try {
			if(con!=null)
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void ralease(Connection con, Statement st,ResultSet rs) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con = null;
		}
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st = null;
		}
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
		}
	}
}
