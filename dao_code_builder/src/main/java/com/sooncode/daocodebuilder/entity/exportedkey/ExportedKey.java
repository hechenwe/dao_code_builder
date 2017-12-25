package com.sooncode.daocodebuilder.entity.exportedkey;

import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;

/**
 * 主键被其他表参照的信息
 * 
 * @author 臣
 * 
 */
public class ExportedKey {
	
	 
	/**
	 *  参照表  
	 *
	 */
	private Table exportedTable;
	
	/** 
	 * 被参照的外键名
	 */
	private String fkName;  
	
	/**
	 * 关联类型 ： 双向关联（默认）;单项关联（unidirectional  外键名中必须包含“_unidirectional” 字符串）;不关联 （independent）
	 */
	private String relevanceType;
	
	/**
	 * 参照表的外键字段
	 */
	private String fkColumnName;//
	 
	/** 
	 * 更新主键时外键发生的变化 
	 * 
	 */
	private short updateRule;
	
	
	/**
	 *  删除主键时外键发生的变化 
	 *  
	 */
	private short deleteRule;
	
	
	/** 
	 * 是否可以将对外键约束的评估延迟到提交时间   
	 * 
	 */
	private short deferrability;


     

	
	public Table getExportedTable() {
		return exportedTable;
	}

	public void setExportedTable(Table exportedTable) {
		this.exportedTable = exportedTable;
	}

	public short getUpdateRule() {
		return updateRule;
	}

	public void setUpdateRule(short updateRule) {
		this.updateRule = updateRule;
	}

	public short getDeleteRule() {
		return deleteRule;
	}

	public void setDeleteRule(short deleteRule) {
		this.deleteRule = deleteRule;
	}

	public short getDeferrability() {
		return deferrability;
	}

	public void setDeferrability(short deferrability) {
		this.deferrability = deferrability;
	}

	public String getFkName() {
		return fkName;
	}

	public void setFkName(String fkName) {
		this.fkName = fkName;
	}

	public String getFkColumnName() {
		return fkColumnName;
	}

	public void setFkColumnName(String fkColumnName) {
		this.fkColumnName = fkColumnName;
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
