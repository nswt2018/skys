package com.sky.app.coder.model;


public class Table {
	//表格列的配置描述，具体项见后文
	private String columns;
	//显示的结构化数据，其中，字段 cellClassName 用于设置任意单元格的样式名称，因此数据不能使用该字段，详见示例特定样式。
	private String data;
	//表格高度，单位 px，设置后，如果表格内容大于此值，会固定表头
	private String height;
	//是否支持高亮选中的行，即单选
	private String highlightRow;
	//是否显示纵向边框
	private String border;
	//表格是否加载中
	private String loading;
	//是否显示间隔斑马纹  表格会间隔显示不同颜色，用于区分不同行数据。
	private String stripe;
	//表格显示大小
	private String size;
	//获取DOM节点
	private String ref;
	//是否需要全选
	private String isSelectAll;
	/*
	 * 表格中的事件
	 */
	//在多选模式下有效，选中某一项时触发
	private String onSelect;
	//在多选模式下有效，取消选中某一项时触发
	private String onSelectCancel;
	//在多选模式下有效，点击全选时触发
	private String onSelectAll;
	//在多选模式下有效，只要选中项发生变化时就会触发
	private String onSelectionChange;
	
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHighlightRow() {
		return highlightRow;
	}
	public void setHighlightRow(String highlightRow) {
		this.highlightRow = highlightRow;
	}
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getLoading() {
		return loading;
	}
	public void setLoading(String loading) {
		this.loading = loading;
	}
	public String getStripe() {
		return stripe;
	}
	public void setStripe(String stripe) {
		this.stripe = stripe;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getIsSelectAll() {
		return isSelectAll;
	}
	public void setIsSelectAll(String isSelectAll) {
		this.isSelectAll = isSelectAll;
	}
	public String getOnSelect() {
		return onSelect;
	}
	public void setOnSelect(String onSelect) {
		this.onSelect = onSelect;
	}
	public String getOnSelectCancel() {
		return onSelectCancel;
	}
	public void setOnSelectCancel(String onSelectCancel) {
		this.onSelectCancel = onSelectCancel;
	}
	public String getOnSelectAll() {
		return onSelectAll;
	}
	public void setOnSelectAll(String onSelectAll) {
		this.onSelectAll = onSelectAll;
	}
	public String getOnSelectionChange() {
		return onSelectionChange;
	}
	public void setOnSelectionChange(String onSelectionChange) {
		this.onSelectionChange = onSelectionChange;
	}
	
	
	
}
