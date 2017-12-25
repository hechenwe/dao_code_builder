package com.sooncode.daocodebuilder.entity.view;

import java.util.List;

import com.sooncode.daocodebuilder.entity.column.Column;

/**
 * 视图 
 * @author pc
 *
 */
public class View {
    /**
     * 视图名
     */
	private String viewCode ;
	
	/**
	 * 视图的字段
	 */
	private List<Column> columns;

	
	
	
	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	
	
}
