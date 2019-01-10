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
	// 模块关联表的关联关系
	String relInfo = null;
	// 存放页面字段标签的相关信息
	List<Element> list = null;
	// 存放表的表名加上主键组合的字段
	List<String> colcodelists = null;
	// 用于放表的主键策略
	List<String> tableprimpklist = null;
	List<Input> inputs = new ArrayList<Input>();
	List<FormItem> addformitems = new ArrayList<FormItem>();
	List<FormItem> updformitems = new ArrayList<FormItem>();
	List<FormItem> viewformitems = new ArrayList<FormItem>();
	List<TableColumn> tablecolumns = new ArrayList<TableColumn>();
	VelocityGetTemplateData vgtd = new VelocityGetTemplateData();
	DefaulteVueComponentPropertyValue dv = new DefaulteVueComponentPropertyValue();
	ParseJsonString pjs = new ParseJsonString();

	public VelocityGetTemplateMulitData(List<Element> list, List<String> colcodelist, List<String> tableprimpklist) {
		this.list = list;
		this.colcodelists = colcodelist;
		this.tableprimpklist = tableprimpklist;
	}

	public Model getModel(Element el, String packname) {
		Model model = new Model();
		// 设置模块标题名称
		model.setTitleName(el.getModuCname());
		// 设置模块标题图标
		model.setTitleIconType("compose");
		// 模块数据库表主键字段,有问题待处理
		model.setTablePrimaryList(colcodelists);
		// 前后端共同需要的信息，表名、交易号（映射路径）
		// 表名 首个字母大写
		String modelmoducode = ConvertString.convertFirstCharUpper(el.getModuCode().toLowerCase());
		model.setModel(modelmoducode);
		// 模块代码 全部字符小写
		model.setModuCode(el.getModuCode().toLowerCase());
		// 模块交易号
		model.setTid(el.getModuTc());

		// 多表模型 ,模块关联表(多表)，以逗号隔开的字符串
		model.setTableNames(el.getRelTable());
		// 多表模型,模块关联表之间的关联关系
		relInfo = el.getRelInfo();
		model.setTableInfo(this.dealTabInfo());
		// 多表模型，为了处理关联字段的赋值问题
		model.setChangeTableInfo(this.getChangeTabInfo(modelmoducode));

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

	/*
	 * 处理关联表的关联关系，将表A.字段=表B.字段=表C.字段改为A.字段=表B.字段，A.字段=表C.字段
	 * 
	 * @param
	 * 
	 * @param
	 * 
	 * @return
	 */
	public String dealTabInfo() {
		String temp = null;
		StringBuffer sb = new StringBuffer();
		String[] relInfoArray = relInfo.split("and");
		for (int j = 0; j < relInfoArray.length; j++) {
			String[] tabinfoarr = relInfoArray[j].split("=");
			for (int i = 1; i < tabinfoarr.length; i++) {
				if (j == relInfoArray.length - 1 && i == tabinfoarr.length - 1) {
					temp = tabinfoarr[0].replace(" ", "") + "=" + tabinfoarr[i].replace(" ", "");
				} else {
					temp = tabinfoarr[0].replace(" ", "") + "=" + tabinfoarr[i].replace(" ", "") + " AND ";
				}
				sb.append(temp);
			}
		}
		return sb.toString();
	}

	/**
	 * 为了处理关联字段的赋值问题
	 * 
	 * @param modelmoducode
	 *            模块代码
	 * @return
	 */
	public String getChangeTabInfo(String modelmoducode) {
		String temp = null;
		StringBuffer sb = new StringBuffer();
		String[] relInfoArray = relInfo.split("and");
		for (int j = 0; j < relInfoArray.length; j++) {
			String[] tabinfoarr = relInfoArray[j].split("=");
			for (int i = 1; i < tabinfoarr.length; i++) {
				String get = ConvertString
						.StringFirstCharUpper(ConvertString.convertSomeCharUpperReplace(tabinfoarr[0]).replace(" ", ""));
				String set = ConvertString
						.StringFirstCharUpper(ConvertString.convertSomeCharUpperReplace(tabinfoarr[i]).replace(" ", ""));
				if (i < tabinfoarr.length - 1) {
					temp = modelmoducode + ".set" + set + "(" + modelmoducode + ".get" + get + "());\r";
				} else {
					temp = "                " + modelmoducode + ".set" + set + "(" + modelmoducode + ".get" + get + "());";
				}
				sb.append(temp);
			}
		}
		return sb.toString();
	}

	/**
	 * 解析页面元素标签信息,并将标签中的属性值赋值
	 */
	public void setComponentPropertyValue() {
		// 字段名称
		String cname = null;
		// 字段
		String ename = null;
		// 将关联表的关联关系放在数组中
		String[] refinfoArray = relInfo.split("and");
		for (int i = 0; i < list.size(); i++) {
			// 用于判断该字段是否需要页面显示
			Boolean flag = true;
			// 字段名称
			cname = list.get(i).getEleCname();
			// 字段，字段全部小写，如果字段中有“_”或“.”,则将字符串中"_"或“.”去掉，后面的第一字母大写
			ename = ConvertString.convertSomeCharUpperReplace(list.get(i).getEleEname());
			// 判断字段是否为关联表关系中的字段
			for(int k=0;k<refinfoArray.length;k++){
				String[] tablerefinfo= refinfoArray[k].split("=");
				for (int j = 1; j < tablerefinfo.length; j++) {
					if (list.get(i).getEleEname().equals(tablerefinfo[j].trim())) {
						flag = false;
					}
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
					inputs.add(input);
				} else if (list.get(i).getComName().equals("列表信息")) {
					TableColumn tablecolumn = new TableColumn();
					// 字段名称
					tablecolumn.setLabel(cname);
					// 字段
					tablecolumn.setValue(ename);
					tablecolumns.add(tablecolumn);
				} else if (list.get(i).getComName().equals("新增信息") || list.get(i).getComName().equals("修改信息")) {
					// 如果字段为主键且主键生成策略为1（自动生成），则该字段在新增或修改页面都不显示
					for (int j = 0; j < colcodelists.size(); j++) {
						if (colcodelists.get(j).equals(ename) && tableprimpklist.get(j).equals("1")) {
							flag = false;
						}
					}
					if (flag) {
						String tagInfo = list.get(i).getTagInfo();
						String dataType = list.get(i).getDataType();
						String eleename = list.get(i).getEleEname();
						FormItem formitem = new FormItem();
						// 新增对话框字段的属性赋值
						formitem = this.setAddModalPropertyValue(cname, ename, tagInfo, dataType, eleename);
						if (list.get(i).getComName().equals("新增信息")) {
							addformitems.add(formitem);
						} else {
							updformitems.add(formitem);
						}
					}
				} else if (list.get(i).getComName().equals("查看信息")) {
					FormItem viewformitem = new FormItem();
					DatePicker dp = null;
					InputNumber in = null;
					Input vinput = null;
					// 如果查看信息的标签信息没有录入，则设置默认值
					if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
						// 查看信息，现在只支持input输入框,日期,数值
						if (list.get(i).getDataType().equals("date") || list.get(i).getDataType().equals("datetime")) {
							dp = new DatePicker();
							dp.setValue(ename);
						} else if (list.get(i).getDataType().equals("decimal")
								|| list.get(i).getDataType().equals("numeric")
								|| list.get(i).getDataType().equals("double")
								|| list.get(i).getDataType().equals("float") || list.get(i).getDataType().equals("int")
								|| list.get(i).getDataType().equals("bigint")) {
							in = new InputNumber();
							in.setValue(ename);
						} else {
							vinput = new Input();
							vinput.setValue(ename);
						}
						viewformitem.setLabel(cname);
						viewformitem.setInput(vinput);
						viewformitem.setDatepicker(dp);
						viewformitem.setInputNumber(in);
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
						if (vmodel.getDatepicker() != null) {
							vmodel.getDatepicker().setValue(ename);
						}
						if (vmodel.getInputnumber() != null) {
							vmodel.getInputnumber().setValue(ename);
						}
						viewformitem.setInput(vmodel.getInput());
					}
					viewformitems.add(viewformitem);
				}
			}

		}
	}

	/**
	 * 设置新增或修改对话框字段的属性以及属性赋值
	 * 
	 * @param cname
	 *            字段中文
	 * @param ename
	 *            转换的字段
	 * @param tagInfo
	 *            字段的标签信息
	 * @param dataType
	 *            字段在数据库的类型
	 * @param eleename
	 *            未转换的字段
	 * @param comname
	 *            单元类型
	 * @return
	 */
	public FormItem setAddModalPropertyValue(String cname, String ename, String tagInfo, String dataType,
			String eleename) {
		FormItem formitem = new FormItem();
		DatePicker dp = null;
		InputNumber in = null;
		Input auinput = null;
		// 如果新增信息或修改信息标签信息没有录入，则设置默认值
		if (tagInfo == null || "".equals(tagInfo)) {
			formitem.setLabel(cname);
			// 如果是字段为主键且主键生成策略为0（手动输入），设为必输
			for (int i = 0; i < colcodelists.size(); i++) {
				if (colcodelists.get(i).equals(ename)) {
					formitem.setProp(ename);
					formitem.setRequired("true");
				}
			}
			// 新增信息或修改信息表单，现在只支持input输入框,日期,数值
			if (dataType.equals("date") || dataType.equals("datetime")) {
				dp = new DatePicker();
				dp.setValue(ename);
				dp.setPlaceholder(cname);
			} else if (dataType.equals("decimal") || dataType.equals("numeric") || dataType.equals("double")
					|| dataType.equals("float") || dataType.equals("int") || dataType.equals("bigint")) {
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
			aumodel = pjs.parseJsonTagInfo(ConvertString.replaceSomeChar(tagInfo));
			if (aumodel.getFormitem() != null) {
				formitem = aumodel.getFormitem();
				if (formitem.getLabel() == null || formitem.getLabel() == "") {
					formitem.setLabel(cname);
				}
				// 如果字段为主键且主键生成策略为0（手动输入），则设为必输
				for (int i = 0; i < colcodelists.size(); i++) {
					if (colcodelists.get(i).equals(ename) && tableprimpklist.get(i).equals("0")) {
						if (formitem.getProp() == null || formitem.getProp() == "") {
							formitem.setProp(ename);
						} else {
							formitem.setProp(ConvertString.convertSomeCharUpper(formitem.getProp()));
						}
						if (formitem.getRequired() == null || formitem.getRequired() == "") {
							formitem.setRequired("true");
						}
					}
				}

			} else {
				formitem.setLabel(cname);
				// 如果字段为主键且主键生成策略为0（手动输入），则设为必输
				for (int i = 0; i < colcodelists.size(); i++) {
					if (colcodelists.get(i).equals(ename) && tableprimpklist.get(i).equals("0")) {
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
				auinput.setConvertValue(eleename);
				if (auinput.getPlaceholder() == null || auinput.getPlaceholder() == "") {
					auinput.setPlaceholder("请输入" + cname);
				}
				/*
				 * if (auinput.getType() == null || auinput.getType() == "") {
				 * auinput.setType("text"); } if (auinput.getIcon() == null ||
				 * auinput.getIcon() == "") { auinput.setIcon("search"); } if
				 * (auinput.getWidth() == null || auinput.getWidth() == "") {
				 * auinput.setWidth("200px"); } if (auinput.getOnChange() ==
				 * null || auinput.getOnChange() == "") {
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
		return formitem;
	}

}
