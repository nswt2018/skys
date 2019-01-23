package com.sky.app.coder.model;

import java.util.List;

public class Checkbox {
	// 只在单独使用时有效。可以使用 v-model 双向绑定数据
	private String value;
	// 只在组合使用时有效。指定当前选项的 value 值，组合会自动判断是否选中
	private String label;
	// 是否禁用当前项
	private String disabled;
	// 多选框的尺寸，可选值为 large、small、default 或者不设置
	private String size;
	//自己加的属性,默认给字段赋值
	private String defaultValue;
	
	//多选框
	private String[] checkboxFields;
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String[] getCheckboxFields() {
		return checkboxFields;
	}

	public void setCheckboxFields(String[] checkboxFields) {
		this.checkboxFields = checkboxFields;
	}

	
	
}
