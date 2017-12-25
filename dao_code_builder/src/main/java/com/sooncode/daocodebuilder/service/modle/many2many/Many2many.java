package com.sooncode.daocodebuilder.service.modle.many2many;

import java.util.List;

import com.sooncode.daocodebuilder.entity.table.Table;

public class Many2many {
	/**
	 * 主表
	 */
	private Table mainTable;
	/**
	 * 中间表 和 次 表
	 */
	private List<MiddleAndFkTable> middleAndFkTables;

	public Table getMainTable() {
		return mainTable;
	}

	public void setMainTable(Table mainTable) {
		this.mainTable = mainTable;
	}

	 
	public List<MiddleAndFkTable> getMiddleAndFkTables() {
		return middleAndFkTables;
	}

	public void setMiddleAndFkTables(List<MiddleAndFkTable> middleAndFkTables) {
		this.middleAndFkTables = middleAndFkTables;
	}

	  
}
