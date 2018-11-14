package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.List;

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
	public Model getModel(List<Element> list,Element el) {
		Model model = new Model();
		List<Input> inputs = new ArrayList<Input>();
		Input input = null;
		List<FormItem> addformitems = new ArrayList<FormItem>();
		List<FormItem> updformitems = new ArrayList<FormItem>();
		List<FormItem> viewformitems = new ArrayList<FormItem>();
		List<TableColumn> tablecolumns = new ArrayList<TableColumn>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getComName().equals("条件搜索")) {
				input = new Input();
				// 如果搜索框的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					input.setType("text");
					//并将英文字段中"_"去掉后第一字母大写
					input.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname()));
					input.setConvertValue(list.get(i).getEleEname());
					input.setPlaceholder("请输入" + list.get(i).getEleCname());
					input.setIcon("search");
					input.setWidth("200px");
					input.setOnChange("true");
				} else {
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
					input = (Input) JSONObject.toBean(jsonObject, Input.class);
				}
				inputs.add(input);
			}else if (list.get(i).getComName().equals("列表信息")) {
				TableColumn tablecolumn = new TableColumn();
				//字段中文
				tablecolumn.setLabel(list.get(i).getEleCname());
				//字段英文，并将字段中"_"去掉后第一字母大写
				tablecolumn.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname()));
				tablecolumns.add(tablecolumn);
			} else if (list.get(i).getComName().equals("新增信息")) {
				FormItem addformitem = new FormItem();
				// 如果新增信息的标签信息没有录入，则设置默认值
				if(list.get(i).getTagInfo()==null|| "".equals(list.get(i).getTagInfo())){
					//字段中文
					addformitem.setLabel(list.get(i).getEleCname());
					//字段英文，并将字段中"_"去掉后第一字母大写
					addformitem.setProp(ConvertString.convertSomeCharUpper(list.get(i).getEleEname()));
					addformitem.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname()));
					addformitem.setRequired("true");
					addformitem.setType("input");
				}else{
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
					addformitem = (FormItem) JSONObject.toBean(jsonObject, FormItem.class);
				}
				addformitems.add(addformitem);
			} else if (list.get(i).getComName().equals("修改信息")) {
				FormItem updformitem = new FormItem();
				// 如果修改信息的标签信息没有录入，则设置默认值
				if(list.get(i).getTagInfo()==null|| "".equals(list.get(i).getTagInfo())){
					//字段中文
					updformitem.setLabel(list.get(i).getEleCname());
					//字段英文，并将字段中"_"去掉后第一字母大写
					updformitem.setProp(ConvertString.convertSomeCharUpper(list.get(i).getEleEname()));
					updformitem.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname()));
					updformitem.setRequired("true");
					updformitem.setType("input");
				}else{
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
					updformitem = (FormItem) JSONObject.toBean(jsonObject, FormItem.class);
				}
				updformitems.add(updformitem);
			} else if (list.get(i).getComName().equals("查看信息")) {
				FormItem viewformitem = new FormItem();
				if(list.get(i).getTagInfo()==null|| "".equals(list.get(i).getTagInfo())){
					// 如果查看信息的标签信息没有录入，则设置默认值
					//字段中文
					viewformitem.setLabel(list.get(i).getEleCname());
					//字段英文,并将字段中"_"去掉后第一字母大写
					viewformitem.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname()));
					viewformitem.setType("input");
				}else{
					JSONObject jsonObject = JSONObject.fromObject(list.get(i).getTagInfo());
					viewformitem = (FormItem) JSONObject.toBean(jsonObject, FormItem.class);
				}
				viewformitems.add(viewformitem);
			}
		}
		model.setButton(this.getButton());
		model.setPage(this.getPage());
		model.setTable(this.getTable());
		model.setModals(this.getModal());
		model.setAddform(this.getForm()[0]);
		model.setUpdform(this.getForm()[1]);
		model.setViewform(this.getForm()[2]);
		// 设置模块标题名称
		model.setTitleName(el.getModuCname());
		// 设置模块标题图标
		model.setTitleIconType("compose");
		// 前后端共同需要的信息，表名、交易号（映射路径）
		//表名
		model.setModel(ConvertString.convertFirstCharUpper(el.getModuCode()));
		//模块代码
		String lowerModuCode=el.getModuCode().toLowerCase();
		model.setModuCode(lowerModuCode);
		//模块交易号
		model.setTid(el.getModuTc());
		// 模块数据库表名
		model.setTableName(el.getRelTable());
		//模块数据库表主键字段
		String colcode=ConvertString.convertSomeCharUpper(el.getColCode());
		model.setTablePrimary(colcode);
		//实体类里面的属性 get/set 方法，传入参数（数据库表名，表主键）
		model.setModelClassStr(new reflectBean(el.getRelTable(),colcode).getClassStr());
		// 读取配置文件coderConfig.xml,获取配置文件的参数
		//包名--controller   三级包名+模块代码（全部小写）+每层固定的命名
		model.setControllerPackName(el.getPackName()+"."+lowerModuCode+".controller");
		//包名--service      三级包名+模块代码（全部小写）+每层固定的命名
		model.setServicePackName(el.getPackName()+"."+lowerModuCode+".service");
		//包名--serviceimpl 	三级包名+模块代码（全部小写）+每层固定的命名
		model.setServiceImplPackName(el.getPackName()+"."+lowerModuCode+".service.impl");
		//包名--dao			三级包名+模块代码（全部小写）+每层固定的命名
		model.setDaoPackName(el.getPackName()+"."+lowerModuCode+".dao");
		//包名--model		三级包名+模块代码（全部小写）+每层固定的命名
		model.setModelPackName(el.getPackName()+"."+lowerModuCode+".model");
		// vue各组件赋值
		model.setInputs(inputs);
		model.setAddformitem(addformitems);
		model.setUpdformitem(updformitems);
		model.setViewformitem(viewformitems);
		model.setTablecolumns(tablecolumns);
		return model;
	}
	public Button getButton() {
		Button button = new Button();
		button.setSize("true");
		return button;
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
	public Page1 getPage() {
		Page1 page = new Page1();
		page.setOnChange("true");
		page.setOnPageSizeChange("true");
		page.setShowElevator("true");
		page.setShowSizer("true");
		page.setShowTotal("true");
		page.setSize("true");
		page.setTransfer("true");
		return page;
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
		addform.setModel("addForm");
		addform.setRules("addRules");
		addform.setLabelWidth("100");
		Form updform = new Form();
		updform.setRef("updFormRef");
		updform.setModel("updForm");
		updform.setRules("updRules");
		updform.setLabelWidth("100");
		Form viewform = new Form();
		viewform.setRef("viewFormRef");
		viewform.setModel("viewForm");
		viewform.setLabelWidth("100");
		Form[] form = new Form[3];
		form[0] = addform;
		form[1] = updform;
		form[2] = viewform;
		return form;
	}

	
}
