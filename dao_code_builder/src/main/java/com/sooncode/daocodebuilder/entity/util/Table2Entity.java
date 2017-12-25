package com.sooncode.daocodebuilder.entity.util;

public class Table2Entity {
	
	
	/**
	 * 字段 --》 属性 如： user_no --> userNo
	 * 
	 * @param oldName
	 * @return
	 */
	public static String columnNameToPropertyName(String columnName) {
		String[] arrays = columnName.toLowerCase().split("_");
		String propertyName = "";
		if (arrays.length > 0) {
			propertyName = arrays[0];
		}
		for (int i = 1; i < arrays.length; i++) {
			propertyName += (arrays[i].substring(0, 1).toUpperCase() + arrays[i].substring(1, arrays[i].length()));
		}
		return propertyName;
	}

	/*-----------------------------------------------------------------------------------9--------------------------------------------------------------*/
	/**
	 * 表名称 --》 类名 如： code_student --> Student  code_student_course -->
	 * StudentCourse
	 * 
	 * @param tableName
	 *            表名
	 * @return 实体名
	 */
	public static String tableNameToClassName(String tableName) {
		String[] arrays = tableName.toLowerCase().split("_");
		String className = "";
		if(arrays.length ==1){
			className = tableName.toUpperCase().substring(0, 1)+tableName.toLowerCase().substring(1, tableName.length()-1);
		}else{
			for (int i = 1; i < arrays.length; i++) {
				className = className + arrays[i].toLowerCase().substring(0, 1).toUpperCase() + arrays[i].substring(1, arrays[i].length());
			}
			
		}
		return className;
	}
	
	

}
