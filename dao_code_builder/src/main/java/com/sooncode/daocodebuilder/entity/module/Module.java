package com.sooncode.daocodebuilder.entity.module;

import java.util.List;

import com.sooncode.daocodebuilder.entity.table.Table;

/**
 * 模块
 * 
 * @author hechen
 *
 */
public class Module {
	
	
	private String moduleName;
	private String moduleRemarks;
	private int  tableSize;
	private List<Table> tables;
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleRemarks() {
		return moduleRemarks;
	}
	public void setModuleRemarks(String moduleRemarks) {
		this.moduleRemarks = moduleRemarks;
	}
	
	public int getTableSize() {
		return tableSize;
	}
	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}
	
	
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	@Override
	public String toString() {
		return "Module [moduleName=" + moduleName + ", moduleRemarks=" + moduleRemarks + "]";
	}
	
	
	
}
