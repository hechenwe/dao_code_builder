package com.sooncode.daocodebuilder.service.modle.many2many;

import com.sooncode.daocodebuilder.entity.table.Table;

public class MiddleAndFkTable {
	private Table middleTable;
	private Table fkTable;

	public Table getMiddleTable() {
		return middleTable;
	}

	public void setMiddleTable(Table middleTable) {
		this.middleTable = middleTable;
	}

	public Table getFkTable() {
		return fkTable;
	}

	public void setFkTable(Table fkTable) {
		this.fkTable = fkTable;
	}

}
