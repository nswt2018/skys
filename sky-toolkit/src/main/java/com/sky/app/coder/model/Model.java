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
	//系统简码
	private String sysCode;
	//模块关联表
	private String tableName;
	
	//多表模型，模块多个关联表,以逗号隔开
	private String tableNames;
	//多表模型，模块多个关联表
	private List<String> tableListName;
	//多表模型，关联表之间的关联关系
	private String tableInfo;
	//多表模型，关联表主键
	private List<String> tablePrimaryList;
	//多表模型,转换后 的模块关联表  
	private String converTableName;
	//多表模型 ，mapper中查询的字段
	private String mapperSelectField;
	//多表模型，为了处理关联字段的赋值问题
	private String changeTableInfo;
	
	//模块数据库表主键字段
	private String tablePrimary;
	//模块数据库表主键策略 0为手动录入，1为自动录入
	private String tablePrimaryValue;
	//model实体类里面的属性 get/set 方法
	private String modelClassStr;
	
	//从表模块，模块关联表
	private String mstableName;
	//从表模型，主键字段
	private String mstablePrimary;
	//从表模型model实体类里面的属性 get/set 方法
	private String msmodelClassStr;
	//多表模型 ，从表mapper中查询的字段
	private String msmapperSelectField;
	//从表模型，主表和从表关联的字段
	private String relationField;
	//从表模型，从表关联的字段
	private String msrelationField;
	//从表模型，从表转换后关联的字段
	private String msconvertRelationField;
	
	//交易号
	private String tid;
	//用于标签信息解析
	private Input input;
	private FormItem formitem;
	private DatePicker datepicker;
	private InputNumber inputnumber;

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
	//主从模型，从表输入框
	private List<Input> msinputs;
	//主从模型，从表列表
	private Table mstable;
	//从表模型，从表分页
	private Page1 mspage;
	//从表模型，从表对话框
	private List<Modal> msmodals;
	//从表模型，从表新增表单
	private Form msaddform;
	//从表模型，从表修改表单
	private Form msupdform;
	//从表模型，从表查看表单
	private Form msviewform;
	//从表模型，从表新增字段
	private List<FormItem> msaddformitems;
	//从表模型，从表修改字段
	private List<FormItem> msupdformitems;
	//从表模型，从表查看字段
	private List<FormItem> msviewformitems;
	//从表模型，
	private List<TableColumn> mstablecolumns;
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
	public DatePicker getDatepicker() {
		return datepicker;
	}
	public void setDatepicker(DatePicker datepicker) {
		this.datepicker = datepicker;
	}
	public InputNumber getInputnumber() {
		return inputnumber;
	}
	public void setInputnumber(InputNumber inputnumber) {
		this.inputnumber = inputnumber;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getConverTableName() {
		return converTableName;
	}
	public void setConverTableName(String converTableName) {
		this.converTableName = converTableName;
	}
	public String getTableNames() {
		return tableNames;
	}
	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}
	public String getTableInfo() {
		return tableInfo;
	}
	public void setTableInfo(String tableInfo) {
		this.tableInfo = tableInfo;
	}
	public List<String> getTableListName() {
		return tableListName;
	}
	public void setTableListName(List<String> tableListName) {
		this.tableListName = tableListName;
	}
	public String getMapperSelectField() {
		return mapperSelectField;
	}
	public void setMapperSelectField(String mapperSelectField) {
		this.mapperSelectField = mapperSelectField;
	}
	public String getChangeTableInfo() {
		return changeTableInfo;
	}
	public void setChangeTableInfo(String changeTableInfo) {
		this.changeTableInfo = changeTableInfo;
	}
	public List<String> getTablePrimaryList() {
		return tablePrimaryList;
	}
	public void setTablePrimaryList(List<String> tablePrimaryList) {
		this.tablePrimaryList = tablePrimaryList;
	}
	public List<Input> getMsinputs() {
		return msinputs;
	}
	public void setMsinputs(List<Input> msinputs) {
		this.msinputs = msinputs;
	}
	public Table getMstable() {
		return mstable;
	}
	public void setMstable(Table mstable) {
		this.mstable = mstable;
	}
	public Page1 getMspage() {
		return mspage;
	}
	public void setMspage(Page1 mspage) {
		this.mspage = mspage;
	}
	public List<Modal> getMsmodals() {
		return msmodals;
	}
	public void setMsmodals(List<Modal> msmodals) {
		this.msmodals = msmodals;
	}
	public Form getMsaddform() {
		return msaddform;
	}
	public void setMsaddform(Form msaddform) {
		this.msaddform = msaddform;
	}
	public Form getMsupdform() {
		return msupdform;
	}
	public void setMsupdform(Form msupdform) {
		this.msupdform = msupdform;
	}
	public Form getMsviewform() {
		return msviewform;
	}
	public void setMsviewform(Form msviewform) {
		this.msviewform = msviewform;
	}
	public List<FormItem> getMsaddformitems() {
		return msaddformitems;
	}
	public void setMsaddformitems(List<FormItem> msaddformitems) {
		this.msaddformitems = msaddformitems;
	}
	public List<FormItem> getMsupdformitems() {
		return msupdformitems;
	}
	public void setMsupdformitems(List<FormItem> msupdformitems) {
		this.msupdformitems = msupdformitems;
	}
	public List<FormItem> getMsviewformitems() {
		return msviewformitems;
	}
	public void setMsviewformitems(List<FormItem> msviewformitems) {
		this.msviewformitems = msviewformitems;
	}
	public List<TableColumn> getMstablecolumns() {
		return mstablecolumns;
	}
	public void setMstablecolumns(List<TableColumn> mstablecolumns) {
		this.mstablecolumns = mstablecolumns;
	}
	public String getMstablePrimary() {
		return mstablePrimary;
	}
	public void setMstablePrimary(String mstablePrimary) {
		this.mstablePrimary = mstablePrimary;
	}
	public String getMsmodelClassStr() {
		return msmodelClassStr;
	}
	public void setMsmodelClassStr(String msmodelClassStr) {
		this.msmodelClassStr = msmodelClassStr;
	}
	public String getMstableName() {
		return mstableName;
	}
	public void setMstableName(String mstableName) {
		this.mstableName = mstableName;
	}
	public String getMsmapperSelectField() {
		return msmapperSelectField;
	}
	public void setMsmapperSelectField(String msmapperSelectField) {
		this.msmapperSelectField = msmapperSelectField;
	}
	public String getRelationField() {
		return relationField;
	}
	public void setRelationField(String relationField) {
		this.relationField = relationField;
	}
	public String getMsrelationField() {
		return msrelationField;
	}
	public void setMsrelationField(String msrelationField) {
		this.msrelationField = msrelationField;
	}
	public String getMsconvertRelationField() {
		return msconvertRelationField;
	}
	public void setMsconvertRelationField(String msconvertRelationField) {
		this.msconvertRelationField = msconvertRelationField;
	}
	
	
}
