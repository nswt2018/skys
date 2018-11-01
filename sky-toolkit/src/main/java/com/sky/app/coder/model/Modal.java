package com.sky.app.coder.model;

public class Modal {
	//对话框是否显示，可使用 v-model 双向绑定数据
	private String value;
	//对话框标题，如果使用 slot 自定义了页头，则 title 无效
	private String title;
	//对话框宽度，对话框的宽度是响应式的，当屏幕尺寸小于 768px 时，宽度会变为自动auto。当其值不大于 100 时以百分比显示，大于 100 时为像素
	private String width;
	//是否显示右上角的关闭按钮，关闭后 Esc 按键也将关闭
	private String closable;
	//确定按钮文字
	private String okText;
	//取消按钮文字
	private String cancelText;
	//点击确定按钮时，确定按钮是否显示 loading 状态，开启则需手动设置value来关闭对话框
	private String loading;
	//页面是否可以滚动
	private String scrollable;
	//是否全屏显示
	private String fullscreen;
	//是否可以拖拽移动
	private String draggable;
	//是否允许点击遮罩层关闭
	private String maskClosable;
	//设置表单，新增：addForm、修改：updForm、查看：viewForm
	private String form;
	//事件
	//点击确定的回调
	private String onOk;
	//点击取消的回调
	private String onCancel;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getClosable() {
		return closable;
	}
	public void setClosable(String closable) {
		this.closable = closable;
	}
	public String getOkText() {
		return okText;
	}
	public void setOkText(String okText) {
		this.okText = okText;
	}
	public String getCancelText() {
		return cancelText;
	}
	public void setCancelText(String cancelText) {
		this.cancelText = cancelText;
	}
	public String getLoading() {
		return loading;
	}
	public void setLoading(String loading) {
		this.loading = loading;
	}
	public String getScrollable() {
		return scrollable;
	}
	public void setScrollable(String scrollable) {
		this.scrollable = scrollable;
	}
	public String getFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(String fullscreen) {
		this.fullscreen = fullscreen;
	}
	public String getDraggable() {
		return draggable;
	}
	public void setDraggable(String draggable) {
		this.draggable = draggable;
	}
	public String getMaskClosable() {
		return maskClosable;
	}
	public void setMaskClosable(String maskClosable) {
		this.maskClosable = maskClosable;
	}
	
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getOnOk() {
		return onOk;
	}
	public void setOnOk(String onOk) {
		this.onOk = onOk;
	}
	public String getOnCancel() {
		return onCancel;
	}
	public void setOnCancel(String onCancel) {
		this.onCancel = onCancel;
	}
	
}
