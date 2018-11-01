package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sky.app.coder.congfig.Configuration;
import com.sky.app.coder.model.Button;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Form;
import com.sky.app.coder.model.FormItem;
import com.sky.app.coder.model.Input;
import com.sky.app.coder.model.Modal;
import com.sky.app.coder.model.Model;
import com.sky.app.coder.model.Page1;
import com.sky.app.coder.model.Table;
import com.sky.app.coder.model.TableColumn;

import net.sf.json.JSONObject;

/*
 * 
 */
public class VelocityGetTemplateData {
	// 将模板中所需要的数据都封装在Model实体类中
	public Model getModel(List<Element> list) {
		Model model = new Model();
		List<Input> inputs = new ArrayList<Input>();
		Input input = null;
		Button button = null;
		Page1 page = null;
		List<FormItem> addformitems = new ArrayList<FormItem>();
		List<FormItem> updformitems = new ArrayList<FormItem>();
		List<FormItem> delformitems = new ArrayList<FormItem>();
		List<TableColumn> tablecolumns = new ArrayList<TableColumn>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUnitType().equals("C")) {
				input = new Input();
				// 如果搜索框的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || list.get(i).getTagInfo() == "") {
					input.setType("text");
					input.setValue(list.get(i).getEleEname());
					input.setPlaceholder("请输入" + list.get(i).getEleCname());
					input.setIcon("search");
					input.setOnChange("true");
				} else {
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
					input = (Input) JSONObject.toBean(jsonObject, Input.class);
				}
				inputs.add(input);
			} else if (list.get(i).getUnitType().equals("B")) {
				button = new Button();
				// 如果按钮的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || list.get(i).getTagInfo() == "") {
					button.setSize("true");
					button.setType("true");
					button.setIcon("true");
				} else {
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
					button = (Button) JSONObject.toBean(jsonObject, Button.class);
				}

			} else if (list.get(i).getUnitType().equals("P")) {
				page = new Page1();
				// 如果分页的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || list.get(i).getTagInfo() == "") {
					page.setSize("true");
					page.setShowTotal("true");
					page.setShowElevator("true");
					page.setShowSizer("true");
				} else {
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
					page = (Page1) JSONObject.toBean(jsonObject, Page1.class);
				}

			} else if (list.get(i).getUnitType().equals("L")) {
				TableColumn tablecolumn = new TableColumn();
				tablecolumn.setLabel(list.get(i).getEleEname());
				tablecolumn.setValue(list.get(i).getEleCname());
				tablecolumns.add(tablecolumn);
			} else if (list.get(i).getUnitType().equals("A")) {
				JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
				FormItem fortitem = (FormItem) JSONObject.toBean(jsonObject, FormItem.class);
				addformitems.add(fortitem);
			} else if (list.get(i).getUnitType().equals("U")) {
				JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
				FormItem fortitem = (FormItem) JSONObject.toBean(jsonObject, FormItem.class);
				updformitems.add(fortitem);
			} else if (list.get(i).getUnitType().equals("S")) {
				JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
				FormItem fortitem = (FormItem) JSONObject.toBean(jsonObject, FormItem.class);
				delformitems.add(fortitem);
			}
		}
		if (button != null) {
			model.setButton(button);
		}
		if (page != null) {
			model.setPage(page);
		}
		model.setTable(this.getTable());
		model.setModals(this.getModal());
		model.setAddform(this.getForm()[0]);
		model.setUpdform(this.getForm()[1]);
		model.setViewform(this.getForm()[2]);
		// 设置模块标题名称
		model.setTitleName("系统参数");
		// 设置模块标题图标
		model.setTitleIconType("compose");
		// 前后端共同需要的信息，表名、交易号（映射路径）
		model.setModel("Parm");
		model.setTid("SYC001");
		// 数据库表名
		model.setTableName("sys_parm");
		// 读取配置文件coderConfig.xml,获取配置文件的参数
		Map<String,String> map=Configuration.loadConfig("b");
		//包名--controller
		model.setControllerPath(map.get("controller.packname"));
		//包名--service
		model.setServicePath(map.get("service.packname"));
		//包名--serviceimpl
		model.setServiceImplPath(map.get("serviceImpl.packname"));
		// vue各组件赋值
		model.setInputs(inputs);
		model.setAddformitem(addformitems);
		model.setUpdformitem(updformitems);
		model.setViewformitem(delformitems);
		model.setTablecolumns(tablecolumns);
		return model;
	}

	public Table getTable() {
		Table table = new Table();
		table.setIsSelectAll("true");
		table.setHeight("410");
		table.setBorder("true");
		table.setSize("true");
		table.setStripe("true");
		table.setRef("true");
		table.setOnSelect("true");
		table.setOnSelectCancel("true");
		table.setOnSelectAll("true");
		table.setOnSelectionChange("true");
		return table;
	}

	public List<Modal> getModal() {
		Modal modal1 = new Modal();
		modal1.setWidth("700");
		modal1.setTitle("信息查看");
		modal1.setValue("viewModal");
		modal1.setForm("viewForm");
		Modal modal2 = new Modal();
		modal2.setWidth("700");
		modal2.setTitle("信息新增");
		modal2.setValue("addModal");
		modal2.setCancelText("关闭");
		modal2.setOkText("保存");
		modal2.setLoading("addloading");
		modal2.setMaskClosable("false");
		modal2.setOnOk("");
		modal2.setOnCancel("");
		modal2.setForm("addForm");
		Modal modal3 = new Modal();
		modal3.setWidth("700");
		modal3.setTitle("信息修改");
		modal3.setValue("updModal");
		modal3.setCancelText("关闭");
		modal3.setOkText("保存");
		modal3.setLoading("updloading");
		modal3.setMaskClosable("false");
		modal3.setOnOk("");
		modal3.setOnCancel("");
		modal3.setForm("updForm");
		List<Modal> list = new ArrayList<Modal>();
		list.add(modal1);
		list.add(modal2);
		list.add(modal3);
		return list;
	}

	public Form[] getForm() {
		Form addform = new Form();
		addform.setRef("addFormRef");
		addform.setModel("addModal");
		addform.setRules("addRules");
		addform.setLabelWidth("100");
		Form updform = new Form();
		updform.setRef("updFormRef");
		updform.setModel("updModal");
		updform.setRules("updRules");
		updform.setLabelWidth("100");
		Form viewform = new Form();
		viewform.setRef("viewFormRef");
		viewform.setModel("viewModel");
		viewform.setLabelWidth("100");
		Form[] form = new Form[3];
		form[0] = addform;
		form[1] = updform;
		form[2] = viewform;
		return form;
	}

	public Button setButton() {
		Button button = new Button();
		button.setType("true");
		button.setIcon("true");
		button.setSize("true");
		return button;
	}

	public Page1 setPage() {
		Page1 page = new Page1();
		page.setOnChange("");
		page.setOnPageSizeChange("");
		page.setShowElevator("");
		page.setShowSizer("");
		page.setShowTotal("");
		page.setSize("");
		page.setTransfer("");
		return page;
	}

	public List<Input> setInput() {
		Input input1 = new Input();
		input1.setType("text");
		input1.setValue("categoryId");
		input1.setPlaceholder("请输入参数类型");
		input1.setWidth("200px");
		input1.setClearable("clearable");
		input1.setIcon("search");
		input1.setReadonly("true");
		input1.setOnChange("true");
		input1.setOnClick("true");
		input1.setOnEnter("true");
		input1.setSize("true");
		Input input2 = new Input();
		input2.setType("text");
		input2.setValue("paraCode");
		input2.setPlaceholder("请输入参数代码");
		input2.setWidth("200px");
		input2.setClearable("clearable");
		input2.setIcon("search");
		input2.setReadonly("true");
		input2.setOnChange("true");
		input2.setOnClick("true");
		input2.setOnEnter("true");
		List<Input> list1 = new ArrayList<Input>();
		list1.add(input1);
		list1.add(input2);
		return list1;
	}

	// FormItem�����ֵ
	public List<FormItem> setFormItem() {
		FormItem fi = new FormItem();
		fi.setLabel("参数类别");
		fi.setProp("categoryId");
		fi.setRequired("");
		fi.setType("input");
		fi.setPropValue("categoryId");
		FormItem fi1 = new FormItem();
		fi1.setLabel("参数代码");
		fi1.setProp("paraCode");
		fi1.setRequired("");
		fi1.setType("select");
		fi1.setPropValue("paraCode");
		FormItem fi2 = new FormItem();
		fi2.setLabel("参数名称");
		fi2.setProp("paraName");
		fi2.setRequired("");
		fi2.setType("radioGroup");
		fi2.setPropValue("paraName");
		FormItem fi3 = new FormItem();
		fi3.setLabel("参数值");
		fi3.setProp("paraValue");
		fi3.setRequired("");
		fi3.setType("checkboxGroup");
		fi3.setPropValue("paraValue");
		FormItem fi4 = new FormItem();
		fi4.setLabel("参数描述");
		fi4.setProp("paraDesc");
		fi4.setRequired("");
		fi4.setType("switch");
		fi4.setPropValue("paraDesc");
		FormItem fi5 = new FormItem();
		fi5.setLabel("创建人");
		fi5.setProp("crtUserCode");
		fi5.setRequired("");
		fi5.setType("slider");
		fi5.setPropValue("crtUserCode");
		List<FormItem> list = new ArrayList<FormItem>();
		list.add(fi);
		list.add(fi1);
		list.add(fi2);
		list.add(fi3);
		list.add(fi4);
		list.add(fi5);
		return list;
	}
}
