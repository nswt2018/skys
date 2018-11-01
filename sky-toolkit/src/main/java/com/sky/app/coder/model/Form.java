package com.sky.app.coder.model;

public class Form {
	//表单数据对象
	private String model;
	//表单验证规则
	private String rules;
	//表单域标签的宽度
	private String labelWidth;
	//表单域标签的位置，可选值为  left、right、top(默认值为right)
	private String labelPosition;
	//是否开启行内表单模式
	private String inline;
	//是否显示校验错误信息(默认值为true)
	private String showMessage;
	//获得dom节点
	private String ref;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public String getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}
	public String getLabelPosition() {
		return labelPosition;
	}
	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}
	public String getInline() {
		return inline;
	}
	public void setInline(String inline) {
		this.inline = inline;
	}
	public String getShowMessage() {
		return showMessage;
	}
	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	

}
