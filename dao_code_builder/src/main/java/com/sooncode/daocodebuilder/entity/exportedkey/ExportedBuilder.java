package com.sooncode.daocodebuilder.entity.exportedkey;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.table.TableBuilder;
import com.sooncode.daocodebuilder.entity.util.Builder;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;
 

/**
 * 
 * @author hechen
 *
 */
public class ExportedBuilder extends  Builder implements Callable<List<ExportedKey>>   {
	 
	private List<ExportedKey> exportedKeys;
	

	public List<ExportedKey> getExportedKeys() {
		ExecutorService pool = Executors.newFixedThreadPool(1); // 线程池 数量为1

		Callable<List<ExportedKey>> c = new ExportedBuilder();

		Future<List<ExportedKey>> future = pool.submit(c);

		// 关闭线程池
		pool.shutdown();

		try {
			this.exportedKeys = future.get();
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
		} catch (ExecutionException e) {
			 
			e.printStackTrace();
		}
		
		
		
		return exportedKeys;
	}


	 


	 
	
	
	public static  List<ExportedKey>  buildExportedKeyByTableName(String databaseName, String tableName  ) throws SQLException {
		connection = DBConnectionUtils.getJDBCConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		List<ExportedKey> exportedKeys = new ArrayList<ExportedKey>();
		ResultSet exportedKeysResultSet = databaseMetaData.getExportedKeys(databaseName, null, tableName);
		while (exportedKeysResultSet.next()) {// 遍历某个表的外键


			short updateRule = Short.parseShort(exportedKeysResultSet.getString("UPDATE_RULE"));
			short deleteRule = Short.parseShort(exportedKeysResultSet.getString("DELETE_RULE"));

			short deferrability = Short.parseShort(exportedKeysResultSet.getString("DEFERRABILITY"));
			String fkName = exportedKeysResultSet.getString("FK_NAME").toUpperCase();
			String fkColumnName = exportedKeysResultSet.getString("FKCOLUMN_NAME").toUpperCase();
			ExportedKey exportedKey  = new ExportedKey();

			exportedKey.setDeferrability(deferrability);
			exportedKey.setDeleteRule(deleteRule);
			exportedKey.setUpdateRule(updateRule);
			exportedKey.setFkName(fkName);
			exportedKey.setFkColumnName(fkColumnName);
			
			
			 
				    String	fkTableName = exportedKeysResultSet.getString("FKTABLE_NAME").toUpperCase();
					Table exportedTable = new Table();
					exportedTable = TableBuilder.tablesCache.get(fkTableName);
					exportedKey.setExportedTable(exportedTable); 
			if(exportedKey.getRelevanceType().equals(GlobalConstant.FK_IDIRECTIONAL_RELEVANCE)){
				exportedKeys.add(exportedKey);
			}
		}
		return exportedKeys;
	}


	@Override
	public List<ExportedKey> call() throws Exception {
		return buildExportedKeyByTableName(databaseName,tableName );
	}


	 

	 
	 
}
