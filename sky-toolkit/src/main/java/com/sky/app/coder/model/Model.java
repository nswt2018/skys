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
	private String controllerPackName;
	//包名--service
	private String servicePackName;
	//包名--serviceimpl
	private String serviceImplPackName;
	//包名--dao
	private String  daoPackName;
	//包名--model
	private String  modelPackName;
	
	//表名
	private String model;
	//模型代码
	private String moduCode;
	//数据库表的名称
	private String tableName;
	//模块数据库表主键字段
	private String tablePrimary;
	//模块数据库表主键策略 0为手动录入，1为自动录入
	private String tablePrimaryValue;
	//model实体类里面的属性 get/set 方法
	private String modelClassStr;
	//交易号
	private String tid;
	
	private Button button;
	private Table table;
	private Page1 page;
	private Form addform;
	private Form updform;
	private Form viewform;
	private Input input;
	private List<Input> inputs;
	private List<Modal> modals;
	private FormItem formitem;
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
	public String getTablePrimary() {
		return tablePrimary;
	}
	public void setTablePrimary(String tablePrimary) {
		this.tablePrimary = tablePrimary;
	}
	public String getModelClassStr() {
		return modelClassStr;
	}
	public void setModelClassStr(String modelClassStr) {
		this.modelClassStr = modelClassStr;
	}
	public String getModuCode() {
		return moduCode;
	}
	public void setModuCode(String moduCode) {
		this.moduCode = moduCode;
	}
	public String getControllerPackName() {
		return controllerPackName;
	}
	public void setControllerPackName(String controllerPackName) {
		this.controllerPackName = controllerPackName;
	}
	public String getServicePackName() {
		return servicePackName;
	}
	public void setServicePackName(String servicePackName) {
		this.servicePackName = servicePackName;
	}
	public String getServiceImplPackName() {
		return serviceImplPackName;
	}
	public void setServiceImplPackName(String serviceImplPackName) {
		this.serviceImplPackName = serviceImplPackName;
	}
	public String getDaoPackName() {
		return daoPackName;
	}
	public void setDaoPackName(String daoPackName) {
		this.daoPackName = daoPackName;
	}
	public String getModelPackName() {
		return modelPackName;
	}
	public void setModelPackName(String modelPackName) {
		this.modelPackName = modelPackName;
	}
	public String getTablePrimaryValue() {
		return tablePrimaryValue;
	}
	public void setTablePrimaryValue(String tablePrimaryValue) {
		this.tablePrimaryValue = tablePrimaryValue;
	}
	public Input getInput() {
		return input;
	}
	public void setInput(Input input) {
		this.input = input;
	}
	public FormItem getFormitem() {
		return formitem;
	}
	public void setFormitem(FormItem formitem) {
		this.formitem = formitem;
	}
	
}
