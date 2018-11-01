package com.sky.app.coder.model;

public class FormItem {
	//标签文本
	private String label;
	//对应表单域 model 里的字段
	private String prop;
	//表单字段  自己添加的不是FormItem组件的属性
	private String propValue;
	//表单域标签的的宽度
	private String labelWidth;
	//是否必填，如不设置，则会根据校验规则自动生成
	private String required;
	//表单验证规则
	private String rules;
	//表单域验证错误信息, 设置该值会使表单验证状态变为error，并显示该错误信息
	private String error;
	//指定原生的 label 标签的 for 属性，配合控件的 element-id 属性，可以点击 label 时聚焦控件。
	private String labelFor;
	//是否显示校验错误信息(默认值为true)
	private String showMessage;
	//表单字段类型 下拉选择框：select,输入框：input,单选框：radioGroup,多选框：checkboxGroup�?
	//日期年月日：DatePicker，日期时分秒：TimePicker  自己添加的不是FormItem组件的属性
	private String type;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	public String getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getLabelFor() {
		return labelFor;
	}
	public void setLabelFor(String labelFor) {
		this.labelFor = labelFor;
	}
	public String getShowMessage() {
		return showMessage;
	}
	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
