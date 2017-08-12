package phone.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public  class DBHelper{
	
	private static final String driver="com.mysql.jdbc.Driver";
	private static final String url="jdbc:mysql://192.168.1.145:3306/b2b2c";
	private static final String uesrName="root";
	private static final String userPwd="MySQL";
	
//	private static  String driver="";
//	private static  String url="";
//	private static  String uesrName="";
//	private static  String userPwd="";
 
	public static Connection getConnection()  throws Exception{
		Connection con = null;
		
//		String filePath = "database.properties";
//		Properties prop = new Properties();
//		InputStream	in = PhoneCategoryAction.class.getClassLoader().getResourceAsStream(filePath);
//		prop.load(in);
//		driver=prop.getProperty("driver");
//		url=prop.getProperty("url");
//		uesrName=prop.getProperty("uesrName");
//		userPwd=prop.getProperty("userPwd");
// 		System.out.println(driver+"#"+url);
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, uesrName, userPwd);
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	public static void closeCon(Connection con){
		try{
			if(con!=null){
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void closeResuleSet(ResultSet rst){
		try{
			if(rst!=null){
				rst.close();
				rst = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void closeStatement(Statement stmt){
		try{
			if(stmt!=null){
				stmt.close();
				stmt = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
