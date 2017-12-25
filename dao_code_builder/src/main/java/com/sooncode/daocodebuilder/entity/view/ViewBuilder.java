package com.sooncode.daocodebuilder.entity.view;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.sooncode.daocodebuilder.entity.util.Jdbc2Java;
import com.sooncode.daocodebuilder.entity.util.Table2Entity;
import com.sooncode.daocodebuilder.entity.util.config.Constant;
import com.sooncode.daocodebuilder.entity.util.config.DBConstant;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;
import com.sooncode.daocodebuilder.service.build.Project;
import com.sooncode.daocodebuilder.util.Inflector;

public class ViewBuilder {
	/**
	 * 数据库连接对象
	 */
	protected static Connection connection;

	/**
	 * 緩存
	 */

	private static Logger logger = Logger.getLogger("TableBuilder.class");

	/**
	 * 获取所用视图
	 * 
	 * @return
	 */
	public static List<View> getViewes() {
		connection = DBConnectionUtils.getJDBCConnection();

		DatabaseMetaData databaseMetaData;
		try {
			databaseMetaData = connection.getMetaData();
			String[] types = { "VIEW" };

			ResultSet viewSet = databaseMetaData.getTables(DBConstant.DATA_BASE, null, "%", types);//
			List<View> viewes = new ArrayList<>();

			while (viewSet.next()) { // 遍历数据库的表

				String viewName = viewSet.getString("TABLE_NAME").toUpperCase();
				List<Column> columns = getColumns(databaseMetaData, DBConstant.DATA_BASE, viewName);
				View view = new View();
				view.setViewCode(viewName);
				view.setColumns(columns);
				viewes.add(view);

			}
			return viewes;
		} catch (Exception e) {
			return null;
		}
	}

	private static List<Column> getColumns(DatabaseMetaData databaseMetaData, String database, String viewName) throws Exception, SQLException {
		List<Column> columnes = new ArrayList<Column>();
		ResultSet columnSet = databaseMetaData.getColumns(database, "%", viewName, "%");
		while (columnSet.next()) { // 遍历某个表的字段

			String columnRemarks = columnSet.getString("REMARKS").replaceAll(GlobalConstant.STRING_SPLIT_SIGN, GlobalConstant.EMPTY_STRING);

			String columnName = columnSet.getString("COLUMN_NAME".toUpperCase());
			String columnType = columnSet.getString("TYPE_NAME");
			int dataType = Integer.parseInt(columnSet.getString("DATA_TYPE"));

			String javaDataType = Jdbc2Java.getJavaData().get(Jdbc2Java.getJdbcData().get("" + dataType));

			String isAutoinCrement = columnSet.getString("IS_AUTOINCREMENT");

			Column column = new Column();
			column.setColumnName(columnName.toUpperCase());
			column.setPropertyName(Table2Entity.columnNameToPropertyName(columnName));
			column.setDatabaseDataType(columnType);
			column.setJavaDataType(javaDataType);
			column.setColumnRemarks(columnRemarks);
			column.setIsAutoinCrement(isAutoinCrement);
			column.setColumnLength(column.getColumnName().length());
			column.setPropertyLength(column.getPropertyName().length());
			columnes.add(column);

		}
		return columnes;
	}

	public static Map<String, View> getViewesMap() {
        
		Map<String, View> map = new HashMap<>();
		for (View view : getViewes()) {
              map.put(view.getViewCode(),view);
		}
      return map;
	}
}
