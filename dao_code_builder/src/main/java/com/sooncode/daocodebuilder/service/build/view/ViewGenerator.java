package com.sooncode.daocodebuilder.service.build.view;

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
import com.sooncode.daocodebuilder.entity.view.View;
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
import com.sooncode.daocodebuilder.util.PathUtil;

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
public class ViewGenerator {
 
	private static Logger logger = Logger.getLogger("ViewGenerator.class");
	/**
	 * 输出视图
	 * 
	 * @param databaseName
	 * @param tableName
	 * @throws Exception
	 */
	public boolean outputByView(View view, String type) throws Exception {
        view.setViewCode(view.getViewCode().toLowerCase());
		Map<String, Object> dataMap  = new HashMap<>();
		dataMap.put("view",view);
		Project project = new Project();
        String outPutPath = project.getViewEntityOutPutPath(view);
        String packageName = project.getViewEntityPackageName(view);
        dataMap.put("packageName",packageName);
		if (type.equals("Entity")) {
			TemplateConfig entityTemplateConfig = new TemplateConfig();
			entityTemplateConfig.setTemplateName(TemplateConstant.VIEW_ENTITY_FTL);
			entityTemplateConfig.setTemplatePath(TemplateConstant.TEMPLATE_PATH);
			entityTemplateConfig.setTemplateOutputPath(outPutPath);
			entityTemplateConfig.setData(dataMap);
			entityTemplateConfig.setFileName(view.getViewCode().toLowerCase()+OutPutFileConstant.ENTITY_JAVA);
			FreemarkerUtil.fileOutput(entityTemplateConfig);
		}
		
		if (type.equals("SQL")) {
			String sqlOutPutPath = project.getViewSqlOutPutPath(view);//输出路径
			String sqlFileName=view.getViewCode().toLowerCase()+"Interface.java"; //文件名称
			TemplateConfig entityTemplateConfig = new TemplateConfig();
			entityTemplateConfig.setTemplateName(TemplateConstant.VIEW_MAPPING_SQL_FTL);
			entityTemplateConfig.setTemplatePath(TemplateConstant.TEMPLATE_PATH);
			entityTemplateConfig.setTemplateOutputPath(sqlOutPutPath);
			entityTemplateConfig.setData(dataMap);
			entityTemplateConfig.setFileName(sqlFileName); 
			FreemarkerUtil.fileOutput(entityTemplateConfig);
		}
 
		return true;
	}
	 

}
