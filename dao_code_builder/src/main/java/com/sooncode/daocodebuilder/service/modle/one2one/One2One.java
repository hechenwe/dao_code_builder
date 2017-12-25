package com.sooncode.daocodebuilder.service.modle.one2one;

import java.util.List;

import com.sooncode.daocodebuilder.entity.table.Table;
/**
 * 1:1 模型  
 * @author hechen
 *
 */
public class One2One {
	/**
	 * 主表
	 */
	private Table mainTable;
	/**
	 * 被参照表
	 */
	private List<Table> tables;

	
	
	public Table getMainTable() {
		return mainTable;
	}

	public void setMainTable(Table mainTable) {
		this.mainTable = mainTable;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	 
	 
	 
	
	
}
