package com.sky.app.coder.velocity;

import java.util.ArrayList;
import java.util.List;

import com.sky.app.coder.helper.ConvertString;
import com.sky.app.coder.helper.DefaulteVueComponentPropertyValue;
import com.sky.app.coder.helper.ParseJsonString;
import com.sky.app.coder.model.DatePicker;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.FormItem;
import com.sky.app.coder.model.Input;
import com.sky.app.coder.model.InputNumber;
import com.sky.app.coder.model.Model;
import com.sky.app.coder.model.TableColumn;

/*
 * 主从模型，将模板中所需要的数据都封装在Model实体类中
 */
public class VelocityGetMsTemplateData {
	//数据库表
	private String[] tablenamearr;
	// 主键字段
	private String[] colcodearr = null;
	// 主键策略
	private String[] pkarr = null;
	List<Input> inputs = new ArrayList<Input>();
	List<FormItem> addformitems = new ArrayList<FormItem>();
	List<FormItem> updformitems = new ArrayList<FormItem>();
	List<FormItem> viewformitems = new ArrayList<FormItem>();
	List<TableColumn> tablecolumns = new ArrayList<TableColumn>();
	List<Input> msinputs = new ArrayList<Input>();
	List<FormItem> msaddformitems = new ArrayList<FormItem>();
	List<FormItem> msupdformitems = new ArrayList<FormItem>();
	List<FormItem> msviewformitems = new ArrayList<FormItem>();
	List<TableColumn> mstablecolumns = new ArrayList<TableColumn>();

	public VelocityGetMsTemplateData(String[] tablenamearr, String[] colcodearr, String[] pkarr) {
		this.tablenamearr=tablenamearr;
		this.colcodearr = colcodearr;
		this.pkarr = pkarr;
	}

	public Model getModel(List<Element> list, String[] classstrarr, Element el, String packname,
			String lastSysCode) {
		Model model = new Model();
		// 设置模块标题名称
		model.setTitleName(el.getModuCname());
		// 设置模块标题图标
		model.setTitleIconType("compose");
		// 前后端共同需要的信息，表名、交易号（映射路径）
		// 表名 首个字母大写
		model.setModel(ConvertString.convertFirstCharUpper(el.getModuCode().toLowerCase()));
		// 模块代码 全部字符小写
		model.setModuCode(el.getModuCode().toLowerCase());
		// 模块交易号
		model.setTid(el.getModuTc());
		// 系统简码
		model.setSysCode(lastSysCode);
		// 模块数据库表名
		model.setTableName(tablenamearr[0]);
		// 从表模型，从表数据库表名
		model.setMstableName(tablenamearr[1]);
				
		// 全部小写，模块数据库表主键字段
		model.setTablePrimary(colcodearr[0]);
		// 从表模型，数据库表加字段组合字段
		model.setMstablePrimary(colcodearr[1]);
		// 实体类里面的属性 以及get/set 方法
		model.setModelClassStr(classstrarr[0]);
		// 从表模型，从表实体类里面的属性 以及get/set 方法
		model.setMsmodelClassStr(classstrarr[1]);
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
		DefaulteVueComponentPropertyValue dv = new DefaulteVueComponentPropertyValue();
		model.setButton(dv.getButton());
		model.setPage(dv.getPage());
		model.setTable(dv.getTable());
		model.setModals(dv.getModal());
		model.setAddform(dv.getForm()[0]);
		model.setUpdform(dv.getForm()[1]);
		model.setViewform(dv.getForm()[2]);
		// 主从模型，从表设置默认的组件属性值
		model.setMstable(dv.getMsTable());
		model.setMspage(dv.getPage());
		model.setMsmodals(dv.getMsModal());
		model.setMsaddform(dv.getMsForm()[0]);
		model.setMsupdform(dv.getMsForm()[1]);
		model.setMsviewform(dv.getMsForm()[2]);

		this.setComponentPropertyValue(list);
		// vue非默认组件属性值赋值，通过前台页面输入，从数据库获取并做相应的处理
		model.setInputs(inputs);
		model.setAddformitem(addformitems);
		model.setUpdformitem(updformitems);
		model.setViewformitem(viewformitems);
		model.setTablecolumns(tablecolumns);
		// 主从模型,从表vue非默认组件属性值赋值，通过前台页面输入，从数据库获取并做相应的处理
		model.setMsinputs(msinputs);
		model.setMsaddformitems(msaddformitems);
		model.setMsupdformitems(msupdformitems);
		model.setMsviewformitems(msviewformitems);
		model.setMstablecolumns(mstablecolumns);
		return model;
	}

	// 解析页面元素标签信息,并将标签中的属性值赋值
	public void setComponentPropertyValue(List<Element> list) {
		// 获得解析json字符串的实例化
		ParseJsonString pjs = new ParseJsonString();
		// 字段名称
		String cname = null;
		// 字段
		String ename = null;
		// 字段前面的表名
		String enametable = null;
		for (int i = 0; i < list.size(); i++) {
			// 用于判断该字段是否需要页面显示
			Boolean flag = true;
			// 字段名称
			cname = list.get(i).getEleCname();
			// 取字段“.”前面的表名,用于判断是主表字段，还是从表字段
			enametable = ConvertString.replaceStringDotBack(list.get(i).getEleEname());
			// 字段，字段全部小写，如果字段中有“_”或“.”,则将字符串中"_"或“.”去掉，后面的第一字母大写
			ename = ConvertString.convertSomeCharUpperReplace(list.get(i).getEleEname());
			for (int j = 0; j < colcodearr.length; j++) {
				if (list.get(i).getEleEname().equalsIgnoreCase(colcodearr[j]) && pkarr[j].equals("1")) {
					flag = false;
				}
			}
			if (flag) {
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
						input = pjs.parseJsonTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo()))
								.getInput();
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
					if (enametable.endsWith(tablenamearr[0])) {
						// 主表输入框
						inputs.add(input);
					} else {
						// 从表输入框
						msinputs.add(input);
					}

				} else if (list.get(i).getComName().equals("列表信息")) {
					TableColumn tablecolumn = new TableColumn();
					// 字段名称
					tablecolumn.setLabel(cname);
					// 字段
					tablecolumn.setValue(ename);
					if (enametable.endsWith(tablenamearr[0])) {
						// 主表列表
						tablecolumns.add(tablecolumn);
					} else {
						// 从表列表
						mstablecolumns.add(tablecolumn);
					}
				} else if (list.get(i).getComName().equals("新增信息") || list.get(i).getComName().equals("修改信息")) {
					FormItem formitem = new FormItem();
					DatePicker dp = null;
					InputNumber in = null;
					Input auinput = null;
					// 如果新增信息或修改信息标签信息没有录入，则设置默认值
					if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
						formitem.setLabel(cname);
						for (int j = 0; j < colcodearr.length; j++) {
							if (colcodearr[j].equals(ename)) {
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
								|| list.get(i).getDataType().equals("numeric")
								|| list.get(i).getDataType().equals("double")
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
							for (int j = 0; j < colcodearr.length; j++) {
								if (colcodearr[j].equals(ename)) {
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
							for (int j = 0; j < colcodearr.length; j++) {
								if (colcodearr[j].equals(ename)) {
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
					if (enametable.endsWith(tablenamearr[0])) {
						// 主表新增修改对话框
						if (list.get(i).getComName().equals("新增信息")) {
							addformitems.add(formitem);
						} else {
							updformitems.add(formitem);
						}
					} else {
						// 从表新增修改对话框
						if (list.get(i).getComName().equals("新增信息")) {
							msaddformitems.add(formitem);
						} else {
							msupdformitems.add(formitem);
						}
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
					if (enametable.endsWith(tablenamearr[0])) {
						// 主表查看对话框
						viewformitems.add(viewformitem);
					} else {
						// 从表查看对话框
						msviewformitems.add(viewformitem);
					}

				}
			}

		}
	}
}
