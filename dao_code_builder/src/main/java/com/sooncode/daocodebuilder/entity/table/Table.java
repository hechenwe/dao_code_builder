package com.sooncode.daocodebuilder.entity.table;

import java.util.List;
import java.util.Map;

import com.sooncode.daocodebuilder.entity.column.Column;
import com.sooncode.daocodebuilder.entity.exportedkey.ExportedKey;
import com.sooncode.daocodebuilder.entity.foreignkey.ForeignKey;
import com.sooncode.daocodebuilder.entity.index.Index;
import com.sooncode.daocodebuilder.entity.primarykey.PrimaryKey;

public class Table implements Cloneable {

	/** 数据库表名 */
	private String tableName;
	
	
	/** 简称 */
	private String abbreviation;

	
	/** 表注释 */
	private String tableRemarks;

	
	/** 所在模块名称 */
	private String moduleName;

	
	/** 对应的实体名称 */
	private String entityName;
	
	
	/**
	 * 实体类的包名
	 */
	private String entityPackageName;

	
	/** 默认的对象名称 */
	private String defaultObjectName;

	
	/** 默认的对象名称(复数) */
	private String defaultObjectNames;

	
	/** 所有字段 */
	private List<Column> columns;

	
	private Map<String, Column> columnsMap;

	
	/** 主键 */
	private PrimaryKey primaryKey;
	
	
	private Map<String, PrimaryKey> primaryKeysMap;

	/** 所有外键 */
	private List<ForeignKey> foreignKeys;
	
	
	private Map<String, ForeignKey> foreignKeysMap;
	
	/** 所有参照建 （被其他表参照） */
	private List<ExportedKey> exportedKeys;
	
	
	private Map<String, ExportedKey> exportedKeysMap;

	/** 表的索引 */
	private List<Index> indexes;
	
	
	private Map<String, Index> indexesMap;

	/**
	 * 字段最长的值
	 */
	private Integer columnMaxLength;
	private Integer propertyMaxLength;

	
	/**
	 * 需要引用的类  全类名
	 */
	private Map<String,String> allClassName;
	
	// --------------------------------------------------------------------------------------
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	// --------------------------------------------------------------------------------------
	public String getTableRemarks() {
		return tableRemarks;
	}

	public void setTableRemarks(String tableRemarks) {
		this.tableRemarks = tableRemarks;
	}

	// --------------------------------------------------------------------------------------
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	// --------------------------------------------------------------------------------------
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	// --------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------
	public List<Index> getIndexes() {
		return indexes;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(List<ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	public List<ExportedKey> getExportedKeys() {
		return exportedKeys;
	}

	public void setExportedKeys(List<ExportedKey> exportedKeys) {
		this.exportedKeys = exportedKeys;
	}

	public void setIndexes(List<Index> indexes) {
		this.indexes = indexes;
	}

	// --------------------------------------------------------------------------------------

	public Map<String, PrimaryKey> getPrimaryKeysMap() {
		return primaryKeysMap;
	}

	public void setPrimaryKeysMap(Map<String, PrimaryKey> primaryKeysMap) {
		this.primaryKeysMap = primaryKeysMap;
	}

	public Map<String, ForeignKey> getForeignKeysMap() {
		return foreignKeysMap;
	}

	public void setForeignKeysMap(Map<String, ForeignKey> foreignKeysMap) {
		this.foreignKeysMap = foreignKeysMap;
	}

	public Map<String, ExportedKey> getExportedKeysMap() {
		return exportedKeysMap;
	}

	public void setExportedKeysMap(Map<String, ExportedKey> exportedKeysMap) {
		this.exportedKeysMap = exportedKeysMap;
	}

	public Map<String, Index> getIndexesMap() {
		return indexesMap;
	}

	public void setIndexesMap(Map<String, Index> indexesMap) {
		this.indexesMap = indexesMap;
	}

	public Map<String, Column> getColumnsMap() {
		return columnsMap;
	}

	public void setColumnsMap(Map<String, Column> columnsMap) {
		this.columnsMap = columnsMap;
	}

	public String getDefaultObjectName() {
		return defaultObjectName;
	}

	public void setDefaultObjectName(String defaultObjectName) {
		this.defaultObjectName = defaultObjectName;
	}

	public String getEntityPackageName() {
		return entityPackageName;
	}

	public void setEntityPackageName(String entityPackageName) {
		this.entityPackageName = entityPackageName;
	}

	public String getDefaultObjectNames() {
		return defaultObjectNames;
	}

	public void setDefaultObjectNames(String defaultObjectNames) {
		this.defaultObjectNames = defaultObjectNames;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	
	
	
	 

	public Map<String, String> getAllClassName() {
		return allClassName;
	}

	public void setAllClassName(Map<String, String> allClassName) {
		this.allClassName = allClassName;
	}

	public Integer getColumnMaxLength() {

		if (this.columns == null) {
			try {
				Table table = TableBuilder.buildTable(this.getTableName());
				TableBuilder.tablesCache.put(table.getTableName(), table);// 更新map
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		int maxLength = 0;
		for (int i = 0; i < this.columns.size(); i++) {
			if (maxLength < this.columns.get(i).getColumnLength()) {
				maxLength = this.columns.get(i).getColumnLength();
			}
		}
		this.columnMaxLength = maxLength;
		return columnMaxLength;

	}

	public Integer getPropertyMaxLength() {

		if (this.columns == null) {
			try {
				Table table = TableBuilder.buildTable(this.getTableName());
				TableBuilder.tablesCache.put(table.getTableName(), table);// 更新map
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		int maxLength = 0;
		for (int i = 0; i < this.columns.size(); i++) {
			if (maxLength < this.columns.get(i).getPropertyLength()) {
				maxLength = this.columns.get(i).getPropertyLength();
			}
		}
		this.propertyMaxLength = maxLength;
		return propertyMaxLength;

	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", tableRemarks=" + tableRemarks + ", moduleName=" + moduleName + ", entityName=" + entityName + ", entityPackageName=" + entityPackageName + ", defaultObjectName=" + defaultObjectName + ", columns=" + columns + ", columnsMap=" + columnsMap + ", primaryKey=" + primaryKey + ", primaryKeysMap=" + primaryKeysMap + ", foreignKeys=" + foreignKeys + ", foreignKeysMap=" + foreignKeysMap + ", exportedKeys=" + exportedKeys + ", exportedKeysMap=" + exportedKeysMap + ", indexes=" + indexes + ", indexesMap=" + indexesMap + "]";
	}

	/**
	 * 浅克隆
	 */
	@Override
	public Object clone() {
		Table table = null;
		try {
			table = (Table) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return table;
	}

}
