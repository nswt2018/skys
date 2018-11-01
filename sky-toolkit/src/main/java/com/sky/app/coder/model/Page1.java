package com.sky.app.coder.model;

public class Page1 {
	//当前页码，支持 .sync 修饰符，默认值为0
	private String current;
	//数据总数 ，默认值为0
	private String total;
	//每页条数
	private String pageSize;
	/*是否将弹层放置于 body 内，在 Tabs、带有 fixed 的 Table 列内使用时，
	建议添加此属性，它将不受父级样式影响，从而达到更好的效果*/
	private String transfer;
	//可选值为small（迷你版）或不填（默认）
	private String size;
	//显示总数
	private String showTotal;
	//显示电梯，可以快速切换到某一页
	private String showElevator;
	//显示分页，用来改变page-size
	private String showSizer;
	//页码改变的回调，返回改变后的页码
	private String onChange;
	//切换每页条数时的回调，返回切换后的每页条数
	private String onPageSizeChange;
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTransfer() {
		return transfer;
	}
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getShowTotal() {
		return showTotal;
	}
	public void setShowTotal(String showTotal) {
		this.showTotal = showTotal;
	}
	public String getShowElevator() {
		return showElevator;
	}
	public void setShowElevator(String showElevator) {
		this.showElevator = showElevator;
	}
	public String getShowSizer() {
		return showSizer;
	}
	public void setShowSizer(String showSizer) {
		this.showSizer = showSizer;
	}
	public String getOnChange() {
		return onChange;
	}
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	public String getOnPageSizeChange() {
		return onPageSizeChange;
	}
	public void setOnPageSizeChange(String onPageSizeChange) {
		this.onPageSizeChange = onPageSizeChange;
	}
	
	
}
