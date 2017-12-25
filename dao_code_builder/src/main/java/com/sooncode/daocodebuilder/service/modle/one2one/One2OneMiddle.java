package com.sooncode.daocodebuilder.service.modle.one2one;

import java.util.List;

import com.sooncode.daocodebuilder.entity.table.Table;

public class One2OneMiddle {
	
	/**
	 * 主表
	 */
	private Table mainTable;
	/**
	 * 1:1 中间表
	 */
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
