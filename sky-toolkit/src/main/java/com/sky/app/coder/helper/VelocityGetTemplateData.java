package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.List;

import com.sky.app.coder.model.Checkbox;
import com.sky.app.coder.model.DatePicker;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.FormItem;
import com.sky.app.coder.model.Input;
import com.sky.app.coder.model.InputNumber;
import com.sky.app.coder.model.Model;
import com.sky.app.coder.model.Radio;
import com.sky.app.coder.model.Select;
import com.sky.app.coder.model.TableColumn;

/*
 * 单表模型，将模板中所需要的数据都封装在Model实体类中
 */
public class VelocityGetTemplateData {
	// 主键字段
	private String colcode = null;
	// 主键策略
	private String pk = null;
	// 搜索框
	List<Input> inputs = new ArrayList<Input>();
	// 新增页面字段
	List<FormItem> addformitems = new ArrayList<FormItem>();
	// 修改页面字段
	List<FormItem> updformitems = new ArrayList<FormItem>();
	// 查看页面字段
	List<FormItem> viewformitems = new ArrayList<FormItem>();
	// 展示列表
	List<TableColumn> tablecolumns = new ArrayList<TableColumn>();
	// 获得解析json字符串的实例化
	ParseJsonString pjs = new ParseJsonString();

	public Model getModel(List<Element> list, Element el, String packname) {
		Model model = new Model();
		colcode = ConvertString.convertSomeCharUpper(el.getColCode());
		pk = el.getPkGen();
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
		// 模块数据库表名
		model.setTableName(el.getRelTable());
		// 全部小写，模块数据库表主键字段
		model.setTablePrimary(colcode);
		// 模块数据库表主键策略 0为手动录入，1为自动录入
		model.setTablePrimaryValue(pk);
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

		this.setComponentPropertyValue(list);
		// vue非默认组件属性值赋值，通过前台页面输入，从数据库获取并做相应的处理
		model.setInputs(inputs);
		model.setAddformitem(addformitems);
		model.setUpdformitem(updformitems);
		model.setViewformitem(viewformitems);
		model.setTablecolumns(tablecolumns);
		return model;
	}

	// 解析页面元素标签信息,并将标签中的属性值赋值
	public void setComponentPropertyValue(List<Element> list) {
		// 字段名称
		String cname = null;
		// 字段
		String ename = null;
		for (int i = 0; i < list.size(); i++) {
			// 字段名称
			cname = list.get(i).getEleCname();
			// 字段，字段全部小写，如果字段中有“_”,则将字段中"_"去掉后第一字母大写
			ename = ConvertString.convertSomeCharUpper(list.get(i).getEleEname());
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
				//如果字段为主键且主键生成策略为自动生成，则页面不显示
				if(colcode.equals(ename) && pk.equals("1")){
					
				}else{
					this.setFormItemPropertyValue(list.get(i));
				}
			} else if (list.get(i).getComName().equals("查看信息")) {
				this.setViewFormItemPropertyValue(list.get(i));
			}
		}
	}

	public void setFormItemPropertyValue(Element element) {
		FormItem formitem = new FormItem();
		DatePicker dp = null;
		InputNumber in = null;
		Input auinput = null;
		Select select = null;
		Radio radio = null;
		Checkbox checkbox = null;
		// 字段中文
		String cname = element.getEleCname();
		// 字段英文
		String ename = ConvertString.convertSomeCharUpper(element.getEleEname());
		// 标签信息
		String tagInfo = element.getTagInfo();
		// 单元名称
		String comName = element.getComName();
		// 显示类型，字符串、日期等
		String uiType = element.getUiType();
		//用于放字段和转换后的字段
		String [] fields=new String[2];
		fields[0]=ename;
		fields[1]=element.getEleEname();
		// 如果新增信息或修改信息标签信息没有录入，则设置默认值
		if (tagInfo == null || "".equals(tagInfo)) {
			formitem.setLabel(cname);
			// 如果字段为主键且主键生成策略为0（手动输入），则设为必输，且有验证提示语
			if (colcode.equals(ename)) {
				formitem.setProp(ename);
				formitem.setRequired("true");
			}
			// 新增信息或修改信息表单，现在只支持字符串,日期,数值，选择框，单选，多选
			if ("C1".equals(uiType)) {
				// 日期
				dp = new DatePicker();
				dp.setValue(ename);
				dp.setPlaceholder(cname);
			} else if ("D1".equals(uiType)) {
				// 数字
				in = new InputNumber();
				in.setValue(ename);
				in.setPlaceholder(cname);
				in.setMax(1000000);
				in.setMin(0);
				if(colcode.equals(ename)&&"修改信息".equals(comName)){
					in.setReadonly("true");
				}
			} else if ("A1".equals(uiType)) {
				// 字符串 单行
				auinput = new Input();
				auinput.setValue(ename);
				auinput.setPlaceholder(cname);
				if(colcode.equals(ename)&&"修改信息".equals(comName)){
					auinput.setReadonly("true");
				}
			} else if ("A2".equals(uiType)) {
				// 字符串 多行
				auinput = new Input();
				auinput.setValue(ename);
				auinput.setType("textarea");
				auinput.setPlaceholder(cname);
			} else if ("B1".equals(uiType)) {
				// 单选按钮
				radio = new Radio();
				radio.setValue(ename);
				radio.setRadioFields(fields);
			} else if ("B2".equals(uiType)) {
				// 多选按钮
				checkbox = new Checkbox();
				checkbox.setValue(ename);
				checkbox.setCheckboxFields(fields);
			} else if ("B3".equals(uiType)) {
				// 下拉选择框 单选
				select = new Select();
				select.setValue(ename);
				select.setSelectFields(fields);
			} else if ("B4".equals(uiType)) {
				// 下拉选择框 多选
				select = new Select();
				select.setValue(ename);
				select.setMultiple("true");
				select.setSelectFields(fields);
			}
			formitem.setInput(auinput);
			formitem.setDatepicker(dp);
			formitem.setInputNumber(in);
			formitem.setRadio(radio);
			formitem.setCheckbox(checkbox);
			formitem.setSelect(select);
		} else {
			Model aumodel = new Model();
			// 将数据库中取出的JSON字符串,去除字符串中的‘@’、‘：’，放入FormItem实体类中
			aumodel = pjs.parseJsonTagInfo(ConvertString.replaceSomeChar(tagInfo));
			if (aumodel.getFormitem() != null) {
				formitem = aumodel.getFormitem();
				if (formitem.getLabel() == null || formitem.getLabel() == "") {
					formitem.setLabel(cname);
				}
				if (colcode.equals(ename) && pk.equals("0")) {
					if (formitem.getProp() == null || formitem.getProp() == "") {
						formitem.setProp(ename);
					} else {
						formitem.setProp(ConvertString.convertSomeCharUpper(formitem.getProp()));
					}
					if (formitem.getRequired() == null || formitem.getRequired() == "") {
						formitem.setRequired("true");
					}
				}
			} else {
				formitem.setLabel(cname);
				if (colcode.equals(ename) && pk.equals("0")) {
					formitem.setProp(ename);
					formitem.setRequired("true");
				}
			}
			// 如果新增或修改组件中录入的标签信息有input、Datepicker、InputNumber标签，则将value的值赋给defaultvalue
			// 字符串
			auinput = aumodel.getInput();
			if (auinput != null) {
				auinput.setDefaultValue(auinput.getValue());
				auinput.setValue(ename);
				auinput.setConvertValue(element.getEleEname());
				if (auinput.getPlaceholder() == null || auinput.getPlaceholder() == "") {
					auinput.setPlaceholder("请输入" + cname);
				}
			}
			// 日期
			dp = aumodel.getDatepicker();
			if (dp != null) {
				dp.setDefaultValue(dp.getValue());
				dp.setValue(ename);
				if (dp.getPlaceholder() == null || dp.getPlaceholder() == "") {
					dp.setPlaceholder("请输入" + cname);
				}
			}
			// 数字
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
			// 下拉选择框
			select = aumodel.getSelect();
			if (select != null) {
				select.setDefaultValue(select.getValue());
				select.setValue(ename);
				select.setSelectFields(fields);
				if (select.getMultiple() == null || select.getMultiple() == "") {
					select.setMultiple("true");
				}
			}
			// 单选框
			radio = aumodel.getRadio();
			if (radio != null) {
				radio.setDefaultValue(radio.getValue());
				radio.setValue(ename);
				radio.setRadioFields(fields);
			}
			// 多选框
			checkbox = new Checkbox();
			if (checkbox != null) {
				checkbox.setDefaultValue(checkbox.getValue());
				checkbox.setValue(ename);
				checkbox.setCheckboxFields(fields);
			}
			formitem.setInput(aumodel.getInput());
			formitem.setDatepicker(aumodel.getDatepicker());
			formitem.setInputNumber(aumodel.getInputnumber());
			formitem.setSelect(aumodel.getSelect());
			formitem.setRadio(aumodel.getRadio());
			formitem.setCheckbox(aumodel.getCheckbox());
		}
		if ("新增信息".equals(comName)) {
			addformitems.add(formitem);
		} else {
			updformitems.add(formitem);
		}
	}

	public void setViewFormItemPropertyValue(Element element) {
		DatePicker dp = null;
		InputNumber in = null;
		Input vinput = null;
		Select select = null;
		Radio radio = null;
		Checkbox checkbox = null;
		// 字段英文
		String ename = ConvertString.convertSomeCharUpper(element.getEleEname());
		// 标签信息
		String tagInfo = element.getTagInfo();
		// 显示类型，字符串、日期等
		String uiType = element.getUiType();
		FormItem viewformitem = new FormItem();
		viewformitem.setLabel(element.getEleCname());
		//用于放字段和转换后的字段
		String [] fields=new String[2];
		fields[0]=ename;
		fields[1]=element.getEleEname();
		// 如果查看信息的标签信息没有录入，则设置默认值
		if (tagInfo == null || "".equals(tagInfo)) {
			// 新增信息或修改信息表单，现在只支持字符串,日期,数值，选择框，单选，多选
			if ("C1".equals(uiType)) {
				// 日期
				dp = new DatePicker();
				dp.setValue(ename);
			} else if ("D1".equals(uiType)) {
				// 数字
				in = new InputNumber();
				in.setValue(ename);
			} else if ("A1".equals(uiType)) {
				// 字符串 单行
				vinput = new Input();
				vinput.setValue(ename);
			} else if ("A2".equals(uiType)) {
				// 字符串 多行
				vinput = new Input();
				vinput.setValue(ename);
				vinput.setType("textarea");
			} else if ("B1".equals(uiType)) {
				// 单选按钮
				radio = new Radio();
				radio.setValue(ename);
				radio.setRadioFields(fields);
			} else if ("B2".equals(uiType)) {
				// 多选按钮
				checkbox = new Checkbox();
				checkbox.setValue(ename);
				checkbox.setCheckboxFields(fields);
			} else if ("B3".equals(uiType)) {
				// 下拉选择框 单选
				select = new Select();
				select.setValue(ename);
				select.setSelectFields(fields);
			} else if ("B4".equals(uiType)) {
				// 下拉选择框 多选
				select = new Select();
				select.setValue(ename);
				select.setMultiple("true");
				select.setSelectFields(fields);
			}
			viewformitem.setInput(vinput);
			viewformitem.setDatepicker(dp);
			viewformitem.setInputNumber(in);
			viewformitem.setRadio(radio);
			viewformitem.setCheckbox(checkbox);
			viewformitem.setSelect(select);
		} else {
			Model vmodel = new Model();
			// 将数据库中取出的JSON字符串，去除字符串中的‘@’、‘：’放入FormItem实体类中
			vmodel = pjs.parseJsonTagInfo(ConvertString.replaceSomeChar(tagInfo));
			//字符串 单行以及多行
			if (vmodel.getInput() != null) {
				vmodel.getInput().setValue(ename);
				if(vmodel.getInput().getType()!=null){
					vmodel.getInput().setType("true");
				}
			}
			//数字
			if(vmodel.getInputnumber()!=null){
				vmodel.getInputnumber().setValue(ename);;
			}
			//日期
			if(vmodel.getDatepicker()!=null){
				vmodel.getDatepicker().setValue(ename);
			}
			//下拉框
			if(vmodel.getSelect()!=null){
				vmodel.getSelect().setValue(ename);
				vmodel.getSelect().setSelectFields(fields);
			}
			//单选框
			if(vmodel.getRadio()!=null){
				vmodel.getRadio().setValue(ename);
				vmodel.getRadio().setRadioFields(fields);
			}
			//多选框
			if(vmodel.getCheckbox()!=null){
				vmodel.getCheckbox().setValue(ename);
				vmodel.getCheckbox().setCheckboxFields(fields);
			}
			viewformitem.setInput(vmodel.getInput());
			viewformitem.setInputNumber(vmodel.getInputnumber());
			viewformitem.setDatepicker(vmodel.getDatepicker());
			viewformitem.setSelect(vmodel.getSelect());
			viewformitem.setRadio(vmodel.getRadio());
			viewformitem.setCheckbox(vmodel.getCheckbox());
		}
		viewformitems.add(viewformitem);
	}
}
