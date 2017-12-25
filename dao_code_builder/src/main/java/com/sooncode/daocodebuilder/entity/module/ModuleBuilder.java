package com.sooncode.daocodebuilder.entity.module;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;
import com.sooncode.daocodebuilder.entity.util.config.Constant;
import com.sooncode.daocodebuilder.entity.util.config.DBConstant;
import com.sooncode.daocodebuilder.entity.util.config.GlobalConstant;

/**
 * 
 * @author hechen
 *
 */
public class ModuleBuilder {

	/**
	 * 数据库连接对象
	 */
	protected static Connection connection;
	private List<Module> modulesCache;

	/**
	 * 
	 * @return
	 */
	public static List<Module> moduleBuilder(List<Table> tables) {
		 
			List<Module> modules = new ArrayList<>();

			String moduleName = "";
			 for (Table table : tables) {

				String tableName = table.getTableName();
				String tableRemarks = table.getTableRemarks();// 表注释
				if (!tableRemarks.contains(Constant.MAP_STRATEGY)) { // 过滤掉 非映射表
					String[] tableNames = tableName.split(GlobalConstant.STRING_SPLIT_SIGN);
					String[] tableRemarkses = tableRemarks.split(GlobalConstant.STRING_SPLIT_SIGN);
					if (!tableNames[0].equals(moduleName)) {
                
						Module module = new Module();
						module.setModuleName(tableNames[0]);
						module.setModuleRemarks(tableRemarkses[0]);
						modules.add(module);
						moduleName = tableNames[0];
					}
				}
			}

			return modules;

		 

	}
	
	 
}
