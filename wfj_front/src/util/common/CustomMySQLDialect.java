package util.common;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * 自定义重写hibernate的mysql方言分页limit查询。
 */
public class CustomMySQLDialect extends MySQLDialect {
	/**
	 * 自定义重写hibernate的mysql方言分页limit查询。
	 * sql：业务查询最终sql语句
	 * hasOffset：真假，使用哪种limit方法
	 */
	public String getLimitString(String sql, boolean hasOffset) {
		StringBuffer customLimit = new StringBuffer( sql.length() + 20 );
		customLimit.append( " select custom.* from ( " )
		.append( sql )
		.append( " ) as custom  " )
		.append( hasOffset ? " limit ?, ?" : " limit ?" );
		return customLimit.toString();
	}
	
	public CustomMySQLDialect () {  
        super();  
        registerHibernateType(Types.LONGVARBINARY, Hibernate.BLOB.getName());  
        registerFunction("group_concat", new StandardSQLFunction("group_concat",Hibernate.STRING));
    } 
	
}
