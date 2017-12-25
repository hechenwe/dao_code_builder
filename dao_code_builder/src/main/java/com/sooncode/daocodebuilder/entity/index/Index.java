package com.sooncode.daocodebuilder.entity.index;

import com.sooncode.daocodebuilder.entity.util.Table2Entity;

/**
 * 索引
 * @author  sooncode
 */
public class Index {

	/** 索引的名称 */
	private String indexName;
	
	/** 非唯一索引 */
	private boolean nonUnique;
	
	/** 索引目录（可能为空） */
	private String indexQualifier;

	/** 非唯一索引 */
	private short type;

	/** 列名 */
	private String columnName;
	
	private String propertyName;

	/** 列排序顺序:升序还是降序 */
	private short ordinalPosition;

	/** 列排序顺序:升序还是降序 */
	private String ascOrDesc;

	/** 基数 */
	private int cardinality;
	
	//----------------------------------------------------------------------------------

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexQualifier() {
		return indexQualifier;
	}

	public void setIndexQualifier(String indexQualifier) {
		this.indexQualifier = indexQualifier;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public short getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(short ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getAscOrDesc() {
		return ascOrDesc;
	}

	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}

	public boolean isNonUnique() {
		return nonUnique;
	}

	public void setNonUnique(boolean nonUnique) {
		this.nonUnique = nonUnique;
	}

	
	
	
	public String getPropertyName() {
		propertyName=Table2Entity.columnNameToPropertyName(columnName);
		return propertyName;
	}

	public Index(String indexName, boolean nonUnique, String indexQualifier, short type, String columnName, short ordinalPosition, String ascOrDesc, int cardinality) {
		super();
		this.indexName = indexName;
		this.nonUnique = nonUnique;
		this.indexQualifier = indexQualifier;
		this.type = type;
		this.columnName = columnName;
		this.ordinalPosition = ordinalPosition;
		this.ascOrDesc = ascOrDesc;
		this.cardinality = cardinality;
	}

	 
}
