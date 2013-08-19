/**
 * 
 */
package wgz.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangguozheng
 * 
 */
public class UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	static Connection connection = null;
	static ResultSet rs = null;
	private static DataSource dataSource;

	// 1. 用JNDI找到数据库池 DataSource
	static {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/testdb");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// UserDAO应该有一个Login方法，但是因为不是这个项目的重点所以不写明了
	public static UserBean login(UserBean user) {
		return null;
	}

	/**
	 * 把UserBean注册到数据表中，如果修改正常返回true, 否则返回false 抛出SQLException相关异常
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static boolean register(UserBean user) throws SQLException {
		logger.debug("rigiser方法启动");
		Statement stmt = null;
		int result = 0;
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		boolean active = user.isActive();
		String updateQuery = "INSERT INTO user ( username,password,email,active ) VALUES ( '"
				+ username + "', '" + password + "','" + email + "','" + active + "');";
		logger.debug("生成SQL: " + updateQuery);
		try {
			connection = dataSource.getConnection();
			logger.debug("获得链接");
			stmt = connection.createStatement();
			result = stmt.executeUpdate(updateQuery);
			logger.debug("更新数据库结果是: " + result);

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
}
