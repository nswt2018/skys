package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sky.app.coder.model.Button;
import com.sky.app.coder.model.DatePicker;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Form;
import com.sky.app.coder.model.FormItem;
import com.sky.app.coder.model.Input;
import com.sky.app.coder.model.Modal;
import com.sky.app.coder.model.Model;
import com.sky.app.coder.model.Page1;
import com.sky.app.coder.model.Table;
import com.sky.app.coder.model.TableColumn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 
 */
public class VelocityGetTemplateData {
	// 将模板中所需要的数据都封装在Model实体类中
	public Model getModel(List<Element> list, Element el, String str,String packname) {
		Model model = new Model();
		String cname=null;
		String ename=null;
		List<Input> inputs = new ArrayList<Input>();
		List<FormItem> addformitems = new ArrayList<FormItem>();
		List<FormItem> updformitems = new ArrayList<FormItem>();
		List<FormItem> viewformitems = new ArrayList<FormItem>();
		List<TableColumn> tablecolumns = new ArrayList<TableColumn>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getComName().equals("条件搜索")) {
				Input input = new Input();
				// 如果搜索框的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					input.setType("text");
					// 字段，字段全部小写，如果字段中有“_”,则将字段中"_"去掉后第一字母大写
					input.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname().toLowerCase()));
					// 设置mapping映射文件where后条件的字段，就是数据库表字段
					input.setConvertValue(list.get(i).getEleEname());
					// 字段名称
					input.setPlaceholder("请输入" + list.get(i).getEleCname());
					input.setIcon("search");
					input.setWidth("200px");
					input.setOnChange("true");
				} else {
					// 将数据库中取出的JSON字符串，去除字符串中的‘@’、‘：’放入input实体类中
					input = this.getTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo())).getInput();
				}
				inputs.add(input);
			} else if (list.get(i).getComName().equals("列表信息")) {
				TableColumn tablecolumn = new TableColumn();
				// 字段名称
				tablecolumn.setLabel(list.get(i).getEleCname());
				// 字段，先全部小写，如果字段中有“_”,将字段中"_"去掉后第一字母大写
				tablecolumn.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname().toLowerCase()));
				tablecolumns.add(tablecolumn);
			} else if (list.get(i).getComName().equals("新增信息")) {
				FormItem addformitem = new FormItem();
				// 如果新增信息的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					// 字段名称
					cname=list.get(i).getEleCname();
					//字段,先全部小写，如果字段中有“_”,将字段中"_"去掉后第一字母大写
					ename=ConvertString.convertSomeCharUpper(list.get(i).getEleEname().toLowerCase());
					addformitem.setLabel(cname);
					if(list.get(i).getDataType().equals("date")){
						DatePicker dp=new DatePicker();
						dp.setValue(ename);
						dp.setPlaceholder(cname);
						addformitem.setDatepicker(dp);
					}else{
						Input ainput=new Input();
						ainput.setValue(ename);
						ainput.setPlaceholder(cname);
						addformitem.setInput(ainput);
						addformitem.setProp(ename);
						addformitem.setRequired("true");
					}
				} else {
					Model amodel = new Model();
					// 将数据库中取出的JSON字符串,去除字符串中的‘@’、‘：’，放入FormItem实体类中
					amodel=this.getTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo()));
					addformitem = amodel.getFormitem();
					addformitem.setInput(amodel.getInput());
					addformitem.setDatepicker(amodel.getDatepicker());
				}
				addformitems.add(addformitem);
			} else if (list.get(i).getComName().equals("修改信息")) {
				FormItem updformitem = new FormItem();
				// 如果修改信息的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					// 字段名称
					cname=list.get(i).getEleCname();
					//字段,先全部小写，如果字段中有“_”,将字段中"_"去掉后第一字母大写
					ename=ConvertString.convertSomeCharUpper(list.get(i).getEleEname().toLowerCase());
					updformitem.setLabel(cname);
					if(list.get(i).getDataType().equals("date")){
						DatePicker dp=new DatePicker();
						dp.setValue(ename);
						dp.setPlaceholder(cname);
						updformitem.setDatepicker(dp);
					}else{
						Input uinput=new Input();
						uinput.setValue(ename);
						uinput.setPlaceholder(cname);
						updformitem.setInput(uinput);
						updformitem.setProp(ename);
						updformitem.setRequired("true");
					}
				} else {
					Model umodel = new Model();
					// 将数据库中取出的JSON字符串，去除字符串中的‘@’、‘：’，放入FormItem实体类中
					umodel=this.getTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo()));
					updformitem = umodel.getFormitem();
					updformitem.setInput(umodel.getInput());
					updformitem.setDatepicker(umodel.getDatepicker());
				}
				updformitems.add(updformitem);
			} else if (list.get(i).getComName().equals("查看信息")) {
				FormItem viewformitem = new FormItem();
				// 如果查看信息的标签信息没有录入，则设置默认值
				if (list.get(i).getTagInfo() == null || "".equals(list.get(i).getTagInfo())) {
					// 字段名称
					viewformitem.setLabel(list.get(i).getEleCname());
					// 字段，先全部小写，如果字段中有“_”,将字段中"_"去掉后第一字母大写
					Input vinput=new Input();
					vinput.setValue(ConvertString.convertSomeCharUpper(list.get(i).getEleEname().toLowerCase()));
					viewformitem.setInput(vinput);
				} else {
					Model vmodel = new Model();
					// 将数据库中取出的JSON字符串，去除字符串中的‘@’、‘：’放入FormItem实体类中
					vmodel=this.getTagInfo(ConvertString.replaceSomeChar(list.get(i).getTagInfo()));
					viewformitem = vmodel.getFormitem();
					viewformitem.setInput(vmodel.getInput());
					viewformitem.setDatepicker(vmodel.getDatepicker());
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
		// 表名 首个字母大写
		model.setModel(ConvertString.convertFirstCharUpper(el.getModuCode().toLowerCase()));
		// 模块代码 全部字符小写
		String lowerModuCode = el.getModuCode().toLowerCase();
		model.setModuCode(lowerModuCode);
		// 模块交易号
		model.setTid(el.getModuTc());
		// 模块数据库表名
		model.setTableName(el.getRelTable());
		//全部小写，模块数据库表主键字段 
		String colcode = ConvertString.convertSomeCharUpper(el.getColCode().toLowerCase());
		model.setTablePrimary(colcode);
		// 模块数据库表主键策略 0为手动录入，1为自动录入
		model.setTablePrimaryValue(el.getPkGen());
		// 实体类里面的属性 get/set 方法，传入参数（数据库表名，表主键）
		model.setModelClassStr(str);
		// 包名--controller 三级包名+模块代码（全部小写）+每层固定的命名
		model.setControllerPackName(packname  + ".controller");
		// 包名--service 三级包名+模块代码（全部小写）+每层固定的命名
		model.setServicePackName(packname  + ".service");
		// 包名--serviceimpl 三级包名+模块代码（全部小写）+每层固定的命名
		model.setServiceImplPackName(packname  + ".service.impl");
		// 包名--dao 三级包名+模块代码（全部小写）+每层固定的命名
		model.setDaoPackName(packname  + ".dao");
		// 包名--model 三级包名+模块代码（全部小写）+每层固定的命名
		model.setModelPackName(packname  + ".model");
		// vue各组件赋值
		model.setInputs(inputs);
		model.setAddformitem(addformitems);
		model.setUpdformitem(updformitems);
		model.setViewformitem(viewformitems);
		model.setTablecolumns(tablecolumns);
		return model;
	}

	/*
	 * 将这种JSON字符串[{"input":{"value":"paraValue"}}]，转为map,并将数据转到model实体类中
	 */
	public Model getTagInfo(String tagInfo) {
		// JSONObject
		JSONArray jsonArray = JSONArray.fromObject(tagInfo);
		// 获得jsonArray的第一个元素
		Object o = jsonArray.get(0);
		JSONObject jsonObject = JSONObject.fromObject(o);
		Map map = new HashMap();
		map.put("input", Input.class);
		map.put("formitem", FormItem.class);
		map.put("datepicker", DatePicker.class);
		// 使用了toBean方法，需要三个参数
		Model model = (Model) JSONObject.toBean(jsonObject, Model.class, map);
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
