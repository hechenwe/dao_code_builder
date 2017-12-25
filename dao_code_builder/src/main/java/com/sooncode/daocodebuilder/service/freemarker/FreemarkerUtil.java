package com.sooncode.daocodebuilder.service.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.sooncode.daocodebuilder.service.output.TemplateConfig;
import com.sooncode.daocodebuilder.service.output.TemplateConstant;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerUtil {
	
	public static Logger logger = Logger.getLogger("FreemarkerUtil.class");

	 
	 

	/**
	 * 
	 * @param configuration
	 * @param templatePath
	 * @return
	 */
	private  Configuration settingConfigurationDirectory(Configuration configuration, String templatePath){
		configuration = new Configuration();
		 
		File tempfile = new File(templatePath);
		try {
			configuration.setDirectoryForTemplateLoading(tempfile);
			return configuration;
		} catch (IOException e) {
			return null;
		}
	}
	/**
	 * 根据模板生成文件并输出
	 * 
	 * @param templateConfig
	 * @throws Exception
	 */
	public static void fileOutput(TemplateConfig templateConfig) throws Exception {
		Configuration configuration = new Configuration();

		File tempfile = new File(TemplateConstant.TEMPLATE_PATH);
		 
		configuration.setDirectoryForTemplateLoading(tempfile);
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setDefaultEncoding("UTF-8");
		Template template = configuration.getTemplate(templateConfig.getTemplateName());// 模板名称
		File folder = new File(templateConfig.getTemplateOutputPath()); // 创建文件夹
		// logger.info(templateConfig.getTemplateOutputPath());
		boolean isExist = folder.exists();
		if (isExist == false) { // 该文件夹不存在
			folder.mkdirs(); // 创建文件夹
			isExist = true;
		}
		if (isExist) {
			File file = new File(templateConfig.getTemplateOutputPath() + File.separator + templateConfig.getFileName());// 创建文件
			
			Writer riter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			template.process(templateConfig.getData(), riter);
			riter.flush();
			riter.close();
		}
	}
}
