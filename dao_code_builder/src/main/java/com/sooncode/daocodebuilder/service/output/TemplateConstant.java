package com.sooncode.daocodebuilder.service.output;

import java.io.File;

/**
 * 描述模板的常量
 * 
 * @author hechen
 *
 */

public class TemplateConstant {
	//public static final String WORKSPACE = "E:/workspace/daocodebuilder/";
	public static final String WORKSPACE = Thread.currentThread().getContextClassLoader().getResource("/").getPath(); 
	public static final String TEMPLATE_PATH =WORKSPACE+ File.separatorChar + "com" + File.separatorChar + "sooncode" + File.separatorChar + "daocodebuilder" + File.separatorChar + "template";
	public static final String MAPPING_INTERFACE_FTL = "mappingInterface.ftl";
	public static final String DAO_FTL = "dao.ftl";
	public static final String ENTITY_FTL = "entity.ftl";
	public static final String MAPPING_SQL_FTL = "mappingSql.ftl";
	public static final String TEST_INTERFACE_FTL = "testInterface.ftl";
	public static final String TEST_DAO_FTL = "testDao.ftl";
	public static final String SERVICE_FTL = "service.ftl";
	
	//-------------------------------------------------------
	public static final String VIEW_ENTITY_FTL = "view_entity.ftl";
	public static final String VIEW_MAPPING_SQL_FTL = "view_mapping_sql.ftl";
}
