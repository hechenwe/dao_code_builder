package com.sooncode.daocodebuilder.entity.util.config;

/**
 * 数据库相关常量
 * 
 * @author pc
 * 
 */
public class DBConstant {
	
	public static String DATA_BASE ;
	public static String IP ;
	public static String PORT ;
	public static String USER ;
	public static String PASSWORD;
	public static String DRIVER = "com.mysql.jdbc.Driver";
	 
	
	public static String getUrl(){
		String url  = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATA_BASE + "?useUnicode=true&characterEncoding=UTF8";
		return url;
	}
}
