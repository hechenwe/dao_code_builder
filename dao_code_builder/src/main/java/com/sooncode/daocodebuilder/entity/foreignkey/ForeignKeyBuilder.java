package com.sooncode.daocodebuilder.entity.foreignkey;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sooncode.daocodebuilder.entity.column.Column;
import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.table.TableBuilder;
import com.sooncode.daocodebuilder.entity.util.Builder;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;
import com.sooncode.daocodebuilder.entity.util.Jdbc2Java;
import com.sooncode.daocodebuilder.entity.util.Table2Entity;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;
  
import com.sooncode.daocodebuilder.util.Inflector;

public class ForeignKeyBuilder extends Builder implements Callable<List<ForeignKey>> {

	private List<ForeignKey> foreignKeys;

	public List<ForeignKey> getForeignKeys() {

		ExecutorService pool = Executors.newFixedThreadPool(1); // 线程池 数量为1

		Callable<List<ForeignKey>> c = new ForeignKeyBuilder();

		Future<List<ForeignKey>> future = pool.submit(c);

		// 关闭线程池
		pool.shutdown();

		try {
			this.foreignKeys = future.get();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		}
		return foreignKeys;
	}

	/**
	 * 查询表的所有外键构造“外键外属性模型”
	 * 
	 * @param databaseName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static List<ForeignKey> buildForeignKeyByTableName(String databaseName, String tableName) throws SQLException {
		connection = DBConnectionUtils.getJDBCConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
		ResultSet columnSet = databaseMetaData.getColumns(databaseName, "%", tableName, "%");
		ResultSet foreignKeyResultSet = databaseMetaData.getImportedKeys(databaseName, null, tableName);
		
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
		
		
		while (foreignKeyResultSet.next()) {// 遍历某个表的外键

			String fkColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
			String fkName = foreignKeyResultSet.getString("FK_NAME").toUpperCase();
			String foreignKeySerial = foreignKeyResultSet.getString("KEY_SEQ");
			String foreignKeyRemarks = "";
			String foreignKeyDataType = "";
			ForeignKey foreignKey = new ForeignKey();
			
            //------------------------------------------------------------------------------------------
			Column column = columnMap.get(fkColumnName);
			foreignKeyDataType = column.getDatabaseDataType(); // columnSet.getString("TYPE_NAME");
			foreignKeyRemarks = column.getColumnRemarks().split(GlobalConstant.STRING_SPLIT_SIGN)[0];
			foreignKey.setJavaDataType(column.getJavaDataType());
			//------------------------------------------------------------------------------------------
			
			
			foreignKey.setFkName(fkName);
			foreignKey.setFkColumnName(fkColumnName.toUpperCase());
			String[] string = fkColumnName.split(GlobalConstant.STRING_SPLIT_SIGN);
			String newFkColumnName = "";
			for (int i = 0; i < string.length - 1; i++) {
				if (i == string.length - 2) {
					newFkColumnName = newFkColumnName + string[i];
				} else {
					newFkColumnName = newFkColumnName + string[i] + GlobalConstant.STRING_SPLIT_SIGN;
				}
			}

			foreignKey.setForeignPropertyName(Table2Entity.columnNameToPropertyName(newFkColumnName));
            foreignKey.setForeignPropertyNames(Inflector.getInstance().pluralize(foreignKey.getForeignPropertyName()));
			
			foreignKey.setForeignKeyRemarks(foreignKeyRemarks);
			foreignKey.setForeignKeySerial(Short.parseShort(foreignKeySerial)); // KEY_SEQ

			foreignKey.setDatabaseDataType(foreignKeyDataType);

			String pkTableName = foreignKeyResultSet.getString("PKTABLE_NAME").toUpperCase();
			Table referencedRelationTable = new Table();
			referencedRelationTable = TableBuilder.tablesCache.get(pkTableName);
			foreignKey.setReferencedRelationTable(referencedRelationTable);
            if(!foreignKey.getRelevanceType().equals(GlobalConstant.FK_INDEPENDENT_RELEVANCE)){
            	foreignKeys.add(foreignKey);
            }
		}
		return foreignKeys;
	}

	@Override
	public List<ForeignKey> call() throws Exception {

		return buildForeignKeyByTableName(databaseName, tableName);
	}

}
