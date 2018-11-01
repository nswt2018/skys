package com.sky.app.coder.model;

//页面搜索输入框
public class Input {
	/*
	 * 属性
	 */
	//输入框的数据
	private String value;
	//输入框提示语
	private String placeholder;
	//输入框宽度
	private String width;
	//输入框类型
	private String type;
	//是否显示清空按钮
	private String clearable;
	//输入框尾部图标，仅在 text 类型下有效
	private String icon;
	//设置输入框为只读
	private String readonly;
	//是否显示为搜索型输入框
	private String search;
	//开启 search 时可用，是否有确认按钮，可设为按钮文字
	private String enterButton;
	//输入框尺寸，可选值为large、small、default或者不设置
	private String size;
	//自适应内容高度，仅在 textarea 类型下有效，可传入对象，如 { minRows: 2, maxRows: 6 }
	private String autosize;
	//最大输入长度
	private String maxlength;
	/*
	 * 事件
	 */
	//数据改变时触发
	private String onChange;
	//设置 icon 属性后，点击图标时触发
	private String onClick;
	//按下回车键时触发
	private String onEnter;
	//开启 search 时可用，点击搜索或按下回车键时触发
	private String onSearch;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClearable() {
		return clearable;
	}
	public void setClearable(String clearable) {
		this.clearable = clearable;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getEnterButton() {
		return enterButton;
	}
	public void setEnterButton(String enterButton) {
		this.enterButton = enterButton;
	}
	
	public String getAutosize() {
		return autosize;
	}
	public void setAutosize(String autosize) {
		this.autosize = autosize;
	}
	public String getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	public String getOnChange() {
		return onChange;
	}
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	public String getOnClick() {
		return onClick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	public String getOnEnter() {
		return onEnter;
	}
	public void setOnEnter(String onEnter) {
		this.onEnter = onEnter;
	}
	public String getOnSearch() {
		return onSearch;
	}
	public void setOnSearch(String onSearch) {
		this.onSearch = onSearch;
	}
	@Override
	public String toString() {
		return "Input [value=" + value + ", placeholder=" + placeholder + ", width=" + width + ", type=" + type
				+ ", clearable=" + clearable + ", icon=" + icon + ", readonly=" + readonly + ", search=" + search
				+ ", enterButton=" + enterButton + ", size=" + size + ", autosize=" + autosize + ", maxlength="
				+ maxlength + ", onChange=" + onChange + ", onClick=" + onClick + ", onEnter=" + onEnter + ", onSearch="
				+ onSearch + "]";
	}
	

}
