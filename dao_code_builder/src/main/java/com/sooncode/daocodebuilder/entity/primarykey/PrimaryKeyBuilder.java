package com.sooncode.daocodebuilder.entity.primarykey;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sooncode.daocodebuilder.entity.column.Column;
import com.sooncode.daocodebuilder.entity.util.Builder;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;
import com.sooncode.daocodebuilder.entity.util.Jdbc2Java;
import com.sooncode.daocodebuilder.entity.util.Table2Entity;

/**
 * 主键
 * 
 * @author sooncode
 *
 */

public class PrimaryKeyBuilder {
	/**
	 * 数据库连接对象
	 */
	protected static Connection connection;

	public static PrimaryKey queryPrimaryKeyByTableName(String databaseName, String tableName) throws SQLException {
		connection = DBConnectionUtils.getJDBCConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		List<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();

		ResultSet columnSet = databaseMetaData.getColumns(databaseName, "%", tableName, "%");
		ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(databaseName, null, tableName);

		Map<String, Column> columnMap = new HashMap<>();
		while (columnSet.next()) { // 遍历某个表的字段
			String columnName = columnSet.getString("COLUMN_NAME".toUpperCase());
			String columnType = columnSet.getString("TYPE_NAME");
			int dataType = Integer.parseInt(columnSet.getString("DATA_TYPE"));

			String javaDataType = Jdbc2Java.getJavaData().get(Jdbc2Java.getJdbcData().get("" + dataType));

			String columnRemarks = columnSet.getString("REMARKS");
			String isAutoinCrement = columnSet.getString("IS_AUTOINCREMENT");

			Column column = new Column();
			column.setColumnName(columnName.toUpperCase());
			column.setPropertyName(Table2Entity.columnNameToPropertyName(columnName));
			column.setDatabaseDataType(columnType);
			column.setJavaDataType(javaDataType);
			column.setColumnRemarks(columnRemarks);
			column.setIsAutoinCrement(isAutoinCrement);
			columnMap.put(columnName, column);
		}
		
		while (primaryKeyResultSet.next()) { // 遍历某个表的主键

			PrimaryKey primaryKey = new PrimaryKey();

			String primaryKeyName = primaryKeyResultSet.getString("COLUMN_NAME");
			String primaryKeySerial = primaryKeyResultSet.getString("KEY_SEQ");
			String primaryKeyDataType = "";
			Column column = columnMap.get(primaryKeyName);
			primaryKey.setJavaDataType(column.getJavaDataType());

			primaryKey.setPrimaryPropertyName(Table2Entity.columnNameToPropertyName(primaryKeyName));
			primaryKey.setPrimaryKeyName(primaryKeyName.toUpperCase());

			primaryKey.setDatabaseDataType(primaryKeyDataType);

			primaryKey.setPrimaryKeyRemarks(column.getColumnRemarks());
			primaryKey.setPrimaryKeySerial(Short.parseShort(primaryKeySerial));

			primaryKeys.add(primaryKey);
		}
		return primaryKeys.get(0);
	}

}
