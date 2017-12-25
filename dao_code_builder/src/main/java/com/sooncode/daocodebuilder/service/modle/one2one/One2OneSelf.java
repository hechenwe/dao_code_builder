package com.sooncode.daocodebuilder.service.modle.one2one;

import java.util.List;

import com.sooncode.daocodebuilder.entity.foreignkey.ForeignKey;
import com.sooncode.daocodebuilder.entity.table.Table;

public class One2OneSelf {
	/**
	 * 主表
	 */
private Table mainTable;
/**
 * 自连接的字段
 */
private List<ForeignKey> foreignKeys;


public Table getMainTable() {
	return mainTable;
}
public void setMainTable(Table mainTable) {
	this.mainTable = mainTable;
}
public List<ForeignKey> getForeignKeys() {
	return foreignKeys;
}
public void setForeignKeys(List<ForeignKey> foreignKeys) {
	this.foreignKeys = foreignKeys;
}
 
 



}
