package com.sooncode.daocodebuilder.entity.table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sooncode.daocodebuilder.entity.column.Column;
import com.sooncode.daocodebuilder.entity.column.ColumnBuilder;
import com.sooncode.daocodebuilder.entity.exportedkey.ExportedBuilder;
import com.sooncode.daocodebuilder.entity.exportedkey.ExportedKey;
import com.sooncode.daocodebuilder.entity.foreignkey.ForeignKey;
import com.sooncode.daocodebuilder.entity.foreignkey.ForeignKeyBuilder;
import com.sooncode.daocodebuilder.entity.index.Index;
import com.sooncode.daocodebuilder.entity.index.IndexBuilder;
import com.sooncode.daocodebuilder.entity.primarykey.PrimaryKeyBuilder;
import com.sooncode.daocodebuilder.entity.util.Builder;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;
import com.sooncode.daocodebuilder.entity.util.Table2Entity;
import com.sooncode.daocodebuilder.entity.util.config.Constant;
import com.sooncode.daocodebuilder.entity.util.config.DBConstant;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;
import com.sooncode.daocodebuilder.service.build.Project;
import com.sooncode.daocodebuilder.util.Inflector;

public class TableBuilder {
	/**
	 * 数据库连接对象
	 */
	protected static Connection connection;

	/**
	 * 緩存
	 */
	public static Map<String, Table> tablesCache;

	private static Logger logger = Logger.getLogger("TableBuilder.class");
	private static Map<Integer, String> capital;
	static {
		Map<Integer, String> map = new HashMap<>();
		String[] a = { "A", "B", "C", "D", };// "E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		int n = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				for (int k = 0; k < a.length; k++) {
					map.put(n, a[i] + a[j] + a[k]);
					n++;
				}
			}
		}
		capital = map;
		try {
			getTablesCache();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// -------------------------------------------------------------------------------
	public static Map<String, Table> getTablesCache() throws SQLException {

		if (tablesCache != null) {
			return tablesCache;
		}

		long startTime = System.currentTimeMillis();// 获取当前时间
		Map<String, Table> map = new HashMap<String, Table>();
		logger.info("*****************构建Table**********************");
		connection = DBConnectionUtils.getJDBCConnection();

		DatabaseMetaData databaseMetaData;
		try {
			databaseMetaData = connection.getMetaData();
			String[] types = { "Table" };
			ResultSet tableSet = databaseMetaData.getTables(DBConstant.DATA_BASE, null, "%", types);//

			int tableSiz = 0;
			while (tableSet.next()) { // 遍历数据库的表

				String tableName = tableSet.getString("TABLE_NAME").toUpperCase();
				String tableRemarks = tableSet.getString("REMARKS");// 表注释
				String str[] = tableRemarks.split("_");
				tableRemarks = "";
				if (str.length == 1) {
					tableRemarks = str[0];
				} else {

					for (int i = 1; i < str.length; i++) {
						if (i == 1) {
							tableRemarks = tableRemarks + str[i];
						} else {
							tableRemarks = tableRemarks + "_" + str[i];
						}
					}
				}
				if (!tableRemarks.contains(Constant.MAP_STRATEGY)) { // 过滤掉 非映射表

					Table table = new Table();

					table.setTableName(tableName.toUpperCase());
					table.setEntityName(Table2Entity.tableNameToClassName(tableName));
					String defaultObjectName = table.getEntityName().substring(0, 1).toLowerCase() + table.getEntityName().substring(1);
					table.setDefaultObjectName(defaultObjectName);
					table.setDefaultObjectNames(Inflector.getInstance().pluralize(table.getDefaultObjectName()));
					table.setTableRemarks(tableRemarks);
					table.setModuleName(tableName.split(GlobalConstant.STRING_SPLIT_SIGN)[0]);
					table.setAbbreviation(capital.get(tableSiz));
					tableSiz++;
					String entityPackageName = new Project().getEntityPackageName(table);
					table.setEntityPackageName(entityPackageName);
					map.put(tableName, table);
				}
			}

		} catch (Exception e) {

			connection.close();

			e.printStackTrace();
		}

		tablesCache = map;
		long endTime = System.currentTimeMillis();
		logger.info("加载数据库所有表，所用时间：" + (endTime - startTime) + "ms");
		connection.close();
		return tablesCache;
	}

	// ------------------------------------------------------------------------------------------------------------
	/**
	 * 深度构建 -- 多线程
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static Table buildTable(String tableName) throws Exception {

		Table table = tablesCache.get(tableName.toUpperCase());
		table.setPrimaryKey(PrimaryKeyBuilder.queryPrimaryKeyByTableName(DBConstant.DATA_BASE, tableName));

		Builder.databaseName = DBConstant.DATA_BASE;
		Builder.tableName = tableName;
		// ------------------------------------------------------------------------------------------
		long t1 = System.currentTimeMillis();

		ColumnBuilder columnBuilder = new ColumnBuilder();
		List<Column> columns = columnBuilder.getColumns();
		Map<String, Column> columnsMap = new HashMap<>();
		if (columns != null && columns.size() >= 0) {
			for (Column column : columns) {
				columnsMap.put(column.getColumnName(), column);
			}
		}
		table.setColumnsMap(columnsMap);
		table.setColumns(columns);

		long t2 = System.currentTimeMillis();
		logger.info("获取字段   耗时 ：" + (t2 - t1));
		// ------------------------------------------------------------------------------------------
		ForeignKeyBuilder foreignKeyBuilder = new ForeignKeyBuilder();
		List<ForeignKey> foreignKeys = foreignKeyBuilder.getForeignKeys();
		Map<String, ForeignKey> foreignKeysMap = new HashMap<>();
		if (foreignKeys != null && foreignKeys.size() >= 0) {
			for (ForeignKey foreignKey : foreignKeys) {
				foreignKeysMap.put(foreignKey.getFkColumnName(), foreignKey);
			}
		}
		table.setForeignKeysMap(foreignKeysMap);
		table.setForeignKeys(foreignKeys);

		long t3 = System.currentTimeMillis();
		logger.info("获取外键   耗时 ：" + (t3 - t2));
		// ------------------------------------------------------------------------------------------

		ExportedBuilder exportedBuilder = new ExportedBuilder();
		List<ExportedKey> exportedKeys = exportedBuilder.getExportedKeys();
		Map<String, ExportedKey> exportedKeysMap = new HashMap<>();
		if (exportedKeys != null && exportedKeys.size() >= 0) {
			for (ExportedKey exportedKey : exportedKeys) {
				exportedKeysMap.put(exportedKey.getFkColumnName(), exportedKey);
			}
		}
		table.setExportedKeysMap(exportedKeysMap);
		table.setExportedKeys(exportedKeys);

		long t4 = System.currentTimeMillis();
		logger.info("获取被参照关系  耗时 ：" + (t4 - t3));
		// ------------------------------------------------------------------------------------------

		IndexBuilder indexBuilder = new IndexBuilder();
		List<Index> indexs = indexBuilder.getIndexs();
		Map<String, Index> indexsMap = new HashMap<>();
		if (indexs != null && indexs.size() >= 0) {
			for (Index index : indexs) {
				indexsMap.put(index.getColumnName(), index);
			}
		}
		table.setExportedKeysMap(exportedKeysMap);
		table.setIndexes(indexs);

		long t5 = System.currentTimeMillis();
		logger.info("获取被参照关系  耗时 ：" + (t5 - t4));

		// -----------------------------设置fk是否唯一-----------------------------------------

		List<ForeignKey> foreignKeysNew = new ArrayList<ForeignKey>();
		if (table.getForeignKeys() != null && table.getForeignKeys().size() > 0) {
			for (ForeignKey fk : table.getForeignKeys()) {
				Index index = indexsMap.get(fk.getFkColumnName());
				boolean bool = index == null ? true : index.isNonUnique(); //
				fk.setNonUnique(bool);
				ForeignKey fkNew = fk;
				foreignKeysNew.add(fkNew);
			}
		}

		long t6 = System.currentTimeMillis();
		logger.info("深度构造一共使用时间 ：" + (t6 - t1));
		table.setForeignKeys(foreignKeysNew);// 更新 table.foreignKeys ;

		tablesCache.put(tableName, table);// 更新map
		return table;
	}

}
