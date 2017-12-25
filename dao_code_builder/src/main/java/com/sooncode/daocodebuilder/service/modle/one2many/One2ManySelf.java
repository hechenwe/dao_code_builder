package com.sooncode.daocodebuilder.service.modle.one2many;

import java.util.List;

import com.sooncode.daocodebuilder.entity.exportedkey.ExportedKey;
import com.sooncode.daocodebuilder.entity.foreignkey.ForeignKey;
import com.sooncode.daocodebuilder.entity.table.Table;

/**
 * 1:n 模型 自我关联
 * 
 * @author hechen
 *
 */
public class One2ManySelf {

	private Table mainTable;
	/**
	 * 关联的外键
	 */
	private List<ExportedKey> exportedKeys;

	public Table getMainTable() {
		return mainTable;
	}

	public void setMainTable(Table mainTable) {
		this.mainTable = mainTable;
	}

	public List<ExportedKey> getExportedKeys() {
		return exportedKeys;
	}

	public void setExportedKeys(List<ExportedKey> exportedKeys) {
		this.exportedKeys = exportedKeys;
	}

 
}
