package com.sky.app.coder.model;

public class Button {
	// 按钮类型，可选值为 default、primary、dashed、text、info、success、warning、error或者不设置
	private String type;
	// 按钮大小，可选值为large、small、default或者不设置
	private String size;
	// 按钮内图标显示
	private String icon;
	// 幽灵属性，使按钮背景透明
	private String ghost;
	// 按钮形状，可选值为circle或者不设置
	private String shape;
	// 设置按钮为加载中状态״̬
	private String loading;
	// 跳转的链接，支持 vue-router 对象
	private String to;
	// 路由跳转时，开启 replace 将不会向 history 添加新记录
	private String replace;
	// 相当于 a 链接的 target 属性
	private String target;
	//�����󣬰�ť�ĳ���Ϊ 100%	long
	private String longs;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getGhost() {
		return ghost;
	}

	public void setGhost(String ghost) {
		this.ghost = ghost;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getLoading() {
		return loading;
	}

	public void setLoading(String loading) {
		this.loading = loading;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLongs() {
		return longs;
	}

	public void setLongs(String longs) {
		this.longs = longs;
	}
	
}
