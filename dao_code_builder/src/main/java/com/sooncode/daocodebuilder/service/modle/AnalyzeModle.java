package com.sooncode.daocodebuilder.service.modle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import com.sooncode.daocodebuilder.entity.exportedkey.ExportedKey;
import com.sooncode.daocodebuilder.entity.foreignkey.ForeignKey;
import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.table.TableBuilder;
import com.sooncode.daocodebuilder.entity.util.config.Constant;
import com.sooncode.daocodebuilder.service.modle.many2many.Many2many;
import com.sooncode.daocodebuilder.service.modle.many2many.Many2manySelf;
import com.sooncode.daocodebuilder.service.modle.many2many.MiddleAndFkTable;
import com.sooncode.daocodebuilder.service.modle.one2many.One2Many;
import com.sooncode.daocodebuilder.service.modle.one2many.One2ManySelf;
import com.sooncode.daocodebuilder.service.modle.one2one.One2One;
import com.sooncode.daocodebuilder.service.modle.one2one.One2OneMiddle;
import com.sooncode.daocodebuilder.service.modle.one2one.One2OneSelf;

/**
 * 模型分析
 * 
 * @author hechen
 *
 */
public class AnalyzeModle {

	private static Logger logger = Logger.getLogger("AnalyzeModle.class");

	/**
	 * 1：1 模型 分析
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exceptio
	 *             n
	 */
	public static One2One anlyzeOne2One(Table mainTable) throws Exception {

		One2One one2One = new One2One();

		if (mainTable.getColumns() == null) {
			mainTable = TableBuilder.buildTable(mainTable.getTableName()); // 深度构建
		}
		one2One.setMainTable(mainTable);

		if (mainTable.getForeignKeys() != null && mainTable.getForeignKeys().size() > 0) {
			List<Table> tables = new ArrayList<Table>();
			List<ForeignKey> foreignKeys = new ArrayList<>();
			int n = 0;
			for (ForeignKey fk : mainTable.getForeignKeys()) {
				if (!fk.getReferencedRelationTable().getTableName().equals(mainTable.getTableName())) { //
					if (fk.getReferencedRelationTable().getColumns() == null) {
						TableBuilder.buildTable(fk.getReferencedRelationTable().getTableName());// 深度构建
					}
					Table table = new Table();
					table = (Table) fk.getReferencedRelationTable().clone();// 克隆
					table.setTableRemarks(fk.getForeignKeyRemarks());
					table.setDefaultObjectName(fk.getForeignPropertyName());
					tables.add(table);
					foreignKeys.add(fk);
					if (mainTable.getAllClassName() == null) {
						Map<String, String> allClassName = new HashMap<>();
						mainTable.setAllClassName(allClassName);
					}
					mainTable.getAllClassName().put(table.getEntityName(), table.getEntityPackageName() + "." + table.getEntityName());
				}
			}
			mainTable.setForeignKeys(foreignKeys);
			if (tables.size() > 0) {
				one2One.setTables(tables);
				one2One.setMainTable(mainTable);
			}
		}

		return one2One;
	}

	/**
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exception
	 */
	public static One2OneSelf anlyzeOne2OneSelf(Table mainTable) throws Exception {

		One2OneSelf one2OneSelf = new One2OneSelf();

		if (mainTable.getColumns() == null) {
			mainTable = TableBuilder.buildTable(mainTable.getTableName()); // 深度构建
		}
		one2OneSelf.setMainTable(mainTable);

		if (mainTable.getForeignKeys() != null && mainTable.getForeignKeys().size() > 0) {
			List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
			for (ForeignKey fk : mainTable.getForeignKeys()) {
				if (fk.isNonUnique() == false && fk.getReferencedRelationTable().getTableName().equals(mainTable.getTableName())) { //

					foreignKeys.add(fk);
				}

			}
			if (foreignKeys.size() > 0) {
				one2OneSelf.setForeignKeys(foreignKeys);
			}
		}

		return one2OneSelf;

	}

	/**
	 * 带中间表的1:1 模型
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exception
	 */
	public static One2OneMiddle anlyzeOne2OneMiddle(Table mainTable) throws Exception {
		One2OneMiddle one2OneMiddle = new One2OneMiddle();
		if (mainTable.getColumns() == null) {
			mainTable = TableBuilder.buildTable(mainTable.getTableName()); // 深度构建
		}
		one2OneMiddle.setMainTable(mainTable);
		List<ExportedKey> exportedKeys = mainTable.getExportedKeys();

		if (exportedKeys != null && exportedKeys.size() > 0) {
			List<Table> ekTables = new ArrayList<Table>();
			for (int i = 0; i < exportedKeys.size(); i++) {
				for (int j = i + 1; j < exportedKeys.size(); j++) {
					Table exTable = exportedKeys.get(i).getExportedTable();
					if (exTable.getTableName().equals(exportedKeys.get(j).getExportedTable().getTableName())) { // 找到中间表

						if (exTable.getColumns() == null) {
							TableBuilder.buildTable(exTable.getTableName()); // 深度构建
						}
						// Table exTable =
						// exportedKeys.get(i).getExportedTable();
						boolean bool1 = exTable.getForeignKeysMap().get(exportedKeys.get(i).getFkColumnName()).isNonUnique();
						boolean bool2 = exTable.getForeignKeysMap().get(exportedKeys.get(j).getFkColumnName()).isNonUnique();
						if (bool1 == false && bool2 == false) { // 判断是否是唯一索引
							ekTables.add(exTable);
						}
					}
				}
			}
			if (ekTables.size() > 0) {
				one2OneMiddle.setMiddleTables(ekTables);
			}
		}
		return one2OneMiddle;
	}

	/**
	 * 1:n 模型
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exception
	 */
	public static One2Many anlyzeOne2Many(Table mainTable) throws Exception {
		One2Many one2Many = new One2Many();
		if (mainTable.getColumns() == null) {
			mainTable = TableBuilder.buildTable(mainTable.getTableName()); // 深度构建
		}
		one2Many.setMainTable(mainTable);
		List<ExportedKey> exportedKeys = mainTable.getExportedKeys();

		if (exportedKeys != null && exportedKeys.size() > 0) {

			List<Table> ekTables = new ArrayList<Table>();
			for (int i = 0; i < exportedKeys.size(); i++) {
				if (exportedKeys.get(i).getExportedTable().getColumns() == null) {
					TableBuilder.buildTable(exportedKeys.get(i).getExportedTable().getTableName()); // 深度构建
				}
				Table ekTable = exportedKeys.get(i).getExportedTable();
				Table table = exportedKeys.get(i).getExportedTable();
				boolean bool1 = ekTable.getForeignKeysMap().get(exportedKeys.get(i).getFkColumnName()).isNonUnique();// 是否有唯一索引
				boolean bool2 = !table.getTableName().equals(mainTable.getTableName());//
				boolean bool3 = !table.getTableRemarks().contains(Constant.MIDDEL_TABLE);// 非映射的表
				boolean bool4 = true; //消除重复
				for (Table t : ekTables) {
					if(t.getTableName().equals(table.getTableName())){
						bool4 = false;
						break;
					}
				}
				if (bool1 == true && bool2 && bool3 && bool4) {
					ekTables.add(table);

					if (mainTable.getAllClassName() == null) {
						Map<String, String> allClassName = new HashMap<>();
						mainTable.setAllClassName(allClassName);
					}

					mainTable.getAllClassName().put(table.getEntityName(), table.getEntityPackageName() + "." + table.getEntityName());
				}

			}
			if (ekTables.size() > 0) {
				one2Many.setTables(ekTables);
			}
		}
		return one2Many;
	}

	/**
	 * 1:n 模型 自我关联
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exception
	 */
	public static One2ManySelf anlyzeOne2ManySelf(Table mainTable) throws Exception {
		One2ManySelf one2ManySelf = new One2ManySelf();
		if (mainTable.getColumns() == null) {
			mainTable = TableBuilder.buildTable(mainTable.getTableName()); // 深度构建
		}

		List<ExportedKey> exportedKeys = mainTable.getExportedKeys();

		if (exportedKeys != null && exportedKeys.size() > 0) {

			List<ExportedKey> eKeys = new ArrayList<ExportedKey>();
			for (int i = 0; i < exportedKeys.size(); i++) {

				if (exportedKeys.get(i).getExportedTable().getColumns() == null) {
					TableBuilder.buildTable(exportedKeys.get(i).getExportedTable().getTableName()); // 深度构建
				}

				Table ekTable = exportedKeys.get(i).getExportedTable();
				boolean bool = ekTable.getForeignKeysMap().get(exportedKeys.get(i).getFkColumnName()).isNonUnique();

				if (bool == true && exportedKeys.get(i).getExportedTable().getTableName().equals(mainTable.getTableName())) { // 判断是否是唯一索引

					eKeys.add(exportedKeys.get(i));
				}

			}
			if (eKeys.size() > 0) {
				one2ManySelf.setMainTable(mainTable);
				one2ManySelf.setExportedKeys(eKeys);
			}
		}
		return one2ManySelf;
	}

	/**
	 * n:m 模型
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exception
	 */
	public static Many2many anlyzeMany2many(Table mainTable) throws Exception {
		Many2many many2many = new Many2many();
		List<MiddleAndFkTable> middleAndFkTables = new ArrayList<MiddleAndFkTable>();
		if (mainTable.getColumns() == null) {
			mainTable = TableBuilder.buildTable(mainTable.getTableName()); // 深度构建
		}
		List<ExportedKey> exportedKeys = mainTable.getExportedKeys();

		if (exportedKeys != null && exportedKeys.size() > 0) {

			for (ExportedKey exportedKey : exportedKeys) {
				MiddleAndFkTable middleAndFkTable = new MiddleAndFkTable();
				if (exportedKey.getExportedTable().getColumns() == null) {
					TableBuilder.buildTable(exportedKey.getExportedTable().getTableName()); // 深度构建
				}
				if (exportedKey.getExportedTable().getTableRemarks().contains(Constant.MIDDEL_TABLE)) {// 中间表
					middleAndFkTable.setMiddleTable(exportedKey.getExportedTable());
					for (ForeignKey fk : exportedKey.getExportedTable().getForeignKeys()) {
						if (!fk.getReferencedRelationTable().getTableName().equals(mainTable.getTableName())) { // 次表
							Table fkTable = (Table) fk.getReferencedRelationTable().clone();
							fkTable.setTableRemarks(fk.getForeignKeyRemarks());
							fkTable.setDefaultObjectName(fk.getForeignPropertyName());
							fkTable.setDefaultObjectNames(fk.getForeignPropertyNames());
							middleAndFkTable.setFkTable(fkTable);
							middleAndFkTables.add(middleAndFkTable);
							if (mainTable.getAllClassName() == null) {
								Map<String, String> allClassName = new HashMap<>();
								mainTable.setAllClassName(allClassName);
							}
							mainTable.getAllClassName().put(fkTable.getEntityName(), fkTable.getEntityPackageName() + "." + fkTable.getEntityName());
							// System.out.println("AnalyzeModle.anlyzeMany2many()--------"+middleAndFkTable.getFkTable());
							break;
						}
					}
				}

			}
			if (middleAndFkTables.size() > 0) {
				many2many.setMainTable(mainTable);
				many2many.setMiddleAndFkTables(middleAndFkTables);
			}
		}

		return many2many;
	}

	/**
	 * m:n 自关联 模型 分析
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exception
	 */
	public static Many2manySelf anlyzeMany2manySelf(Table mainTable) throws Exception {
		Many2manySelf many2manySelf = new Many2manySelf();
		List<Table> middleTables = new ArrayList<Table>();
		if (mainTable.getColumns() == null) {
			mainTable = TableBuilder.buildTable(mainTable.getTableName()); // 深度构建
		}
		List<ExportedKey> exportedKeys = mainTable.getExportedKeys();

		if (exportedKeys != null && exportedKeys.size() > 0) {

			for (ExportedKey exportedKey : exportedKeys) { // 遍历 主表的 参照键

				if (exportedKey.getExportedTable().getColumns() == null) {
					TableBuilder.buildTable(exportedKey.getExportedTable().getTableName()); // 深度构建
				}
				if (exportedKey.getExportedTable().getTableRemarks().contains(Constant.MIDDEL_TABLE)) {// 获得中间表

					Table middleTable = exportedKey.getExportedTable();
					List<ForeignKey> middleTablefks = middleTable.getForeignKeys();
					boolean forFlag = false;
					for (int i = 0; i < middleTablefks.size(); i++) {
						for (int j = 1; j < middleTablefks.size() - i; j++) {
							if (middleTablefks.get(i).getReferencedRelationTable().getTableName().equals(middleTablefks.get(j).getReferencedRelationTable().getTableName())) { // 有2个外键关联同一个表
								boolean bool1 = middleTable.getForeignKeysMap().get(middleTablefks.get(i).getFkColumnName()).isNonUnique();
								boolean bool2 = middleTable.getForeignKeysMap().get(middleTablefks.get(j).getFkColumnName()).isNonUnique();
								if (bool1 == true && bool2 == true && forFlag == false) {
									forFlag = true;
									if (middleTables.size() == 1 && middleTables.get(0).getTableName().equals(exportedKey.getExportedTable().getTableName())) {
										break;
									} else {
										middleTables.add(exportedKey.getExportedTable());
										break;
									}
								}
							}
						}
					}
				}
			}
			if (middleTables.size() > 0) {
				many2manySelf.setMainTable(mainTable);
				many2manySelf.setMiddleTables(middleTables);
			}
		}

		return many2manySelf;
	}

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();// 获取当前时间
		for (Map.Entry<String, Table> entry : TableBuilder.tablesCache.entrySet()) {
			One2One one2One = anlyzeOne2One(entry.getValue());
			One2OneSelf one2OneSelf = anlyzeOne2OneSelf(entry.getValue());
			One2OneMiddle one2OneMiddle = anlyzeOne2OneMiddle(entry.getValue());
			One2Many one2Many = anlyzeOne2Many(entry.getValue());
			One2ManySelf one2ManySelf = anlyzeOne2ManySelf(entry.getValue());
			Many2many many2many = anlyzeMany2many(entry.getValue());
			Many2manySelf many2manySelf = anlyzeMany2manySelf(entry.getValue());
			System.out.println("--mainTable----------:" + one2One.getMainTable());
			System.out.println("--one2One------------:" + one2One.getTables());
			System.out.println("--one2OneSelf--------:" + one2OneSelf.getForeignKeys());
			System.out.println("--one2OneMiddle------:" + one2OneMiddle.getMiddleTables());
			System.out.println("--one2Many-----------:" + one2Many.getTables());
			System.out.println("--one2ManySelf-------:" + one2ManySelf.getMainTable());
			if (many2many.getMiddleAndFkTables() != null && many2many.getMiddleAndFkTables().size() > 0) {
				System.out.println("--many2Many----------:" + many2many.getMiddleAndFkTables().get(0).getMiddleTable());
			}
			System.out.println("--many2manySelf------:" + many2manySelf.getMainTable());
			System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		}
		long endTime = System.currentTimeMillis();
		System.out.println("分析表结构，所用时间：" + (endTime - startTime) + "ms");
	}

}
