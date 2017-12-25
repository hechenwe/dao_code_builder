package com.sooncode.daocodebuilder.entity.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.sooncode.daocodebuilder.entity.util.config.DBConstant;

public class DBConnectionUtils {
	 

	public static Connection getJDBCConnection() {
		Connection conn = null;
		 
			try {
				Properties properties = new Properties();

				Class.forName(DBConstant.DRIVER); //
				String url = DBConstant.getUrl();//
				 
				String user = DBConstant.USER;
				String password = DBConstant.PASSWORD;

				properties.setProperty("user", user);
				properties.setProperty("password", password);
				properties.setProperty("remarks", "true");
				properties.setProperty("useInformationSchema", "true");

				conn = DriverManager.getConnection(url, properties);
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
		return conn;
	}
	
	
    /**
     * 测试连接数据库
     * @param ip 
     * @param port
     * @param user
     * @param password
     * @param dataBaseName
     * @return 
     */
	public static boolean testConnection(String ip, String port, String user, String password, String dataBaseName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Properties properties = new Properties();
			properties.setProperty("user", user);
			properties.setProperty("password", password);
			properties.setProperty("remarks", "true");
			properties.setProperty("useInformationSchema", "true");

			String url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBaseName + "?useUnicode=true&characterEncoding=UTF8";
			
			Connection connection = DriverManager.getConnection(url, properties);
			if (connection != null) {
				connection.close();
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}

	}

	 
	 

}
