package com.sooncode.daocodebuilder.entity.column;

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

import com.sooncode.daocodebuilder.entity.util.Builder;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;
import com.sooncode.daocodebuilder.entity.util.Jdbc2Java;
import com.sooncode.daocodebuilder.entity.util.Table2Entity;
import com.sooncode.daocodebuilder.entity.util.config.Constant;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;

public class ColumnBuilder extends Builder implements Callable<List<Column>> {

	private List<Column> columns;
	
	
	
	public List<Column> getColumns() {
		
		ExecutorService pool = Executors.newFixedThreadPool(2); // 线程池 数量为1

		Callable<List<Column>> c = new ColumnBuilder();

		Future<List<Column>> future = pool.submit(c);

		// 关闭线程池
		pool.shutdown();

		try {
			this.columns = future.get();
			
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
		} catch (ExecutionException e) {
			 
			e.printStackTrace();
		}
		
		return this.columns;
	}
	 
	
	/**
	 * 查询数据库表的所有字段 构造 “字段属性模型”
	 * 
	 * @param databaseName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static List<Column> buildColumnByTableName(String databaseName, String tableName) throws SQLException {

		connection = DBConnectionUtils.getJDBCConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		//---索引---
		Map<String,Object> indexMap = new HashMap<>();
		ResultSet indexResultSet = databaseMetaData.getIndexInfo(databaseName, null, tableName, true, true);
		while (indexResultSet.next()) {// 遍历某个表的外键
            
			String columnName = indexResultSet.getString("COLUMN_NAME").toUpperCase();// 列名(大写)
			boolean nonUnique = indexResultSet.getBoolean("NON_UNIQUE");// 非唯一索引
			if(nonUnique){
				indexMap.put(columnName,"NO");
				
			}else{
				
				indexMap.put(columnName, "YES");
			}
			
		}
		//---字段---
		List<Column> columnes = new ArrayList<Column>();
		ResultSet columnSet = databaseMetaData.getColumns(databaseName, "%", tableName, "%");
		while (columnSet.next()) { // 遍历某个表的字段

			if(! columnSet.getString("REMARKS").contains(Constant.MAP_STRATEGY)){ //过滤掉 非映射字段 
				
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
				
				String isUnique = (String) indexMap.get(columnName.toUpperCase());
				if(isUnique !=null && isUnique.equals("YES")){
					column.setIsOnly("YES");
				}else{
					column.setIsOnly("NO");
				}
				
				columnes.add(column);
				
			}
			
		}

		return columnes;
	}
	
	 
//-------------------------------------------------------------
 
@Override
public List<Column> call() throws Exception {
	 
	return buildColumnByTableName( databaseName,  tableName);
	
	
}
	
	

}
