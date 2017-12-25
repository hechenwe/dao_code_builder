package com.sooncode.daocodebuilder.entity.index;

import java.sql.Connection;
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

import com.sooncode.daocodebuilder.entity.util.Builder;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;

public class IndexBuilder extends  Builder  implements Callable<List<Index>> {
	/**
	 * 数据库连接对象
	 */
	protected static  Connection connection;
	private List<Index> indexs ;
	
	public List<Index> getIndexs() {
		
		ExecutorService pool = Executors.newFixedThreadPool(1); // 线程池 数量为1

		Callable<List<Index>> c = new IndexBuilder();

		Future<List<Index>> future = pool.submit(c);

		// 关闭线程池
		pool.shutdown();

		try {
			this.indexs = future.get();
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
		} catch (ExecutionException e) {
			 
			e.printStackTrace();
		}
		
		return indexs;
	}
 
	
	/**
	 * 查询表的所有索引
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<Index> buildIndex(String databaseName, String tableName) throws SQLException {
		connection = DBConnectionUtils.getJDBCConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		List<Index> indexes = new ArrayList<Index>();
		ResultSet indexResultSet = databaseMetaData.getIndexInfo(databaseName, null, tableName, true, true);
		try {
			while (indexResultSet.next()) {// 遍历某个表的外键

				String indexName = indexResultSet.getString("INDEX_NAME").toLowerCase();// 索引的名称
				String columnName = indexResultSet.getString("COLUMN_NAME").toUpperCase();// 列名
				boolean nonUnique = indexResultSet.getBoolean("NON_UNIQUE");// 非唯一索引
				String indexQualifier = indexResultSet.getString("INDEX_QUALIFIER");// 索引目录（可能为空）
				short type = indexResultSet.getShort("TYPE");// 索引类型
				short ordinalPosition = indexResultSet.getShort("ORDINAL_POSITION");// 在索引列顺序号
				String ascOrDesc = indexResultSet.getString("ASC_OR_DESC");// 列排序顺序:升序还是降序
				int cardinality = indexResultSet.getInt("CARDINALITY"); // 基数

				Index index = new Index(indexName, nonUnique, indexQualifier, type, columnName, ordinalPosition, ascOrDesc, cardinality);
				indexes.add(index);
			}
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		return indexes;
	}

	@Override
	public List<Index> call() throws Exception {
		 
		return buildIndex(databaseName,tableName);
	}
}
