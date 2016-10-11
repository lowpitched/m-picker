package com.mlh.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mbag.log.LogTools;

public class JDBCUtil {

	public static Properties property;
	
	static{
		try {
			property = new Properties();
			property.load(JDBCUtil.class.getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			LogTools.e(JDBCUtil.class, e.getMessage(),e);
		}
	}
	
	public static Connection getConnection(){
		try {
			Class.forName(property.getProperty("driverClass"));
			String url = property.getProperty("jdbcUrl");
			String userName = property.getProperty("user");
			String password = property.getProperty("password");
			return DriverManager.getConnection(url,userName,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			LogTools.e(JDBCUtil.class, e.getMessage(),e);
		} catch (SQLException e) {
			e.printStackTrace();
			LogTools.e(JDBCUtil.class, e.getMessage(),e);
		}
		return null;
	}
	
}
