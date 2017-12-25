package com.sooncode.daocodebuilder.service.config;

/**
 * mapping 接口 方法名称前缀 配置
 * 
 * @author hechen
 * 
 */

public class InterfaceConfig {
	
	private String insert = "insert";
	private String delete = "delete";
	private String update = "update";
	private String select = "select";
	private String paging = "paging";
	private String page = "Page";
	private String by = "By";
	private String size = "Size";
	private String connect = "connect";

	public String getInsert() {
		return insert;
	}

	public String getDelete() {
		return delete;
	}

	public String getUpdate() {
		return update;
	}

	public String getSelect() {
		return select;
	}

	public String getPaging() {
		return paging;
	}

	public String getBy() {
		return by;
	}

	public String getPage() {
		return page;
	}

	public String getSize() {
		return size;
	}

	public String getConnect() {
		return connect;
	}

}
