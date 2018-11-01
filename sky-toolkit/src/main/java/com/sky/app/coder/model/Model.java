package com.sky.app.coder.model;

import java.util.List;

public class Model {
	/*
	 * 各组件数据
	 */
	//模型名称
	private String titleName; 
	private String titleIconType;
	
	//包名--controller
	private String controllerPath;
	//包名--service
	private String servicePath;
	//包名--serviceimpl
	private String serviceImplPath;
	//表名
	private String model;
	//数据库表的名称
	private String tableName;
	//交易号
	private String tid;
	
	private Button button;
	private Table table;
	private Page1 page;
	private Form addform;
	private Form updform;
	private Form viewform;
	private List<Input> inputs;
	private List<Modal> modals;
	private List<FormItem> addformitem;
	private List<FormItem> updformitem;
	private List<FormItem> viewformitem;
	private List<TableColumn> tablecolumns;
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getTitleIconType() {
		return titleIconType;
	}
	public void setTitleIconType(String titleIconType) {
		this.titleIconType = titleIconType;
	}
	public String getControllerPath() {
		return controllerPath;
	}
	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}
	
	public String getServicePath() {
		return servicePath;
	}
	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}
	public String getServiceImplPath() {
		return serviceImplPath;
	}
	public void setServiceImplPath(String serviceImplPath) {
		this.serviceImplPath = serviceImplPath;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public Page1 getPage() {
		return page;
	}
	public void setPage(Page1 page) {
		this.page = page;
	}
	public Form getAddform() {
		return addform;
	}
	public void setAddform(Form addform) {
		this.addform = addform;
	}
	public Form getUpdform() {
		return updform;
	}
	public void setUpdform(Form updform) {
		this.updform = updform;
	}
	public Form getViewform() {
		return viewform;
	}
	public void setViewform(Form viewform) {
		this.viewform = viewform;
	}
	public List<Input> getInputs() {
		return inputs;
	}
	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}
	public List<Modal> getModals() {
		return modals;
	}
	public void setModals(List<Modal> modals) {
		this.modals = modals;
	}
	public List<FormItem> getAddformitem() {
		return addformitem;
	}
	public void setAddformitem(List<FormItem> addformitem) {
		this.addformitem = addformitem;
	}
	public List<FormItem> getUpdformitem() {
		return updformitem;
	}
	public void setUpdformitem(List<FormItem> updformitem) {
		this.updformitem = updformitem;
	}
	public List<FormItem> getViewformitem() {
		return viewformitem;
	}
	public void setViewformitem(List<FormItem> viewformitem) {
		this.viewformitem = viewformitem;
	}
	public List<TableColumn> getTablecolumns() {
		return tablecolumns;
	}
	public void setTablecolumns(List<TableColumn> tablecolumns) {
		this.tablecolumns = tablecolumns;
	}
	
}
