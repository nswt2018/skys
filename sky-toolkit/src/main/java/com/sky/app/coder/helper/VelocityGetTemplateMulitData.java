package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.List;

import com.sky.app.coder.model.DatePicker;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.FormItem;
import com.sky.app.coder.model.Input;
import com.sky.app.coder.model.InputNumber;
import com.sky.app.coder.model.Model;
import com.sky.app.coder.model.TableColumn;

/*
 * 多表模型 ， 将模板中所需要的数据都封装在Model实体类中
 */
public class VelocityGetTemplateMulitData {
	//存放页面字段标签的相关信息
	List<Element> list=null;
	//用于放关联表的主键
	List<String> colcodelists=new ArrayList<String>();
	List<Input> inputs = new ArrayList<Input>();
	List<FormItem> addformitems = new ArrayList<FormItem>();
	List<FormItem> updformitems = new ArrayList<FormItem>();
	List<FormItem> viewformitems = new ArrayList<FormItem>();
	List<TableColumn> tablecolumns = new ArrayList<TableColumn>();
	VelocityGetTemplateData vgtd = new VelocityGetTemplateData();
	DefaulteVueComponentPropertyValue dv = new DefaulteVueComponentPropertyValue();
	ParseJsonString pjs = new ParseJsonString();
	public VelocityGetTemplateMulitData(List<Element> list,List<String> colcodelist) {
		this.list=list;
		for(String colcode:colcodelist){
			this.colcodelists.add(ConvertString.convertSomeCharUpper(colcode.toLowerCase()));
		}
	}
	public Model getModel(Element el, String packname) {
		Model model = new Model();
		// 设置模块标题名称
		model.setTitleName(el.getModuCname());
		// 设置模块标题图标
		model.setTitleIconType("compose");
		// 模块数据库表主键字段,有问题待处理
		if(colcodelists!=null){
			model.setTablePrimary(colcodelists.get(0));
		}
		// 前后端共同需要的信息，表名、交易号（映射路径）
		// 表名 首个字母大写
		model.setModel(ConvertString.convertFirstCharUpper(el.getModuCode().toLowerCase()));
		// 模块代码 全部字符小写
		model.setModuCode(el.getModuCode().toLowerCase());
		// 模块交易号
		model.setTid(el.getModuTc());
		
		//模块关联表(多表)，以逗号隔开的字符串,多表模型
		model.setTableNames(el.getRelTable());
		//多个模块关联表之间的关联关系，多表模型
		model.setTableInfo(el.getRelInfo());
		
		// 包名--controller 二级包名+系统简码（全部小写）+每层固定的命名
		model.setControllerPackName(packname + ".controller");
		// 包名--service 二级包名+系统简码（全部小写）+每层固定的命名
		model.setServicePackName(packname + ".service");
		// 包名--serviceimpl 二级包名+系统简码（全部小写）+每层固定的命名
		model.setServiceImplPackName(packname + ".service.impl");
		// 包名--dao 二级包名+系统简码（全部小写）+每层固定的命名
		model.setDaoPackName(packname + ".dao");
		// 包名--model 二级包名+系统简码（全部小写）+每层固定的命名
		model.setModelPackName(packname + ".model");
		// 设置默认的组件属性值
		model.setButton(dv.getButton());
		model.setPage(dv.getPage());
		model.setTable(dv.getTable());
		model.setModals(dv.getModal());
		model.setAddform(dv.getForm()[0]);
		model.setUpdform(dv.getForm()[1]);
		model.setViewform(dv.getForm()[2]);
		this.setComponentPropertyValue();
		// vue非默认组件属性值赋值，通过前台页面输入，从数据库获取并做相应的处理
		model.setInputs(inputs);
		model.setAddformitem(addformitems);
		model.setUpdformitem(updformitems);
		model.setViewformitem(viewformitems);
		model.setTablecolumns(tablecolumns);
		return model;
	}

	// 解析页面元素标签信息,并将标签中的属性值赋值
	public void setComponentPropertyValue() {
		// 字段名称
		String cname = null;
		// 字段
		String ename = null;
		for (int i = 0; i < list.size(); i++) {
			// 字段名称
			cname = list.get(i).getEleCname();
			// 字段，字段全部小写，如果字段中有“_”,则将字段中"_"去掉后第一字母大写
			ename = ConvertString.convertSomeCharUpper(ConvertString.replaceStringDot(list.get(i).getEleEname()).toLowerCase());
			if (list.get(i).getComName().equals("条件搜索")) {
				Input input = new Input();
				// 如果搜索框的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					input.setType("text");
					// 字段
					input.setValue(ename);
					// 设置mapping映射文件where后条件的字段，就是数据库表字段
					input.setConvertValue(list.get(i).getEleEname());
					// 字段名称
					input.setPlaceholder("请输入" + cname);
					input.setIcon("search");
					input.setWidth("200px");
					input.setOnChange("true");
				} else {
					// 将数据库中取出的JSON字符串，去除字符串中的‘@’、‘：’放入input实体类中
					input = pjs.parseJsonTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo())).getInput();
					// 设置默认值
					input.setDefaultValue(input.getValue());
					// 字段
					input.setValue(ename);
					// 设置mapping映射文件where后条件的字段，就是数据库表字段
					input.setConvertValue(list.get(i).getEleEname());
					// 如果搜索框标签信息中属性不全，则设置默认必需的属性值
					if (input.getType() == null || input.getType() == "") {
						input.setType("text");
					}
					if (input.getPlaceholder() == null || input.getPlaceholder() == "") {
						input.setPlaceholder("请输入" + cname);
					}
					if (input.getIcon() == null || input.getIcon() == "") {
						input.setIcon("search");
					}
					if (input.getWidth() == null || input.getWidth() == "") {
						input.setWidth("200px");
					}
					if (input.getOnChange() == null || input.getOnChange() == "") {
						input.setOnChange("true");
					}
				}
				inputs.add(input);
			} else if (list.get(i).getComName().equals("列表信息")) {
				TableColumn tablecolumn = new TableColumn();
				// 字段名称
				tablecolumn.setLabel(cname);
				// 字段
				tablecolumn.setValue(ename);
				tablecolumns.add(tablecolumn);
			} else if (list.get(i).getComName().equals("新增信息") || list.get(i).getComName().equals("修改信息")) {
				FormItem formitem = new FormItem();
				DatePicker dp = null;
				InputNumber in = null;
				Input auinput = null;
				// 如果新增信息或修改信息标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					formitem.setLabel(cname);
					// 如果字段为主键且主键生成策略为0（手动输入），则设为必输，且有验证提示语
					for (String colcode : colcodelists) {
						if (colcode.equals(ename)) {
							formitem.setProp(ename);
							formitem.setRequired("true");
						}
					}
					// 新增信息或修改信息表单，现在只支持input输入框,日期,数值
					if (list.get(i).getDataType().equals("date") || list.get(i).getDataType().equals("datetime")) {
						dp = new DatePicker();
						dp.setValue(ename);
						dp.setPlaceholder(cname);
					} else if (list.get(i).getDataType().equals("decimal")
							|| list.get(i).getDataType().equals("numeric") || list.get(i).getDataType().equals("double")
							|| list.get(i).getDataType().equals("float") || list.get(i).getDataType().equals("int")
							|| list.get(i).getDataType().equals("bigint")) {
						in = new InputNumber();
						in.setValue(ename);
						in.setPlaceholder(cname);
						in.setMax(1000000);
						in.setMin(0);
					} else {
						auinput = new Input();
						auinput.setValue(ename);
						auinput.setPlaceholder(cname);
					}
					formitem.setInput(auinput);
					formitem.setDatepicker(dp);
					formitem.setInputNumber(in);
				} else {
					Model aumodel = new Model();
					// 将数据库中取出的JSON字符串,去除字符串中的‘@’、‘：’，放入FormItem实体类中
					aumodel = pjs.parseJsonTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo()));
					if (aumodel.getFormitem() != null) {
						formitem = aumodel.getFormitem();
						if (formitem.getLabel() == null || formitem.getLabel() == "") {
							formitem.setLabel(cname);
						}
						for (String colcode : colcodelists) {
							if (colcode.equals(ename)) {
								if (formitem.getProp() == null || formitem.getProp() == "") {
									formitem.setProp(ename);
								} else {
									formitem.setProp(
											ConvertString.convertSomeCharUpper(formitem.getProp().toLowerCase()));
								}
								if (formitem.getRequired() == null || formitem.getRequired() == "") {
									formitem.setRequired("true");
								}
							}
						}

					} else {
						formitem.setLabel(cname);
						for (String colcode : colcodelists) {
							if (colcode.equals(ename)) {
								formitem.setProp(ename);
								formitem.setRequired("true");
							}
						}
					}
					auinput = aumodel.getInput();
					// 如果新增或修改组件中录入的标签信息有input、Datepicker、InputNumber标签，则将value的值赋给defaultvalue
					if (auinput != null) {
						auinput.setDefaultValue(auinput.getValue());
						auinput.setValue(ename);
						auinput.setConvertValue(list.get(i).getEleEname());
						if (auinput.getPlaceholder() == null || auinput.getPlaceholder() == "") {
							auinput.setPlaceholder("请输入" + cname);
						}
						/*
						 * if (auinput.getType() == null || auinput.getType() ==
						 * "") { auinput.setType("text"); } if
						 * (auinput.getIcon() == null || auinput.getIcon() ==
						 * "") { auinput.setIcon("search"); } if
						 * (auinput.getWidth() == null || auinput.getWidth() ==
						 * "") { auinput.setWidth("200px"); } if
						 * (auinput.getOnChange() == null ||
						 * auinput.getOnChange() == "") {
						 * auinput.setOnChange("true"); }
						 */
					}
					dp = aumodel.getDatepicker();
					if (dp != null) {
						dp.setDefaultValue(dp.getValue());
						dp.setValue(ename);
						if (dp.getPlaceholder() == null || dp.getPlaceholder() == "") {
							dp.setPlaceholder("请输入" + cname);
						}
					}
					in = aumodel.getInputnumber();
					if (in != null) {
						in.setDefaultValue(in.getValue());
						in.setValue(ename);
						if (in.getMax() == 0) {
							in.setMax(100000);
						}
						if (in.getMin() == 0) {
							in.setMin(1);
						}
						if (in.getPlaceholder() == null || in.getPlaceholder() == "") {
							in.setPlaceholder("请输入" + cname);
						}
					}
					formitem.setInput(aumodel.getInput());
					formitem.setDatepicker(aumodel.getDatepicker());
					formitem.setInputNumber(aumodel.getInputnumber());
				}
				if (list.get(i).getComName().equals("新增信息")) {
					addformitems.add(formitem);
				} else {
					updformitems.add(formitem);
				}
			} else if (list.get(i).getComName().equals("查看信息")) {
				FormItem viewformitem = new FormItem();
				// 如果查看信息的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					// 字段名称
					viewformitem.setLabel(cname);
					Input vinput = new Input();
					// 字段
					vinput.setValue(ename);
					viewformitem.setInput(vinput);
				} else {
					Model vmodel = new Model();
					// 将数据库中取出的JSON字符串，去除字符串中的‘@’、‘：’放入FormItem实体类中
					vmodel = pjs.parseJsonTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo()));
					if (vmodel.getFormitem() != null) {
						viewformitem = vmodel.getFormitem();
						if (viewformitem.getLabel() == null || viewformitem.getLabel() == "") {
							viewformitem.setLabel("请输入" + cname);
						}
					} else {
						viewformitem.setLabel(cname);
					}
					if (vmodel.getInput() != null) {
						vmodel.getInput().setValue(ename);
					}
					viewformitem.setInput(vmodel.getInput());
				}
				viewformitems.add(viewformitem);
			}
		}
	}
}
