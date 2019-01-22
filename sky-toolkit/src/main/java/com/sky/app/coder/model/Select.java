package com.sky.app.coder.model;

import java.util.List;

public class Select {
	//指定选中项目的 value 值，可以使用 v-model 双向绑定数据。单选时只接受 String 或 Number，多选时只接受 Array
	private String value;
	//是否支持多选
	private String multiple;
	//是否禁用
	private String disabled;
	//是否可以清空选项，只在单选时有效
	private String clearable;
	//是否支持搜索
	private String filterable;
	//选择框默认文字(默认值“请选择”)
	private String placeholder;
	//选择框大小，可选值为large、small、default或者不填
	private String size;
	//弹窗的展开方向，可选值为 top、bottom、top-start、bottom-start、top-end、bottom-end（默认值‘bottom-start’）
	private String placement;
	//当下拉列表为空时显示的内容（默认值‘无匹配数据’）
	private String notFoundText;
	//在返回选项时，是否将 label 和 value 一并返回，默认只返回 value
	private String labelInValue;
	//自己加的属性,默认给字段赋值
	private String defaultValue;
	//事件
	//选中的Option变化时触发，默认返回 value，如需返回 label，详见 label-in-value 属性
	private String onChange;
	//搜索词改变时触发
	private String onQueryChange;
	//点击清空按钮时触发
	private String onClear;
	//下拉框展开或收起时触发
	private String onOpenChange;
	
	//页面下拉框
	private String[] selectFields;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getClearable() {
		return clearable;
	}
	public void setClearable(String clearable) {
		this.clearable = clearable;
	}
	public String getFilterable() {
		return filterable;
	}
	public void setFilterable(String filterable) {
		this.filterable = filterable;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getPlacement() {
		return placement;
	}
	public void setPlacement(String placement) {
		this.placement = placement;
	}
	public String getNotFoundText() {
		return notFoundText;
	}
	public void setNotFoundText(String notFoundText) {
		this.notFoundText = notFoundText;
	}
	public String getLabelInValue() {
		return labelInValue;
	}
	public void setLabelInValue(String labelInValue) {
		this.labelInValue = labelInValue;
	}
	public String getOnChange() {
		return onChange;
	}
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	public String getOnQueryChange() {
		return onQueryChange;
	}
	public void setOnQueryChange(String onQueryChange) {
		this.onQueryChange = onQueryChange;
	}
	public String getOnClear() {
		return onClear;
	}
	public void setOnClear(String onClear) {
		this.onClear = onClear;
	}
	public String getOnOpenChange() {
		return onOpenChange;
	}
	public void setOnOpenChange(String onOpenChange) {
		this.onOpenChange = onOpenChange;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String[] getSelectFields() {
		return selectFields;
	}
	public void setSelectFields(String[] selectFields) {
		this.selectFields = selectFields;
	}
	
	
}
