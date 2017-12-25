package com.sooncode.daocodebuilder.service.build;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.table.TableBuilder;
import com.sooncode.daocodebuilder.service.build.ModleType;
import com.sooncode.daocodebuilder.service.build.Package;
import com.sooncode.daocodebuilder.service.build.Project;
import com.sooncode.daocodebuilder.service.config.InterfaceConfig;
import com.sooncode.daocodebuilder.service.freemarker.FreemarkerUtil;
import com.sooncode.daocodebuilder.service.modle.AnalyzeModle;
import com.sooncode.daocodebuilder.service.modle.many2many.Many2many;
import com.sooncode.daocodebuilder.service.modle.many2many.Many2manySelf;
import com.sooncode.daocodebuilder.service.modle.one2many.One2Many;
import com.sooncode.daocodebuilder.service.modle.one2many.One2ManySelf;
import com.sooncode.daocodebuilder.service.modle.one2one.One2One;
import com.sooncode.daocodebuilder.service.modle.one2one.One2OneMiddle;
import com.sooncode.daocodebuilder.service.modle.one2one.One2OneSelf;
import com.sooncode.daocodebuilder.service.output.OutPutFileConstant;
import com.sooncode.daocodebuilder.service.output.TemplateConfig;
import com.sooncode.daocodebuilder.service.output.TemplateConstant;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * dao层 代码生成器
 * 
 * @author 臣
 *
 */
@Service
public class DaoCodeGenerator {

	 
	private static Logger logger = Logger.getLogger("DaoCodeGenerator.class");

	 

	/**
	 * 获取主表的所有关系
	 * 
	 * @param mainTable
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getMainTableAllRelation(Table mainTable) throws Exception {

		Map<String, Object> map = new HashMap<>();

		One2One one2One = AnalyzeModle.anlyzeOne2One(mainTable);
		One2OneSelf one2OneSelf = AnalyzeModle.anlyzeOne2OneSelf(mainTable);
		One2OneMiddle one2OneMiddle = AnalyzeModle.anlyzeOne2OneMiddle(mainTable);

		One2Many one2Many = AnalyzeModle.anlyzeOne2Many(mainTable);
		One2ManySelf one2ManySelf = AnalyzeModle.anlyzeOne2ManySelf(mainTable);

		Many2many many2many = AnalyzeModle.anlyzeMany2many(mainTable);
		Many2manySelf many2manySelf = AnalyzeModle.anlyzeMany2manySelf(mainTable);

		map.put(ModleType.MAIN_TABLE, mainTable);
		map.put(ModleType.ONE_TO_ONE, one2One);
		map.put(ModleType.ONE_TO_ONE_SELF, one2OneSelf);
		map.put(ModleType.ONE_TO_ONE_MIDDLE, one2OneMiddle);

		map.put(ModleType.ONE_TO_MANY, one2Many);
		map.put(ModleType.ONE_TO_MANY_SELF, one2ManySelf);

		map.put(ModleType.MANY_TO_MANY, many2many);
		map.put(ModleType.MANY_TO_MANY_SELF, many2manySelf);

		return map;
	}

 

	/**
	 * 输出
	 * 
	 * @param databaseName
	 * @param tableName
	 * @throws Exception
	 */
	public boolean outputByTabaleAndEntityMap(Table mainTable, String type) throws Exception {

		Map<String, Object> dataMap = getMainTableAllRelation(mainTable);

		Project project = new Project();
		// mapping 接口包
		dataMap.put(Package.DAO_MAPPING_PACKAGE, project.getDaoMappingPackageName(mainTable));
		//logger.info(project.getDaoMappingPackageName(mainTable));
		// *DaoInterface借口包名
		 
		dataMap.put(Package.DAO_INTERFACE_PACKAGE, project.getDaoMappingPackageName(mainTable));

		// *Dao 包名
		dataMap.put(Package.DAO_PACKAGE, project.getDaoPackageName(mainTable));
		dataMap.put(OutPutFileConstant.DAO_INTERFACE, OutPutFileConstant.DAO_INTERFACE);
		// *Service 包名

		dataMap.put(Package.SERVICE_PACKAGE, project.getServicePackageName(mainTable));
		// Pager 包
		dataMap.put(Package.PAGER_PACKAGE, project.getPagerPackage());

		// 模板位置
		String templatePath = TemplateConstant.TEMPLATE_PATH;
		// 接口方法名称前缀
		dataMap.put("INTERFACE_CONFIG", new InterfaceConfig());
		//
		dataMap.put("Pager", project.getCommonUtilPackageName("Pager"));

		if (type.equals("Entity")) {
			TemplateConfig entityTemplateConfig = new TemplateConfig();
			entityTemplateConfig.setTemplateName("entity/entity_main.ftl");
			//entityTemplateConfig.setTemplatePath(templatePath);
			entityTemplateConfig.setTemplateOutputPath(project.getEntityOutPutPath(mainTable));
			entityTemplateConfig.setData(dataMap);
			entityTemplateConfig.setFileName(mainTable.getEntityName() + OutPutFileConstant.ENTITY_JAVA);
			FreemarkerUtil.fileOutput(entityTemplateConfig);
		}

		if (type.toUpperCase().equals("SQL")) {
			TemplateConfig mappingSqlTemplateConfig = new TemplateConfig();

			mappingSqlTemplateConfig.setTemplateName("sql/mappingSql.ftl");
			mappingSqlTemplateConfig.setTemplateOutputPath(project.getDaoMappingOutPutPath(mainTable));
			mappingSqlTemplateConfig.setFileName(mainTable.getEntityName() + OutPutFileConstant.DAO_INTERFACE_XML);
			mappingSqlTemplateConfig.setData(dataMap);
			FreemarkerUtil.fileOutput(mappingSqlTemplateConfig);
		}

		if (type.equals("Interface")) {
			TemplateConfig mappingInterfaceTemplateConfig = new TemplateConfig();

			mappingInterfaceTemplateConfig.setTemplateName("interfac/mappingInterface.ftl");
			mappingInterfaceTemplateConfig.setTemplateOutputPath(project.getDaoMappingOutPutPath(mainTable));
			mappingInterfaceTemplateConfig.setFileName(mainTable.getEntityName() + OutPutFileConstant.DAO_INTERFACE_JAVA);
			mappingInterfaceTemplateConfig.setData(dataMap);
			FreemarkerUtil.fileOutput(mappingInterfaceTemplateConfig);
		}

		if (type.equals("TestInterface")) {
			TemplateConfig testInerfaceTemplateConfig = new TemplateConfig();
			testInerfaceTemplateConfig.setFileName(mainTable.getEntityName() + OutPutFileConstant.DAO_INTERFACE_TEST_JAVA);
			//testInerfaceTemplateConfig.setTemplatePath(templatePath);
			testInerfaceTemplateConfig.setTemplateName(TemplateConstant.TEST_INTERFACE_FTL);
			testInerfaceTemplateConfig.setTemplateOutputPath(project.getTestDaoInterfaceOutPutPath(mainTable));
			testInerfaceTemplateConfig.setData(dataMap);
			FreemarkerUtil.fileOutput(testInerfaceTemplateConfig);
		}
		if (type.equals("Dao")) {
			TemplateConfig daoTemplateConfig = new TemplateConfig();
			daoTemplateConfig.setTemplateName("dao/dao.ftl");
			daoTemplateConfig.setTemplateOutputPath(project.getDaoInterfacesOutPutPath(mainTable));
			daoTemplateConfig.setFileName(mainTable.getEntityName() + OutPutFileConstant.ENTITY_DAO_JAVA);
			daoTemplateConfig.setData(dataMap);
			FreemarkerUtil.fileOutput(daoTemplateConfig);
			//logger.info(project.getDaoInterfacesOutPutPath(mainTable));
		}
		// -------------------------------------------TestDao
		// -------------------------------------------------------

		if (type.equals("TestDao")) {
			TemplateConfig testDaoTemplateConfig = new TemplateConfig();
			testDaoTemplateConfig.setFileName(mainTable.getEntityName() + OutPutFileConstant.TEST_DAO_JAVA);
			//testDaoTemplateConfig.setTemplatePath(templatePath);
			testDaoTemplateConfig.setTemplateName(TemplateConstant.TEST_DAO_FTL);
			testDaoTemplateConfig.setTemplateOutputPath(project.getTestDaoOutPutPath(mainTable));
			testDaoTemplateConfig.setData(dataMap);
			FreemarkerUtil.fileOutput(testDaoTemplateConfig);
		}
		// --------------------------------------------service--------------------------------------------------------
		if (type.equals("Service")) {
			TemplateConfig serviceTemplateConfig = new TemplateConfig();
			serviceTemplateConfig.setTemplateName(TemplateConstant.SERVICE_FTL);
			//serviceTemplateConfig.setTemplatePath(templatePath);
			serviceTemplateConfig.setTemplateOutputPath(project.getServiceOutPutPath(mainTable));
			serviceTemplateConfig.setFileName(mainTable.getEntityName() + OutPutFileConstant.SERVICE_JAVA);
			serviceTemplateConfig.setData(dataMap);
			FreemarkerUtil.fileOutput(serviceTemplateConfig);
		}
		
		//----------------------------Pager---------------------------------------  
		// 注意:待优化 ,Pager.java 文件将多次创建  
		TemplateConfig serviceTemplateConfig = new TemplateConfig();
		serviceTemplateConfig.setTemplateName("util/page.ftl");
		 
		serviceTemplateConfig.setTemplateOutputPath(new Project().getPpsppc()+"/util");
		serviceTemplateConfig.setFileName("Pager.java");
		serviceTemplateConfig.setData(dataMap);
		FreemarkerUtil.fileOutput(serviceTemplateConfig);
		return true;
	}

	 
	public static void main(String[] args) throws Exception {
		// long startTime = System.currentTimeMillis();// 获取当前时间
		// ------------------------------------------------------------输出所有表-----------------------------------------------------------------------------
		// for (Map.Entry<String, Table> entry :
		// TableBuilder.tablesCache.entrySet()) {
		// long startTimeOutPut = System.currentTimeMillis();// 获取当前时间
		// outputByTabaleAndEntityMap(entry.getValue());
		// long endTimeOutPut = System.currentTimeMillis();
		// logger.info("----------输出[" + entry.getValue().getTableRemarks() +
		// "]的相关文件，所用时间：" + (endTimeOutPut - startTimeOutPut) + "ms");
		// }
		// long endTime = System.currentTimeMillis();
		// logger.info("----------输出所有文件所用的时间：" + (endTime - startTime) + "ms");
		// ------------------------------------------------------------输出一个表--------------------------------------------------------------------------------------
		// Table mainTable =
		// TableBuilder.tablesCache.get("WEALTH_WEALTH_TRADE_TYPE".toUpperCase());
		// DaoCodeGenerator dao = new DaoCodeGenerator();
		// .outputByTabaleAndEntityMap(mainTable, "Entity");
		//
	}

}
