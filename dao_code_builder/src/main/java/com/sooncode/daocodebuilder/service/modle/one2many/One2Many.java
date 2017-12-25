package com.sooncode.daocodebuilder.service.modle.one2many;

import java.util.List;

import com.sooncode.daocodebuilder.entity.table.Table;

/**
 * 1:n 模型
 * 
 * @author hechen
 *
 */
public class One2Many {
	
	
	private Table mainTable;
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
