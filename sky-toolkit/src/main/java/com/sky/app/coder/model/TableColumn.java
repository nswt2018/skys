package com.sky.app.coder.model;

public class TableColumn {
	// 显示列表的列名称
	private String label;
	// 显示列表的列字段
	private String value;
	//字段是否日期类型
	private String isDate;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIsDate() {
		return isDate;
	}
	public void setIsDate(String isDate) {
		this.isDate = isDate;
	}
	
}
