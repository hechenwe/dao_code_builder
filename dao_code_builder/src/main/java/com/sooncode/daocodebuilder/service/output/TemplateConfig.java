package com.sooncode.daocodebuilder.service.output;

import java.util.Map;

/**
 * 模板构建
 * 
 * @author hechen
 *
 */
public class TemplateConfig {

	/** 模板名称 */
	private String templateName;

	/** 模板位置 */
	private String templatePath;

	/** 模板生成的文件输出位置 */
	private String templateOutputPath;

	/** 输出文件的名称 */
	private String fileName;

	/** 模板渲染所需的数据 */
	private Map<String, Object> Data;

	// ------------------------------------------------------------------------------------------------------------------------

	/**
	 * 模板名称
	 * 
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * 模板名称
	 * 
	 * @param templateName
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTemplateOutputPath() {
		return templateOutputPath;
	}

	public void setTemplateOutputPath(String templateOutputPath) {
		this.templateOutputPath = templateOutputPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, Object> getData() {
		return Data;
	}

	public void setData(Map<String, Object> data) {
		Data = data;
	}

}
