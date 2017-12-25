package com.sooncode.daocodebuilder.service.modle.many2many;

import java.util.List;

import com.sooncode.daocodebuilder.entity.table.Table;

public class Many2manySelf {
	
	private Table mainTable;
	private List<Table> middleTables;

	public Table getMainTable() {
		return mainTable;
	}

	public void setMainTable(Table mainTable) {
		this.mainTable = mainTable;
	}

	public List<Table> getMiddleTables() {
		return middleTables;
	}

	public void setMiddleTables(List<Table> middleTables) {
		this.middleTables = middleTables;
	}

}
