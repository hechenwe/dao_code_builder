package com.sooncode.daocodebuilder.entity.foreignkey;



import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;

/**
 * 外键
 * @author sooncode
 *
 */
public class ForeignKey {
	/**
	 * 外键名
	 */
	private String fkName;
	
	/**外键字段名称 */
	private String fkColumnName; 
	
	/**
	 * 关联类型 ： 双向关联（默认）;单项关联（unidirectional  外键名中必须包含“_unidirectional” 字符串）;不关联 （independent）
	 */
	private String relevanceType;
	
	/**外属性名称*/
	private String foreignPropertyName;
	
	
	
	/**外属性名称(复数)*/
	private String foreignPropertyNames;
	
	
	
	/**外键的注释 */
	private String foreignKeyRemarks;
	
	
	
	/**数据库的数据类型） */
	private String DatabaseDataType;
	
	
	
	/**Java数据类型*/
	private String JavaDataType;
	
	
	
	/** 外键和外属性的序列号 */
	private short foreignKeySerial; 
	
	
	/**被参照表*/
	private Table referencedRelationTable;

    /**
     * 是否不唯一
     */
	private boolean isNonUnique ;


	public String getFkColumnName() {
		return fkColumnName;
	}


	public void setFkColumnName(String fkColumnName) {
		this.fkColumnName = fkColumnName;
	}


	public String getForeignPropertyName() {
		return foreignPropertyName;
	}


	public void setForeignPropertyName(String foreignPropertyName) {
		this.foreignPropertyName = foreignPropertyName;
	}


	public String getForeignKeyRemarks() {
		return foreignKeyRemarks;
	}


	public void setForeignKeyRemarks(String foreignKeyRemarks) {
		this.foreignKeyRemarks = foreignKeyRemarks;
	}


	public String getDatabaseDataType() {
		return DatabaseDataType;
	}


	public void setDatabaseDataType(String databaseDataType) {
		DatabaseDataType = databaseDataType;
	}


	public String getJavaDataType() {
		return JavaDataType;
	}


	public void setJavaDataType(String javaDataType) {
		JavaDataType = javaDataType;
	}


	public short getForeignKeySerial() {
		return foreignKeySerial;
	}


	public void setForeignKeySerial(short foreignKeySerial) {
		this.foreignKeySerial = foreignKeySerial;
	}


	public Table getReferencedRelationTable() {
		return referencedRelationTable;
	}


	public void setReferencedRelationTable(Table referencedRelationTable) {
		this.referencedRelationTable = referencedRelationTable;
	}


	public String getFkName() {
		return fkName;
	}


	public void setFkName(String fkName) {
		this.fkName = fkName;
	}


	public boolean isNonUnique() {
		return isNonUnique;
	}


	public void setNonUnique(boolean isNonUnique) {
		this.isNonUnique = isNonUnique;
	}


	public String getForeignPropertyNames() {
		return foreignPropertyNames;
	}


	public void setForeignPropertyNames(String foreignPropertyNames) {
		this.foreignPropertyNames = foreignPropertyNames;
	}


	public String getRelevanceType() {
		if(this.fkName.toUpperCase().contains(GlobalConstant.FK_UNIDIRECTIONAL_RELEVANCE.toUpperCase())){
			this.relevanceType = GlobalConstant.FK_UNIDIRECTIONAL_RELEVANCE;
			return this.relevanceType;
		}else if(this.fkName.toUpperCase().contains(GlobalConstant.FK_INDEPENDENT_RELEVANCE.toUpperCase())){
			this.relevanceType =GlobalConstant.FK_INDEPENDENT_RELEVANCE;
			return relevanceType;
		}else{
			this.relevanceType =GlobalConstant.FK_IDIRECTIONAL_RELEVANCE;
			return relevanceType;
		}
		
	}


	 


	 
	 
	 

	 
	
}
